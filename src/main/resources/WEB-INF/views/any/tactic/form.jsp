<%--
- form.jsp
-
- Copyright (C) 2012-2026 Rafael Corchuelo.
-
- In keeping with the traditional purpose of furthering education and research, it is
- the policy of the copyright owner to permit non-commercial use and redistribution of
- this software. It has been tested carefully, but it is not guaranteed for any particular
- purposes.  The copyright owner does not offer any warranties or representations, nor do
- they accept any liabilities with respect to them.
--%>

<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form>
	<acme:form-textbox code="any.tactic.form.label.name" path="name"/>
	<acme:form-textarea code="any.tactic.form.label.notes" path="notes"/>
	<acme:form-double code="any.tactic.form.label.expectedPercentage" path="expectedPercentage"/>
	<acme:form-url code="any.tactic.form.label.kind" path="kind"/>			
</acme:form>

