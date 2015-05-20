package be.lode.jukebox.front.web.view.account;

import java.util.ArrayList;

import be.lode.jukebox.front.web.controller.CancelListener;
import be.lode.jukebox.front.web.controller.SaveAccountListener;
import be.lode.jukebox.front.web.view.MainUI;
import be.lode.jukebox.front.web.view.VaadinSessionManager;
import be.lode.jukebox.front.web.view.general.ErrorLabel;
import be.lode.jukebox.front.web.view.general.MainLayout;
import be.lode.jukebox.service.dto.AccountDTO;

import com.vaadin.data.validator.EmailValidator;
import com.vaadin.data.validator.NullValidator;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

public class EditAccountView extends CustomComponent implements View {
	private static final String NAME = "EditAccount";
	private static final long serialVersionUID = 5356689012928107750L;

	public static String getName() {
		return NAME;
	}

	private AccountDTO currentAccount;

	public AccountDTO getCurrentAccount() {
		return currentAccount;
	}

	private MainLayout ml;
	private TextField firstNameTF;
	private TextField lastNameTF;
	private TextField emailTF;
	private CancelListener cancelListener;
	private VerticalLayout errorMessageLayout;

	public EditAccountView() {
		super();
		init();
	}

	private void init() {

		Label titleLabel = new Label("Edit account");
		titleLabel.setStyleName("titlelabel");

		FormLayout form = new FormLayout();

		firstNameTF = new TextField("First name");
		firstNameTF.setRequired(true);
		firstNameTF.setImmediate(true);
		firstNameTF.addValidator(new NullValidator("Cannot be empty!", false));
		form.addComponent(firstNameTF);

		lastNameTF = new TextField("Last name");
		lastNameTF.setRequired(true);
		lastNameTF.addValidator(new NullValidator("Cannot be empty!", false));
		form.addComponent(lastNameTF);

		emailTF = new TextField("Email address");
		emailTF.setRequired(true);
		emailTF.addValidator(new NullValidator("Cannot be empty!", false));
		emailTF.addValidator(new EmailValidator("Must be a valid email address"));
		form.addComponent(emailTF);

		HorizontalLayout buttonLayout = new HorizontalLayout();
		Button saveButton = new Button("Save");
		saveButton.addClickListener(new SaveAccountListener(this));
		saveButton.setClickShortcut(KeyCode.ENTER);
		Button cancelButton = new Button("Cancel");
		cancelButton.setClickShortcut(KeyCode.ESCAPE);
		cancelListener = new CancelListener(getName());
		cancelButton.addClickListener(cancelListener);
		buttonLayout.addComponent(saveButton);
		buttonLayout.addComponent(cancelButton);

		HorizontalLayout buttonContainer = new HorizontalLayout();
		buttonContainer.setWidth(100, Unit.PERCENTAGE);
		buttonContainer.addComponent(buttonLayout);
		buttonContainer
				.setComponentAlignment(buttonLayout, Alignment.TOP_RIGHT);

		errorMessageLayout = new VerticalLayout();

		VerticalLayout vl = new VerticalLayout();
		vl.setMargin(true);
		vl.setWidth(23, Unit.PERCENTAGE);
		vl.addComponent(titleLabel);
		vl.addComponent(form);
		vl.addComponent(buttonContainer);
		vl.setComponentAlignment(buttonContainer, Alignment.TOP_CENTER);
		vl.addComponent(errorMessageLayout);

		HorizontalLayout centerLayout = new HorizontalLayout();
		Panel centerPanel = new Panel();
		centerPanel.setStyleName("centerpanel");
		centerPanel.setSizeUndefined();
		centerPanel.setContent(vl);
		centerLayout.setWidth(100, Unit.PERCENTAGE);
		centerLayout.addComponent(centerPanel);
		centerLayout.setComponentAlignment(centerPanel, Alignment.TOP_CENTER);

		ml = new MainLayout();
		ml.addComponentToContainer(centerLayout);
		this.setCompositionRoot(ml);
	}

	public TextField getFirstNameTF() {
		return firstNameTF;
	}

	public TextField getLastNameTF() {
		return lastNameTF;
	}

	public TextField getEmailTF() {
		return emailTF;
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

	public void update() {
		MainUI ui = (MainUI) UI.getCurrent();
		currentAccount = ui.getJukeboxManager().getAccount(
				VaadinSessionManager.getLoggedInAccount());
		ml.update();
		fillFields();
		errorMessageLayout.removeAllComponents();
	}

	private void fillFields() {
		if (currentAccount != null)
			firstNameTF.setValue(currentAccount.getFirstName());
		lastNameTF.setValue(currentAccount.getLastName());
		emailTF.setValue(currentAccount.getEmailAddress());
	}

	public void showErrors(ArrayList<String> errors) {
		errorMessageLayout.removeAllComponents();
		for (String error : errors) {
			errorMessageLayout.addComponent(new ErrorLabel(error));
		}
	}
}
