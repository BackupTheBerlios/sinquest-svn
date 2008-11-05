<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<h2>Admin Bereich - Passwort</h2>

<display:table id="user" name="${model.users}">
  <display:column property="id" title="ID" />
  <display:column property="username" title="Benutzername"/>
  <display:column property="firstName" title="Vorname"/>
  <display:column property="lastName" title="Nachname"/>
  <display:column title="Bearbeiten"><a href="<c:url value="/admin/usermanager/editUser.htm?id=${user.id}"/>">Bearbeiten</a></display:column>
</display:table>

