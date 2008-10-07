<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<form:form commandName="user">
            <font color="red">
            	<form:errors path="*" />
            </font>

			<table>
    			<tr>
    				<td>
            			Benutzername:
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
            			Vorname:
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
            			Nachname:
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
            			Password:
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
            			<input type="submit" value="Send"> <INPUT type="Reset"> <input type="submit" value="Cancel" name="_cancel">
            		</td>
            	</tr>
			</table>
       </form:form>