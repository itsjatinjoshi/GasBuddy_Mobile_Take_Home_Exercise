package com.example.gasbuddymobiletakehomeexercise.ViewModel;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.gasbuddymobiletakehomeexercise.Model.Unsplash;
import com.example.gasbuddymobiletakehomeexercise.R;
import com.example.gasbuddymobiletakehomeexercise.View.MainActivity;

import java.util.ArrayList;
import java.util.List;

public class UnsplashAdapter extends RecyclerView.Adapter<UnsplashAdapter.ViewHolder> {
    private static final String TAG = "UnsplashAdapter";
    private ArrayList<Unsplash> mUnsplashArrayList;
    private ArrayList<Unsplash> mUnsplashArrayListFiltered;
    private Context context;

    private View.OnClickListener mOnClickListener;

    public void setOnClickListener(View.OnClickListener onClickListener) {
        mOnClickListener = onClickListener;
    }

    public UnsplashAdapter(ArrayList<Unsplash> unsplashArrayList, Context context) {
        mUnsplashArrayList = unsplashArrayList;
        this.context = context;
        this.mUnsplashArrayListFiltered = unsplashArrayList;

    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.photos_cardview, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //for Animations on card view
        holder.ivThumbnail.setAnimation(AnimationUtils.loadAnimation(context, R.anim.face_animations));
        holder.mCardView.setAnimation(AnimationUtils.loadAnimation(context, R.anim.cardview_animations));

        holder.tvName.setText(mUnsplashArrayListFiltered.get(position).getUser().getName());
        Glide.with(context).load(mUnsplashArrayListFiltered.get(position).getUrls().getSmall()).override(70, 70).into(holder.ivThumbnail);
    }


    @Override
    public int getItemCount() {

        return mUnsplashArrayListFiltered == null ? 0 : mUnsplashArrayListFiltered.size();
    }

    public Unsplash getItem(int position) {
        return mUnsplashArrayListFiltered.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public Filter getFilter() {
        Filter filter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults filterResults = new FilterResults();
                if (constraint == null || constraint.length() == 0) {
                    filterResults.count = mUnsplashArrayList.size();
                    filterResults.values = mUnsplashArrayList;
                } else {
                    List<Unsplash> searchByName = new ArrayList<>();
                    String searchStr = constraint.toString().toLowerCase();

                    for (Unsplash items : mUnsplashArrayList) {
                        if (items.getUser().getName().toLowerCase().contains(searchStr)) {
                            searchByName.add(items);
                        }
                        filterResults.count = searchByName.size();
                        filterResults.values = searchByName;
                    }
                }
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                mUnsplashArrayListFiltered = (ArrayList<Unsplash>) results.values;
                MainActivity.imageList = (ArrayList<Unsplash>) results.values;
                notifyDataSetChanged();
            }
        };
        return filter;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private static final String TAG = "ViewHolder";
        ImageView ivThumbnail;
        TextView tvName;
        CardView mCardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivThumbnail = itemView.findViewById(R.id.ivThumbnail);
            tvName = itemView.findViewById(R.id.tvHeading);

            mCardView= itemView.findViewById(R.id.cardView);


            Log.d(TAG, "ViewHolder: " + itemView.getId());
            itemView.setTag(this);
            itemView.setOnClickListener(mOnClickListener);

        }
    }
}
