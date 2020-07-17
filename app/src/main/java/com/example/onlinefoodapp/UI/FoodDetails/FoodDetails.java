package com.example.onlinefoodapp.UI.FoodDetails;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.onlinefoodapp.R;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.paypal.android.sdk.payments.PayPalAuthorization;
import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalFuturePaymentActivity;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;

import java.math.BigDecimal;

public class FoodDetails extends AppCompatActivity {
    // now we will get data from intent and set to UI

    ImageView imageView;
    TextView itemName, itemPrice, itemRating;
    RatingBar ratingBar;

    String name, price, rating, imageUrl;
    // PayPalConfiguration.ENVIRONMENT_NO_NETWORK;
    private static final String CONFIG_ENVIRONMENT = PayPalConfiguration.ENVIRONMENT_SANDBOX;
    // note that these credentials will differ between live & sandbox
    // environments.
    public static final String PAYPAL_KEY = "AcgiVwsiRraneVt_odGsC6LarKVBuxXpGqmL1jaxRD_S2NfCBqqPsLAHTaIP9pRAJb7MM5-t5C2Hz9e2";
    private static final int REQUEST_CODE_PAYMENT = 1;
    private static final int REQUEST_CODE_FUTURE_PAYMENT = 2;
    private static PayPalConfiguration config ;
    PayPalPayment thingToBuy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        setGui();
        PayoalConfig();
    }
    private void setGui(){
        Intent intent = getIntent();

        name = intent.getStringExtra("name");
        price = intent.getStringExtra("price");
        rating = intent.getStringExtra("rating");
        imageUrl = intent.getStringExtra("image");

        imageView = findViewById(R.id.imageView5);
        itemName = findViewById(R.id.name);
        itemPrice = findViewById(R.id.price);
        itemRating = findViewById(R.id.rating);
        ratingBar = findViewById(R.id.ratingBar);

        Glide.with(getApplicationContext()).load(imageUrl).into(imageView);
        itemName.setText(name);
        itemPrice.setText("₹ "+price);
        itemRating.setText(rating);
        ratingBar.setRating(Float.parseFloat(rating));
    }
    int cliked=0;
    public void likeOnClick(View view) {
        ImageView imageView= findViewById(R.id.imageView7);
        if(cliked==0)
        {imageView.setImageResource(R.drawable.ic_like2);cliked=1;}
        else {
            imageView.setImageResource(R.drawable.ic_like);cliked=0;
        }
    }
    public void orderBuClick(View view) {
        makePayment();

    }
    private void makePayment() {
        Intent intent=new Intent(this, PayPalService.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION,config);
        startService(intent);

        thingToBuy=new PayPalPayment(new BigDecimal("150"),"USD","Payment",PayPalPayment.PAYMENT_INTENT_SALE);
        Intent paymentIntent=new Intent(this, PaymentActivity.class);
        paymentIntent.putExtra(PaymentActivity.EXTRA_PAYMENT,thingToBuy);
        paymentIntent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION,config);
        startActivityForResult(paymentIntent,REQUEST_CODE_PAYMENT);
    }

    private void PayoalConfig(){
        config = new PayPalConfiguration()
                .environment(CONFIG_ENVIRONMENT)
                .clientId(PAYPAL_KEY)
                // The following are only used in PayPalFuturePaymentActivity.
                .merchantName("Online Food")
                .merchantPrivacyPolicyUri(Uri.parse("https://www.example.com/privacy"))
                .merchantUserAgreementUri(Uri.parse("https://www.example.com/legal"));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_PAYMENT) {
            if (resultCode == Activity.RESULT_OK) {
                PaymentConfirmation confirm = data
                        .getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);
                if (confirm != null) {
                    try {
                        System.out.println(confirm.toJSONObject().toString(4));
                        System.out.println(confirm.getPayment().toJSONObject().toString(4));
                        Toast.makeText(getApplicationContext(), "Order placed",Toast.LENGTH_LONG).show();

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            } else if (resultCode == Activity.RESULT_CANCELED) {
                System.out.println("The user canceled.");
            } else if (resultCode == PaymentActivity.RESULT_EXTRAS_INVALID) {
                System.out.println("An invalid Payment or PayPalConfiguration was submitted. Please see the docs.");
            }
        } else if (requestCode == REQUEST_CODE_FUTURE_PAYMENT) {
            if (resultCode == Activity.RESULT_OK) {
                PayPalAuthorization auth = data.getParcelableExtra(PayPalFuturePaymentActivity.EXTRA_RESULT_AUTHORIZATION);
                if (auth != null) {
                    try {
                        Log.i("FuturePaymentExample", auth.toJSONObject()
                                .toString(4));

                        String authorization_code = auth.getAuthorizationCode();
                        Log.i("FuturePaymentExample", authorization_code);
                        sendAuthorizationToServer(auth);
                        Toast.makeText(getApplicationContext(),
                                "Future Payment code received from PayPal",
                                Toast.LENGTH_LONG).show();

                    } catch (Exception e) {
                        Log.e("FuturePaymentExample",
                                "an extremely unlikely failure occurred: ", e);
                    }
                }
            } else if (resultCode == Activity.RESULT_CANCELED) {
                Log.i("FuturePaymentExample", "The user canceled.");
            } else if (resultCode == PayPalFuturePaymentActivity.RESULT_EXTRAS_INVALID) {
                Log.i("FuturePaymentExample",
                        "Probably the attempt to previously start the PayPalService had an invalid PayPalConfiguration. Please see the docs.");
            }
        }
    }
    private void sendAuthorizationToServer(PayPalAuthorization authorization) {

    }

    public void onFuturePaymentPurchasePressed(View pressed) {
        // Get the Application Correlation ID from the SDK
        String correlationId = PayPalConfiguration
                .getApplicationCorrelationId(this);

        Log.i("FuturePaymentExample", "Application Correlation ID: "
                + correlationId);

        // TODO: Send correlationId and transaction details to your server for
        // processing with
        // PayPal...
        Toast.makeText(getApplicationContext(),
                "App Correlation ID received from SDK", Toast.LENGTH_LONG)
                .show();
    }
    @Override
    public void onDestroy() {
        // Stop service when done
        stopService(new Intent(this, PayPalService.class));
        super.onDestroy();
    }

    private void showButtomsheet(){
      /*  final BottomSheetDialog BottomSheetDialog =new BottomSheetDialog(FoodDetails.this,R.style.BottomSheetDialogTheme);
        View bottomSheetView= LayoutInflater.from(getApplicationContext())
                .inflate(R.layout.layout_bottom_sheet,(LinearLayout)findViewById(R.id.bottomSheetContainer));
        bottomSheetView.findViewById(R.id.OrderBottomSheet).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                makePayment();
                BottomSheetDialog.dismiss();
            }
        });
        BottomSheetDialog.setContentView(bottomSheetView);
        BottomSheetDialog.show();*/
        MyBottomSheetDialog myBottomSheetDialog=new MyBottomSheetDialog();
        myBottomSheetDialog.show(getSupportFragmentManager(),"");
    }

    public void AddToCardClick(View view) {
        showButtomsheet();
        //Toast.makeText(getApplicationContext(),"HHHHHHHHHHHHH",Toast.LENGTH_LONG).show();
    }
}