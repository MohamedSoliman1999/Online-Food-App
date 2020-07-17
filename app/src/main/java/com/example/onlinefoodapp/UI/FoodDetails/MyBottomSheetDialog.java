package com.example.onlinefoodapp.UI.FoodDetails;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.onlinefoodapp.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.paypal.android.sdk.payments.PayPalAuthorization;
import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalFuturePaymentActivity;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;

import java.math.BigDecimal;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class MyBottomSheetDialog extends BottomSheetDialogFragment {
    ImageView increment;
    ImageView decriment;
    int number=1;
    TextView numberTxt;

    public TextView button;

    // PayPalConfiguration.ENVIRONMENT_NO_NETWORK;
    private static final String CONFIG_ENVIRONMENT = PayPalConfiguration.ENVIRONMENT_SANDBOX;
    // note that these credentials will differ between live & sandbox
    // environments.
    public static final String PAYPAL_KEY = "AcgiVwsiRraneVt_odGsC6LarKVBuxXpGqmL1jaxRD_S2NfCBqqPsLAHTaIP9pRAJb7MM5-t5C2Hz9e2";
    private static final int REQUEST_CODE_PAYMENT = 1;
    private static final int REQUEST_CODE_FUTURE_PAYMENT = 2;
    private static PayPalConfiguration config ;
    PayPalPayment thingToBuy;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.layout_bottom_sheet, container, false);
        increment=fragmentView.findViewById(R.id.increament);
        decriment=fragmentView.findViewById(R.id.decrement);
        numberTxt=fragmentView.findViewById(R.id.productNumber);
        button=fragmentView.findViewById(R.id.OrderBottomSheet);
        PayoalConfig();
        setOnClick();

        return fragmentView;
    }
    public void setOnClick(){
        increment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                number++;
                numberTxt.setText(number+"");
            }
        });
        decriment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                number--;
                numberTxt.setText(number+"");
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                makePayment();
            }
        });
    }
    private void makePayment() {
        Intent intent=new Intent(getActivity(), PayPalService.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION,config);
        getActivity().startService(intent);

        thingToBuy=new PayPalPayment(new BigDecimal("150"),"USD","Payment",PayPalPayment.PAYMENT_INTENT_SALE);
        Intent paymentIntent=new Intent(getActivity(), PaymentActivity.class);
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

}
