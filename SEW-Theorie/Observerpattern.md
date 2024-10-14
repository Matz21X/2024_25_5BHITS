# Observerpattern
#SEW 

Jedes Objekt hat einen Zustand indem es sich aktuell befindet. Bei Veränderungen an diesem Zustand kann es vorkommen dass es andere Objekte gibt die von diesem einem Objekt abhängig sind und von solchen Zustandsänderungen benachrichtigt werden möchten. Man bezeichnet diese abhängigen Objekte als Observer und das zu beobachtende Objekt als Subjekt.  

Das gewünschte Verhalten des Observer-patterns kann man folgender Massen erreichen:  
- Man kann Observer bei einem Subjekt "anmelden". 
- Jeder Observer hat eine "Update" - Methode, in der der eigene Zustand aktualisiert wird.  
- Ändert sich der Zustand eines Subjekts, werden die Observer benachrichtigt (english: to notify), indem deren update-Methode aufgerufen wird.

(Göd Zeichnung, Sorry)
![[Observerpattern-20241014141853794.webp]]