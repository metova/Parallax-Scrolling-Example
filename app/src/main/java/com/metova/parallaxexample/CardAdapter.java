package com.metova.parallaxexample;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class CardAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private int spacerBottom = 0;
    private List<Object> mObjects = new ArrayList<>();

    public CardAdapter(Context context, int spacerBottom, List<Object> objects) {
        mContext = context;
        this.spacerBottom = spacerBottom;
        mObjects = objects;
    }

    public void setObjects(List<Object> objects) {
        mObjects = objects;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = null;
        switch(viewType) {

            case 0:

                // Header, make a spacer
                itemView = new View(mContext);
                parent.addView(itemView);
                HeaderHolder headerHolder = new HeaderHolder(itemView);
                return headerHolder;

            case 1:

                itemView = LayoutInflater.from(mContext).inflate(R.layout.item_card, parent, false);
                CardHolder cardHolder = new CardHolder(itemView);
                return cardHolder;
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {

        int viewType = getItemViewType(position);

        switch(viewType) {

            case 0:

                setupHeader((HeaderHolder) viewHolder, position);
                break;

            case 1:

                setupCard((CardHolder) viewHolder, position);
                break;
        }
    }

    private void setupHeader(HeaderHolder holder, int position) {

        // Setup the header spacing
        ViewGroup.LayoutParams lp = holder.itemView.getLayoutParams();
        lp.height = spacerBottom;
        holder.itemView.setLayoutParams(lp);
    }

    private void setupCard(CardHolder holder, int position) {

        Object object = getItem(position);

        // Bind the holder here
    }

    public Object getItem(int position) {

        return (mObjects == null || position >= mObjects.size() ? null : mObjects.get(position));
    }

    // 0 = header, 1 = card
    @Override
    public int getItemViewType(int position) {

        if(position == 0) { return 0; }
        else { return 1; }
    }

    @Override
    public int getItemCount() {
        return mObjects.size();
    }

    public class CardHolder extends RecyclerView.ViewHolder {

        public CardHolder(View itemView) {
            super(itemView);
        }
    }

    public class HeaderHolder extends RecyclerView.ViewHolder {

        View itemView;

        public HeaderHolder(View itemView) {

            super(itemView);
            this.itemView = itemView;
        }
    }
}
