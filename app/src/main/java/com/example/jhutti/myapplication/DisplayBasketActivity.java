package com.example.jhutti.myapplication;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;
import android.content.Intent;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.RelativeLayout;
import android.widget.ListView;
import android.widget.ArrayAdapter;

public class DisplayBasketActivity extends ActionBarActivity{


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_message);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Create a Layout
        LinearLayout myLayout = new LinearLayout(this);
        LinearLayout.LayoutParams lvParams = new LinearLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);

        //Create the ListAdapter and populate it
        ListAdapter listAdapter = new ArrayAdapter<String>(this, R.layout.list_item, Basket.getFullBasketList() );

        //Connect the ListAdapter and ListView
        ListView lv = new ListView(this);
        lv.setAdapter(listAdapter);

        //Connect the Layout and now-populated View
        myLayout.addView(lv, lvParams);
        setContentView(myLayout);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_activity_actions, menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        Intent intent = new Intent(this, DisplayBasketActivity.class);

        //Select what happens when menu item selected (basket/checkout/empty)
        switch (item.getItemId()) {
            case R.id.action_basket:
                startActivity(intent);
                return true;
            case R.id.action_checkout:
                //TODO INTEGRATE PAYPAL SDK CALL HERE
                return true;
            case R.id.action_empty:
                Basket.emptyBasket();
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() { }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.activity_display_message, container, false);
            System.out.println("here!!!!!!!!!!!!!!!!!!!!!!!");
            return rootView;
        }
    }
}