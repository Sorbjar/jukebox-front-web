package be.lode.jukebox.front.web.view.jukeboxPlayer.parts;


import be.lode.jukebox.front.web.view.jukebox.EditJukeboxView;
import be.lode.jukebox.front.web.view.jukeboxPlayer.JukeboxPlayerView;
import be.lode.jukebox.service.dto.PlaylistDTO;
import be.lode.jukebox.service.manager.JukeboxManager;

import com.vaadin.data.Container;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.event.Action;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.event.ItemClickEvent.ItemClickListener;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;

//TODO 610 prettify
public class CurrentJukeboxPanel extends Panel {
	private static final long serialVersionUID = -2978450768039658888L;
	private Label jukeboxNameLabel;
	private JukeboxPlayerView parent;
	private Table playlistTable;

	public CurrentJukeboxPanel(JukeboxPlayerView parent) {
		super();
		this.parent = parent;
		init();
	}

	public void update() {
		updateJukeboxName();
		updatePlaylistTable();
	}

	private Container generatePlaylistTableContent() {
		BeanItemContainer<PlaylistDTO> cont = new BeanItemContainer<PlaylistDTO>(
				PlaylistDTO.class);
		if (parent.isAccountLoggedIn() && parent.getMainUI() != null) {
			JukeboxManager mgr = parent.getJukeboxManager();
			cont.addAll(mgr.getSavedPlaylists(mgr.getCurrentJukeboxDTO()));
		}
		return cont;
	}

	private void init() {
		jukeboxNameLabel = new Label();
		// TODO 600 change button to crotchet
		Button editJukeboxButton = new Button("Edit");
		editJukeboxButton.addClickListener(new ClickListener() {
			private static final long serialVersionUID = 5000011869857119573L;

			@Override
			public void buttonClick(ClickEvent event) {
				parent.getMainUI().navigateTo(EditJukeboxView.getName());
			}
		});

		HorizontalLayout jukeboxNameLayout = new HorizontalLayout();
		jukeboxNameLayout.setWidth(100, Unit.PERCENTAGE);
		jukeboxNameLayout.addComponent(jukeboxNameLabel);
		jukeboxNameLayout.addComponent(editJukeboxButton);
		jukeboxNameLayout.setComponentAlignment(editJukeboxButton,
				Alignment.BOTTOM_RIGHT);

		playlistTable = new Table();
		updatePlaylistTable();
		playlistTable.setPageLength(15);
		playlistTable.setSelectable(true);
		playlistTable.setMultiSelect(false);
		playlistTable.setImmediate(true);
		playlistTable.setWidth(100, Unit.PERCENTAGE);
		playlistTable.addItemClickListener(new ItemClickListener(){
			private static final long serialVersionUID = -7620499927420782545L;

			@Override
			public void itemClick(ItemClickEvent event) {
				if (event.isDoubleClick() && event.getItemId() != null) {
					JukeboxManager mgr = parent.getJukeboxManager();
					if (mgr != null) {
						mgr.setCurrentPlaylist((PlaylistDTO) event.getItemId());
					}
				}
				
			}
		});
		
		final Action deleteAction = new Action("Delete");
 
        playlistTable.addActionHandler(new Action.Handler() {
			private static final long serialVersionUID = 7468376138899471634L;

			@Override
            public Action[] getActions(final Object target, final Object sender) {
                    return new Action[] { deleteAction };
            }
 
            @Override
            public void handleAction(final Action action, final Object sender,
                    final Object target) {
                if (deleteAction == action) {
                	JukeboxManager mgr = parent.getJukeboxManager();
					if (mgr != null) {
						mgr.deletePlaylist((PlaylistDTO) target);
					}
                } 
                playlistTable.markAsDirtyRecursive();
            }
        });

		VerticalLayout currentJukeboxLayout = new VerticalLayout();
		currentJukeboxLayout.addComponent(jukeboxNameLayout);
		currentJukeboxLayout.addComponent(playlistTable);

		this.setContent(currentJukeboxLayout);
	}

	private void updateJukeboxName() {
		if (parent.getJukeboxManager() != null
				&& parent.getJukeboxManager().getCurrentJukeboxDTO().getName() != null)
			jukeboxNameLabel.setValue(parent.getJukeboxManager()
					.getCurrentJukeboxDTO().getName());
		else
			jukeboxNameLabel.setValue("");
	}

	private void updatePlaylistTable() {
		playlistTable.setContainerDataSource(generatePlaylistTableContent());
		playlistTable.setVisibleColumns(new Object[] { "name" });
		playlistTable.setColumnHeaders("Playlists");
	}
}
