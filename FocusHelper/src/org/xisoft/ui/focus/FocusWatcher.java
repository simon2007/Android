package org.xisoft.ui.focus;

import android.util.Log;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnGlobalFocusChangeListener;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.ViewTreeObserver.OnScrollChangedListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ListView;

public class FocusWatcher implements OnGlobalFocusChangeListener, OnScrollChangedListener, OnGlobalLayoutListener {
	protected final FocusChangeListener mFocusListener;
	protected boolean isPaused = false;

	protected View mLastFocusControl;
	private int repeatCount;

	public interface FocusChangeListener {
		public void onFocusChanged(View newFocus, int repeatCount);
	};

	public FocusWatcher(FocusChangeListener focusListener) {
		this.mFocusListener = focusListener;
	}

	public void addWatch(ViewGroup view) {
		ViewTreeObserver observer = view.getViewTreeObserver();

		observer.addOnGlobalFocusChangeListener(this);
		observer.addOnScrollChangedListener(this);
		observer.addOnGlobalLayoutListener(this);
	}

	protected ListViewFocusListener createListViewFocusListener(ListView listView, OnFocusChangeListener onFocusChangeListener, OnItemSelectedListener onItemSelectListener) {
		return new ListViewFocusListener(this, listView, onFocusChangeListener, onItemSelectListener);
	}

	protected View getFocusable(View newFocus) {

		return newFocus;
	}

	public boolean isPaused() {
		return isPaused;
	}

	@Override
	public void onGlobalFocusChanged(View oldFocus, View newFocus) {
		if (isPaused)
			return;
		if (newFocus != null) {
			if (newFocus instanceof ListView) {
				ListView listView = (ListView) newFocus;
				OnItemSelectedListener onItemSelectListener = listView.getOnItemSelectedListener();
				OnFocusChangeListener onFocusChangeListener = listView.getOnFocusChangeListener();

				if (onItemSelectListener == null || onItemSelectListener != onFocusChangeListener || !(onItemSelectListener instanceof ListViewFocusListener)) {
					if (onItemSelectListener != null && onItemSelectListener instanceof ListViewFocusListener)
						onItemSelectListener = ((ListViewFocusListener) onItemSelectListener).getOnItemChangeListener();
					if (onFocusChangeListener != null && onFocusChangeListener instanceof ListViewFocusListener)
						onFocusChangeListener = ((ListViewFocusListener) onFocusChangeListener).getOnFocusChangeListener();

					ListViewFocusListener listener = createListViewFocusListener(listView, onFocusChangeListener, onItemSelectListener);
					listView.setOnItemSelectedListener(listener);
					listView.setOnFocusChangeListener(listener);
				}

				return;
			}
			View anchorView = getFocusable(newFocus);

			if (anchorView == null) {
				Log.d("FocusHelper", "newFocus=" + anchorView);
				mFocusListener.onFocusChanged(null, repeatCount = 0);
				// 不是焦点控件(Focusable)，控件自己处理
				mLastFocusControl = null;

				return;
			}

			if (mLastFocusControl == anchorView)
				repeatCount++;
			else
				repeatCount = 0;
			mFocusListener.onFocusChanged(anchorView, repeatCount);
			mLastFocusControl = anchorView;
		} else {
			Log.d("FocusHelper", "has no focus");
			// 这个页面没有任何控件可以获取焦点
			mFocusListener.onFocusChanged(null, repeatCount = 0);
			mLastFocusControl = null;

		}
	}

	@Override
	public void onGlobalLayout() {
		if (isPaused || mLastFocusControl == null)
			return;
		mFocusListener.onFocusChanged(mLastFocusControl, repeatCount++);

	}

	@Override
	public void onScrollChanged() {
		onGlobalLayout();
	}

	public void pause() {
		isPaused = true;
	}

	public void removeWatch(ViewGroup view) {
		ViewTreeObserver observer = view.getViewTreeObserver();
		observer.removeOnGlobalFocusChangeListener(this);
		observer.removeOnScrollChangedListener(this);
		observer.removeGlobalOnLayoutListener(this);

	}

	public void resume() {
		boolean oldIsPaused = isPaused;
		isPaused = false;
		if (oldIsPaused)
			onGlobalLayout();

	}
}
