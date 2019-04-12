/*<!--// Name                 Andrew MacFarlane-->
        <!--// Student ID           S1511223-->
        <!--// Programme of Study   Computer Games(Software Development)-->
        <!--//-->*/
package com.example.mpd_cw;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import android.widget.Toast;
import android.view.View.OnClickListener;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> implements Filterable {

    private List<Earthquake> quakes;
    private CardListener mCardListener;
    private List<Earthquake> quakesFull;
    private Context context;

    public RecyclerAdapter(List<Earthquake> _quakes, Context _context, CardListener cardListener) {
        this.quakes = _quakes;
        this.quakesFull = new ArrayList<>(_quakes); //copy of list for searching purposes
        this.context = _context;
        this.mCardListener = cardListener;

        Log.d("andrew", "size" + quakes.size());
    }


    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d("andrew", "in here 2");
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.rec_item, parent, false);
        return new ViewHolder(v, mCardListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        Earthquake e = quakes.get(position);
        Log.d("andrew", "e = " + e.GetLoc());
        viewHolder.textViewHead.setText(e.GetLoc());
        viewHolder.textViewDesc.setText(e.GetFullDate() + "   "+ e.GetFullTime());
        viewHolder.textViewMag.setText(Float.toString(e.GetMag()));

    }

    @Override
    public int getItemCount() {
        return quakes.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements OnClickListener {
        public TextView textViewHead;
        public TextView textViewDesc;
        public TextView textViewMag;
        CardListener cardListener;

        public ViewHolder(@NonNull View itemView, CardListener cardListener) {
            super(itemView);

            textViewHead = (TextView) itemView.findViewById(R.id.textViewHead);
            textViewDesc = (TextView) itemView.findViewById(R.id.textViewDesc);
            textViewMag = (TextView) itemView.findViewById(R.id.textViewMag);
            this.cardListener = cardListener;

            itemView.setOnClickListener(this);
        }


        @Override
        public void onClick(View v) {
            Log.d("andrew", "Test");
            cardListener.OnCardClick(getAdapterPosition());
        }
    }

    public interface CardListener{
        void OnCardClick(int pos);
    }

    @Override
    public Filter getFilter() {
        return searchFilter;
    }

    private Filter searchFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
           List<Earthquake> filteredList = new ArrayList<>();
           if (constraint == null || constraint.length() == 0)
           {
               filteredList.addAll(quakesFull);
           }
           else
           {
               String filterPattern = constraint.toString().toLowerCase().trim();
               for (Earthquake item : quakesFull)
               {
                    if(item.GetLoc().toLowerCase().contains(filterPattern))
                    {
                        filteredList.add(item);

                    }
               }

           }
           FilterResults results = new FilterResults();
           results.values = filteredList;
           return results;

        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            quakes.clear();
            quakes.addAll((List)results.values);
            notifyDataSetChanged();
        }
    };

    public void onClick(View view) {
        Log.d("andrew","Test");


    }



}
