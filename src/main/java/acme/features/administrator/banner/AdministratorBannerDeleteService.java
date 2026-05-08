package acme.features.administrator.banner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.principals.Administrator;
import acme.client.services.AbstractService;
import acme.entities.banner.Banner;

@Service
public class AdministratorBannerDeleteService extends AbstractService<Administrator, Banner> {

	@Autowired
	private AdministratorBannerRepository	repository;

	private Banner							banner;

	@Override
	public void load() {
		int id;

		id = super.getRequest().getData("id", int.class);
		this.banner = this.repository.findBannerById(id);
	}

	@Override
	public void authorise() {
		boolean status;

		status = this.banner != null;
		super.setAuthorised(status);
	}

	@Override
	public void bind() {
		super.bindObject(this.banner, "slogan", "targetUrl", "picture");
	}

	@Override
	public void validate() {
		// No extra validations for delete
	}

	@Override
	public void execute() {
		this.repository.delete(this.banner);
	}

	@Override
	public void unbind() {
		super.unbindObject(this.banner, "slogan", "targetUrl", "picture");
	}
}
