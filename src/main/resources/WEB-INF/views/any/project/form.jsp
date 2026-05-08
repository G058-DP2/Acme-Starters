<%@page%>
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form>
	<acme:form-textbox  code="any.project.form.label.title"           path="title"/>
	<acme:form-textbox  code="any.project.form.label.keyWords"        path="keyWords"/>
	<acme:form-textarea code="any.project.form.label.description"     path="description"/>
	<acme:form-moment   code="any.project.form.label.kickOffMoment"   path="kickOffMoment"/>
	<acme:form-moment   code="any.project.form.label.closeOutMoment"  path="closeOutMoment"/>
	<acme:form-url      code="any.project.form.label.moreInfo"        path="moreInfo"/>

	<acme:button code="any.project.form.button.manager" action="/any/manager/show?id=${managerId}"/>
</acme:form>
