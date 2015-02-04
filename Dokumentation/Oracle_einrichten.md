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

Damit Liferay mit Oracle (anstelle der H2-DB) arbeiten kann - (Liferay kann nur mit einer Datenbank pro Portal arbeiten, nicht mit mehreren für verschiedene Portlets), wird eine `ojdbc.jar` von Oracle benötigt.

Diese wird bei der Oracle XE installation mit bereit gestellt unter: `C:\oraclexe\app\oracle\product\11.2.0\server\jdbc\lib\ojdbc6.jar`

**Bitte darauf achten, die Version mit der 6 zu nehmen.** Dieses ist zu dem jdk 6 kombatible, mit dem wir mit dem von Liferay mitgelieferten Tomcat arbeiten.

Wir kopieren (**nicht verschieben/auschneiden**) diese Bibliothek nach `C:\liferay\tomcat\lib\ext`.


