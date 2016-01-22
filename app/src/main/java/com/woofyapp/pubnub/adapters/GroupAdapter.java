package com.woofyapp.pubnub.adapters;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
    private GroupListClicked mClickListener;
    public GroupAdapter(List<Group> groups,GroupListClicked clickListener){
        this.groups = groups;
        this.mClickListener = clickListener;
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

    public class GroupViewHolder extends RecyclerView.ViewHolder
    implements View.OnClickListener{
        TextView groupName;
        CardView cvGroup;

        public GroupViewHolder(View view){
            super(view);
            groupName = (TextView) view.findViewById(R.id.tvGroupName);
            cvGroup = (CardView) view.findViewById(R.id.cvGroup);
            cvGroup.setOnClickListener(this);
        }

        @Override
        public void onClick(View v){
            mClickListener.onGroupItemClicked(getPosition());
        }
    }

    public interface GroupListClicked{
        public void onGroupItemClicked(int position);
    }

}
