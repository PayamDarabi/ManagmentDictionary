package com.managmentdic;


import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.channguyen.rsv.RangeSliderView;

/**
 * A simple {@link Fragment} subclass.
 */
public class SettingFragment extends Fragment {

    static Context context;
    View view;
    SharedPreferences.Editor editor;
    SharedPreferences prefs;
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
            largeSlider = (RangeSliderView) view.findViewById(
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

            final RangeSliderView.OnSlideListener listener = new RangeSliderView.OnSlideListener() {
                @Override
                public void onSlide(int index) {
                    float speed = Float.valueOf("0.0");
                    if (index == 0) {
                        speed = Float.parseFloat("0.25");
                    }
                    if (index == 1) {
                        speed = Float.parseFloat("0.5");
                    }
                    if (index == 2) {
                        speed = Float.parseFloat("1.0");
                    }
                    if (index == 3) {
                        speed = Float.parseFloat("1.5");
                    }
                    if (index == 4) {
                        speed = Float.parseFloat("2.0");
                    }
                    context.getSharedPreferences("PREFERENCE", Context.MODE_PRIVATE)
                            .edit()
                            .putFloat("SpeechSpeed", speed)
                            .apply();
                }
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
  /*  @Override
    public void setMenuVisibility(final boolean visible) {
        super.setMenuVisibility(visible);
        if (visible) {
            // ...
        }
    }
    // ...*/
}
