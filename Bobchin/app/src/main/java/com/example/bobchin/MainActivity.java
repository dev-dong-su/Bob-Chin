package com.example.bobchin;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.bobchin.Adapter.TabPagerAdapter;
import com.example.bobchin.Fragment.FirstAdFragment;

import com.example.bobchin.Fragment.Meetings;
import com.example.bobchin.Fragment.Mymeetings;

import com.example.bobchin.Fragment.Settings;
import com.example.bobchin.Networking.HttpGet;
import com.google.android.material.tabs.TabLayout;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.concurrent.ExecutionException;

import me.relex.circleindicator.CircleIndicator;


public class MainActivity extends AppCompatActivity {

    private Context mContext;
    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private TabPagerAdapter mContentPagerAdapter;

    private FragmentPagerAdapter adapterViewPager;

    private static final int REQ_ADDMEET = 99;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = getApplicationContext();
        mTabLayout = (TabLayout) findViewById(R.id.layout_tab);
        mTabLayout.addTab(mTabLayout.newTab().setText("게시판").setIcon(R.drawable.ic_home_black_24dp));
        mTabLayout.addTab(mTabLayout.newTab().setText("내 모임").setIcon(R.drawable.ic_chat_black_24dp));
        mTabLayout.addTab(mTabLayout.newTab().setText("내 프로필").setIcon(R.drawable.ic_person_black_24dp));
        mViewPager = (ViewPager) findViewById(R.id.pager_content);
        mViewPager.setOffscreenPageLimit(5);
        mContentPagerAdapter = new TabPagerAdapter(
                getSupportFragmentManager(), mTabLayout.getTabCount());
        mViewPager.setAdapter(mContentPagerAdapter);
        mViewPager.addOnPageChangeListener(
                new TabLayout.TabLayoutOnPageChangeListener(mTabLayout));
        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                ViewGroup viewGroup = findViewById(R.id.ad_layout);
                if (tab.getPosition() != 0) {
                    viewGroup.setVisibility(View.GONE);
                }
                else {
                    viewGroup.setVisibility(View.VISIBLE);
                }
                mViewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });

        //ad 관련
        ViewPager adPager = (ViewPager) findViewById(R.id.view_pager_ad);
        adapterViewPager = new MyPagerAdapter(getSupportFragmentManager(),(BobChin) getApplication());
        adPager.setAdapter(adapterViewPager);

        CircleIndicator indicator = (CircleIndicator) findViewById(R.id.indicator);
        indicator.setViewPager(adPager);
    }

    public static class MyPagerAdapter extends FragmentPagerAdapter {
        private static JSONArray arrNotice;

        MyPagerAdapter(FragmentManager fragmentManager,BobChin bobChin) {
            super(fragmentManager);
            try {
                HttpGet httpGet = new HttpGet();
                String notice = httpGet.execute("http://bobchin.cf/api/getnotice.php?token="+bobChin.getUserInfoObj().getUserAccessToken()).get();
                arrNotice = new JSONArray(notice);
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            try {
                return FirstAdFragment.newInstance(position, arrNotice.getJSONObject(position).getString("url") );
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        public int getCount() {
            return arrNotice.length();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return "Page " + position;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        ActionBar actionBar = getSupportActionBar();

        // Custom Actionbar를 사용하기 위해 CustomEnabled을 true 시키고 필요 없는 것은 false 시킨다
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(false);            //액션바 아이콘을 업 네비게이션 형태로 표시합니다.
        actionBar.setDisplayShowTitleEnabled(false);        //액션바에 표시되는 제목의 표시유무를 설정합니다.
        actionBar.setDisplayShowHomeEnabled(false);            //홈 아이콘을 숨김처리합니다.


        //layout을 가지고 와서 actionbar에 포팅을 시킵니다.
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        View actionbar = inflater.inflate(R.layout.custom_actionbar, null);

        actionBar.setCustomView(actionbar);

        //액션바 양쪽 공백 없애기
        Toolbar parent = (Toolbar) actionbar.getParent();
        parent.setContentInsetsAbsolute(0, 0);
        ActionBar.LayoutParams params = new ActionBar.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.MATCH_PARENT);
        actionBar.setCustomView(actionbar,params);

        ImageButton alarmButton = findViewById(R.id.button_alarm);
        alarmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AlarmActivity.class);
                startActivity(intent);
            }
        });

        return true;
    }

    //EnterMeet 처리
    public Fragment findFragmentByPosition(int position) { return ((Fragment)mContentPagerAdapter.instantiateItem(mViewPager,position));}
    public void RefreshMeets(){
        mViewPager.setCurrentItem(1);
        Meetings meetings = (Meetings) findFragmentByPosition(0);
        meetings.setResultNull();
        meetings.Refresh();
        Mymeetings mymeetings = (Mymeetings) findFragmentByPosition(1);
        mymeetings.setResultNull();
        mymeetings.Refresh();
    }

    @Override
    public void onActivityResult(int reqCode, int resCode, Intent data){
        super.onActivityResult(reqCode,resCode,data);
        String msg = "";
        if(reqCode == 1) {
            switch (resCode) {
                case 0:
                    msg = "밥친이 되었습니다";
                    break;
                case 1:
                    msg = "밥친이 이미 다 모였습니다";
                    break;
                case 2:
                    msg = "이미 밥친입니다";
                    break;
            }
            if (0 <= resCode && resCode <= 2) {
                RefreshMeets();
                Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
            }
            else if(resCode == 3){
                RefreshMeets();
            }
        }
        else if(reqCode == 3) {
            if(resCode == 0) {
                String url = data.getStringExtra("url");
                //Toast.makeText(getApplicationContext(), url, Toast.LENGTH_LONG).show();
                ((BobChin) getApplication()).getUserInfoObj().setUserPhotoURL(url);
                Settings settings = (Settings) findFragmentByPosition(2);
                settings.setMyProfile(url);
            }
        }
        else if(reqCode == REQ_ADDMEET){
            if(resCode == RESULT_OK) {
                msg="밥친찾기가 등록되었습니다!";
                RefreshMeets();
                Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
            }
        }
    }

    static long pressedTime;
    @Override
    public void onBackPressed() {
        if (pressedTime == 0) {
            Toast.makeText(MainActivity.this, " 한 번 더 누르면 종료됩니다.", Toast.LENGTH_LONG).show();
            pressedTime = System.currentTimeMillis();
        } else {
            int seconds = (int) (System.currentTimeMillis() - pressedTime);

            if (seconds > 2000) {
                Toast.makeText(MainActivity.this, " 한 번 더 누르면 종료됩니다.", Toast.LENGTH_LONG).show();
                pressedTime = 0;
            } else {
                finishAffinity();
            }
        }
    }
}
