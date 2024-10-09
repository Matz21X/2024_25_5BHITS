Get-FileHash -Path "$PSScriptRoot\plane.jpg" -Algorithm MD5
Get-FileHash -Path "$PSScriptRoot\ship.jpg" -Algorithm MD5


Get-FileHash -Path "$PSScriptRoot\ship.jpg" -Algorithm SHA256
Get-FileHash -Path "$PSScriptRoot\plane.jpg" -Algorithm SHA256