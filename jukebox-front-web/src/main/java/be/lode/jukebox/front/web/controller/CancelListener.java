package be.lode.jukebox.front.web.controller;

import be.lode.jukebox.front.web.view.VaadinSessionManager;

import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;

public class CancelListener implements ClickListener {
	private static final long serialVersionUID = 6545003499682551628L;

	@Override
	public void buttonClick(ClickEvent event) {
		VaadinSessionManager.getMainUI().navigateBack();
	}

}
