package be.lode.jukebox.front.web.view.general;

import be.lode.jukebox.front.web.controller.EditProfileCommand;
import be.lode.jukebox.front.web.controller.LogoutCommand;
import be.lode.jukebox.front.web.view.VaadinSessionManager;
import be.lode.jukebox.service.dto.AccountDTO;

import com.vaadin.ui.Alignment;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.MenuBar.MenuItem;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;

//TODO 610 prettify
public class MainLayout extends VerticalLayout {
	private static final long serialVersionUID = 5257805110680869966L;
	// The container is what the application will interact with.
	// Every method that wants to add or alter or,... will be placed in the
	// container, make sure not to call the standard methods
	private VerticalLayout container;
	private HorizontalLayout hl;

	public MainLayout() {
		super();
		init();
	}

	public void addComponentToContainer(Component c) {
		container.addComponent(c);
	}

	@Override
	public void attach() {
		super.attach();
		update();
	}

	public void setComponentAlignmentContainer(Component childComponent,
			Alignment alignment) {
		container.setComponentAlignment(childComponent, alignment);
	}

	public void update() {
		hl.removeAllComponents();
		
		setJukeboxLabel();
		
		if (VaadinSessionManager.getLoggedInAccount() != null) {
			MenuBar menu = getMenuBar();
			hl.addComponent(menu);
			hl.setComponentAlignment(menu, Alignment.MIDDLE_RIGHT);
		} else {
			// TODO 700 else add facebook button
			// TODO 550 make sure the bar is not too wide
		}

		hl.setSizeFull();

	}

	private void setJukeboxLabel() {
		Label jbLabel = new Label("Lode's Jukebox");
		hl.addComponent(jbLabel);
		hl.setComponentAlignment(jbLabel, Alignment.MIDDLE_LEFT);
	}

	private MenuBar getMenuBar() {
		MenuBar ret = new MenuBar();
		AccountDTO acc = VaadinSessionManager.getLoggedInAccount();
		MenuItem userItem = ret.addItem(acc.getFirstName(), null);
		MenuItem editProfileItem = userItem.addItem("Edit Profile", null);
		editProfileItem.setCommand(new EditProfileCommand());
		MenuItem logoutItem = userItem.addItem("Logout", null);
		logoutItem.setCommand(new LogoutCommand());
		return ret;
	}

	private void init() {
		hl = new HorizontalLayout();
		hl.setSizeFull();
		Panel headerBar = new Panel();
		headerBar.setContent(hl);
		headerBar.setSizeFull();
		headerBar.setStyleName("headerbar");
		this.addComponent(headerBar);
		container = new VerticalLayout();
		container.setSizeFull();
		this.addComponent(container);
		this.setSizeFull();
	}
}
