# Steckbrief: Simple Mail Transfer Protocol (SMTP)
#NWT

## Allgemeines
- **Name:** Simple Mail Transfer Protocol (SMTP)
- **Definition:** SMTP ist ein Protokoll für den Austausch von E-Mails zwischen E-Mail-Servern und für das Senden von E-Mails von einem Client an einen Server.
- **Standardisiert durch:** IETF (Internet Engineering Task Force)
- **Erstveröffentlichung:** 1982 (RFC 821), aktuell RFC 5321 (2008)
- **Portnummern:** 
  - Standard: **25** (für Server-zu-Server Kommunikation)
  - Sicher: **587** (für Client-zu-Server Kommunikation mit STARTTLS)
  - Alternative: **465** (deprecated, jedoch noch verbreitet für SSL/TLS)

## Eigenschaften
- **Verbindungsorientiert:** SMTP verwendet das Transmission Control Protocol (TCP) für eine zuverlässige Datenübertragung.
- **Zustandslos:** Jede SMTP-Transaktion ist unabhängig von anderen.
- **Push-Protokoll:** Daten werden aktiv an den Empfänger übermittelt.
- **Erweiterungen:** Unterstützung durch ESMTP (Extended SMTP) für moderne Funktionen wie Authentifizierung und Verschlüsselung.

## Hauptfunktionen
1. **E-Mail-Versand:** Übertragung von E-Mails vom Absender an den zuständigen Mailserver.
2. **Weiterleitung:** Routing von E-Mails zwischen verschiedenen Mailservern.
3. **Fehlerbenachrichtigungen:** Zurückmeldung an den Absender, wenn die Zustellung fehlschlägt.

## Wichtige SMTP-Kommandos 
- `HELO`: Stellt eine Verbindung zu einem SMTP-Server her. 
	- `ESMTP` - EHLO
- `MAIL FROM`: Gibt die E-Mail-Adresse des Absenders an. 
- `RCPT TO`: Gibt die E-Mail-Adresse des Empfängers an. 
- `DATA`: Beginnt den E-Mail-Inhalt. 
- `QUIT`: Beendet die Verbindung zum SMTP-Server.

## Kommunikationsablauf
1. **Verbindungsaufbau:** SMTP-Client initiiert Verbindung mit einem SMTP-Server über TCP.
2. **Handshake:** Server sendet Begrüßung, Client stellt sich vor (HELO/EHLO).
3. **E-Mail-Übermittlung:**
   - Absenderadresse (MAIL FROM)
   - Empfängeradresse(n) (RCPT TO)
   - E-Mail-Inhalt (DATA)
4. **Abschluss:** Verbindung wird geschlossen.

## Vorteile
- **Einfachheit:** Minimalistisches Design für grundlegende E-Mail-Kommunikation.
- **Weite Verbreitung:** Standardprotokoll für den E-Mail-Verkehr.
- **Erweiterbarkeit:** ESMTP ermöglicht zusätzliche Features wie Authentifizierung.

## Nachteile
- **Keine Sicherheit im Standard:** Datenübertragung erfolgt im Klartext (ohne STARTTLS).
- **Spam-Anfälligkeit:** Keine integrierten Mechanismen zur Verhinderung von Spam (erfordert zusätzliche Lösungen wie SPF/DKIM/DMARC).
- **Nur für Versand:** SMTP ist nicht für den Empfang von E-Mails verantwortlich (dies übernehmen IMAP/POP3).

## Alternativen/Ergänzungen
- **IMAP (Internet Message Access Protocol):** Für den Zugriff auf empfangene E-Mails.
- **POP3 (Post Office Protocol):** Herunterladen von E-Mails.
- **SPF/DKIM/DMARC:** Zur Authentifizierung und Schutz vor E-Mail-Spoofing.

## Beispiel einer SMTP-Sitzung

```
C: HELO mail.example.com
S: 250 Hello mail.example.com
C: MAIL FROM:<sender@example.com>
S: 250 OK
C: RCPT TO:<receiver@example.com>
S: 250 OK
C: DATA
S: 354 Start mail input; end with <CRLF>.<CRLF>
C: Subject: Test Email
C: This is a test message.
C: .
S: 250 OK: Message accepted
C: QUIT
S: 221 Bye
```
