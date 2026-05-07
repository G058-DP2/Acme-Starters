
package acme.features.administrator.banner;

import javax.annotation.PostConstruct;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;

import acme.client.components.principals.Administrator;
import acme.client.controllers.AbstractController;
import acme.entities.banner.Banner;

@Controller
public class AdministratorBannerController extends AbstractController<Administrator, Banner> {

	@PostConstruct
	protected void initialise() {
		super.setMediaType(MediaType.TEXT_HTML);

		super.addBasicCommand("list", AdministratorBannerListService.class);
		super.addBasicCommand("show", AdministratorBannerShowService.class);
		super.addBasicCommand("create", AdministratorBannerCreateService.class);
		super.addBasicCommand("update", AdministratorBannerUpdateService.class);
		super.addBasicCommand("delete", AdministratorBannerDeleteService.class);
	}
}
