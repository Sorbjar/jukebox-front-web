package be.lode.jukebox.front.web.view.jukeboxPlayer.parts;

import java.io.File;

import be.lode.jukebox.front.web.view.jukeboxPlayer.JukeboxPlayerView;
import be.lode.jukebox.service.dto.SongDTO;

import com.kbdunn.vaadin.addons.mediaelement.MediaComponent;
import com.kbdunn.vaadin.addons.mediaelement.MediaComponentOptions;
import com.kbdunn.vaadin.addons.mediaelement.MediaComponentOptions.Feature;
import com.kbdunn.vaadin.addons.mediaelement.PlaybackEndedListener;
import com.vaadin.server.FileResource;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;

public class MejsAudioComponentPanel extends Panel {
	private static final long serialVersionUID = -4904541552593172280L;

	private HorizontalLayout hl;

	private JukeboxPlayerView parent;

	private MediaComponent audioPlayer;

	public MejsAudioComponentPanel(JukeboxPlayerView parent) {
		super();
		this.parent = parent;
		init();
	}

	public void playSong(SongDTO song) {
		parent.getJukeboxManager().setCurrentSong(song);
		audioPlayer.setSource(new FileResource(new File(song.getPath())));
		audioPlayer.play();
	}

	public void stop() {
		audioPlayer.pause();
		audioPlayer.setCurrentTime(0);
	}

	private void init() {
		VerticalLayout vl = new VerticalLayout();
		hl = new HorizontalLayout();
		HorizontalLayout hlComp = new HorizontalLayout();
		hl.setSizeFull();
		audioPlayer = new MediaComponent(MediaComponent.Type.AUDIO);
		hl.addComponent(audioPlayer);
		audioPlayer.setSizeFull();
		audioPlayer.setWidth(100, Unit.PERCENTAGE);
		hlComp.setComponentAlignment(audioPlayer, Alignment.MIDDLE_CENTER);
		vl.addComponent(hlComp);
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

		Button playButton = new Button("Play");
		playButton.addClickListener(new ClickListener() {
			private static final long serialVersionUID = 1479848859524217690L;

			@Override
			public void buttonClick(ClickEvent event) {

				audioPlayer.play();
			}
		});

		Button pauseButton = new Button("Pause");
		pauseButton.addClickListener(new ClickListener() {
			private static final long serialVersionUID = 6621456899098450879L;

			@Override
			public void buttonClick(ClickEvent event) {
				audioPlayer.pause();
			}
		});

		Button stopButton = new Button("Stop");
		stopButton.addClickListener(new ClickListener() {
			private static final long serialVersionUID = -847246503778518028L;

			@Override
			public void buttonClick(ClickEvent event) {
				stop();
			}
		});

		hl.addComponent(playButton);
		hl.addComponent(pauseButton);
		hl.addComponent(stopButton);
		vl.addComponent(hl);
		this.setContent(vl);
	}

}
