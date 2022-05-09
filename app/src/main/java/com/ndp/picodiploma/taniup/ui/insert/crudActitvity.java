package com.ndp.picodiploma.taniup.ui.insert;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;

import com.ndp.picodiploma.taniup.Helper.DateHelper;
import com.ndp.picodiploma.taniup.Helper.ViewModelFactory;
import com.ndp.picodiploma.taniup.R;
import com.ndp.picodiploma.taniup.database.Note;
import com.ndp.picodiploma.taniup.databinding.ActivityCrudActitvityBinding;

public class crudActitvity extends AppCompatActivity {


    public static final String EXTRA_NOTE = "extra_note";
    private static final String EXTRA_POSITION = "Position";
    private final int ALERT_DIALOG_CLOSE = 10;
    private final int ALERT_DIALOG_DELETE = 20;

    private boolean isEdit = false;
    private Note note;

    private crudViewModel crudViewModel;
    private ActivityCrudActitvityBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCrudActitvityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        crudViewModel = obtainViewModel(crudActitvity.this);

        note = getIntent().getParcelableExtra(EXTRA_NOTE);
        if (note != null) {
            isEdit = true;
        } else {
            note = new Note();
        }
        String actionBarTitle;
        String btnTitle;
        if (isEdit) {
            actionBarTitle = getString(R.string.change);
            btnTitle = getString(R.string.update);
            if (note != null) {
                binding.edtTitle.setText(note.getTitle());
                binding.edtDescription.setText(note.getDescription());
            }
        } else {
            actionBarTitle = getString(R.string.add);
            btnTitle = getString(R.string.save);
        }
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(actionBarTitle);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        binding.btnSubmit.setText(btnTitle);

        binding.btnSubmit.setOnClickListener(view -> {
            String title = binding.edtTitle.getText().toString().trim();
            String description = binding.edtDescription.getText().toString().trim();

            if (title.isEmpty()) {
                binding.edtTitle.setError(getString(R.string.empty));
            } else if (description.isEmpty()) {
                binding.edtDescription.setError(getString(R.string.empty));
            } else {
                note.setTitle(title);
                note.setDescription(description);

                if (isEdit) {
                    crudViewModel.update(note);
                    showToast(getString(R.string.changed));
                } else {
                    note.setDate(DateHelper.getCurrentDate());
                    crudViewModel.insert(note);
                    showToast(getString(R.string.added));
                }
                finish();
            }
        });
    }


    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding = null;
    }

    @NonNull
    private static crudViewModel obtainViewModel(AppCompatActivity activity) {
        ViewModelFactory factory = ViewModelFactory.getInstance(activity.getApplication());
        return new ViewModelProvider(activity, factory).get(crudViewModel.class);
    }

}