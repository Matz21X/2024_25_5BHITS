Function Encrypt-Asymmetric {
    [CmdletBinding()]
    [OutputType([System.String])]
    param(
        [Parameter(Position=0, Mandatory=$true)][ValidateNotNullOrEmpty()][System.String]
        $ClearText,
        [Parameter(Position=1, Mandatory=$true)][ValidateNotNullOrEmpty()][System.String]
        $CertThumbprint
    )
    # Encrypts a string with the public key
    # Assumes the certificate is in the LocalMachine\My (Personal) Store
    $Cert = Get-ChildItem cert:\CurrentUser\My | where { $_.Thumbprint -eq $CertThumbprint }
    if($Cert) {
        $ByteArray = [System.Text.Encoding]::UTF8.GetBytes($ClearText)
        $EncryptedByteArray = $Cert.PublicKey.Key.Encrypt($ByteArray,$true)
        $EncryptedBase64String = [Convert]::ToBase64String($EncryptedByteArray)
    }
    Else {Write-Error "Certificate with thumbprint: $CertThumbprint not found!"}

    Return $EncryptedBase64String 
}

Function Decrypt-Asymmetric {
    [CmdletBinding()]
    [OutputType([System.String])]
    param(
        [Parameter(Position=0, Mandatory=$true)][ValidateNotNullOrEmpty()][System.String]
        $EncryptedBase64String,
        [Parameter(Position=1, Mandatory=$true)][ValidateNotNullOrEmpty()][System.String]
        $CertThumbprint
    )
    # Decrypts text using the private key
    # Assumes the certificate is in the LocalMachine\My (Personal) Store
    $Cert = Get-ChildItem cert:\CurrentUser\My | where { $_.Thumbprint -eq $CertThumbprint }
    if($Cert) {
        $EncryptedByteArray = [Convert]::FromBase64String($EncryptedBase64String)
        $ClearText = [System.Text.Encoding]::UTF8.GetString($Cert.PrivateKey.Decrypt($EncryptedByteArray,$true))
    }
    Else {Write-Error "Certificate with thumbprint: $CertThumbprint not found!"}

    Return $ClearText
}


### New-SelfSignedCertificate -DnsName "abc.com" -CertStoreLocation "Cert:\CurrentUser\My"

$cleartext = "This is my super secret message."
$certificateHash = "BBD334F5FD4391F9F2052B327E63A00994F09203"

$encryptedText = Encrypt-Asymmetric $cleartext $certificateHash
Measure-Command { $encryptedText = Encrypt-Asymmetric $cleartext $certificateHash }

$encryptedText
Write-Host

Decrypt-Asymmetric $encryptedText $certificateHash
Measure-Command { Decrypt-Asymmetric $encryptedText $certificateHash }