<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
  

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title><fmt:message key="common.browser_title"/></title>
	<link rel="stylesheet" type="text/css" href="/SimpleInquest/common.css"/>
</head>
<body>
	<div class="topbox">
		<table cellspacing="0">
			<tr>
				<td>&nbsp;</td>
			</tr>
		</table>
	</div>
	<div id="Content">
		<tiles:insertAttribute name="content" />
	</div>
</body>
</html>
