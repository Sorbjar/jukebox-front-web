package be.lode.jukebox.front.web.view.login;

import be.lode.jukebox.front.web.view.general.MainLayout;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.CustomComponent;

public class LoggedInView extends CustomComponent implements View {
	private static final long serialVersionUID = 8079850607581069102L;

	private static final String NAME = "";

	public LoggedInView() {
		super();
		MainLayout vl = new MainLayout();
		setCompositionRoot(vl);

		this.markAsDirty();

	}

	@Override
	public void enter(ViewChangeEvent event) {
		// OAuthUser u = (OAuthUser)
		// VaadinSession.getCurrent().getAttribute("user");
		// mgr.getUser((OAuthUser)
		// VaadinSession.getCurrent().getAttribute("user"));
		// Get the user name from the session
		/*
		 * String username = String.valueOf(getSession().getAttribute("user"));
		 * 
		 * And show the username text.setValue("Hello " + username);
		 */
		this.markAsDirty();
	}

	public static String getName() {
		return NAME;
	}
}