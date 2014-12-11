package org.xisoft.ui.focus.anim;

import android.view.View;
import android.view.ViewPropertyAnimator;

public class FadeAnimator extends FocusAnimatorBase {
	private final float alpha;

	public FadeAnimator(View sourceView, float alpha) {
		super(sourceView);
		if (alpha > 1 || alpha < 0)
			throw new IllegalArgumentException("alpha");
		this.alpha = alpha;
	}

	@Override
	public void startAnimator(View anchorView) {
		ViewPropertyAnimator anim = sourceView.animate();
		anim.alpha(alpha);
		startAnimator(anim);
	}

}
