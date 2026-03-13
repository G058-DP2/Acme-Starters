<%-- src/main/resources/WEB-INF/views/sponsor/donation/list.jsp --%>
<%@page%>
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:list>
	<acme:list-column code="fundraiser.tactic.list.label.name"  path="name"  width="40%"/>
	<acme:list-column code="fundraiser.tactic.list.label.expectedPercentage" path="expectedPercentage" width="30%"/>
	<acme:list-column code="fundraiser.tactic.list.label.kind"  path="kind"  width="30%"/>
</acme:list>

<jstl:if test="${showCreate == true}">
	<acme:button code="fundraiser.tactic.list.button.create" action="/fundraiser/tactic/create?strategyId=${strategyId}"/>
</jstl:if>