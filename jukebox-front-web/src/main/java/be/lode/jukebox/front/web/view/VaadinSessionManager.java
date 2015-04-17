package be.lode.jukebox.front.web.view;

import be.lode.jukebox.service.dto.AccountDTO;

import com.vaadin.server.VaadinSession;
import com.vaadin.ui.UI;

public class VaadinSessionManager {

	public static AccountDTO getLoggedInAccount() {
		boolean isLoggedIn = VaadinSession.getCurrent().getAttribute("user") != null;
		if (isLoggedIn) {
			return (AccountDTO) VaadinSession.getCurrent().getAttribute("user");
		}
		return null;
	}

	public static boolean isAccountLoggedIn() {
		return VaadinSession.getCurrent().getAttribute("user") != null;
	}

	public static void setLoggedInAccount(AccountDTO user) {
		VaadinSession.getCurrent().setAttribute("user", user);
	}

	public static void logOutAccount() {
		VaadinSession.getCurrent().setAttribute("user", null);
	}

	public static MainUI getMainUI() {
		return (MainUI) UI.getCurrent();
	}
}
