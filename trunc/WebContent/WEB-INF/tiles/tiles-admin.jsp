<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
  

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title><fmt:message key="common.browser_title"/></title>
		<script type="text/javascript" src="/DailyWork/scripts/yui/build/yahoo/yahoo.js"></script> 
	<script type="text/javascript" src="/DailyWork/scripts/yui/build/event/event.js"></script> 
	<script type="text/javascript" src="/DailyWork/scripts/yui/build/dom/dom.js"></script> 
	
	<script type="text/javascript" src="/DailyWork/scripts/yui/build/element/element-beta-min.js"></script>
	<script type="text/javascript" src="/DailyWork/scripts/yui/build/datasource/datasource-beta-min.js"></script>

	<!-- OPTIONAL: JSON Utility -->
	<script type="text/javascript" src="/DailyWork/scripts/yui/build/json/json-min.js"></script>

	<!-- OPTIONAL: Connection (enables XHR) -->
	<script type="text/javascript" src="/DailyWork/scripts/yui/build/connection/connection-min.js"></script>
	
	<!-- Source files -->
	<script type="text/javascript" src="/DailyWork/scripts/yui/build/datatable/datatable-beta-min.js"></script>
	
	<!-- Helper files -->
	<script type="text/javascript" src="/DailyWork/scripts/CalendarHelper.js"></script>
	 
	<script type="text/javascript" src="/DailyWork/scripts/yui/build/calendar/calendar.js"></script> 
	<link type="text/css" rel="stylesheet" href="/DailyWork/scripts/yui/build/calendar/assets/skins/sam/calendar.css"/>
	<!--CSS file (default YUI Sam Skin) -->
	<link type="text/css" rel="stylesheet" href="/DailyWork/scripts/yui/build/datatable/assets/skins/sam/datatable.css"/>
	<link rel="stylesheet" type="text/css" href="/SimpleInquest/common.css"/>
</head>
<body>
	<div class="topbox">
		<table cellspacing="0">
			<tr>
				<td><tiles:insertAttribute name="top" /></td>
			</tr>
		</table>
	</div>
	<tiles:insertAttribute name="navigation" />
	<div id="AdminContent">
		<tiles:insertAttribute name="content" />
	</div>
</body>
</html>
