package mu29.maruviewer;

import java.util.ArrayList;
import java.util.Locale;

import android.content.Context;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.FragmentPagerAdapter;
import android.os.Bundle;
import android.support.v4.view.ViewPager;

public class MainActivity extends ActionBarActivity implements ActionBar.TabListener {

    SectionsPagerAdapter mSectionsPagerAdapter;
    ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        actionBar.setTitle("");

        // 섹션 어댑터 만들기
        mSectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        // 섹션 어뎁터를 이용해 뷰페이저 만들기
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        // 섹션 간 스왑. 탭 레퍼런스를 가지고 있다면 ActionBar.Tab#select()을 통해서도 할 수 있다.
        mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                actionBar.setSelectedNavigationItem(position);
            }
        });

        // 액션바에 탭을 추가하자
        for (int i = 0; i < mSectionsPagerAdapter.getCount(); i++) {
            // 어댑터를 참고하여 탭을 추가하자. 탭이 선택된 경우의 리스너도 넣어준다.
            actionBar.addTab(
                    actionBar.newTab()
                            .setText(mSectionsPagerAdapter.getPageTitle(i))
                            .setTabListener(this));
        }
    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
        // 탭이 선택된 경우 페이지 넘기기
        mViewPager.setCurrentItem(tab.getPosition());
        switch (tab.getPosition()) {
            case 1:
                ((UpdateFragment) mSectionsPagerAdapter.getItem(tab.getPosition())).refresh();
                break;
            case 2:
                ((ListFragment) mSectionsPagerAdapter.getItem(tab.getPosition())).refresh();
                break;
        }
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {
        private Context context;
        private ArrayList<Fragment> fragments = new ArrayList<>();

        public SectionsPagerAdapter(Context context, FragmentManager fm) {
            super(fm);
            this.context = context;

            fragments.add(0, BookmarkFragment.newInstance(context));
            fragments.add(1, UpdateFragment.newInstance(context));
            fragments.add(2, ListFragment.newInstance(context));
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            switch (position) {
                case 0:
                    return fragments.get(0);
                case 1:
                    return fragments.get(1);
                case 2:
                    return fragments.get(2);
            }

            return null;
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            Locale l = Locale.getDefault();
            switch (position) {
                case 0:
                    return getString(R.string.title_bookmark).toUpperCase(l);
                case 1:
                    return getString(R.string.title_update).toUpperCase(l);
                case 2:
                    return getString(R.string.title_list).toUpperCase(l);
            }
            return null;
        }
    }

}
