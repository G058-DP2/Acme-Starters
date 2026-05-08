
package acme.features.fundraiser.project;

import javax.annotation.PostConstruct;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;

import acme.client.controllers.AbstractController;
import acme.entities.project.Project;
import acme.realms.Fundraiser;

@Controller
public class FundraiserProjectController extends AbstractController<Fundraiser, Project> {

	@PostConstruct
	protected void initialise() {
		super.setMediaType(MediaType.TEXT_HTML);

		super.addBasicCommand("list", FundraiserProjectListService.class);
		super.addBasicCommand("show", FundraiserProjectShowService.class);
	}
}
