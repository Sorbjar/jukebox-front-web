package be.lode.jukebox.front.web.view.jukeboxPlayer.parts;

import be.lode.jukebox.front.web.view.jukeboxPlayer.JukeboxPlayerView;
import be.lode.jukebox.service.dto.SongDTO;
import be.lode.jukebox.service.manager.JukeboxManager;

import com.vaadin.data.Container;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Panel;
import com.vaadin.ui.Table;
import com.vaadin.ui.Table.TableDragMode;
import com.vaadin.ui.VerticalLayout;

public class LibraryPanel extends Panel {
	private static final long serialVersionUID = 1363346584892497032L;

	private Table libraryTable;
	private JukeboxPlayerView parent;

	public LibraryPanel(JukeboxPlayerView parent) {
		super();
		this.parent = parent;
		init();
	}

	public Table getLibraryTable() {
		return libraryTable;
	}

	public void update() {
		updateSongLibraryTable();
	}

	private Container generateLibraryTableContent() {
		BeanItemContainer<SongDTO> cont = new BeanItemContainer<SongDTO>(
				SongDTO.class);
		if (parent.isAccountLoggedIn() && parent.getMainUI() != null) {
			JukeboxManager mgr = parent.getJukeboxManager();
			cont.addAll(mgr.getAllSongs());
		}
		return cont;
	}

	private void init() {
		Button syncLibraryButton = new Button("Sync");
		syncLibraryButton.addClickListener(new ClickListener() {
			private static final long serialVersionUID = 3268458617996093852L;

			@Override
			public void buttonClick(ClickEvent event) {
				updateSongLibraryTable();
			}
		});

		HorizontalLayout syncLayout = new HorizontalLayout();
		syncLayout.addComponent(syncLibraryButton);
		syncLayout.setComponentAlignment(syncLibraryButton,
				Alignment.MIDDLE_RIGHT);

		// TODO 700 current song bold
		libraryTable = new Table();
		updateSongLibraryTable();
		libraryTable.setPageLength(15);
		libraryTable.setSelectable(true);
		libraryTable.setMultiSelect(true);
		libraryTable.setImmediate(true);
		libraryTable.setWidth(100, Unit.PERCENTAGE);
		libraryTable.setDragMode(TableDragMode.ROW);
		// TODO 800 add drag-drop listener, to currentplaylisttable => multirow
		libraryTable.addItemClickListener(event -> {
			if (event.isDoubleClick() && event.getItemId() != null) {
				parent.getJukeboxManager().setNewCurrentPlaylist((SongDTO) event.getItemId());
				parent.playSong((SongDTO) event.getItemId());
			}
		});

		VerticalLayout songPanelLayout = new VerticalLayout();
		songPanelLayout.addComponent(libraryTable);
		songPanelLayout.addComponent(syncLayout);
		songPanelLayout.setSizeFull();

		this.setContent(songPanelLayout);
	}

	private void updateSongLibraryTable() {
		// TODO 300 setup correct columns
		libraryTable.setContainerDataSource(generateLibraryTableContent());
	}
}
