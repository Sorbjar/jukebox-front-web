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
			ConfirmDialog.show(mainUI, "Confirm delete",
					"Are you sure you wish to delete: "
							+ chooseJukeboxView.getSelectedJukebox().getName(),
					"Yes", "No", new ConfirmDialog.Listener() {
						private static final long serialVersionUID = -8020518088629472261L;

						public void onClose(ConfirmDialog dialog) {
							if (dialog.isConfirmed()) {
								mainUI.getJukeboxManager().deleteJukebox(jbDto);
							}
						}
					});
		}

	}

}
