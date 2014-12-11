package org.xisoft.ui.focus;

import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemSelectedListener;

public class ListViewFocusListener implements OnItemSelectedListener, OnFocusChangeListener {
	private OnFocusChangeListener focusChangeListener;
	private OnItemSelectedListener onItemSelectListener;
	private ListView listView;
	protected FocusWatcher watcher;

	public ListViewFocusListener(FocusWatcher watcher, ListView listView, OnFocusChangeListener focusChangeListener, OnItemSelectedListener onItemSelectListener) {
		this.watcher = watcher;
		this.listView = listView;
		this.focusChangeListener = focusChangeListener;
		this.onItemSelectListener = onItemSelectListener;
	}

	public OnFocusChangeListener getOnFocusChangeListener() {
		return focusChangeListener;
	}

	public OnItemSelectedListener getOnItemChangeListener() {
		return onItemSelectListener;
	}

	@Override
	public void onFocusChange(View v, boolean hasFocus) {

		if (focusChangeListener != null)
			focusChangeListener.onFocusChange(v, hasFocus);

		if (hasFocus && listView.getSelectedView() != null)
			watcher.onGlobalFocusChanged(null, listView.getSelectedView());

	}

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
		if (onItemSelectListener != null)
			onItemSelectListener.onItemSelected(parent, view, position, id);

		if ((parent != null && parent.isFocused()) || (view != null && view.isFocused()))
			watcher.onGlobalFocusChanged(null, view);
	}

	@Override
	public void onNothingSelected(AdapterView<?> parent) {
		if (onItemSelectListener != null)
			onItemSelectListener.onNothingSelected(parent);
	}

	public void setOnFocusChangeListener(OnFocusChangeListener focusChangeListener) {
		this.focusChangeListener = focusChangeListener;
	}

	public void setOnItemChangeListener(OnItemSelectedListener itemChangeListener) {
		this.onItemSelectListener = itemChangeListener;
	}

}