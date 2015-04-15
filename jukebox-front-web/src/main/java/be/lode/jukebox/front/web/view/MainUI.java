package be.lode.jukebox.front.web.view;

import javax.servlet.annotation.WebServlet;

import be.lode.jukebox.front.web.controller.LoggedInViewChangeListener;
import be.lode.jukebox.front.web.view.chooseJukebox.ChooseJukeboxView;
import be.lode.jukebox.front.web.view.login.LoginView;
import be.lode.jukebox.front.web.view.temp.TempView;
import be.lode.jukebox.service.manager.JukeboxManager;
import be.lode.jukebox.service.manager.OAuthApiInfoManager;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.annotations.Widgetset;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.UI;
//TODO externalise Strings
//TODO internationalisation
//TODO solve lazy loading issue
//TODO solve observer pattern

@Theme("jukeboxTheme")
@Widgetset("be.lode.jukebox.front.web.JukeboxWidgetset")
public class MainUI extends UI {
	@WebServlet(urlPatterns = "/*", name = "MainUIServlet", asyncSupported = true)
	@VaadinServletConfiguration(ui = MainUI.class, productionMode = false)
	public static class MainUIServlet extends VaadinServlet {
		private static final long serialVersionUID = 826835517787166751L;
	}

	private static final long serialVersionUID = -4892783635443538479L;

	private JukeboxManager jukeboxManager;
	private OAuthApiInfoManager oAuthManager;

	public MainUI() {
		super();
		jukeboxManager = new JukeboxManager();
		oAuthManager = new OAuthApiInfoManager();
		getPage().setTitle("Lode's Jukebox");
	}

	@Override
	protected void init(VaadinRequest vaadinRequest) {
		if (jukeboxManager == null)
			jukeboxManager = new JukeboxManager();
		if (oAuthManager == null)
			oAuthManager = new OAuthApiInfoManager();
		this.setNavigator(new Navigator(this, this));
		this.getNavigator().addView(LoginView.getName(), LoginView.class);
		this.getNavigator().addView(TempView.getName(), TempView.class);
		//this.getNavigator().addView(ChooseJukeboxView.getName(), ChooseJukeboxView.class);
		/*
		 * this.getNavigator().addView(LoginView.getName(), new
		 * LoginView(jukeboxManager));
		 * this.getNavigator().addView(ChooseJukeboxView.getName(), new
		 * ChooseJukeboxView(jukeboxManager));
		 * this.getNavigator().addView(JukeboxPlayerView
		 * .getName(),JukeboxPlayerView.class);
		 */
		// Redirected user to the login view if the user is not logged in
		this.getNavigator().addViewChangeListener(
				new LoggedInViewChangeListener(this));


	}

	public OAuthApiInfoManager getoAuthManager() {
		return oAuthManager;
	}

	public JukeboxManager getJukeboxManager() {
		return jukeboxManager;
	}
}
