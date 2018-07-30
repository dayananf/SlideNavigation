package com.slidenavigation;

import android.app.Fragment;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.slidenavigation.fragment.CenteredTextFragment;
import com.slidenavigation.fragment.MessageTextFragment;
import com.slidenavigation.navigation.DrawerAdapter;
import com.slidenavigation.navigation.DrawerItem;
import com.slidenavigation.navigation.SimpleItem;
import com.yarolegovich.slidingrootnav.SlidingRootNav;
import com.yarolegovich.slidingrootnav.SlidingRootNavBuilder;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity implements DrawerAdapter.OnItemSelectedListener {

    private static final int POS_DASHBOARD = 0;
    private static final int POS_ACCOUNT = 1;
    private static final int POS_MESSAGES = 2;
    private static final int POS_CART = 3;
    private static final int POS_SENT = 4;
    private static final int POS_SAVE = 5;
    private static final int POS_LOGOUT = 6;

    private String[] screenTitles;
    private Drawable[] screenIcons;
    Toolbar toolbar;

    private SlidingRootNav slidingRootNav;
    int pos = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        try {
            toolbar = findViewById(R.id.toolbar);
            toolsetTitle(pos);
            setSupportActionBar(toolbar);

            slidingRootNav = new SlidingRootNavBuilder(this)
                    .withToolbarMenuToggle(toolbar)
                    .withMenuOpened(false)
                    .withContentClickableWhenMenuOpened(false)
                    .withSavedState(savedInstanceState)
                    .withMenuLayout(R.layout.menu_left_drawer)
                    .inject();

            screenIcons = loadScreenIcons();
            screenTitles = loadScreenTitles();

            DrawerAdapter adapter = new DrawerAdapter(Arrays.asList(
                    createItemFor(POS_DASHBOARD).setChecked(true),
                    createItemFor(POS_ACCOUNT),
                    createItemFor(POS_MESSAGES),
                    createItemFor(POS_CART),
                    createItemFor(POS_SENT),
                    createItemFor(POS_SAVE),
                    createItemFor(POS_LOGOUT)));
            adapter.setListener(this);

            RecyclerView list = findViewById(R.id.list);
            list.setNestedScrollingEnabled(false);
            list.setLayoutManager(new LinearLayoutManager(this));
            list.setAdapter(adapter);

            adapter.setSelected(POS_DASHBOARD);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void toolsetTitle(int pos) {
        try {
            String[] activityTitles = getResources().getStringArray(R.array.ld_activityScreenTitles);
            toolbar.setTitle(activityTitles[pos]);
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onItemSelected(int position) {

        try {
            pos = position;
            toolsetTitle(pos);
            // slidingRootNav.closeMenu();
            Fragment selectedScreen = null;
            if (position == POS_MESSAGES) {
                selectedScreen = MessageTextFragment.createFor(screenTitles[position]);
            } else if (position == POS_DASHBOARD) {
                selectedScreen = CenteredTextFragment.createFor(screenTitles[position]);
            } else if (position == POS_CART) {
                selectedScreen = MessageTextFragment.createFor(screenTitles[position]);
            } else if (position == POS_ACCOUNT) {
                selectedScreen = CenteredTextFragment.createFor(screenTitles[position]);
            } else if (position == POS_SENT) {
                selectedScreen = MessageTextFragment.createFor(screenTitles[position]);
            } else if (position == POS_SAVE) {
                selectedScreen = CenteredTextFragment.createFor(screenTitles[position]);
            } else if (position == POS_LOGOUT) {
                finish();
            }

            showFragment(selectedScreen);
            slidingRootNav.closeMenu();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showFragment(Fragment fragment) {
        try {
            getFragmentManager().beginTransaction()
                    .replace(R.id.container, fragment)
                    .commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private DrawerItem createItemFor(int position) {

        SimpleItem simpleItem = null;
        try {
            simpleItem = new SimpleItem(screenIcons[position], screenTitles[position])
                    .withIconTint(color(R.color.textColorSecondary))
                    .withTextTint(color(R.color.textColorPrimary))
                    .withSelectedIconTint(color(R.color.colorAccent))
                    .withSelectedTextTint(color(R.color.colorAccent));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return simpleItem;
    }

    private String[] loadScreenTitles() {
        return getResources().getStringArray(R.array.ld_activityScreenTitles);
    }

    private Drawable[] loadScreenIcons() {
        Drawable[] icons = null;
        try {
            TypedArray ta = getResources().obtainTypedArray(R.array.ld_activityScreenIcons);
            icons = new Drawable[ta.length()];
            for (int i = 0; i < ta.length(); i++) {
                int id = ta.getResourceId(i, 0);
                if (id != 0) {
                    icons[i] = ContextCompat.getDrawable(this, id);
                }
            }
            ta.recycle();
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
        }
        return icons;
    }

    @ColorInt
    private int color(@ColorRes int res) {
        return ContextCompat.getColor(this, res);
    }


    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_search:
                Toast.makeText(this, "You have selected Search Menu", Toast.LENGTH_SHORT).show();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}