<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="st" uri="SimpleInquestTagsTags" %> 

<h2><fmt:message key="admin.home.title"/></h2>

<p><fmt:message key="admin.home.abstract.message"/></p>

<h3><fmt:message key="admin.home.second_title"/></h3>

<div id="buttons" style="border: 1px solid silver;">
<table border="0" style="width: 100%">
  <tr>
    <td>
      <a href="<c:url value="/admin/usermanager/userList.htm"/>">
        <img src="/SimpleInquest/img/system-users-big.jpg">
      </a> <fmt:message key="admin.home.usermanager.message"/>
    </td>
    <td>
      <a href="<c:url value="/admin/tasks/taskOverview.htm"/>">
        <img src="/SimpleInquest/img/grey_wheel.jpg">
      </a> <fmt:message key="admin.home.indexer.message"/>
    </td>
  </tr>
</table>
</div>

<h3><fmt:message key="admin.home.version.title"/></h3>

<fmt:message key="admin.home.version.label"/> <st:version/>