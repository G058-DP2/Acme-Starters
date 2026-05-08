<%@page%>
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form readonly="true">
	<acme:form-textbox  code="spokesperson.project.form.label.title"          path="title"/>
	<acme:form-textbox  code="spokesperson.project.form.label.keyWords"       path="keyWords"/>
	<acme:form-textarea code="spokesperson.project.form.label.description"    path="description"/>
	<acme:form-moment   code="spokesperson.project.form.label.kickOffMoment"  path="kickOffMoment"/>
	<acme:form-moment   code="spokesperson.project.form.label.closeOutMoment" path="closeOutMoment"/>
	<acme:form-url      code="spokesperson.project.form.label.moreInfo"       path="moreInfo"/>
</acme:form>
