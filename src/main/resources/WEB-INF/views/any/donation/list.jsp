<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:list>
	<acme:list-column code="any.donation.list.label.name" path="name" width="40%"/>
	<acme:list-column code="any.donation.list.label.money" path="money" width="30%"/>
	<acme:list-column code="any.donation.list.label.kind" path="kind" width="30%"/>
</acme:list>