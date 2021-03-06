package be.lode.jukebox.front.web.view.general;

import be.lode.jukebox.front.web.controller.ChooseJukeboxCommand;
import be.lode.jukebox.front.web.controller.EditProfileCommand;
import be.lode.jukebox.front.web.controller.LogoutCommand;
import be.lode.jukebox.front.web.view.VaadinSessionManager;
import be.lode.jukebox.service.dto.AccountDTO;

import com.vaadin.server.ThemeResource;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.MenuBar.MenuItem;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;

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
		}

		hl.setSizeFull();

	}

	private MenuBar getMenuBar() {
		MenuBar ret = new MenuBar();
		AccountDTO acc = VaadinSessionManager.getLoggedInAccount();
		MenuItem userItem = ret.addItem(acc.getFirstName(), null);
		MenuItem editProfileItem = userItem.addItem("Edit profile", null);
		editProfileItem.setCommand(new EditProfileCommand());
		MenuItem chooseJukebox = userItem.addItem("Choose jukebox", null);
		chooseJukebox.setCommand(new ChooseJukeboxCommand());
		MenuItem logoutItem = userItem.addItem("Logout", null);
		logoutItem.setCommand(new LogoutCommand());
		return ret;
	}

	private void init() {
		hl = new HorizontalLayout();
		Panel headerBar = new Panel();
		headerBar.setContent(hl);
		headerBar.setStyleName("headerbar");
		headerBar.setWidth(100, Unit.PERCENTAGE);
		this.addComponent(headerBar);
		container = new VerticalLayout();
		container.setSizeFull();
		container.setWidth(100, Unit.PERCENTAGE);
		this.addComponent(container);
	}

	private void setJukeboxLabel() {

		ThemeResource resource = new ThemeResource("Images/icon_24x24.png");
		hl.setSpacing(false);
		Image img = new Image(null, resource);
		img.setStyleName("icon");

		HorizontalLayout labelLayout = new HorizontalLayout();

		VerticalLayout vl = new VerticalLayout();
		vl.setHeight(100, Unit.PERCENTAGE);
		vl.setWidth(26, Unit.PIXELS);
		vl.addComponent(img);
		vl.setComponentAlignment(img, Alignment.MIDDLE_LEFT);

		Label jbLabel = new Label("Nyimbox");

		labelLayout.addComponent(vl);
		labelLayout.setComponentAlignment(vl, Alignment.MIDDLE_LEFT);
		labelLayout.addComponent(jbLabel);
		labelLayout.setComponentAlignment(jbLabel, Alignment.MIDDLE_LEFT);

		hl.addComponent(labelLayout);
		hl.setComponentAlignment(labelLayout, Alignment.MIDDLE_LEFT);

	}
}
