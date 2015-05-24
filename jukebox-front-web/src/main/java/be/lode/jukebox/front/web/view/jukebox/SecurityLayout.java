package be.lode.jukebox.front.web.view.jukebox;

import org.vaadin.dialogs.ConfirmDialog;

import be.lode.jukebox.front.web.view.VaadinSessionManager;
import be.lode.jukebox.front.web.view.chooseJukebox.ChooseJukeboxView;
import be.lode.jukebox.service.dto.AccountDTO;
import be.lode.jukebox.service.dto.SecurityAccountDTO;
import be.lode.jukebox.service.manager.JukeboxManager;

import com.vaadin.data.Container;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.event.Action;
import com.vaadin.shared.ui.combobox.FilteringMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;

public class SecurityLayout extends VerticalLayout {
	private static final long serialVersionUID = -7586412676903468219L;
	private VerticalLayout addAccountContainer;
	private Table addAccountTable;
	private Panel mainPanel;
	private VerticalLayout mainPanelLayout;
	private EditJukeboxView parent;
	private VerticalLayout securityContainer;
	private Table securityTable;
	private Label titleLabel;

	public SecurityLayout(EditJukeboxView parent) {
		super();
		this.parent = parent;
		init();
	}

	public void update() {
		this.updateAddAccountTable();
		this.updateSecurityTable();
	}

	private void addAddAccountActions() {
		final Action addAction = new Action("Add this account");

		addAccountTable.addActionHandler(new Action.Handler() {
			private static final long serialVersionUID = 1879059980213418192L;

			@Override
			public Action[] getActions(final Object target, final Object sender) {
				AccountDTO item = (AccountDTO) target;
				if (item != null) {
					return new Action[] { addAction };
				}
				return null;
			}

			@Override
			public void handleAction(final Action action, final Object sender,
					final Object target) {
				if (addAction == action) {
					AccountDTO toAdd = (AccountDTO) target;
					parent.getJukeboxManager().addAccount(toAdd);
					update();
					securityTable.setVisible(true);
					addAccountContainer.setVisible(false);
				}
				addAccountTable.markAsDirtyRecursive();
			}

		});

	}

	private void addSecurityActions() {
		final Action addAction = new Action("Add an account");
		final Action deleteAction = new Action("Delete account");

		securityTable.addActionHandler(new Action.Handler() {
			private static final long serialVersionUID = 7468376138899471634L;

			@Override
			public Action[] getActions(final Object target, final Object sender) {
				SecurityAccountDTO item = (SecurityAccountDTO) target;
				if (item != null) {
					return new Action[] { addAction, deleteAction };
				}
				return new Action[] { addAction };
			}

			@Override
			public void handleAction(final Action action, final Object sender,
					final Object target) {

				if (deleteAction == action) {
					if (parent.getJukeboxManager() != null) {
						SecurityAccountDTO toDelete = (SecurityAccountDTO) target;
						if (canRemove(toDelete)) {

							ConfirmDialog.show(parent.getMainUI(),
									"Confirm delete",
									"Are you sure you wish to delete: "
											+ toDelete.getFullName(), "Yes",
									"No", new ConfirmDialog.Listener() {
										private static final long serialVersionUID = 4106098936414046976L;

										public void onClose(ConfirmDialog dialog) {
											if (dialog.isConfirmed()) {
												parent.getJukeboxManager()
														.deleteAccount(toDelete);
												update();
												if (parent
														.getJukeboxManager()
														.isCurrentAccount(
																VaadinSessionManager
																		.getLoggedInAccount(),
																toDelete)) {
													parent.getJukeboxManager()
															.updateCurrentUser();
													parent.getMainUI()
															.navigateTo(
																	ChooseJukeboxView
																			.getName());
												}
											}
										}
									});
						}
					}
				} else if (addAction == action) {

					titleLabel.setValue("Add account ");
					securityTable.setVisible(false);
					addAccountContainer.setVisible(true);
				}
				securityTable.markAsDirtyRecursive();

			}

		});

	}

	private boolean canRemove(SecurityAccountDTO toDelete) {
		return parent.getJukeboxManager().canRemove(toDelete);
	}

	private void createAddAccountTable() {
		addAccountContainer = new VerticalLayout();
		addAccountTable = new Table();
		addAccountContainer.addComponent(addAccountTable);
		addAccountTable.addItemClickListener(event -> {
			if (event.isDoubleClick() && event.getItemId() != null) {
				AccountDTO toAdd = (AccountDTO) event.getItemId();
				parent.getJukeboxManager().addAccount(toAdd);
				update();
				securityTable.setVisible(true);
				addAccountContainer.setVisible(false);
			}
		});
		addAddAccountActions();
		updateAddAccountTable();

		Button backButton = new Button("Back");
		backButton.addClickListener(new ClickListener() {
			private static final long serialVersionUID = -4426923552502859188L;

			@Override
			public void buttonClick(ClickEvent event) {
				update();
				securityTable.setVisible(true);
				addAccountContainer.setVisible(false);
			}
		});
		addAccountContainer.setComponentAlignment(addAccountTable,
				Alignment.TOP_CENTER);
		addAccountContainer.addComponent(backButton);
		addAccountContainer.setComponentAlignment(backButton,
				Alignment.TOP_CENTER);
		mainPanelLayout.addComponent(addAccountContainer);
		mainPanelLayout.setComponentAlignment(addAccountContainer,
				Alignment.TOP_CENTER);
	}

	private void createSecurityTable() {
		securityContainer = new VerticalLayout();
		securityTable = new Table();
		securityContainer.addComponent(securityTable);
		addSecurityActions();
		updateSecurityTable();

		Button deleteAllCustomers = new Button("Delete all customers");
		deleteAllCustomers.addClickListener(new ClickListener() {
			private static final long serialVersionUID = -4426923552502859188L;

			@Override
			public void buttonClick(ClickEvent event) {
				ConfirmDialog.show(parent.getMainUI(), "Confirm delete",
						"Are you sure you wish to delete all customers?",
						"Yes", "No", new ConfirmDialog.Listener() {
							private static final long serialVersionUID = 4106098936414046976L;

							public void onClose(ConfirmDialog dialog) {
								if (dialog.isConfirmed()) {
									parent.getJukeboxManager()
											.removeAllCustomers();
									update();
									securityTable.setVisible(true);
									addAccountContainer.setVisible(false);
								}
							}
						});
			}
		});

		securityContainer.setComponentAlignment(securityTable,
				Alignment.TOP_CENTER);
		securityContainer.addComponent(deleteAllCustomers);
		securityContainer.setComponentAlignment(deleteAllCustomers,
				Alignment.TOP_CENTER);

		mainPanelLayout.addComponent(securityContainer);
		mainPanelLayout.setComponentAlignment(securityContainer,
				Alignment.TOP_CENTER);

	}

	private Container generateAddAccountTableContent() {
		BeanItemContainer<AccountDTO> cont = new BeanItemContainer<AccountDTO>(
				AccountDTO.class);
		if (parent.isAccountLoggedIn() && parent.getMainUI() != null) {
			JukeboxManager mgr = parent.getJukeboxManager();
			cont.addAll(mgr.getAllNonPermittedAccounts());
		}
		return cont;
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

	private void init() {
		mainPanel = new Panel();

		mainPanel.setSizeUndefined();
		mainPanelLayout = new VerticalLayout();

		titleLabel = new Label("Edit permissions");
		titleLabel.setValue("Edit permissions");
		titleLabel.setStyleName("titlelabel");

		mainPanelLayout.addComponent(titleLabel);
		mainPanel.setContent(mainPanelLayout);

		createSecurityTable();
		createAddAccountTable();
		securityTable.setVisible(true);
		addAccountContainer.setVisible(false);

		mainPanel.setStyleName("centerpanel");
		this.addComponent(mainPanel);
	}

	private void updateAddAccountTable() {
		addAccountTable
				.setContainerDataSource(generateAddAccountTableContent());

		addAccountTable.setVisibleColumns(new Object[] { "fullName",
				"emailAddress", });
		addAccountTable.setColumnHeader("fullName", "Name");
		addAccountTable.setColumnHeader("emailAddress", "Email");

		addAccountTable.setColumnCollapsingAllowed(false);
		addAccountTable.setColumnReorderingAllowed(false);

		addAccountTable.setPageLength(10);
		addAccountTable.setWidth(80, Unit.PERCENTAGE);
		addAccountTable.setImmediate(true);
	}

	private void updateSecurityTable() {
		securityTable.setContainerDataSource(generateSecurityTableContent());

		securityTable.setVisibleColumns(new Object[] { "fullName",
				"emailAddress", });
		securityTable.setColumnHeader("fullName", "Name");
		securityTable.setColumnHeader("emailAddress", "Email");
		securityTable.removeGeneratedColumn("Role");
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
				cbox.addValueChangeListener(new ValueChangeListener() {
					private static final long serialVersionUID = -1379342202553619087L;

					@Override
					public void valueChange(ValueChangeEvent event) {
						if (canRemove((SecurityAccountDTO) itemId)) {
							parent.getJukeboxManager().updateAccount(
									(SecurityAccountDTO) itemId,
									(String) cbox.getValue());
						} else {
							cbox.setValue(((SecurityAccountDTO) itemId)
									.getRole());
						}
					}
				});
				return cbox;
			}
		});

		securityTable.setWidth(80, Unit.PERCENTAGE);
		securityTable.setPageLength(10);
		securityTable.setColumnCollapsingAllowed(false);
		securityTable.setColumnReorderingAllowed(false);
		securityTable.setImmediate(true);

	}

}
