package com.managmentdic.dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import com.managmentdic.R;

/**
 * Created by Payam on 9/6/2015.
 */
public class CmDialog extends Dialog  implements   android.view.View.OnClickListener {
    public Activity c;
    public Dialog d;
    public Button yes, no;


    public CmDialog(Activity a) {
        super(a);
        // TODO Auto-generated constructor stub
        this.c = a;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_cmcustom);
        yes = (Button) findViewById(R.id.btn_yes);
        no = (Button) findViewById(R.id.btn_no);
        yes.setOnClickListener(this);
        no.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_yes:
                try {
                    Intent intent = new Intent(Intent.ACTION_EDIT);
                    intent.setData(Uri.parse("http://cafebazaar.ir/app/" + c.getPackageName() + "/?l=fa"));
                    c.startActivity(intent);
                    c.getSharedPreferences("PREFERENCE", Context.MODE_PRIVATE)
                            .edit()
                            .putBoolean("isCommented", true)
                            .apply();
                } catch (ActivityNotFoundException e) {
                    c.startActivity(new Intent(
                            Intent.ACTION_VIEW,
                            Uri.parse("http://cafebazaar.ir/app/" + c.getPackageName() + "/?l=fa")));
                  //  FirebaseCrash.report(new Exception(e.toString()));
                }
                dismiss();
                break;
            case R.id.btn_no:
                c.getSharedPreferences("PREFERENCE", Context.MODE_PRIVATE)
                        .edit()
                        .putBoolean("isCommented", false)
                        .apply();
                c.finish();
                break;
            default:
                break;
        }
        dismiss();
    }
}
