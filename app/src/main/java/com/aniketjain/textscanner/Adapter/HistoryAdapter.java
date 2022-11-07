package com.aniketjain.textscanner.Adapter;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.aniketjain.textscanner.Model.HistoryModel;
import com.aniketjain.textscanner.R;

import java.util.ArrayList;

import io.paperdb.Paper;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.viewHolder> {



    Context context;
    ArrayList<String> list;

    public HistoryAdapter(Context context, ArrayList<String> list) {
        this.context = context;
        this.list = list;
    }
    @NonNull
    @Override
    public HistoryAdapter.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.history_sample, parent, false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryAdapter.viewHolder holder, int position) {


        String s = list.get(position);
        holder.historyText.setText(s);

        String s1 = holder.historyText.getText().toString();


        holder.copy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipboardManager clipboardManager = (ClipboardManager)
                        context.getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clipData = ClipData.newPlainText("Copied text",s1 );
                clipboardManager.setPrimaryClip(clipData);

//        let user know data save on clipBoard Successfully.
                Toast.makeText(context, "Copied to clipBoard", Toast.LENGTH_SHORT).show();
            }
        });

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Dialog dialog = new Dialog(context);
                View mylayout = LayoutInflater.from(context).inflate(R.layout.delete_history, null);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setCancelable(true);
                dialog.setContentView(mylayout);

                Button delete = mylayout.findViewById(R.id.deleteHistory);
                Button cancle = mylayout.findViewById(R.id.cancleHistory);


                delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        list.remove(position);
                        Paper.book().write("history",list);
                        dialog.dismiss();
                        notifyDataSetChanged();
                    }
                });

                cancle.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });


                dialog.getWindow().setBackgroundDrawableResource(R.drawable.dialog_animation);
                dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation3;
                dialog.setCancelable(false);
                dialog.show();


            }
        });

        holder.share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareTextOnly(s1);

            }
        });


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        TextView historyText;
        ImageView delete,copy,share;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            this.historyText = itemView.findViewById(R.id.textHistroy);
            this.delete = itemView.findViewById(R.id.delete);
            this.copy = itemView.findViewById(R.id.copy);
            this.share = itemView.findViewById(R.id.share);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    ShowUpgradePopUp();
                }
            });
        }
        private void ShowUpgradePopUp() {

            final Dialog dialog = new Dialog(context);
            View dialogView = LayoutInflater.from(context).inflate(R.layout.detail_text, null);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setCancelable(true);
            dialog.setContentView(dialogView);

//            final AlertDialog dialogBuilder = new AlertDialog.Builder(context).create();
//            LayoutInflater inflater = LayoutInflater.from(context);
//            View dialogView = inflater.inflate(R.layout.detail_text, null);

            TextView textScan = dialogView.findViewById(R.id.scanText);
            textScan.setText(historyText.getText().toString());

            ImageView closeDialog = dialogView.findViewById(R.id.closeDialog);
            closeDialog.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });


//            AppCompatButton settings = dialogView.findViewById(R.id.update_btn);
//            settings.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//
//                    final String appPackageName = getPackageName(); // getPackageName() from Context or Activity object
//                    try {
//                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
//                    } catch (android.content.ActivityNotFoundException anfe) {
//                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
//                    }
//
//
//                }
//            });


            dialog.getWindow().setBackgroundDrawableResource(R.drawable.dialog_animation);
            dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation3;
            dialog.setCancelable(false);
            dialog.show();

        }

    }
    private void shareTextOnly(String titlee) {
        String sharebody = titlee;

        // The value which we will sending through data via
        // other applications is defined
        // via the Intent.ACTION_SEND
        Intent intentt = new Intent(Intent.ACTION_SEND);

        // setting type of data shared as text
        intentt.setType("text/plain");
        intentt.putExtra(Intent.EXTRA_SUBJECT, "Subject Here");

        // Adding the text to share using putExtra
        intentt.putExtra(Intent.EXTRA_TEXT, sharebody);
        context.startActivity(Intent.createChooser(intentt, "Share Via"));
    }


}
