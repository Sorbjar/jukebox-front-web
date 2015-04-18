package be.lode.jukebox.front.web.controller;

import be.lode.jukebox.front.web.view.MainUI;
import be.lode.jukebox.front.web.view.VaadinSessionManager;
import be.lode.jukebox.front.web.view.chooseJukebox.ChooseJukeboxView;
import be.lode.oauth.OAuthButton.IOAuthUser;
import be.lode.oauth.OAuthButton.OAuthListener;

//TODO 400 issue with error message
public class OAuthListenerJukebox implements OAuthListener {
	private MainUI mainUI;

	@Override
	public void failed(String reason) {
		// TODO 400 something when failed
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
