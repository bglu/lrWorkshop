#Custom Taglibs für JSP-Seiten

Um Java-Snippets in JSP Seiten zu vermeiden, bieten sich Taglibs an. Hier beschreiben wir nun, wie wir eigene Taglibs implementieren können. Als Beispiel entwicklen wir ein Taglib, dass eine Tabelle mit allen Benutzern des Liferay Portals anzeigt.




##TLD erstellen

Um unsere Taglib verwenden zu können, müssen wir erst ihre Definition schreiben. Dazu legen wir die Datei `src/main/webapp/web-inf/tld/userTable.tld` an:

<taglib>
  <tlib-version>1.0</tlib-version>
  <jsp-version>2.0</jsp-version>
  <short-name>Example TLD</short-name>
  <tag>
    <name>UserTable</name>
    <tag-class>de.lineas.training.sbtest.taglibs.UserTableTag</tag-class>
    <body-content>empty</body-content>
  </tag>
</taglib>

###Erläuterung

Wir vergeben zuerst ein paar Meta-Daten. Dann geben wir im `name` Tag an, wie unser *Tag* heißen soll. In unserem Fall also *UserTable*.

Mit der `tag-class` geben wir an, welche Java-Klasse die Funktionen unseres Tags implementiert.

Zuletzt geben wir bei `body-conent` mit *empty* an, dass unser Tag keinen Body verarbeiten soll; d.h. wir erwarten, dass unser Tag sofort geschlossen wir:

```JSP
<lit:UserTable />
````




##Tag-Klasse implementieren

Wir erstellen nun die Klasse `de.lineas.training.sbtest.taglibs.**UserTableTag**` und geben als Oberklasse `javax.servlet.jsp.tagext.**SimpleTagSupport**` an. Dadurch müssen wir in unserer Klasse nur die Methode `doTag` überschreiben. Als ersten Test können wir dies vie folgt tun:

```Java
@Override
public void doTag() throws JspException, IOException {
    JspWriter out = getJspContext().getOut();
    out.write("Hello World.");
}
```



##Erster Test

Zum Testen benutzen wir unsere Test einfach einmal in unserer View:

```JSP
<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>
<%@ taglib uri="/WEB-INF/tld/userTable.tld" prefix="lit" %>

<portlet:defineObjects />


<lit:UserTable />
```

Nach dem Deployen müssten wir somit ein einfaches *Hello World" Beispiel geschaffen haben. Leider ist dies jedoch noch weit von unserem Ziel der Benutzertabelle fern.



##Render Templates mit Velocity

Um unsere Tabelle zu rendern, benutzen wir [Velocity](http://de.wikipedia.org/wiki/Apache_Velocity).

> Bitte die Folgende Dependency in die Maven `pom.xml` eintragen:
>
> ```XML
> <dependency>
>     <groupId>org.apache.velocity</groupId>
>     <artifactId>velocity</artifactId>
>     <version>1.7</version>
> </dependency>
> ```

Dies ist eine Template-Engine. Wir können einfach die HTML-Tabelle mit Platzhaltern in eine seperate Datei schreiben. Velocity wird die Platzhalter dann mit unseren Daten ersetzen.

###Velocity Template

Das Template sieht wie folgt aus (speichern in: `src/main/java/templates/userTable.vm`):

```Velocity
<table class="table table-striped">
	<tr>
		<th>Benutzername</th>
		<th>Login</th>
		<th>Id</th>
	</tr>
	#foreach ( $user in $userList )
		<tr>
			<td>$user.fullName</td>
			<td>$user.login</td>
			<td>$user.userId</td>
		</tr>
	#end
</table>
```

###Tag-Klasse: Template initialisieren

In unserer Tag-Klasse schreiben wir die folgenden Hilfsmethode:

```Java
private Template initVelocityTemplate() {
    VelocityEngine ve = new VelocityEngine();
    ve.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
    ve.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
    ve.init();
    Template t = ve.getTemplate("templates/userTable.vm");
    return t;
}
```

Hier wird Velocity initialisiert und aus unserer Template Datei eine Velocity-Template Objekt erzeugt.


###Tag-Klasse: Benutzerliste Laden

Wir schreiben uns noch eine zweite Hilfsmethode, mit der wir die Liste der aktuellen Liferay Benutzer laden:

```Java
private List<User> getUserList() {
    try {
        return UserLocalServiceUtil.getUsers(QueryUtil.ALL_POS, QueryUtil.ALL_POS);
    } catch (SystemException e) {
        e.printStackTrace();
        return Collections.emptyList();
    }
}
```

###Tag-Klasse: doTag Methode richtig implementiert:

Jetzt fügen wir alles zusammen uns überschreiben die *doTag* Methode wie folgt:

```Java
@Override
public void doTag() throws JspException, IOException {
    Template t = initVelocityTemplate();
    VelocityContext ctx = new VelocityContext();
	
    List<User> userList = getUserList();
    ctx.put("userList", userList);
	
    StringWriter writer = new StringWriter();
    t.merge(ctx, writer);
	
    JspWriter out = getJspContext().getOut();
    out.write(writer.toString());
	
}
```

Nach einem (Clean und) Redeploy sollte unsere Benutzertabelle nun richtig gerendert werden.
