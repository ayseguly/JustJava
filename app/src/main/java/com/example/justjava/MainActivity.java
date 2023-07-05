package com.example.justjava;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;

/**
     * This app displays an order form to order coffee.
     */
    public class MainActivity extends AppCompatActivity {

    int numberOfCups =2;
    int pricePerCup = 5;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);
        }



    public void decrement(View view) {

        if(numberOfCups == 1){
//            Toast.makeText(this, "You cannot have less than 1 coffee", Toast.LENGTH_SHORT).show();
            Toast.makeText(this, getString(R.string.toastDecrease), Toast.LENGTH_SHORT).show();
            return;
        }
        else {
            numberOfCups=numberOfCups-1;
        }
        displayQuantity(numberOfCups);
    }

    public void increment(View view) {

        if(numberOfCups == 50){
            // Show an error message as a toast
//            Toast.makeText(this, "You cannot have more than 50 coffees", Toast.LENGTH_SHORT).show();
            // Exit this method early because there's nothing left to do

            Toast.makeText(this, getString(R.string.toastIncrease), Toast.LENGTH_SHORT).show();
            return;
        }

        numberOfCups=numberOfCups+1;

        displayQuantity(numberOfCups);


    }

//    /**
//     * This method displays the given text on the screen.
//     */
//    private void displayMessage(String message) {
//        TextView orderSummaryTextView = (TextView) findViewById(R.id.orderSummary_text_view);
//        orderSummaryTextView.setText(message);
//    }

        /**
         * This method is called when the order button is clicked.
         */
        public void submitOrder(View view) {

            EditText nameInput = (EditText) findViewById(R.id.name_input);
            String name = nameInput.getText().toString();
            /**
            Log.v("Main Activity", "Name: " + name);
             */

            CheckBox whippedCheckBox = (CheckBox) findViewById(R.id.whipped_check_box);
            boolean hasWhippedCream= whippedCheckBox.isChecked();
            /**
            Log.v("Main Activity", "Has whipped cream: "+ hasWhippedCream);
             */

            CheckBox chocolateCheckBox = findViewById(R.id.chocolate_check_box);
            boolean hasChocolate = chocolateCheckBox.isChecked();

            int price= calculatePrice(hasWhippedCream, hasChocolate);
            String PriceMessage=createOrderSummary(name, price, hasWhippedCream, hasChocolate);

            Intent intent = new Intent(Intent.ACTION_SENDTO);
            intent.setData(Uri.parse("mailto:")); // only email apps should handle this
//            intent.putExtra(Intent.EXTRA_EMAIL, addresses);
            intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.order_summary_email_subject, name));
            intent.putExtra(Intent.EXTRA_TEXT, PriceMessage);
            if (intent.resolveActivity(getPackageManager()) != null) {
                startActivity(intent);
            }
//
//            displayMessage(PriceMessage);

//            Intent intent = new Intent(Intent.ACTION_VIEW);


     }
    /**
     * @param addWhippedCream is whether or not the user wants whipped cream topping
     * @param addChocolate is whether or not the user wants chocolate topping
     * @return total price
     */

    private int calculatePrice(boolean addWhippedCream, boolean addChocolate){

        if(addWhippedCream) {
            pricePerCup+=1;
        }


        if(addChocolate)
        {
            pricePerCup += 2;
        }
        return numberOfCups*pricePerCup;

    }


    /**
     * Create summary of the order.
     * @param name is the name of customer
     * @param addWhippedCream is whether or not the user wants whipped cream topping
     * @param addChocolate is whether or not the user wants chocolate topping
     * @param price of the order
     * @return text summary
     */

        private String createOrderSummary( String name, int price, boolean addWhippedCream, boolean addChocolate){

            String PriceMessage=getString(R.string.order_summary_name,name);
            PriceMessage += "\n"+ getString(R.string.order_summary_whipped_cream,addWhippedCream); //(addWhippedCream? getString(R.string.boolean_true) : getString(R.string.boolean_false)));;
            PriceMessage += "\n"+ getString(R.string.order_summary_chocolate, addChocolate); //(addChocolate? getString(R.string.boolean_true) : getString(R.string.boolean_false)));;
            PriceMessage += "\n"+ getString(R.string.order_summary_quantity, numberOfCups);
            PriceMessage += "\n"+ getString(R.string.order_summary_price,
                    NumberFormat.getCurrencyInstance().format(price));
            PriceMessage += "\n" + getString(R.string.thank_you);
            return PriceMessage;


        }




        /**
         * This method displays the given quantity value on the screen.
         */
        private void displayQuantity(int quantity) {
            TextView quantityTextView = (TextView) findViewById(R .id.count);
            quantityTextView.setText("" + quantity);
        }

    }