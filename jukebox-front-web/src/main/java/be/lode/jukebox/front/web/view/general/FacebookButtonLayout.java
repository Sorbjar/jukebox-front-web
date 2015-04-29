package be.lode.jukebox.front.web.view.general;

import be.lode.jukebox.front.web.controller.OAuthListenerJukebox;
import be.lode.jukebox.service.dto.OAuthApiInfoDTO;
import be.lode.jukebox.service.manager.OAuthApiInfoManager;
import be.lode.oauth.FacebookButton;

import com.vaadin.server.FontAwesome;
import com.vaadin.ui.VerticalLayout;

public class FacebookButtonLayout extends VerticalLayout {
	private static final long serialVersionUID = 6341978049030566583L;

	private OAuthApiInfoDTO oAuthApiInfoDTO;
	private OAuthListenerJukebox oauthListener;
	private FacebookButton btn;

	@Override
	public void attach() {
		super.attach();
		OAuthApiInfoManager OAuthManager = new OAuthApiInfoManager();
		oAuthApiInfoDTO = OAuthManager.getOAuthApiInfo("Facebook");

		btn = new FacebookButton("Facebook",
				oAuthApiInfoDTO.getApiKey(), oAuthApiInfoDTO.getApiSecret(),
				oauthListener);
		btn.setIcon(FontAwesome.FACEBOOK_SQUARE);
		btn.setStyleName("facebookbutton");
		this.addComponent(btn);
	}

	public void setAuthListener(OAuthListenerJukebox oauthListener) {
		btn.setAuthListener(oauthListener);
	}

}
