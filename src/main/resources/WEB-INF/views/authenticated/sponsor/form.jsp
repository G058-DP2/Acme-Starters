<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form>
	<acme:form-textbox code="authenticated.sponsor.form.label.address" path="address"/>
	<acme:form-textbox code="authenticated.sponsor.form.label.im" path="im"/>
	<acme:form-select code="authenticated.sponsor.form.label.gold" path="gold" choices="${goldChoices}"/>

	<jstl:if test="${_command == 'create'}">
		<acme:submit code="authenticated.sponsor.form.button.create" action="/authenticated/sponsor/create"/>
	</jstl:if>
	<jstl:if test="${_command == 'update'}">
		<acme:submit code="authenticated.sponsor.form.button.update" action="/authenticated/sponsor/update"/>
	</jstl:if>
</acme:form>
