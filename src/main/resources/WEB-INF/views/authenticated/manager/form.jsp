<%@page%>
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form>
	<acme:form-textbox code="authenticated.manager.form.label.position"  path="position"/>
	<acme:form-textbox code="authenticated.manager.form.label.skills"    path="skills"/>
	<acme:form-select  code="authenticated.manager.form.label.executive" path="executive" choices="${executiveChoices}"/>

	<jstl:if test="${_command == 'create'}">
		<acme:submit code="authenticated.manager.form.button.create" action="/authenticated/manager/create"/>
	</jstl:if>
	<jstl:if test="${_command == 'update'}">
		<acme:submit code="authenticated.manager.form.button.update" action="/authenticated/manager/update"/>
	</jstl:if>
</acme:form>