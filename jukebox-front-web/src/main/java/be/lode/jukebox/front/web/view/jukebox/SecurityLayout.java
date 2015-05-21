package be.lode.jukebox.front.web.view.jukebox;

import be.lode.jukebox.service.dto.SecurityAccountDTO;
import be.lode.jukebox.service.manager.JukeboxManager;

import com.vaadin.data.Container;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.shared.ui.combobox.FilteringMode;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;

public class SecurityLayout extends VerticalLayout {
	private static final long serialVersionUID = -7586412676903468219L;
	private Table securityTable;
	private EditJukeboxView parent;

	public SecurityLayout(EditJukeboxView parent) {
		super();
		this.parent = parent;
		init();
	}

	private void init() {
		createSecurityTable();
	}

	private void createSecurityTable() {
		securityTable = new Table();
		updateSecurityTable();
		this.addComponent(securityTable);
	}

	private void updateSecurityTable() {
		securityTable.setContainerDataSource(generateSecurityTableContent());

		securityTable.setVisibleColumns(new Object[] { "fullName",
				"emailAddress", });
		securityTable.setColumnHeader("fullName", "Name");
		securityTable.setColumnHeader("emailAddress", "Email");
		securityTable.addGeneratedColumn("Role", new Table.ColumnGenerator() {
			private static final long serialVersionUID = 8506780937035396964L;

			@Override
			public Object generateCell(Table source, Object itemId,
					Object columnId) {
				ComboBox cbox = new ComboBox();
				cbox.setFilteringMode(FilteringMode.CONTAINS);
				cbox.addItems(parent.getJukeboxManager().getRoleList());
				cbox.setNullSelectionAllowed(false);
				cbox.setValue(((SecurityAccountDTO) itemId).getRole());
				return cbox;
			}
		});
		// TODO 100 action delete
		// TODO 100 delete all customers
		// TODO 100 react to combobox change

		securityTable.setColumnCollapsingAllowed(false);
		securityTable.setColumnReorderingAllowed(false);
		securityTable.setImmediate(true);

	}

	private Container generateSecurityTableContent() {
		BeanItemContainer<SecurityAccountDTO> cont = new BeanItemContainer<SecurityAccountDTO>(
				SecurityAccountDTO.class);
		if (parent.isAccountLoggedIn() && parent.getMainUI() != null) {
			JukeboxManager mgr = parent.getJukeboxManager();
			cont.addAll(mgr.getAllSecurityAccounts());
		}
		return cont;
	}

}
