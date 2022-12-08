package com.example.receptionsystem.Adaptor;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.receptionsystem.Model.HistoryVisitorResponse;
import com.example.receptionsystem.R;

import java.util.List;

public class HistoryRecycleAdapter extends RecyclerView.Adapter<HistoryRecycleAdapter.ListViewHolder> {
    List<HistoryVisitorResponse> list;
    public HistoryRecycleAdapter(List<HistoryVisitorResponse> historyResponseVisitorResponse) {
        this.list=historyResponseVisitorResponse;
    }

    @NonNull
    @Override
    public HistoryRecycleAdapter.ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.history_list_layout, parent, false);
        return new ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryRecycleAdapter.ListViewHolder holder, int position) {
        holder.inTime.setText((list.get(position).getInTime()).substring(10,16));
        holder.OutTime.setText(list.get(position).getOutTime());
        holder.id.setText(list.get(position).getVisitorId());
        holder.name.setText(list.get(position).getName());
        holder.mobileNumber.setText(list.get(position).getMobileNo());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public String InTime(List<HistoryVisitorResponse> list,int position)
    {
        String inTime=list.get(position).getInTime();
        String Time= inTime.substring(10,16);
        return null;
    }

    public class ListViewHolder extends RecyclerView.ViewHolder {
        TextView inTime,OutTime,id,name,mobileNumber;
        public ListViewHolder(@NonNull View itemView) {
            super(itemView);
            inTime=itemView.findViewById(R.id.InTime);
            OutTime=itemView.findViewById(R.id.OutTime);
            id=itemView.findViewById(R.id.HistoryId);
            name=itemView.findViewById(R.id.HistoryName);
            mobileNumber=itemView.findViewById(R.id.HistoryMobileNumber);

        }
    }
}
