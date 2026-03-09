<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:list>
	<acme:list-column code="any.auditReport.list.label.ticker" path="ticker" width="15%"/>
	<acme:list-column code="any.auditReport.list.label.name" path="name" width="45%"/>
	<acme:list-column code="any.auditReport.list.label.startMoment" path="startMoment" width="20%"/>
	<acme:list-column code="any.auditReport.list.label.endMoment" path="endMoment" width="20%"/>
	<acme:list-hidden path="auditor.identity.fullName"/>
</acme:list>