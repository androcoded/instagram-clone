package com.example.instagramclone.Adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.instagramclone.Fragment.ProfileTap;
import com.example.instagramclone.Fragment.SharePicTap;
import com.example.instagramclone.Fragment.UserTap;

public class TabAdapter extends FragmentPagerAdapter {
    public TabAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int tapPosition) {
        switch (tapPosition){
            case 0:
                return new ProfileTap();
            case 1:
                return new UserTap();
            case 2:
                return new SharePicTap();
        }
        return null;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "Profile";
            case 1:
                return "User";
            case 2:
                return "Share";
        }
        return null;
    }
}
