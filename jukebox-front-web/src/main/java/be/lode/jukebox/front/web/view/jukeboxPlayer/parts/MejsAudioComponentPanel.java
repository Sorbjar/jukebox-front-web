package be.lode.jukebox.front.web.view.jukeboxPlayer.parts;

import java.util.Observable;
import java.util.Observer;

import be.lode.jukebox.front.web.controller.AudioManager;
import be.lode.jukebox.front.web.view.jukeboxPlayer.JukeboxPlayerView;
import be.lode.jukebox.service.dto.SongDTO;
import be.lode.jukebox.service.manager.JukeboxManager;

import com.kbdunn.vaadin.addons.mediaelement.MediaComponent;
import com.kbdunn.vaadin.addons.mediaelement.MediaComponentOptions;
import com.kbdunn.vaadin.addons.mediaelement.MediaComponentOptions.Feature;
import com.kbdunn.vaadin.addons.mediaelement.PlaybackEndedListener;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;

// TODO 300 handle mandatory, set buttons not accesible
public class MejsAudioComponentPanel extends Panel implements Observer {
	private static final long serialVersionUID = -4904541552593172280L;

	private AudioManager audioManager;
	private MediaComponent audioPlayer;
	private JukeboxPlayerView parent;
	private Button playPauseButton;

	private Button loopButton;

	private Button randomButton;

	public MejsAudioComponentPanel(JukeboxPlayerView parent) {
		super();
		this.parent = parent;
		audioManager = new AudioManager(this);
		init();
	}

	public MediaComponent getAudioPlayer() {
		return audioPlayer;
	}

	public void playSong(SongDTO song) {
		parent.getJukeboxManager().setCurrentSong(song);
		audioManager.play(song);
	}

	private void init() {
		audioPlayer = new MediaComponent(MediaComponent.Type.AUDIO);

		audioPlayer.setWidth(100, Unit.PERCENTAGE);

		MediaComponentOptions opts = audioPlayer.getOptions();
		opts.setEnableKeyboard(true);
		opts.setFeatures(new Feature[] { Feature.PROGRESS, Feature.CURRENT,
				Feature.DURATION, });
		audioPlayer.setOptions(opts);

		audioPlayer.setStyleName("audioplayer");
		audioPlayer.addPlaybackEndedListener(new PlaybackEndedListener() {

			@Override
			public void playbackEnded(MediaComponent component) {
				// TODO 100 somestuff
			}
		});
		audioPlayer.setFlashFallbackEnabled(true);
		audioPlayer.setSilverlightFallbackEnabled(true);

		playPauseButton = new Button("Play");
		playPauseButton.addClickListener(new ClickListener() {
			private static final long serialVersionUID = 1479848859524217690L;

			@Override
			public void buttonClick(ClickEvent event) {
				audioManager.playPause();
			}
		});

		Button stopButton = new Button("Stop");
		stopButton.addClickListener(new ClickListener() {
			private static final long serialVersionUID = -847246503778518028L;

			@Override
			public void buttonClick(ClickEvent event) {
				audioManager.stop();
			}
		});

		Button nextButton = new Button("Next");
		nextButton.addClickListener(new ClickListener() {
			private static final long serialVersionUID = -847246503778518028L;

			@Override
			public void buttonClick(ClickEvent event) {
				audioManager.next();
			}
		});
		
		Button previousButton = new Button("Previous");
		previousButton.addClickListener(new ClickListener() {
			private static final long serialVersionUID = -847246503778518028L;

			@Override
			public void buttonClick(ClickEvent event) {
				audioManager.previous();
			}
		});

		loopButton = new Button("Loop");
		loopButton.addClickListener(new ClickListener() {
			private static final long serialVersionUID = -847246503778518028L;

			@Override
			public void buttonClick(ClickEvent event) {
				parent.getJukeboxManager().changeLoopState();
			}
		});

		randomButton = new Button("Random");
		randomButton.addClickListener(new ClickListener() {
			private static final long serialVersionUID = -847246503778518028L;

			@Override
			public void buttonClick(ClickEvent event) {
				parent.getJukeboxManager().changeRandomState();
			}
		});

		HorizontalLayout audioLayout = new HorizontalLayout();
		audioLayout.setWidth(100, Unit.PERCENTAGE);
		audioLayout.addComponent(audioPlayer);
		audioLayout.setComponentAlignment(audioPlayer, Alignment.BOTTOM_CENTER);

		HorizontalLayout buttonLayout = new HorizontalLayout();
		buttonLayout.addComponent(previousButton);
		buttonLayout.addComponent(playPauseButton);
		buttonLayout.addComponent(stopButton);
		buttonLayout.addComponent(nextButton);
		// TODO 100 Volume
		buttonLayout.addComponent(new Button("Volume"));
		buttonLayout.addComponent(randomButton);
		buttonLayout.addComponent(loopButton);

		VerticalLayout vl = new VerticalLayout();

		vl.addComponent(audioLayout);
		vl.addComponent(buttonLayout);
		this.setContent(vl);
	}

	public void update() {
		// TODO 610 change to icons
		playPauseButton.setCaption("Pause");
		loopButton.setCaption("Loop");
		randomButton.setCaption("Random");
		if (audioManager.isPaused())
			playPauseButton.setCaption("Play");
		if (getJukeboxManager().isLooped())
			loopButton.setCaption("Unloop");
		if (getJukeboxManager().isRandom())
			randomButton.setCaption("Unrandom");

		playPauseButton.markAsDirty();
		loopButton.markAsDirty();
		randomButton.markAsDirty();
	}

	public JukeboxManager getJukeboxManager() {
		return parent.getJukeboxManager();
	}

	@Override
	public void update(Observable o, Object arg) {
		update();
	}

	@Override
	public void attach() {
		super.attach();
		parent.getJukeboxManager().addObserver(this);
		update();
	}
}
