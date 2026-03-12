<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form>
	<acme:form-textbox code="authenticated.fundraiser.form.label.bank" path="bank"/>
	<acme:form-textarea code="authenticated.fundraiser.form.label.statement" path="statement"/>
	<acme:form-select code="authenticated.fundraiser.form.label.agent" path="agent" choices="${agentChoices}"/>
	
	<jstl:if test="${_command == 'create' }">
		<acme:submit code="authenticated.fundraiser.form.button.create" action="/authenticated/fundraiser/create"/>
	</jstl:if>
	
	<jstl:if test="${_command == 'update' }">
		<acme:submit code="authenticated.fundraiser.form.button.update" action="/authenticated/fundraiser/update"/>
	</jstl:if>
	
</acme:form>