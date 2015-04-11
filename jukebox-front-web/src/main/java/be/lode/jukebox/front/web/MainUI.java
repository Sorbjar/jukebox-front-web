package be.lode.jukebox.front.web;

import javax.servlet.annotation.WebServlet;

import be.lode.jukebox.front.web.controller.LoggedInViewChangeListener;
import be.lode.jukebox.front.web.view.login.LoggedInView;
import be.lode.jukebox.front.web.view.login.LoginView;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.annotations.Widgetset;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

/**
 *
 */
@Theme("jukeboxTheme")
@Widgetset("be.lode.jukebox.front.web.JukeboxWidgetset")
public class MainUI extends UI {

    @Override
    protected void init(VaadinRequest vaadinRequest) {
    	this.setNavigator(new Navigator(this, this));
		this.getNavigator().addView(LoginView.getName(), new LoginView());
		this.getNavigator().addView(LoggedInView.getName(), new LoggedInView());

		// User is always redirected to the login view if the user is not logged
		// in
		this.getNavigator().addViewChangeListener(
				new LoggedInViewChangeListener(this));

    }

    @WebServlet(urlPatterns = "/*", name = "MainUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = MainUI.class, productionMode = false)
    public static class MainUIServlet extends VaadinServlet {
    }
}
