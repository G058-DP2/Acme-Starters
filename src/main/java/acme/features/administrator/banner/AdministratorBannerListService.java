package acme.features.administrator.banner;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.principals.Administrator;
import acme.client.services.AbstractService;
import acme.entities.banner.Banner;

@Service
public class AdministratorBannerListService extends AbstractService<Administrator, Banner> {

	@Autowired
	private AdministratorBannerRepository repository;

	private Collection<Banner> banners;

	@Override
	public void authorise() {
		super.setAuthorised(true);
	}

	@Override
	public void load() {
		this.banners = this.repository.findAllBanners();
	}

	@Override
	public void unbind() {
		super.unbindObjects(this.banners, "slogan", "targetUrl");
	}
}
