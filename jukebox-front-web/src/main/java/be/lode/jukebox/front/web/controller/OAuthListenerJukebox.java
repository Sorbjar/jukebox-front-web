package be.lode.jukebox.front.web.controller;

import be.lode.jukebox.front.web.view.login.LoggedInView;
import be.lode.jukebox.service.dto.OAuthApiInfoDTO;
import be.lode.oauth.OAuthButton.OAuthListener;
import be.lode.oauth.OAuthButton.OAuthUser;

import com.vaadin.server.VaadinSession;
import com.vaadin.ui.UI;

public class OAuthListenerJukebox implements OAuthListener {

	private UI mainUI;
	private OAuthApiInfoDTO oAuthApiInfoDTO;

	public OAuthListenerJukebox(UI mainUI, OAuthApiInfoDTO oAuthApiInfoDTO) {
		this.mainUI = mainUI;
		this.oAuthApiInfoDTO = oAuthApiInfoDTO;
	}

	@Override
	public void failed(String reason) {

	}

	public void setMainUI(UI mainUI) {
		this.mainUI = mainUI;
	}

	@Override
	public void userAuthenticated(OAuthUser user) {
		VaadinSession.getCurrent();
		VaadinSession.getCurrent().setAttribute("user", user);
		mainUI.getNavigator().navigateTo(LoggedInView.getName());
	}
}
