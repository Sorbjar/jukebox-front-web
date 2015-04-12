package be.lode.jukebox.front.web.controller;

import be.lode.jukebox.front.web.MainUI;
import be.lode.jukebox.front.web.view.login.LoginView;

import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.UI;

public class LoggedInViewChangeListener implements ViewChangeListener {
	private static final long serialVersionUID = -2242355441353421389L;
	private UI mainUI;

	public LoggedInViewChangeListener(MainUI mainUI) {
		super();
		this.mainUI = mainUI;
	}

	@Override
	public boolean beforeViewChange(ViewChangeEvent event) {
		// Check if a user has logged in
		boolean isLoggedIn = VaadinSession.getCurrent().getAttribute("user") != null;
		boolean isLoginView = event.getNewView() instanceof LoginView;

		if (!isLoggedIn && !isLoginView) {
			// Redirect to login view always if a user has not yet logged in
			mainUI.getNavigator().navigateTo(LoginView.getName());
			return false;

		} else if (isLoggedIn && isLoginView) {
			// If someone tries to access to login view while logged in, then
			// cancel the viewChange
			return false;
		}

		return true;
	}

	@Override
	public void afterViewChange(ViewChangeEvent event) {

	}

}
