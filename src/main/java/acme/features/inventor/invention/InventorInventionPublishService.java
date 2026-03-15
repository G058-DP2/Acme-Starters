
package acme.features.inventor.invention;

import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.models.Tuple;
import acme.client.helpers.MomentHelper;
import acme.client.services.AbstractService;
import acme.entities.invention.Invention;
import acme.entities.invention.Part;
import acme.realms.Inventor;

@Service
public class InventorInventionPublishService extends AbstractService<Inventor, Invention> {

	@Autowired
	private InventionRepository	repository;

	private Invention			invention;


	@Override
	public void load() {
		int id;

		id = super.getRequest().getData("id", int.class);
		this.invention = this.repository.findInventionById(id);
	}

	@Override
	public void authorise() {
		boolean status;

		status = this.invention != null && //
			this.invention.isDraftMode() && //
			this.invention.getInventor().isPrincipal();
		super.setAuthorised(status);
	}

	@Override
	public void bind() {
		super.bindObject(this.invention, "ticker", "name", "description", "startMoment", "endMoment", "moreInfo");
	}

	@Override
	public void validate() {
		super.validateObject(this.invention);
		{
			Collection<Part> parts;
			boolean hasParts;

			parts = this.repository.findPartsByInventionId(this.invention.getId());
			hasParts = parts != null && !parts.isEmpty();
			super.state(hasParts, "*", "acme.validation.invention.parts.error");
		}
		{
			Date start;
			Date end;
			boolean validInterval;

			start = this.invention.getStartMoment();
			end = this.invention.getEndMoment();
			validInterval = start != null && end != null && MomentHelper.isBefore(start, end);
			super.state(validInterval, "endMoment", "acme.validation.invention.dates.error");
		}
		{
			Date start;
			Date end;
			boolean validDates;

			start = this.invention.getStartMoment();
			end = this.invention.getEndMoment();
			validDates = start != null && end != null && MomentHelper.isFuture(start) && MomentHelper.isFuture(end);
			super.state(validDates, "*", "acme.validation.invention.dates.error-publish");
		}
	}

	@Override
	public void execute() {
		this.invention.setDraftMode(false);
		this.repository.save(this.invention);
	}

	@Override
	public void unbind() {
		Tuple tuple;

		tuple = super.unbindObject(this.invention, //
			"ticker", "name", "description", "startMoment", "endMoment", "moreInfo");
		tuple.put("draftMode", this.invention.isDraftMode());
		tuple.put("monthsActive", this.invention.getMonthsActive());
		tuple.put("cost", this.invention.getCost());
	}

}
