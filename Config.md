/interface/bridge add name=br vlan-filtering=yes

/interface/bridge/port/add bridge=br interface=ether1  
/interface/bridge/port/add bridge=br interface=ether2 
/interface/bridge/port/add bridge=br interface=ether3 
/interface/bridge/port/add bridge=br interface=ether4 
/interface/bridge/port/add bridge=br interface=ether5 

/interface/bridge/vlan/add bridge=br vlan-ids=10 tagged=br,ether1  
/interface/bridge/vlan/add bridge=br vlan-ids=20 tagged=br,ether1  
/interface/bridge/vlan/add bridge=br vlan-ids=30 tagged=br,ether1  
/interface/bridge/vlan/add bridge=br vlan-ids=40 tagged=br,ether1  
/interface/bridge/vlan/add bridge=br vlan-ids=50 tagged=br,ether1  
/interface/bridge/vlan/add bridge=br vlan-ids=60 tagged=br,ether1