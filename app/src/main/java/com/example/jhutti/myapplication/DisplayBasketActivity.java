package com.example.jhutti.myapplication;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
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
import android.widget.Toast;

import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalItem;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalPaymentDetails;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;
import com.paypal.android.sdk.payments.ProofOfPayment;
import com.paypal.android.sdk.payments.ShippingAddress;

import org.json.JSONException;

import java.math.BigDecimal;

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

               // PayPalConfiguration payPalConfiguration = new PayPalConfiguration().
                //Calculate Costs
                BigDecimal subtotal = PayPalItem.getItemTotal(Basket.getBasketPayPalItems());
                BigDecimal shipping = new BigDecimal("9.99");
                BigDecimal tax = shipping.multiply(new BigDecimal("1.2"));
                PayPalPaymentDetails paymentDetails = new PayPalPaymentDetails(shipping, subtotal, tax);
                BigDecimal amount = subtotal.add(shipping).add(tax);

                //Set top level Payment details
                PayPalPayment payment = new PayPalPayment(amount, "GBP", "DipsSausageShop Order", PayPalPayment.PAYMENT_INTENT_SALE);


                //Set Address
                ShippingAddress shippingAddress = new ShippingAddress();
                shippingAddress.city("Whitchurch");
                shippingAddress.countryCode("GB");
                shippingAddress.line1("38 Micheldever Road");
                shippingAddress.state("Hampshire");
                shippingAddress.postalCode("RG28 7JH");
                payment.providedShippingAddress(shippingAddress);
                payment.enablePayPalShippingAddressesRetrieval(true);
                //PaymentAc

                //Set Details of Payment
                payment.items(Basket.getBasketPayPalItems()).paymentDetails(paymentDetails);

                Intent intentPay = new Intent(this, PaymentActivity.class);
                intentPay.putExtra(PaymentActivity.EXTRA_PAYMENT, payment);
                startActivityForResult(intentPay, 0);
                return true;
            case R.id.action_empty:
                Basket.emptyBasket();
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onActivityResult (int requestCode, int resultCode, Intent data) {

        if (resultCode == Activity.RESULT_OK) {
            PaymentConfirmation confirm = data.getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);
            if (confirm != null) {
                try {
                    Log.i("paymentExample", confirm.toJSONObject().toString(4));
                    Toast.makeText(getApplicationContext(),"Your Order was successful",Toast.LENGTH_LONG).show();
                    Basket.emptyBasket();
                    Intent intent = new Intent(this, MainActivity.class);
                    startActivity(intent);
                    // TODO: send 'confirm' to your server for verification.
                    // see https://developer.paypal.com/webapps/developer/docs/integration/mobile/verify-mobile-payment/
                    // for more details.

                } catch (JSONException e) {
                    Log.e("paymentExample", "an extremely unlikely failure occurred: ", e);
                }
            }
        }
        else if (resultCode == Activity.RESULT_CANCELED) {
            Log.i("paymentExample", "The user cancelled.");
            Toast.makeText(getApplicationContext(),"Your Order was cancelled. Please retry by clicking the PayPal icon",Toast.LENGTH_LONG).show();
        }
        else if (resultCode == PaymentActivity.RESULT_EXTRAS_INVALID) {
            Log.i("paymentExample", "An invalid Payment or PayPalConfiguration was submitted. Please see the docs.");
            Toast.makeText(getApplicationContext(),"An invalid Payment was submitted.",Toast.LENGTH_LONG).show();
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