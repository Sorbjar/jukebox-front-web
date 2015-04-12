package be.lode.jukebox.front.web.view.general;

import be.lode.jukebox.service.dto.AccountDTO;

import com.vaadin.ui.Alignment;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.MenuBar.MenuItem;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;

public class HeaderBar extends VerticalLayout {
	private static final long serialVersionUID = -6328436734391733513L;
	private AccountDTO acc;

	public HeaderBar() {
		super();
		this.acc = new AccountDTO();
		constructor();
	}

	public HeaderBar(AccountDTO acc) {
		super();
		this.acc = acc;
		constructor();
	}

	private void constructor() {
		Component barmenu = createMenuBar();

		HorizontalLayout hl = new HorizontalLayout();
		hl.setSizeFull();
		hl.addComponent(barmenu);
		hl.setComponentAlignment(barmenu, Alignment.MIDDLE_RIGHT);

		Panel content = new Panel();
		content.setContent(hl);
		content.setSizeFull();
		content.setStyleName("headerbar");
		this.addComponent(content);
	}

	private Component createMenuBar() {
		// TODO update menubar upon login
		if (acc != null) {
			MenuBar barmenu = new MenuBar();
			if (acc.getFirstName() != null) {
				MenuItem userItem = barmenu.addItem(acc.getFirstName(), null);
				//TODO edit profile command
				userItem.addItem("edit profile", null);
				//TODO logout command
				userItem.addItem("logout", null);
			}
			return barmenu;
		} else
		{
			FacebookButtonLayout btn = new FacebookButtonLayout();
			return btn;
		}
	}
}
