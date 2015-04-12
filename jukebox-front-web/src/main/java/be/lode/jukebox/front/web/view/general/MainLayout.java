package be.lode.jukebox.front.web.view.general;

import be.lode.jukebox.service.dto.AccountDTO;

import com.vaadin.ui.VerticalLayout;

public class MainLayout extends VerticalLayout {
	private static final long serialVersionUID = 5264699711112212722L;

	public MainLayout() {
		super();
		this.setSizeFull();
		this.addComponent(new HeaderBar());
	}

	public MainLayout(AccountDTO acc) {
		super();
		this.setSizeFull();
		this.addComponent(new HeaderBar(acc));
	}
}
