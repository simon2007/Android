package org.xisoft.ui.focus;

import org.xisoft.ui.focus.FocusWatcher.FocusChangeListener;
import org.xisoft.ui.focus.anim.AnimatorList;
import org.xisoft.ui.focus.anim.FadeAndScaleAnimator;
import org.xisoft.ui.focus.anim.FocusAnimator;
import org.xisoft.ui.focus.anim.PositionAnim;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnticipateInterpolator;

/**
 * 焦点帮助类。监听焦点变化，并正确显示焦点位置 第二个版本
 * 
 * @author lilongwu
 * 
 */
public class FocusHelper3 implements FocusChangeListener {

	private FocusWatcher focusWatcher;
	protected View sourceView;

	public FocusHelper3(View focusControl) {
		sourceView = focusControl;

	}

	/**
	 * 需要监听的容器
	 * 
	 * @param view
	 */
	public void addWatch(ViewGroup view) {
		if (view != null) {
			if (focusWatcher == null)
				focusWatcher = createFocusWatcher();
			focusWatcher.addWatch(view);
		}
	}

	private void change(View anchorView, boolean immediate) {
		if (focusWatcher != null && focusWatcher.isPaused()) {
			Log.d("FocusHelper", "isPaused");
			return;
		}
		if (anchorView == null || !anchorView.isShown()) {

			onHide(anchorView);
			return;
		}
		if (!isSourceViewShown())
			immediate = true;
		onChange(anchorView, immediate);

	}

	protected void onChange(View anchorView, boolean immediate) {
		sourceView.clearAnimation();

		AnimatorList animList = new AnimatorList();

		FocusAnimator anim = new FadeAndScaleAnimator(sourceView, 0f, 1.1f, 1.1f);
		anim.setDuration(200);
		anim.setInterpolator(new AnticipateInterpolator());
		animList.addAnimator(anim);

		anim = new PositionAnim(sourceView);
		anim.setDuration(10);
		animList.addAnimator(anim);

		anim = new FadeAndScaleAnimator(sourceView, 1f, 0.9f, 0.9f);
		anim.setDuration(200);
		anim.setInterpolator(new AnticipateInterpolator());
		animList.addAnimator(anim);

		animList.startAnimator(anchorView);
	}

	protected FocusWatcher createFocusWatcher() {
		return new FocusWatcher(this);
	}

	public void hide(View newFocus) {
		onHide(newFocus);

	}

	protected boolean isSourceViewShown() {
		return sourceView.getVisibility() == View.VISIBLE;
	}

	protected void onHide(View focusedView) {
		sourceView.setVisibility(View.INVISIBLE);
	}

	protected void onShow(View focusedView) {
		sourceView.setVisibility(View.VISIBLE);
	}

	public void pause() {
		Log.d("FocusHelper", "pause");
		if (focusWatcher != null)
			focusWatcher.pause();
	}

	/**
	 * 移除监听
	 * 
	 * @param view
	 */
	public void removeWatch(ViewGroup view) {
		if (view != null) {
			FocusWatcher f = focusWatcher;
			focusWatcher = null;
			if (f != null) {
				f.pause();
				f.removeWatch(view);
			}
		}
	}

	public void resume() {
		Log.d("FocusHelper", "resume");
		if (focusWatcher != null)
			focusWatcher.resume();

	}

	public void setDefaultFocus(View control) {
		Log.d("FocusHelper", "setDefaultFocus " + control);

		hide(control);
		focusWatcher.onGlobalFocusChanged(null, control);
	}

	@Override
	public void onFocusChanged(View newFocus, int repeatCount) {
		if (newFocus != null)
			change(newFocus, repeatCount > 0);

	}

}
