<%@page%>
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form>
	<jstl:choose>
		<jstl:when test="${_command == 'create'}">
			<acme:form-select code="manager.project-membership.form.label.inventor"      path="inventor"      choices="${inventorChoices}"/>
			<acme:form-select code="manager.project-membership.form.label.spokesperson"   path="spokesperson"  choices="${spokespersonChoices}"/>
			<acme:form-select code="manager.project-membership.form.label.fundraiser"     path="fundraiser"    choices="${fundraiserChoices}"/>
			<acme:submit code="manager.project-membership.form.button.create" action="/manager/project-membership/create?projectId=${projectId}"/>
		</jstl:when>
		<jstl:when test="${_command == 'show' || _command == 'delete'}">
			<acme:form-textbox code="manager.project-membership.form.label.memberName" path="memberName" readonly="true"/>
			<acme:form-textbox code="manager.project-membership.form.label.memberType" path="memberType" readonly="true"/>
			<jstl:if test="${draftMode == true}">
				<acme:submit code="manager.project-membership.form.button.delete" action="/manager/project-membership/delete"/>
			</jstl:if>
		</jstl:when>
	</jstl:choose>
</acme:form>
