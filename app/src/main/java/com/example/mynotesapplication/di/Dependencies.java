package com.example.mynotesapplication.di;

import com.example.mynotesapplication.domain.FireStoreNotesRepository;
import com.example.mynotesapplication.domain.NotesRepository;

public class Dependencies { //класс зависимотси

    private static final NotesRepository NOTES_REPOSITORY = new FireStoreNotesRepository();

    public static NotesRepository getNotesRepository(){
        return NOTES_REPOSITORY;
    }
}
