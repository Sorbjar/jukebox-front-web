package be.lode.jukebox.front.web.controller;

import be.lode.jukebox.front.web.view.VaadinSessionManager;
import be.lode.jukebox.front.web.view.login.LoginView;

import com.vaadin.ui.MenuBar.Command;
import com.vaadin.ui.MenuBar.MenuItem;
import com.vaadin.ui.UI;

//TODO jump into logout command
//TODO logout command not fired
//TODO make sure headerbar is rebuilt after logout
public class LogoutCommand implements Command {

	private static final long serialVersionUID = 1698755078641018236L;

	@Override
	public void menuSelected(MenuItem selectedItem) {
		if (VaadinSessionManager.isAccountLoggedIn())
			VaadinSessionManager.logOutAccount();
		UI.getCurrent().getNavigator().navigateTo(LoginView.getName());
	}
}
