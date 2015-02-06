#HeroDB-Portlet

Wir wollen nun ein *Data-Driven-Portlet* implementieren, d.h. ein Portlet, welches mit Daten aus der Datenbank arbeitet. Insbesondere interessieren wir uns für die typischen **C**reate|**R**ead|**U**pdate|**D**elete Operationen.

Dazu erstellen wir ein Portlet, womit wir als Superhelden unsere Superkräfte speichern können.

##Neues Plugin Project anlegen

- Project name: herodb-portlet
- Display name: HeroDB
- Group id: de.lineas.training.herodb
- Active profile: Liferay-v6.2-CE-(Tomcat-7)

JSF, IceFaces

In der `pom.xml` das Profile mit folgenden Zeilen aktivieren:

    <activation>
		<activeByDefault>true</activeByDefault>
	</activation>

Dependencies:

        <dependency>
	    <groupId>com.google.guava</groupId>
	    <artifactId>guava</artifactId>
	    <version>18.0</version>
        </dependency>
    
>Wir binden an dieser Stelle [Google Guava](https://code.google.com/p/guava-libraries/) ein. Dies ist eine Hilfsbibliothek, die Java um einige Funktionaliäten erweitert, sodaß wir besser lesbaren Code schreiben können. So können wir statt:
>
>       String s = ....;
>       if (s != null && ! s.empty())
>	
>schreiben:
>
>       if (! Strings.isNullOrEmpty(s))

	

Nun testen wir, ob das Portlet mit Maven gebaut werden kann. Dazu Konsole im Hauptverzeichnis des Portlets öffnen (`C:\liferay\workspace\herodb-portlet`), und folgenden Befehl absenden:

    mvn package

Hier sollte es zu einer Meldung kommen:

    ----------------
    BUILD SUCCESS
    ----------------




##Service-Beschreibung anlegen

Um mit der Datenbank zu Arbeiten, stellt Lifeay den sogenannten `Service-Builder` zur Verfügung. Dieser stellt sicher, dass alle Portlets einheitlich auf die Datenbank des Portals zugreifen.

> **Achtung:** Es wird dringend davon ageraten, selber *etwa per SQL* auf der Datenbank zu arbeiten. Liferay behählt einen Cache der Daten vor. Im schlimmsten Fall kann es bei Abweichungen zu inkonsistenten Daten kommen.

Für uns als Entwickler bedeutet dies, dass wir alle Datenbank-Aufgaben mit dem Service-Builder realisieren können und müssen.


###Service-xml anlegen

Wir fangen an, indem wir unser Modell in *XML-Form* beschreiben. Dazu erstellen wir die Datei `herodb-portlet/src/main/webapp/web-inf/service.xml`.

Die Eclipse erkennt sofort, dass es sich dabei um eine besondere Datei handelt und öffnet sie deshalb in der Ansicht des Service-Builders.

Hier auf den Eintrag `Service-Builder` klicken, worauf hin im rechten Bereich ein Formular eingeblendet wird. Hier tragen wir folgende Werte ein:

- Package path: de.lineas.training.herodb
- Namespace: hero

![Service Builder - Package path und Namespace](https://github.com/bglu/lrWorkshop/blob/master/Dokumentation/img/herodb_sb01.PNG)  


Der *Package path* legt den Packetnamen fest, unter dem die später erzeugten Dateien abgelegt werden. Der *Namespace* dient als Präfix für die Tabellennamen, um Namenskonflikte mit anderen Portlets zu vermeiden.

Wenn wir dies mit Strg+s speichern, schreibt Eclipse folgendes in unsere XML:

    <?xml version="1.0" encoding="UTF-8"?>
    <!DOCTYPE service-builder PUBLIC "-//Liferay//DTD Service Builder 6.2.0//EN" "http://www.liferay.com/dtd/liferay-service-builder_6_2_0.dtd">
    <service-builder package-path="de.lineas.training.herodb">
        <namespace>hero</namespace>
    </service-builder>

Nun estellen wir eine erste Tabelle: *Power*. In dieser sollen verschiedene `Superkräfte` unserer Helden gespeichert werden. Dafür brauchen wir drei Spalten:

- long PowerId (primärschlüssel)
- String PowerName
- String PowerDescription

Um diese Tabelle im Service-Builder anzulegen, fügen wir unserer `service.xml` folgenden Abschnitt hinzu:

    <entity name="Power" local-service="true" remote-service="false">
    	<!-- the primary key -->
    	<column name="PowerId" type="long" primary="true"></column>

		<!-- name of the super power -->
        <column name="PowerName" type="String"></column>
        <!-- short description of the super power -->
        <column name="PowerDescription" type="String"></column>
    </entity>

Im *entity* Tag wir eine eine Entität (sprich Tabelle) definiert. Für diese tabelle wollen wir einen *lokalen service* haben; d.h. wir wollen mit unserem Portlet darauf zugreifen wollen. *Remote service* ist deaktiviert, weil wir nicht von außerhalb auf diese Tabellen zugreifen möchten. Andernfalls würde der Service-Builder auch einen SOAP Webservice generieren.

Anschließend werden die drei Spalten in den *column* Tags mit ihren Datentypen definiert.



###Service-Builder starten

Um anhand dieser XML-Dateien nun die entsprechenden Service-Klassen zu generieren, starten wir den Service-Builder, indem wir auf der Windows Konsole folgenden Befehl absenden:

    mvn liferay:build-service

Auch hier sollte Maven ein Build-Success melden.

Wenn wir nun in der Eclipse das Projekt aktualisieren, sehen wir, dass einige Pakete und Klassen generiert wurden. Mit diesen Klassen können wir die Einträge in der *Power* Tabelle:

    - lesen / auflisten
    - erstellen
    - bearbeiten
    - löschen

Dazu jedoch später mehr. Zuerst sollten wir das Portlet einmal deployen. Wir sollten im Log ein Eintrag wie folgt finden:

    11:39:02,018 INFO  [localhost-startStop-2][ServiceComponentLocalServiceImpl:313] Running hero SQL scripts

Dieser wird die Tabelle in der Datenbank anlegen. *FYI:* Die Scripte, von dennen hier gespochen wird, wurden vom Service-Builder in folgendem Ordner geschrieben: `/herodb-portlet/src/main/webapp/WEB-INF/sql`

Wenn wir die Datenbank nun in SQuirrelSQL betrachten, finden wir eine neue Tabelle: `HERO_POWER`




##Testdaten-Mockup

Um unsere Anwendung besser schreiben zu können, sollten wir an dieser Stelle *von Hand* Testdaten in die Tabelle schreiben. **Achtung:** Ich habe vorher gesagt, dass wir dies nicht tun sollten. Dies soll nur als Demo dienen.

Wir öffnen also die Tabelle in SQuirrelSQL und klicken mit der rechten Maustatse auf eine der Spalten. Im Kontextmenü können wir nun mit `make editable` die Tabelle editierbar machen. Nun können wir mit einem weiteren Rechtsklick auf eine der Spalte den Eintrag `insert row` wählen.

Hier erstellen wir folgende Zeile:

	- powerId: 0
	- powerName: Clean Code
	- powerDescription: Wielder can write code cleaner than Mr. Proper.



##Daten anzeigen

###Ein Controller (Bean) für die Powers

Neue Klasse erstellen: `de.lineas.training.herodb.beans.PowerBean.java`

    @ManagedBean
    @SessionScoped
    public class PowerBean {

	    private List<Power> powers = new ArrayList<Power>();
	
	    public PowerBean () {
		    updatePowers();
	    }
	
	
	    //--- Helpers
	
	    private void updatePowers () {
		    try {
			    powers = PowerLocalServiceUtil.getPowers(QueryUtil.ALL_POS, QueryUtil.ALL_POS);
		    } catch (SystemException e) {
			    e.printStackTrace();
		    }
	    }
	
	    //--- Getters and Setters

	    public List<Power> getPowers() {
		    return powers;
	    }
	
    }




###Eine View für die Powers

Den Body-Teil in der `/herodb-portlet/src/main/webapp/views/view.xhtml` ersetzen:

    <h:body>
		<h3>Powers:</h3>
		<ul>
			<ui:repeat var="power" value="#{powerBean.powers}">
				<li>
					<h:outputText value="#{power.powerName}" />:
					<h:outputText value="#{power.powerDescription}" />
				</li>
			</ui:repeat>
		</ul>
	</h:body>


Das Portlet sollte nun eine Liste mit einem Eintrag anzeigen.

![Screenshot Portlet zeigt Datensatz](https://github.com/bglu/lrWorkshop/blob/master/Dokumentation/img/herodb_portlet01.PNG) 

Somit können wir Datensätze aus der Datenbank lesen und anzeigen.




##Neue Datensätze anlegen

###LocalService erweiten

Wenn wir neue Datensätze zur Datenbank hinzufügen möchten, können wir wieder *PowerLocalServiceUtil* verwenden. Diese stellt dafür jedoch nur Methoden zur Verfügung, um Power Objekte zu speichern. Wir können ein Power Objekt jedoch nicht erzeugen, da wir hierfür die nächste freie ID für den Primärschlüssel ermitteln müssten. Ingesammt wäre das zu viel Code in der View, welcher nicht in deren Verantwortungsbereich liegt.

Stattdessen erweitern wir den Service um eine Methode, die nur noch die beiden Strings für den PowerName und PowerDescription bekommt, daraus ein Power Objekt erstellt und diese in die Datenbank schreibt.

Dazu schreiben wir folggende Methode in die `/herodb-portlet/src/main/java/de/lineas/training/herodb/service/impl/PowerLocalServiceImpl.java`:

	public Power addPower(String name, String description) {
			Power p = new PowerClp();
			
			if (! Strings.isNullOrEmpty(name) && ! Strings.isNullOrEmpty(description)) {
				try {
					long powerId = counterLocalService.increment();
					p = powerPersistence.create(powerId);
					
					if (p != null) {
						p.setPowerName(name);
						p.setPowerDescription(description);
						
						p = powerPersistence.update(p);
					}
					systemException e) {
					e.printStackTrace();
				}
				
			}
			
			return p;
		}
	}


Nun müssen wir den Service-Builder erneut durchlaufen lassen, damit diese Methode auch in die `PowerLocalServiceUtil` geschrieben wird.




###Controller und Bean

Nun können wir in userer Bean einen Action-Listener zum Hinuzfügen von Powers einfügen:
	
	//--- actions
	public String addPower() {
		if (! powerName.isEmpty() && !powerDescription.isEmpty()) {
			Power p = PowerLocalServiceUtil.addPower(powerName, powerDescription);
			if (p != null){
				System.out.println(String.format("Power added: %s", p.getPowerName()));
			} else {
				System.err.println(String.format("Power was NOT added: ", powerName));
			}
		}

		updatePowers();

		return "";
	}

Außerdem brauchen wir in der Bean noch zwei Strings (mit Gettern und Settern), um den PowerName und die PowerDescription in der View zu halten:

    private String powerName 		= "";
	private String powerDescription = "";


Wir erweitern unsere View um ein Formular, um einen neuen Datensatz anzulegen (direkt unter der ul liste):

    <h:form>
		<table>
			<tr>
				<td>Name:</td>
				<td><h:inputText value="#{powerBean.powerName}"></h:inputText></td>
			</tr>
			<tr>
				<td>Description:</td>
				<td><h:inputText value="#{powerBean.powerDescription}"></h:inputText></td>
			</tr>
		</table>
			
		<h:commandButton action="#{powerBean.addPower}" value="Add Power"></h:commandButton>
	</h:form>


Wenn wir auf der Seite nun den Knopf klicken, wird der entsprechende Eintrag zur Datenbank hinzugefüt, und die Liste wird aktualisiert.




##Datensätze bearbeiten

###Service-Eweitern

Folgende Methode einfügen in `PowerLocalServiceImpl`:

	public Power updatePower(long powerId, String name, String description) {
		Power p = null;
		try {
			p = powerPersistence.fetchByPrimaryKey(powerId);
			if (p != null &&
				! Strings.isNullOrEmpty(name) &&
				! Strings.isNullOrEmpty(description)) {
				
				p.setPowerName(name);
				p.setPowerDescription(description);
				
				p = powerPersistence.update(p);
			}
			
		} catch (SystemException e) {
			e.printStackTrace();
		}
		
		return p;
	}

Service-Builde neu durchlaufen lassen. Projekt in Eclipse aktualisieren.


###Bean erweitern:

powerID mit Getters und Setters:

    private String powerId			= "";


Action-Listener editPower:

	public String editPower() {
		if (! powerId.isEmpty() &&
			! powerName.isEmpty() &&
			! powerDescription.isEmpty()) {
			
			Power p = PowerLocalServiceUtil.updatePower(Long.parseLong(powerId), powerName, powerDescription);
			
			if (p != null){
				System.out.println(String.format("Power edited: %s", p.getPowerName()));
			} else {
				System.err.println(String.format("Power was NOT edited: ", powerName));
			}
		}
		
		updatePowers();
		
		return "";
	}
	


###View erweitern:

	<h:body>
		<h3>Powers:</h3>
		<table>
			<ui:repeat var="power" value="#{powerBean.powers}">
				<tr>
					<td>
						<h:outputText value="#{power.powerId}" />:
					</td>
					<td>
						<h:outputText value="#{power.powerName}" />:
					</td>
					<td>
						<h:outputText value="#{power.powerDescription}" />
					</td>
				</tr>
			</ui:repeat>
		</table>
		
		
		<h:form>
			<table>
				<tr>
					<td>ID:</td>
					<td><h:inputText value="#{powerBean.powerId}"></h:inputText></td>
				</tr>
				<tr>
					<td>Name:</td>
					<td><h:inputText value="#{powerBean.powerName}"></h:inputText></td>
				</tr>
				<tr>
					<td>Description:</td>
					<td><h:inputText value="#{powerBean.powerDescription}"></h:inputText></td>
				</tr>
			</table>
			
			<h:commandButton action="#{powerBean.addPower}" value="Add Power"></h:commandButton>
			<h:commandButton action="#{powerBean.editPower}" value="Edit Power"></h:commandButton>
		</h:form>
		
	</h:body>


Nun können wir Einträge bearbeiten




##Einträge löschen

###Controller erweitern

	public String deltePower() {
		if (! powerId.isEmpty() ) {
			
			Power p = null;
			try {
				p = PowerLocalServiceUtil.deletePower(Long.parseLong(powerId));
			} catch (NumberFormatException e) {
				e.printStackTrace();
			} catch (PortalException e) {
				e.printStackTrace();
			} catch (SystemException e) {
				e.printStackTrace();
			}
			
			if (p != null){
				System.out.println(String.format("Power deleted: %s", p.getPowerName()));
			} else {
				System.err.println(String.format("Power was NOT deleted: ", powerName));
			}
		}
		
		updatePowers();
		
		return "";
	}
	

##View erweitern

    <h:commandButton action="#{powerBean.deletePower}" value="Delete Power"></h:commandButton>



Nun haben wir ein Portlet mit den vier Standard Datenbankoperationen:

    - Create
    - Read
    - Update
    - Delete

Leider ist der Umgang jedoch sehr unkomfortabel. Um einen Eintrag zu bearbeiten (oder zu löschen), müssen wir erst die ID raussuchen. Außerdem ist es aus sicherheitsgründen (und Usability) ungünstig, den Benutzer die IDs (Primärschlüssel) anzuzeigen.

Stattdessen wollen wir nun die Daten in einer Tabelle auflisten, in der man Zeilen per Klick auswählen kann. Die Ausgewählte Zeile wird dann das Objekt, was berbeitet oder gelöscht wird.




##IceFaces

Um auf Ereignisse im Browser (Benutzer klickt auf ein Element) zu reagieren, wird i.d.R. JavaScript (jQuery) verwendet. Wir können mit JavaScript jedoch nur sehr umständlich auf unsere Bean zugreifen, bzw. Daten an die Bean senden. Um dieses Problem zu lösen, wurde JSF (Java Server Faces) um einige Frameworks erweitert, welche UI-Komponenten bereistellen, sodaß man ohne JavaScript auskommt. Zu diesen Frameworks gehören:

- [http://www.icesoft.org/java/projects/ICEfaces/overview.jsf](IceFaces) 
- [http://primefaces.org/](PrimeFaces) 
- [http://www.liferay.com/de/community/liferay-projects/liferay-faces/overview](LiferayFaces) 

Als Konzernstandard wurde *IceFaces* gewählt.

Damit wir unsere bisherige View später nocheinmal betrachten können, sichern wie sie. Dazu kopieren wir die Datei `herodb-portlet/src/main/webapp/views/view.xhtml` als `herodb-portlet/src/main/webapp/views/view2.xhtml`.

In der `view2.xhtml` entfernen wir die obere Tabelle. Stattdessen fügen wir folgende *DataTable* von IceFaces ein:

	<ace:dataTable
				id="powerTable"
				value="#{powerBean.powers}"
				var="power"
				selectionMode="single"	
			>
						
			<ace:column id="name">
				<f:facet name="header">
					Name
				</f:facet>
				<h:outputText value="#{power.powerName}"></h:outputText>
			</ace:column>
			
			<ace:column id="description">
				<f:facet name="header">
					Beschreibung
				</f:facet>
				<h:outputText value="#{power.powerDescription}"></h:outputText>
			</ace:column>
		
	</ace:dataTable>


**Erläuterung:** Die DataTable erhält eine eindeutige Id namens "powerId". Als *Value* übergeben wir unsere Liste mit den Power-Einträgen. Mit *var* definieren wir eine Laufvariable, mit der wir auf die Einzelnen Einträge der Liste zugreifen können.

In der DataTable definieren wir zwei Spalten: *name* und *description*. Dabei wird mit dem `<f:facet name="header">` die Überschrift der Spalte definiert. Unter dieser Facet schreiben wir den jeweiligen Inhalt der Splate rein - also den Namen des Power Objekts, bzw. die Beschreibung.


Wenn wir das Portlet nun neu deployen und uns die Seite ansehen, bekommen wir eine Fehlermeldung. Wir werden darauf hingewiesen, dass sie die DataTable in einem Form befinden muss. Also verschieben wir das Form Tag über die Tabelle. Nach einem weiteren Redeploy sollte die Tabelle richtig angezeigt werden.

Wir könnnen nun Zeilen in der Tabelle markieren. Allerdings geschied bei der Auswahl noch nichts mit unseren Formularfeldern. Deshalb müssen wir einen *SelectListener* in unserer Bean definieren, der aufgerufen wird, wenn eine Tabellenzeile ausgewählt wurde.




###Bean erweitern

Zuerste erstellen wir eine neue Property, mit der wir uns die ausgewählte Spalte merken (mit Getter und Setter):

    private Power selectedPower;

Nun schreiben wir einen SelectListener, welche die Property bei Auswahl entsprechend setzt:
    
    public void selectListener(SelectEvent e) {
    	selectedPower = (Power) e.getObject();
    		
    	if (selectedPower != null) {
    		//set form fields
    		setPowerName(selectedPower.getPowerName());
    		setPowerDescription(selectedPower.getPowerDescription());
    		setPowerId(String.valueOf(selectedPower.getPowerId()));
    	}

    }
    

Als letztes müssen wir in der View noch der DataTable sagen, welcher Listener bei Zeielenauswahl aufzurufen ist. Dazu fügen wir folgendes Attribut zur DataTable hinzu:
    
    rowSelectListener="#{powerBean.selectListener}"
	
Die Definition sieht also wie folgt aus:

    <ace:dataTable
		id="powerTable"
		value="#{powerBean.powers}"
		var="power"
		selectionMode="single"	
	    rowSelectListener="#{powerBean.selectListener}"
	>


Wenn wir jetzt redeployen, und die Seite neu betrachten, sehen wir, dass sich die Formularfelder bei Auswahl einer Zeile entsprechend ändern. Wir können nun Einträge bequem bearbeiten und löschen.

Wir sollten jedoch noch einen *Unselect-Listener* implementieren, welcher die Auswahl aufhebt. Dazu erweitern wir unsere Bean um die folgende Funktion:

    //Bitte auf das Unselect (statt Select) Event achten!
    public void unselectListener(UnselectEvent e) { 
    	//reset selected power
		selectedPower = null;

		//reset form fields
		setPowerName("");
		setPowerDescription("");
		setPowerId("");
	}


Auch hier müssen wir der DataTable sagen, dass sie diesen Listener bei Zeilenabwahl aufrufen soll. Dazu fügen wir diese Attribut hinzu:

	rowUnselectListener="#{powerBean.unselectListener}"


Die DataTable sieht im Ganzen also so aus:
 	
    <h:form>
    	<ace:dataTable
    		id="powerTable"
    		value="#{powerBean.powers}"
    		var="power"
    		selectionMode="single"
    		rowSelectListener="#{powerBean.selectListener}"
    		rowUnselectListener="#{powerBean.unselectListener}"
    	>
    				
    		<ace:column id="name">
    			<f:facet name="header">
    				Name
    			</f:facet>
    			<h:outputText value="#{power.powerName}"></h:outputText>
    		</ace:column>
    		
    		<ace:column id="description">
    			<f:facet name="header">
    				Beschreibung
    			</f:facet>
    			<h:outputText value="#{power.powerDescription}"></h:outputText>
    		</ace:column>

    	</ace:dataTable>


