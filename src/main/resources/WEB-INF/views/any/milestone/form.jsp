<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form>
	<acme:form-textbox code="any.milestone.form.label.title" path="title"/>
	<acme:form-money code="any.milestone.form.label.effort" path="effort"/>	
	<acme:form-textbox code="any.milestone.form.label.kind" path="kind"/>
	<acme:form-textarea code="any.milestone.form.label.achievements" path="achievements"/>
</acme:form>