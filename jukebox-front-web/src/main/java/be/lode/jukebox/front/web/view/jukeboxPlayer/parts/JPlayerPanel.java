package be.lode.jukebox.front.web.view.jukeboxPlayer.parts;

import be.lode.jukebox.front.web.view.jukeboxPlayer.JukeboxPlayerView;
import be.lode.jukebox.service.dto.SongDTO;

import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Panel;

public class JPlayerPanel extends Panel {
	private static final long serialVersionUID = -4904541552593172280L;

	private AudioComponent audio;

	private HorizontalLayout hl;

	private JukeboxPlayerView parent;

	public JPlayerPanel(JukeboxPlayerView parent) {
		super();
		this.parent = parent;
		init();
	}

	public void playSong(SongDTO song) {
		parent.getJukeboxManager().setCurrentSong(song);
		hl.removeComponent(audio);
		audio = new AudioComponent(song);
		hl.addComponent(audio);
		audio.play();
	}

	public void stop() {
		if (parent.getJukeboxManager().getCurrentSongDTO() != null) {
			hl.removeComponent(audio);
			audio = new AudioComponent(parent.getJukeboxManager()
					.getCurrentSongDTO());
			hl.addComponent(audio);
		}
	}

	private void init() {
		audio = new AudioComponent();

		hl = new HorizontalLayout();

		hl.addComponent(audio);

		Button playButton = new Button("Play");
		playButton.addClickListener(new ClickListener() {
			private static final long serialVersionUID = 1479848859524217690L;

			@Override
			public void buttonClick(ClickEvent event) {
				audio.play();
			}
		});

		Button pauseButton = new Button("Pause");
		pauseButton.addClickListener(new ClickListener() {
			private static final long serialVersionUID = 6621456899098450879L;

			@Override
			public void buttonClick(ClickEvent event) {
				audio.pause();
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
		this.setContent(hl);
	}

}
