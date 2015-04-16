package be.lode.jukebox.front.web.controller;

import be.lode.jukebox.front.web.view.MainUI;
import be.lode.jukebox.front.web.view.VaadinSessionManager;
import be.lode.jukebox.front.web.view.chooseJukebox.ChooseJukeboxView;
import be.lode.oauth.OAuthButton.IOAuthUser;
import be.lode.oauth.OAuthButton.OAuthListener;

//TODO make sure it can be reused after logout
public class OAuthListenerJukebox implements OAuthListener {
	private MainUI mainUI;

	@Override
	public void failed(String reason) {
		// TODO something when failed
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
