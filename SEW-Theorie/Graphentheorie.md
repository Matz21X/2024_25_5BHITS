# Graphen
#SEW 

> Ein Graph ist eine Struktur, die aus einer Menge von Knoten (Nodes, Vertices) und einer Menge von Kanten (Edges) besteht:

$$V= [ v1, v2, ..., vn ]$$
$$E= [ e1, e2, ..., en ]$$
z.B:


![[Graph.svg]]

**Mittels Graphen kann z.B. beschrieben werden:**
- Straßenverbindungen zwischen Städten
- Netzwerktopologien
- Rohrleitungssysteme

**Anwendungsbereiche:**
- Routenplanungssysteme (Google Maps)
- Kürzeste Rundreise (Problem des Handlungsreisenden)
- Netzwerkrouting 

## Mögliche Eigenschaften von Graphen

1. **Ungerichtet / Gerichtet Graph**
	- Endet die Kante zwischen zwei Knoten mit einem Pfeil so spricht man von einem gerichteten Graphen ansonsten einem ungerichteten Graphen.
2. **Gewichtet / Ungewichtet**
	- Ein gewichteter Graph ist ein Graph dessen Kanten ein Gewicht (numerischer Wert) zugewiesen ist. 

![[GraphGewichtet.svg]]


## Zyklischer Graph

Ein Zyklus ist ein Kantenzug mit unterschiedlichen Kanten bei dem Start- und Endknoten gleich sind. Ein zyklischer Graph ist ein Graph mit mindestens einem Zyklus.

## Zusammenhängender Graph

Ein Graph heißt zusammenhängend, wenn es von jedem Knoten einen Weg zu jedem anderen Knoten gibt.