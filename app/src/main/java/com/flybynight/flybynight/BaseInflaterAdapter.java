package com.flybynight.flybynight;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

public class BaseInflaterAdapter<T> extends BaseAdapter
{
	private List<T> items = new ArrayList<T>();
	private IAdapterViewInflater<T> vi;

	public BaseInflaterAdapter(IAdapterViewInflater<T> viewInflater)
	{
		vi = viewInflater;
	}

	public BaseInflaterAdapter(List<T> items, IAdapterViewInflater<T> viewInflater)
	{
		items.addAll(items);
		vi = viewInflater;
	}

	public void setViewInflater(IAdapterViewInflater<T> viewInflater, boolean notifyChange)
	{
		vi = viewInflater;

		if (notifyChange)
			notifyDataSetChanged();
	}

	public void addItem(T item, boolean notifyChange)
	{
		items.add(item);

		if (notifyChange)
			notifyDataSetChanged();
	}

	public void addItems(List<T> items, boolean notifyChange)
	{
		items.addAll(items);

		if (notifyChange)
			notifyDataSetChanged();
	}

	public void clear(boolean notifyChange)
	{
		items.clear();

		if (notifyChange)
			notifyDataSetChanged();
	}

	@Override
	public int getCount()
	{
		return items.size();
	}

	@Override
	public Object getItem(int pos)
	{
		return getTItem(pos);
	}

	public T getTItem(int pos)
	{
		return items.get(pos);
	}

	@Override
	public long getItemId(int pos)
	{
		return pos;
	}

	@Override
	public View getView(int pos, View convertView, ViewGroup parent)
	{
		return vi != null ? vi.inflate(this, pos, convertView, parent) : null;
	}
}
