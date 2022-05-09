package com.ndp.picodiploma.taniup.ui.main;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.ndp.picodiploma.taniup.database.Note;
import com.ndp.picodiploma.taniup.repo.NoteRepo;

import java.util.List;

public class noteViewModel extends ViewModel {

    private final NoteRepo mNoteRepository;

    public noteViewModel(Application application) {
        mNoteRepository = new NoteRepo(application);
    }
    LiveData<List<Note>> getAllNotes() {
        return mNoteRepository.getAllNotes();
    }

}
