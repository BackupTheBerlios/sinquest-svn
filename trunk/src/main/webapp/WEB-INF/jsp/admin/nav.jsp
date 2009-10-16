<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="dt" uri="http://displaytag.sf.net"  %>

<ul id="Navigation">	
  <li><a href="<c:url value="/admin/index.htm"/>"><fmt:message key="admin.nav.home.label"/></a></li>
  <li><a href="<c:url value="/admin/usermanager/userList.htm"/>"><fmt:message key="admin.nav.usermanagement.label"/></a></li>
  <li><a href="<c:url value="/admin/tasks/taskOverview.htm"/>"><fmt:message key="admin.nav.indexer.label"/></a></li>
  <li><div id="log"></div></li>
</ul>