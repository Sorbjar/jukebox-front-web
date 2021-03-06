package be.lode.jukebox.front.web.view.general;

import be.lode.jukebox.front.web.view.MainUI;
import be.lode.jukebox.service.dto.AccountDTO;
import be.lode.jukebox.service.manager.CurrencyManager;
import be.lode.jukebox.service.manager.JukeboxManager;

public interface IJukeboxView {
	CurrencyManager getCurrencyManager();

	JukeboxManager getJukeboxManager();

	MainUI getMainUI();

	boolean isAccountLoggedIn();

	AccountDTO loggedInAccount();
}
