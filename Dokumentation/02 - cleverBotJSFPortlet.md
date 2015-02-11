#CleverBotJSF-Portlet

Wir bauen das gleiche Portlet noch einmal mit JSF


##Dependencies

- ChatterBot https://github.com/pierredavidbelanger/chatter-bot-api

      <dependency>
          <groupId>ca.pjer</groupId>
          <artifactId>chatter-bot-api</artifactId>
          <version>1.3.2</version>
      </dependency>

##Vorgehen

1. Setup Wizzard (Liferay JSF, IceFaces)
1. Dependencies in pom.xml
1. Test-deploy


###Model bauen
- Klasse *ChatLine* (Immutable):

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


###Bean bauen

1. *CleverBotBean* Klasse anlegen
1. String property anlegen
1. Property in View abfragen
1. Redeploy und test, ob propertiy in der View angezeigt wird.


###View umbauen

    <h:form>
        <h:inputText value="#{cleverBotBean.userLine}" style="width: 95%;"></h:inputText>
        <br/>
        <h:commandButton action="#{cleverBotBean.sendLine}" value="Senden"></h:commandButton>
    </h:form>

###Bean umbauen
    private String userLine = "";

    public String sendLine() {
        System.out.println("Sending line: " + userLine);

        return "";
    }



###Resume

Wir können nun auf Knopfdruck Java-Methoden auf dem Server ausführen. D.h. wir haben eine *Action-Listener*.

Wir können mit der Bean sehr einfach Daten zwischen Controller und View austauschen.


###Controller um ChatterBot erweitern

        private static final ChatterBotFactory factory = new ChatterBotFactory();
        private static final List<ChatLine> chatLines = new ArrayList<ChatLine>();

        private static ChatterBot bot1;
        private static ChatterBotSession bot1session;

        public CleverBotBean () {
            try {
                bot1 = factory.create(ChatterBotType.CLEVERBOT);
                bot1session = bot1.createSession();

            } catch (Exception e) {
               e.printStackTrace();
            }
        }

        public String sendLine() {
            try {
                if (!userLine.isEmpty()) {
                    String botResponse = bot1session.think(userLine);

                    ChatLine myLine  = new ChatLine("Me", userLine);
                    ChatLine botLine = new ChatLine("CleverBot", botResponse);

                    chatLines.add(myLine);
                    chatLines.add(botLine);

                    System.out.println(myLine);
                    System.out.println(botLine);

                }
            } catch (Exception e) {
                e.printStackTrace();
            }

                return "";
        }



###View erweitern, sodass Gespräch angezeigt wird

        <div class="cleverbot dialog-box">
            <ui:repeat value="#{cleverBotBean.chatLines}" var="chatLine">
                <p>
                    <h:outputText value="#{chatLine}" escape="false"></h:outputText>
                </p>
            </ui:repeat>
        </div>



##Next up

Data Driven Portlet
