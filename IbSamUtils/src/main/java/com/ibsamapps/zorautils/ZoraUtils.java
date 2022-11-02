package com.ibsamapps.zorautils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.Toast;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import okio.BufferedSource;
import okio.Okio;

public class ZoraUtils {

    public static String readJsonFromAssets(Activity activity, String filePath) {
        try {
            InputStream input = activity.getAssets().open(filePath);
            BufferedSource source = Okio.buffer(Okio.source(input));
            return source.readByteString().string(StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    private static void Rate_App(Activity activity) {
        try {
            activity.startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("market://details?id=" + activity.getPackageName())));
        } catch (ActivityNotFoundException e) {
            activity.startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("http://play.google.com/store/apps/details?id=" + activity.getPackageName())));
        }
    }

    public static boolean isOnline(Activity activity) {
        ConnectivityManager cm = (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);
        @SuppressLint("MissingPermission")
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    public static void exitApp(Activity activity){
        Dialog dialog = new Dialog(activity);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_exit);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        ImageView close = dialog.findViewById(R.id.clear_dialog);
        LinearLayout yesExit = dialog.findViewById(R.id.btn_yesConfirm);
        LinearLayout rateExit = dialog.findViewById(R.id.btn_noConfirm);

        close.setOnClickListener(view -> dialog.dismiss());
        yesExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((Activity)activity).finishAffinity();
            }
        });
        rateExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                ratingApp(activity);
            }
        });
        dialog.show();
    }

    public static void checkInternet(Activity activity) {
        if (!isOnline(activity)){
            activity.startActivity(new Intent(activity.getApplicationContext(), NoConnection.class));
        }
    }

    public static void ratingApp(Activity activity){
        RatingBar ratingBar;
        Button submitRating;
        final Dialog dialog = new Dialog(activity);
        dialog.setContentView(R.layout.ratedialog);
        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        ratingBar = dialog.findViewById(R.id.ratingBar);
        ratingBar.setNumStars(5);
        submitRating = dialog.findViewById(R.id.submitRating);

        dialog.show();

        submitRating.setOnClickListener(v -> {
            if (ratingBar.getRating() >= 4) {
                Rate_App(activity);
            } else {
                Toast.makeText(activity, "Thanks for your feedback!!", Toast.LENGTH_SHORT).show();
            }
            dialog.dismiss();
        });
    }

    public static void shareApp(Activity activity){
        try {
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, activity.getResources().getString(R.string.app_name));
            String shareMessage= "\n" + activity.getResources().getString(R.string.share_text) + "\n\n";
            shareMessage = shareMessage + "https://play.google.com/store/apps/details?id=" + activity.getPackageName() +"\n\n";
            shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
            activity.startActivity(Intent.createChooser(shareIntent, "Share wherever you want"));
        } catch(Exception e) {
            //e.toString();
        }
    }

    public static void privacyOutApp(Activity activity, String url){
        activity.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
    }

    public static void privacyInApp(Activity activity, String url){
        Intent intent;
        intent = new Intent(activity.getApplicationContext(), PrivacyActivity.class);
        intent.putExtra("Url", url);
        activity.startActivity(intent);
    }

}
