package com.example.remotev10.app;

import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.widget.SimpleAdapter;

public class MainActivity extends FragmentActivity implements
        ActionBar.TabListener, InfoFragment.Communicator {

    private ViewPager viewPager;
    private TabsPagerAdapter mAdapter;
    private ActionBar actionBar;
    Thread mthread;
    Info info;
    Modusmanager modusmanager;
    // Tab titles
    private String[] tabs = { "Control", "Info", "Properties"};
    private Handler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //new Variablen();
        //ExampleFragment fragment = (ExampleFragment) getFragmentManager().findFragmentById(R.id.example_fragment);

        // Initilization
        viewPager = (ViewPager) findViewById(R.id.pager);
        actionBar = getActionBar();
        mAdapter = new TabsPagerAdapter(getSupportFragmentManager());

        viewPager.setAdapter(mAdapter);
        actionBar.setHomeButtonEnabled(false);
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);


        // Adding Tabs
        for (String tab_name : tabs) {
            actionBar.addTab(actionBar.newTab().setText(tab_name)
                    .setTabListener(this));
        }



        //fragmentManager.findFragmentById(R.id.fragment).onActivityCreated();

        /**
         * on swiping the viewpager make respective tab selected
         * */
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                // on changing the page
                // make respected tab selected
                actionBar.setSelectedNavigationItem(position);
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
            }

            @Override
            public void onPageScrollStateChanged(int arg0) {
            }
        });

        info = new Info();
        new Variablen();

        final SimpleAdapter adapter = new SimpleAdapter(this, info.data,
                android.R.layout.simple_list_item_2,
                new String[] {"name", "value"},
                new int[] {android.R.id.text1,
                        android.R.id.text2});

        //handler für Kommunikation mit dem Thread
        mHandler = new Handler(){

            @Override
            public void handleMessage(Message msg){

                Bundle bundle = msg.getData();

                //hier if-Block, um die Nachricht aus dem Thread an das addressierte Fragment
                //weiterzugeben

                ControlFragment controlFragment = (ControlFragment) findFragmentByPosition(0);
                //wenn die empfangene Nachricht vom typ mode ist, setze mode und state entsprechend
                if(bundle.getString("typ").equals("mode")){
                    if (bundle.getInt("idx") == 0){
                        controlFragment.textView1.setText(String.valueOf(bundle.getInt("state")));
                    }
                    else if(bundle.getInt("idx") == 1){
                        controlFragment.textView2.setText(String.valueOf(bundle.getInt("state")));
                    }
                }


                //wenn die empfangene Nachricht vom typ mode ist, setze mode und state entsprechend
                if(bundle.getString("typ").equals("info")){

                    if(info.compare(bundle.getString("name"))){
                        info.setValue(bundle.getString("name"),bundle.getString("value"));
                    }
                    else{
                        info.add(bundle.getString("name"),bundle.getString("value"));
                    }
                    InfoFragment infoFragment = (InfoFragment) findFragmentByPosition(1);
                    infoFragment.changeData();
                }
            }
        };
        mthread = new Thread(new ThreadRunnable(mHandler));
        mthread.start();
    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {

    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
        // on tab selected
        // show respected fragment view
        viewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {
    }



    @Override
    public void respond(String data) {
        //getFragmentManager().findFragmentByTag("control");
        //PropertiesFragment propertiesfragment = (PropertiesFragment) findFragmentByPosition(2);
        //propertiesfragment.changeData(data);

    }

    //Methode um an die fragment tags ranzukommen
    public Fragment findFragmentByPosition(int position) {
        return getSupportFragmentManager().findFragmentByTag(
                "android:switcher:" + viewPager.getId() + ":"
                        + mAdapter.getItemId(position));
    }


}


