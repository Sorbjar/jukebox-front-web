package be.lode.jukebox.front.web.view.general;

import com.vaadin.event.FieldEvents.BlurEvent;
import com.vaadin.event.FieldEvents.BlurListener;
import com.vaadin.event.LayoutEvents.LayoutClickEvent;
import com.vaadin.event.LayoutEvents.LayoutClickListener;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

public class EditLabel extends VerticalLayout {
	private static final long serialVersionUID = 1L;
	private String caption;
	private Label tempLabel;
	private TextField tempTF;

	public EditLabel()  {
		super();
		this.caption = "";
		init();
	}
	
	public EditLabel(String caption) {
		super();
		this.caption = caption;
		init();
	}

	private void init() {
		tempLabel = new Label(caption);
		tempTF = new TextField();

		this.addComponent(tempLabel);

		this.addLayoutClickListener(new LayoutClickListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void layoutClick(LayoutClickEvent event) {
				removeAllComponents();
				tempTF.setValue(caption);
				addComponent(tempTF);
				tempTF.addBlurListener(new BlurListener() {
					private static final long serialVersionUID = 1L;

					@Override
					public void blur(BlurEvent event) {
						caption = tempTF.getValue();
						removeAllComponents();
						tempLabel.setValue(caption);
						addComponent(tempLabel);
						fireEvent(event);
					}
				});
			}
		});
	}

	public void setValue(String name) {
		removeAllComponents();
		caption = name;
		tempLabel.setValue(caption);
		addComponent(tempLabel);
	}

	public String getValue() {
		return caption;
	}

	public void addBlurListener(BlurListener listener) {
		addListener(BlurEvent.EVENT_ID, BlurEvent.class, listener,
                BlurListener.blurMethod);
	}

}
