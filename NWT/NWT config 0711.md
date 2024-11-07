/interface bridge
add name=br vlan-filtering=yes
/interface vlan
add interface=br name=VL10 vlan-id=10
add interface=br name=VL20 vlan-id=20
add interface=br name=VL30 vlan-id=30
add interface=br name=VL40 vlan-id=40
add interface=br name=VL50 vlan-id=50
add interface=br name=VL60 vlan-id=60
/interface wifi datapath
add name=dpGaeste
/interface wifi security
add authentication-types=wpa2-psk disabled=no name=SecHTLIoT
add authentication-types=wpa2-psk name=SecGaeste
/interface wifi
set [ find default-name=wifi1 ] configuration.country=Austria .manager=local \
    .mode=station-bridge .ssid=HTLIoT disabled=no security=SecHTLIoT
/ip pool
add name=POOL10 ranges=192.168.10.10-192.168.10.250
add name=POOL20 ranges=192.168.20.10-192.168.20.250
add name=POOL30 ranges=192.168.30.10-192.168.30.250
add name=POOL40 ranges=192.168.40.10-192.168.40.250
add name=POOL50 ranges=192.168.50.10-192.168.50.250
add name=POOL60 ranges=192.168.60.10-192.168.60.250
/ip dhcp-server
add address-pool=POOL10 interface=VL10 name=DHCP10
add address-pool=POOL20 interface=VL20 name=DHCP20
add address-pool=POOL30 interface=VL30 name=DHCP30
add address-pool=POOL40 interface=VL40 name=DHCP40
add address-pool=POOL50 interface=VL50 name=DHCP50
add address-pool=POOL60 interface=VL60 name=DHCP60
/interface bridge port
add bridge=br interface=ether1
add bridge=br interface=ether2 pvid=20
add bridge=br interface=ether3 pvid=20
add bridge=br interface=ether4 pvid=20
add bridge=br interface=ether5 pvid=20
/interface bridge vlan
add bridge=br tagged=br,ether1 vlan-ids=10
add bridge=br tagged=br,ether1 vlan-ids=20
add bridge=br tagged=br,ether1 vlan-ids=30
add bridge=br tagged=br,ether1 vlan-ids=40
add bridge=br tagged=br,ether1 vlan-ids=50
add bridge=br tagged=br,ether1 vlan-ids=60
/interface wifi capsman
set enabled=yes
/ip address
add address=192.168.10.1/24 interface=VL10 network=192.168.10.0
add address=192.168.20.1/24 interface=VL20 network=192.168.20.0
add address=192.168.30.1/24 interface=VL30 network=192.168.30.0
add address=192.168.40.1/24 interface=VL40 network=192.168.40.0
add address=192.168.50.1/24 interface=VL50 network=192.168.50.0
add address=192.168.60.1/24 interface=VL60 network=192.168.60.0
/ip dhcp-client
add interface=wifi1
/ip dhcp-server network
add address=192.168.10.0/24 dns-server=192.168.60.1 gateway=192.168.10.1
add address=192.168.20.0/24 dns-server=192.168.60.1 gateway=192.168.20.1
add address=192.168.30.0/24 dns-server=192.168.60.1 gateway=192.168.30.1
add address=192.168.40.0/24 dns-server=192.168.60.1 gateway=192.168.40.1
add address=192.168.50.0/24 dns-server=192.168.60.1 gateway=192.168.50.1
add address=192.168.60.0/24 dns-server=192.168.60.1 gateway=192.168.60.1
/ip dns
set allow-remote-requests=yes
/ip dns static
add address=10.0.10.37 name=htl-hl.it
/ip firewall nat
add action=masquerade chain=srcnat out-interface=wifi1
/system clock
set time-zone-name=Europe/Vienna
/system identity
set name=Router1
/system note
set show-at-login=no
/system ntp client
set enabled=ye