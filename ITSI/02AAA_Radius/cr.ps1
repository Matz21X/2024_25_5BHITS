Param (
    [Parameter(Mandatory=$true)]
    [string]
    $Password
)

$nonce = -join ((65..90) + (97..122) | Get-Random -Count 5 | % {[char]$_})
$timestamp = Get-Date

$salt = -join ((65..90) + (97..122) | Get-Random -Count 5 | % {[char]$_})

$hasher = [System.Security.Cryptography.HashAlgorithm]::Create('sha256')

Write-Host
Write-Host "Nonce:" $nonce
Write-Host "Current Time: " $timestamp
Write-Host

Write-Host "Task 1: Plain Text CR"
$task1hash = $hasher.ComputeHash([System.Text.Encoding]::UTF8.GetBytes($Password + $nonce))
$task1hashshstring = [System.BitConverter]::ToString($task1hash).replace('-', '')
Write-Host "Plain-Text Password: " $Password
Write-Host "Nonce:" $nonce
Write-Host "Hash: " $task1hashshstring
Write-Host

Write-Host "Task 2: SHA256 Hashed CR"
$sha256hash = $hasher.ComputeHash([System.Text.Encoding]::UTF8.GetBytes($Password))
$sha256hashstring = [System.BitConverter]::ToString($sha256hash).replace('-', '')

$task2hash = $hasher.ComputeHash([System.Text.Encoding]::UTF8.GetBytes($sha256hashstring + $nonce))
$task2hashshstring = [System.BitConverter]::ToString($task2hash).replace('-', '')
Write-Host "Plain-Text Password: " $Password
Write-Host "SHA256 Hash:" $sha256hashstring
Write-Host "Nonce:" $nonce
Write-Host "Hash: " $task2hashshstring
Write-Host

Write-Host "Task 3: Salted SHA256 Hashed CR"
$sha256saltedhash = $hasher.ComputeHash([System.Text.Encoding]::UTF8.GetBytes($Password + $salt))
$sha256saltedhashstring = [System.BitConverter]::ToString($sha256saltedhash).replace('-', '')

$task3hash = $hasher.ComputeHash([System.Text.Encoding]::UTF8.GetBytes($sha256saltedhashstring + $nonce))
$task3hashshstring = [System.BitConverter]::ToString($task3hash).replace('-', '')
Write-Host "Plain-Text Password: " $Password
Write-Host "Salt: " $salt
Write-Host "SHA256 Salted Hash:" $sha256saltedhashstring
Write-Host "Nonce:" $nonce
Write-Host "Hash: " $task3hashshstring
Write-Host

Write-Host "Task 4: Salted SHA256 Hashed Timestamp CR"
$sha256saltedhash = $hasher.ComputeHash([System.Text.Encoding]::UTF8.GetBytes($Password + $salt))
$sha256saltedhashstring = [System.BitConverter]::ToString($sha256saltedhash).replace('-', '')

$task4hash = $hasher.ComputeHash([System.Text.Encoding]::UTF8.GetBytes($sha256saltedhashstring + $nonce + $timestamp))
$task4hashshstring = [System.BitConverter]::ToString($sha256saltedhash).replace('-', '')
$task4timediff = New-TimeSpan –Start $timestamp –End $(Get-Date)
Write-Host "Plain-Text Password: " $Password
Write-Host "Salt: " $salt
Write-Host "SHA256 Salted Hash:" $sha256saltedhashstring
Write-Host "Timestamp: " $timestamp
Write-Host "Nonce:" $nonce
Write-Host "Hash: " $task4hashshstring
Write-Host "Time Difference: " $task4timediff
Write-Host

