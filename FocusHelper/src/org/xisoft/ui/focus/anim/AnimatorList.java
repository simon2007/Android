package org.xisoft.ui.focus.anim;

import java.util.ArrayList;
import java.util.List;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.view.View;

public class AnimatorList extends FocusAnimatorBase {
	public AnimatorList() {
		super(null);

	}

	List<FocusAnimator> anims = new ArrayList<FocusAnimator>();

	@Override
	public void startAnimator(View anchorView) {
		if (0 >= anims.size())
			return;
		new FocusAnimatorProcess(anchorView, this).playNext();

	}

	static class FocusAnimatorProcess implements AnimatorListener {
		private final View anchorView;
		private final AnimatorList list;

		private int playIndex;

		public FocusAnimatorProcess(View anchorView, AnimatorList list) {
			this.anchorView = anchorView;
			this.list = list;

		}

		public void playNext() {
			if (playIndex < list.anims.size()) {
				FocusAnimator anim = list.anims.get(playIndex++);
				anim.setAnimatorListener(this);
				if (list.mDuration > 0)
					anim.setDuration(list.mDuration);
				if (list.mInterpolator != null)
					anim.setInterpolator(list.mInterpolator);
				anim.startAnimator(anchorView);
			}

		}

		@Override
		public void onAnimationStart(Animator animation) {
			if (list.mListener != null && playIndex == 1)
				list.mListener.onAnimationStart(animation);

		}

		@Override
		public void onAnimationEnd(Animator animation) {
			if (playIndex >= list.anims.size()) {
				if (list.mListener != null)
					list.mListener.onAnimationEnd(animation);
			} else
				playNext();

		}

		@Override
		public void onAnimationCancel(Animator animation) {
			if (list.mListener != null)
				list.mListener.onAnimationCancel(animation);

		}

		@Override
		public void onAnimationRepeat(Animator animation) {

		}
	}

	public void addAnimator(FocusAnimator focusAnimator) {
		anims.add(focusAnimator);

	}

}
