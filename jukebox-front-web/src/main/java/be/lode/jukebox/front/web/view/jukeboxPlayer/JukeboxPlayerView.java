package be.lode.jukebox.front.web.view.jukeboxPlayer;

import java.util.Observable;
import java.util.Observer;

import be.lode.jukebox.front.web.view.general.JukeboxCustomComponent;
import be.lode.jukebox.front.web.view.general.MainLayout;
import be.lode.jukebox.service.UpdateArgs;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;

public class JukeboxPlayerView extends JukeboxCustomComponent implements View,
		Observer {
	private static final long serialVersionUID = -849898443596794108L;
	private static final String NAME = "JukeboxPlayer";

	public static String getName() {
		return NAME;
	}

	private MainLayout ml;
	private Label playlistNameTF;
	private Table playlistTable;

	public JukeboxPlayerView() {
		super();

		Panel currentPlaylistPanel = setupCurrentPlaylistpanel();

		Panel songTablePanel = new Panel("Song table panel");
		songTablePanel.setSizeFull();

		Panel playlistPanel = new Panel("Current playlist panel");
		playlistPanel.setSizeFull();

		Panel musicButtonPanel = new Panel("Music button panel");
		// musicButtonPanel.setSizeFull();

		HorizontalLayout topLayout = new HorizontalLayout();
		topLayout.addComponent(currentPlaylistPanel);
		topLayout.addComponent(songTablePanel);
		topLayout.addComponent(playlistPanel);
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

	private Panel setupCurrentPlaylistpanel() {
		playlistNameTF = new Label();
		// TODO 600 change button to crotchet
		Button editPlaylistButton = new Button("Edit");

		HorizontalLayout playlistNameLayout = new HorizontalLayout();
		playlistNameLayout.addComponent(playlistNameTF);
		playlistNameLayout.addComponent(editPlaylistButton);
		playlistNameLayout.setComponentAlignment(editPlaylistButton,
				Alignment.MIDDLE_RIGHT);

		playlistTable = new Table();
		updatePlaylistTable();
		playlistTable.setPageLength(20);
		playlistTable.setSelectable(true);
		playlistTable.setMultiSelect(false);
		playlistTable.setImmediate(true);
		// TODO 100 add columns
		// playlistTable.setWidth(200, Unit.PIXELS);

		VerticalLayout currentPlaylistLayout = new VerticalLayout();
		currentPlaylistLayout.addComponent(playlistNameLayout);
		currentPlaylistLayout.addComponent(playlistTable);

		Panel p = new Panel("Current playlist panel");
		p.setContent(currentPlaylistLayout);
		return p;
	}

	@Override
	public void enter(ViewChangeEvent event) {
		update();
	}

	@Override
	public void attach() {
		super.attach();
		getMainUI().getJukeboxManager().addObserver(this);
		update();
	}

	private void update() {
		ml.update();
		updateCurrentJukebox();
		// TODO 100 update
	}

	private void updatePlaylistName() {
		if (getJukeboxManager() != null
				&& getJukeboxManager().getCurrentJukebox() != null
				&& getJukeboxManager().getCurrentJukebox().getName() != null)
			playlistNameTF.setValue(getJukeboxManager().getCurrentJukebox()
					.getName());
		else
			playlistNameTF.setValue("");

	}

	@Override
	public void update(Observable o, Object arg) {
		if (arg.equals(UpdateArgs.CURRENT_JUKEBOX)) {
			updateCurrentJukebox();
			
		}
	}

	private void updateCurrentJukebox() {
		updatePlaylistName();
		updatePlaylistTable();
		
	}

	private void updatePlaylistTable() {
		//TOD
		
		//playlistTable.setContainerDataSource(generateTableContent());
		playlistTable.setVisibleColumns(new Object[] { "name" });
		playlistTable.setColumnHeaders("Playlists");
	}

}
