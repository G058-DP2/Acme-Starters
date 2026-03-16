<%-- src/main/resources/WEB-INF/views/sponsor/donation/list.jsp --%>
<%@page%>
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:list>
	<acme:list-column code="auditor.auditSection.list.label.name"  path="name"  width="40%"/>
	<acme:list-column code="auditor.auditSection.list.label.hours" path="hours" width="30%"/>
	<acme:list-column code="auditor.auditSection.list.label.kind"  path="kind"  width="30%"/>
</acme:list>

<jstl:if test="${showCreate == true}">
	<acme:button code="auditor.auditSection.list.button.create" action="/auditor/audit-section/create?auditReportId=${auditReportId}"/>
</jstl:if>