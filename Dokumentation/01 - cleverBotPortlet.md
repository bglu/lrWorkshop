#CleverBot-Portlet

Als Einstieg programmieren wir ein *Hello-World* Portlet. Dieses soll jedoch etwas mehr machen, als nur den statischen Text anzuzeigen. Stattdessen bauen wir ein Anbindung an den [Cleverbot](http://www.cleverbot.com/).

![Screenshot Cleverbot Webseite](img/cleverbotPortlet/cleverbotPortlet001_screenshot_Webseite.PNG)

Unser Portlet wird also folgenden Inhalt haben:

 - Ein Div mit dem Chat-Verlauf
 - Ein Inputfeld, in dem der Benutzer eine neue Zeile einfügen kann
 - Ein 'Senden' Button


Für die Anbindung an den Cleverbot gibt es bereits eine Bibliothek: [ChatterBot](https://github.com/pierredavidbelanger/chatter-bot-api). Diese können wir so verwenden, dass wir beim Klicken auf die Schaltrfläche "Senden" nur noch den Text aus dem Inputfeld lesen müssen, und diesen an eine Funktion der Bibliothek übergeben können. Diese liefert und die Antwort des Cleverbots als String, welchen wir in unseren Div mit dem Chatverlauf eintragen können.




##Neues Projekt anlegen

Wir öffnen Eclipse und wählen wählen *File* -> *New Liferay Plugin Project*. Es wird ein Wizzard zum Anlegen des Projekts geöffnet. Hier tragen wir folgende Werte ein:

- Project name: CleverBot-portlet (**Achtung:** Alle Projekte müssen auf *-portlet* enden!)
- Build type: Maven (liferay-maven-plugin)
- Group id: de.lineas.training.cleverbot

Das Feld *Active profiles* ist noch leer. Aber rechts neben dem Feld gibt es zwei Schaltflächen. Wir klicken auf die Schaltfläche ganz rechts: *Create new Maven profile based on Liferay runtime....*.

![Screenshot New Project Wizzard](img/cleverbotPortlet/cleverbotPortlet002_eclipse_neues_Projekt_Wizzard.PNG)

Hier wählen wir als *Liferay Version* die Version *6.2.1* und lassen als *profile location* die *Project pom.xml*. Mit OK können wir den Dialog wieder schließen.

![Screenshot New Maven Profile](img/cleverbotPortlet/cleverbotPortlet003_eclipse_neues_maven_profil.PNG)

Nun gehen wir im Wizzard mit *Next>* weiter.

Hier sollten wir ein *Portlet framework* wählen. Liferay unterstüzt mehrere Frameworks. So können wir wählen zwischen:

- Liferay MVC
- JSF
  - IceFaces
  - PrimeFaces
  - LiferayFaces
- Vaadin

![Screenshot Portlet Frameworks](img/cleverbotPortlet/cleverbotPortlet004_portlet_framework_auswählen.PNG)

Wir werden konzernbedingt am häufigsten mit JSF IceFaces arbeiten. Für dieses erste Beispiel lassen wir jedoch *Liferay MVC* ausgewählt und klicken auf *Finish*.

Der Wizzard wird für das Anlegen des Portlets einen Moment brauchen. Insbesondere beim ersten Projekt, weil Maven im Hintergrund sehr viele Dependencies auflösen muss. Während des Bauens werden evtl. auch Fehler im Projekt angezeigt. Diese sollten jedoch (bis auf ectl. Warnungen) alle verschwinden.




##Dependencies

Wie oben erwähnt, werden wir mit der ChatterBot API arbeiten. Diese müssen wir jedoch **nicht** selber herunterladen und zum Build-Path hinzufügen. Darum soll sich Maven kümmern. Deswegen öffnen wir die `CleverBot-portlet/pom.xml` und fügen bei den Dependencies den folgenden Eintrag hinzu:

```XML
<dependency>
  <groupId>ca.pjer</groupId>
  <artifactId>chatter-bot-api</artifactId>
  <version>1.3.2</version>
</dependency>
```

Später werden wir auch ein paar Unit-Tests schreiben. Dafür benötigen wir JUnit4.

```XML
<dependency>
    <groupId>junit</groupId>
    <artifactId>junit</artifactId>
    <version>4.4</version>
</dependency>
```

Außerdem sollten wir auch das Profil per standard aktivieren (um Probleme beim Compilieren von der Konsole aus zu verhindern). Dazu in der selben `pom.xml`unter dem Eintrag *profile* noch folgende Zeilen einfügen:

```XML
<activation>
    <activeByDefault>true</activeByDefault>
</activation>
```

Wenn wir die Datei nun Speichern (Strg + S), und mit der rechten Maustaste auf das Projekt klicken, bekommen wir ein Kontext-Menü mit dem Eintrag *Maven >*. In diesem Untermenü wählen wir *Update project...*. Anschlißend sollten wir im Projektbaum unter *Maven Dependencies* zwei neue Jar finden:

- *chatter-bot-api-1.3.2.jar*.
- *junit.4.4.jar*

![Screenshot Maven Dependencies](img/cleverbotPortlet/cleverbotPortlet005_junit_dependency.PNG)

> Sollte dies noch nicht der Fall sein, kann man versuchen, die Maven-Dependencies zu aktualisieren. Dazu einmal mit Rechtsklick auf das Projekt klicken. Im Kontext-Menü den Eintrag: *Maven* > *Update Project...* wählen.
> Bringt auch dies keine Besserung, sollte man versuchen, dass Projekt von der Konsole aus zu bauen. Dazu einfach eine Windows Konsole im Projektpfad (C:\liferay\workspace\cleverbot-portlet\) öffnen und folgenden Befehl absetzten:
>
>        mvn package
>
> Maven sollte dann mit einem `Build-Success` abschließen.
>
> Nun zurück in der Eclipse das Projekt aktualiseren (Projekt makieren, rechtsklick, refresh). Dann sollte alles richtig sein.




##Model Implementieren

Liferay Portlets arbeiten nach dem *MVC* Prinzip, welches die Verantwortungsbereiche des Programms in *Model*, *View* und *Controller* teilt. Unser Model soll die Daten representieren, mit dem unsere Anwendung arbeitet. In unserem Fall ist dies das Gespräch mit dem CleverBot.

Dabei besteht jedes Gespräch aus mehreren *Lines*: Was hat der Benutzer gesagt, bzw. was hat der Bot geantwort. Genau so eine 'Line' können wir mit der folgenden Klasse representieren. Wir wählen dazu im Menü *File* > *New* > *Class*. In diesem Dialog wähelen wir die folgenden Werte:

- Package: de.lineas.training.cleverbot.model
- Name: ChatLine

![Screenshot Maven Dependencies](img/cleverbotPortlet/cleverbotPortlet006_neues_Klasse_für_Model.PNG)

Mit *Finish* schließen wir den Dialog. Dann füllen wir die Klasse wie folgt:

```Java
public class ChatLine {
    private final String speaker;
    private final String line;

    private final static String CSS_CLASS_BOT 	= "speaker-bot";
    private final static String CSS_CLASS_HUMAN = "speaker-human";

    public ChatLine(String speaker, String line) {
        this.speaker 	= speaker;
        this.line 		= line;
    }

    public String getSpeaker() {
        return speaker;
    }

    public String getLine() {
        return line;
    }

    @Override
    public String toString(){
        String cssClass = speaker.equals("CleverBot") ? CSS_CLASS_BOT : CSS_CLASS_HUMAN;

        return "<span class='" + cssClass + "'>" + speaker + "</span>: " + line;
    }

}
```



###Erläuterung:

Die Klasse hält zwei Strings:

- speaker
- line

Diese werden im Constructor gesetzt. Zuzätzlich wird die *toString* Methode überschrieben, sodass der speaker mit dem html-tag *span* umschloßen wird. Die Klasse dieses spans wird abhängig vom Sprecher gesetzt. Dies soll später einfach den Text rot darstellen, wenn der Sprecher der CleverBot ist, und blau, wenn es der Benutzer war.

> *BTW:* Es handelt sich hierbar sogar um eine Klasse, welche [Immutable](http://www.javapractices.com/topic/TopicAction.do?Id=29) ist. Das bedeutet, dass sich ihr Status zur Laufzeit niemals ändern kann. Immutable Objects werden allgemein als gutes Design betrachtet, weil sie einfacher zu debuggen sind.




##Exkurs JUnit-Testing

Um sicher zu stellen, dass unsere Klasse *ChatLine* auch richtig funktioniert, sollten wir eine Testklasse schreiben. Vorher müssen wir jedoch unter dem Ordner `src` noch einen neuen Ordner `test` erstellen.

![Screenshot Maven Dependencies](img/cleverbotPortlet/cleverbotPortlet008_ordner_test_für_testklassen_erstellen.PNG)

In diesen `test` Ordner erstellen wir einen neuen Ordner `java`. Die Eclipse erkennt nun, dass es sich dabei um einen Source-Folder handelt, und zeigt ihn nun im Baum weit oben an:

![Screenshot Maven Dependencies](img/cleverbotPortlet/cleverbotPortlet009_unterordner_java_im_test_ordner_erstellen.PNG)


Nun öffnen wir mit dem Tastenkürzel Strg + N ein Dialog zum Erstellen. Im Baum wählen wir *Java* > *JUnit* > *JUnit Test Case* und klicken auf *Next>*.

Hier wählen wir:

-Source-Folder: CleverBot-portlet/src/test/java
-Package: de.lineas.training.cleverbot.model
-Name: ChatLineTest
-Which method stubs would you like to create?
    -setUp
-Class under test: de.lineas.training.cleverbot.model.ChatLine

![Screenshot Maven Dependencies](img/cleverbotPortlet/cleverbotPortlet010_neue_testklasse_erstellen.PNG)


Mit *Next>* gelangen wir zum nächsten Schritt. Hier können wir auswählen, zu welchen Methoden wir tests schreiben wollen. Da es sich bei den anderen Methoden lediglich um Getter handelt, wählen wir nur die *toString()* Methode aus.

![Screenshot Maven Dependencies](img/cleverbotPortlet/cleverbotPortlet011_zu_testende_Klassen_auswählen.PNG)

Nun können wir den Wizzard mit *Finish* abschließen.

Der Wizzard hat uns die folgende Klasse erzeugt:

```Java
import static junit.framework.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class ChatLineTest {

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void testToString() {
        fail("Not yet implemented");
    }

}
```

Hier sehen wir, dass mit *Annotations* gearbeitet wird. Jede Methode, welche die Annotation *@Test* hat, wird als JUnit-Test ausgeführt. Metoden, welche mit *@Before* annotiert sind, werden vor den Testmethoden ausgeführt.

Wir erweitern die Klasse um ein paar Memebers:

```Java

private ChatLine chatLine;
private final String speakerHuman = "Me";
private final String speakerBot = "CleverBot";
private final String line = "Hello";
```

Nun können wir die *testToString* Methode ersetzen durch die beiden Folgenden:

```Java
@Test
public void testToStringHuman() {
    chatLine = new ChatLine(speakerHuman, line);
    
    assertEquals(speakerHuman, chatLine.getSpeaker()); 
    assertEquals(line, chatLine.getLine()); 
    
    String expectedToString = "<span class='speaker-human'>Me</span>: Hello";
    assertEquals(expectedToString, chatLine.toString());
    
}

@Test
public void testToStringBot() {
    chatLine = new ChatLine(speakerBot, line);
    
    assertEquals(speakerBot, chatLine.getSpeaker()); 
    assertEquals(line, chatLine.getLine()); 
    
    String expectedToString = "<span class='speaker-bot'>CleverBot</span>: Hello";
    assertEquals(expectedToString, chatLine.toString());
    
}
```

Wenn wir diese Tests nun durchlaufen lassen mit: *Menü* > *Run* > *Run As* > *JUnit Test*, sollten beide Tests erfolgreich durchlaufen. Somit arbeitet unsere *ChatLine* Klasse genauso, wie wir es erwarten.

![Screenshot Junit alles grün](img/cleverbotPortlet/cleverbotPortlet012_erfolgreicher_Testrun.PNG)]

>**TODO**: Wie sieht dass aus, wenn Tests fehlschlagen.




##View Bauen

Nun bauen wir eine View, welche im Portlet angezeigt werden soll. Diese befindet sich unter: `CleverBot-portlet/src/main/webapp/view.jsp`.

Als erstes importieren wir die *Taglib* [AlloyUI](http://www.liferay.com/de/community/wiki/-/wiki/Main/Alloy+UI). Duzu fügen wir ganz open die folgende Zeile hinzu:

```JSP
<%@ taglib uri="http://alloy.liferay.com/tld/aui" prefix="aui" %>
```

Somit können wir auf alle Tags mit dem prefix `aui` zugreife, z.B. `<aui:form>`.

Nun erstellen wir eine *ActionURL*. Dies wird unsere Verbindung zu unserem Controller (den wir noch nicht implementiert haben). Wir können hier den Namen einer Methode in unserem zukünftigen Kontroller definieren, die wir dann als *form action* aufrufen können.

```JSP
<portlet:actionURL name="addChatLine" var="addChatLineURL"></portlet:actionURL>
```

Im Attribut `name` legen wir den Namen der Methode fest, welche im Controller aufgerufen werden soll. Das Atttribut `var` dient als Referenz, mit der wir diese ActionURL später aufrufen.


Jetzt fügen wir noch eine Form mit einem Submit Button hinzu, um den Aufruf einer Controoler-Funktion zu testen:

```JSP
<aui:form action="<%= addChatLineURL %>" name="<portlet:namespace />fm">
    <aui:button-row>
        <aui:button type="Submit" value="Senden"></aui:button>
    </aui:button-row>
</aui:form>
```

Der Code sollte recht selbsterklärent sein. In der *Form* wird das Attribut `name` auf den *Portlet Namespace* gesetzt. Dies sorgt dafür, dass der Name auch dann eindeutig ist, wenn das Portlet mehrfach auf der gleichen Seite platziert wird. Im Attribut `acion` geben wir unsere ActionURL von oben an, sodass eben die Methode *addChatLine* beim Klicken auf den Senden Button aufgerufen wird.

![Screenshot unserer View](img/cleverbotPortlet/cleverbotPortlet013_unsere_view.PNG)




##Controller Bauen

Wir brauchen natürlich noch einen Kontroller, in dem wir die Methonde `addChatLine` implementieren. Dazu öffnen wir in der Eclipse mit der Tastenkombination *Strg+N* wieder unseren *New Wizzard*. Dort wählen wir *Class* und klicken auf *Next>*.

![Screenshot new class wizzard](img/cleverbotPortlet/cleverbotPortlet014_neue_Klasse_für_Controller_erstellen1.PNG)]

Hier füllen wir die Felder wie folgt:

- *Package:* de.lineas.training.cleverbot.controller
- *Name:* CleverBotPortlet
- *Superclass:* com.liferay.util.bridges.mvc.MVCPortlet

![Screenshot new class wizzard](img/cleverbotPortlet/cleverbotPortlet015_neue_Klasse_für_Controller_erstellen2.PNG)]

Wir klicken auf *Finish* und erhalten unsere gewünschte Klasse.

> *Achtung:* Wir erweitern hier die Oberklasse *MVCPortlet*. Dies ist für LiferayMVC Portlets die Oberklasse für alle Controller. Nähere Informationen dazu findet man [hier](https://www.liferay.com/de/web/meera.success/blog/-/blogs/liferay-mvc-portlet-development-introduction).

In dieser Klasse können wir nun unsere Methode implementieren. Hier soll erstmal nur eine Zeile auf die Konsole geschrieben werden.



#<font color="red">Ab hier noch nicht ausgeschrieben</font>

###Resume

Wir können nun auf Knopfdruck Java-Methoden auf dem Server ausführen. D.h. wir haben eine *Action-Listener*.

Nun wollen wir die Daten aus der View an den CleverBot senden und die Antwort auf der Konsole ausgeben.

###Controller um ChatterBot erweitern

```Java
        private static final ChatterBotFactory factory = new ChatterBotFactory();
        private static final List<ChatLine> chatLines = new ArrayList<ChatLine>();

        private static ChatterBot bot1;
        private static ChatterBotSession bot1session;

        private static ChatterBot bot2;
        private static ChatterBotSession bot2session;

        public CleverBotPortlet () {
            super();

            initChatterBot();
        }

        private static void initChatterBot () {
            try {
                bot1 = factory.create(ChatterBotType.CLEVERBOT);
                bot1session = bot1.createSession();

            } catch (Exception e) {
               e.printStackTrace();
            }
        }

```

Und *addChatLine* erweitern:

```Java

        try {
            String chatLine = ParamUtil.getString(request, "line");

            if (chatLine != null && !chatLine.isEmpty()) {
                String botResponse = bot1session.think(chatLine);

                ChatLine myLine 		    = new ChatLine("Me", chatLine);
                ChatLine cleverBotLine 	= new ChatLine("CleverBot", botResponse);

                chatLines.add(myLine);
                chatLines.add(cleverBotLine);

                System.out.println(myLine);
                System.out.println(cleverBotLine);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

```

###View erweitern, sodass Gespräch angezeigt wird

Noch vor dem *define objects*:

```JSP

    <%@ page import="de.training.lineas.cleverbot.model.ChatLine" %>
    <%@ page import="java.util.ArrayList" %>

```

Eine Div, um das Gepräch anzugeigen:

```JSP

    <%
        ArrayList<ChatLine> chatLines = (ArrayList<ChatLine>) request.getAttribute("chatLines");
    %>

    <div class="cleverbot dialog-box">

        <%
            for (ChatLine c : chatLines) {
        %>
                <p><%= c.toString() %></p>
        <%  } %>

    </div>

```

###Etwas hübscher mit CSS

folgende CSS Regeln in *main.css* eintragen:

```CSS

    .cleverbot.dialog-box {
        height: 200px;
        overflow-y: scroll;
    }

    span.speaker-bot {
        color: red;
    }

    span.speaker-human {
        color: blue;
    }
```


##Review

Wir haben gelernt, wie wir ein eigenes Portlet erstellen können. Dabei haben wir in der View auf Standard Liferay Mechanismen zurück gegriffen: *JSP*.

Wir können nun Daten von der View an den Controller sender - und umgekehrt.

Gutes:

- Quick & Dirty
- Wenig dependencies, Standard Liferay
- Schnelles deployment

Schlechtes:

- Jave-Code in der View!
- Controller ist keine richtige Bean. Daher schlecht testbar.

Verbesserungsideen:

- Custom Taglib, um Snippet in JSP zu vermeiden


##Next up

Wir bauen das selbe Portlet mit JSF.
