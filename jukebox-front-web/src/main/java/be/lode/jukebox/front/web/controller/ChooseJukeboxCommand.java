package be.lode.jukebox.front.web.controller;

import be.lode.jukebox.front.web.view.VaadinSessionManager;
import be.lode.jukebox.front.web.view.chooseJukebox.ChooseJukeboxView;

import com.vaadin.ui.MenuBar.Command;
import com.vaadin.ui.MenuBar.MenuItem;

public class ChooseJukeboxCommand implements Command {
	private static final long serialVersionUID = -5495168420607001268L;

	@Override
	public void menuSelected(MenuItem selectedItem) {
		VaadinSessionManager.getMainUI()
				.navigateTo(ChooseJukeboxView.getName());
	}

}
