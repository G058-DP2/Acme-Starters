<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form>
	<acme:form-textbox code="authenticated.inventor.form.label.bio" path="bio"/>
	<acme:form-textbox code="authenticated.inventor.form.label.keyWords" path="keyWords"/>
	<acme:form-select code="authenticated.inventor.form.label.licensed" path="licensed" choices="${licensedChoices}"/>
	
	<jstl:if test="${_command == 'create'}">
		<acme:submit code="authenticated.inventor.form.button.create" action="/authenticated/inventor/create"/>
	</jstl:if>
	<jstl:if test="${_command == 'update'}">
		<acme:submit code="authenticated.inventor.form.button.update" action="/authenticated/inventor/update"/>
	</jstl:if>

</acme:form>