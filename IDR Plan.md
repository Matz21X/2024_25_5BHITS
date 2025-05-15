# **Planung eines IR Prozesses: Datenbank mit Kunden-IBANs wird abgegriffen**

#### **1. Rollen und Verantwortlichkeiten**

- **Incident Response Manager**: Gesamtverantwortung für den Vorfall, Koordination aller Maßnahmen 
	- (Einer aus einem Pool von 4 ausgebildeten Personen)
- **IT-Security-Team**: Untersuchung des Angriffs, Identifikation der Schwachstellen, Einleitung von Sofortmaßnahmen 
	- (7 Personen)
- **Datenbank-Administratoren**: Analyse der betroffenen Datenbank, Einspielen von Backups, Sicherstellung der Integrität 
	- (3 Personen)
- **Legal & Compliance**: Bewertung der rechtlichen Konsequenzen, Meldung an Behörden (z. B. Datenschutzbehörden gemäß DSGVO) 
	- (2 Personen)
- **Kommunikator**: Externe und interne Kommunikation, Vermeidung von Reputationsschäden 
	- (1 Person aus einem Pool von 4)
- **Customer Support**: Information betroffener Kunden, Bearbeitung von Anfragen und Beschwerden 
	- (Bestehendes Customer Support Team)

---

#### **2. Eingesetzte Software – welche und für was**

- **SIEM (Security Information and Event Management)**: Überwachung und Analyse von verdächtigen Aktivitäten
- **IDS/IPS (Intrusion Detection/Prevention System)**: Erkennung und Verhinderung weiterer Angriffe
- **Forensik-Tools (z. B. Autopsy, Volatility, Wireshark)**: Analyse von Angriffsspuren
- **Backup- und Recovery-Systeme**: Wiederherstellung der betroffenen Datenbank
- **Verschlüsselungstools**: Absicherung sensibler Daten vor weiterem Missbrauch

---

#### **3. Business-Continuity-Plan – was tun, um Betrieb aufrecht zu erhalten**

- **Sofortige Trennung der betroffenen Datenbank vom Netzwerk** zur Eindämmung des Schadens
- **Umstellung auf eine sichere Backup-Datenbank**, falls möglich
- **Einschränkung des Zugriffs auf Zahlungstransaktionen**, um Missbrauch zu verhindern
- **Schnelle Sicherheitsupdates und Patches** zur Schließung der Schwachstellen
- **Ersatzkanäle für Zahlungen (z. B. manuelle Überprüfung bestimmter Transaktionen)**

---

#### **4. Detaillierter Ablauf Plan**

1. **Erkennung & Analyse**
    - Meldung des Vorfalls durch Monitoring-Systeme oder Dritte
    - Erste Bewertung der betroffenen Systeme und Daten
    - Identifikation der Einfallstore (z. B. unsichere API, gestohlene Zugangsdaten)
2. **Eindämmung**
    - Sperrung der kompromittierten Datenbank
    - Änderung aller Zugangsdaten und API-Schlüssel
    - Prüfung auf weitere Kompromittierungen
3. **Beseitigung**
    - Schließen der Sicherheitslücke (Patchen, Konfigurationsänderungen)
    - Forensische Untersuchung zur Identifikation des Angriffswegs
    - Wiederherstellung aus einem sicheren Backup
4. **Erholung**
    - Testen der abgesicherten Systeme
    - Wiederanbindung der Datenbank an den Betrieb
    - Verstärkte Überwachung der Systeme
5. **Nachbereitung**
    - Dokumentation des Vorfalls für interne und regulatorische Zwecke
    - Verbesserung der Sicherheitsmaßnahmen zur Vermeidung zukünftiger Vorfälle
    - Schulungen für Mitarbeiter zur Sensibilisierung

---

#### **5. Kommunikations-Plan – wann wird wer wie informiert**

- **Interne Kommunikation (sofort)**: IT-Security-Team, Management, Legal-Abteilung
- **Behördliche Meldung (innerhalb von 72h nach DSGVO)**: Datenschutzbehörde, ggf. Finanzaufsichtsbehörde
- **Kundenbenachrichtigung (nach Absprache mit Legal & PR)**:
    - Betroffene Kunden per E-Mail/Brief informieren
    - Erklärung über den Vorfall und empfohlene Maßnahmen (z. B. Überwachung des Kontos)
- **Öffentliche Kommunikation (falls notwendig)**:
    - Pressestatement mit faktenbasierten Informationen, um Reputationsschäden zu minimieren
    - Social-Media-Monitoring, um auf Kundenanfragen zu reagieren
