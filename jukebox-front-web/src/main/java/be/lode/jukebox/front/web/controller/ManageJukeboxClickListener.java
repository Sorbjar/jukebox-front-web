package be.lode.jukebox.front.web.controller;

import be.lode.jukebox.front.web.view.VaadinSessionManager;
import be.lode.jukebox.front.web.view.jukeboxPlayer.JukeboxPlayerView;

import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;

public class ManageJukeboxClickListener implements ClickListener {
	private static final long serialVersionUID = 1895232990503924742L;

	@Override
	public void buttonClick(ClickEvent event) {
		VaadinSessionManager.getMainUI()
				.navigateTo(JukeboxPlayerView.getName());
	}

}
