package com.managmentdic.dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.Window;

import com.managmentdic.R;

/**
 * Created by Payam on 8/27/2015.
 */
public class CustomDialogClass extends Dialog{

    public Activity c;
    public Dialog d;
//    public Button no;

    public CustomDialogClass(Activity a) {
        super(a);
        // TODO Auto-generated constructor stub
        this.c = a;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.custom_dialog);
    }
}