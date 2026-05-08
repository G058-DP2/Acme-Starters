
package acme.features.inventor.project;

import javax.annotation.PostConstruct;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;

import acme.client.controllers.AbstractController;
import acme.entities.project.Project;
import acme.realms.Inventor;

@Controller
public class InventorProjectController extends AbstractController<Inventor, Project> {

	@PostConstruct
	protected void initialise() {
		super.setMediaType(MediaType.TEXT_HTML);

		super.addBasicCommand("list", InventorProjectListService.class);
		super.addBasicCommand("show", InventorProjectShowService.class);
	}
}
