package com.woofyapp.pubnub.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import android.view.ViewGroup;
import android.widget.TextView;

import com.woofyapp.pubnub.R;
import com.woofyapp.pubnub.database.Group;

import java.util.List;


/**
 * Created by rujul on 1/21/2016.
 */
public class GroupAdapter extends RecyclerView.Adapter<GroupAdapter.GroupViewHolder>{

    List<Group> groups;

    public GroupAdapter(List<Group> groups){
        this.groups = groups;
    }

    @Override
    public GroupViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.group_child,parent,false);
        GroupViewHolder vh = new GroupViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(GroupViewHolder holder, int position) {
        holder.groupName.setText(groups.get(position).getGroupName());
    }

    @Override
    public int getItemCount() {
        return groups.size();
    }

    public static class GroupViewHolder extends RecyclerView.ViewHolder {
        TextView groupName;

        public GroupViewHolder(View view){
            super(view);
            groupName = (TextView) view.findViewById(R.id.tvGroupName);
        }
    }

}
