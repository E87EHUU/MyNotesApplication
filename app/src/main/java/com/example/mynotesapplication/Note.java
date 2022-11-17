package com.example.mynotesapplication;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.RequiresApi;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Random;

@RequiresApi(api = Build.VERSION_CODES.O)
public class Note implements Parcelable {

    private static final Random random = new Random();
    private static Note[] notes;
    private static int counter;

    public static int getCounter() {
        return counter;
    }

   // private int id;
    private String title;
    private String description;
    private LocalDateTime creationDate;

    /*public int getId(){
        return id;
    }*/


    protected Note(Parcel in) {
        title = in.readString();
        description = in.readString();
    }

    public static final Creator<Note> CREATOR = new Creator<Note>() {
        @Override
        public Note createFromParcel(Parcel in) {
            return new Note(in);
        }

        @Override
        public Note[] newArray(int size) {
            return new Note[size];
        }
    };

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public static Note[] getNotes(){
        return notes;
    }

    public String getDescription() {
        return description;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    static {
        notes = new Note[10];
        for (int i = 0; i < notes.length; i++) {
            notes[i] = Note.getNote(i);
        }
    }

    public Note(String title, String description, LocalDateTime creationDate) {
        this.title = title;
        this.description = description;
        this.creationDate = creationDate;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @SuppressLint("DefaultLocale")
    public static Note getNote(int index){
        String title = String.format("Заметка %d", index);
        String description = String.format("Описание заметки %d", index);
        LocalDateTime creationDate = LocalDateTime.now().plusDays(-random.nextInt(5));
        return new Note(title, description, creationDate);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(title);
        parcel.writeString(description);
    }
}
//
//
