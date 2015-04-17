package be.lode.jukebox.front.web.view.jukeboxPlayer;

import be.lode.jukebox.front.web.view.general.MainLayout;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;

public class JukeboxPlayerView extends CustomComponent implements View {
	private static final long serialVersionUID = -849898443596794108L;
	private static final String NAME = "JukeboxPlayer";

	public static String getName() {
		return NAME;
	}

	private MainLayout ml;
	
	public JukeboxPlayerView() {
		super();
		
		Panel currentPlaylistPanel = new Panel("Current playlist panel");
		currentPlaylistPanel.setSizeFull();
		
		Panel songTablePanel = new Panel("Song table panel");
		songTablePanel.setSizeFull();
		
		Panel playlistPanel = new Panel("Current playlist panel");
		playlistPanel.setSizeFull();
		
		Panel musicButtonPanel = new Panel("Music button panel");
		musicButtonPanel.setSizeFull();
		
		HorizontalLayout topLayout =  new HorizontalLayout();
		topLayout.addComponent(currentPlaylistPanel);
		topLayout.addComponent(songTablePanel);
		topLayout.addComponent(playlistPanel);
		topLayout.setSizeFull();
		
		HorizontalLayout bottomLayout = new HorizontalLayout();
		bottomLayout.addComponent(musicButtonPanel);
		
		VerticalLayout vl = new VerticalLayout();
		vl.setSizeFull();
		vl.addComponent(topLayout);
		vl.addComponent(bottomLayout);
		
		ml = new MainLayout();
		ml.addComponentToContainer(vl);
		this.setCompositionRoot(ml);
	}

	@Override
	public void enter(ViewChangeEvent event) {
		update();
	}
	
	@Override
	public void attach() {
		super.attach();
		update();
	}

	private void update() {
		ml.update();
		//TODO update
	}

}
