<%@page%>
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form>
	<acme:form-textbox code="administrator.banner.form.label.slogan"    path="slogan"/>
	<acme:form-url     code="administrator.banner.form.label.targetUrl" path="targetUrl"/>
	<acme:form-url     code="administrator.banner.form.label.pictureUrl" path="pictureUrl"/>

	<jstl:choose>
		<jstl:when test="${_command == 'show'}">
			<acme:submit code="administrator.banner.form.button.update" action="/administrator/banner/update"/>
			<acme:submit code="administrator.banner.form.button.delete" action="/administrator/banner/delete"/>
		</jstl:when>
		<jstl:when test="${acme:anyOf(_command, 'update|delete')}">
			<acme:submit code="administrator.banner.form.button.update" action="/administrator/banner/update"/>
			<acme:submit code="administrator.banner.form.button.delete" action="/administrator/banner/delete"/>
		</jstl:when>
		<jstl:when test="${_command == 'create'}">
			<acme:submit code="administrator.banner.form.button.create" action="/administrator/banner/create"/>
		</jstl:when>
	</jstl:choose>
</acme:form>
