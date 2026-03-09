<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form>
	<acme:form-textbox code="any.auditReport.form.label.ticker" path="ticker"/>
	<acme:form-textbox code="any.auditReport.form.label.name" path="name"/>
	<acme:form-textarea code="any.auditReport.form.label.description" path="description"/>
	<acme:form-moment code="any.auditReport.form.label.startMoment" path="startMoment"/>
	<acme:form-moment code="any.auditReport.form.label.endMoment" path="endMoment"/>
	<acme:form-url code="any.auditReport.form.label.moreInfo" path="moreInfo"/>
	
	<acme:button code="any.auditReport.form.button.auditSections" action="/any/audit-section/list?auditReportId=${id}"/>
	<acme:button code="any.auditReport.form.button.auditor" action="/any/auditor/show?id=${auditorId}"/>
</acme:form>