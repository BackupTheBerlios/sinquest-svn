<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
	<div class="yui-dt">
		<c:choose>
			<c:when test="${!empty errors}">
				<table id="errors" border="1">
					<colgroup>
						<col width="5%">
    					<col width="50%">
    					<col width="45%">
  					</colgroup>
					<c:forEach var="error" items="${errors}">
						<tr class="yui-dt-first yui-dt-last">
							<th><fmt:message key="error.severity.label"/></th>
							<th><fmt:message key="error.message.label"/></th>
							<th><fmt:message key="error.exception.label"/></th>
						</tr>
						<tr>
							<td><c:out value="${error.severity}"/></td>
							<td><c:out value="${error.message}"/></td>
							<td><c:out value="${error.exception}"/></td>
						</tr>
					</c:forEach>
				</table>
			</c:when>
			<c:otherwise>
				<fmt:message key="config.error_free"/>
			</c:otherwise>
		</c:choose>
	</div>