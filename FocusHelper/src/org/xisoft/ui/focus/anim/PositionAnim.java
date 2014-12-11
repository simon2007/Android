package org.xisoft.ui.focus.anim;

import android.view.View;
import android.view.ViewPropertyAnimator;

public class PositionAnim extends FocusAnimatorBase {

	public PositionAnim(View sourceView) {
		super(sourceView);
	}

	@Override
	public void startAnimator(View anchorView) {

		ViewPropertyAnimator anim = sourceView.animate();

		int[] location = new int[2];
		anchorView.getLocationOnScreen(location);

		anim.scaleX(getScaleX(anchorView));
		anim.scaleY(getScaleY(anchorView));
		anim.translationX(getTranslationX(anchorView, location[0]));
		anim.translationY(getTranslationY(anchorView, location[1]));
		startAnimator(anim);
	}

	protected float getScaleX(View anchorView) {
		return anchorView.getMeasuredWidth() * 1.0f / sourceView.getMeasuredWidth();
	}

	protected float getTranslationY(View anchorView, int anchorViewTop) {
		return anchorViewTop + (anchorView.getMeasuredHeight() - sourceView.getMeasuredHeight()) / 2;
	}

	protected float getTranslationX(View anchorView, int anchorViewLeft) {
		return anchorViewLeft + (anchorView.getMeasuredWidth() - sourceView.getMeasuredWidth()) / 2;
	}

	protected float getScaleY(View anchorView) {
		return anchorView.getMeasuredHeight() * 1.0f / sourceView.getMeasuredHeight();
	}

}
