$ErrorActionPreference = 'Stop'
docker compose -f docker-compose.render-local.yml down
Write-Host "Ecosistema local del perfil Render detenido." -ForegroundColor Green