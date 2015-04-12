package be.lode.jukebox.front.web.controller;

import be.lode.jukebox.front.web.view.login.ChooseJukeboxView;
import be.lode.jukebox.service.manager.JukeboxManager;
import be.lode.oauth.OAuthButton.IOAuthUser;
import be.lode.oauth.OAuthButton.OAuthListener;

import com.vaadin.server.VaadinSession;
import com.vaadin.ui.UI;

public class OAuthListenerJukebox implements OAuthListener {

	private UI mainUI;
	private JukeboxManager mgr;

	public OAuthListenerJukebox(UI mainUI, JukeboxManager mgr) {
		this.mainUI = mainUI;
		this.mgr = mgr;
	}

	@Override
	public void failed(String reason) {

	}

	public void setMainUI(UI mainUI) {
		this.mainUI = mainUI;
	}

	@Override
	public void userAuthenticated(IOAuthUser user) {
		VaadinSession.getCurrent().setAttribute("user", mgr.getUser(user));
		mainUI.getNavigator().navigateTo(ChooseJukeboxView.getName());
	}
}
