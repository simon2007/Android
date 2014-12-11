package org.xisoft.imageanim;

import org.xisoft.ui.focus.FocusHelper3;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;

public class MainActivity extends Activity {

	private FocusHelper3 focusHelper;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ViewGroup rootView = (ViewGroup) View.inflate(this, R.layout.activity_main, null);
		setContentView(rootView);


		for (int i = 0; i < 10; i++) {
			View button = new View(this);
			button.setBackgroundColor(Color.BLUE);
			button.setFocusable(true);
			RelativeLayout.LayoutParams params = new LayoutParams(90 * (i & 3) + 50, 200);
			params.topMargin = (i / 5) * 300 + 100;
			params.leftMargin = (i % 5) * 400 + 100;
			rootView.addView(button, params);
		}
		
		View focusView=findViewById(R.id.imageView);
		focusHelper = new FocusHelper3(focusView);
		focusHelper.addWatch(rootView);
		focusView.bringToFront();
	}

	@Override
	public boolean dispatchKeyEvent(KeyEvent event) {
		// TODO Auto-generated method stub
		return super.dispatchKeyEvent(event);
	}

}
