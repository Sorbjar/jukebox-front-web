package be.lode.jukebox.front.web.view;

import be.lode.jukebox.service.dto.AccountDTO;

import com.vaadin.server.VaadinSession;

public class VaadinSessionManager {

	public static AccountDTO loggedInAccount() {
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
}
