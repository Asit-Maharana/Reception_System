package com.example.receptionsystem.Adaptor;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import android.widget.Toast;
import androidx.recyclerview.widget.RecyclerView;

import com.example.receptionsystem.Model.AutoCompleteTxtModel;
import com.example.receptionsystem.R;

import java.util.ArrayList;
import java.util.List;

public class AutoCompleteTxtAdapter extends RecyclerView.Adapter<AutoCompleteTxtAdapter.MyViewHolder> implements Filterable {
    /* access modifiers changed from: private */
    public final Context context;
    /* access modifiers changed from: private */
    public List<AutoCompleteTxtModel> items;
    /* access modifiers changed from: private */
    public List<AutoCompleteTxtModel> itemsFiltered;

    public AutoCompleteTxtAdapter(Context context2, List<AutoCompleteTxtModel> items2, List<AutoCompleteTxtModel> itemsFiltered2) {
        this.context = context2;
        this.items = items2;
        this.itemsFiltered = itemsFiltered2;
    }

    public Filter getFilter() {
        return new Filter() {
            /* access modifiers changed from: protected */
            public FilterResults performFiltering(CharSequence charSequence) {
                FilterResults results = new FilterResults();
                ArrayList<AutoCompleteTxtModel> arrayListFilter = new ArrayList<>();
                if (charSequence == null || charSequence.length() == 0) {
                    results.count = AutoCompleteTxtAdapter.this.items.size();
                    results.values = AutoCompleteTxtAdapter.this.items;
                } else {
                    for (AutoCompleteTxtModel itemModel : AutoCompleteTxtAdapter.this.items) {
                        if (itemModel.getUserName().toLowerCase().contains(charSequence.toString().toLowerCase())) {
                            arrayListFilter.add(itemModel);
                        }
                    }
                    results.count = arrayListFilter.size();
                    results.values = arrayListFilter;
                }
                return results;
            }

            /* access modifiers changed from: protected */
            public void publishResults(CharSequence charSequence, FilterResults filterResults) {
                List unused = AutoCompleteTxtAdapter.this.itemsFiltered = (List) filterResults.values;
                AutoCompleteTxtAdapter.this.notifyDataSetChanged();
                if (AutoCompleteTxtAdapter.this.itemsFiltered.size() == 0) {
                    Toast.makeText(AutoCompleteTxtAdapter.this.context, "Not Found", Toast.LENGTH_LONG).show();
                }
            }
        };
    }

    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_auto_complete_txt, parent, false));
    }

    public void onBindViewHolder(MyViewHolder holder, int position) {
    }

    public int getItemCount() {
        List<AutoCompleteTxtModel> list = this.items;
        if (list == null) {
            return 0;
        }
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView name;

        public MyViewHolder(View itemView) {
            super(itemView);
            this.name = (TextView) itemView.findViewById(R.id.user_name);
        }

        public void set(AutoCompleteTxtModel item) {
            this.name.setText(item.getUserName());
        }
    }
}
