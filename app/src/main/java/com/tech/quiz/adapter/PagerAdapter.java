package com.tech.quiz.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.tech.quiz.util.Constant;
import com.tech.quiz.view.fragment.CategoryFragment;

public class PagerAdapter extends FragmentPagerAdapter {
    private String tabTitles[] = new String[]{"ANDROID", "iOS", "JAVA"};

    public PagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public int getCount() {
        return tabTitles.length;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return CategoryFragment.getInstance(tabTitles[position], Constant.ANDROID);
            case 1:
                return CategoryFragment.getInstance(tabTitles[position], Constant.IOS);
            case 2:
                return CategoryFragment.getInstance(tabTitles[position], Constant.JAVA);
        }
        return null;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles[position];
    }
}
