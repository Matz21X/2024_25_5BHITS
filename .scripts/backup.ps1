$sourcePath = "C:\Users\matth\Documents\2024_25_5BHITS"
$destinationPath = "C:\Users\matth\OneDrive - HTL Hollabrunn\2023_24_4BHITS"
Set-Location "C:\Users\matth\Documents\2024_25_5BHITS"

function UpdateOneDrive {
    if (Test-Path $sourcePath -PathType Container) {
        if (-not (Test-Path $destinationPath -PathType Container)) {
            New-Item -Path $destinationPath -ItemType Directory -Force
        }
    
        try {
            Copy-Item -Path "$sourcePath\*" -Destination $destinationPath -Force -Recurse -Exclude ".git"
            Write-Host "Copy successful"
            
        } catch {
            Write-Host "Error at copying: $_"
        }
    } else {
        Write-Host "The sourcefolder does not exist"
    }
}

function Test-GithubConnection {
    $ErrorActionPreference = "Stop"
    try {
        $pingResult = Test-Connection -ComputerName "github.com" -Count 1 -ErrorAction SilentlyContinue
        if ($null -ne $pingResult) {
            Write-Host "Github is reachable"
            return $true
        } else {
            Write-Host "Github is not reachable"
            return $false
        }
    } catch {
        Write-Host "Github is not reachable"
        return $false
    }
}

function GitPull {
    $gitPullOutput = git pull
    if ($LASTEXITCODE -eq 0) {
    Write-Host "Git Pull-Output: $gitPullOutput"
    #UpdateOneDrive
    Pause
} else {
    Write-Host "ERROR: Git-Pull"
    Write-Host "Git Pull-Output: $gitPullOutput"
    Pause
}

}
if (Test-GithubConnection) {
    GitPull
} else {
    Write-Host "Cannot perform tasks that require an Github connection"
}