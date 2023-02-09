package com.managmentdic.widgets;

/**
 * Created by payam on 12/25/2016.
 */

import android.graphics.Paint;
import android.util.Log;
import android.view.Gravity;
import android.widget.TextView;


public class TextViewJustify {

	/*
	 * PLEASE DO NOT REMOVE Coded by Mathew Kurian I wrote this code for a
	 * Google Interview for Internship. Unfortunately, I got too nervous during
	 * the interview that I messed, but anyhow that doesn't matter. I have
	 * resent my work in hopes that I might still get a position there. Thank
	 * you :DD
	 */

    final static String SYSTEM_NEWLINE = "\n";
    final static float COMPLEXITY = 2.12f; // Reducing this will increase
    // efficiency but will decrease
    // effectiveness
    static Paint p;

    public static void justifyText(final TextView tv, final double origWidth) {
        String s = tv.getText().toString();

        p = tv.getPaint();
        // LineSpacing(0.2f, 1.5f);
        String[] splits = s.split(SYSTEM_NEWLINE);
        double width = origWidth;// origWidth;
        for (int x = 0; x < splits.length; x++) {
            Log.e(splits[x], x + "");
            if (p.measureText(splits[x]) > width) {

                splits[x] = wrap(splits[x], width, p);
                String[] microSplits = splits[x].split(SYSTEM_NEWLINE);
                for (int y = 0; y < microSplits.length - 1; y++)
                    microSplits[y] = justify(removeLast(microSplits[y], " "),
                            width, p);
                StringBuilder smb_internal = new StringBuilder();
                for (int z = 0; z < microSplits.length; z++)
                    smb_internal.append(microSplits[z]
                            + ((z + 1 < microSplits.length) ? SYSTEM_NEWLINE
                            : ""));
                splits[x] = smb_internal.toString();
            }
        }
        final StringBuilder smb = new StringBuilder();
        for (String cleaned : splits)
            smb.append(cleaned + SYSTEM_NEWLINE);
        tv.setGravity(Gravity.RIGHT);
        tv.setText(smb);
    }



    public static String justifyText2(final TextView tv, final float origWidth) {
        String s = tv.getText().toString();

        p = tv.getPaint();
        // LineSpacing(0.2f, 1.5f);
        String[] splits = s.split(SYSTEM_NEWLINE);
        float width = origWidth;// origWidth;
        for (int x = 0; x < splits.length; x++) {
            Log.e(splits[x], x + "");
            if (p.measureText(splits[x]) > width) {

                splits[x] = wrap(splits[x], width, p);
                String[] microSplits = splits[x].split(SYSTEM_NEWLINE);
                for (int y = 0; y < microSplits.length - 1; y++)
                    microSplits[y] = justify(removeLast(microSplits[y], " "),
                            width, p);
                StringBuilder smb_internal = new StringBuilder();
                for (int z = 0; z < microSplits.length; z++)
                    smb_internal.append(microSplits[z]
                            + ((z + 1 < microSplits.length) ? SYSTEM_NEWLINE
                            : ""));
                splits[x] = smb_internal.toString();
            }
        }
        final StringBuilder smb = new StringBuilder();
        for (String cleaned : splits)
            smb.append(cleaned + SYSTEM_NEWLINE);
        // tv.setGravity(Gravity.LEFT);
        // tv.setText(smb);
        return "salam" + smb.toString();
    }

    private static String wrap(String s, double width, Paint p) {
        String[] str = s.split("\\s"); // regex
        StringBuilder smb = new StringBuilder(); // save memory
        smb.append(SYSTEM_NEWLINE);
        for (int x = 0; x < str.length; x++) {
            float length = p.measureText(str[x]);
            String[] pieces = smb.toString().split(SYSTEM_NEWLINE);
            try {
                if (p.measureText(pieces[pieces.length - 1]) + length > width)
                    smb.append(SYSTEM_NEWLINE);
            } catch (Exception e) { //FirebaseCrash.report(new Exception(e.toString()));
            }
            smb.append(str[x] + " ");
        }
        return smb.toString().replaceFirst(SYSTEM_NEWLINE, "");
    }

    private static String removeLast(String s, String g) {
        if (s.contains(g)) {
            int index = s.lastIndexOf(g);
            int indexEnd = index + g.length();
            if (index == 0)
                return s.substring(1);
            else if (index == s.length() - 1)
                return s.substring(0, index);
            else
                return s.substring(0, index) + s.substring(indexEnd);
        }
        return s;
    }

    private static String justifyOperation(String s, double width, Paint p) {
        float holder = (float) (COMPLEXITY * Math.random());
        while (s.contains(Float.toString(holder)))
            holder = (float) (COMPLEXITY * Math.random());
        String holder_string = Float.toString(holder);
        double lessThan = width;
        int timeOut = 100;
        int current = 0;
        while (p.measureText(s) < lessThan && current < timeOut) {

            try {
                s = s.replaceFirst(" ([^" + holder_string + "])", " "
                        + holder_string + "$1");
            } catch (Exception e) { //FirebaseCrash.report(new Exception(e.toString()));

            }
            lessThan = p.measureText(holder_string) + lessThan
                    - p.measureText(" ");
            current++;
        }
        String cleaned = s.replaceAll(holder_string, " ");
        return cleaned;
    }

    private static String justify(String s, double width, Paint p) {
        while (p.measureText(s) < width) {
            s = justifyOperation(s, width, p);
        }
        return s;
    }
}