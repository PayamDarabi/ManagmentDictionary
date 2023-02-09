package com.managmentdic;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.managmentdic.adapters.DataBaseHelper;
import com.managmentdic.adapters.ExpandableListAdapter;
import com.managmentdic.widgets.FavPairs;
import com.managmentdic.widgets.StringPair;

import java.util.ArrayList;
import java.util.List;

public class FavFragment extends Fragment {


    public static FavFragment ins;
    Handler handler;

    static Context context;
    List<ExpandableListAdapter.Item> data = new ArrayList<>();
    DataBaseHelper myDbHelper;
 //   DataAdapter da;
    private RecyclerView recyclerview;
    private TextView textViewCounter;
    SharedPreferences prefs;
    View view;

    public FavFragment() {
    }

    public static FavFragment newInstance() {
        FavFragment fragment = new FavFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getActivity();
        myDbHelper = new DataBaseHelper(context);
//        da = new DataAdapter(context);

    }

    @Override
    public void onResume() {
     //      view= update();
        super.onResume();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        try {
            ins = this;
            handler = new Handler();

            // Inflate the layout for this fragment
            view = inflater.inflate(R.layout.fragment_fav, container, false);
            view.setFocusableInTouchMode(true);
            view.requestFocus();
            view.setOnKeyListener(new View.OnKeyListener() {
                @Override
                public boolean onKey(View v, int keyCode, KeyEvent event) {
                    if (keyCode == KeyEvent.KEYCODE_BACK) {
                        Fragment newFragment;
                        FragmentTransaction transaction = getFragmentManager().beginTransaction();
                        newFragment = new MainFragment().newInstance();
                        transaction.replace(R.id.mainactivity, newFragment);
                        transaction.commit();
                        return true;
                    } else {
                        return false;
                    }
                }
            });

            recyclerview = view.findViewById(R.id.recyclerview);
            recyclerview.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
            textViewCounter = view.findViewById(R.id.favstitle);

            Update();
        } catch (Exception e) {
        }
        return view;
    }

    public void Update() {

        data.clear();
        prefs = context.getSharedPreferences("UserData", Context.MODE_PRIVATE);
        String shActions = prefs.getString("Favs", "");
        if (!shActions.equals("")) {
            final FavPairs favPairs = new Gson().fromJson(shActions, FavPairs.class);
            List<StringPair> favs = favPairs.getFavs();
            for (int i = 0; i < favs.size(); i++) {
                if (favs.get(i).getKey() == null)
                    continue;
                else {
                    String English = favs.get(i).getKey().toString();
                    String Farsi = favs.get(i).getValue().toString();
                    ExpandableListAdapter.Item word =
                            new ExpandableListAdapter.Item(ExpandableListAdapter.HEADER,
                                    English, Farsi);
                    data.add(word);
                }
            }
        }
        handler.post(new Runnable() {
            @Override
            public void run() {
                try {
                    recyclerview.setAdapter(new ExpandableListAdapter(context, data, true,false));
                    textViewCounter.setText(getResources().getString(R.string.Favs).toString()
                            + " ( " + data.size() + " )");
                }catch (Exception e)
                {
                }
            }
        });
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        myDbHelper = new DataBaseHelper(activity);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }

}