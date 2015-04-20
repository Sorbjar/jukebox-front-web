package be.lode.jukebox.front.web.view.jukeboxPlayer;

import java.util.Observable;
import java.util.Observer;

import be.lode.jukebox.front.web.view.general.JukeboxCustomComponent;
import be.lode.jukebox.front.web.view.general.MainLayout;
import be.lode.jukebox.service.UpdateArgs;
import be.lode.jukebox.service.dto.PlaylistDTO;
import be.lode.jukebox.service.dto.SongDTO;
import be.lode.jukebox.service.manager.JukeboxManager;

import com.vaadin.data.Container;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;

//TODO 200 break up in pieces
public class JukeboxPlayerView extends JukeboxCustomComponent implements View,
		Observer {
	private static final String NAME = "JukeboxPlayer";
	private static final long serialVersionUID = -849898443596794108L;

	public static String getName() {
		return NAME;
	}

	private Label jukeboxNameLabel;
	private MainLayout ml;
	private Label playListNameLabel;
	private Table playlistSongTable;
	private Table playlistTable;
	private Table songLibraryTable;

	public JukeboxPlayerView() {
		super();
		init();
	}

	@Override
	public void attach() {
		super.attach();
		getMainUI().getJukeboxManager().addObserver(this);
		update();
	}

	@Override
	public void enter(ViewChangeEvent event) {
		update();
	}

	@Override
	public void update(Observable o, Object arg) {
		if (arg.equals(UpdateArgs.CURRENT_JUKEBOX)) {
			updateCurrentJukebox();
			updateCurrentPlaylist();
		}
		if (arg.equals(UpdateArgs.CURRENT_PLAYLIST)) {
			updateCurrentPlaylist();
		}
	}

	private Container generatePlaylistSongTableContent() {
		BeanItemContainer<SongDTO> cont = new BeanItemContainer<SongDTO>(
				SongDTO.class);
		if (isAccountLoggedIn() && getMainUI() != null) {
			JukeboxManager mgr = getJukeboxManager();
			cont.addAll(mgr.getSongs(mgr.getCurrentPlaylistDTO()));
		}
		return cont;
	}

	private Container generatePlaylistTableContent() {
		BeanItemContainer<PlaylistDTO> cont = new BeanItemContainer<PlaylistDTO>(
				PlaylistDTO.class);
		if (isAccountLoggedIn() && getMainUI() != null) {
			JukeboxManager mgr = getJukeboxManager();
			cont.addAll(mgr.getSavedPlaylists(mgr.getCurrentJukeboxDTO()));
		}
		return cont;
	}

	private Container generateSongLibraryTableContent() {
		BeanItemContainer<SongDTO> cont = new BeanItemContainer<SongDTO>(
				SongDTO.class);
		if (isAccountLoggedIn() && getMainUI() != null) {
			JukeboxManager mgr = getJukeboxManager();
			cont.addAll(mgr.getAllSongs());
		}
		return cont;
	}

	private void init() {
		Panel jukeboxPlaylistPanel = setupJukeboxPlaylistpanel();

		Panel songTablePanel = setupSongTablepanel();

		Panel currentPlaylistSongpanel = setupCurrentPlaylistSongpanel();

		Panel musicButtonPanel = new Panel("Music button panel");
		// musicButtonPanel.setSizeFull();

		HorizontalLayout topLayout = new HorizontalLayout();
		topLayout.addComponent(currentPlaylistSongpanel);
		topLayout.addComponent(songTablePanel);
		topLayout.addComponent(jukeboxPlaylistPanel);
		topLayout.setSizeFull();

		HorizontalLayout bottomLayout = new HorizontalLayout();
		bottomLayout.setSizeFull();
		bottomLayout.addComponent(musicButtonPanel);

		VerticalLayout vl = new VerticalLayout();
		vl.setSizeFull();
		vl.addComponent(topLayout);
		vl.addComponent(bottomLayout);

		ml = new MainLayout();
		ml.addComponentToContainer(vl);
		this.setCompositionRoot(ml);

	}

	private Panel setupCurrentPlaylistSongpanel() {

		jukeboxNameLabel = new Label();
		// TODO 600 change button to crotchet
		// TODO 200 add listener
		Button editJukeboxButton = new Button("Edit");

		HorizontalLayout jukeboxNameLayout = new HorizontalLayout();
		jukeboxNameLayout.addComponent(jukeboxNameLabel);
		jukeboxNameLayout.addComponent(editJukeboxButton);
		jukeboxNameLayout.setComponentAlignment(editJukeboxButton,
				Alignment.MIDDLE_RIGHT);

		playlistTable = new Table();
		updatePlaylistTable();
		playlistTable.setPageLength(20);
		playlistTable.setSelectable(true);
		playlistTable.setMultiSelect(false);
		playlistTable.setImmediate(true);
		playlistTable.setWidth(200, Unit.PIXELS);
		playlistTable.addItemClickListener(event -> {
			if (event.isDoubleClick() && event.getItemId() != null) {
				JukeboxManager mgr = getJukeboxManager();
				if (mgr != null)
					event.getItem();
				mgr.setCurrentPlaylist((PlaylistDTO) event.getItemId());
			}
		});

		VerticalLayout currentJukeboxLayout = new VerticalLayout();
		currentJukeboxLayout.addComponent(jukeboxNameLayout);
		currentJukeboxLayout.addComponent(playlistTable);

		Panel p = new Panel("Jukebox playlist panel");
		p.setContent(currentJukeboxLayout);
		return p;

	}

	private Panel setupJukeboxPlaylistpanel() {
		playListNameLabel = new Label();
		// TODO 600 change button to disk icon
		// TODO 200 add listener
		Button savePlaylistButton = new Button("Save");

		HorizontalLayout playlistNameLayout = new HorizontalLayout();
		playlistNameLayout.addComponent(playListNameLabel);
		playlistNameLayout.addComponent(savePlaylistButton);
		playlistNameLayout.setComponentAlignment(savePlaylistButton,
				Alignment.MIDDLE_RIGHT);

		playlistSongTable = new Table();
		updatePlaylistSongTable();
		playlistSongTable.setPageLength(20);
		playlistSongTable.setSelectable(true);
		playlistSongTable.setMultiSelect(false);
		playlistSongTable.setImmediate(true);
		playlistSongTable.setWidth(200, Unit.PIXELS);
		playlistSongTable.setSortEnabled(false);
		// TODO clicklistener
		// TODO change order

		VerticalLayout currentPlaylistLayout = new VerticalLayout();
		currentPlaylistLayout.addComponent(playlistNameLayout);
		currentPlaylistLayout.addComponent(playlistSongTable);

		Panel p = new Panel("Playlist song panel");
		p.setContent(currentPlaylistLayout);
		return p;
	}

	private Panel setupSongTablepanel() {
		// TODO 600 change button to crotchet
		// TODO 200 add listener
		Button syncLibraryButton = new Button("Sync");

		HorizontalLayout syncLayout = new HorizontalLayout();
		syncLayout.addComponent(syncLibraryButton);
		syncLayout.setComponentAlignment(syncLibraryButton,
				Alignment.MIDDLE_RIGHT);

		songLibraryTable = new Table();
		updateSongLibraryTable();
		songLibraryTable.setPageLength(20);
		songLibraryTable.setSelectable(true);
		songLibraryTable.setMultiSelect(true);
		songLibraryTable.setImmediate(true);
		songLibraryTable.setWidth(200, Unit.PIXELS);
		// TODO add drag and drop listener, to currentplaylisttable
		// TODO add doublce click

		VerticalLayout songPanelLayout = new VerticalLayout();
		songPanelLayout.addComponent(songLibraryTable);
		songPanelLayout.addComponent(syncLayout);

		Panel p = new Panel("Song panel");
		p.setContent(songPanelLayout);
		return p;
	}

	private void update() {
		ml.update();
		updateCurrentJukebox();
		updateCurrentPlaylist();
		updateSongLibraryTable();
		// TODO 100 update
	}

	private void updateCurrentJukebox() {
		updateJukeboxName();
		updatePlaylistTable();
	}

	private void updateCurrentPlaylist() {
		updatePlaylistName();
		updatePlaylistSongTable();
	}

	private void updateJukeboxName() {
		if (getJukeboxManager() != null
				&& getJukeboxManager().getCurrentJukeboxDTO().getName() != null)
			jukeboxNameLabel.setValue(getJukeboxManager()
					.getCurrentJukeboxDTO().getName());
		else
			jukeboxNameLabel.setValue("");

	}

	private void updatePlaylistName() {
		if (getJukeboxManager() != null
				&& getJukeboxManager().getCurrentPlaylistDTO().getName() != null)
			playListNameLabel.setValue(getJukeboxManager()
					.getCurrentPlaylistDTO().getName());
		else
			playListNameLabel.setValue("");
	}

	private void updatePlaylistSongTable() {
		// TODO 010 set tostring as table
		playlistSongTable
				.setContainerDataSource(generatePlaylistSongTableContent());
		playlistSongTable.setVisibleColumns(new Object[] { "title" });
		playlistSongTable.setColumnHeaders("Songs");

	}

	private void updatePlaylistTable() {
		playlistTable.setContainerDataSource(generatePlaylistTableContent());
		playlistTable.setVisibleColumns(new Object[] { "name" });
		playlistTable.setColumnHeaders("Playlists");
	}

	private void updateSongLibraryTable() {
		//TODO setup correct columns
		songLibraryTable
				.setContainerDataSource(generateSongLibraryTableContent());

	}

}
