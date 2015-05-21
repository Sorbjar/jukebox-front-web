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
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.server.Resource;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.NativeButton;
import com.vaadin.ui.Panel;
import com.vaadin.ui.Slider;
import com.vaadin.ui.VerticalLayout;

public class MejsAudioComponentPanel extends Panel implements Observer {
	private static final long serialVersionUID = -4904541552593172280L;

	private AudioManager audioManager;
	private MediaComponent audioPlayer;
	private NativeButton loopButton;
	private JukeboxPlayerView parent;
	private NativeButton playPauseButton;
	private Button randomButton;
	private Label songLabel;
	private Slider volumeSlider;
	private NativeButton nextButton;
	private NativeButton previousButton;

	public MejsAudioComponentPanel(JukeboxPlayerView parent) {
		super();
		this.parent = parent;
		audioManager = new AudioManager(this);
		init();
	}

	@Override
	public void attach() {
		super.attach();
		parent.getJukeboxManager().addObserver(this);
		update();
	}

	public MediaComponent getAudioPlayer() {
		return audioPlayer;
	}

	public JukeboxManager getJukeboxManager() {
		return parent.getJukeboxManager();
	}

	public void playSong(SongDTO song) {
		parent.getJukeboxManager().setCurrentSong(song);
		audioManager.play(song);
	}

	public void update() {
		playPauseButton.setStyleName("pausebutton");
		loopButton.setStyleName("loopbutton");
		randomButton.setCaption("Random");
		if (audioManager.isPaused())
			playPauseButton.setStyleName("playbutton");
		if (getJukeboxManager().isLooped())
			loopButton.setCaption("Unloop");
		if (getJukeboxManager().isRandom())
			randomButton.setCaption("Unrandom");

		if (parent.getJukeboxManager().getCurrentSongDTO() != null)
			songLabel.setValue(parent.getJukeboxManager().getCurrentSongDTO()
					.toString());
		else
			songLabel.setValue("");

		nextButton.setEnabled(true);
		previousButton.setEnabled(true);
		// TODO 100 change disabled buttons so it's visual
		if (getJukeboxManager().isMandatory()) {
			nextButton.setEnabled(false);
			previousButton.setEnabled(false);
		}

		playPauseButton.markAsDirty();
		loopButton.markAsDirty();
		randomButton.markAsDirty();
		volumeSlider.markAsDirty();
	}

	@Override
	public void update(Observable o, Object arg) {
		update();
	}

	private void init() {
		MediaComponentOptions opts = MediaComponentOptions.getDefaultOptions();
		opts.setEnableKeyboard(true);
		opts.setFeatures(new Feature[] { Feature.PROGRESS, Feature.CURRENT,
				Feature.DURATION });
		opts.setAudioWidth(800);

		audioPlayer = new MediaComponent(MediaComponent.Type.AUDIO, opts);

		songLabel = new Label();
		songLabel.setValue("");
		audioPlayer.setVolume(5);

		audioPlayer.setStyleName("audioplayer");
		audioPlayer.addPlaybackEndedListener(new PlaybackEndedListener() {

			@Override
			public void playbackEnded(MediaComponent component) {
				audioManager.next();
				update();
			}
		});
		audioPlayer.setFlashFallbackEnabled(true);
		audioPlayer.setSilverlightFallbackEnabled(true);

		// playPauseButton = new Button("Play");
		playPauseButton = new NativeButton();
		playPauseButton.setStyleName("playbutton");
		playPauseButton.addClickListener(new ClickListener() {
			private static final long serialVersionUID = 1479848859524217690L;

			@Override
			public void buttonClick(ClickEvent event) {
				if (getAudioSource() == null) {
					setAudioSourceFromPlaylist();
				}
				// it is still null if ther is no song in playlist
				if (getAudioSource() != null) {
					audioManager.playPause();
					update();
				}
			}

			private void setAudioSourceFromPlaylist() {
				audioManager.first();
			}

			private Resource getAudioSource() {
				return getAudioPlayer().getSource();
			}

		});

		NativeButton stopButton = new NativeButton();
		stopButton.setStyleName("stopbutton");
		stopButton.addClickListener(new ClickListener() {
			private static final long serialVersionUID = -847246503778518028L;

			@Override
			public void buttonClick(ClickEvent event) {
				audioManager.stop();
				update();
			}
		});

		nextButton = new NativeButton();
		nextButton.setStyleName("nextbutton");
		nextButton.addClickListener(new ClickListener() {
			private static final long serialVersionUID = -847246503778518028L;

			@Override
			public void buttonClick(ClickEvent event) {
				audioManager.next();
				update();
			}
		});

		previousButton = new NativeButton();
		previousButton.setStyleName("previousbutton");
		previousButton.addClickListener(new ClickListener() {
			private static final long serialVersionUID = -847246503778518028L;

			@Override
			public void buttonClick(ClickEvent event) {
				audioManager.previous();
				update();
			}
		});

		loopButton = new NativeButton();
		loopButton.setStyleName("loopbutton");
		loopButton.addClickListener(new ClickListener() {
			private static final long serialVersionUID = -847246503778518028L;

			@Override
			public void buttonClick(ClickEvent event) {
				parent.getJukeboxManager().changeLoopState();
				update();
			}
		});

		randomButton = new Button("Random");
		randomButton.addClickListener(new ClickListener() {
			private static final long serialVersionUID = -847246503778518028L;

			@Override
			public void buttonClick(ClickEvent event) {
				parent.getJukeboxManager().changeRandomState();
				update();
			}
		});

		// TODO 620 add volume icon
		volumeSlider = new Slider();
		volumeSlider.setImmediate(true);
		volumeSlider.setMin(0.0);
		volumeSlider.setMax(100.0);
		volumeSlider.setValue(50.0);
		volumeSlider.addValueChangeListener(new ValueChangeListener() {
			private static final long serialVersionUID = -6882639799565748798L;

			@Override
			public void valueChange(ValueChangeEvent event) {
				Double vol = (Double) event.getProperty().getValue();
				audioPlayer.setVolume((vol.intValue() + 9) / 10);
			}
		});

		VerticalLayout audioPlayercontainer = new VerticalLayout();
		audioPlayercontainer.setWidth(99, Unit.PERCENTAGE);
		audioPlayercontainer.addComponent(audioPlayer);
		audioPlayercontainer.setComponentAlignment(audioPlayer, Alignment.TOP_CENTER);
		
		HorizontalLayout audioLayout = new HorizontalLayout();
		audioLayout.setWidth(99, Unit.PERCENTAGE);
		audioLayout.addComponent(songLabel);
		audioLayout.setExpandRatio(songLabel, 1);
		audioLayout.addComponent(audioPlayercontainer);
		audioLayout.setExpandRatio(audioPlayercontainer, 10);
		Label fluff = new Label();
		audioLayout.addComponent(fluff);
		audioLayout.setExpandRatio(fluff, 1);
		audioLayout.setComponentAlignment(audioPlayercontainer, Alignment.TOP_CENTER);

		HorizontalLayout buttonLayout = new HorizontalLayout();
		buttonLayout.setSpacing(false);
		buttonLayout.addComponent(previousButton);
		buttonLayout.addComponent(playPauseButton);
		buttonLayout.addComponent(stopButton);
		buttonLayout.addComponent(nextButton);
		buttonLayout.addComponent(randomButton);
		buttonLayout.addComponent(loopButton);
		buttonLayout.addComponent(volumeSlider);
		HorizontalLayout buttonContainer = new HorizontalLayout();
		buttonContainer.addComponent(buttonLayout);
		buttonContainer.setWidth(99, Unit.PERCENTAGE);

		VerticalLayout vl = new VerticalLayout();

		vl.addComponent(audioLayout);
		vl.setComponentAlignment(audioLayout, Alignment.TOP_CENTER);
		vl.addComponent(buttonLayout);
		vl.setComponentAlignment(buttonLayout, Alignment.TOP_CENTER);
		this.setContent(vl);
	}

	public void playMandatorySong() {
		if (audioManager.isPaused()) {
			audioManager.first();
			audioManager.playPause();
		}
	}
}
