package be.lode.jukebox.front.web.view.general;

import be.lode.jukebox.front.web.view.MainUI;
import be.lode.jukebox.service.dto.AccountDTO;

public interface JukeboxView {
	MainUI getMainUI();
	boolean isAccountLoggedIn();
	AccountDTO loggedInAccount();
}
