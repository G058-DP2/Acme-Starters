<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form>
	<acme:form-textbox code="authenticated.spokesperson.form.label.cv" path="cv"/>
	<acme:form-textbox code="authenticated.spokesperson.form.label.achievements" path="achievements"/>
	<acme:form-select code="authenticated.spokesperson.form.label.licensed" path="licensed" choices="${licensedChoices}"/>

	<jstl:if test="${_command == 'create'}">
		<acme:submit code="authenticated.spokesperson.form.button.create" action="/authenticated/spokesperson/create"/>
	</jstl:if>
	<jstl:if test="${_command == 'update'}">
		<acme:submit code="authenticated.spokesperson.form.button.update" action="/authenticated/spokesperson/update"/>
	</jstl:if>
</acme:form>