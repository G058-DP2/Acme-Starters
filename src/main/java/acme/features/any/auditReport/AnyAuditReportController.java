
package acme.features.any.auditReport;

import javax.annotation.PostConstruct;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;

import acme.client.components.principals.Any;
import acme.client.controllers.AbstractController;
import acme.entities.auditReport.AuditReport;

@Controller
public class AnyAuditReportController extends AbstractController<Any, AuditReport> {

	@PostConstruct
	protected void initialise() {
		super.setMediaType(MediaType.TEXT_HTML);

		super.addBasicCommand("list", AnyAuditReportListService.class);
		super.addBasicCommand("show", AnyAuditReportShowService.class);
	}
}
