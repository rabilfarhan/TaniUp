package com.ndp.picodiploma.taniup.ui.main;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import com.ndp.picodiploma.taniup.ui.insert.crudActitvity;
import com.ndp.picodiploma.taniup.Helper.ViewModelFactory;
import com.ndp.picodiploma.taniup.R;
import com.ndp.picodiploma.taniup.databinding.ActivityNoteBinding;

public class noteActivity extends AppCompatActivity {


    private ActivityNoteBinding binding;
    private NoteAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityNoteBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().hide();

        noteViewModel mainViewModel = obtainViewModel(noteActivity.this);
        mainViewModel.getAllNotes().observe(this, notes -> {
            if (notes != null) {
                adapter.setListNotes(notes);
            }
        });

        adapter = new NoteAdapter();
        binding.rvNotes.setLayoutManager(new GridLayoutManager(this, 2));
        binding.rvNotes.setHasFixedSize(true);
        binding.rvNotes.setAdapter(adapter);

        binding.fabAdd.setOnClickListener(view -> {
            if (view.getId() == R.id.fab_add) {
                Intent intent = new Intent(noteActivity.this, crudActitvity.class);
                startActivity(intent);
            }
        });
    }

    @NonNull
    private static noteViewModel obtainViewModel(AppCompatActivity activity) {
        ViewModelFactory factory = ViewModelFactory.getInstance(activity.getApplication());
        return new ViewModelProvider(activity, factory).get(noteViewModel.class);
    }

}