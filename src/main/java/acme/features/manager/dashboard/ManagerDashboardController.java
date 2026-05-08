package acme.features.manager.dashboard;

import javax.annotation.PostConstruct;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;

import acme.client.controllers.AbstractController;
import acme.entities.dashboard.Dashboard;
import acme.realms.Manager;

@Controller
public class ManagerDashboardController extends AbstractController<Manager, Dashboard> {

	@PostConstruct
	protected void initialise() {
		super.setMediaType(MediaType.TEXT_HTML);

		super.addCustomCommand("dashboard", "show", ManagerDashboardShowService.class);
	}
}
