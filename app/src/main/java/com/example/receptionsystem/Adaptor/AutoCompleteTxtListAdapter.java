package com.example.receptionsystem.Adaptor;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.receptionsystem.Model.AutoCompleteTxtModel;
import com.example.receptionsystem.R;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class AutoCompleteTxtListAdapter extends ArrayAdapter<AutoCompleteTxtModel> {
    /* access modifiers changed from: private */
    public Context context;
    private List<AutoCompleteTxtModel> items;
    Filter modelFilter = new Filter() {
        public CharSequence convertResultToString(Object resultValue) {
            return ((AutoCompleteTxtModel) resultValue).getUserName();
        }

        /* access modifiers changed from: protected */
        public FilterResults performFiltering(CharSequence charSequence) {
            if (charSequence == null) {
                return new FilterResults();
            }
            AutoCompleteTxtListAdapter.this.suggestions.clear();
            for (AutoCompleteTxtModel fruit : AutoCompleteTxtListAdapter.this.tempItems) {
                if (fruit.getUserName().toLowerCase().startsWith(charSequence.toString().toLowerCase())) {
                    AutoCompleteTxtListAdapter.this.suggestions.add(fruit);
                }
            }
            FilterResults filterResults = new FilterResults();
            filterResults.values = AutoCompleteTxtListAdapter.this.suggestions;
            filterResults.count = AutoCompleteTxtListAdapter.this.suggestions.size();
            return filterResults;
        }

        /* access modifiers changed from: protected */
        public void publishResults(CharSequence charSequence, FilterResults filterResults) {
            ArrayList<AutoCompleteTxtModel> tempValues = (ArrayList) filterResults.values;
            if (filterResults == null || filterResults.count <= 0) {
                AutoCompleteTxtListAdapter.this.clear();
                AutoCompleteTxtListAdapter.this.notifyDataSetChanged();
                return;
            }
            AutoCompleteTxtListAdapter.this.clear();
            Iterator<AutoCompleteTxtModel> it = tempValues.iterator();
            while (it.hasNext()) {
                AutoCompleteTxtListAdapter.this.add(it.next());
            }
            AutoCompleteTxtListAdapter.this.notifyDataSetChanged();
        }
    };
    private int resourceId;
    /* access modifiers changed from: private */
    public List<AutoCompleteTxtModel> suggestions;
    /* access modifiers changed from: private */
    public List<AutoCompleteTxtModel> tempItems;

    public AutoCompleteTxtListAdapter(Context context2, int resourceId2, List<AutoCompleteTxtModel> items2) {
        super(context2, resourceId2, items2);
        this.context = context2;
        this.items = items2;
        this.resourceId = resourceId2;
        this.tempItems = new ArrayList(items2);
        this.suggestions = new ArrayList();
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (convertView == null) {
            try {
                view = ((Activity) this.context).getLayoutInflater().inflate(this.resourceId, parent, false);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        final AutoCompleteTxtModel model = getItem(position);
        ((TextView) view.findViewById(R.id.user_name)).setText(model.getUserName());
        ((ImageView) view.findViewById(R.id.image_view)).setOnClickListener(view1 -> {
            Context access$000 = AutoCompleteTxtListAdapter.this.context;
            Toast.makeText(access$000, "" + model.getMobileNo(), 0).show();
            Intent callIntent = new Intent("android.intent.action.CALL");
            callIntent.setData(Uri.parse("tel:" + model.getMobileNo()));
            AutoCompleteTxtListAdapter.this.context.startActivity(callIntent);
        });
        return view;
    }

    public AutoCompleteTxtModel getItem(int position) {
        return (AutoCompleteTxtModel) super.getItem(position);
    }

    public int getCount() {
        return this.items.size();
    }

    public long getItemId(int position) {
        return (long) position;
    }

    public Filter getFilter() {
        return this.modelFilter;
    }
}
