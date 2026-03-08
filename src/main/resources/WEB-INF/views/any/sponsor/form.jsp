<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form>
	<acme:form-textbox code="any.sponsor.form.label.name" path="identity.name"/>
	<acme:form-textbox code="any.sponsor.form.label.surname" path="identity.surname"/>
	<acme:form-textbox code="any.sponsor.form.label.email" path="identity.email"/>
	<acme:form-textbox code="any.sponsor.form.label.address" path="address"/>
	<acme:form-textbox code="any.sponsor.form.label.im" path="im"/>
	<acme:form-textbox code="any.sponsor.form.label.gold" path="gold"/>
</acme:form>