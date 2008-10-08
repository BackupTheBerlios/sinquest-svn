<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<c:if test="${search.hits eq null}">
<div id="Centerbox">
	<form:form commandName="search">
			<table style="width: 500px; table-layout: fixed; border-style: none; padding:0px; margin: 0px; border-spacing: 0px;">
				<tr>
					<td style="width: 500px; height: 99px; background-image: url('/SimpleInquest/img/Headline.jpg'); background-repeat: no-repeat;">&nbsp;</td>
				</tr>
    			<tr>
            		<td align="center">
            			<font color="red">
            				<form:errors path="searchString" />
            			</font>
            			<form:input path="searchString" size="256" cssStyle="width: 450px; margin-right: 20px;"/>
            		</td>
            	</tr>
            	<tr style="text-align: right;">
            		<td>
            			<input type="submit" value="Suche" style="margin-top: 10px; margin-right: 30px;">
            		</td>
            	</tr>
			</table>
       </form:form>
</div>
</c:if>
<c:if test="${search.hits ne null}">
       <form:form commandName="search">
			<table style="width: 100%">
				<colgroup>
					<col width="150px">
    				<col width="360px">
    				<col width="*">
  				</colgroup>
    			<tr>
    				<td>
    					<a href="/SimpleInquest/">
    						<img src="/SimpleInquest/img/Logo_s.jpg" width="136px" height="54" alt=""/>
    					</a>
    				</td>    				
            		<td>
            			<font color="red">
            				<form:errors path="searchString" />
            			</font>
            			<form:input path="searchString" size="80" cssStyle="width: 350px;"/>
            		</td>
            		<td align="left">
            			<input type="submit" value="Suche">
            		</td>
            	</tr>            	
            	<tr>
            		<td colspan="3" style="background-color: gray">&nbsp;</td>
            	</tr>
			</table>
       </form:form>	
	<c:if test="${search.hitsCount gt 0}">
		<table id="results">
			<c:forEach var="document" items="${search.currentResults}">
			<tr>
				<td><c:out value="${document.path}"/></td>
			</tr>
			</c:forEach>
		</table>
	</c:if>
	<c:if test="${search.hitsCount eq 0}">
		Die Suche ergab keine Treffer
	</c:if>
</c:if>