package com.example.receptionsystem.Adaptor;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.receptionsystem.Interface.OnClickInterface;
import com.example.receptionsystem.R;
import com.example.receptionsystem.Activity.RegistrationActivity;
import com.example.receptionsystem.Model.VisitorResponse;

import java.util.List;

public class RecycleAdapter extends RecyclerView.Adapter<RecycleAdapter.ListViewHolder> {
    Context context;
    List<VisitorResponse> list;
    OnClickInterface clickListener;
    public RecycleAdapter(List<VisitorResponse> data, RegistrationActivity registrationActivity, OnClickInterface clickListener){
        this.list=data;
        this.context=registrationActivity;
        this.clickListener=clickListener;
    }

    public ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        View view =inflater.inflate(R.layout.list_item_layout,parent,false);
        return new ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListViewHolder holder, int Position) {
        holder.name.setText(list.get(Position).getName());
        holder.mobileNo.setText(list.get(Position).getMobileNo());
        holder.id.setText(String.valueOf(list.get(Position).getVisitorId()));
        holder.exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                Toast.makeText(context,"Exit button clicked ",Toast.LENGTH_LONG).show();
                clickListener.onClick(list.get(Position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ListViewHolder  extends RecyclerView.ViewHolder {
        TextView name,mobileNo,id;
        View exit;
        public ListViewHolder(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.Name);
            mobileNo= itemView.findViewById(R.id.MobileNumber);
            id=itemView.findViewById(R.id.id);
            exit = itemView.findViewById(R.id.exit);

        }
    }
}