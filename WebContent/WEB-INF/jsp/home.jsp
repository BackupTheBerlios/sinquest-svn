<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

	<div class="centerbox">
		<h1><fmt:message key="view.home.title"/></h1>
		<p>
			<a href="<c:url value="createJob.htm"/>"><fmt:message key="view.createjob.title"/></a><br/>
			<a href="<c:url value="listJobs.htm"/>"><fmt:message key="view.listjobs.title"/></a><br/>
		</p>
	</div> <!-- centerbox -->

	<div class="actionbox">
		<div><input type="submit" /> <input type="reset" /></div>
	</div>
