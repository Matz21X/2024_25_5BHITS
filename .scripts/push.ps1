Set-Location "C:\Users\matth\Documents\2024_25_5BHITS"
$commitMessage = "Vault backup: $(Get-Date -Format 'dd/MM/yy HH:mm:ss') $env:COMPUTERNAME SHUTDOWN"

git add -A
git commit -m $commitMessage
git push
if ($LASTEXITCODE -eq 0) {
    shutdown.exe /p
} else {
    Pause
}