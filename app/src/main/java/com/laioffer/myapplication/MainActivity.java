package com.laioffer.myapplication;

//import android.app.Fragment;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.laioffer.myapplication.CommentFragment;
import com.laioffer.myapplication.EventFragment;
import com.laioffer.myapplication.R;
import com.laioffer.myapplication.User;

public class MainActivity extends AppCompatActivity implements EventFragment.OnItemSelectListener, CommentFragment.OnItemSelectListenerInGrid  {
    private EditText mUsernameEditText;
    private EditText mPasswordEditText;
    private Button mSubmitButton;
    private Button mRegisterButton;
    private DatabaseReference mDatabase;

    // fragments的交流：activity 实现 fragment1 的接口，调用 fragment2的 function
    EventFragment listFragment;
    CommentFragment gridFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); // R here is the java class that indicate the corresponding activity xml file

/*        // Show different fragments based on screen size.
        if (findViewById(R.id.fragment_container) != null) {
            Fragment fragment = isTablet() ? new  CommentFragment() : new EventFragment();
            getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, fragment).commit();
            // fragmentmanager only support android.support.v4.app.Fragment;
            // add fragments dynamically base on the id in xml
}*/

        AdView mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        // Firebase uses singleton to initialize the sdk
        mDatabase = FirebaseDatabase.getInstance().getReference();

        mUsernameEditText = (EditText) findViewById(R.id.editTextLogin);
        mPasswordEditText = (EditText) findViewById(R.id.editTextPassword);
        mSubmitButton = (Button) findViewById(R.id.submit);
        mRegisterButton = (Button) findViewById(R.id.register);

        mRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String username = mUsernameEditText.getText().toString();
                final String password = mPasswordEditText.getText().toString();
                final User user = new User(username, password, System.currentTimeMillis());
                mDatabase.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.hasChild(username)) {
                            Toast.makeText(getBaseContext(),"username is already registered, please change one", Toast.LENGTH_SHORT).show();
                        } else if (!username.equals("") && !password.equals("")){
                            // put username as key to set value
                            mDatabase.child("users").child(user.getUsername()).setValue(user);
                            Toast.makeText(getBaseContext(),"Successfully registered", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });


        mSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String username = mUsernameEditText.getText().toString();
                final String password = mPasswordEditText.getText().toString();
                mDatabase.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.hasChild(username) && (password.equals(dataSnapshot.child(username).child("password").getValue()))) {
                            Log.i("Your log", "You successfully login");
                            Intent myIntent = new Intent(MainActivity.this, EventActivity.class);
                            myIntent.putExtra("Username", username);
                            startActivity(myIntent);
                        } else {
                            Toast.makeText(getBaseContext(),"Please login again", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });


/*
        //add list view
        // All Fragment-to-Fragment communication is done through the associated Activity
        if (isTablet()) { // create the list fragment object
            listFragment = new EventFragment();
            //getSupportFragmentManager().beginTransaction().add(R.id.event_container, listFragment).commit();
        }

        //add Gridview
        gridFragment = new CommentFragment();
        //getSupportFragmentManager().beginTransaction().add(R.id.comment_container, gridFragment).commit();
*/

    }

    private boolean isTablet() {
        return (getApplicationContext().getResources().getConfiguration().screenLayout &
                Configuration.SCREENLAYOUT_SIZE_MASK) >=
                Configuration.SCREENLAYOUT_SIZE_LARGE;
    }

    @Override
    public void onItemSelected(int position){
        gridFragment.onItemSelected(position);
    }

    @Override
    public void onItemSelectedInGrid(int position){
        listFragment.onItemSelectedInGrid(position);
    }

//        Log.e("Life cycle test", "We are at onCreate()");
//        // Get ListView object from xml.
//        ListView restaurantListView = (ListView) findViewById(R.id.restaurant_list);
//
//        // Initialize an adapter.
//        RestaurantAdapter adapter = new RestaurantAdapter(this);
////        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
////                this, // which activity for display
////                R.layout.activity_restaurant_list_item,// xml file
////                R.id.restaurant_name,// string id
////                getRestaurantNames()); // string array, fake one
//
//        // Assign adapter to ListView.
//        restaurantListView.setAdapter(adapter); // 连接 list view to adapter

    //} 光标下control + enter inport

//    /**
//     * A dummy function to get fake restaurant names.
//     *
//     * @return an array of fake restaurant names.
//     */
//    private String[] getRestaurantNames() {
//        String[] names= {
//                "Restaurant1", "Restaurant2", "Restaurant3",
//                "Restaurant4", "Restaurant5", "Restaurant6",
//                "Restaurant7", "Restaurant8", "Restaurant9",
//                "Restaurant10", "Restaurant11", "Restaurant12"};
//        return names;
//    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.e("Life cycle test", "We are at onStart()");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e("Life cycle test", "We are at onResume()");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.e("Life cycle test", "We are at onPause()");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e("Life cycle test", "We are at onStop()");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e("Life cycle test", "We are at onDestroy()");
    }
}
