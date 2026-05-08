package acme.features.any.manager;

import javax.annotation.PostConstruct;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;

import acme.client.components.principals.Any;
import acme.client.controllers.AbstractController;
import acme.realms.Manager;

@Controller
public class AnyManagerController extends AbstractController<Any, Manager> {

	@PostConstruct
	protected void initialise() {
		super.setMediaType(MediaType.TEXT_HTML);

		super.addBasicCommand("list", AnyManagerListService.class);
		super.addBasicCommand("show", AnyManagerShowService.class);
	}
}
