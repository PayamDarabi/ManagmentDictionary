package com.managmentdic;


import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.github.channguyen.rsv.RangeSliderView;
import com.managmentdic.adapters.MySpinnerAdapter;

import java.util.Arrays;

/**
 * A simple {@link Fragment} subclass.
 */
public class SettingFragment extends Fragment {

    static Context context;
    View view;
    SharedPreferences.Editor editor;
    SharedPreferences prefs;
    String[] accents = { "آمریکایی", "انگلیسی"};

    private RangeSliderView largeSlider;
     public SettingFragment() {
        // Required empty public constructor
    }
    public static SettingFragment newInstance() {
        final SettingFragment fragment = new SettingFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getActivity();
    }

    @Override
    public void onResume() {
        super.onResume();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        try {
            view = inflater.inflate(R.layout.fragment_setting, container, false);
            view.setFocusableInTouchMode(true);
            view.requestFocus();
            Spinner spin = view.findViewById(R.id.spinner);

            //Creating the ArrayAdapter instance having the accents list
            ArrayAdapter aa = new ArrayAdapter(context, android.R.layout.simple_spinner_item, accents);
            MySpinnerAdapter adapter = new MySpinnerAdapter(
                    context,
                    android.R.layout.simple_spinner_item,
                    accents);
            aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            //Setting the ArrayAdapter data on the Spinner
            spin.setAdapter(adapter);
            int selectedAccent = 0;
            String accentStr = context.getSharedPreferences("PREFERENCE", Context.MODE_PRIVATE).getString(
                    "Accent", "US");
            if (accentStr.equals("US")) {
                selectedAccent = 0;
            } else {
                selectedAccent = 1;
            }
            spin.setSelection(selectedAccent);
            spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                               @Override
                                               public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                                   String accent = "US";
                                                   if (id == 0) {
                                                       accent = "US";
                                                   } else {
                                                       accent = "UK";
                                                   }
                                                   context.getSharedPreferences("PREFERENCE", Context.MODE_PRIVATE)
                                                           .edit()
                                                           .putString("Accent", accent)
                                                           .apply();
                                               };
                                               @Override
                                               public void onNothingSelected(AdapterView<?> parent) {

                                               }
                                           });

            view.setOnKeyListener((v, keyCode, event) -> {
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
            });
            largeSlider = view.findViewById(
                    R.id.rsv_speed);
            float speed = context.getSharedPreferences("PREFERENCE", Context.MODE_PRIVATE).getFloat(
                    "SpeechSpeed", 1);
            if (speed == 0.25) {
                largeSlider.setInitialIndex(0);
            }
            if (speed == 0.5) {
                largeSlider.setInitialIndex(1);
            }
            if (speed == 1.0) {
                largeSlider.setInitialIndex(2);
            }
            if (speed == 1.5) {
                largeSlider.setInitialIndex(3);
            }
            if (speed == 2.0) {
                largeSlider.setInitialIndex(4);
            }

            final RangeSliderView.OnSlideListener listener = index -> {
                float speed1 = Float.valueOf("0.0");
                if (index == 0) {
                    speed1 = Float.parseFloat("0.25");
                }
                if (index == 1) {
                    speed1 = Float.parseFloat("0.5");
                }
                if (index == 2) {
                    speed1 = Float.parseFloat("1.0");
                }
                if (index == 3) {
                    speed1 = Float.parseFloat("1.5");
                }
                if (index == 4) {
                    speed1 = Float.parseFloat("2.0");
                }
                context.getSharedPreferences("PREFERENCE", Context.MODE_PRIVATE)
                        .edit()
                        .putFloat("SpeechSpeed", speed1)
                        .apply();
            };
            largeSlider.setOnSlideListener(listener);

        } catch (Exception e) {
        }
        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }
}
