package com.managmentdic;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.view.inputmethod.InputMethodSubtype;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.managmentdic.adapters.DataAdapter;
import com.managmentdic.adapters.DataBaseHelper;
import com.managmentdic.adapters.ExpandableListAdapter;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MainFragment extends Fragment {
    private RecyclerView recyclerview;
    public static Cursor c;
    public static MainFragment ins;
    Handler handler;
    static Context context;
    DataBaseHelper myDbHelper;
    DataAdapter da;

    public MainFragment() {
    }
    public static MainFragment newInstance() {
        MainFragment fragment = new MainFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            context = getActivity();
            myDbHelper = new DataBaseHelper(context);
            da = new DataAdapter(context);

            try {
                myDbHelper.createDataBase();
            } catch (IOException ioe) {
              //  FirebaseCrash.report(new Exception("Unable to create database"));
            }

            try {
                myDbHelper.openDataBase();
            } catch (SQLException sqlle) {
            //    FirebaseCrash.report(new Exception(sqlle.toString()));
            }

            c = da.selectall();
        }
        catch (Exception e){
            Log.d("db", e.toString());
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        recyclerview = (RecyclerView) view.findViewById(R.id.recyclerview);

        try {
            ins = this;
            handler = new Handler();

            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context,
                    LinearLayoutManager.VERTICAL, false);
            recyclerview.setLayoutManager(linearLayoutManager);

            final EditText editTextsrch = (EditText) view.findViewById(R.id.edittext_search);
            editTextsrch.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    String CurrentText = s.toString();
                    final List<ExpandableListAdapter.Item> data = new ArrayList<>();
                    if (CurrentText.equals("")) {
                        Cursor c = da.selectall();
                        if (c.moveToFirst()) {
                            do {
                                String English = c.getString(c.getColumnIndexOrThrow("English"));
                                String Farsi = c.getString(c.getColumnIndexOrThrow("Farsi"));
                                ExpandableListAdapter.Item word =
                                        new ExpandableListAdapter.Item(ExpandableListAdapter.HEADER, English, Farsi);
                                data.add(word);
                            } while (c.moveToNext());
                        }
                        c.close();
                        recyclerview.setAdapter(new ExpandableListAdapter(context, data, false,false));
                    } else {
                        if (CurrentText.length() > 1) {
                            Cursor c = da.LikeQuery(CurrentText);
                            if (c.moveToFirst()) {
                                do {
                                    String English = c.getString(c.getColumnIndexOrThrow("English"));
                                    String Farsi = c.getString(c.getColumnIndexOrThrow("Farsi"));
                                    ExpandableListAdapter.Item word =
                                            new ExpandableListAdapter.Item(ExpandableListAdapter.HEADER, English, Farsi);
                                    data.add(word);
                                } while (c.moveToNext());
                            }
                            c.close();
                            recyclerview.setAdapter(new ExpandableListAdapter(context, data, false,true));

                        }
                    }
                }
            });

            InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            InputMethodSubtype ims = imm.getCurrentInputMethodSubtype();
            String locale = ims.getLocale();
            ImageView imgv_switch = view.findViewById(R.id.img_changLanguage);


            final boolean[] isPersian = {false};
            final TextView txt_english = view.findViewById(R.id.txtEnglishType);
            final TextView txt_persian = view.findViewById(R.id.txt_perLang);

            if(locale.equals("en_GB")) {
                editTextsrch.setTextDirection(View.TEXT_DIRECTION_LTR);
                txt_persian.setTextColor(getResources().getColor(R.color.md_white_1000));
                txt_english.setTextColor(getResources().getColor(R.color.selectedLang));
                isPersian[0] = false;
            }

            else if(locale.equals("fa")) {
                editTextsrch.setTextDirection(View.TEXT_DIRECTION_RTL);
                txt_english.setTextColor(getResources().getColor(R.color.md_white_1000));
                txt_persian.setTextColor(getResources().getColor(R.color.selectedLang));
                isPersian[0] = true;

            }
            else
            {
                editTextsrch.setTextDirection(View.TEXT_DIRECTION_LTR);
                txt_persian.setTextColor(getResources().getColor(R.color.md_white_1000));
                txt_english.setTextColor(getResources().getColor(R.color.selectedLang));
                isPersian[0] =false;
            }



             txt_persian.setOnClickListener(view13 -> {
                 editTextsrch.setTextDirection(View.TEXT_DIRECTION_RTL);
                 txt_english.setTextColor(getResources().getColor(R.color.md_white_1000));
                 txt_persian.setTextColor(getResources().getColor(R.color.selectedLang));
                 isPersian[0] = true;

             });

            txt_english.setOnClickListener(view12 -> {
                editTextsrch.setTextDirection(View.TEXT_DIRECTION_LTR);
                txt_persian.setTextColor(getResources().getColor(R.color.md_white_1000));
                txt_english.setTextColor(getResources().getColor(R.color.selectedLang));
                isPersian[0] = false;

            });
            imgv_switch.setOnClickListener(view1 -> {
                if(!isPersian[0])
                {
                    editTextsrch.setTextDirection(View.TEXT_DIRECTION_LTR);
                    txt_persian.setTextColor(getResources().getColor(R.color.md_white_1000));
                    txt_english.setTextColor(getResources().getColor(R.color.selectedLang));
                    isPersian[0]=true;

                }
                else
                {
                    editTextsrch.setTextDirection(View.TEXT_DIRECTION_RTL);
                    txt_english.setTextColor(getResources().getColor(R.color.md_white_1000));
                    txt_persian.setTextColor(getResources().getColor(R.color.selectedLang));
                    isPersian[0] = false;
                }
            });
            Update();
        } catch (Exception e) {
        }
        return view;
    }
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        myDbHelper = new DataBaseHelper(activity);
    }

    public void Update() {
        if (c.isClosed()) {
            da = new DataAdapter(context);
            c = da.selectall();
        }

        final List<ExpandableListAdapter.Item> data = new ArrayList<>();
        if (c.moveToFirst()) {
            do {
                String English = c.getString(c.getColumnIndexOrThrow("English"));
                String Farsi = c.getString(c.getColumnIndexOrThrow("Farsi"));
                ExpandableListAdapter.Item word =
                            new ExpandableListAdapter.Item(ExpandableListAdapter.HEADER, English,Farsi);
                 data.add(word);

            } while (c.moveToNext());
        }
        c.close();
        handler.post(() -> recyclerview.setAdapter(new ExpandableListAdapter(context.getApplicationContext(), data, false,false)));
    }
}