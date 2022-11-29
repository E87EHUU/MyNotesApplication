package com.example.mynotesapplication;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

public class NotificationFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_notification, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getView().findViewById(R.id.button_toast).setOnClickListener(v -> showToast());

        view.findViewById(R.id.button_snack_bar).setOnClickListener(v -> showSnackBar(view));

        view.findViewById(R.id.button_alert_dialog_with_view).setOnClickListener(v -> showDialogWithCustomView());
    }

    private void showToast(){
        Toast.makeText(getContext(),"Внимание",Toast.LENGTH_SHORT).show();
    }

    private void showToast(String text){
        Toast.makeText(getContext(),text,Toast.LENGTH_SHORT).show();
    }

    private void showSnackBar (View view) {
        Snackbar.make(view.findViewById(R.id.notification_container_view),"Внимание",Snackbar.LENGTH_SHORT)
                .setAction("Нажми меня", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        showToast();
                    }
                })
                .show();
    }

   private void showDialogWithCustomView(){
        final View customView = getLayoutInflater().inflate(R.layout.custom_dialog_view,null);
        customView.findViewById(R.id.button_custom_view).setOnClickListener(v ->
                showToast() );

        new AlertDialog.Builder(getContext())
                .setTitle("Мое диалоговое окно")
                .setView(customView)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        showToast("OK click!");
                    }
                })
                .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        showToast("NO click!");
                    }
                })
                .setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        showToast("CANCEL click!");
                    }
                })
                .show();
   }

}