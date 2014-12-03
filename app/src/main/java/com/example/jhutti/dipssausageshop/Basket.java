package com.example.jhutti.dipssausageshop;

import com.paypal.android.sdk.payments.PayPalItem;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by mjhutti on 17/11/2014.
 */
public class Basket {

    //TODO Add private constructor and singleton methodology

    private static ArrayList<BasketItem>myBasket = new ArrayList<BasketItem>();
    public static String currencyISO = "GBP";
    public static String currencySymbol = "£";


    public static void addItem(BasketItem myItem){
        myBasket.add(myItem);
    }

    public static void removeItem(BasketItem myItem){
        myBasket.remove(myItem);
    }

    public static void emptyBasket(){
        myBasket.clear();
    }

    public static String getBasket(){
        String basketContents="";
        for (BasketItem item : myBasket) {
            basketContents = basketContents + item.getIdentifier() + "          " + item.getDescription() + "          " + item.getPrice() + "\r\n ";
        }
        if (basketContents.length()<=2) {basketContents="BASKET EMPTY";}
        return basketContents;
    }


    public static PayPalItem[] getBasketPayPalItems(){
        PayPalItem [] myPayPalBasket = new PayPalItem[myBasket.size()];

        for (int i=0; i<myBasket.size(); i++){
            myPayPalBasket[i] = new PayPalItem(myBasket.get(i).getDescription(),1,myBasket.get(i).getPriceDecimal(),currencyISO,myBasket.get(i).getIdentifierString());
           // myPayPalBasket[i] = new PayPalItem(myBasket.get(i).getDescription(),1,new BigDecimal(2.01),currencyISO,"123456789");


        }

        return myPayPalBasket;
    }

    public static ArrayList<String> getFullBasketList(){
        ArrayList<String> stringBasket = new ArrayList<String>();
        stringBasket.add(0,"Identifier" + "          " + "Description" + "          " + "Price");
        int i =0;

        for (BasketItem item : myBasket) {
            stringBasket.add(i,item.getIdentifier() + "          " + item.getDescription() + "          " + currencySymbol + item.getPrice() );
        }
        Collections.reverse(stringBasket);
        return stringBasket;
    }

    public void checkout(){


    }

}
