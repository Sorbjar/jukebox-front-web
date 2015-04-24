package be.lode.jukebox.front.web.view.general;

import com.vaadin.ui.Label;

public class ErrorLabel extends Label {
	private static final long serialVersionUID = 4204283318820555957L;

	public ErrorLabel(String content) {
		super(content);
		this.setStyleName("errorlabel");
	}

}
