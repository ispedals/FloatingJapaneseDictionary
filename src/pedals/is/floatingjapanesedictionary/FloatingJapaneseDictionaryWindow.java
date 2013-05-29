package pedals.is.floatingjapanesedictionary;

import android.view.LayoutInflater;
import android.widget.FrameLayout;
import wei.mark.standout.StandOutWindow;
import wei.mark.standout.constants.StandOutFlags;
import wei.mark.standout.ui.Window;

public class FloatingJapaneseDictionaryWindow extends StandOutWindow {

	@Override
	public String getAppName() {
		return "Floating Japanese Dictionary";
	}

	@Override
	public int getAppIcon() {
		return android.R.drawable.ic_menu_add;
	}

	@Override
	public void createAndAttachView(int id, FrameLayout frame) {
		LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.floatingdictionary, frame, true);

	}

	@Override
	public StandOutLayoutParams getParams(int id, Window window) {
		return new StandOutLayoutParams(id, 300, 200,
				StandOutLayoutParams.AUTO_POSITION, StandOutLayoutParams.AUTO_POSITION);
	}

	@Override
	public int getFlags(int id) {
		return super.getFlags(id) |
				StandOutFlags.FLAG_DECORATION_SYSTEM |
				StandOutFlags.FLAG_BODY_MOVE_ENABLE |
				StandOutFlags.FLAG_WINDOW_EDGE_LIMITS_ENABLE |
				StandOutFlags.FLAG_WINDOW_BRING_TO_FRONT_ON_TAP |
				StandOutFlags.FLAG_ADD_FUNCTIONALITY_DROP_DOWN_DISABLE;
	}

}
