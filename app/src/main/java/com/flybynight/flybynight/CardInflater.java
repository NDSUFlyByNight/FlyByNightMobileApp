package com.flybynight.flybynight;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.flybynight.flybynight.api.objects.Flight;

/**
 * Created by Everett on 10/23/2015.
 */
public class CardInflater implements IAdapterViewInflater<Flight>
{
	@Override
	public View inflate(final BaseInflaterAdapter<Flight> adapter, final int pos, View convertView, ViewGroup parent)
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

		final Flight item = adapter.getTItem(pos);
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

		public void updateDisplay(Flight item)
		{
			text1.setText(item.flight_num);
			text2.setText("Departure: " + item.departure_time);
			text3.setText("Arrival: " + item.arrival_time);
		}
	}
}
