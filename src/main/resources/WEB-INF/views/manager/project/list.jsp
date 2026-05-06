<%@page%>
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:list>
	<acme:list-column code="manager.project.list.label.title"           path="title"           width="40%"/>
	<acme:list-column code="manager.project.list.label.kickOffMoment"   path="kickOffMoment"   width="25%"/>
	<acme:list-column code="manager.project.list.label.closeOutMoment"  path="closeOutMoment"  width="25%"/>
</acme:list>

<acme:button code="manager.project.list.button.create" action="/manager/project/create"/>
