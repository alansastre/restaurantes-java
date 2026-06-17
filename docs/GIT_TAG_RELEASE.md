

# Versionado y Release

## Instalar gh

https://cli.github.com/

```bash
gh --version
winget install --id GitHub.cli

gh auth login
gh auth status

git status 
git branch --show-current 
git pull 
```

## Actualizar versión en el pom.xml

```xml
<!-- ANTES -->
<version>0.0.1-SNAPSHOT</version>
<!-- DESPUÉS -->
<version>1.0.0</version>
```

## Compilar y empaquetar

```bash
 .\mvnw clean verify
```

