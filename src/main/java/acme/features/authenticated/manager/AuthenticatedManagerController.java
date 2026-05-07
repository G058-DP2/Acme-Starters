
package acme.features.authenticated.manager;

import javax.annotation.PostConstruct;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;

import acme.client.components.principals.Authenticated;
import acme.client.controllers.AbstractController;
import acme.realms.Manager;

@Controller
public class AuthenticatedManagerController extends AbstractController<Authenticated, Manager> {

	@PostConstruct
	protected void initialise() {
		super.setMediaType(MediaType.TEXT_HTML);

		super.addBasicCommand("create", AuthenticatedManagerCreateService.class);
		super.addBasicCommand("update", AuthenticatedManagerUpdateService.class);
	}
}
