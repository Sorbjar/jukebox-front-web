package be.lode.jukebox.front.web.view.login;

import be.lode.jukebox.front.web.controller.OAuthListenerJukebox;
import be.lode.jukebox.front.web.view.MainUI;
import be.lode.jukebox.front.web.view.general.MainLayout;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.ThemeResource;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.Layout;
import com.vaadin.ui.VerticalLayout;

public class LoginView extends CustomComponent implements View {
	private static final String NAME = "Login";
	private static final long serialVersionUID = 3684376934342087722L;

	public static String getName() {
		return NAME;
	}

	private FacebookButtonLayout fbBtnLayout;
	private MainLayout mainLayout;
	private OAuthListenerJukebox oauthListener;

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

		ThemeResource resource = new ThemeResource(
				"Images/portalpic_958x491.png");
		Image img = new Image(null, resource);

		fbBtnLayout = new FacebookButtonLayout();
		HorizontalLayout hl = new HorizontalLayout();
		hl.setSpacing(true);
		hl.addComponent(fbBtnLayout);
		hl.setComponentAlignment(fbBtnLayout, Alignment.TOP_RIGHT);

		VerticalLayout vl = new VerticalLayout();
		vl.addComponent(img);
		vl.addComponent(hl);
		vl.setComponentAlignment(hl, Alignment.TOP_RIGHT);
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