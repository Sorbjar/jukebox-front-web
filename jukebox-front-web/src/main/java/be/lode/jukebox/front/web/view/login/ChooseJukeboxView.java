package be.lode.jukebox.front.web.view.login;

import be.lode.jukebox.front.web.view.general.MainLayout;
import be.lode.jukebox.service.dto.AccountDTO;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.CustomComponent;

public class ChooseJukeboxView extends CustomComponent implements View {
	private static final long serialVersionUID = 8079850607581069102L;

	private static final String NAME = "";
	AccountDTO acc;

	public ChooseJukeboxView() {
		super();
		acc = (AccountDTO) VaadinSession.getCurrent().getAttribute("user");
		MainLayout vl = new MainLayout(acc);
		setCompositionRoot(vl);

		this.markAsDirty();

	}

	@Override
	public void enter(ViewChangeEvent event) {
		
		acc = (AccountDTO) VaadinSession.getCurrent().getAttribute("user");
		this.markAsDirty();
	}

	public static String getName() {
		return NAME;
	}
}