package be.lode.jukebox.front.web.view.login;

import be.lode.jukebox.front.web.controller.OAuthListenerJukebox;
import be.lode.jukebox.front.web.view.general.FacebookButtonLayout;
import be.lode.jukebox.front.web.view.general.MainLayout;
import be.lode.jukebox.service.manager.JukeboxManager;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.Reindeer;

public class LoginView extends CustomComponent implements View {
	private static final String NAME = "Login";
	private static final long serialVersionUID = 3684376934342087722L;

	public static String getName() {
		return NAME;
	}

	// private FacebookButton btn;
	private JukeboxManager mgr;
	private OAuthListenerJukebox oauthListener;
	private FacebookButtonLayout fbLayout;

	// https://vaadin.com/wiki/-/wiki/Main/Creating%20a%20simple%20login%20view
	public LoginView(JukeboxManager mgr) {
		super();
		this.mgr = mgr;
		setSizeFull();

		fbLayout = new FacebookButtonLayout();
		HorizontalLayout facebookButtonLayout = new HorizontalLayout();
		facebookButtonLayout.setSpacing(true);
		facebookButtonLayout.addComponent(fbLayout);

		VerticalLayout fb = new VerticalLayout();
		fb.addComponent(facebookButtonLayout);
		fb.setSpacing(true);
		fb.setMargin(new MarginInfo(true, true, true, false));
		fb.setSizeUndefined();

		MainLayout viewLayout = new MainLayout();
		viewLayout.addComponent(fb);
		viewLayout.setSizeFull();
		viewLayout.setComponentAlignment(fb, Alignment.MIDDLE_CENTER);
		viewLayout.setStyleName(Reindeer.LAYOUT_BLUE);
		setCompositionRoot(viewLayout);
	}

	public void attach() {
		super.attach();
		if (oauthListener != null) {
			oauthListener.setMainUI(this.getUI());
		} else {
			oauthListener = new OAuthListenerJukebox(this.getUI(), mgr);
		}
		fbLayout.setAuthListener(oauthListener);
	};

	@Override
	public void enter(ViewChangeEvent event) {
		fbLayout = new FacebookButtonLayout();
	}

}