<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>


<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title><fmt:message key="common.browser_title" /></title>
<!--CSS file (default YUI Sam Skin) -->
<link type="text/css" rel="stylesheet"
	href="/SimpleInquest/scripts/yui/build/datatable/assets/skins/sam/datatable.css">
	
<link type="text/css" rel="stylesheet" 
	href="/SimpleInquest/scripts/yui/build/logger/assets/skins/sam/logger.css"> 

<!-- Dependencies -->
<script type="text/javascript"
	src="/SimpleInquest/scripts/yui/build/yahoo-dom-event/yahoo-dom-event.js"></script>
<script type="text/javascript"
	src="/SimpleInquest/scripts/yui/build/element/element-beta-min.js"></script>
<script type="text/javascript"
	src="/SimpleInquest/scripts/yui/build/datasource/datasource-min.js"></script>

<!-- OPTIONAL: JSON Utility (for DataSource) -->
<script type="text/javascript"
	src="/SimpleInquest/scripts/yui/build/json/json-min.js"></script>

<!-- OPTIONAL: Connection Manager (enables XHR for DataSource) -->
<script type="text/javascript"
	src="/SimpleInquest/scripts/yui/build/connection/connection-min.js"></script>

<!-- OPTIONAL: Get Utility (enables dynamic script nodes for DataSource) -->
<script type="text/javascript"
	src="/SimpleInquest/scripts/yui/build/get/get-min.js"></script>

<!-- OPTIONAL: Drag Drop (enables resizeable or reorderable columns) -->
<script type="text/javascript"
	src="/SimpleInquest/scripts/yui/build/dragdrop/dragdrop-min.js"></script>

<!-- OPTIONAL: Calendar (enables calendar editors) -->
<script type="text/javascript"
	src="/SimpleInquest/scripts/yui/build/calendar/calendar-min.js"></script>

<!-- Source files -->
<script type="text/javascript"
	src="/SimpleInquest/scripts/yui/build/datatable/datatable-min.js"></script>
	
<script type="text/javascript" 
	src="/SimpleInquest/scripts/yui/build/logger/logger-min.js"></script>
	
<script type="text/javascript"
	src="/SimpleInquest/scripts/jquery/jquery-1.3.2.min.js"></script>

<link rel="stylesheet" type="text/css" href="/SimpleInquest/common.css" />
</head>
<body class="yui-skin-sam">
<div class="topbox">
<table cellspacing="0">
	<tr>
		<td><tiles:insertAttribute name="top" /></td>
	</tr>
</table>
</div>
<tiles:insertAttribute name="navigation" />
<div id="AdminContent"><tiles:insertAttribute name="content" /></div>
</body>
</html>
