package me.saket.phonepesaket.ui.main;

import android.os.Bundle;

import me.saket.phonepesaket.R;
import me.saket.phonepesaket.ui.BaseActivity;

/**
 * Home-screen of the app.
 */
public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

}