package acme.features.administrator.banner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.principals.Administrator;
import acme.client.services.AbstractService;
import acme.entities.banner.Banner;

@Service
public class AdministratorBannerCreateService extends AbstractService<Administrator, Banner> {

	@Autowired
	private AdministratorBannerRepository	repository;

	private Banner							banner;

	@Override
	public void load() {
		this.banner = new Banner();
	}

	@Override
	public void authorise() {
		super.setAuthorised(true);
	}

	@Override
	public void bind() {
		super.bindObject(this.banner, "slogan", "targetUrl", "pictureUrl");
	}

	@Override
	public void validate() {
		super.validateObject(this.banner);
	}

	@Override
	public void execute() {
		this.repository.save(this.banner);
	}

	@Override
	public void unbind() {
		super.unbindObject(this.banner, "slogan", "targetUrl", "pictureUrl");
	}
}
