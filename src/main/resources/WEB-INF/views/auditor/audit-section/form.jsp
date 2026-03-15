<%-- src/main/resources/WEB-INF/views/sponsor/donation/form.jsp --%>
<%@page%>
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form>
	<acme:form-textbox  code="auditor.auditSection.form.label.name"  path="name"/>
	<acme:form-textarea code="auditor.auditSection.form.label.notes" path="notes"/>
	<acme:form-double    code="auditor.auditSection.form.label.hours" path="hours"/>
	<acme:form-select   code="auditor.auditSection.form.label.kind"  path="kind" choices="${kinds}"/>

	<jstl:choose>
		<jstl:when test="${acme:anyOf(_command, 'show|update|delete') && draftMode == true}">
			<acme:submit code="auditor.auditSection.form.button.update" action="/auditor/audit-section/update"/>
			<acme:submit code="auditor.auditSection.form.button.delete" action="/auditor/audit-section/delete"/>
		</jstl:when>
		<jstl:when test="${_command == 'create'}">
			<acme:submit code="auditor.auditSection.form.button.create" action="/auditor/audit-section/create?auditReportId=${auditReportId}"/>
		</jstl:when>
	</jstl:choose>
</acme:form>