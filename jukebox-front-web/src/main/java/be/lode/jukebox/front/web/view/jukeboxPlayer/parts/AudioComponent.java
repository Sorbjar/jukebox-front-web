package be.lode.jukebox.front.web.view.jukeboxPlayer.parts;

import java.io.File;

import be.lode.jukebox.service.dto.SongDTO;

import com.vaadin.server.FileResource;
import com.vaadin.ui.Audio;

public class AudioComponent extends Audio {
	private static final long serialVersionUID = 4834369259317208971L;
	private String resourcePath;

	public AudioComponent() {
		super();
		resourcePath = "";
		init();
	}
	
	public AudioComponent(SongDTO song) {
		super();
		resourcePath = song.getPath();
		init();
	}

	private void init() {
		this.setShowControls(false);
		try {
			FileResource res = new FileResource(new File(resourcePath));
			this.addSource(res);
		} catch (Exception ex) {
		}
	}
}
