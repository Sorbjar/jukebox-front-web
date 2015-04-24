package be.lode.jukebox.front.web.view.jukeboxPlayer;

import java.util.Observable;
import java.util.Observer;

import be.lode.jukebox.front.web.view.general.JukeboxCustomComponent;
import be.lode.jukebox.front.web.view.general.MainLayout;
import be.lode.jukebox.front.web.view.jukeboxPlayer.parts.CurrentJukeboxPanel;
import be.lode.jukebox.front.web.view.jukeboxPlayer.parts.CurrentPlaylistPanel;
import be.lode.jukebox.front.web.view.jukeboxPlayer.parts.LibraryPanel;
import be.lode.jukebox.front.web.view.jukeboxPlayer.parts.MejsAudioComponentPanel;
import be.lode.jukebox.service.UpdateArgs;
import be.lode.jukebox.service.dto.SongDTO;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;

public class JukeboxPlayerView extends JukeboxCustomComponent implements View,
		Observer {
	private static final String NAME = "JukeboxPlayer";
	private static final long serialVersionUID = -849898443596794108L;

	public static String getName() {
		return NAME;
	}

	private CurrentJukeboxPanel currentJukeboxPanel;
	private CurrentPlaylistPanel currentPlaylistPanel;
	private LibraryPanel libraryPanel;

	private MainLayout ml;
	private MejsAudioComponentPanel audioPlayerPanel;

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

	public Table getLibraryTable() {
		return libraryPanel.getLibraryTable();
	}

	@Override
	public void update(Observable o, Object arg) {
		if (arg.equals(UpdateArgs.CURRENT_JUKEBOX)) {
			currentJukeboxPanel.update();
			currentPlaylistPanel.update();
		}
		if (arg.equals(UpdateArgs.CURRENT_PLAYLIST)) {
			currentPlaylistPanel.update();
		}
	}

	private void init() {
		currentPlaylistPanel = new CurrentPlaylistPanel(this);

		libraryPanel = new LibraryPanel(this);

		currentJukeboxPanel = new CurrentJukeboxPanel(this);

		audioPlayerPanel = new MejsAudioComponentPanel(this);// new
												// Panel("Music button panel");

		HorizontalLayout topLayout = new HorizontalLayout();
		topLayout.addComponent(currentJukeboxPanel);
		topLayout.addComponent(libraryPanel);
		topLayout.addComponent(currentPlaylistPanel);
		topLayout.setSizeFull();

		HorizontalLayout bottomLayout = new HorizontalLayout();
		bottomLayout.setSizeFull();
		bottomLayout.addComponent(audioPlayerPanel);

		VerticalLayout vl = new VerticalLayout();
		vl.setSizeFull();
		vl.addComponent(topLayout);
		vl.addComponent(bottomLayout);

		ml = new MainLayout();
		ml.addComponentToContainer(vl);
		this.setCompositionRoot(ml);
	}

	private void update() {
		ml.update();
		currentJukeboxPanel.update();
		currentPlaylistPanel.update();
		libraryPanel.update();
	}

	public void playSong(SongDTO song) {
		audioPlayerPanel.playSong(song);
	}
}
