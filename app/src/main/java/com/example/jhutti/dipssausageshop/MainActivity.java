package com.example.jhutti.dipssausageshop;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.content.Intent;
import android.widget.Toast;

import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalItem;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalPaymentDetails;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;
import com.paypal.android.sdk.payments.ShippingAddress;

import org.json.JSONException;

import java.math.BigDecimal;


public class MainActivity extends ActionBarActivity {
    public final static String BASKET_MESSAGE = "com.example.jhutti.myapplication.MESSAGE";

    private static PayPalConfiguration config = new PayPalConfiguration()

            // Start with mock environment.  When ready, switch to sandbox (ENVIRONMENT_SANDBOX)
            // or live (ENVIRONMENT_PRODUCTION)
            .environment(PayPalConfiguration.ENVIRONMENT_SANDBOX)

            .clientId("AW-jfRBs4umTiV1HVxqhYH3eFuakfZ4Qn_aPHrQ6Jx4OrwsXYFp3NIMmkwuN");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);

        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();

            Intent intent = new Intent(this, PayPalService.class);

            intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);

            startService(intent);

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

    @Override
    public void onDestroy() {
        stopService(new Intent(this, PayPalService.class));
        super.onDestroy();
    }


    @Override
    protected void onActivityResult (int requestCode, int resultCode, Intent data) {

        if (resultCode == Activity.RESULT_OK) {
            PaymentConfirmation confirm = data.getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);
            if (confirm != null) {
                try {
                    Log.i("paymentExample", confirm.toJSONObject().toString(4));
                    Toast.makeText(getApplicationContext(), "Your Order was successful", Toast.LENGTH_LONG).show();
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
            Log.i("paymentExample", "The user canceled.");
        }
        else if (resultCode == PaymentActivity.RESULT_EXTRAS_INVALID) {
            Log.i("paymentExample", "An invalid Payment or PayPalConfiguration was submitted. Please see the docs.");
        }
    }


}
