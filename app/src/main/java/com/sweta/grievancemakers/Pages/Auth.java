package com.sweta.grievancemakers.Pages;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;

import com.sweta.grievancemakers.R;
import com.sweta.grievancemakers.adapter.AuthScreenViewPagerAdapter;



public class Auth extends FragmentActivity {

    public static ViewPager viewPager;
    private AuthScreenViewPagerAdapter adaptor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);

        viewPager = (ViewPager) findViewById(R.id.viewPager);

        adaptor = new AuthScreenViewPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adaptor);
    }
}