
package acme.features.manager.project;

import javax.annotation.PostConstruct;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;

import acme.client.controllers.AbstractController;
import acme.entities.project.Project;
import acme.realms.Manager;

@Controller
public class ManagerProjectController extends AbstractController<Manager, Project> {

	@PostConstruct
	protected void initialise() {
		super.setMediaType(MediaType.TEXT_HTML);

		super.addBasicCommand("list", ManagerProjectListService.class);
		super.addBasicCommand("show", ManagerProjectShowService.class);
		super.addBasicCommand("create", ManagerProjectCreateService.class);
		super.addBasicCommand("update", ManagerProjectUpdateService.class);
		super.addBasicCommand("delete", ManagerProjectDeleteService.class);

		super.addCustomCommand("publish", "update", ManagerProjectPublishService.class);
	}
}
