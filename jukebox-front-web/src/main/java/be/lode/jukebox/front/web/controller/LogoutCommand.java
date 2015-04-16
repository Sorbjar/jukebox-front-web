package be.lode.jukebox.front.web.controller;

import be.lode.jukebox.front.web.view.VaadinSessionManager;
import be.lode.jukebox.front.web.view.login.LoginView;

import com.vaadin.ui.MenuBar.Command;
import com.vaadin.ui.MenuBar.MenuItem;

public class LogoutCommand implements Command {

	private static final long serialVersionUID = 1698755078641018236L;

	@Override
	public void menuSelected(MenuItem selectedItem) {
		if (VaadinSessionManager.isAccountLoggedIn())
			VaadinSessionManager.logOutAccount();
		VaadinSessionManager.getMainUI().navigateTo(LoginView.getName());
	}
}
