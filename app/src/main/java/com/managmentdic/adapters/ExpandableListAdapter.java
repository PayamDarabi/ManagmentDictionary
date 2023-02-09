package com.managmentdic.adapters;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.speech.tts.TextToSpeech;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.managmentdic.FavFragment;
import com.managmentdic.MainFragment;
import com.managmentdic.R;
import com.managmentdic.widgets.BubbleTextGetter;
import com.managmentdic.widgets.FavPairs;
import com.managmentdic.widgets.StringPair;

import java.util.List;
import java.util.Locale;

/**
 * Created by payam on 09/06/15.
 */
public class ExpandableListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements BubbleTextGetter {

    public static final int HEADER = 0;
    TextToSpeech t1;
    SharedPreferences.Editor editor;
    SharedPreferences prefs;
    boolean isShearched=false;
    private Context context;
    private List<Item> data;
    boolean isFave=false;

    public ExpandableListAdapter(Context c, List<Item> data, boolean isFav, boolean isSearched) {
        this.data = data;
        context = c;
        isFave = isFav;
        this.isShearched = isSearched;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int type) {
        View view = null;
        Context context = parent.getContext();
        float dp = context.getResources().getDisplayMetrics().density;
        int subItemPaddingLeft = (int) (18 * dp);
        int subItemPaddingTopAndBottom = (int) (10 * dp);
        Typeface faface,enface;

        switch (type) {
            case HEADER:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_adapter, parent, false);
                TextView txt_eng = view.findViewById(R.id.header_title);
                TextView txt_farsi = view.findViewById(R.id.txt_tarjome);
                LinearLayout lay_back = view.findViewById(R.id.lay_back);

                enface = Typeface.createFromAsset(context.getAssets(), "ROBOTOREGULAR.TTF");
                txt_eng.setTypeface(enface);
                faface = Typeface.createFromAsset(context.getAssets(), "IRKoodak.ttf");
                txt_farsi.setTypeface(faface);
                txt_farsi.setPadding(subItemPaddingLeft, subItemPaddingTopAndBottom,
                        20, subItemPaddingTopAndBottom);
             //   txt_farsi.setTextColor(0x88FFFFFF);
                txt_farsi.setGravity(Gravity.CENTER_VERTICAL);

                ListHeaderViewHolder header = new ListHeaderViewHolder(view);
                return header;
        }
        return null;
    }

    @Override
    public String getTextToShowInBubble(final int pos) {
        if (data == null || data.size() == 0)
            return "";
        Character ch = data.get(pos).englisi.charAt(0);
        if (Character.isDigit(ch)) {
            return "#";
        } else
            return Character.toString(ch);
    }
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final Item item = data.get(position);
        switch (item.type) {

            case HEADER:
                final ListHeaderViewHolder itemController = (ListHeaderViewHolder) holder;
                itemController.enligisi.setText(item.englisi);
                itemController.farsi.setText(item.farsi);


                if (chekFavs(item.englisi)) {
                    itemController.select.setImageResource(R.drawable.ic_unfav);
                } else
                    itemController.select.setImageResource(R.drawable.ic_fav);

                t1 = new TextToSpeech(context.getApplicationContext(), new TextToSpeech.OnInitListener() {
                    @Override
                    public void onInit(int status) {
                        if (status != TextToSpeech.ERROR) {
                            t1.setLanguage(Locale.UK);
                        }
                    }
                });

                itemController.talafoz.setOnClickListener(v -> {
                    String toSpeak = item.englisi;
                    float speed = context.getSharedPreferences("PREFERENCE",Context.MODE_PRIVATE).getFloat(
                            "SpeechSpeed",1);
                    t1.setPitch(1);
                    t1.setSpeechRate(speed);
                    t1.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);
                });
                itemController.copy.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String english = item.englisi;
                        String Persian = item.farsi;
                        String word = english + " = " + Persian;
                        ClipboardManager clipboard = (ClipboardManager) v.getContext().getSystemService(Context.CLIPBOARD_SERVICE);
                        ClipData clip = ClipData.newPlainText("word", word);
                        clipboard.setPrimaryClip(clip);
                        Toast.makeText(v.getContext(), "Copied To Clipboard", Toast.LENGTH_SHORT).show();
                    }
                });

                itemController.select.setOnClickListener(v -> {
                    int pos;
                    String english = item.englisi;
                    String persian = item.farsi;
                    if (!chekFavs(english)) {
                        if (addTofavs(english, persian)) {
                            if(isShearched)
                                itemController.select.setImageResource(R.drawable.ic_unfav_white);

                            itemController.select.setImageResource(R.drawable.ic_unfav);
                            //     Toast.makeText(context,"به مورد علاقه های شما اضافه شد "+english, Toast.LENGTH_SHORT).show();
                            new AsyncTask<Void, Void, Void>() {
                                protected Void doInBackground(Void... in) {

                                    if (FavFragment.ins != null)
                                        FavFragment.ins.Update();
                                    return null;
                                }
                            }.execute();
                        } else
                            Toast.makeText(context, "خطایی رخ داده", Toast.LENGTH_SHORT).show();
                    } else {
                        if (removeFromfavs(english)) {
                            if(isShearched)
                                itemController.select.setImageResource(R.drawable.ic_fav_white);

                            itemController.select.setImageResource(R.drawable.ic_fav);
                            //     Toast.makeText(context,"از مورد علاقه های شما حذف شد "+english, Toast.LENGTH_SHORT).show();
                            new AsyncTask<Void, Void, Void>() {
                                protected Void doInBackground(Void... in) {
                                    if (FavFragment.ins != null)
                                        FavFragment.ins.Update();
                                    MainFragment.ins.Update();
                                    return null;
                                }
                            }.execute();
                        } else
                            Toast.makeText(context, "خطایی رخ داده", Toast.LENGTH_SHORT).show();
                    }
                });

                if(isShearched && position==0)
                {
                    itemController.lay_back.setBackgroundColor(context.getResources().getColor(R.color.searchback));
                    itemController.enligisi.setTextColor(context.getResources().getColor(R.color.md_white_1000));
                    itemController.farsi.setTextColor(context.getResources().getColor(R.color.md_white_1000));
                    itemController.talafoz.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_speaker_pressed_white));
                    itemController.talafoz.setImageResource(R.drawable.imgvw_press_top);
                    itemController.copy.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_copy_white));
                    itemController.select.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_fav_white));

                }

                break;
        }
    }

    public boolean chekFavs(String english) {
        try {
            prefs = context.getSharedPreferences("UserData", Context.MODE_PRIVATE);
            String shActions = prefs.getString("Favs", "");
            Boolean hasAction = false;
            if (!shActions.equals("")) {
                final FavPairs favPairs = new Gson().fromJson(shActions, FavPairs.class);
                List<StringPair> favs = favPairs.getFavs();
                for (int i = 0; i < favs.size(); i++) {
                    if (favs.get(i).getKey() == null)
                        continue;
                    if (favs.get(i).getKey().toString().equals(english))
                        hasAction = true;
                }
            }
            return hasAction;
        }catch (Exception e){
            return false;
        }
    }

    public boolean addTofavs(String english, String persian) {
        try {
            prefs = context.getSharedPreferences("UserData", Context.MODE_PRIVATE);
            editor = context.getSharedPreferences("UserData", Context.MODE_PRIVATE).edit();
            String shActions = prefs.getString("Favs", "");
            FavPairs favPairs = new Gson().fromJson(shActions, FavPairs.class);
            if (favPairs == null) {
                FavPairs favPairs1 = new FavPairs();
                StringPair stringPair = new StringPair(english, persian);
                favPairs1.getFavs().add(stringPair);
                String actionJson = new Gson().toJson(favPairs1,FavPairs.class);
                editor.putString("Favs", actionJson);
                editor.commit();
            } else {
                favPairs.getFavs().add(new StringPair(english, persian));
                String actionJson = new Gson().toJson(favPairs,FavPairs.class);
              //  editor.remove("Favs");
                editor.putString("Favs", actionJson);
                editor.commit();
            }

        } catch (Exception e) {
           // FirebaseCrash.report(new Exception(e.toString()));
            return false;
        }
        return true;
    }

    public boolean removeFromfavs(String english) {
        try {
            editor = context.getSharedPreferences("UserData", Context.MODE_PRIVATE).edit();
            String shActions = prefs.getString("Favs", "");
            final FavPairs favPairs = new Gson().fromJson(shActions, FavPairs.class);
            if (!shActions.equals("")) {
                List<StringPair> favs = favPairs.getFavs();
                for (int i = 0; i < favs.size(); i++) {
                    if (favs.get(i).getKey() == null)
                        continue;
                    if (favs.get(i).getKey().toString().equals(english)) {


                        favPairs.getFavs().remove(i);
                        String actionJson = new Gson().toJson(favPairs,FavPairs.class);
                      //  editor.remove("Favs");
                        editor.putString("Favs", actionJson);
                        editor.commit();
                        break;

                    }
                }
            }
        }
        catch (Exception e)
        {
         //   FirebaseCrash.report(new Exception(e.toString()));
            return false;
        }
        return true;
    }
    @Override
    public int getItemViewType(int position) {
        return data.get(position).type;
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    private static class ListHeaderViewHolder extends RecyclerView.ViewHolder {
        public TextView enligisi;
        public TextView farsi;
        public ImageView talafoz;
        public ImageView select;
        public ImageView copy;
        public LinearLayout lay_back;

        public ListHeaderViewHolder(View itemView) {
            super(itemView);
            enligisi = itemView.findViewById(R.id.header_title);
            farsi = itemView.findViewById(R.id.txt_tarjome);
            copy = itemView.findViewById(R.id.btn_copy);
            talafoz = itemView.findViewById(R.id.btn_spell);
            select = itemView.findViewById(R.id.btn_fav);
            lay_back= itemView.findViewById(R.id.lay_back);

        }
    }

    public static class Item {
        public int type;
        public String englisi;
        public String farsi;

        public Item() {
        }

        public Item(int type, String englisi, String farsi) {
            this.type = type;
            this.englisi = englisi;
            this.farsi = farsi;
        }
    }
}