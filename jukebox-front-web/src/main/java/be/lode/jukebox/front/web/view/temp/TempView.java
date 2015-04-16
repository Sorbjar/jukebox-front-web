package be.lode.jukebox.front.web.view.temp;

import be.lode.jukebox.front.web.view.general.MainLayout;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;

public class TempView extends CustomComponent implements View {
	private static final long serialVersionUID = -9112596372107786366L;
	private static final String NAME = "";

	public static String getName() {
		return NAME;
	}

	private HorizontalLayout hl;
	private MainLayout ml;

	@Override
	public void attach() {
		super.attach();
		update();
	}

	public TempView() {
		super();
		init();
	}

	private void init() {
		ml = new MainLayout();
		this.setCompositionRoot(ml);

	}

	@Override
	public void enter(ViewChangeEvent event) {
		update();
	}

	private void update() {
		ml.update();
	}

}
