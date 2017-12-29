package com.ecell.icamp.Activity;

import android.os.Bundle;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;

import com.ecell.icamp.Fragment.Admin_Statistics;
import com.ecell.icamp.Fragment.Admin_StudentList;
import com.ecell.icamp.Fragment.Dummy;
import com.ecell.icamp.Fragment.Student_Details;
import com.ecell.icamp.R;

import java.lang.reflect.Field;

/**
 * Created by Niklaus on 28-Feb-17.
 */

public class Dashboard_Admin extends AppCompatActivity {

    private Dashboard_Admin.SectionsPagerAdapter mSectionsPagerAdapter;
    private static ViewPager mViewPager;
    private BottomNavigationView bottomNavigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        mSectionsPagerAdapter = new Dashboard_Admin.SectionsPagerAdapter(getSupportFragmentManager());
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        mViewPager.beginFakeDrag();

        bottomNavigation = (BottomNavigationView)findViewById(R.id.bottom_navigation);
        bottomNavigation.inflateMenu(R.menu.navbar_admin);
        bottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                int id = item.getItemId();
                switch (id){
                    case R.id.db_student:
                        fragmentChange(0);
                        break;
                    case R.id.db_company:
                        fragmentChange(1);
                        break;
                    case R.id.db_info:
                        fragmentChange(2);
                        break;
                }
                return true;
            }
        });
        disableShiftMode(bottomNavigation);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.menu_share:
                break;
            case R.id.menu_procedure:
                break;
            case R.id.menu_tnc:
                break;
            case R.id.menu_faq:
                break;
            case R.id.menu_aboutus:
        }
        return super.onOptionsItemSelected(item);
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {
        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }
        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0: return new Admin_StudentList();
                case 1: return new Dummy();
                case 2: return new Admin_Statistics();
                case 3: return new Student_Details();
                default:return null;
            }
        }
        @Override
        public int getCount() {
            return 4;
        }
    }

    public static void fragmentChange(int position) {
        mViewPager.setCurrentItem(position, true);
    }

    public static void disableShiftMode(BottomNavigationView view) {
        BottomNavigationMenuView menuView = (BottomNavigationMenuView) view.getChildAt(0);
        try {
            Field shiftingMode = menuView.getClass().getDeclaredField("mShiftingMode");
            shiftingMode.setAccessible(true);
            shiftingMode.setBoolean(menuView, false);
            shiftingMode.setAccessible(false);
            for (int i = 0; i < menuView.getChildCount(); i++) {
                BottomNavigationItemView item = (BottomNavigationItemView) menuView.getChildAt(i);
                item.setShiftingMode(false);
                item.setChecked(item.getItemData().isChecked());
            }
        } catch (NoSuchFieldException e) {
            Log.e("ERROR NO SUCH FIELD", "Unable to get shift mode field");
        } catch (IllegalAccessException e) {
            Log.e("ERROR ILLEGAL ALG", "Unable to change value of shift mode");
        }
    }
}