package com.example.project_0_v1;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class LocationAdapter  {


    private ArrayList<LocationItem> mLocationList;
    private OnItemClickListener mListener;

    public interface  OnItemClickListener{
        void onItemClick(int position);
        void onDeleteClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        mListener = listener;
    } // will pass this in the main activity




    @NonNull
    public LocationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_location_item, parent, false);
        LocationViewHolder lvh = new LocationViewHolder(v, mListener); // since we changed to ctor to include hte itemclicklistener, we modify this to take that in too
        return lvh; // this will hold the view?
    }

    //
    public LocationAdapter(ArrayList<LocationItem> exampleList){
        mLocationList = exampleList;
    }

    public void onBindViewHolder(@NonNull LocationViewHolder holder, int position) {
        // will pass the views on in here
        LocationItem currentItem = mLocationList.get(position);
        holder.mLocationImage.setImageResource(currentItem.getImageResource());
        holder.mWishImage.setImageResource(currentItem.getImageResource());
        holder.mLocationImage.setImageResource(currentItem.getImageResource());

        holder.mLocationName.setText(currentItem.getmLocationText());
        holder.mLatLong.setText(currentItem.getmLatLongText());

    }

    public int getItemCount() {
        // defines the # of items in the list
        return mLocationList.size(); // returns the # of items in the list
    }

    // needs a view holder
    public static class LocationViewHolder extends RecyclerView.ViewHolder{
        public ImageView mLocationImage;
        public TextView mLocationName;
        public TextView mLatLong;
        public ImageView mWishImage;
        public ImageView mVisitImage;
        public ImageView mDeleteImage;



        public LocationViewHolder(@NonNull View itemView, final OnItemClickListener listner) {
            super(itemView);
            // after super, we get the items from the ids
            mLocationImage = itemView.findViewById(R.id.imgLocation);
            mLocationName = itemView.findViewById(R.id.txtLocationName);
            mLatLong = itemView.findViewById(R.id.txtLatLong); // note: we'l pass these in the onbindview above
            mWishImage = itemView.findViewById(R.id.imgWishLocation);
            mVisitImage = itemView.findViewById(R.id.imgVisitedLocation);
            mDeleteImage = itemView.findViewById(R.id.imgDeleteLocation);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // want to pass this click to the main; but we can't access the mListener var because it's in a static class.
                    // So, we pass the listener into the ctor of theh ExampleViewHolder: add 'OnItemClickListener listener'
                    // Once we've done that, we can access the listener.
                    if(listner != null){
                        int position = getAdapterPosition(); // provides the position for us
                        if(position != RecyclerView.NO_POSITION)
                        {
                            // ^^ to ensure the position is valid; such as clicking a card recently deleted
                            // then call the listener:\
                            listner.onItemClick(position); // passing the position of the card that we're clicking on
                        }
                    }
                }
            });

            // for the delete click, we need to make another click listener
            mDeleteImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // It's going to be the same as the itemview one:
                    if(listner != null){
                        int position = getAdapterPosition(); // provides the position for us
                        if(position != RecyclerView.NO_POSITION)
                        {
                            listner.onDeleteClick(position); // have to make a method and interface addition for the item delete
                        }
                    }
                }
            });
        }
    }
}
