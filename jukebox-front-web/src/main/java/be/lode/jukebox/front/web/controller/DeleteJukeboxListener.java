package be.lode.jukebox.front.web.controller;

import org.vaadin.dialogs.ConfirmDialog;

import be.lode.jukebox.front.web.view.MainUI;
import be.lode.jukebox.front.web.view.VaadinSessionManager;
import be.lode.jukebox.front.web.view.chooseJukebox.ChooseJukeboxView;
import be.lode.jukebox.service.dto.JukeboxDTO;

import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;

public class DeleteJukeboxListener implements ClickListener {
	private static final long serialVersionUID = -7789634809435880360L;
	private ChooseJukeboxView chooseJukeboxView;

	public DeleteJukeboxListener(ChooseJukeboxView chooseJukeboxView) {
		super();
		this.chooseJukeboxView = chooseJukeboxView;
	}

	@Override
	public void buttonClick(ClickEvent event) {
		JukeboxDTO jbDto = chooseJukeboxView.getSelectedJukebox();
		if (VaadinSessionManager.getMainUI() != null) {
			MainUI mainUI = VaadinSessionManager.getMainUI();
			// The quickest way to confirm
			//TODO solve
			ConfirmDialog.show(this, repeat(MESSAGE_1),
			        new ConfirmDialog.Listener() {

			            public void onClose(ConfirmDialog dialog) {
			                if (dialog.isConfirmed()) {
			                    // Confirmed to continue
			                    feedback(dialog.isConfirmed());
			                } else {
			                    // User did not confirm
			                    feedback(dialog.isConfirmed());
			                }
			            }
			        });
			// TODO ask confirmation
			// TODO delete
			// TODO update table
		}

	}

}
