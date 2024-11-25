# Graphen kürzester Pfad Dijkstra
#SEW 

>Ein kürzester Pfad ist ein Pfad zwischen zwei unterschiedlichen Knoten eines Graphen, der die minimale Länge bezüglich der Kantengewichtsfunktion hat.
>Der Algorithmus von Dijkstra löst das Problem der kürzesten Pfade in dem er einen kürzesten Pfad zwischen dem gegebenen Startknoten und einem anderen Knoten in einem kantengewichteten Graphen berechnet.

**Pseudocode:**
```
function Dijkstra (V, E, start)   // Knoten, Kanten, Startknoten
	Für alle v ∈ V
		Wenn v == start
			distanz [v] := 0      // Abstand zum Startknoten
		andernfalls
			distanz [v] := ∞ 
		vorgänger [v] := null
		Q.einfügen(v)             // Menge der zu bearbeitenden Knoten  

	Solange Q nicht leer ist
		u := kleinstes aus Q      // Knoten mit geringster Distanz
		Q.entferne(u)
		Für alle (u, v) ∈ E & ∈ Q                              // Neue min. Distanz ?
			Wenn distanz [u] + gewicht(u ,v) < distanz [v]
				distanz [v] := distanz [u] + gewicht(u, v)
				vorgänger [v] := u                             // Ja, Aktualisieren Distanz und
				                                               // Vorgänger
				                                       
```

**Bemerkung:**
Pseudocode ist eine detaillierte und dennoch lesbare Beschreibung dessen was ein Computerprogramm oder ein Algorithmus machen soll. Pseudocode wird in einer formal gestalteten natürlichen Sprache und nicht in einer Programmiersprache ausgedrückt