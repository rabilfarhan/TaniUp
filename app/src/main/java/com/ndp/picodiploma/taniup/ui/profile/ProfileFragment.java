package com.ndp.picodiploma.taniup.ui.profile;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.ndp.picodiploma.taniup.Login.Login;
import com.ndp.picodiploma.taniup.Login.Register;
import com.ndp.picodiploma.taniup.R;
import com.ndp.picodiploma.taniup.databinding.FragmentProfileBinding;


public class ProfileFragment extends Fragment {

    private FragmentProfileBinding binding;
    private FirebaseAuth mAuth;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentProfileBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.tvLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getActivity().getApplication(), Login.class));
                getActivity().finish();
            }

        });

        FirebaseUser fbUser = FirebaseAuth.getInstance().getCurrentUser();
        String name = fbUser.getDisplayName();
        String email = fbUser.getEmail();
        setData(name, email);


    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }



    private void setData(String name, String email) {
        binding.nameUser.setText(name);
        binding.etUsername.setText(name);
        binding.etEmail.setText(email);

    }
}