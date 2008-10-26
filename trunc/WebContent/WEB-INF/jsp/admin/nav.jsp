<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="dt" uri="http://displaytag.sf.net"  %>

<ul id="Navigation">	
  <li><a href="<c:url value="/admin/index.htm"/>">Admin Bereich - Home</a></li>
  <li><a href="<c:url value="/admin/usermanager/userList.htm"/>">Admin Passwort</a></li>
  <li><a href="<c:url value="/admin/tasks/taskOverview.htm"/>">Indexer Tasks</a></li>
</ul>