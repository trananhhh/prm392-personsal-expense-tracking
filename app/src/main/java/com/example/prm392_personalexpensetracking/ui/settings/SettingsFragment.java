package com.example.prm392_personalexpensetracking.ui.settings;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.prm392_personalexpensetracking.ExpenseActivity;
import com.example.prm392_personalexpensetracking.LoginActivity;
import com.example.prm392_personalexpensetracking.MainActivity;
import com.example.prm392_personalexpensetracking.R;
import com.example.prm392_personalexpensetracking.databinding.FragmentSettingsBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class SettingsFragment extends Fragment {

    private FragmentSettingsBinding binding;
    Button currencyBtn, languageBtn, faqBtn, logOutBtn;
    TextView textViewUsername, textViewEmail;
    FirebaseFirestore fStore;
    FirebaseAuth fAuth;
    FirebaseUser fUser;

    Dialog dialog;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentSettingsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
//        Firebase
        fStore = FirebaseFirestore.getInstance();
        fAuth = FirebaseAuth.getInstance();
        fUser = fAuth.getCurrentUser();

        textViewUsername = binding.textViewUsername;
        textViewEmail = binding.textViewEmail;

        textViewUsername.setText(MainActivity.displayName);
        textViewEmail.setText(MainActivity.email);

        currencyBtn = binding.chooseCurrencyBtn;
        languageBtn = binding.languageBtn;
        faqBtn = binding.faqBtn;
        logOutBtn = binding.signOutBtn;

        currencyBtn.setOnClickListener(view -> showCurrencyDialog());
        languageBtn.setOnClickListener(view -> showLanguageDialog());
        faqBtn.setOnClickListener(view -> showFaqDialog());

        logOutBtn.setOnClickListener(view -> {
            fAuth.signOut();
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

    void showCurrencyDialog() {
        dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.currency_dialog);

        final RadioButton usdBtn = dialog.findViewById(R.id.usdBtn);
        final RadioButton vndBtn = dialog.findViewById(R.id.vndBtn);
        final RadioButton euroBtn = dialog.findViewById(R.id.euroBtn);
        Button submitButton = dialog.findViewById(R.id.saveBtn);

        if(MainActivity.currency == "$") usdBtn.setChecked(true);
        if(MainActivity.currency == "đ") vndBtn.setChecked(true);
        if(MainActivity.currency == "€") euroBtn.setChecked(true);

        usdBtn.setOnClickListener(view -> MainActivity.currency = "$");
        vndBtn.setOnClickListener(view -> MainActivity.currency = "đ");
        euroBtn.setOnClickListener(view -> MainActivity.currency = "€");

        submitButton.setOnClickListener(view -> setCurrency(MainActivity.currency));

        dialog.show();
    }

    private void setCurrency(String currency){
        fStore.collection("Data").document(fAuth.getUid()).update("currency", currency).addOnSuccessListener(unused -> {
            Toast.makeText(getActivity(), "Saved", Toast.LENGTH_SHORT).show();
            dialog.dismiss();
        }).addOnFailureListener(e -> Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show());

    }
    void showLanguageDialog() {
        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.language_dialog);

        final RadioButton engBtn = dialog.findViewById(R.id.engBtn);
        engBtn.setChecked(true);

        Button submitButton = dialog.findViewById(R.id.saveBtn);
        submitButton.setOnClickListener(view -> dialog.dismiss());

        dialog.show();
    }
    void showFaqDialog() {
        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.faq_dialog);

        Button submitButton = dialog.findViewById(R.id.saveBtn);
        submitButton.setOnClickListener(view -> dialog.dismiss());

        dialog.show();
    }
}