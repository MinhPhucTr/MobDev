package vn.uit.project.ListView;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import vn.uit.project.R;

public class AssetListAdapter extends BaseAdapter {
    final ArrayList<AssetItem> assetItems;

    public AssetListAdapter(ArrayList<AssetItem> assetItems) {
        this.assetItems = assetItems;
    }

    @Override
    public int getCount() {
        return assetItems.size();
    }

    @Override
    public Object getItem(int i) {
        return assetItems.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        View viewAsset;
        if (convertView == null) {
            viewAsset = View.inflate(viewGroup.getContext(), R.layout.asset_item_view, null);
        } else viewAsset = convertView;

        AssetItem assetItem = (AssetItem) getItem(i);
        ((TextView) viewAsset.findViewById(R.id.texAssetNameLV)).setText(String.format("%s",assetItem.assetName));
        ((TextView) viewAsset.findViewById(R.id.texLon)).setText(String.format("Longitude: %s",assetItem.lon));
        ((TextView) viewAsset.findViewById(R.id.texLat)).setText(String.format("Latitude: %s",assetItem.lat));

        return viewAsset;
    }
}
