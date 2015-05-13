package be.lode.jukebox.front.web.view.jukebox;

import java.util.ArrayList;

import be.lode.jukebox.front.web.controller.CancelListener;
import be.lode.jukebox.front.web.controller.SaveJukeboxListener;
import be.lode.jukebox.front.web.view.general.ErrorLabel;
import be.lode.jukebox.front.web.view.general.JukeboxCustomComponent;
import be.lode.jukebox.front.web.view.general.MainLayout;
import be.lode.jukebox.service.dto.JukeboxDTO;
import be.lode.jukebox.service.dto.PayPalSettingsDTO;

import com.vaadin.data.validator.NullValidator;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.StreamResource;
import com.vaadin.shared.ui.combobox.FilteringMode;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
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
	private TextField paymentEmailTF;
	private ComboBox currencyCBox;
	private TextField pricePerSongTF;
	private VerticalLayout security;

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
		generateQR();
	}

	private void generateQR() {
		// TODO 200 print QR code
		// TODO 800 multiple options QR code eg download app from marketplace,
		// open app, ....
		security.removeAllComponents();
		StreamResource.StreamSource imagesource = getJukeboxManager()
				.getQRImage(450, 450);
		StreamResource resource = new StreamResource(imagesource,
				"JukeboxQR.png");
		Image qr = new Image("QR", resource);
		security.addComponent(qr);

		// TODO print QR code
		Button printQRButton = new Button("Print the QR code");
		security.addComponent(printQRButton);

		/*
		 * printQRButton.addClickListener(new ClickListener() {
		 * 
		 * @Override public void buttonClick(ClickEvent event) {
		 * StreamResource.StreamSource pdfImagesource = getJukeboxManager()
		 * .getQRImage(450, 450); StreamResource pdfResource = new
		 * StreamResource(pdfImagesource, "JukeboxQR.png"); Image img = new
		 * Image("QR", pdfResource); PDFStream pdfStream = new PDFStream();
		 * 
		 * }
		 * 
		 * });
		 */
	}

	private void fillFields() {
		currencyCBox.removeAllItems();
		currencyCBox.addItems(getCurrencyManager().getCurrencyList());
		currencyCBox.setNullSelectionAllowed(false);
		if (getJukeboxManager() != null
				&& getJukeboxManager().getCurrentJukeboxDTO() != null) {
			JukeboxDTO currentJukeboxDTO = getJukeboxManager()
					.getCurrentJukeboxDTO();
			PayPalSettingsDTO currentPayPalSettingsDTO = getJukeboxManager()
					.getCurrentPayPalSettingsDTO();
			nameTF.setValue(currentJukeboxDTO.getName());
			paymentEmailTF.setValue(currentPayPalSettingsDTO.getEmail());
			currencyCBox.setValue(getCurrencyManager().getCurrency(
					currentPayPalSettingsDTO.getPayPalCurrencyCode()));
			pricePerSongTF.setValue(currentPayPalSettingsDTO.getPricePerSong());
		}
	}

	public TextField getPaymentEmailTF() {
		return paymentEmailTF;
	}

	public ComboBox getCurrencyCBox() {
		return currencyCBox;
	}

	public TextField getPricePerSongTF() {
		return pricePerSongTF;
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

		Label paypalLabel = new Label("Payment information");
		form.addComponent(paypalLabel);

		paymentEmailTF = new TextField("Paypal account email");
		paymentEmailTF.setRequired(true);
		paymentEmailTF.setImmediate(true);
		paymentEmailTF
				.addValidator(new NullValidator("Cannot be empty!", false));
		form.addComponent(paymentEmailTF);

		currencyCBox = new ComboBox("Currency");
		currencyCBox.setFilteringMode(FilteringMode.CONTAINS);
		currencyCBox.addItems(getCurrencyManager().getCurrencyList());
		currencyCBox.setNullSelectionAllowed(false);
		form.addComponent(currencyCBox);

		pricePerSongTF = new TextField("Price per song");
		pricePerSongTF.setRequired(true);
		pricePerSongTF.setImmediate(true);
		pricePerSongTF
				.addValidator(new NullValidator("Cannot be empty!", false));

		pricePerSongTF.addStyleName("numerical");
		form.addComponent(pricePerSongTF);

		HorizontalLayout buttonLayout = new HorizontalLayout();
		Button saveButton = new Button("Save");
		saveButton.addClickListener(new SaveJukeboxListener(this));
		saveButton.setClickShortcut(KeyCode.ENTER);
		Button cancelButton = new Button("Cancel");
		cancelButton.setClickShortcut(KeyCode.ESCAPE);
		cancelListener = new CancelListener(getName());
		cancelButton.addClickListener(cancelListener);
		buttonLayout.addComponent(saveButton);
		buttonLayout.addComponent(cancelButton);

		errorMessageLayout = new VerticalLayout();

		VerticalLayout vl = new VerticalLayout();
		vl.addComponent(form);
		vl.addComponent(buttonLayout);
		vl.addComponent(errorMessageLayout);

		security = new VerticalLayout();

		generateQR();

		HorizontalLayout hl = new HorizontalLayout();
		hl.addComponent(vl);
		hl.addComponent(security);

		ml = new MainLayout();
		ml.addComponentToContainer(hl);
		this.setCompositionRoot(ml);
	}
}
