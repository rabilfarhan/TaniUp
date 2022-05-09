package com.ndp.picodiploma.taniup.ui.insert;

import android.app.Application;

import androidx.lifecycle.ViewModel;

import com.ndp.picodiploma.taniup.database.Note;
import com.ndp.picodiploma.taniup.repo.NoteRepo;

public class crudViewModel extends ViewModel {

    private final NoteRepo mNoteRepository;

    public crudViewModel(Application application) {
        mNoteRepository = new NoteRepo(application);
    }
    public void insert(Note note) {
        mNoteRepository.insert(note);
    }
    public void update(Note note) {
        mNoteRepository.update(note);
    }
    public void delete(Note note) {
        mNoteRepository.delete(note);
    }
}
