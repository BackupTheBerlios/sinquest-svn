<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<h2><fmt:message key="admin.usermanagement.edit.title"/></h2>
<form:form commandName="user">
            <font color="red">
            	<form:errors path="*" />
            </font>

			<table>
    			<tr>
    				<td>
            			<fmt:message key="admin.usermanagement.edit.username" />
            			<font color="red">
            				<form:errors path="username" />
            			</font>
            		</td>
            		<td colspan="3">
            			<form:input path="username" size="106"/>
            		</td>
            	</tr>
            	<tr>
            		<td>
            			<fmt:message key="admin.usermanagement.edit.firstname" />
            			<font color="red">
            				<form:errors path="firstName" />
            			</font>
            		</td>
            		<td colspan="3">
            			<form:input path="firstName" size="106"/>
            		</td>
            	</tr>
            	<tr>
            		<td>
            			<fmt:message key="admin.usermanagement.edit.surname" />
            			<font color="red">
            				<form:errors path="lastName" />
            			</font>
            		</td>
            		<td colspan="3">
            			<form:input path="lastName" size="106"/>
            		</td>
            	</tr>
            	<tr>
            		<td>
            			<fmt:message key="admin.usermanagement.edit.password"/>
            			<font color="red">
            				<form:errors path="password" />
            			</font>
            		</td>
            		<td>
            			<form:input path="password" />
            		</td>
            	</tr>
            	<tr>
            		<td colspan="4">&nbsp;</td>
            	</tr>
            	<tr style="text-align: right;">
            		<td colspan="4">
            			<input type="submit" value="<fmt:message key="common.button.ok.label"/>"> <INPUT type="<fmt:message key="common.button.reset.label"/>"> <input type="submit" value="<fmt:message key="common.button.cancel.label"/>" name="_cancel">
            		</td>
            	</tr>
			</table>
       </form:form>