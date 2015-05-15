package be.lode.jukebox.front.web.controller;

import be.lode.jukebox.front.web.view.MainUI;
import be.lode.jukebox.front.web.view.VaadinSessionManager;
import be.lode.jukebox.front.web.view.chooseJukebox.ChooseJukeboxView;
import be.lode.jukebox.front.web.view.login.LoginView;
import be.lode.oauth.OAuthButton.IOAuthUser;
import be.lode.oauth.OAuthButton.OAuthListener;

public class OAuthListenerJukebox implements OAuthListener {
	private MainUI mainUI;

	@Override
	public void failed(String reason) {
		mainUI.navigateTo(LoginView.getName());
	}

	@Override
	public void userAuthenticated(IOAuthUser user) {
		VaadinSessionManager.setLoggedInAccount(mainUI.getJukeboxManager()
				.getUser(user));
		mainUI.navigateTo(ChooseJukeboxView.getName());
	}

	public MainUI getMainUI() {
		return mainUI;
	}

	public void setMainUI(MainUI mainUI) {
		this.mainUI = mainUI;
	}

}
