package be.lode.jukebox.front.web.view.chooseJukebox;

import java.util.Observable;
import java.util.Observer;

import be.lode.jukebox.front.web.controller.ManageJukeboxClickListener;
import be.lode.jukebox.front.web.controller.NewJukeboxButtonClickListener;
import be.lode.jukebox.front.web.view.general.MainLayout;
import be.lode.jukebox.service.UpdateArgs;
import be.lode.jukebox.service.dto.AccountDTO;
import be.lode.jukebox.service.dto.JukeboxDTO;
import be.lode.jukebox.service.manager.JukeboxManager;

import com.vaadin.data.Container;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Table;

//TODO prettify
public class ChooseJukeboxView extends CustomComponent implements View,
		Observer {
	private static final long serialVersionUID = 8079850607581069102L;

	private static final String NAME = "";
	private AccountDTO acc;
	private MainLayout layout;
	private Table jukeboxTable;
	private JukeboxManager mgr;
	private JukeboxDTO selectedJukebox;
	private Button deleteButton;
	private Button manageButton;

	public ChooseJukeboxView(JukeboxManager mgr) {
		super();
		this.acc = (AccountDTO) VaadinSession.getCurrent().getAttribute("user");
		this.mgr = mgr;
		init();
	}

	private void init() {
		layout = new MainLayout();
		setupJukeboxTable();
		layout.addComponent(jukeboxTable);
		layout.setComponentAlignment(jukeboxTable, Alignment.MIDDLE_CENTER);
		setupJukeboxTable();
		addNewJukeboxButton();
		addManageButton();
		addDeleteButton();
		setButtonsActiveStatus();
		setCompositionRoot(layout);
		this.markAsDirty();
	}

	private void addDeleteButton() {
		// TODO setup new delete listener
		deleteButton = new Button("Delete jukebox");
		layout.addComponent(deleteButton);
	}

	private void addManageButton() {
		// TODO setup new manage listener
		manageButton = new Button("Manage jukebox");
		manageButton.addClickListener(new ManageJukeboxClickListener());
		layout.addComponent(manageButton);
	}

	private void addNewJukeboxButton() {
		// TODO setup new jukeboxbutton listener
		Button newJukeboxButton = new Button("New jukebox");
		layout.addComponent(newJukeboxButton);
		newJukeboxButton.addClickListener(new NewJukeboxButtonClickListener());
	}

	private void setupJukeboxTable() {
		// TODO administrator jukeboxes in bold
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

	private Container generateTableContent() {
		BeanItemContainer<JukeboxDTO> cont = new BeanItemContainer<JukeboxDTO>(
				JukeboxDTO.class);
		cont.addAll(mgr.getJukeboxes(acc));
		return cont;
	}

	@Override
	public void attach() {
		super.attach();
		init();
		markAsDirty();
	}

	@Override
	public void enter(ViewChangeEvent event) {
		init();
		acc = (AccountDTO) VaadinSession.getCurrent().getAttribute("user");
		update();
		this.markAsDirty();
	}

	public static String getName() {
		return NAME;
	}

	@Override
	public void update(Observable o, Object arg) {
		if (UpdateArgs.CURRENT_ACCOUNT.equals(arg))
			update();
	}

	public void update() {
		jukeboxTable.setContainerDataSource(generateTableContent());
	}
}