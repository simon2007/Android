package org.xisoft.ui.focus.anim;

import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.Animation;
import android.view.animation.Transformation;

public class FadeAndScaleAnimator extends FocusAnimatorBase {

	private final float alpha, scaleX, scaleY;

	public FadeAndScaleAnimator(View sourceView, float alpha, float scaleX, float scaleY) {
		super(sourceView);
		if (alpha > 1 || alpha < 0)
			throw new IllegalArgumentException("alpha");
		this.alpha = alpha;
		this.scaleX = scaleX;
		this.scaleY = scaleY;
	}

	@Override
	public void startAnimator(View anchorView) {
		LayoutParams params = sourceView.getLayoutParams();

		final int mStartWidth = params.width;
		final int mStartHeight = params.height;
		final int mWidth = (int) (mStartWidth * scaleX);
		final int mHeight = (int) (mStartHeight * scaleY);

		Animation ani = new Animation() {
			@Override
			protected void applyTransformation(float interpolatedTime, Transformation t) {
				int newWidth = mStartWidth + (int) ((mWidth - mStartWidth) * interpolatedTime);
				int netHeight = mStartHeight + (int) ((mHeight - mStartHeight) * interpolatedTime);
				sourceView.getLayoutParams().width = newWidth;
				sourceView.getLayoutParams().height = netHeight;
				sourceView.requestLayout();
			}

			@Override
			public boolean willChangeBounds() {
				return true;
			}
		};
		



		startAnimation(ani);
	}



}
