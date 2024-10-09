# Übungen
#ITSI 

## Hash Funktionen

1. MD5 `9acfecec51b9d408197f7bd420896b33` 
2. SHA256 `ee8b51987a178f62a5ffb0f595492ea46c77fe8bfb6e4363956046dd410f4d15`

Ein Zeichen verändert:

1. MD5 `5bd1a7b0ffe1d92cac1c8b124fd1fec6`
2. SHA256 `4184b41e80cb651a2904795b1d44b3b7ec4c0016222a06203cbc9ca8a9620e4d`

```
The hex string is not the same
The binary data is not the same

MD5 hash for binary #1: 008ee33a9d58b51cfeb425b0959121c9 
MD5 hash for binary #2: 008ee33a9d58b51cfeb425b0959121c9 
The MD5 hashes are the same 

SHA256 hash for binary #1: 54bcb9a4fda31e4f254303e3959acd5e420ad18a80949d56a3000c3716fbd1a0 
SHA256 hash for binary #2: 90774a6455a2bdb7d106e533923ecbefe81392ca55bed0ce81cfab2c1a7f0afe 
The SHA256 hashes are not the **same**
```


## Verschlüsselung

- Messung der Laufzeit der Verschlüsselungs- / Entschlüsselungs- Operationen (symmetrisch/asymmetrisch)
- Unterschied Verschlüsselung (z.B. AES-256) und Encodierung (z.B. Base64)

![[asymmetric.ps1]]

![[symmetric.ps1]]

Symmetrisch: 0.5712 ms
Asymmetrisch: 6.3404 ms

## Firewall

• Paketfilter Regeln beschreiben: 
	• Zugriff aus dem Internet auf die IP 192.168.1.3 auf Port tcp/80 und tcp/443 erlauben 
	• Zugriff aus dem Internet auf die IP 192.168.1.10 auf Port tcp/22 erlauben 
	• Zugriff von der IP-Range 192.168.3.0/24 auf die IP 192.168.1.10 Port tcp/22 erlauben 
	• Alle anderen Zugriffen

```
Any Any 192.168.1.3/32 tcp/80,tcp/443 Allow
Any Any 192.168.1.10/32 tcp/22 Allow 
192.168.3.0/24 Any 192.168.1.10/32 tcp/22 Allow
Any Any Any Any Deny
```