package be.lode.jukebox.front.web.view.jukeboxPlayer.parts;

import be.lode.jukebox.front.web.view.jukeboxPlayer.JukeboxPlayerView;
import be.lode.jukebox.service.dto.SongDTO;
import be.lode.jukebox.service.manager.JukeboxManager;

import com.vaadin.data.Container;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.event.DataBoundTransferable;
import com.vaadin.event.dd.DragAndDropEvent;
import com.vaadin.event.dd.DropHandler;
import com.vaadin.event.dd.acceptcriteria.AcceptCriterion;
import com.vaadin.event.dd.acceptcriteria.And;
import com.vaadin.event.dd.acceptcriteria.Or;
import com.vaadin.event.dd.acceptcriteria.SourceIs;
import com.vaadin.ui.AbstractSelect.AbstractSelectTargetDetails;
import com.vaadin.ui.AbstractSelect.AcceptItem;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.Table;
import com.vaadin.ui.Table.ColumnGenerator;
import com.vaadin.ui.Table.TableDragMode;
import com.vaadin.ui.VerticalLayout;

public class CurrentPlaylistPanel extends Panel {
	private static final long serialVersionUID = -4053283653825728163L;

	private JukeboxPlayerView parent;
	private Label playListNameLabel;
	private Table playlistSongTable;

	public CurrentPlaylistPanel(JukeboxPlayerView parent) {
		super();
		this.parent = parent;
		init();
	}

	public void update() {
		updatePlayListName();
		updatePlaylistSongTable();
	}

	private Container generatePlaylistSongTableContent() {
		BeanItemContainer<SongDTO> cont = new BeanItemContainer<SongDTO>(
				SongDTO.class);
		if (parent.isAccountLoggedIn() && parent.getMainUI() != null) {
			JukeboxManager mgr = parent.getJukeboxManager();
			cont.addAll(mgr.getSongs(mgr.getCurrentPlaylistDTO()));
		}
		return cont;
	}

	private void init() {
		playListNameLabel = new Label();
		// TODO 600 change button to disk icon
		// TODO 200 add listener
		Button savePlaylistButton = new Button("Save");
		savePlaylistButton.addClickListener(new ClickListener() {
			private static final long serialVersionUID = 4802873580639834918L;

			@Override
			public void buttonClick(ClickEvent event) {
				parent.getJukeboxManager().saveCurrentPlayListToJukebox();
			}
		});

		HorizontalLayout playlistNameLayout = new HorizontalLayout();
		playlistNameLayout.addComponent(playListNameLabel);
		playlistNameLayout.addComponent(savePlaylistButton);
		playlistNameLayout.setComponentAlignment(savePlaylistButton,
				Alignment.MIDDLE_RIGHT);
		// TODO 700 current song bold
		playlistSongTable = new Table();
		playlistSongTable.addGeneratedColumn("Songs", new ColumnGenerator() {
			private static final long serialVersionUID = -5803413248406541910L;

			@Override
			public Object generateCell(final Table source, final Object itemId,
					Object columnId) {
				Label lbl = new Label(itemId.toString());
				return lbl;
			}
		});
		updatePlaylistSongTable();
		playlistSongTable.setPageLength(15);
		playlistSongTable.setSelectable(true);
		playlistSongTable.setMultiSelect(false);
		playlistSongTable.setImmediate(true);
		playlistSongTable.setWidth(100, Unit.PERCENTAGE);
		playlistSongTable.setSortEnabled(false);
		playlistSongTable.addItemClickListener(event -> {
			if (event.isDoubleClick() && event.getItemId() != null) {
				parent.playSong((SongDTO) event.getItemId());
			}
		});
		playlistSongTable.setDragMode(TableDragMode.ROW);
		playlistSongTable.setDropHandler(new DropHandler() {
			private static final long serialVersionUID = 3989491152940554274L;

			@Override
			public AcceptCriterion getAcceptCriterion() {
				return new Or(new And(new SourceIs(playlistSongTable),
						AcceptItem.ALL), new And(new SourceIs(parent
						.getLibraryTable()), AcceptItem.ALL));
			}

			@Override
			public void drop(DragAndDropEvent event) {
				if (event.getTransferable().getSourceComponent()
						.equals(playlistSongTable)) {
					DataBoundTransferable t = (DataBoundTransferable) event
							.getTransferable();
					SongDTO sourceItemId = (SongDTO) t.getItemId();
					AbstractSelectTargetDetails dropData = ((AbstractSelectTargetDetails) event
							.getTargetDetails());
					SongDTO targetItemId = (SongDTO) dropData.getItemIdOver();

					// No move if source and target are the same, or there is no
					// target
					if (sourceItemId != targetItemId && targetItemId != null)
						parent.getJukeboxManager().reorderPlaylist(
								sourceItemId, targetItemId);
				} else if (event.getTransferable().getSourceComponent()
						.equals(parent.getLibraryTable())) {
					DataBoundTransferable t = (DataBoundTransferable) event
							.getTransferable();
					SongDTO sourceItemId = (SongDTO) t.getItemId();
					AbstractSelectTargetDetails dropData = ((AbstractSelectTargetDetails) event
							.getTargetDetails());
					SongDTO targetItemId = (SongDTO) dropData.getItemIdOver();
					if (sourceItemId != targetItemId) {
						if (targetItemId == null) {
							parent.getJukeboxManager().addSong(sourceItemId);
						} else if (sourceItemId.getPlayListOrder() != targetItemId
								.getPlayListOrder()) {
							parent.getJukeboxManager().addSong(sourceItemId,
									targetItemId);
						}
					}
					// TODO 600 handle multiple
				}

			}
		});

		VerticalLayout currentPlaylistLayout = new VerticalLayout();
		currentPlaylistLayout.addComponent(playlistNameLayout);
		currentPlaylistLayout.addComponent(playlistSongTable);

		this.setContent(currentPlaylistLayout);
	}

	private void updatePlayListName() {
		if (parent.getJukeboxManager() != null
				&& parent.getJukeboxManager().getCurrentPlaylistDTO().getName() != null)
			playListNameLabel.setValue(parent.getJukeboxManager()
					.getCurrentPlaylistDTO().getName());
		else
			playListNameLabel.setValue("");
	}

	private void updatePlaylistSongTable() {
		playlistSongTable
				.setContainerDataSource(generatePlaylistSongTableContent());
		playlistSongTable.setVisibleColumns(new Object[] { "Songs" });
		playlistSongTable.setColumnHeaders("Songs");
	}
}
