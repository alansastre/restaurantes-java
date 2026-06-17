

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

## Crear y subir tag de git

```bash
git tag -a v1.0.0 -m "release v1.0.0 primera version estable"
git push origin --tags
```

## Crear release de GitHub

```shell
gh release create v1.0.0 `
--title "v1.0.0: primera versión de Restaurantes FoodDevs" `
--notes "MVC CRUD completo con API REST" `
target/restaurantes-java-1.0.0.jar
```

o en https://github.com/alansastre/restaurantes-java/releases