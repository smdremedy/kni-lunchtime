package com.whoelse.knilunchtime;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.whoelse.knilunchtime.model.Item;
import com.whoelse.knilunchtime.model.Supplier;

import java.util.List;

public class MenuArrayAdapter extends ArrayAdapter<Item>{

    private final LayoutInflater mInflater;
    private final int mResourceId;

    public MenuArrayAdapter(Context context, int resource) {

        super(context, resource);

        mInflater = LayoutInflater.from(context);
        mResourceId = resource;
    }

    public void setSuppliers(Supplier[] suppliers) {
        clear();

        for (Supplier supplier : suppliers) {
            for (Item item : supplier.items) {
                add(item);
                item.supplier = supplier;
            }
        }

        if (isEmpty()) {
            notifyDataSetInvalidated();
        } else {
            notifyDataSetChanged();
        }
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = mInflater.inflate(mResourceId, viewGroup, false);
            viewHolder.mItemNameTextView = (TextView) convertView.findViewById(R.id.item_name_tv);
            viewHolder.mSupplierNameTextView = (TextView) convertView.findViewById(R.id.item_supplier_name_tv);
            viewHolder.mPriceTextView = (TextView) convertView.findViewById(R.id.item_price_tv);
            viewHolder.mSoupIconImageView = (ImageView) convertView.findViewById(R.id.soup_indicator_iv);
            viewHolder.mItemContainerLinearLayout = (LinearLayout) convertView.findViewById(R.id.item_on_list_ll);
            viewHolder.mOrderedIconImageView = (ImageView)convertView.findViewById(R.id.ordered_ico_iv);
            viewHolder.mPriceLinearLayout = (LinearLayout)convertView.findViewById(R.id.item_price_and_soup_ll);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Item item = getItem(i);

        viewHolder.mItemNameTextView.setText(item.name);
        viewHolder.mPriceTextView.setText(String.valueOf(item.price));
        viewHolder.mSupplierNameTextView.setText(item.supplier.name);
        viewHolder.mSoupIconImageView.setVisibility(item.options.length > 0 ? View.VISIBLE : View.INVISIBLE);
        if (item.order == null) {
            viewHolder.mPriceLinearLayout.setVisibility(View.VISIBLE);
            viewHolder.mOrderedIconImageView.setVisibility(View.GONE);
            viewHolder.mSupplierNameTextView.setTextColor(getContext().getResources().getColor(R.color.green));
            viewHolder.mItemContainerLinearLayout.setBackgroundResource(R.color.white);
        } else {
            viewHolder.mPriceLinearLayout.setVisibility(View.GONE);
            viewHolder.mOrderedIconImageView.setVisibility(View.VISIBLE);
            viewHolder.mSupplierNameTextView.setTextColor(getContext().getResources().getColor(R.color.white));
            viewHolder.mItemContainerLinearLayout.setBackgroundResource(R.color.green);
        }

        return convertView;
    }

    private class ViewHolder {
        TextView mItemNameTextView;
        TextView mSupplierNameTextView;
        TextView mPriceTextView;
        ImageView mSoupIconImageView;
        LinearLayout mItemContainerLinearLayout;
        ImageView mOrderedIconImageView;
        LinearLayout mPriceLinearLayout;
    }



}
