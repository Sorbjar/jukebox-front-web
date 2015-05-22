package be.lode.jukebox.front.web.view.login;

import be.lode.jukebox.front.web.controller.OAuthListenerJukebox;
import be.lode.jukebox.front.web.view.MainUI;
import be.lode.jukebox.front.web.view.general.MainLayout;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Layout;
import com.vaadin.ui.VerticalLayout;

//TODO 610 prettify
public class LoginView extends CustomComponent implements View {
	private static final String NAME = "Login";
	private static final long serialVersionUID = 3684376934342087722L;

	public static String getName() {
		return NAME;
	}

	private FacebookButtonLayout fbBtnLayout;
	private MainLayout mainLayout;
	private OAuthListenerJukebox oauthListener;

	// https://vaadin.com/wiki/-/wiki/Main/Creating%20a%20simple%20login%20view
	public LoginView() {
		super();
		init();
	}

	public void attach() {
		super.attach();
		update();

	}

	@Override
	public void enter(ViewChangeEvent event) {
		update();
	}

	private Layout getFacebookLayout() {

		fbBtnLayout = new FacebookButtonLayout();
		HorizontalLayout hl = new HorizontalLayout();
		hl.setSpacing(true);
		hl.addComponent(fbBtnLayout);

		VerticalLayout vl = new VerticalLayout();
		vl.addComponent(hl);
		vl.setSpacing(true);
		vl.setMargin(new MarginInfo(true, true, true, false));
		vl.setSizeUndefined();
		return vl;
	};

	private void init() {
		this.setSizeFull();

		Layout facebookLayout = getFacebookLayout();

		mainLayout = new MainLayout();

		mainLayout.addComponentToContainer(facebookLayout);
		mainLayout.setComponentAlignmentContainer(facebookLayout,
				Alignment.MIDDLE_CENTER);

		setCompositionRoot(mainLayout);
		update();

	}

	private void update() {
		mainLayout.update();
		if (this.getUI() != null) {
			if (oauthListener != null) {
				oauthListener.setMainUI((MainUI) this.getUI());
			} else {
				oauthListener = new OAuthListenerJukebox();
				oauthListener.setMainUI((MainUI) this.getUI());
			}
			fbBtnLayout.setAuthListener(oauthListener);
		}
	}

}