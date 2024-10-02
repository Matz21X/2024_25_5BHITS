SWITCH:  

/interface/bridge add name=br vlan-filtering=yes

/interface/vlan add interface=br name=VL10 vlan-id=10 comment=NetworkManagement  
/interface/vlan add interface=br name=VL20 vlan-id=20 comment=Mitarbeiter  
/interface/vlan add interface=br name=VL30 vlan-id=30 comment=Gaeste  
/interface/vlan add interface=br name=VL40 vlan-id=40 comment=NetworkAdministrators  
/interface/vlan add interface=br name=VL50 vlan-id=50 comment=Quarantine  
/interface/vlan add interface=br name=VL60 vlan-id=60 comment=DMZ

/interface/bridge/port add bridge=br interface=ether1  
/interface/bridge/port add bridge=br interface=ether2  
/interface/bridge/port add bridge=br interface=ether3  
/interface/bridge/port add bridge=br interface=ether4 pvid=20  
/interface/bridge/port add bridge=br interface=ether5 pvid=20

/interface/bridge/vlan add bridge=br tagged=br,ether1,ether2 vlan-ids=10  
/interface/bridge/vlan add bridge=br tagged=br,ether1 untagged=ether4,ether5 vlan-ids=20  
  
_______________________________________________________________________________  
  
ROUTER1:  
  
/system/identity/set name=Router1  
  
/interface/bridge/add name=br vlan-filtering=yes  
  
/interface/bridge/port/add bridge=br interface=ether1  
/interface/bridge/port/add bridge=br interface=ether2 pvid=20  
/interface/bridge/port/add bridge=br interface=ether3 pvid=20  
/interface/bridge/port/add bridge=br interface=ether4 pvid=20  
/interface/bridge/port/add bridge=br interface=ether5 pvid=20

  
/interface/bridge/vlan/add bridge=br vlan-ids=10 tagged=br,ether1  
/interface/bridge/vlan/add bridge=br vlan-ids=20 tagged=br,ether1  
/interface/bridge/vlan/add bridge=br vlan-ids=30 tagged=br,ether1  
/interface/bridge/vlan/add bridge=br vlan-ids=40 tagged=br,ether1  
/interface/bridge/vlan/add bridge=br vlan-ids=50 tagged=br,ether1  
/interface/bridge/vlan/add bridge=br vlan-ids=60 tagged=br,ether1  
  
for vlanID from=10 to=60 step=10 do={  
/interface/vlan/add name=("VL".$vlanID) interface=br vlan-id=$vlanID

/ip/address/add interface=("VL".$vlanID) address=("192.168.".$vlanID.".1/24")

/ip/pool/add name=("POOL".$vlanID) ranges=("192.168.".$vlanID.".10-192.168.".$vlanID.".250")

/ip/dhcp-server/add name=("DHCP".$vlanID) address-pool=("POOL".$vlanID) disabled=no interface=("VL".$vlanID)

/ip/dhcp-server/network/add address=("192.168.".$vlanID.".0/24") gateway=("192.168.".$vlanID.".1") dns-server=192.168.60.1  
}  
  
/ip/dns/static/add name=htl-hl.it address=10.0.10.37  
  
/ip/dns/set allow-remote-requests=yes  
  
/ip/firewall/nat/add chain=srcnat action=masquerade out-interface=wifi1