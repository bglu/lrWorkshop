<%--
/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */
--%>
<%@ page import="de.lineas.training.cleverbot.model.ChatLine" %>
<%@ page import="java.util.ArrayList" %>

<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>
<%@ taglib uri="http://alloy.liferay.com/tld/aui" prefix="aui" %>


<portlet:defineObjects />

<%
	ArrayList<ChatLine> chatLines = (ArrayList<ChatLine>) request.getAttribute("chatLines");
%>

<div class="cleverbot dialog-box">

	<%
		for (ChatLine c : chatLines) {
	%>
			<p><%= c.toString() %></p>
	
	<%
		}
	%>

</div>

<portlet:actionURL name="addChatLine" var="addChatLineURL"></portlet:actionURL>

<aui:form action="<%= addChatLineURL  %>" name="<portlet:namespace />fm">
	<aui:input type="text" name="line" style="width: 95%;" label=""></aui:input>
	<aui:button-row>
		<aui:button type="submit" value="Senden"></aui:button>
	</aui:button-row>
</aui:form>