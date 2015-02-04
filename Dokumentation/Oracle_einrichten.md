
##Eigenen Tablespace und Benutzer für Liferay anlegen

Wir könnten Oracle schon mit dem *System-Benutzer* verwenden. Dabei hätten wir jedoch das Problem, dass unsere **Liferay Tabellen nicht von den System Tabellen getrennt** wären.

Jeder Benutzer hat in Oracle einen *default Tablespace*, auf dem er beim Verbinden arbeitet. Deshalb können wir nun einfach einen eigenen Benutzer mit eigenem Tablespace für Liferay anlegen.

###Tablespace anlegen

Zuerst öffnen wir ein Oracle SQL Terminal. Dieses finden wir im Starmenü:

![Screenshot Run SQL Command](https://github.com/bglu/lrWorkshop/blob/master/Dokumentation/img/Oracle_RunSql.png)

Nun verbinden wir uns mit unserem System-Benutzer:

    SQL> connect
    user-name: system
    passowrd: [password]

Jetzt können wir den Tablespace mit folgendem SQL Befehl anlegen:

    SQL> CREATE BIGFILE TABLESPACE lportal DATAFILE 'lportal.dat' SIZE 5000M AUTOEXTEND ON;

Dies erzeugt einen 5GB Tablespace in der Datei `C:\oraclexe\app\oracle\product\11.2.0\server\database\lportal.dat`.

###Benutzer anlegen
Für diesen Tablespace erstellen wir noch einen Benutzer. Liferay hat eine Konvention, dass für den Datenbank-User der Name *sa* verwendet wird. Dies ist kein Muss, aber wir übernehmen das mal so.

    SQL> CREATE USER sa IDENTIFIED BY [password] DEFAULT TABLESPACE lportal;

Dabei ist [password] durch ein einfaches Passwort zu ersetzen.

Dieser Benutzer hat jedoch noch keine Berechtigungen. Er darf sich aktuell noch nicht einmal mit der Datenbank verbinden. Deshalb müssen wir ihm diese Rechte geben. Der einfach halt halber geben wir ihm *alle* Rechte. **Bitte niemals auf einer Produktiven Maschine so umsetzen!***

    SQL> grant connect to sa;
    SQL> grant all privileges to sa;

