package com.example.prm392_personalexpensetracking.ui.notifications;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.prm392_personalexpensetracking.AddExpenseActivity;
import com.example.prm392_personalexpensetracking.LoginActivity;
import com.example.prm392_personalexpensetracking.databinding.FragmentNotificationsBinding;
import com.google.firebase.auth.FirebaseAuth;

public class NotificationsFragment extends Fragment {

    private FragmentNotificationsBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentNotificationsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        FirebaseAuth mAuth = FirebaseAuth.getInstance();

        Button logOutBtn = binding.signOutBtn;
        logOutBtn.setOnClickListener(view -> {
            mAuth.signOut();
            Intent intent = new Intent(getActivity(), LoginActivity.class);
            startActivity(intent);
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}