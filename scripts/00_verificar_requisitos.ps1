$ErrorActionPreference = "SilentlyContinue"
$script:HayErrores = $false

function Mostrar-Resultado {
    param([string]$Nombre, [bool]$Correcto, [string]$Detalle)
    if ($Correcto) { Write-Host "[OK]    $Nombre - $Detalle" -ForegroundColor Green }
    else { Write-Host "[ERROR] $Nombre - $Detalle" -ForegroundColor Red; $script:HayErrores = $true }
}

Write-Host "==========================================================" -ForegroundColor Cyan
Write-Host " CHECKLIST PREVIO - CUBEBUSTER RENDER" -ForegroundColor Cyan
Write-Host "==========================================================" -ForegroundColor Cyan

$archivos = @(
    "pom.xml", "render.yaml.template", "docker-compose.render-local.yml", "postman_render_online.json",
    "scripts\configurar-render.ps1", "scripts\01_validar_estructura.ps1", "scripts\02_probar_render_local.ps1", "scripts\03_detener_render_local.ps1"
)
foreach ($archivo in $archivos) { Mostrar-Resultado $archivo (Test-Path $archivo) "Estado Archivo" }

$carpetas = @(
    "api-gateway", "clientes-service", "reservas-service", "reclamos-service", "peliculas-service",
    "juegos-service", "resenas-service", "pagos-service", "cupones-service", "sucursales-service",
    "multas-service", "eureka-server", "render", "scripts"
)
foreach ($carpeta in $carpetas) { Mostrar-Resultado $carpeta (Test-Path $carpeta) "Estado Carpeta" }

if ($script:HayErrores) { Write-Host "RESULTADO: FALTAN COMPONENTES." -ForegroundColor Yellow }
else { Write-Host "RESULTADO: TODO EN ORDEN PARA EMPEZAR." -ForegroundColor Green }