package com.example.mynotesapplication;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Arrays;
import java.util.Optional;

public class NoteDetail extends Fragment {

    static final String SELECTED_NOTE = "note";
    private Note note;
    private Toast deleteToast;

    public NoteDetail() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // setHasOptionsMenu(true);
        /* if (savedInstanceState != null)
            requireActivity().getSupportFragmentManager().popBackStack();

         */
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        inflater.inflate(R.menu.note_menu, menu);

         MenuItem menuItemExit = menu.findItem(R.id.action_exit);
        if (menuItemExit != null) {
            menuItemExit.setVisible(false);
        }

        MenuItem menuItemAbout = menu.findItem(R.id.action_about);
        if (menuItemAbout != null) {
            menuItemAbout.setVisible(false);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_delete) {

            new AlertDialog.Builder(getContext())
                    .setTitle("Внимание").setMessage("Вы действительно желаете удалить заметку?")
                            .setPositiveButton("Да", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    Note.getNotes().remove(note);
                                    note = null;
                                    updateData();
                                    if (!isLandscape())
                                        requireActivity().getSupportFragmentManager().popBackStack();
                                    //Toast.makeText(getContext(), "Заметка удалена", Toast.LENGTH_SHORT).show();
                                    if(deleteToast != null)
                                        deleteToast.show();
                                }
                            }).setNegativeButton("Нет", null)
                    .show();


            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private Toast prepareCustomToast(View view){
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.my_toast_layout,
                (ViewGroup) view.findViewById(R.id.toast_layout_root));

        ImageView image = (ImageView) layout.findViewById(R.id.image);
        image.setImageResource(R.drawable.ic_edit);
        TextView text = (TextView) layout.findViewById(R.id.text);
        text.setText("Заметка удалена!");
        Toast toast = new Toast(getContext());
        toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(layout);
        return toast;
    }

    private boolean isLandscape () {
        return getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (savedInstanceState == null) // убирает задвоение кнопки "Удалить" в портретном режиме
        setHasOptionsMenu(true);
        return inflater.inflate(R.layout.fragment_note, container, false);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        deleteToast = prepareCustomToast(view);

        Bundle arguments = getArguments();

        Button buttonBack = view.findViewById(R.id.btnBack);
        if (buttonBack != null)

        buttonBack.setOnClickListener(v -> {
            requireActivity().getSupportFragmentManager().popBackStack();
        });

        if (arguments != null) {

            Note paramNote = (Note)arguments.getParcelable(SELECTED_NOTE);
            if (paramNote != null) {
                Optional<Note> selectedNote = Note.getNotes().stream().filter(n -> n.getId() == paramNote.getId()).findFirst();

                /* if (selectedNote.isPresent()) {
                    note = selectedNote.get();
                }
                else {
                    note = Note.getNotes().get(0);
                } */
                note = selectedNote.orElseGet(() -> Note.getNotes().get(0));
            }


            // note = Arrays.stream(Note.getNotes()).filter(n -> n.getId() == paramNote.getId()).findFirst().get();
            // note = Note.getNotes().stream().filter(n -> n.getId() == paramNote.getId()).findFirst().get();

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