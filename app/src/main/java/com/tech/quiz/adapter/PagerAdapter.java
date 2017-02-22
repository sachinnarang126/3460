package com.tech.quiz.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.util.ArrayMap;

import com.tech.quiz.util.Constant;
import com.tech.quiz.view.fragment.CategoryFragment;

import java.util.Map;

public class PagerAdapter extends FragmentPagerAdapter {
    private String tabTitles[] = new String[]{"ANDROID", "iOS", "JAVA"};
    private Map<String, CategoryFragment> categoryFragmentMap;

    public PagerAdapter(FragmentManager fm) {
        super(fm);
        initCategoryFragmentMap();
    }

    @Override
    public int getCount() {
        return tabTitles.length;
    }

    @Override
    public Fragment getItem(int position) {
        return categoryFragmentMap.get(tabTitles[position]);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles[position];
    }

    private void initCategoryFragmentMap() {
        categoryFragmentMap = new ArrayMap<>();
        categoryFragmentMap.put(tabTitles[0], CategoryFragment.getInstance(tabTitles[0], Constant.ANDROID));
        categoryFragmentMap.put(tabTitles[1], CategoryFragment.getInstance(tabTitles[1], Constant.IOS));
        categoryFragmentMap.put(tabTitles[2], CategoryFragment.getInstance(tabTitles[2], Constant.JAVA));
    }

    public CategoryFragment getCategoryInstance(int key) {
        return categoryFragmentMap.get(tabTitles[key]);
    }
}
