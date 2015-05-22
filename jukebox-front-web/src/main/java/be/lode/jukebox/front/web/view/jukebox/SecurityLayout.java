package be.lode.jukebox.front.web.view.jukebox;

import org.vaadin.dialogs.ConfirmDialog;

import be.lode.jukebox.front.web.view.VaadinSessionManager;
import be.lode.jukebox.front.web.view.chooseJukebox.ChooseJukeboxView;
import be.lode.jukebox.service.dto.SecurityAccountDTO;
import be.lode.jukebox.service.manager.JukeboxManager;

import com.vaadin.data.Container;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.event.Action;
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
		addActions();
		updateSecurityTable();
		this.addComponent(securityTable);
	}

	private void addActions() {
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
												if (parent
														.getJukeboxManager()
														.isCurrentAccount(
																VaadinSessionManager
																		.getLoggedInAccount(),
																toDelete))
													parent.getMainUI()
															.navigateTo(
																	ChooseJukeboxView
																			.getName());
											}
										}
									});
						}
					}
				} else if (addAction == action) {
					// TODO 100 addAction
				}
				securityTable.markAsDirtyRecursive();

			}

		});

	}

	private boolean canRemove(SecurityAccountDTO toDelete) {
		return parent.getJukeboxManager().canRemove(toDelete);
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
				cbox.addValueChangeListener(new ValueChangeListener() {
					private static final long serialVersionUID = -1379342202553619087L;

					@Override
					public void valueChange(ValueChangeEvent event) {
						if (canRemove((SecurityAccountDTO) itemId)) {
							parent.getJukeboxManager().updateAccount(
									(SecurityAccountDTO) itemId,
									(String) cbox.getValue());
						}
						else
						{
							cbox.setValue(((SecurityAccountDTO) itemId).getRole());
						}
					}
				});
				return cbox;
			}
		});
		// TODO 100 delete all customers

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
