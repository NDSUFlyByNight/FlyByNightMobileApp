package com.flybynight.flybynight;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Everett on 10/23/2015.
 */
public class CardInflater implements IAdapterViewInflater<Card>
{
	@Override
	public View inflate(final BaseInflaterAdapter<Card> adapter, final int pos, View convertView, ViewGroup parent)
	{
		ViewHolder holder;

		if (convertView == null)
		{
			LayoutInflater inflater = LayoutInflater.from(parent.getContext());
			convertView = inflater.inflate(R.layout.list_item_card, parent, false);
			holder = new ViewHolder(convertView);
		}
		else
		{
			holder = (ViewHolder) convertView.getTag();
		}

		final Card item = adapter.getTItem(pos);
		holder.updateDisplay(item);

		return convertView;
	}

	private class ViewHolder
	{
		private View rootView;
		private TextView text1;
		private TextView text2;
		private TextView text3;

		public ViewHolder(View rootView)
		{
			rootView = rootView;
			text1 = (TextView) rootView.findViewById(R.id.text1);
			text2 = (TextView) rootView.findViewById(R.id.text2);
			text3 = (TextView) rootView.findViewById(R.id.text3);
			rootView.setTag(this);
		}

		public void updateDisplay(Card item)
		{
			text1.setText(item.getText1());
			text2.setText(item.getText2());
			text3.setText(item.getText3());
		}
	}
}
