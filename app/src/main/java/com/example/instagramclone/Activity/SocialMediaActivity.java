package com.example.instagramclone.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;
import android.os.Bundle;
import com.example.instagramclone.Adapter.TabAdapter;
import com.example.instagramclone.R;
import com.google.android.material.tabs.TabLayout;


public class SocialMediaActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private ViewPager mViewPager;
    private TabLayout mTabLayout;
    private TabAdapter mTabAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_social_media);
        mToolbar = findViewById(R.id.myToolbar);
        setSupportActionBar(mToolbar);
        mViewPager = findViewById(R.id.viewPager);
        mTabAdapter = new TabAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mTabAdapter);
        mTabLayout = findViewById(R.id.tabLayout);
        mTabLayout.setupWithViewPager(mViewPager,false);
    }
}
