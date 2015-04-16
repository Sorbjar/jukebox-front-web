package be.lode.jukebox.front.web.controller;

import be.lode.jukebox.front.web.view.VaadinSessionManager;
import be.lode.jukebox.front.web.view.account.EditAccountView;

import com.vaadin.ui.MenuBar.Command;
import com.vaadin.ui.MenuBar.MenuItem;

public class EditProfileCommand implements Command {
	private static final long serialVersionUID = 113255954759120944L;

	@Override
	public void menuSelected(MenuItem selectedItem) {
		VaadinSessionManager.getMainUI().navigateTo(EditAccountView.getName());

	}

}
