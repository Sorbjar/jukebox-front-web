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
import com.vaadin.server.BrowserWindowOpener;
import com.vaadin.server.StreamResource;
import com.vaadin.server.StreamResource.StreamSource;
import com.vaadin.shared.ui.combobox.FilteringMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
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
	private SecurityLayout sl;

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
		sl.update();
	}

	private void generateQR() {
		// TODO 800 multiple options QR code eg download app from marketplace,
		// open app, ....
		security.removeAllComponents();
		//security.addComponent(new Label());
		try {
			StreamResource.StreamSource imagesource = getJukeboxManager()
					.getQRStream(263, 263);
			StreamResource resource = new StreamResource(imagesource, "QR "
					+ getJukeboxManager().getCurrentJukeboxDTO().getName()
					+ ".png");

			Image qr = new Image("", resource);
			qr.setStyleName("qrcode");
			HorizontalLayout local = new HorizontalLayout();
			local.setWidth(100, Unit.PERCENTAGE);
			local.addComponent(qr);
			local.setComponentAlignment(qr, Alignment.TOP_CENTER);
			security.addComponent(local);
			security.setWidth(100, Unit.PERCENTAGE);
		} catch (NullPointerException e) {
			// do nothing
		}

		try {
			Button printQRButton = new Button("Print the QR code");
			BrowserWindowOpener opener = new BrowserWindowOpener(generatePDF());
			opener.extend(printQRButton);
			security.addComponent(printQRButton);
			security.setComponentAlignment(printQRButton, Alignment.TOP_CENTER);
		} catch (NullPointerException e) {
			// do nothing
		}
	}

	private StreamResource generatePDF() {
		StreamSource pdfSource = getJukeboxManager().getPDFStream();
		String pdfFilename = "QR "
				+ getJukeboxManager().getCurrentJukeboxDTO().getName() + ".pdf";
		StreamResource pdfresource = new StreamResource(pdfSource, pdfFilename);
		pdfresource.setMIMEType("application/pdf");
		pdfresource.getStream().setParameter("Content-Disposition",
				"attachment; filename=" + pdfFilename);
		return pdfresource;
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

		Label titleLabel = new Label("Edit jukebox");
		titleLabel.setStyleName("titlelabel");

		FormLayout form = new FormLayout();

		nameTF = new TextField("Jukebox name");
		nameTF.setRequired(true);
		nameTF.setImmediate(true);
		nameTF.addValidator(new NullValidator("Cannot be empty!", false));
		form.addComponent(nameTF);

		form.addComponent(new Label());
		Label paypalLabel = new Label("Payment information");
		paypalLabel.setStyleName("subtitlelabel");
		form.addComponent(paypalLabel);

		paymentEmailTF = new TextField("Paypal account email");
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

		HorizontalLayout buttonContainer = new HorizontalLayout();
		buttonContainer.setWidth(100, Unit.PERCENTAGE);
		buttonContainer.addComponent(buttonLayout);
		buttonContainer
				.setComponentAlignment(buttonLayout, Alignment.TOP_RIGHT);

		VerticalLayout vl = new VerticalLayout();
		vl.addComponent(form);
		vl.addComponent(errorMessageLayout);
		vl.setComponentAlignment(errorMessageLayout, Alignment.TOP_RIGHT);

		security = new VerticalLayout();
		security.setSpacing(true);

		generateQR();

		HorizontalLayout hl = new HorizontalLayout();
		hl.addComponent(vl);
		hl.addComponent(security);
		
		VerticalLayout vl1 = new VerticalLayout();
		vl1.addComponent(titleLabel);
		vl1.addComponent(hl);
		vl1.addComponent(buttonContainer);
		vl1.setComponentAlignment(buttonContainer, Alignment.TOP_CENTER);
		vl1.addComponent(new Label());

		
		sl = new SecurityLayout(this);
		vl1.addComponent(sl);
		vl1.setComponentAlignment(sl, Alignment.TOP_CENTER);
		
		
		
		HorizontalLayout centerLayout = new HorizontalLayout();
		Panel centerPanel = new Panel();
		centerPanel.setStyleName("centerpanel");
		centerPanel.setSizeUndefined();
		centerPanel.setContent(vl1);
		centerLayout.setWidth(100, Unit.PERCENTAGE);
		centerLayout.addComponent(centerPanel);
		centerLayout.setComponentAlignment(centerPanel, Alignment.TOP_CENTER);
		
		ml = new MainLayout();
		ml.addComponentToContainer(centerLayout);
		this.setCompositionRoot(ml);
	}
}
