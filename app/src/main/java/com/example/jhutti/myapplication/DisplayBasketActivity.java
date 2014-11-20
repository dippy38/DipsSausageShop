package com.example.jhutti.myapplication;

import android.app.Activity;
import android.app.ListActivity;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;
import android.content.Intent;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.RelativeLayout;
import android.widget.ListView;
import android.widget.TextView;
import com.paypal.android.sdk.payments.PayPalAuthorization;
import java.util.ArrayList;
import android.widget.Button;


public class DisplayBasketActivity extends ActionBarActivity{


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_message);

        //TODO CHANGE TO LISTVIEW HERE???????
        RelativeLayout myLayout = new RelativeLayout(this);

        Button myButton = new Button(this);
        myButton.setText("Press me");

        RelativeLayout.LayoutParams buttonParams =
                new RelativeLayout.LayoutParams(
                        RelativeLayout.LayoutParams.WRAP_CONTENT,
                        RelativeLayout.LayoutParams.WRAP_CONTENT);

        buttonParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
        buttonParams.addRule(RelativeLayout.CENTER_VERTICAL);

        myLayout.addView(myButton, buttonParams);
        setContentView(myLayout);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Create the text view THIS WORKS!
       // TextView textView = new TextView(this);
       // textView.setTextSize(20);
      //  textView.setText(Basket.displayBasket());

        // Set the text view as the activity layout
      // setContentView(textView);
      // getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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