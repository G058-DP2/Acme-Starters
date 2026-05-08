<%@page%>
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:list>
	<acme:list-column code="manager.project-membership.list.label.memberName" path="memberName" width="50%"/>
	<acme:list-column code="manager.project-membership.list.label.memberType" path="memberType" width="50%"/>
</acme:list>

<jstl:if test="${showCreate == true}">
	<acme:button code="manager.project-membership.list.button.create" action="/manager/project-membership/create?projectId=${projectId}"/>
</jstl:if>