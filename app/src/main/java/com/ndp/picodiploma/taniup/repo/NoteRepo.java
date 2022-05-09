package com.ndp.picodiploma.taniup.repo;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.ndp.picodiploma.taniup.database.Note;
import com.ndp.picodiploma.taniup.database.NoteDao;
import com.ndp.picodiploma.taniup.database.NoteRoomDb;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class NoteRepo {

    private final NoteDao mNotesDao;

    private final ExecutorService executorService;

    public NoteRepo(Application application) {
        executorService = Executors.newSingleThreadExecutor();
        NoteRoomDb db = NoteRoomDb.getDatabase(application);
        mNotesDao = db.noteDao();
    }
    public LiveData<List<Note>> getAllNotes() {
        return mNotesDao.getAllNotes();
    }
    public void insert(final Note note) {
        executorService.execute(() -> mNotesDao.insert(note));
    }

    public void delete(final Note note){
        executorService.execute(() -> mNotesDao.delete(note));
    }

    public void update(final Note note){
        executorService.execute(() -> mNotesDao.update(note));
    }

}
