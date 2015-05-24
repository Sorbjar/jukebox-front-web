package be.lode.jukebox.front.web.controller;

import be.lode.jukebox.front.web.view.MainUI;
import be.lode.jukebox.front.web.view.VaadinSessionManager;
import be.lode.jukebox.front.web.view.chooseJukebox.ChooseJukeboxView;
import be.lode.jukebox.front.web.view.login.LoginView;

import com.vaadin.navigator.ViewChangeListener;

public class LoggedInViewChangeListener implements ViewChangeListener {
	private static final long serialVersionUID = -2242355441353421389L;
	private MainUI mainUI;

	public LoggedInViewChangeListener(MainUI mainUI) {
		super();
		this.mainUI = mainUI;
	}

	@Override
	public void afterViewChange(ViewChangeEvent event) {

	}

	@Override
	public boolean beforeViewChange(ViewChangeEvent event) {
		// Check if a user has logged in
		boolean isLoginView = event.getNewView() instanceof LoginView;
		if (!VaadinSessionManager.isAccountLoggedIn() && !isLoginView) {
			// Redirect to login view always if a user has not yet logged in
			((MainUI) mainUI).navigateTo(LoginView.getName());
			return false;

		} else if (VaadinSessionManager.isAccountLoggedIn() && isLoginView) {
			// If someone tries to access to login view while logged in, then go
			// to default view
			((MainUI) mainUI).navigateTo(ChooseJukeboxView.getName());
			return false;
		}

		return true;
	}

}
