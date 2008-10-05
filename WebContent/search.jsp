<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>SimpleInquest</title>
</head>

<body>
  <h1>SimpleInquest V 0.1 Suche</h1>
	<p>
	<form:form commandName="search">
			<table>
    			<tr>
    				<td>
            			Suche:
            			<font color="red">
            				<form:errors path="search" />
            			</font>
            		</td>
            		<td>
            			<form:input path="search" size="106"/>
            		</td>
            	</tr>
            	<tr style="text-align: right;">
            		<td colspan="2">
            			<input type="submit" value="Send"> <INPUT type="Reset"> <input type="submit" value="Cancel" name="_cancel">
            		</td>
            	</tr>
			</table>
       </form:form>
	</p>
</body>
</html>