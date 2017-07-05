package com.sweta.grievancemakers.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.sweta.grievancemakers.authfragments.LoginFragment;
import com.sweta.grievancemakers.authfragments.RegisterFragment;

/**
 * Created by 1406074 on 02-06-2017.
 */

public class AuthScreenViewPagerAdapter extends FragmentStatePagerAdapter {


    public AuthScreenViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new LoginFragment();
            case 1:
                return new RegisterFragment();
            case 2:
        }
        return null;
    }

    @Override
    public int getCount() {
        return 2;
    }
}
