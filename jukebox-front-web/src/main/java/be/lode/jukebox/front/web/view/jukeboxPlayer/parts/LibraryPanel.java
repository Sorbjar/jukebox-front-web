package be.lode.jukebox.front.web.view.jukeboxPlayer.parts;

import be.lode.jukebox.front.web.view.jukeboxPlayer.JukeboxPlayerView;
import be.lode.jukebox.service.dto.SongDTO;
import be.lode.jukebox.service.manager.JukeboxManager;

import com.vaadin.data.Container;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Panel;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Table.TableDragMode;

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
		// TODO 200 add listener
		Button syncLibraryButton = new Button("Sync");

		HorizontalLayout syncLayout = new HorizontalLayout();
		syncLayout.addComponent(syncLibraryButton);
		syncLayout.setComponentAlignment(syncLibraryButton,
				Alignment.MIDDLE_RIGHT);

		//TODO 700 current song bold
		libraryTable = new Table();
		updateSongLibraryTable();
		libraryTable.setPageLength(15);
		libraryTable.setSelectable(true);
		libraryTable.setMultiSelect(true);
		libraryTable.setImmediate(true);
		libraryTable.setWidth(100, Unit.PERCENTAGE);
		libraryTable.setDragMode(TableDragMode.ROW);
		// TODO 800 add drag and drop listener, to currentplaylisttable => multirow
		// TODO 300 add double click listener

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
