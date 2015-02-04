#Oracle Einrichten

Liferay arbeitet in der Standard-Konfiguration mit einer [H2-SQL](http://en.wikipedia.org/wiki/H2_(DBMS)) Datenbank. Da es sich dabei um eine *In-Memory* Datenbank handelt, können wir im laufenden Betrieb nicht mit anderen Programmen darauf zugreifen, um die aktuellen Daten in den Tabellen zu betrachten.

Deshalb wollen wir Liferay mit einer *richtigen* Datenbank verbinden. Als Konzernstandard wurde Oracle gewählt.

##Oracle XE installieren

Wir verwenden im Workshop die Kostenlose Oracle XE Version. Die Installationsdateien werden im Workshop verteilt und umfassen:
- OracleXE112_Win64.zip
- Install_Info.docx (Detailierte Installationsanleitung)

###Kurzanleitung
- Zip entpacken und `Disk1\setup.exe` ausführen

![Screenshot Oracle Installer 1](https://github.com/bglu/lrWorkshop/blob/master/Dokumentation/img/oracle-installer01.png)

"Abbildung 1"

![Screenshot Oracle Installer 2](https://github.com/bglu/lrWorkshop/blob/master/Dokumentation/img/oracle-installer02.png) 

"Abbildung 2"

**Installationsort**

Wir empfehlen, den Standard-Wert zu übernehmen: `c:\oraclexe\`

![Screenshot Oracle Installer 3](https://github.com/bglu/lrWorkshop/blob/master/Dokumentation/img/oracle-installer03.png) 

"Abbildung 3"

**Achtung!!!**

**Dieses Passwort unbedingt merken!** Es ist das Systempasswort.

![Screenshot Oracle Installer 4](https://github.com/bglu/lrWorkshop/blob/master/Dokumentation/img/oracle-installer04.png) 

"Abbildung 4"

![Screenshot Oracle Installer 5](https://github.com/bglu/lrWorkshop/blob/master/Dokumentation/img/oracle-installer05.png) 

"Abbildung 5"

![Screenshot Oracle Installer 6](https://github.com/bglu/lrWorkshop/blob/master/Dokumentation/img/oracle-installer06.png) 

"Abbildung 6"


##Eigenen Tablespace und Benutzer für Liferay anlegen

Wir könnten Oracle schon mit dem *System-Benutzer* verwenden. Dabei hätten wir jedoch das Problem, dass unsere **Liferay Tabellen nicht von den System Tabellen getrennt** wären.

Jeder Benutzer hat in Oracle einen *default Tablespace*, auf dem er beim Verbinden arbeitet. Deshalb können wir nun einfach einen eigenen Benutzer mit eigenem Tablespace für Liferay anlegen.

###Tablespace anlegen

Zuerst öffnen wir ein Oracle SQL Terminal. Dieses finden wir im Starmenü:

![Screenshot Run SQL Command](https://github.com/bglu/lrWorkshop/blob/master/Dokumentation/img/Oracle_RunSql.png)

"Abbildung 7"

Nun verbinden wir uns mit unserem System-Benutzer:

    SQL> connect
    user-name: system
    password: [system passort siehe Abbildung 4]

Jetzt können wir den Tablespace mit folgendem SQL Befehl anlegen:

    SQL> CREATE BIGFILE TABLESPACE lportal DATAFILE 'lportal.dat' SIZE 5000M AUTOEXTEND ON;

Dies erzeugt einen 5GB Tablespace in der Datei `C:\oraclexe\app\oracle\product\11.2.0\server\database\lportal.dat`.

###Benutzer anlegen
Für diesen Tablespace erstellen wir noch einen Benutzer. Liferay hat eine Konvention, dass für den Datenbank-User der Name *sa* verwendet wird. Dies ist kein Muss, aber wir übernehmen das mal so.

    SQL> CREATE USER sa IDENTIFIED BY [password] DEFAULT TABLESPACE lportal;

Dabei ist [password] durch ein einfaches Passwort zu ersetzen.

Dieser Benutzer hat jedoch noch keine Berechtigungen. Er darf sich aktuell noch nicht einmal mit der Datenbank verbinden. Deshalb müssen wir ihm diese Rechte geben. Der einfach halt halber geben wir ihm *alle* Rechte. <strong><font color="#FF0000">Bitte niemals auf einer produktiven Maschine so umsetzen!!!</font></strong>

    SQL> grant connect to sa;
    SQL> grant all privileges to sa;





##Liferay mit Oracle Verbinden

###Obdbc kopieren
Damit Liferay mit Oracle (anstelle der H2-DB) arbeiten kann - (Liferay kann nur mit einer Datenbank pro Portal arbeiten, nicht mit mehreren für verschiedene Portlets), wird eine `ojdbc.jar` von Oracle benötigt.

Diese wird bei der Oracle XE installation mit bereit gestellt unter: `C:\oraclexe\app\oracle\product\11.2.0\server\jdbc\lib\ojdbc6.jar`

**Bitte darauf achten, die Version mit der 6 zu nehmen.** Dieses ist zu dem jdk 6 kombatible, mit dem wir mit dem von Liferay mitgelieferten Tomcat arbeiten.

Wir kopieren (**nicht verschieben/auschneiden**) diese Bibliothek nach `C:\liferay\tomcat\lib\ext`.


###Ojdbc in Server Classpath eintragen
In der Eclipse öffnen wir nun die Servereinstellungen, indem wir doppelt auf dem Server im linken unteren Bereich klicken. Dann in den Einstellungen auf den Link `Open launch configuration` klicken:

![Screenshot - Eclipse - Open launch configuration](https://github.com/bglu/lrWorkshop/blob/master/Dokumentation/img/eclipse-launch-config.png)

"Abbildung 8"

Hier nun im Reiter *Classpath* die Bibliothek mit *Add External JARs...* hinzufügen. Anschließend mit Stg+S Speichern.

![Screenshot - Eclipse - Server Classpath](https://github.com/bglu/lrWorkshop/blob/master/Dokumentation/img/eclipse-server-classpath.png)


###Portal-ext.properties erstellen

Abschließend müssen wir Liferay noch mitteilen, dass es sich beim Server-Start mit der Oracle Datenbank verbinden soll. Dazu erstellen wir die Datei `C:\liferay\portal\portal-ext.properties`.

Diese wird beim starten des Servers automatisch geladen und wird genutzt, um proerties zu überschreiben (die Standardwerte).

In der Datei tragen wir folgende Zeilen ein:

    dbc.default.driverClassName=oracle.jdbc.driver.OracleDriver
    jdbc.default.url=jdbc:oracle:thin:@localhost:1521:xe
    jdbc.default.username=sa
    jdbc.default.password=[passwort von sa - siehe oben]

Wenn wir den Server nun starten, sollte Liferay im Log melden, dass es sich mit der Oracle Datenbank verbunden hat:

    Loading file:/C:/Liferay/portal/portal-ext.properties
    Loading file:/C:/Liferay/portal/portal-setup-wizard.properties
    Loading file:/C:/Liferay/portal/portal-ide.properties
    Feb 04, 2015 7:05:38 AM org.apache.catalina.core.ApplicationContext log
    INFORMATION: Initializing Spring root WebApplicationContext
    07:05:44,649 INFO  [localhost-startStop-1][DialectDetector:71] Determine dialect for Oracle 11
    07:05:44,695 INFO  [localhost-startStop-1][DialectDetector:136] Found dialect org.hibernate.dialect.Oracle10gDialect
    Starting Liferay Portal Community Edition 6.2 CE GA2 (Newton / Build 6201 / March 20, 2014)



    
##SQuirrel SQL

SQuirrel SQL ist ein SQL Client, mit dem man Anfragen an eine Datenbank senden kann. Wir verwenden sie bei der Liferay-Entwicklung, um den Ablauf des Datentransfers unserer Anwendungen verfolgen zu können. Konkret wollen wir folgende Punkte prüfen können:

- Werden von der Anwendung alle Daten (richtig) gelesen?
- Werden Daten (richtig) geschrieben?
- Welche Daten sind aktuell in den Tabellen?
 
Das Programm wird im Workshop verteilt. Man kann es einfach in einen beliebigen Ordner auf der Festplatte kopieren und dort mit der `squirrel-sql.bat` starten.

Dort fügen wir einen neuen *Alias* hinzu, sodaß wir uns immer wieder bequem mit den gleichen Einstellungen verbinden können:

![Screenshot - SquiirelSQL - Alias anlegen](https://github.com/bglu/lrWorkshop/blob/master/Dokumentation/img/squirrel01.png)

Im nun folgenden Dialog vergeben wir folgende Werte:

- Name: [frei wählbar]
- Driver: Oracle Thin Driver
- URL: jdbc:oracle:thin:@localhost:1521:xe
- UserName: sa
- Password: [passwort des Benutzers sa]

![Screenshot - SquiirelSQL - Alias anlegen](https://github.com/bglu/lrWorkshop/blob/master/Dokumentation/img/squirrel02.png)

Mit der Schaltfläche *Test* können wir die Einstellungen prüfen.

Wenn der Test erfolgreich ist, schließen wir den Dialog mit *OK*. Ein Doppelklick auf den neu angelegten Alias öffnen nun ein Dialogfeld, dass wir zum Verbinden benutzen können. Hier einfach auf *Connect* klicken.

![Screenshot - SquiirelSQL - Verbinden](https://github.com/bglu/lrWorkshop/blob/master/Dokumentation/img/squirrel03.png)

Evtl. wird Squiirel meckern, dass das Laden des Schemas ungewöhnlich lange dauert. Dies ist normal und kann ignoriert werden.

Wenn die Schemata geladen sind, sollte es im Baum das Schema *SA* geben, welches wir mit Doppelklick öffnen können. Mit einem Klick auf *Table* können wir auch die Tabellen von Liferay öffnen und durchstöbern.

![Screenshot - SquiirelSQL - Tabellen ansehen](https://github.com/bglu/lrWorkshop/blob/master/Dokumentation/img/squirrel04.png)
