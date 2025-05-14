### **Problemstellung**

- **"It works on my machine!"** – Anwendungen laufen lokal, aber nicht auf dem Server wegen unterschiedlicher Systeme und Konfigurationen.
    
- **Unterschiedliche Entwicklungsumgebungen** – Entwickler nutzen verschiedene Systeme, was zu Problemen führen kann.
    
- **Komplexe Abhängigkeiten** – Bibliotheks- oder Paketkonflikte treten häufig auf.
    
- **Skalierbarkeit** – Docker erleichtert horizontale Skalierung durch das einfache Starten mehrerer Container.
    

---

###  **Was ist Docker?**

- Eine Plattform zur **Containerisierung von Anwendungen**.
    
- Anwendungen laufen mit allen Abhängigkeiten in isolierten Containern.
    
- Docker funktioniert **plattforunabhängig** (lokal, Server, Cloud).
    
- Bietet **Isolation** zwischen Anwendungen – weniger Konflikte, höhere Stabilität.
    

---

###  **Einsatzgebiete**

- **Softwareentwicklung & Testing** – identische Umgebungen für alle.
    
- **Cloud-Umgebungen** (AWS, Azure, Google Cloud) – schnelle und portable Bereitstellung.
    
- **Plattformübergreifendes Deployment** – Container funktionieren überall gleich.
    

---

###  **Docker Architektur**

- **Docker Engine** führt Container aus.
    
- **Docker Daemon** verwaltet Prozesse im Hintergrund.
    
- **Docker CLI** (Command Line Interface) für die Bedienung.
    
- **Images** sind Baupläne für Container, erstellt mit einem **Dockerfile**.
    
- **Container** sind laufende Instanzen eines Images.
    
- **Registries** wie Docker Hub speichern Images (öffentlich oder privat).
    

---

###  **Netzwerkmodi**

1. **Bridge-Modus** – Standard, eigene IPs, virtuelles Netzwerk.
    
2. **Host-Modus** – direkter Netzwerkzugriff, weniger isoliert.
    
3. **Overlay-Modus** – für verteilte Systeme über mehrere Hosts hinweg.
    

---

###  **Docker vs. Virtuelle Maschinen**

- **Virtuelle Maschinen**: eigenes Betriebssystem, hoher Ressourcenverbrauch.
    
- **Docker**: nutzt Host-OS, schneller Start, weniger Overhead.
    
- **Fazit**: Docker ist effizienter für leichtgewichtige, skalierbare Anwendungen; VMs bieten stärkere Isolation.
    

---

###  **Wichtige Docker-Befehle**

|Befehl|Beschreibung|
|---|---|
|`docker run <image>`|Startet Container|
|`docker pull <image>`|Lädt Image herunter|
|`docker ps / ps -a`|Zeigt laufende / alle Container|
|`docker stop <container>`|Stoppt Container|
|`docker rm <container>`|Entfernt gestoppten Container|
|`docker images / rmi`|Listet / entfernt Images|
|`docker logs [-f]`|Zeigt (Live-)Logs eines Containers|

---

```d
version: "3.8"

services:
  web:
    image: nginx:latest
    ports:
      - "80:80"
    volumes:
      - ./nginx.conf:/etc/nginx/nginx.conf:ro
      - ./frontend:/usr/share/nginx/html:ro
    depends_on:
      - app

  app:
    build: ./app
    ports:
      - "3000:3000"
    volumes:
      - ./app:/usr/src/app
    environment:
      - NODE_ENV=development
    command: ["npm", "start"]

```
## Fragen

- Wie funktioniert die Docker-Architektur (Docker Engine, Daemon, CLI)?

- Was ist ein Dockerfile und wofür wird es verwendet?

- Was passiert, wenn man den Befehl `docker run <image>` ausführt?

- Wie kommunizieren Container untereinander?

- Was ist der Hauptunterschied zwischen Docker-Containern und VMs?
    
- Warum gelten Docker-Container als ressourcenschonender?
    
- Wann wäre der Einsatz einer virtuellen Maschine sinnvoller als ein Container?


Port mapping - Containerization of Databases


Winkler Präsis
Date