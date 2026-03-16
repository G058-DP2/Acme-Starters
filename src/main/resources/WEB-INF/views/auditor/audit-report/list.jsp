<%-- src/main/resources/WEB-INF/views/sponsor/sponsorship/list.jsp --%>
<%@page%>
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:list>
	<acme:list-column code="auditor.auditReport.list.label.ticker"      path="ticker"      width="15%"/>
	<acme:list-column code="auditor.auditReport.list.label.name"        path="name"        width="35%"/>
	<acme:list-column code="auditor.auditReport.list.label.startMoment" path="startMoment" width="20%"/>
	<acme:list-column code="auditor.auditReport.list.label.endMoment"   path="endMoment"   width="20%"/>
	<acme:list-column code="auditor.auditReport.list.label.draftMode"   path="draftMode"   width="10%"/>
</acme:list>

<acme:button code="auditor.auditReport.list.button.create" action="/auditor/audit-report/create"/>