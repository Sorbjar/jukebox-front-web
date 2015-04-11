package be.lode.jukebox.front.web.view.general;

import com.vaadin.ui.Alignment;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.MenuBar.MenuItem;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;

public class HeaderBar extends VerticalLayout {
	private static final long serialVersionUID = -6328436734391733513L;

	public HeaderBar() {
		MenuBar barmenu = createMenuBar();

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

	private MenuBar createMenuBar() {
		MenuBar barmenu = new MenuBar();
		MenuItem userItem = barmenu.addItem("test", null);
		userItem.addItem("edit profile", null);
		userItem.addItem("logout", null);
		return barmenu;
	}

}
