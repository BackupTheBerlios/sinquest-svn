<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<%@page import="org.apache.lucene.document.Document;"%>

<h1>SimpleInquest V 0.1 Suche</h1>
	<p>
	<form:form commandName="search">
			<table>
    			<tr>
    				<td>
            			Suche:
            			<font color="red">
            				<form:errors path="searchString" />
            			</font>
            		</td>
            		<td>
            			<form:input path="searchString" size="106"/>
            		</td>
            	</tr>
            	<tr style="text-align: right;">
            		<td colspan="2">
            			<input type="submit" value="Suchen">
            		</td>
            	</tr>
			</table>
       </form:form>
	</p>
	
	<c:if test="${search.hitsCount gt 0}">
		<table id="results">
			<c:forEach var="document" items="${search.currentResults}">
			<tr>
				<td><c:out value="${document.path}"/></td>
			</tr>
			</c:forEach>
		</table>
	</c:if>