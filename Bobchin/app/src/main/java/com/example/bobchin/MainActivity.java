package com.example.bobchin;

import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.os.Bundle;

import com.example.bobchin.Adapter.TabPagerAdapter;
import com.example.bobchin.Font.BaseActivity;
import com.google.android.material.tabs.TabLayout;


public class MainActivity extends BaseActivity {
    private Context mContext;



    private TabLayout mTabLayout;



    private ViewPager mViewPager;

    private TabPagerAdapter mContentPagerAdapter;



    @Override

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        mContext = getApplicationContext();



        mTabLayout = (TabLayout) findViewById(R.id.layout_tab);
        
        mTabLayout.addTab(mTabLayout.newTab().setText("게시판"));

        mTabLayout.addTab(mTabLayout.newTab().setText("내 모임"));

        mTabLayout.addTab(mTabLayout.newTab().setText("내 프로필"));

        mViewPager = (ViewPager) findViewById(R.id.pager_content);

        mContentPagerAdapter = new TabPagerAdapter(
                getSupportFragmentManager(), mTabLayout.getTabCount());
        mViewPager.setAdapter(mContentPagerAdapter);
        mViewPager.addOnPageChangeListener(
                new TabLayout.TabLayoutOnPageChangeListener(mTabLayout));
        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mViewPager.setCurrentItem(tab.getPosition());
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }
            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
    }
}