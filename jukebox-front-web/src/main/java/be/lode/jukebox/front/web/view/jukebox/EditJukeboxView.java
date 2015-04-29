package be.lode.jukebox.front.web.view.jukebox;

import java.util.ArrayList;

import be.lode.jukebox.front.web.controller.CancelListener;
import be.lode.jukebox.front.web.controller.SaveJukeboxListener;
import be.lode.jukebox.front.web.view.general.ErrorLabel;
import be.lode.jukebox.front.web.view.general.JukeboxCustomComponent;
import be.lode.jukebox.front.web.view.general.MainLayout;
import be.lode.jukebox.service.dto.JukeboxDTO;

import com.vaadin.data.validator.NullValidator;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Button;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

public class EditJukeboxView extends JukeboxCustomComponent implements View {
	private static final String NAME = "EditJukebox";
	private static final long serialVersionUID = 5356689012928107750L;

	public static String getName() {
		return NAME;
	}

	private CancelListener cancelListener;
	private VerticalLayout errorMessageLayout;
	private MainLayout ml;
	private TextField nameTF;

	public EditJukeboxView() {
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

	public TextField getNameTF() {
		return nameTF;
	}

	public void showErrors(ArrayList<String> errors) {
		errorMessageLayout.removeAllComponents();
		for (String error : errors) {
			errorMessageLayout.addComponent(new ErrorLabel(error));
		}
	}

	public void update() {
		ml.update();
		fillFields();
		errorMessageLayout.removeAllComponents();
	}

	private void fillFields() {
		if (getJukeboxManager() != null
				&& getJukeboxManager().getCurrentJukeboxDTO() != null) {
			JukeboxDTO currentJukeboxDTO = getJukeboxManager()
					.getCurrentJukeboxDTO();
			nameTF.setValue(currentJukeboxDTO.getName());
		}
	}

	private void init() {
		// TODO 800 sql injection
		// TODO 750 validate on back end
		// TODO 750 validate on front end
		FormLayout form = new FormLayout();

		nameTF = new TextField("Jukebox name");
		nameTF.setRequired(true);
		nameTF.setImmediate(true);
		nameTF.addValidator(new NullValidator("Cannot be empty!", false));
		form.addComponent(nameTF);

		HorizontalLayout buttonLayout = new HorizontalLayout();
		Button saveButton = new Button("Save");
		saveButton.addClickListener(new SaveJukeboxListener(this));
		saveButton.setClickShortcut(KeyCode.ENTER);
		Button cancelButton = new Button("Cancel");
		cancelListener = new CancelListener(getName());
		cancelButton.addClickListener(cancelListener);
		buttonLayout.addComponent(saveButton);
		buttonLayout.addComponent(cancelButton);

		errorMessageLayout = new VerticalLayout();

		VerticalLayout vl = new VerticalLayout();
		vl.addComponent(form);
		vl.addComponent(buttonLayout);
		vl.addComponent(errorMessageLayout);

		ml = new MainLayout();
		ml.addComponentToContainer(vl);
		this.setCompositionRoot(ml);
	}
}
