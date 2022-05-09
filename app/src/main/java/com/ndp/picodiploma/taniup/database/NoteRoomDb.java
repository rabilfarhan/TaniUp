package com.ndp.picodiploma.taniup.database;



import android.content.Context;

import androidx.room.Database;

import androidx.room.Room;
import androidx.room.RoomDatabase;


@Database(entities = {Note.class}, version = 1)

public abstract class NoteRoomDb extends RoomDatabase {

    public abstract NoteDao noteDao();

    private static volatile NoteRoomDb INSTANCE;

    public static NoteRoomDb getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (NoteRoomDb.class) {
                INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                        NoteRoomDb.class, "note_database")
                        .build();
            }
        }
        return INSTANCE;
    }
}
