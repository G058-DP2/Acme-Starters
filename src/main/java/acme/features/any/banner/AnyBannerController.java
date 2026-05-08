
package acme.features.any.banner;

import javax.annotation.PostConstruct;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;

import acme.client.components.principals.Any;
import acme.client.controllers.AbstractController;
import acme.entities.banner.Banner;
import acme.internals.controllers.RestResponse;

@Controller
@RestResponse
public class AnyBannerController extends AbstractController<Any, Banner> {

	@PostConstruct
	protected void initialise() {
		super.setMediaType(MediaType.APPLICATION_JSON);

		super.addBasicCommand("show", AnyBannerShowService.class);
	}
}
