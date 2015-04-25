package be.lode.jukebox.front.web.controller;

import java.io.File;

import be.lode.jukebox.front.web.view.jukeboxPlayer.parts.MejsAudioComponentPanel;
import be.lode.jukebox.service.dto.SongDTO;

import com.kbdunn.vaadin.addons.mediaelement.MediaComponent;
import com.vaadin.server.FileResource;

public class AudioManager {

	private MejsAudioComponentPanel mejsAudioComponentPanel;
	private boolean paused;

	public AudioManager(MejsAudioComponentPanel mejsAudioComponentPanel) {
		super();
		this.mejsAudioComponentPanel = mejsAudioComponentPanel;
		this.paused = true;
	}

	public boolean isPaused() {
		return paused;
	}

	public void setPaused(boolean paused) {
		this.paused = paused;
	}

	public void play(SongDTO song) {
		stop();
		getAudioPlayer().setSource(new FileResource(new File(song.getPath())));
		play();
		update();
	}

	public void playPause() {
		if (isPaused())
			play();
		else
			pause();
		update();
	}

	public void stop() {
		pause();
		getAudioPlayer().setCurrentTime(0);
		update();
	}

	private MediaComponent getAudioPlayer() {
		return mejsAudioComponentPanel.getAudioPlayer();
	}

	private void pause() {
		setPaused(true);
		getAudioPlayer().pause();
	}

	private void play() {
		setPaused(false);
		getAudioPlayer().play();
	}

	private void update() {
		mejsAudioComponentPanel.update();
	}

	public void next() {
		boolean temp = paused;
		stop();
		SongDTO song = mejsAudioComponentPanel.getJukeboxManager()
				.getNextSong();
		if (song != null) {
			getAudioPlayer().setSource(
					new FileResource(new File(song.getPath())));
			if (!temp)
				play();
		} else {
			stop();
		}
	}

	public void previous() {
		boolean temp = paused;
		stop();
		SongDTO song = mejsAudioComponentPanel.getJukeboxManager()
				.getPreviousSong();
		if (song != null) {
			getAudioPlayer().setSource(
					new FileResource(new File(song.getPath())));
			if (!temp)
				play();
		} else {
			stop();
		}
		
	}
}
