# IDRAC
#NWT 

```
/interface bridge
add name=br
/interface wifi security
add authentication-types=wpa2-psk disabled=no name=SecHTLIoT
/interface wifi
set [ find default-name=wifi1 ] configuration.country=Austria .manager=local .mode=station-bridge .ssid=HTLIoT disabled=no security=SecHTLIoT
/ip pool
add name=pool ranges=192.168.10.10-192.168.10.100
/ip dhcp-server
add address-pool=pool interface=br name=dhcp1
/interface bridge port
add bridge=br interface=ether1
add bridge=br interface=ether2
add bridge=br interface=ether3
add bridge=br interface=ether4
add bridge=br interface=ether5
/ip address
add address=192.168.10.1/24 interface=br network=192.168.10.0
/ip dhcp-client
add interface=wifi1
/ip dhcp-server network
add address=192.168.10.0/24 dns-server=192.168.10.1 gateway=192.168.10.1
/ip dns
set allow-remote-requests=yes
/ip firewall nat
add action=masquerade chain=srcnat out-interface=wifi1
add action=dst-nat chain=dstnat dst-port=8443 in-interface=wifi1 protocol=tcp to-addresses=192.168.10.98 to-ports=443
/system clock
set time-zone-name=Europe/Vienna
/system identity
set name=hapac2-17
/system note
set show-at-login=no
/system ntp client
set enabled=yes
```