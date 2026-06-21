$ErrorActionPreference = 'Stop'
Write-Host "COMPILANDO Y LEVANTANDO CUBEBUSTER PERFIL RENDER LOCAL" -ForegroundColor Cyan
mvn clean package -DskipTests
if ($LASTEXITCODE -ne 0) { throw "Fallo la compilacion de Maven." }

docker compose -f docker-compose.render-local.yml up --build -d
if ($LASTEXITCODE -ne 0) { throw "Docker Compose no pudo levantar." }

Write-Host "`nURLs de Pruebas locales (Gateway):" -ForegroundColor Green
Write-Host "GET http://localhost:8080/api/v1/clientes/"
Write-Host "GET http://localhost:8080/api/v1/peliculas/"
Write-Host "GET http://localhost:8080/api/v1/juegos/"
Write-Host "GET http://localhost:8080/api/v1/reservas/"