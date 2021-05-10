package com.example.dietapp.Adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.example.dietapp.R;

import java.util.List;
import java.util.Map;

public class ExpendableListAdapter extends BaseExpandableListAdapter {
    private Context context;
    private List<String> listTitle;
    private Map<String,List<String>> listItem;

    public ExpendableListAdapter(Context context, List<String> listTitle, Map<String, List<String>> listItem) {
        this.context = context;
        this.listTitle = listTitle;
        this.listItem = listItem;
    }


    @Override
    public int getGroupCount() {
        return listTitle.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return listItem.size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return listTitle.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {

        if (listItem.get(listTitle.get(groupPosition)).get(childPosition)!=null)
            return listItem.get(listTitle.get(groupPosition)).get(childPosition) ;
        return null;
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        String title= (String) getGroup(groupPosition);
        if(convertView==null){
            convertView= LayoutInflater.from(context).inflate(R.layout.navlist_group,null);
        }

        TextView textTitle=(TextView) convertView.findViewById(R.id.listTitle);
        textTitle.setTypeface(null, Typeface.BOLD);
        textTitle.setText(title);

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        String title= (String) getChild(groupPosition,childPosition);
        if(convertView==null){
            convertView= LayoutInflater.from(context).inflate(R.layout.navlist_item,null);
        }

        TextView textChild=(TextView) convertView.findViewById(R.id.expendableListItem);
        textChild.setText(title);

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
