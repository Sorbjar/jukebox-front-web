package be.lode.jukebox.front.web.controller;

import java.util.ArrayList;

import be.lode.jukebox.front.web.view.VaadinSessionManager;
import be.lode.jukebox.front.web.view.jukebox.EditJukeboxView;
import be.lode.jukebox.service.dto.CurrencyDTO;
import be.lode.jukebox.service.manager.JukeboxManager;

import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;

public class SaveJukeboxListener implements ClickListener {
	private static final long serialVersionUID = -7126017894291458053L;
	private EditJukeboxView editJukeboxView;
	private ArrayList<String> errors;

	public SaveJukeboxListener(EditJukeboxView editJukeboxView) {
		this.editJukeboxView = editJukeboxView;
	}

	@Override
	public void buttonClick(ClickEvent event) {
		if (validate()) {
			JukeboxManager mgr = editJukeboxView.getJukeboxManager();
			mgr.editJukebox(editJukeboxView.getNameTF().getValue(),
					editJukeboxView.getPaymentEmailTF().getValue(),
					(CurrencyDTO) editJukeboxView.getCurrencyCBox().getValue(),
					editJukeboxView.getPricePerSongTF().getValue());
			editJukeboxView.getMainUI().navigateBack(
					editJukeboxView.getClass().getName());
		} else {
			editJukeboxView.showErrors(errors);
		}
	}

	private boolean validate() {
		errors = new ArrayList<String>();
		if ("".equals(editJukeboxView.getNameTF().getValue())) {
			errors.add("Name cannot be empty!");
		}
		validatePayment();
		return errors.size() == 0;
	}

	private void validatePayment() {
		try {
			double pricePerSong = Double.parseDouble(editJukeboxView
					.getPricePerSongTF().getValue());
			JukeboxManager mgr = VaadinSessionManager.getMainUI()
					.getJukeboxManager();
			pricePerSong = mgr.round(pricePerSong, 2);
			if (pricePerSong < 0) {
				errors.add("Price per song must be positive!");
			}

			if (pricePerSong > 0) {
				if ("".equals(editJukeboxView.getPaymentEmailTF().getValue())) {
					errors.add("Email cannot be empty, if a price has been set!");
				}

				if (!mgr.isValidEmailAddress(editJukeboxView
						.getPaymentEmailTF().getValue())) {
					errors.add("A valid emailaddress must be presented, if a a price has been set!");
				}
			}
		} catch (NumberFormatException ex) {
			errors.add("Price per song must be a number!");
		}

	}
}
