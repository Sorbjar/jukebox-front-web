package be.lode.jukebox.front.web.view.login;

import be.lode.jukebox.front.web.controller.OAuthListenerJukebox;
import be.lode.jukebox.service.dto.OAuthApiInfoDTO;
import be.lode.jukebox.service.manager.OAuthApiInfoManager;
import be.lode.oauth.FacebookButton;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.Reindeer;

public class LoginView extends CustomComponent implements View {
	private static final long serialVersionUID = 3684376934342087722L;
	private static final String NAME = "Login View";

	public static String getName() {
		return NAME;
	}

	private OAuthApiInfoDTO oAuthApiInfoDTO;
	private FacebookButton btn;
	private OAuthListenerJukebox oauthListener;

	// https://vaadin.com/wiki/-/wiki/Main/Creating%20a%20simple%20login%20view
	public LoginView() {
		super();
		setSizeFull();
		OAuthApiInfoManager manager = new OAuthApiInfoManager();
		oAuthApiInfoDTO = manager.getOAuthApiInfo("Facebook");

		oauthListener = new OAuthListenerJukebox(this.getUI(), oAuthApiInfoDTO);
		btn = new FacebookButton("Login with facebook",
				oAuthApiInfoDTO.getApiKey(), oAuthApiInfoDTO.getApiSecret(),
				oauthListener);

		HorizontalLayout facebookButtonLayout = new HorizontalLayout();
		facebookButtonLayout.setSpacing(true);
		facebookButtonLayout.addComponent(btn);

		VerticalLayout fb = new VerticalLayout();
		fb.addComponent(facebookButtonLayout);
		fb.setSpacing(true);
		fb.setMargin(new MarginInfo(true, true, true, false));
		fb.setSizeUndefined();

		VerticalLayout viewLayout = new VerticalLayout(fb);
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
			oauthListener = new OAuthListenerJukebox(this.getUI(),
					oAuthApiInfoDTO);
		}
		btn.setAuthListener(oauthListener);
	};

	@Override
	public void enter(ViewChangeEvent event) {
		btn = new FacebookButton("Login with facebook",
				oAuthApiInfoDTO.getApiKey(), oAuthApiInfoDTO.getApiSecret(),
				new OAuthListenerJukebox(this.getUI(), oAuthApiInfoDTO));
	}

}