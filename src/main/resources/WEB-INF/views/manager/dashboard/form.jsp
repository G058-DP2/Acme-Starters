<%@page%>
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form readonly="true">
	<acme:form-integer code="manager.dashboard.form.label.totalProjects"       path="totalProjects"/>
	<acme:form-double  code="manager.dashboard.form.label.deviationFromAverage" path="deviationFromAverage"/>
	<acme:form-double  code="manager.dashboard.form.label.minEffort"            path="minEffort"/>
	<acme:form-double  code="manager.dashboard.form.label.maxEffort"            path="maxEffort"/>
	<acme:form-double  code="manager.dashboard.form.label.avgEffort"            path="avgEffort"/>
	<acme:form-double  code="manager.dashboard.form.label.devEffort"            path="devEffort"/>
</acme:form>
