package be.lode.jukebox.front.web.view.jukeboxPlayer.parts;

import be.lode.jukebox.front.web.view.jukeboxPlayer.JukeboxPlayerView;
import be.lode.jukebox.service.dto.PlaylistDTO;
import be.lode.jukebox.service.manager.JukeboxManager;

import com.vaadin.data.Container;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;

//TODO 610 prettify
public class CurrentJukeboxPanel extends Panel {
	private static final long serialVersionUID = -2978450768039658888L;
	private Label jukeboxNameLabel;
	private JukeboxPlayerView parent;
	private Table playlistTable;

	public CurrentJukeboxPanel(JukeboxPlayerView parent) {
		super();
		this.parent = parent;
		init();
	}

	public void update() {
		updateJukeboxName();
		updatePlaylistTable();
	}

	private Container generatePlaylistTableContent() {
		BeanItemContainer<PlaylistDTO> cont = new BeanItemContainer<PlaylistDTO>(
				PlaylistDTO.class);
		if (parent.isAccountLoggedIn() && parent.getMainUI() != null) {
			JukeboxManager mgr = parent.getJukeboxManager();
			cont.addAll(mgr.getSavedPlaylists(mgr.getCurrentJukeboxDTO()));
		}
		return cont;
	}

	private void init() {
		jukeboxNameLabel = new Label();
		// TODO 600 change button to crotchet
		// TODO 200 add listener
		Button editJukeboxButton = new Button("Edit");

		HorizontalLayout jukeboxNameLayout = new HorizontalLayout();
		jukeboxNameLayout.setWidth(100, Unit.PERCENTAGE);
		jukeboxNameLayout.addComponent(jukeboxNameLabel);
		jukeboxNameLayout.addComponent(editJukeboxButton);
		jukeboxNameLayout.setComponentAlignment(editJukeboxButton,
				Alignment.BOTTOM_RIGHT);

		playlistTable = new Table();
		updatePlaylistTable();
		playlistTable.setPageLength(15);
		playlistTable.setSelectable(true);
		playlistTable.setMultiSelect(false);
		playlistTable.setImmediate(true);
		playlistTable.setWidth(100, Unit.PERCENTAGE);
		playlistTable.addItemClickListener(event -> {
			if (event.isDoubleClick() && event.getItemId() != null) {
				JukeboxManager mgr = parent.getJukeboxManager();
				if (mgr != null)
					event.getItem();
				mgr.setCurrentPlaylist((PlaylistDTO) event.getItemId());
			}
		});

		VerticalLayout currentJukeboxLayout = new VerticalLayout();
		currentJukeboxLayout.addComponent(jukeboxNameLayout);
		currentJukeboxLayout.addComponent(playlistTable);

		this.setContent(currentJukeboxLayout);
	}

	private void updateJukeboxName() {
		if (parent.getJukeboxManager() != null
				&& parent.getJukeboxManager().getCurrentJukeboxDTO().getName() != null)
			jukeboxNameLabel.setValue(parent.getJukeboxManager()
					.getCurrentJukeboxDTO().getName());
		else
			jukeboxNameLabel.setValue("");
	}

	private void updatePlaylistTable() {
		playlistTable.setContainerDataSource(generatePlaylistTableContent());
		playlistTable.setVisibleColumns(new Object[] { "name" });
		playlistTable.setColumnHeaders("Playlists");
	}
}
