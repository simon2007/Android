package org.xisoft.ui.focus.anim;

import android.animation.Animator.AnimatorListener;
import android.view.View;
import android.view.ViewPropertyAnimator;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.Interpolator;

public abstract class FocusAnimatorBase implements FocusAnimator {

	protected Interpolator mInterpolator;
	protected AnimatorListener mListener;
	protected long mDuration;
	protected final View sourceView;

	public FocusAnimatorBase(View sourceView) {
		this.sourceView = sourceView;
	}

	@Override
	public void setInterpolator(Interpolator interpolator) {
		this.mInterpolator = interpolator;

	}

	@Override
	public void setAnimatorListener(AnimatorListener listener) {
		this.mListener = listener;

	}

	@Override
	public void setDuration(long duration) {
		this.mDuration = duration;

	}

	protected void startAnimator(ViewPropertyAnimator anim) {
		if (mInterpolator != null)
			anim.setInterpolator(mInterpolator);
		if (mDuration > 0)
			anim.setDuration(mDuration);
		if (mListener != null)
			anim.setListener(mListener);
		anim.start();
	}

	protected void startAnimation(Animation ani) {
		if (mDuration > 0)
			ani.setDuration(mDuration);
		if (mInterpolator != null)
			ani.setInterpolator(mInterpolator);
		if (mListener != null)
			ani.setAnimationListener(new AnimationListenerWrapper(mListener));

		sourceView.startAnimation(ani);

	}

	static class AnimationListenerWrapper implements AnimationListener {
		private final AnimatorListener listener;

		public AnimationListenerWrapper(AnimatorListener listener) {
			this.listener = listener;
		}

		@Override
		public void onAnimationStart(Animation animation) {
			listener.onAnimationStart(null);

		}

		@Override
		public void onAnimationEnd(Animation animation) {
			listener.onAnimationEnd(null);

		}

		@Override
		public void onAnimationRepeat(Animation animation) {
			listener.onAnimationRepeat(null);

		}
	}

}
