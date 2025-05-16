# Docker

### Was ist Docker?

Docker ist eine Plattform zur **Containerisierung** von Anwendungen. Das bedeutet: Docker ermöglicht es, Software inklusive aller Abhängigkeiten, Bibliotheken und Konfigurationen in sogenannten **Containern** auszuführen – isolierten, portablen Einheiten, die auf jedem System mit Docker gleich laufen.

### Wie Funktioniert Docker?

Docker basiert auf **Linux-Containern**, die eine **prozessbasierte Virtualisierung** bereitstellen. Anders als virtuelle Maschinen (VMs) virtualisieren Container **nicht das gesamte Betriebssystem**, sondern nutzen den **Kernel des Host-Betriebssystems** und isolieren Prozesse durch **Namespaces** und **Control Groups (cgroups)**.

![[Docker Zusammenfassung-20250515164733929.webp|1023]]

**Cgroups:**
- Definition von Quotas auf Prozesse (auf CPU- und RAM-Ebene)

**Namespaces:**
- Prozesse isolieren (denkt er ist der einzige Prozess)
- Netzwerkinterface zu Prozessen zuordnen
- virtuelles Dateisystem für Prozesse


## Images

Ein **Docker Image** (ein ISO-File sozusagen) ist eine **unveränderliche Vorlage** (read-only), aus der ein oder mehrere **Container** erstellt werden können. Man kann es sich als **Schnappschuss eines Dateisystems** vorstellen – inklusive aller benötigten Dateien, Konfigurationen, Abhängigkeiten und der Anwendung selbst.

Ein Docker Image besteht aus mehreren **Layern**, die übereinandergestapelt sind:

```
[ Layer 4 ]  ⟵ Anwendungscode (z. B. Python-Skript)
[ Layer 3 ]  ⟵ zusätzliche Abhängigkeiten (z. B. pip install ...)
[ Layer 2 ]  ⟵ Systempakete (z. B. apt install ...)
[ Layer 1 ]  ⟵ Basis-Image (z. B. ubuntu:20.04)
```

Jeder dieser Layer ist **read-only**. Erst beim Starten eines Containers wird **ein schreibbarer Layer** darübergelegt.
Um Images herunterzuladen wird der Befehl `docker pull <imagename>` verwendet


## Dockerfile

Um ein Image zu erstellen benötigt man zuerst ein Dockerfile. Dieses könnte Beispielhaft so aussehen:

```
FROM python:3.11-slim           # Layer 1: Basisimage
WORKDIR /app                    # Layer 2: Arbeitsverzeichnis
COPY requirements.txt .         # Layer 3: Dateien kopieren
RUN pip install -r requirements.txt  # Layer 4: Python-Abhängigkeiten
COPY . .                        # Layer 5: Anwendungscode
CMD ["python", "main.py"]       # Startbefehl für Container
```

Mit dem Befehl `docker build -t imagename .` wird dann das Docker Image erstellt. 

## Docker Container

Ein Docker Container ist die laufende Instanz eines Docker-Images. 
