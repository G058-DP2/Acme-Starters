
package acme.features.authenticated.spokesperson;

import javax.annotation.PostConstruct;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;

import acme.client.components.principals.Authenticated;
import acme.client.controllers.AbstractController;
import acme.realms.Spokesperson;

@Controller
public class AuthenticatedSpokespersonController extends AbstractController<Authenticated, Spokesperson> {

	// Constructors -----------------------------------------------------------

	@PostConstruct
	protected void initialise() {
		super.setMediaType(MediaType.TEXT_HTML);

		super.addBasicCommand("create", AuthenticatedSpokespersonCreateService.class);
		super.addBasicCommand("update", AuthenticatedSpokespersonUpdateService.class);
	}

}
