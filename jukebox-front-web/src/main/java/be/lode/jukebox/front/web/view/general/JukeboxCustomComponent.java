package be.lode.jukebox.front.web.view.general;

import be.lode.jukebox.front.web.view.MainUI;
import be.lode.jukebox.front.web.view.VaadinSessionManager;
import be.lode.jukebox.service.dto.AccountDTO;
import be.lode.jukebox.service.manager.JukeboxManager;

import com.vaadin.ui.CustomComponent;

public abstract class JukeboxCustomComponent extends CustomComponent implements
		IJukeboxView {
	private static final long serialVersionUID = 3369786400854669264L;

	@Override
	public JukeboxManager getJukeboxManager() {
		if (getMainUI() != null)
			return getMainUI().getJukeboxManager();
		return null;
	}

	@Override
	public MainUI getMainUI() {
		if (VaadinSessionManager.getMainUI() == null)
			return (MainUI) this.getUI();
		return VaadinSessionManager.getMainUI();
	}

	@Override
	public boolean isAccountLoggedIn() {
		return VaadinSessionManager.isAccountLoggedIn();
	}

	@Override
	public AccountDTO loggedInAccount() {
		return VaadinSessionManager.getLoggedInAccount();
	}

}
