#!/usr/bin/env bash
# Prepara SonarQube local: contrasena de admin, gate "Curso Java" (analisis estatico, sin
# cobertura) asignado a este proyecto, y token de analisis. Idempotente.
# Ejecutar desde la raiz del repo: ./sonar/provision.sh
set -euo pipefail
SONAR="http://localhost:9000"
NEWPASS='Adecco.Sonar.2026'
PROJECT_KEY='g1_java'
GATE='Curso Java'

echo "Esperando a que SonarQube este UP en $SONAR (puede tardar 1-2 min)..."
for _ in $(seq 1 90); do
  st=$(curl -s "$SONAR/api/system/status" | grep -oE '"status":"[^"]+"' | cut -d'"' -f4 || true)
  if [ "$st" = "UP" ]; then echo "SonarQube UP."; break; fi
  echo "  estado: ${st:-arrancando} ..."; sleep 5
done

valid() { curl -s -u "admin:$1" "$SONAR/api/authentication/validate" | grep -q '"valid":true'; }

if valid admin; then
  curl -s -u "admin:admin" -X POST "$SONAR/api/users/change_password" \
    --data-urlencode login=admin --data-urlencode previousPassword=admin --data-urlencode password="$NEWPASS" >/dev/null || true
  PASS="$NEWPASS"
elif valid "$NEWPASS"; then
  PASS="$NEWPASS"
else
  echo "ERROR: no puedo autenticar como admin"; exit 1
fi
AUTH="admin:$PASS"

post() { curl -s -u "$AUTH" -X POST "$SONAR$1" "${@:2}" >/dev/null || true; }

post /api/qualitygates/create --data-urlencode name="$GATE"
for id in $(curl -s -u "$AUTH" "$SONAR/api/qualitygates/show?name=Curso%20Java" | grep -oE '"id":"[^"]+"' | cut -d'"' -f4); do
  curl -s -u "$AUTH" -X POST "$SONAR/api/qualitygates/delete_condition" --data-urlencode id="$id" >/dev/null || true
done
post /api/qualitygates/create_condition --data-urlencode gateName="$GATE" --data-urlencode metric=duplicated_lines_density --data-urlencode op=GT --data-urlencode error=10
post /api/projects/create     --data-urlencode project="$PROJECT_KEY" --data-urlencode name="$PROJECT_KEY"
post /api/qualitygates/select --data-urlencode gateName="$GATE" --data-urlencode projectKey="$PROJECT_KEY"

curl -s -u "$AUTH" -X POST "$SONAR/api/user_tokens/revoke" --data-urlencode name="$PROJECT_KEY" >/dev/null || true
TOKEN=$(curl -s -u "$AUTH" -X POST "$SONAR/api/user_tokens/generate" --data-urlencode name="$PROJECT_KEY" | grep -oE '"token":"[^"]+"' | cut -d'"' -f4)
[ -n "$TOKEN" ] || { echo "ERROR: no se pudo generar el token"; exit 1; }
printf '%s' "$TOKEN" > sonar/token.txt

echo ""
echo "Listo. Login: admin / $NEWPASS  |  proyecto: $PROJECT_KEY  |  gate: $GATE  |  token en sonar/token.txt"
echo "Siguiente:  ./sonar/analyze.sh"
