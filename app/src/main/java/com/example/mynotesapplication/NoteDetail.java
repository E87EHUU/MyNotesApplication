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
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.Arrays;

public class NoteDetail extends Fragment {

    static final String SELECTED_NOTE = "note";
    private Note note;

    public NoteDetail() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        /* if (savedInstanceState != null)
            requireActivity().getSupportFragmentManager().popBackStack();

         */
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        MenuItem menuItemExit = menu.findItem(R.id.action_exit);
        if (menuItemExit != null) {
            menuItemExit.setVisible(false);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_note, container, false);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle arguments = getArguments();

        Button buttonBack = view.findViewById(R.id.btnBack);
        buttonBack.setOnClickListener(v -> {
            requireActivity().getSupportFragmentManager().popBackStack();
        });

        if (arguments != null) {

            Note paramNote = (Note)arguments.getParcelable(SELECTED_NOTE);
            note = Arrays.stream(Note.getNotes()).filter(n -> n.getId() == paramNote.getId()).findFirst().get();



            TextView tvTitle = view.findViewById(R.id.tvTitle);
            tvTitle.setText(note.getTitle());
            tvTitle.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }
                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2){
                    note.setTitle(tvTitle.getText().toString());
                    updateData();
                }
                @Override
                public void afterTextChanged(Editable editable) { }
            });

            TextView tvDescription = view.findViewById(R.id.tvDescription);
            tvDescription.setText(note.getDescription());
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void updateData(){
        NotesFragment notesFragment = (NotesFragment) requireActivity().getSupportFragmentManager().getFragments().stream().filter( fragment -> fragment instanceof NotesFragment)
                .findFirst().get();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notesFragment.initNotes();
        }

    }

    public static NoteDetail newInstance(int index) {
        NoteDetail fragment = new NoteDetail();
        Bundle args = new Bundle();
        args.putInt(SELECTED_NOTE, index);
        fragment.setArguments(args);
        return fragment;
    }

    public static NoteDetail newInstance(Note note) {
        NoteDetail fragment = new NoteDetail();
        Bundle args = new Bundle();
        args.putParcelable(SELECTED_NOTE, note);
        fragment.setArguments(args);
        return fragment;
    }
}