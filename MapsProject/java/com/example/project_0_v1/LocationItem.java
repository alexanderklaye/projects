package com.example.project_0_v1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;

public class LocationItem extends AppCompatActivity {
    private int mImageResource;
    private  String mLocationText;
    private String mLatLongText;
    public ImageView mDeleteImage;
    public ImageView mWishList;
    public ImageView mVisited;

    public LocationItem(int imageResource, String locationText, String latLongText){
        mImageResource = imageResource;
        mLocationText = locationText;
        mLatLongText = latLongText;

    }

    public void changeLocation(String loc)
    {
        mLocationText = loc;
    }
    public int getImageResource(){
        return mImageResource;
    }
    public String getmLocationText(){
        return mLocationText;
    }
    public String getmLatLongText(){
        return mLatLongText;
    }


}
