<%@page%>
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:list>
	<acme:list-column code="any.manager.list.label.position"  path="position"  width="40%"/>
	<acme:list-column code="any.manager.list.label.skills"    path="skills"    width="40%"/>
	<acme:list-column code="any.manager.list.label.executive" path="executive" width="20%"/>
</acme:list>
