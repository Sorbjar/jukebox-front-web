package be.lode.jukebox.front.web.view.chooseJukebox;

import be.lode.jukebox.front.web.view.general.MainLayout;
import be.lode.jukebox.service.dto.AccountDTO;
import be.lode.jukebox.service.dto.JukeboxDTO;
import be.lode.jukebox.service.manager.JukeboxManager;

import com.vaadin.data.Container;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Table;

public class ChooseJukeboxView extends CustomComponent implements View {
	private static final long serialVersionUID = 8079850607581069102L;

	private static final String NAME = "";
	private AccountDTO acc;
	private MainLayout layout;
	private Table jukeboxTable;
	private JukeboxManager mgr;

	public ChooseJukeboxView(JukeboxManager mgr) {
		super();
		this.acc = (AccountDTO) VaadinSession.getCurrent().getAttribute("user");
		this.mgr = mgr;
		init();
	}

	private void init() {
		layout = new MainLayout(acc);

		layout.addComponent(jukeboxTable);
		setupJukeboxTable();
		setCompositionRoot(layout);
		this.markAsDirty();
	}

	private void setupJukeboxTable() {
		jukeboxTable = new Table("Your jukeboxes", generateTableContent());
		jukeboxTable.setPageLength(0);

		jukeboxTable.setSelectable(true);
		jukeboxTable.setMultiSelect(false);
		jukeboxTable.setImmediate(true);
		// TODO Choose columns
		// TODO click handler for selected jukebox
		/*
		 * // Handle selection changes table.addValueChangeListener(event -> {
		 * // Java 8 if (event.getProperty().getValue() != null)
		 * layout.addComponent(new Label("Selected item " +
		 * event.getProperty().getValue().toString())); else // Item deselected
		 * layout.addComponent(new Label("Nothing selected")); });
		 */

	}

	private Container generateTableContent() {
		BeanItemContainer<JukeboxDTO> cont = new BeanItemContainer<JukeboxDTO>(
				JukeboxDTO.class);
		cont.addAll(mgr.getJukeboxes(acc));
		return cont;
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