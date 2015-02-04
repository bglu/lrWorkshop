
==Eigenen Tablespace und Benutzer für Liferay anlegen

Wir könnten Oracle schon mit dem *System-Benutzer* verwenden. Dabei hätten wir jedoch das Problem, dass unsere **Liferay Tabellen nicht von den System Tabellen getrennt** wären.

Jeder Benutzer hat in Oracle einen *default Tablespace*, auf dem er beim Verbinden arbeitet. Deshalb können wir nun einfach einen eigenen Benutzer mit eignem Tablespace für Liferay anlegen.

===Tablespace anlegen
Zuerst öffnen wir ein Oracle SQL Terminal. Dies finden wir im Starmenü:

![Screenshot Run SQL Command](https://octodex.github.com/bglu/lrWorkshop/Dokumentation/img/Oracle_RunSql.png)

Bla.