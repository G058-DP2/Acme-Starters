
package acme.features.auditor.auditsection;

import javax.annotation.PostConstruct;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;

import acme.client.controllers.AbstractController;
import acme.entities.auditReport.AuditSection;
import acme.realms.Auditor;

@Controller
public class AuditorAuditSectionController extends AbstractController<Auditor, AuditSection> {

	@PostConstruct
	protected void initialise() {
		super.setMediaType(MediaType.TEXT_HTML);

		super.addBasicCommand("list", AuditorAuditSectionListService.class);
		super.addBasicCommand("show", AuditorAuditSectionShowService.class);
		super.addBasicCommand("create", AuditorAuditSectionCreateService.class);
		super.addBasicCommand("update", AuditorAuditSectionUpdateService.class);
		super.addBasicCommand("delete", AuditorAuditSectionDeleteService.class);
	}
}
