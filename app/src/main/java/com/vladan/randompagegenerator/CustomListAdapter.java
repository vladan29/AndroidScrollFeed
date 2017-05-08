package com.vladan.randompagegenerator;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import java.util.List;

/**
 * Created  on 5/7/2017.
 */

public class CustomListAdapter extends BaseAdapter {

    private Activity activity;
    private LayoutInflater inflater;
   private List<BasicParameters> bpItems;

    ImageLoader imageLoader;


    public CustomListAdapter(Activity activity, List<BasicParameters> bpItems) {
        this.activity = activity;
        this.bpItems = bpItems;

    }

    private class ViewHolder {
        NetworkImageView image;
        TextView number;
        TextView page;
        TextView count;
    }

    @Override
    public int getCount() {
        return bpItems.size();
    }

    @Override
    public Object getItem(int position) {
        return bpItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final ViewHolder holder;

        if (inflater == null)
            inflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (imageLoader == null)
            imageLoader = AppController.getInstance().getImageLoader();
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.list_item, null);

            holder = new ViewHolder();
            holder.image = (NetworkImageView) convertView.findViewById(R.id.image5);
            holder.number = (TextView) convertView.findViewById(R.id.textView);
            holder.page=(TextView)convertView.findViewById(R.id.textPage);
            holder.count=(TextView)convertView.findViewById(R.id.textCount);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        final BasicParameters bP=bpItems.get(position);
        holder.number.setText(bP.getNumber());
        holder.page.setText("This is page:"+ " "+bP.getPage());
        holder.count.setText("Total item count"+" "+bP.getTotalItemCount());
        holder.image.setImageUrl(bP.getUrl(), imageLoader);


        return convertView;
    }


}
