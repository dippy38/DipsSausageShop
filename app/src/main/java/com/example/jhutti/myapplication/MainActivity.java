package com.example.jhutti.myapplication;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.content.Intent;


public class MainActivity extends ActionBarActivity {
    public final static String BASKET_MESSAGE = "com.example.jhutti.myapplication.MESSAGE";
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);

        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
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

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_my, container, false);
            return rootView;
        }
    }

    /** Called when the user clicks the Sausage button */
    public void buySausage(View view) {
        Intent intent = new Intent(this, DisplayBasketActivity.class);
        String itemDescription=view.getContentDescription().toString();
        int itemNumber;
        double itemPrice;
        if (itemDescription.equals("Sausage Rolls")) {itemNumber=20293847; itemPrice=25.63;} else {itemNumber=18475843; itemPrice=10.59;}
        BasketItem myItem= new BasketItem(itemNumber,itemDescription,itemPrice);
        Basket.addItem(myItem);
        startActivity(intent);
    }

}
