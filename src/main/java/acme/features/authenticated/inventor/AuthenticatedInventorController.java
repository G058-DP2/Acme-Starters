
package acme.features.authenticated.inventor;

import javax.annotation.PostConstruct;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;

import acme.client.components.principals.Authenticated;
import acme.client.controllers.AbstractController;
import acme.realms.Inventor;

@Controller
public class AuthenticatedInventorController extends AbstractController<Authenticated, Inventor> {

	@PostConstruct
	protected void initialise() {
		super.setMediaType(MediaType.TEXT_HTML);

		super.addBasicCommand("create", AuthenticatedInventorCreateService.class);
		super.addBasicCommand("update", AuthenticatedInventorUpdateService.class);
	}

}
