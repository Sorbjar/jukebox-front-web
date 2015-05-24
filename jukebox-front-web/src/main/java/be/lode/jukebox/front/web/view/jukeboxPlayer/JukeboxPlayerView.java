package be.lode.jukebox.front.web.view.jukeboxPlayer;

import java.util.Observable;
import java.util.Observer;

import be.lode.jukebox.front.web.view.general.JukeboxCustomComponent;
import be.lode.jukebox.front.web.view.general.MainLayout;
import be.lode.jukebox.front.web.view.jukeboxPlayer.parts.CurrentJukeboxLayout;
import be.lode.jukebox.front.web.view.jukeboxPlayer.parts.CurrentPlaylistLayout;
import be.lode.jukebox.front.web.view.jukeboxPlayer.parts.LibraryLayout;
import be.lode.jukebox.front.web.view.jukeboxPlayer.parts.MejsAudioComponentPanel;
import be.lode.jukebox.service.UpdateArgs;
import be.lode.jukebox.service.dto.SongDTO;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.HorizontalSplitPanel;
import com.vaadin.ui.Panel;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;

public class JukeboxPlayerView extends JukeboxCustomComponent implements View,
		Observer {
	private static final String NAME = "JukeboxPlayer";
	private static final long serialVersionUID = -849898443596794108L;

	public static String getName() {
		return NAME;
	}

	private MejsAudioComponentPanel audioPlayerPanel;
	private CurrentJukeboxLayout currentJukeboxLayout;
	private CurrentPlaylistLayout currentPlaylistLayout;

	private LibraryLayout libraryLayout;
	private MainLayout ml;

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
		return libraryLayout.getLibraryTable();
	}

	public void playMandatorySong() {
		audioPlayerPanel.playMandatorySong();
	}

	public void playSong(SongDTO song) {
		audioPlayerPanel.playSong(song);
	}

	@Override
	public void update(Observable o, Object arg) {
		if (arg.equals(UpdateArgs.CURRENT_JUKEBOX)
				|| arg.equals(UpdateArgs.CURRENT_ACCOUNT)) {
			currentJukeboxLayout.update();
			currentPlaylistLayout.update();
		}
		if (arg.equals(UpdateArgs.CURRENT_PLAYLIST)) {
			currentPlaylistLayout.update();
		}
	}

	private void init() {

		currentPlaylistLayout = new CurrentPlaylistLayout(this);

		libraryLayout = new LibraryLayout(this);

		currentJukeboxLayout = new CurrentJukeboxLayout(this);

		audioPlayerPanel = new MejsAudioComponentPanel(this);

		HorizontalSplitPanel leftSplitPanel = new HorizontalSplitPanel();
		leftSplitPanel.setFirstComponent(currentJukeboxLayout);
		leftSplitPanel.setSecondComponent(libraryLayout);
		leftSplitPanel.setSplitPosition(33, Unit.PERCENTAGE);
		HorizontalSplitPanel rightSplitPanel = new HorizontalSplitPanel();
		rightSplitPanel.setFirstComponent(leftSplitPanel);
		rightSplitPanel.setSecondComponent(currentPlaylistLayout);
		rightSplitPanel.setSplitPosition(70, Unit.PERCENTAGE);
		HorizontalLayout topLayout = new HorizontalLayout();
		Panel topContainerPanel = new Panel();
		topContainerPanel.setContent(rightSplitPanel);
		topContainerPanel.setWidth(99, Unit.PERCENTAGE);
		topLayout.addComponent(topContainerPanel);
		topLayout
				.setComponentAlignment(topContainerPanel, Alignment.TOP_CENTER);
		topLayout.setSizeFull();

		HorizontalLayout bottomLayout = new HorizontalLayout();
		bottomLayout.setSizeFull();
		audioPlayerPanel.setWidth(99, Unit.PERCENTAGE);
		bottomLayout.addComponent(audioPlayerPanel);
		bottomLayout.setComponentAlignment(audioPlayerPanel,
				Alignment.TOP_CENTER);

		VerticalLayout vl = new VerticalLayout();
		vl.setSizeFull();
		vl.addComponent(topLayout);
		vl.setComponentAlignment(topLayout, Alignment.TOP_CENTER);
		vl.addComponent(bottomLayout);
		vl.setComponentAlignment(bottomLayout, Alignment.TOP_CENTER);

		ml = new MainLayout();
		ml.addComponentToContainer(vl);
		this.setCompositionRoot(ml);
	}

	private void update() {
		ml.update();
		currentJukeboxLayout.update();
		currentPlaylistLayout.update();
		libraryLayout.update();
		audioPlayerPanel.update();
	}
}
