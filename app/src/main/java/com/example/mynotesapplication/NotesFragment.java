package com.example.mynotesapplication;

import android.annotation.SuppressLint;
import android.content.res.Configuration;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import static com.example.mynotesapplication.Note.getNotes;
import static com.example.mynotesapplication.NoteDetail.SELECTED_NOTE;

public class NotesFragment extends Fragment {

    Note note;
    View dataContainer;
    static final String  SELECTED_INDEX = "index";
    int selectedIndex = 0;

    public NotesFragment() {
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putInt(SELECTED_INDEX, selectedIndex);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_notes, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (savedInstanceState != null) {
            selectedIndex = savedInstanceState.getInt(SELECTED_NOTE, 0);
            //note = (Note)savedInstanceState.getSerializable(SELECTED_NOTE);
            //note = (Note)savedInstanceState.getParcelable(SELECTED_NOTE);
        }

        /*dataContainer = view.findViewById(R.id.data_container);
        initNotes(dataContainer);*/
        initNotes(view.findViewById(R.id.data_container));

        if (isLandscape()) {
            showLandNoteDetails(selectedIndex);
        }
    }

    private boolean isLandscape() {
        return getResources().getConfiguration().orientation
                == Configuration.ORIENTATION_LANDSCAPE;
    }

    public void initNotes() {
        initNotes(dataContainer);
    }

    @SuppressLint("NewApi")
    private void initNotes(View view){
        LinearLayout layoutView = (LinearLayout) view;
        //layoutView.removeAllViews();
        for (int i = 0; i < getNotes().length; i++) {

            TextView tv = new TextView(getContext());
            tv.setText(getNotes()[i].getTitle());
            tv.setTextSize(24);
            layoutView.addView(tv);

            final int index = i;
            tv.setOnClickListener(v -> {
                showNoteDetails(index);
            });
        }
    }

   /*private void showNoteDetails(){
        this.note = note;
        if (isLandscape()) {
            showLandNoteDetails(note);
        } else {
            showPortNoteDetails(note);
        }
    }*/

    private void showNoteDetails(int index){

        if (isLandscape()) {
            showLandNoteDetails(index);
        } else {
            showPortNoteDetails(index);
        }
    }

    private void showPortNoteDetails(int index) {

        NoteDetail noteDetail = NoteDetail.newInstance(index);
        FragmentManager fragmentManager =
                requireActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction =
                fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.notes_container, noteDetail);
        fragmentTransaction.addToBackStack("");
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        fragmentTransaction.commit();
    }





    private void showLandNoteDetails(int index) {
        NoteDetail noteDetail = NoteDetail.newInstance(index);
        FragmentManager fragmentManager =
                requireActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.note_container, noteDetail); // замена  фрагмента
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        fragmentTransaction.commit();
    }
}
//

