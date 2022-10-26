package com.example.mynotesapplication;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class NoteDetail extends Fragment {

    static final String ARG_INDEX = "index";
    static final String SELECTED_NOTE = "note";
    private Note note;

    public NoteDetail() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null)
            requireActivity().getSupportFragmentManager().popBackStack();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_note, container, false);
    }

    @SuppressLint("NewApi")
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle arguments = getArguments();

        Button buttonBack = view.findViewById(R.id.btnBack);
        buttonBack.setOnClickListener(v -> {
            requireActivity().getSupportFragmentManager().popBackStack();
        });

        if (arguments != null) {

            int index = arguments.getInt(ARG_INDEX);


            TextView tvTitle = view.findViewById(R.id.tvTitle);
            tvTitle.setText(Note.getNotes()[index].getTitle());
            tvTitle.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                }

                @SuppressLint("NewApi")
                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                   Note.getNotes()[index].setTitle(charSequence.toString());
                }

                @Override
                public void afterTextChanged(Editable editable) {
                }
            });

            TextView tvDescription = view.findViewById(R.id.tvDescription);
            tvDescription.setText(Note.getNotes()[index].getTitle());
        }
    }

  /* public static NoteDetail newInstance(Note note) {
        NoteDetail fragment = new NoteDetail();
        Bundle args = new Bundle();
        args.putInt(SELECTED_NOTE, note);
        fragment.setArguments(args);
        return fragment;
    }*/

    public static NoteDetail newInstance(int index) {
        NoteDetail fragment = new NoteDetail();
        Bundle args = new Bundle();
        args.putInt(SELECTED_NOTE, index);
        fragment.setArguments(args);
        return fragment;
    }
}