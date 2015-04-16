package be.lode.jukebox.front.web.controller;

import java.util.ArrayList;

import be.lode.jukebox.front.web.view.VaadinSessionManager;
import be.lode.jukebox.front.web.view.account.EditAccountView;
import be.lode.jukebox.service.dto.AccountDTO;
import be.lode.jukebox.service.manager.JukeboxManager;

import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;

public class SaveAccountListener implements ClickListener {
	private static final long serialVersionUID = -7126017894291458053L;
	private EditAccountView editAccountView;
	private ArrayList<String> errors;

	public SaveAccountListener(EditAccountView editAccountView) {
		this.editAccountView = editAccountView;
	}

	@Override
	public void buttonClick(ClickEvent event) {
		if (validate()) {
			AccountDTO dto = editAccountView.getCurrentAccount();
			dto.setFirstName(editAccountView.getFirstNameTF().getValue());
			dto.setLastName(editAccountView.getLastNameTF().getValue());
			dto.setEmailAddress(editAccountView.getEmailTF().getValue());
			JukeboxManager mgr = VaadinSessionManager.getMainUI()
					.getJukeboxManager();
			dto = mgr.save(dto);
			VaadinSessionManager.setLoggedInAccount(dto);
			VaadinSessionManager.getMainUI().navigateBack(
					editAccountView.getClass().getName());
		} else {
			editAccountView.showErrors(errors);
		}

	}

	private boolean validate() {
		errors = new ArrayList<String>();
		if ("".equals(editAccountView.getFirstNameTF().getValue())) {
			errors.add("First name cannot be empty!");
		}
		if ("".equals(editAccountView.getLastNameTF().getValue())) {
			errors.add("Last name cannot be empty!");
		}
		if ("".equals(editAccountView.getEmailTF().getValue())) {
			errors.add("Email cannot be empty!");
		}
		JukeboxManager mgr = VaadinSessionManager.getMainUI()
				.getJukeboxManager();
		if (!mgr.isValidEmailAddress(editAccountView.getEmailTF().getValue())) {
			errors.add("A valid emailaddress must be presented!");
		}
		return errors.size() == 0;

	}

}
