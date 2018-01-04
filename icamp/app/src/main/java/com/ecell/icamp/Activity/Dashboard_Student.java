package com.ecell.icamp.Activity;

import android.content.SharedPreferences;
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
import android.widget.TextView;
import android.widget.Toast;

import com.ecell.icamp.Fragment.Company_Details;
import com.ecell.icamp.Fragment.Company_ListDetails;
import com.ecell.icamp.Fragment.Dummy;
import com.ecell.icamp.Fragment.Student_Details;
import com.ecell.icamp.R;

import java.lang.reflect.Field;

/**
 * Created by Niklaus on 28-Feb-17.
 */

public class Dashboard_Student extends AppCompatActivity {

    private String indexed;
    public static int index;

    private Dashboard_Student.SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;
    private BottomNavigationView bottomNavigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        Bundle bundle = getIntent().getExtras();
      //  indexed = bundle.getString("indexed");
      //  index = Integer.parseInt(indexed);


        //Toast.makeText(getBaseContext(), ""+indexed, Toast.LENGTH_LONG).show();

        mSectionsPagerAdapter = new Dashboard_Student.SectionsPagerAdapter(getSupportFragmentManager());
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        mViewPager.beginFakeDrag();

        bottomNavigation = (BottomNavigationView)findViewById(R.id.bottom_navigation);
        bottomNavigation.inflateMenu(R.menu.navbar_student);
        bottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                int id = item.getItemId();
                switch (id){
                    case R.id.db_company:
                        fragmentChange(0);
                        break;
                    case R.id.db_student:
                        fragmentChange(1);
                        break;
                    case R.id.db_message:
                        fragmentChange(2);
                        break;
                }
                return true;
            }
        });
        //disableShiftMode(bottomNavigation);

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
                case 0: return new Company_ListDetails();
                case 1: return new Student_Details();
                case 2: return new Dummy();
                default:return null;
            }
        }
        @Override
        public int getCount() {
            return 3;
        }
    }

    void fragmentChange(int position) {
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