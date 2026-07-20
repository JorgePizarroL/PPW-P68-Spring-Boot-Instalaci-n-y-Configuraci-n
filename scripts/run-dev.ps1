$ProjectDir = Split-Path -Parent $PSScriptRoot
Set-Location $ProjectDir

Get-Content .env.dev | ForEach-Object {
    if ($_ -match '^\s*#' -or $_.Trim() -eq '') { return }
    $name, $value = $_ -split '=', 2
    [System.Environment]::SetEnvironmentVariable($name.Trim(), $value.Trim())
}

./gradlew.bat bootRun
