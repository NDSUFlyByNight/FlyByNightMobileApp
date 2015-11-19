package com.flybynight.flybynight;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.flybynight.flybynight.IAdapterViewInflater;
import com.flybynight.flybynight.api.objects.Passenger;

/**
 * Created by Everett on 11/18/2015.
 */
public class PassengerInflater implements IAdapterViewInflater<Passenger> {

    @Override
    public View inflate(final BaseInflaterAdapter<Passenger> adapter, final int pos, View convertView, ViewGroup parent)
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

        final Passenger item = adapter.getTItem(pos);
        holder.updateDisplay(item);

        return convertView;
    }

    private class ViewHolder
    {
        private View rootView;
        private TextView text1;
        private TextView text2;

        public ViewHolder(View rootView)
        {
            rootView = rootView;
            text1 = (TextView) rootView.findViewById(R.id.text1);
            text2 = (TextView) rootView.findViewById(R.id.text2);
            rootView.setTag(this);
        }

        public void updateDisplay(Passenger item)
        {
            text1.setText("Name: "+item.first_name+ " " + item.last_name);
            text2.setText("Seat: " + item.row+item.rowId);
        }
    }
}

