package com.managmentdic;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;
import com.managmentdic.dialogs.CmDialog;
import com.managmentdic.dialogs.CustomDialogClass;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

import java.util.HashMap;
import java.util.Map;


public class MainActivity extends AppCompatActivity {


    private Toolbar toolbar;
    public static Cursor c;
    public Drawer result;
    public Typeface faface;
   /* private ViewPager viewPager;
    private TabLayout tabLayout;
*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        try {
            toolbar = (Toolbar) findViewById(R.id.tool_bar);
            TextView mTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);
            mTitle.setText(getResources().getString(R.string.app_name));

            float dp = this.getResources().getDisplayMetrics().density;
            int subItemPaddingLeft = (int) (18 * dp);
            int subItemPaddingTopAndBottom = (int) (10 * dp);

            faface = Typeface.createFromAsset(this.getAssets(), "IRKoodak.ttf");
            mTitle.setTypeface(faface, Typeface.BOLD);
            mTitle.setPadding(subItemPaddingLeft, subItemPaddingTopAndBottom,
                    20, subItemPaddingTopAndBottom);
            mTitle.setGravity(Gravity.CENTER_VERTICAL);

            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            getSupportActionBar().setDisplayUseLogoEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);

            Fragment newFragment = MainFragment.newInstance();
            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.add(R.id.mainactivity, newFragment);
            transaction.commit();
            PrimaryDrawerItem item1 = new PrimaryDrawerItem().withIdentifier(1).withName(R.string.search).withIcon(
                    android.R.drawable.ic_menu_search).withTypeface(faface);
            SecondaryDrawerItem item2 = new SecondaryDrawerItem().withIdentifier(2).withName(R.string.settingTitle)
                    .withTypeface(faface);

            AccountHeader headerResult = new AccountHeaderBuilder()
                    .withActivity(this)
                    .withSelectionListEnabledForSingleProfile(false)
                    .withHeaderBackground(R.drawable.ic_profile)
                    .addProfiles(
                            new ProfileDrawerItem().withName(getString(R.string.profile))
                                    .withIcon(getResources().getDrawable(R.mipmap.ic_launcher))
                    )

                    .build();

            result = new DrawerBuilder()
                    .withActivity(this)
                    .withToolbar(toolbar)
                    .withActionBarDrawerToggleAnimated(true)
                    .withAccountHeader(headerResult)
                    .addDrawerItems(
                            item1,
                            new DividerDrawerItem(),
                            new SecondaryDrawerItem().withName(R.string.Favs).withIcon(R.drawable.ic_fav)
                                    .withTypeface(faface),
                            item2.withIcon(R.drawable.ic_setting),

                            new SecondaryDrawerItem().withName(R.string.send_vocab).withTypeface(faface)
                                    .withIcon(R.drawable.ic_contact),

                            new SecondaryDrawerItem().withName(R.string.sharee).
                                    withIcon(android.R.drawable.ic_menu_share).withTypeface(faface),
                            new SecondaryDrawerItem().withName(R.string.comment)
                                    .withIcon(R.drawable.ic_comment).withTypeface(faface),

                            new SecondaryDrawerItem().withName(R.string.downloadVazhed).withIcon(R.drawable.ic_baseline_cloud_download_24)
                                    .withTypeface(faface)

                    )
                    .withOnDrawerItemClickListener((view, position, drawerItem) -> {

                        if (position == 1) {
                            Fragment newFragment1;
                            FragmentTransaction transaction1 = getFragmentManager().beginTransaction();
                            newFragment1 = new MainFragment().newInstance();
                            transaction1.replace(R.id.mainactivity, newFragment1);
                            transaction1.commit();

                        }
                        if (position == 3) {
                            Fragment newFragment1;
                            FragmentTransaction transaction1 = getFragmentManager().beginTransaction();
                            newFragment1 = new FavFragment().newInstance();
                            transaction1.replace(R.id.mainactivity, newFragment1);
                            transaction1.commit();

                        }

                        if (position == 4) {
                            Fragment newFragment1;
                            FragmentTransaction transaction1 = getFragmentManager().beginTransaction();
                            newFragment1 = new SettingFragment().newInstance();
                            transaction1.replace(R.id.mainactivity, newFragment1).addToBackStack("main");
                            transaction1.commit();
                        }

                        if (position == 5) {
                            CustomDialogClass cmd = new CustomDialogClass(MainActivity.this);
                            cmd.show();
                        }

                        if (position == 6) {
                            String shareBody = getResources().getString(R.string.share_using);
                            Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                            sharingIntent.setType("text/plain");
                            sharingIntent.putExtra(Intent.EXTRA_SUBJECT, "دیکشنری تخصصی مدیریت");
                            sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
                            startActivity(Intent.createChooser(sharingIntent, "به اشتراک گذاری"));
                        }
                        if (position == 7) {
                            if (isBazaarInstalled()) {
                                Intent intent = new Intent(Intent.ACTION_EDIT);
                                intent.setData(Uri.parse("bazaar://details?id=" + getPackageName()));
                                intent.setPackage("com.farsitel.bazaar");
                                startActivity(intent);
                                getSharedPreferences("PREFERENCE", Context.MODE_PRIVATE)
                                        .edit()
                                        .putBoolean("isCommented", true)
                                        .apply();
                            } else {
                                Toast.makeText(this, getString(R.string.bazar_not_installed), Toast.LENGTH_SHORT).show();
                            }
                        }

                        if (position == 8) {
                            if (isBazaarInstalled()) {
                                Intent intent = new Intent(Intent.ACTION_VIEW);
                                intent.setData(Uri.parse("bazaar://details?id=com.mr47.vazhehdictionary"));
                                intent.setPackage("com.farsitel.bazaar");
                                startActivity(intent);
                            } else {
                                Toast.makeText(this, getString(R.string.bazar_not_installed), Toast.LENGTH_SHORT).show();
                            }
                        }
                        result.closeDrawer();
                        return true;
                    })
                    .build();


        } catch (Exception e) {
        }
    }
    private Boolean isBazaarInstalled() {
        PackageManager manager = getPackageManager();
        try {
            manager.getPackageInfo("com.farsitel.bazaar", PackageManager.GET_ACTIVITIES);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }
    private int GetDipsFromPixel(int pixels) {
        final float scale = getResources().getDisplayMetrics().density;
        return (int) (pixels * scale + 0.5f);
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        return true;
    }

    private boolean doubleBackToExitPressedOnce = false;

    @Override
    protected void onResume() {
        super.onResume();
        // .... other stuff in my onResume ....
        this.doubleBackToExitPressedOnce = false;
        /*viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setVisibility(View.VISIBLE);*/
    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();

            return;
        } else {
            if (!checkCommented()) {
                CmDialog cmd = new CmDialog(MainActivity.this);
                cmd.show();
            } else {
                this.doubleBackToExitPressedOnce = true;
                Toast.makeText(this, "Press Back Again", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public boolean checkCommented() {
        boolean isCommented = MainActivity.this.getSharedPreferences("PREFERENCE",
                Context.MODE_PRIVATE).getBoolean("isCommented", false);
        if (isCommented) {
            return true;
        } else
            return false;
    }

 /*   private void setupViewPager(final ViewPager viewPager) {
        final FragmentPageAdapter adapter = new FragmentPageAdapter(getFragmentManager());

        adapter.addFragment(MainFragment.newInstance(),getString(R.string.search));
        adapter.addFragment(FavFragment.newInstance(), getString(R.string.Favs));
        adapter.notifyDataSetChanged();

        viewPager.setAdapter(adapter);
    }*/
}


 /*   class FragmentPageAdapter extends FragmentStatePagerAdapter {

        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        @Override
        public int getItemPosition(Object object) {
            return POSITION_NONE;
        }

        public FragmentPageAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }*/