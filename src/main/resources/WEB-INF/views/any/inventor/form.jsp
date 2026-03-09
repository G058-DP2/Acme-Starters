<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form>
	<acme:form-textbox code="any.inventor.form.label.name" path="identity.name"/>
	<acme:form-textbox code="any.inventor.form.label.surname" path="identity.surname"/>
	<acme:form-textbox code="any.inventor.form.label.email" path="identity.email"/>
	<acme:form-textbox code="any.inventor.form.label.bio" path="bio"/>
	<acme:form-textbox code="any.inventor.form.label.keywords" path="keyWords"/>
	<acme:form-textbox code="any.inventor.form.label.licensed" path="licensed"/>
</acme:form>