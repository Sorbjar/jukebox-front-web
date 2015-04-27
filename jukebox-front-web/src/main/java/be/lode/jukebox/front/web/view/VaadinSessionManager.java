package be.lode.jukebox.front.web.view;

import javax.servlet.http.Cookie;

import be.lode.jukebox.service.dto.AccountDTO;
import be.lode.jukebox.service.manager.JukeboxManager;

import com.vaadin.server.VaadinService;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.UI;

public class VaadinSessionManager {

	private static final String ID_COOKIE = "jbserviceid";
	private static final String NAME_COOKIE = "jbservicename";

	public static AccountDTO getLoggedInAccount() {
		if (isAccountLoggedIn()) {
			Cookie idCookie = getCookieByName(ID_COOKIE);
			Cookie nameCookie = getCookieByName(NAME_COOKIE);
			if (idCookie != null && nameCookie != null
					&& idCookie.getValue().length() > 0
					&& nameCookie.getValue().length() > 0) {
				MainUI mainui = getMainUI();
				if (mainui != null) {
					JukeboxManager mgr = mainui.getJukeboxManager();
					if (mgr != null) {
						return mgr.getAccount(nameCookie.getValue(),
								idCookie.getValue());
					}
				}
			}
		}
		return (AccountDTO) VaadinSession.getCurrent().getAttribute("user");
		// return null;
	}

	public static boolean isAccountLoggedIn() {
		Cookie idCookie = getCookieByName(ID_COOKIE);
		Cookie nameCookie = getCookieByName(NAME_COOKIE);
		if (idCookie != null && nameCookie != null
				&& idCookie.getValue() != null && nameCookie.getValue() != null
				&& idCookie.getValue().length() > 0
				&& nameCookie.getValue().length() > 0) {
			MainUI mainui = getMainUI();
			if (mainui != null) {
				JukeboxManager mgr = mainui.getJukeboxManager();
				if (mgr != null) {
					return mgr.getAccount(nameCookie.getValue(),
							idCookie.getValue()) != null;
				}
			}
		}
		return VaadinSession.getCurrent().getAttribute("user") != null;
		// return false;
		// return VaadinSession.getCurrent().getAttribute("user") != null;
	}

	public static void setLoggedInAccount(AccountDTO user) {
		Cookie idCookie = getCookieByName(ID_COOKIE);
		Cookie nameCookie = getCookieByName(NAME_COOKIE);

		if (idCookie != null) {
			idCookie.setValue(user.getServiceId());
		} else {
			// Create a new cookie
			idCookie = new Cookie(ID_COOKIE, user.getServiceId());
		}
		if (nameCookie != null) {
			nameCookie.setValue(user.getServiceName());
		} else {
			nameCookie = new Cookie(NAME_COOKIE, user.getServiceName());
		}
		idCookie.setMaxAge(31536000);
		nameCookie.setMaxAge(31536000);

		idCookie.setPath(VaadinService.getCurrentRequest().getContextPath());
		nameCookie.setPath(VaadinService.getCurrentRequest().getContextPath());

		VaadinService.getCurrentResponse().addCookie(idCookie);
		VaadinService.getCurrentResponse().addCookie(nameCookie);

		VaadinSession.getCurrent().setAttribute("user", user);
	}

	private static Cookie getCookieByName(String name) {
		// Fetch all cookies from the request
		Cookie[] cookies = VaadinService.getCurrentRequest().getCookies();

		// Iterate to find cookie by its name
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if (name.equals(cookie.getName())) {
					return cookie;
				}
			}
		}
		return null;
	}

	public static void logOutAccount() {
		Cookie idCookie = getCookieByName(ID_COOKIE);
		Cookie nameCookie = getCookieByName(NAME_COOKIE);

		if (idCookie != null) {
			idCookie.setValue("");
		}
		if (nameCookie != null) {
			nameCookie.setValue("");
		}

		idCookie.setMaxAge(1);
		nameCookie.setMaxAge(1);

		idCookie.setPath(VaadinService.getCurrentRequest().getContextPath());
		nameCookie.setPath(VaadinService.getCurrentRequest().getContextPath());

		VaadinService.getCurrentResponse().addCookie(idCookie);
		VaadinService.getCurrentResponse().addCookie(nameCookie);

		VaadinSession.getCurrent().setAttribute("user", null);
	}

	public static MainUI getMainUI() {
		return (MainUI) UI.getCurrent();
	}
}
