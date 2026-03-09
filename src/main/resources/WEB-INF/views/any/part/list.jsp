<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:list>
	<acme:list-column code="any.part.form.label.name" path="name" width="25%"/>
	<acme:list-column code="any.part.form.label.description" path="description" width="25%"/>
	<acme:list-column code="any.part.form.label.cost" path="cost" width="25%"/>
	<acme:list-column code="any.part.form.label.kind" path="kind" width="25%"/>
</acme:list>