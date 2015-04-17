package be.lode.jukebox.front.web.controller;

import be.lode.jukebox.front.web.view.VaadinSessionManager;
import be.lode.jukebox.front.web.view.jukeboxPlayer.JukeboxPlayerView;

import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;

public class NewJukeboxButtonClickListener implements ClickListener {
	private static final long serialVersionUID = -8851459820539270520L;

	@Override
	public void buttonClick(ClickEvent event) {
		if (VaadinSessionManager.getLoggedInAccount() != null
				&& VaadinSessionManager.getMainUI() != null) {
			VaadinSessionManager
					.getMainUI()
					.getJukeboxManager()
					.createNewJukebox(VaadinSessionManager.getLoggedInAccount());
			VaadinSessionManager.getMainUI().navigateTo(
					JukeboxPlayerView.getName());
		}
	}

}
