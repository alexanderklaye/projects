package com.example.project_0_v1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    // members
    private ArrayList<LocationItem> mLocationList;
    private  RecyclerView mRecyclerView;
    private LocationAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    //UI items
    private Button btnClearAll;
    private Button btnAbout;
    private Button btnNewLocation;
    private TextView txtLocationName;
    private TextView txtLatLong;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        createExampleList(); // for testing purposes
        buildRecyclerView();
        setButtons();
    }
    public void insertItem(int p){
        try{
            mLocationList.add(p, new LocationItem(R.drawable.ic_place_black_24dp, "New Item @ Pos" + p, "line2 @ pos " + p));
            // notify adapter
            mAdapter.notifyDataSetChanged(); // this will refresh the whole list, so no animation
            //mAdapter.notifyItemInserted(p); // this will allow animations
        }
        catch (Exception e){

        }
    }
    public void removeItem(int p){
        try{
            if(mExampleList.size() != 0)
            {
                mExampleList.remove(p);
                // notify adapter
                //mAdapter.notifyDataSetChanged();// refreshes the whole list; avoid it if we can
                mAdapter.notifyItemRemoved(p);
            }
        }
        catch(Exception e)
        {
            System.out.println("error:" + e.toString());
        }
    }

    //
    public void changeItem(int position, String text)
    {
        mExampleList.get(position).changeText1(text); // This makes it more portable since it's in a method of its own I guess?
        // note: recall that we're going to need to notify the adapter that data has changed:
        mAdapter.notifyItemChanged(position);

    }


    // inits
    public void createExampleList(){
        mExampleList = new ArrayList<>();
        mExampleList.add(new ExampleItem(R.drawable.ic_local,"Line1", "line2"));
        mExampleList.add(new ExampleItem(R.drawable.ic_build,"Line3", "line4"));
        mExampleList.add(new ExampleItem(R.drawable.ic_android,"Line5", "line6"));
    }
    public void buildRecyclerView(){
        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);// this means the layout wont change size; this improves performance

        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new ExampleAdapter(mExampleList);

        mRecyclerView.setLayoutManager(mLayoutManager); // actually pass the manager to the view
        mRecyclerView.setAdapter(mAdapter); // passing the adapter to the view
        // we add this for teh click listeners:
        mAdapter.setOnItemClickListener(new ExampleAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                // with this method, we can handle the clicks on the items
                // We take the data (mExampleList) and pass the position of the clikc event:
                //mExampleList.get(position).changeText1("Clicked"); // we want to change the name, we we make a method in the exampleitem class to do this
                // note: we can alternatively put this ^^ into a separate method that the click can call (makes it more portable, I guess?)
                // then we can simply call the method:
                changeItem(position, "Clicked"); //
            }
            // note: Remember that because we made a new method in the interface, we have to build it here.
            @Override
            public void onDeleteClick(int position)
            {
                // We already have a remove item method, so we just call it here
                removeItem(position); // this will be passed by the adapter?
            }
        });
    }

    public void setButtons(){
        // Note: since we had a lot of items to init, we just put them all into a method to clean up the init method
        //init components
        buttonInsert = findViewById(R.id.button_insert);
        buttonRemove = findViewById(R.id.button_remove);
        editTextInsert = findViewById(R.id.edittext_insert);
        editTextRemove = findViewById(R.id.edittext_remove);



        // listeners
        buttonInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = Integer.parseInt(editTextInsert.getText().toString());
                insertItem(position);
            }
        });
        buttonRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = Integer.parseInt(editTextRemove.getText().toString());
                removeItem(position);

            }
        });

    }



}
