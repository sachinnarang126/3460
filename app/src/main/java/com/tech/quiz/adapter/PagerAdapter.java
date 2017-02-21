package com.tech.quiz.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.tech.quiz.util.Constant;
import com.tech.quiz.view.fragment.CategoryFragment;

public class PagerAdapter extends FragmentStatePagerAdapter {
    private String tabTitles[] = new String[]{"ANDROID", "iOS", "JAVA"};

    public PagerAdapter(FragmentManager fm, Context context) {
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

    /*public View getTabView(int position) {
        View tab = LayoutInflater.from(context).inflate(R.layout.custom_tab, null);
        TextView tv = (TextView) tab.findViewById(R.id.custom_text);
        tv.setText(tabTitles[position]);
        return tab;
    }*/
}
