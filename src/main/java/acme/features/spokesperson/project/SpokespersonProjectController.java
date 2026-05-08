
package acme.features.spokesperson.project;

import javax.annotation.PostConstruct;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;

import acme.client.controllers.AbstractController;
import acme.entities.project.Project;
import acme.realms.Spokesperson;

@Controller
public class SpokespersonProjectController extends AbstractController<Spokesperson, Project> {

	@PostConstruct
	protected void initialise() {
		super.setMediaType(MediaType.TEXT_HTML);

		super.addBasicCommand("list", SpokespersonProjectListService.class);
		super.addBasicCommand("show", SpokespersonProjectShowService.class);
	}
}
