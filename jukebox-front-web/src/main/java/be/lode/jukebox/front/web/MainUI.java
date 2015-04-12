package be.lode.jukebox.front.web;

import javax.servlet.annotation.WebServlet;

import be.lode.jukebox.front.web.controller.LoggedInViewChangeListener;
import be.lode.jukebox.front.web.view.chooseJukebox.ChooseJukeboxView;
import be.lode.jukebox.front.web.view.login.LoginView;
import be.lode.jukebox.service.manager.JukeboxManager;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.annotations.Widgetset;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.UI;
//TODO externalise Strings
//TODO internationalisation

@Theme("jukeboxTheme")
@Widgetset("be.lode.jukebox.front.web.JukeboxWidgetset")
public class MainUI extends UI {
	@WebServlet(urlPatterns = "/*", name = "MainUIServlet", asyncSupported = true)
	@VaadinServletConfiguration(ui = MainUI.class, productionMode = false)
	public static class MainUIServlet extends VaadinServlet {
		private static final long serialVersionUID = 826835517787166751L;
	}

	private static final long serialVersionUID = -4892783635443538479L;

	private JukeboxManager mgr;

	@Override
	protected void init(VaadinRequest vaadinRequest) {
		if (mgr == null)
			mgr = new JukeboxManager();
		this.setNavigator(new Navigator(this, this));
		this.getNavigator().addView(LoginView.getName(), new LoginView(mgr));
		this.getNavigator().addView(ChooseJukeboxView.getName(),
				new ChooseJukeboxView(mgr));

		// User is always redirected to the login view if the user is not logged
		// in
		this.getNavigator().addViewChangeListener(
				new LoggedInViewChangeListener(this));

	}
}
