<%--
- custom-scripts.jsp
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

<%--
- Include whatever custom JS scripts you need using the following template:
-
- <script type="text/javascript" src="libraries/[[component]]/[[version]]/js/[[component]].min.js"></script>
-
- where [[component]] must be replaced by the name of the corresponding component and
- [[version]] by the corresponding version number.
--%>

<script type="text/javascript">
(function () {
    "use strict";
    window.addEventListener("DOMContentLoaded", function () {
        fetch("${pageContext.request.contextPath}/any/banner/show", { headers: { "Accept": "application/json" } })
            .then(function (response) {
                if (!response.ok) return null;
                return response.json();
            })
            .then(function (data) {
                if (!data || !data.pictureUrl) return;
                var container = document.getElementById("acme-banner-container");
                if (!container) return;
                var link    = document.createElement("a");
                var img     = document.createElement("img");
                link.href   = data.targetUrl;
                link.target = "_blank";
                link.rel    = "noopener noreferrer";
                link.title  = data.slogan;
                img.src     = data.pictureUrl;
                img.alt     = data.slogan;
                img.className = "img-fluid rounded";
                img.style.maxHeight = "70px";
                link.appendChild(img);
                container.appendChild(link);
            })
            .catch(function () { /* silently ignore errors */ });
    });
}());
</script>
