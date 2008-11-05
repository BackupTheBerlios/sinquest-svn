<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<h2>Admin Bereich - Passwort</h2>

<div id="userTable" class="yui-dt">
  <display:table id="user" name="${model.users}">
    <display:column property="id" title="ID" headerClass="yui-dt0-col-name yui-dt-col-name yui-dt-sortable yui-dt-first" />
    <display:column property="username" title="Benutzername" headerClass="yui-dt0-col-name yui-dt-col-name yui-dt-sortable yui-dt-first"/>
    <display:column property="firstName" title="Vorname" headerClass="yui-dt0-col-name yui-dt-col-name yui-dt-sortable yui-dt-first"/>
    <display:column property="lastName" title="Nachname" headerClass="yui-dt0-col-name yui-dt-col-name yui-dt-sortable yui-dt-first"/>
    <display:column title="Bearbeiten" headerClass="yui-dt0-col-name yui-dt-col-name yui-dt-sortable yui-dt-first"><a href="<c:url value="/admin/usermanager/editUser.htm?id=${user.id}"/>">Bearbeiten</a></display:column>
  </display:table>
</div>

