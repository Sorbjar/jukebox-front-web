package be.lode.jukebox.front.web.view.jukeboxPlayer.parts;

import be.lode.jukebox.front.web.view.general.EditLabel;
import be.lode.jukebox.front.web.view.jukeboxPlayer.JukeboxPlayerView;
import be.lode.jukebox.service.dto.SongDTO;
import be.lode.jukebox.service.manager.JukeboxManager;

import com.vaadin.data.Container;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.event.Action;
import com.vaadin.event.DataBoundTransferable;
import com.vaadin.event.FieldEvents.BlurEvent;
import com.vaadin.event.FieldEvents.BlurListener;
import com.vaadin.event.dd.DragAndDropEvent;
import com.vaadin.event.dd.DropHandler;
import com.vaadin.event.dd.acceptcriteria.AcceptCriterion;
import com.vaadin.event.dd.acceptcriteria.And;
import com.vaadin.event.dd.acceptcriteria.Or;
import com.vaadin.event.dd.acceptcriteria.SourceIs;
import com.vaadin.ui.AbstractSelect.AbstractSelectTargetDetails;
import com.vaadin.ui.AbstractSelect.AcceptItem;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.NativeButton;
import com.vaadin.ui.Table;
import com.vaadin.ui.Table.ColumnGenerator;
import com.vaadin.ui.Table.TableDragMode;
import com.vaadin.ui.VerticalLayout;

public class CurrentPlaylistLayout extends VerticalLayout {
	private static final long serialVersionUID = -4053283653825728163L;

	private Table mandatorySongTable;
	private JukeboxPlayerView parent;
	private EditLabel playListNameComponent;
	private Table playlistSongTable;
	private int mandatoryPageLength;

	public CurrentPlaylistLayout(JukeboxPlayerView parent) {
		super();
		this.parent = parent;
		init();
	}

	public void update() {
		updatePlayListName();
		updateMandatorySongTable();
		updatePlaylistSongTable();
	}

	private void createMandatorySongTable() {
		mandatorySongTable = new Table();
		mandatorySongTable.setStyleName("mandatoryplaylist");
		mandatorySongTable.addGeneratedColumn("Songs", new ColumnGenerator() {
			private static final long serialVersionUID = 1L;

			@Override
			public Object generateCell(final Table source, final Object itemId,
					Object columnId) {
				Label lbl = new Label(itemId.toString());
				return lbl;
			}
		});
		updateMandatorySongTable();
		mandatorySongTable.setSelectable(true);
		mandatorySongTable.setMultiSelect(false);
		mandatorySongTable.setImmediate(true);
		mandatorySongTable.setWidth(100, Unit.PERCENTAGE);
		mandatorySongTable.setSortEnabled(false);
		mandatorySongTable.setColumnHeaderMode(Table.ColumnHeaderMode.HIDDEN);

	}

	private void createPlaylistSongTable() {
		playlistSongTable = new Table();

		playlistSongTable.setStyleName("currentplaylist");
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
		playlistSongTable.setSelectable(true);
		playlistSongTable.setMultiSelect(false);
		playlistSongTable.setImmediate(true);
		playlistSongTable.setWidth(100, Unit.PERCENTAGE);
		playlistSongTable.setSortEnabled(false);
		playlistSongTable.setColumnHeaderMode(Table.ColumnHeaderMode.HIDDEN);
		playlistSongTable.addItemClickListener(event -> {
			if (event.isDoubleClick() && event.getItemId() != null) {
				if (!parent.getJukeboxManager().isMandatory()
						&& parent.getJukeboxManager().mandatoryEmpty())
					parent.playSong((SongDTO) event.getItemId());
				else
					parent.playMandatorySong();
			}
		});
		playlistSongTable.setDragMode(TableDragMode.ROW);
		playlistSongTable.setDropHandler(new DropHandler() {
			private static final long serialVersionUID = 3989491152940554274L;

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
				}

			}

			@Override
			public AcceptCriterion getAcceptCriterion() {
				return new Or(new And(new SourceIs(playlistSongTable),
						AcceptItem.ALL), new And(new SourceIs(parent
						.getLibraryTable()), AcceptItem.ALL));
			}
		});

		final Action removeAction = new Action("Remove from playlist");

		playlistSongTable.addActionHandler(new Action.Handler() {
			private static final long serialVersionUID = 7468376138899471634L;

			@Override
			public Action[] getActions(final Object target, final Object sender) {
				return new Action[] { removeAction };
			}

			@Override
			public void handleAction(final Action action, final Object sender,
					final Object target) {
				if (removeAction == action) {
					JukeboxManager mgr = parent.getJukeboxManager();
					if (mgr != null) {
						mgr.removeSongFromCurrentPlaylist((SongDTO) target);
					}
				}
				playlistSongTable.markAsDirtyRecursive();
			}
		});

	}

	private Container generateMandatorySongTableContent() {
		BeanItemContainer<SongDTO> cont = new BeanItemContainer<SongDTO>(
				SongDTO.class);
		if (parent.isAccountLoggedIn() && parent.getMainUI() != null) {
			JukeboxManager mgr = parent.getJukeboxManager();
			cont.addAll(mgr.getMandatorySongs());
		}
		mandatoryPageLength = min(cont.size(), 5);
		return cont;
	}

	private int min(int i1, int i2) {
		if (i1 > i2)
			return i2;
		return i1;
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
		playListNameComponent = new EditLabel();
		playListNameComponent.addBlurListener(new BlurListener() {
			private static final long serialVersionUID = -8222698381464476671L;

			@Override
			public void blur(BlurEvent event) {
				parent.getJukeboxManager().setCurrentPlaylistName(
						playListNameComponent.getValue());

			}
		});

		// TODO 610 change button to disk icon
		NativeButton savePlaylistButton = new NativeButton();
		savePlaylistButton.setStyleName("savebutton");
		savePlaylistButton.addClickListener(new ClickListener() {
			private static final long serialVersionUID = 4802873580639834918L;

			@Override
			public void buttonClick(ClickEvent event) {
				parent.getJukeboxManager().saveCurrentPlayListToJukebox();
				update();
			}
		});

		HorizontalLayout playlistNameLayout = new HorizontalLayout();
		playlistNameLayout.addComponent(playListNameComponent);
		playlistNameLayout.addComponent(savePlaylistButton);
		playlistNameLayout.setComponentAlignment(savePlaylistButton,
				Alignment.MIDDLE_RIGHT);

		createMandatorySongTable();
		createPlaylistSongTable();

		VerticalLayout currentPlaylistLayout = new VerticalLayout();
		currentPlaylistLayout.addComponent(playlistNameLayout);
		mandatorySongTable.setWidth(99, Unit.PERCENTAGE);
		playlistSongTable.setWidth(99, Unit.PERCENTAGE);
		currentPlaylistLayout.addComponent(mandatorySongTable);
		currentPlaylistLayout.addComponent(playlistSongTable);
		currentPlaylistLayout.setWidth(99, Unit.PERCENTAGE);

		this.addComponent(currentPlaylistLayout);
	}

	private void updateMandatorySongTable() {
		mandatorySongTable
				.setContainerDataSource(generateMandatorySongTableContent());
		mandatorySongTable.setVisibleColumns(new Object[] { "Songs" });
		mandatorySongTable.setColumnHeaders("Songs");
		mandatorySongTable.setPageLength(mandatoryPageLength);
		mandatorySongTable.setVisible(true);
		if (mandatoryPageLength <= 0)
			mandatorySongTable.setVisible(false);
	}

	private void updatePlayListName() {
		if (parent.getJukeboxManager() != null
				&& parent.getJukeboxManager().getCurrentPlaylistDTO().getName() != null)
			playListNameComponent.setValue(parent.getJukeboxManager()
					.getCurrentPlaylistDTO().getName());
		else
			playListNameComponent.setValue("");
	}

	private void updatePlaylistSongTable() {
		playlistSongTable
				.setContainerDataSource(generatePlaylistSongTableContent());
		playlistSongTable.setVisibleColumns(new Object[] { "Songs" });
		playlistSongTable.setColumnHeaders("Songs");
		playlistSongTable.setPageLength(16 - mandatoryPageLength);
	}
}
