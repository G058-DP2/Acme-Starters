<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:list>
	<acme:list-column code="any.tactic.list.label.name" path="name" width="30%"/>
	<acme:list-column code="any.tactic.list.label.notes" path="notes" width="30%"/>
	<acme:list-column code="any.tactic.list.label.percentage" path="expectedPercentage" width="20%"/>
	<acme:list-column code="any.tactic.list.label.kind" path="kind" width="20%"/>
</acme:list>