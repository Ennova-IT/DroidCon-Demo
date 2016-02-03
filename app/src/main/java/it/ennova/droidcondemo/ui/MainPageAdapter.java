package it.ennova.droidcondemo.ui;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainPageAdapter extends FragmentPagerAdapter{

    private List<Fragment> fragments = new ArrayList<>();
    private List<String> titles = new ArrayList<>();

    public static MainPageAdapter forMainActivity(@NonNull FragmentManager manager) {
        final MainPageAdapter adapter = new MainPageAdapter(manager);
        adapter.addFragment(new ServerFragment(), "SERVER");
        adapter.addFragment(new ClientFragment(), "CLIENT");

        return adapter;
    }

    public MainPageAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles.get(position);
    }

    public void addFragment(@NonNull Fragment fragment, @NonNull String title) {
        fragments.add(fragment);
        titles.add(title);
    }
}
