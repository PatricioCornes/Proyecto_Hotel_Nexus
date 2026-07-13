$ErrorActionPreference = "Stop"
$root = Split-Path -Parent $PSScriptRoot

$projects = @(
    @{ Pom = "nexus-eureka-server/pom.xml"; Wrapper = "Nexus_hospitality_rooms/mvnw.cmd" },
    @{ Pom = "nexus-staff/nexus-staff/pom.xml"; Wrapper = "nexus-staff/nexus-staff/mvnw.cmd" },
    @{ Pom = "Nexus_hospitality_rooms/pom.xml"; Wrapper = "Nexus_hospitality_rooms/mvnw.cmd" },
    @{ Pom = "Nexus_hospitality_restaurant/pom.xml"; Wrapper = "Nexus_hospitality_restaurant/mvnw.cmd" },
    @{ Pom = "Nexus_hospitality_reservation/pom.xml"; Wrapper = "Nexus_hospitality_reservation/mvnw.cmd" },
    @{ Pom = "Nexus_hospitality_payments/pom.xml"; Wrapper = "Nexus_hospitality_payments/mvnw.cmd" },
    @{ Pom = "Nexus_hospitality_garage/Nexus_hospitality_garage/pom.xml"; Wrapper = "Nexus_hospitality_garage/Nexus_hospitality_garage/mvnw.cmd" },
    @{ Pom = "Nexus_hospitality_cleaning_service/pom.xml"; Wrapper = "Nexus_hospitality_cleaning_service/mvnw.cmd" }
)

foreach ($project in $projects) {
    Write-Host "Empaquetando $($project.Pom)" -ForegroundColor Cyan
    & "$root/$($project.Wrapper)" -q -f "$root/$($project.Pom)" clean package
    if ($LASTEXITCODE -ne 0) { throw "Fallo Maven en $($project.Pom)" }
}

Write-Host "Los ocho artefactos se generaron correctamente." -ForegroundColor Green
