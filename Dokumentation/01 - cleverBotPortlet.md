#CleverBot-Portlet

Als Einstieg programmieren wir ein *Hello-World* Portlet. Dieses soll jedoch etwas mehr machen, als nur den statischen Text anzuzeigen. Stattdessen bauen wir ein Anbindung an den [Cleverbot](http://www.cleverbot.com/).

![Screenshot Cleverbot Webseite](https://github.com/bglu/lrWorkshop/blob/master/Dokumentation/img/cleverbotPortlet01.PNG)

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

![Screenshot New Project Wizzard](https://github.com/bglu/lrWorkshop/blob/master/Dokumentation/img/cleverbotPortlet02.PNG)

Hier wählen wir als *Liferay Version* die Version *6.2.1* und lassen als *profile location* die *Project pom.xml*. Mit OK können wir den Dialog wieder schließen.

![Screenshot New Maven Profile](https://github.com/bglu/lrWorkshop/blob/master/Dokumentation/img/cleverbotPortlet02.PNG)

Nun gehen wir im Wizzard mit *Next>* weiter.

Hier sollten wir ein *Portlet framework* wählen. Liferay unterstüzt mehrere Frameworks. So können wir wählen zwischen:

- Liferay MVC
- JSF
  - IceFaces
  - PrimeFaces
  - LiferayFaces
- Vaadin

![Screenshot Portlet Frameworks](https://github.com/bglu/lrWorkshop/blob/master/Dokumentation/img/cleverbotPortlet04.PNG)

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

![Screenshot Maven Dependencies](https://github.com/bglu/lrWorkshop/blob/master/Dokumentation/img/cleverbotPortlet05.PNG)

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

![Screenshot Maven Dependencies](https://github.com/bglu/lrWorkshop/blob/master/Dokumentation/img/cleverbotPortlet06.PNG)

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

![Screenshot Maven Dependencies](https://github.com/bglu/lrWorkshop/blob/master/Dokumentation/img/cleverbotPortlet08.PNG)

In diesen `test` Ordner erstellen wir einen neuen Ordner `java`. Die Eclipse erkennt nun, dass es sich dabei um einen Source-Folder handelt, und zeigt ihn nun im Baum weit oben an:

![Screenshot Maven Dependencies](https://github.com/bglu/lrWorkshop/blob/master/Dokumentation/img/cleverbotPortlet09.PNG)


Nun öffnen wir mit dem Tastenkürzel Strg + N ein Dialog zum Erstellen. Im Baum wählen wir *Java* > *JUnit* > *JUnit Test Case* und klicken auf *Next>*.

Hier wählen wir:

-Source-Folder: CleverBot-portlet/src/test/java
-Package: de.lineas.training.cleverbot.model
-Name: ChatLineTest
-Which method stubs would you like to create?
    -setUp
-Class under test: de.lineas.training.cleverbot.model.ChatLine

![Screenshot Maven Dependencies](https://github.com/bglu/lrWorkshop/blob/master/Dokumentation/img/cleverbotPortlet10.PNG)


Mit *Next>* gelangen wir zum nächsten Schritt. Hier können wir auswählen, zu welchen Methoden wir tests schreiben wollen. Da es sich bei den anderen Methoden lediglich um Getter handelt, wählen wir nur die *toString()* Methode aus.

![Screenshot Maven Dependencies](https://github.com/bglu/lrWorkshop/blob/master/Dokumentation/img/cleverbotPortlet11.PNG)

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





























###View bauen

1. Aui Taglib einbinden:

        <%@ taglib uri="http://alloy.liferay.com/tld/aui" prefix="aui" %>


2. ActionURL erstellen:

        <portlet:actionURL name="addChatLine" var="addChatLineURL"></portlet:actionURL>

3. Form mit einfachem Submit Button:

        <aui:form action="<%= addChatLineURL %>" name="<portlet:namespace />fm">
            <aui:button-row>
                <aui:button type="Submit" value="Senden"></aui:button>
            </aui:button-row>
        </aui:form>


###Controller bauen

1. *CleverBotPortlet* Klasse als MVC Controller anlegen (*extends MVCController*)
1. CleverBotPortlet in *Web-INF/portlet.xml* als *portlet-class* eintragen.
1. Action-Methode implementieren:

        public void addChatLine(ActionRequest request, ActionResponse response) {
            System.out.println("Button pressed");
        }

1. Button testen

###Resume

Wir können nun auf Knopfdruck Java-Methoden auf dem Server ausführen. D.h. wir haben eine *Action-Listener*.

Nun wollen wir die Daten aus der View an den CleverBot senden und die Antwort auf der Konsole ausgeben.

###Controller um ChatterBot erweitern

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

Und *addChatLine* erweitern:

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

###View erweitern, sodass Gespräch angezeigt wird

Noch vor dem *define objects*:

    <%@ page import="de.training.lineas.cleverbot.model.ChatLine" %>
    <%@ page import="java.util.ArrayList" %>

Eine Div, um das Gepräch anzugeigen:

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

###Etwas hübscher mit CSS

folgende CSS Regeln in *main.css* eintragen:

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
