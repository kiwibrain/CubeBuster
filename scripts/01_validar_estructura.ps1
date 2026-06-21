$ErrorActionPreference = "SilentlyContinue"
$script:HayErrores = $false

function Mostrar-Resultado {
    param([string]$Nombre, [bool]$Correcto, [string]$Detalle)
    if ($Correcto) { Write-Host "[OK]    $Nombre - $Detalle" -ForegroundColor Green }
    else { Write-Host "[ERROR] $Nombre - $Detalle" -ForegroundColor Red; $script:HayErrores = $true }
}

$raizProyecto = Split-Path -Parent $PSScriptRoot
Set-Location $raizProyecto

Write-Host "VALIDANDO DOCKERFILES EN RENDER/DOCKER/..." -ForegroundColor Cyan
$dockerfiles = @(
    "api-gateway", "clientes-service", "reservas-service", "reclamos-service", "peliculas-service",
    "juegos-service", "resenas-service", "pagos-service", "cupones-service", "sucursales-service", "multas-service"
)
foreach ($df in $dockerfiles) {
    $ruta = "render\docker\$df.Dockerfile"
    Mostrar-Resultado $ruta (Test-Path $ruta) "Archivo Dockerfile"
}

if ($script:HayErrores) { Write-Host "CORRIJA LOS DOCKERFILES FALTANTES." -ForegroundColor Yellow; exit 1 }
else { Write-Host "ESTRUCTURA CORRECTA PARA DESPLIEGUE." -ForegroundColor Green }