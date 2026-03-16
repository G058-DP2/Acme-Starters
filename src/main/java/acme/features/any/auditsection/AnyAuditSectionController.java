
package acme.features.any.auditsection;

import javax.annotation.PostConstruct;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;

import acme.client.components.principals.Any;
import acme.client.controllers.AbstractController;
import acme.entities.auditReport.AuditSection;

@Controller
public class AnyAuditSectionController extends AbstractController<Any, AuditSection> {

	@PostConstruct
	protected void initialise() {
		super.setMediaType(MediaType.TEXT_HTML);

		super.addBasicCommand("list", AnyAuditSectionListService.class);
		super.addBasicCommand("show", AnyAuditSectionShowService.class);
	}
}
