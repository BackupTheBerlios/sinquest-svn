<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="st" uri="SimpleInquestTagsTags"  %> 

<c:if test="${search.hits eq null}">
<div id="Centerbox">
	<form:form commandName="search" method="get">
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
       <form:form commandName="search" method="get">
			<table style="width: 100%; border-style: none; border-spacing: 0;">
				<colgroup>
					<col width="150px">
    				<col width="360px">
    				<col width="*">
  				</colgroup>
    			<tr>
    				<td>
    					<a href="/SimpleInquest/">
    						<img src="/SimpleInquest/img/Logo_s.jpg" width="136px" height="54" alt="" border="0"/>
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
            		<td colspan="3" style="background-color: gray;"><div style="color: white; margin: 3px;"> Ergebnisse <c:out value="${search.currentPage.first + 1}"/> - <c:out value="${search.currentPage.last + 1}"/> von <c:out value="${search.hitsCount}"/> f&uuml;r <c:out value="${search.searchString}"/></div></td>
            	</tr>
			</table>
       </form:form>	
	<c:if test="${search.hitsCount gt 0}">
		<table id="results">
			<colgroup>
				<col width="20px">
    			<col width="*">
  			</colgroup>
			<c:forEach var="document" items="${search.currentPage.results}">
			<tr>
				<td rowspan="2"><a href="./download/fetchFile.htm?id=<c:out value="${document.id}"/>"><img src="/SimpleInquest/img/mime/22/text-generic.gif" alt="" border="0"/></a></td>
				<td><a href="./download/fetchFile.htm?id=<c:out value="${document.id}"/>"> <c:out value="${document.fileName}"/> </a> - geändert am <c:out value="${document.lastModified}"/></td>
			</tr>
			<tr>
				<td><div id="path"><c:out value="${document.path}"/></div></td>
			</tr>
			</c:forEach>
		</table>
		
		<div id="navigation">
			<table style="width: 100%; border-style: none; border-spacing: 0;">
				<tr>
            		<td align="center" style="background-color: gray;">
            			<div id="resultnav">
            				<st:nav search="${search}"/>
            			</div>
            		</td>
            	</tr>
			</table>
		</div>		
	</c:if>
	<c:if test="${search.hitsCount eq 0}">
		Die Suche ergab keine Treffer
	</c:if>
</c:if>