<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form>
	<acme:form-textbox code="any.strategy.form.label.ticker" path="ticker"/>
	<acme:form-textbox code="any.strategy.form.label.name" path="name"/>
	<acme:form-textarea code="any.strategy.form.label.description" path="description"/>
	<acme:form-moment code="any.strategy.form.label.startMoment" path="startMoment"/>
	<acme:form-moment code="any.strategy.form.label.endMoment" path="endMoment"/>
	<acme:form-url code="any.strategy.form.label.moreInfo" path="moreInfo"/>
	
</acme:form>