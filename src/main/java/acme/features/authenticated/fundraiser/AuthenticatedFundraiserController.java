
package acme.features.authenticated.fundraiser;

import javax.annotation.PostConstruct;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;

import acme.client.components.principals.Authenticated;
import acme.client.controllers.AbstractController;
import acme.realms.Fundraiser;

@Controller
public class AuthenticatedFundraiserController extends AbstractController<Authenticated, Fundraiser> {

	// Constructors -----------------------------------------------------------

	@PostConstruct
	protected void initialise() {
		super.setMediaType(MediaType.TEXT_HTML);

		super.addBasicCommand("create", AuthenticatedFundraiserCreateService.class);
		//super.addBasicCommand("update", AuthenticatedWorkerUpdateService.class);
	}

}
