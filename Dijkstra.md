# Graphen kürzester Pfad Dijkstra
#SEW 

>Ein kürzester Pfad ist ein Pfad zwischen zwei unterschiedlichen Knoten eines Graphen, der die minimale Länge bezüglich der Kantengewichtsfunktion hat.
>Der Algorithmus von Dijkstra löst das Problem der kürzesten Pfade in dem er einen kürzesten Pfad zwischen dem gegebenen Startknoten und einem anderen Knoten in einem kantengewichteten Graphen berechnet.

**Pseudocode:**
```
function Dijkstra (V, E, start)   // Knoten, Kanten, Startknoten
	Für alle v ∈ V
		Wenn v == start
			distanz [v] := 0   // Abstand zum Startknoten
		andernfalls
			distanz [v] := ∞

```