#Convert a string into a secret key.
Function Convert-SecretKey
{
    [CmdletBinding()]
    Param
    (
        [string]$Key
    )

    #Get key length.
    $Length = $Key.Length;
    
    #Pad length.
    $Pad = 32-$Length;
    
    #If the length is less than 16 or more than 32.
    If(($Length -lt 16) -or ($Length -gt 32))
    {
        #Throw exception.
        Throw "String must be between 16 and 32 characters";
    }
    
    #Create a new ASCII encoding object.
    $Encoding = New-Object System.Text.ASCIIEncoding;

    #Get byte array.
    $Bytes = $Encoding.GetBytes($Key + "0" * $Pad);

    #Return byte array.
    Return $Bytes;
}

#Encrypt data with a secret key.
Function Encrypt-Data
{
    [CmdletBinding()]
    Param
    (
        $Key,
        [string]$TextInput
    )
    
    #Create a new secure string object.
    $SecureString = New-Object System.Security.SecureString;

    #Convert the text input to a char array.
    $Chars = $TextInput.ToCharArray();
    
    #Foreach char in the array.
    ForEach($Char in $Chars)
    {
        #Append the char to the secure string.
        $SecureString.AppendChar($Char);
    }
    
    #Encrypt the data from the secure string.
    $EncryptedData = ConvertFrom-SecureString -SecureString $SecureString -Key $Key;

    #Return the encrypted data.
    Return $EncryptedData;
}

#Decrypt data with a secret key.
Function Decrypt-Data
{
    [CmdletBinding()]
    Param
    (
        $Key,
        $TextInput
    )

    #Decrypt the text input with the secret key.
    $Result = $TextInput | ConvertTo-SecureString -Key $Key | ForEach-Object {[Runtime.InteropServices.Marshal]::PtrToStringAuto([Runtime.InteropServices.Marshal]::SecureStringToBSTR($_))};

    #Return the decrypted data.
    Return $Result;
}

#Text you want to encrypt
$TextInput = "This is my super secret message.";
$sharedSecret = "01234567890123456"

#Create a secret key (between 16 or 32 in length).
$Key = Convert-SecretKey -Key $sharedSecret;

#Encrypt data with the secret key using AES
$EncryptedData = Encrypt-Data -Key $Key -TextInput $TextInput;
Measure-Command { $EncryptedData = Encrypt-Data -Key $Key -TextInput $TextInput; }

# Show the encrypted data
$EncryptedData
Write-Host

#Decrypt data with the secret key using AES
$DecryptedData = Decrypt-Data -Key $Key -TextInput $EncryptedData;
Measure-Command { $DecryptedData = Decrypt-Data -Key $Key -TextInput $EncryptedData; }

#Show decrypted data
$DecryptedData
