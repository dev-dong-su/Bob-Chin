package com.example.bobchin.Adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.bobchin.Fragment.Meetings;
import com.example.bobchin.Fragment.Mymeetings;
import com.example.bobchin.Fragment.Settings;

public class TabPagerAdapter extends FragmentStatePagerAdapter {

    // Count number of tabs
    private int tabCount;

    public TabPagerAdapter(FragmentManager fm, int tabCount) {
        super(fm,BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        this.tabCount = tabCount;
    }

    @Override
    public Fragment getItem(int position) {

        // Returning the current tabs
        switch (position) {
            case 0:
                Meetings meetings = new Meetings();
                return meetings;
            case 1:
                Mymeetings mymeetings = new Mymeetings();
                return mymeetings;
            case 2:
                Settings settings = new Settings();
                return settings;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return tabCount;
    }
}
