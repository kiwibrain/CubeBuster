param(
    [Parameter(Mandatory = $true)]
    [ValidatePattern('^[a-z0-9-]+$')]
    [string]$Prefijo
)

$template = Join-Path $PSScriptRoot '..\render.yaml.template'
$salida = Join-Path $PSScriptRoot '..\render.yaml'

if (-not (Test-Path $template)) {
    throw "No se encontro el archivo render.yaml.template"
}

(Get-Content $template -Raw).Replace('TU-PREFIJO', $Prefijo) |
    Set-Content $salida -Encoding utf8

Write-Host "Archivo render.yaml generado correctamente." -ForegroundColor Green
Write-Host "Prefijo utilizado: $Prefijo"
Write-Host "Gateway esperado: https://$Prefijo-fullstack-gateway.onrender.com"