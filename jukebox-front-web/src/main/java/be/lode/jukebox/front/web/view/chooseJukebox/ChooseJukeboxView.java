package be.lode.jukebox.front.web.view.chooseJukebox;

import be.lode.jukebox.front.web.controller.DeleteJukeboxListener;
import be.lode.jukebox.front.web.controller.ManageJukeboxClickListener;
import be.lode.jukebox.front.web.controller.NewJukeboxButtonClickListener;
import be.lode.jukebox.front.web.view.VaadinSessionManager;
import be.lode.jukebox.front.web.view.general.MainLayout;
import be.lode.jukebox.service.dto.AccountDTO;
import be.lode.jukebox.service.dto.JukeboxDTO;
import be.lode.jukebox.service.manager.JukeboxManager;

import com.vaadin.data.Container;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Button;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;

//TODO prettify
public class ChooseJukeboxView extends CustomComponent implements View {

	private static final String NAME = "";
	private static final long serialVersionUID = -9756195884344605L;

	public static String getName() {
		return NAME;
	}

	private VerticalLayout buttonsLayout;
	private Button deleteButton;
	private Table jukeboxTable;
	private VerticalLayout jukeboxTableLayout;
	private Button manageButton;
	private MainLayout ml;
	private JukeboxDTO selectedJukebox;

	public JukeboxDTO getSelectedJukebox() {
		return selectedJukebox;
	}

	public ChooseJukeboxView() {
		super();
		init();
	}

	@Override
	public void attach() {
		super.attach();
		update();
	}

	@Override
	public void enter(ViewChangeEvent event) {
		update();
	}

	private void addDeleteButton() {
		// TODO setup new delete listener
		deleteButton = new Button("Delete jukebox");
		deleteButton.addClickListener(new DeleteJukeboxListener(this));
		buttonsLayout.addComponent(deleteButton);
	}

	private void addJukeboxTable() {
		jukeboxTable = new Table();
		jukeboxTable.setContainerDataSource(generateTableContent());
		jukeboxTable.setPageLength(20);
		jukeboxTable.setSelectable(true);
		jukeboxTable.setMultiSelect(false);
		jukeboxTable.setImmediate(true);
		jukeboxTable.setVisibleColumns(new Object[] { "name" });
		jukeboxTable.setColumnHeaders("Your jukeboxes");
		jukeboxTable.setWidth(1000, Unit.PIXELS);
		jukeboxTable.addValueChangeListener(event -> {
			if (event.getProperty().getValue() != null)
				selectedJukebox = (JukeboxDTO) event.getProperty().getValue();
			else
				selectedJukebox = null;
			setButtonsActiveStatus();
		});
		jukeboxTableLayout.addComponent(jukeboxTable);

	}

	private void addManageButton() {
		manageButton = new Button("Manage jukebox");
		manageButton.addClickListener(new ManageJukeboxClickListener());
		buttonsLayout.addComponent(manageButton);
	}

	private void addNewJukeboxButton() {
		// TODO setup new jukeboxbutton listener
		Button newJukeboxButton = new Button("New jukebox");
		buttonsLayout.addComponent(newJukeboxButton);
		newJukeboxButton.addClickListener(new NewJukeboxButtonClickListener());
	}

	private Container generateTableContent() {
		BeanItemContainer<JukeboxDTO> cont = new BeanItemContainer<JukeboxDTO>(
				JukeboxDTO.class);
		if (VaadinSessionManager.isAccountLoggedIn()
				&& VaadinSessionManager.getMainUI() != null) {
			AccountDTO loggedInAccount = VaadinSessionManager.loggedInAccount();
			JukeboxManager mgr = VaadinSessionManager.getMainUI()
					.getJukeboxManager();
			loggedInAccount = mgr.getAccount(loggedInAccount);
			cont.addAll(mgr.getJukeboxes(loggedInAccount));
		}
		return cont;
	}

	private void init() {

		jukeboxTableLayout = new VerticalLayout();

		addJukeboxTable();

		buttonsLayout = new VerticalLayout();

		addNewJukeboxButton();
		buttonsLayout.addComponent(new Label());
		addManageButton();
		buttonsLayout.addComponent(new Label());
		addDeleteButton();

		HorizontalLayout hl = new HorizontalLayout();
		hl.addComponent(new Label());
		hl.addComponent(jukeboxTableLayout);
		hl.addComponent(new Label());
		hl.addComponent(buttonsLayout);
		hl.addComponent(new Label());

		VerticalLayout vl = new VerticalLayout();
		vl.addComponent(hl);

		ml = new MainLayout();
		ml.addComponentContainer(vl);
		this.setCompositionRoot(ml);

	}

	private void setButtonsActiveStatus() {
		if (selectedJukebox != null)
			setButtonsEnabled(true);
		else
			setButtonsEnabled(false);
	}

	private void setButtonsEnabled(boolean enabled) {
		manageButton.setEnabled(enabled);
		deleteButton.setEnabled(enabled);
	}

	private void update() {
		ml.update();
		jukeboxTable.setContainerDataSource(generateTableContent());
		jukeboxTable.setVisibleColumns(new Object[] { "name" });
		jukeboxTable.setColumnHeaders("Your jukeboxes");
		setButtonsActiveStatus();
	}
}