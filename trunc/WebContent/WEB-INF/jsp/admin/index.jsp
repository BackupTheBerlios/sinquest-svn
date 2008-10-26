<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<h2>Admin Bereich - Home</h2>
<p>
Der Funktionsumfang des Admin-Bereichs beschr&auml;nkt sich momentan auf die M&ouml;glichkeit, dass Passwort des Administrators zu setzen und die konfigurierten Indexer-Tasks zu starten und zu stoppen. 
Weitere Konfigurationsm&ouml;glichkeiten, wie die zu indexierenden Verzeichnisse, sind nur &uuml;ber die Konfigurationsdatei .../WEB-INF/SimpleInquestConf.xml m&ouml;glich.
</p>


<h3>Admin Funktionen</h3>

<div id="buttons" style="border: 1px solid silver;">
<table border="0" style="width: 100%">
  <tr>
    <td>
      <a href="<c:url value="/admin/usermanager/userList.htm"/>">
        <img src="/SimpleInquest/img/system-users-big.jpg">
      </a> Benutzernamen und Passwort des Administrators &auml;ndern. 
    </td>
    <td>
      <a href="<c:url value="/admin/tasks/taskOverview.htm"/>">
        <img src="/SimpleInquest/img/grey_wheel.jpg">
      </a> Indexer Tasks starten und stoppen.
    </td>
  </tr>
</table>
</div>

<h3>Build Informationen</h3>

TODO Bildversion