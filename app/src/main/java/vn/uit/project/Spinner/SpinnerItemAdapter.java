package vn.uit.project.Spinner;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

import vn.uit.project.R;

public class SpinnerItemAdapter extends ArrayAdapter<SpinnerItem> {
    public SpinnerItemAdapter(@NonNull Context context, int resource, @NonNull List<SpinnerItem> objects) {
        super(context, resource, objects);
    }

    public SpinnerItemAdapter(@NonNull Context context, int resource) {
        super(context, resource);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_selected, parent, false);
        TextView texItemSelected = (TextView) convertView.findViewById(R.id.texItemSelected);
        ImageView imaSymbol = (ImageView) convertView.findViewById(R.id.imaSymbol);
        SpinnerItem spinnerItem = this.getItem(position);
        if(spinnerItem != null)
        {
            texItemSelected.setText(spinnerItem.getName().toString());
            imaSymbol.setImageResource(spinnerItem.getImg());
        }
        return convertView;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_dropdown, parent, false);
        TextView texItem = (TextView) convertView.findViewById(R.id.texItem);
        ImageView imaItem = (ImageView) convertView.findViewById(R.id.imaItem);
        SpinnerItem spinnerItem = this.getItem(position);
        if(spinnerItem != null)
        {
            texItem.setText(spinnerItem.getName().toString());
            imaItem.setImageResource(spinnerItem.getImg());
        }
        return convertView;
    }
}
