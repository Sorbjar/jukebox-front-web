package be.lode.jukebox.front.web.controller;

import java.util.ArrayList;

import be.lode.jukebox.front.web.view.jukebox.EditJukeboxView;
import be.lode.jukebox.service.dto.JukeboxDTO;
import be.lode.jukebox.service.manager.JukeboxManager;

import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;

public class SaveJukeboxListener implements ClickListener {
	private static final long serialVersionUID = -7126017894291458053L;
	private EditJukeboxView editJukeboxView;
	private ArrayList<String> errors;

	public SaveJukeboxListener(EditJukeboxView editJukeboxView) {
		this.editJukeboxView = editJukeboxView;
	}

	@Override
	public void buttonClick(ClickEvent event) {
		if (validate()) {
			JukeboxManager mgr = editJukeboxView.getJukeboxManager();
			JukeboxDTO dto = editJukeboxView.getJukeboxManager()
					.getCurrentJukeboxDTO();
			dto.setName(editJukeboxView.getNameTF().getValue());
			dto = mgr.save(dto);
			editJukeboxView.getMainUI().navigateBack(
					editJukeboxView.getClass().getName());
		} else {
			editJukeboxView.showErrors(errors);
		}
	}

	private boolean validate() {
		errors = new ArrayList<String>();
		if ("".equals(editJukeboxView.getNameTF().getValue())) {
			errors.add("Name cannot be empty!");
		}
		return errors.size() == 0;
	}
}
