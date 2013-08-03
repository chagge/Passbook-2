package com.boxxymays.passbook;

import android.os.Bundle;
import android.app.Activity;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.pdf417.PDF417Writer;
import com.google.zxing.qrcode.encoder.ByteMatrix;

import java.io.UnsupportedEncodingException;

public class PassbookContainer extends Activity{

    private boolean mShowingBack = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passbook_container);

        if (savedInstanceState == null) {
            getFragmentManager()
                    .beginTransaction()
                    .add(R.id.container, new PassbookFront())
                    .commit();
        }

        String data = "Kevin Booth";


//get a byte matrix for the data
        ByteMatrix matrix;
        com.google.zxing.Writer writer = new PDF417Writer();
        try {
            matrix = writer.encode(data, BarcodeFormat.PDF_417, 100, 100);
        }
        catch (com.google.zxing.WriterException e) {
            //exit the method
            return;
        }

        FrameLayout container = (FrameLayout) this.findViewById(R.id.container);
        container.setOnTouchListener(new OnSwipeTouchListener() {

            public void onSwipeRight() {
                flipCard(true);
            }

            public void onSwipeLeft() {
                flipCard(false);
            }

        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.passbook_container, menu);
        return true;
    }

    private void flipCard(boolean swipedRight) {
        if(swipedRight){
            if(mShowingBack){
                getFragmentManager()
                        .beginTransaction()
                        .setCustomAnimations(
                                R.animator.card_flip_right_in, R.animator.card_flip_right_out,
                                R.animator.card_flip_left_in, R.animator.card_flip_left_out)
                        .replace(R.id.container, new PassbookFront())
                        .addToBackStack(null)
                        .commit();
            }else{
                getFragmentManager()
                        .beginTransaction()
                        .setCustomAnimations(
                                R.animator.card_flip_right_in, R.animator.card_flip_right_out,
                                R.animator.card_flip_left_in, R.animator.card_flip_left_out)
                        .replace(R.id.container, new PassbookBack())
                        .addToBackStack(null)
                        .commit();
            }
        }else{
            if(mShowingBack){
                getFragmentManager()
                        .beginTransaction()
                        .setCustomAnimations(
                                R.animator.card_flip_left_in, R.animator.card_flip_left_out,
                                R.animator.card_flip_right_in, R.animator.card_flip_right_out)
                        .replace(R.id.container, new PassbookFront())
                        .addToBackStack(null)
                        .commit();
            }else{
                getFragmentManager()
                        .beginTransaction()
                        .setCustomAnimations(
                                R.animator.card_flip_left_in, R.animator.card_flip_left_out,
                                R.animator.card_flip_right_in, R.animator.card_flip_right_out)
                        .replace(R.id.container, new PassbookBack())
                        .addToBackStack(null)
                        .commit();
            }
        }
        mShowingBack = !mShowingBack;
        return;
    }

}
