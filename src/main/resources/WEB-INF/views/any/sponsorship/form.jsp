<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form>
	<acme:form-textbox code="any.sponsorship.form.label.ticker" path="ticker"/>
	<acme:form-textbox code="any.sponsorship.form.label.name" path="name"/>
	<acme:form-textarea code="any.sponsorship.form.label.description" path="description"/>
	<acme:form-moment code="any.sponsorship.form.label.startMoment" path="startMoment"/>
	<acme:form-moment code="any.sponsorship.form.label.endMoment" path="endMoment"/>
	<acme:form-url code="any.sponsorship.form.label.moreInfo" path="moreInfo"/>
	
	<acme:button code="any.sponsorship.form.button.donations" action="/any/donation/list?sponsorshipId=$	
	<acme:button code="any.sponsorship.form.button.sponsor" action="/any/sponsor/show?id=${sponsorId}"/>
</acme:form>