
package acme.features.manager.membership;

import javax.annotation.PostConstruct;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;

import acme.client.controllers.AbstractController;
import acme.entities.project.ProjectMembership;
import acme.realms.Manager;

@Controller
public class ManagerProjectMembershipController extends AbstractController<Manager, ProjectMembership> {

	@PostConstruct
	protected void initialise() {
		super.setMediaType(MediaType.TEXT_HTML);

		super.addBasicCommand("list", ManagerProjectMembershipListService.class);
		super.addBasicCommand("show", ManagerProjectMembershipShowService.class);
		super.addBasicCommand("create", ManagerProjectMembershipCreateService.class);
	}
}
