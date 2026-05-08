package acme.features.projectMember.project;

import javax.annotation.PostConstruct;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;

import acme.client.controllers.AbstractController;
import acme.entities.project.Project;
import acme.realms.ProjectMember;

@Controller
public class ProjectMemberProjectController extends AbstractController<ProjectMember, Project> {

	@PostConstruct
	protected void initialise() {
		super.setMediaType(MediaType.TEXT_HTML);

		super.addBasicCommand("list", ProjectMemberProjectListService.class);
		super.addBasicCommand("show", ProjectMemberProjectShowService.class);
	}

}
