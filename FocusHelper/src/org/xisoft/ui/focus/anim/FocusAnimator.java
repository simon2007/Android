package org.xisoft.ui.focus.anim;

import android.animation.Animator.AnimatorListener;
import android.view.View;
import android.view.animation.Interpolator;

public interface FocusAnimator {
	public void startAnimator(View anchorView);

	public void setInterpolator(Interpolator interpolator);

	public void setAnimatorListener(AnimatorListener listener);

	public void setDuration(long duration);
}
