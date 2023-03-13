package com.example.prm392_personalexpensetracking.ui.settings;

import static android.content.ContentValues.TAG;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.prm392_personalexpensetracking.LoginActivity;
import com.example.prm392_personalexpensetracking.R;
import com.example.prm392_personalexpensetracking.databinding.FragmentSettingsBinding;
import com.example.prm392_personalexpensetracking.model.Category;
import com.example.prm392_personalexpensetracking.model.CategoryReport;
import com.example.prm392_personalexpensetracking.model.Expense;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Set;

public class SettingsFragment extends Fragment {

    private FragmentSettingsBinding binding;
    Button currencyBtn, languageBtn, faqBtn, logOutBtn;
    TextView textViewUsername, textViewEmail;
    FirebaseFirestore fStore;
    FirebaseAuth fAuth;
    FirebaseUser fUser;

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

        setProfileName();

        currencyBtn = binding.chooseCurrencyBtn;
        languageBtn = binding.languageBtn;
        faqBtn = binding.faqBtn;
        logOutBtn = binding.signOutBtn;

        currencyBtn.setOnClickListener(view -> showCustomDialog());

        logOutBtn.setOnClickListener(view -> {
            fAuth.signOut();
            Intent intent = new Intent(getActivity(), LoginActivity.class);
            startActivity(intent);
        });


        return root;
    }

    private void setProfileName(){
        fStore.collection("Data").document(fAuth.getUid()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        textViewUsername.setText(document.getString("displayName"));
                        textViewEmail.setText(fAuth.getCurrentUser().getEmail());
                    } else {
                        Log.d(TAG, "No such document");
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    void showCustomDialog() {
        final Dialog dialog = new Dialog(getActivity());
        //We have added a title in the custom layout. So let's disable the default title.
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //The user will be able to cancel the dialog bu clicking anywhere outside the dialog.
        dialog.setCancelable(true);
        //Mention the name of the layout of your custom dialog.
        dialog.setContentView(R.layout.currency_dialog);

        //Initializing the views of the dialog.
        final EditText nameEt = dialog.findViewById(R.id.name_et);
        final EditText ageEt = dialog.findViewById(R.id.age_et);
        final CheckBox termsCb = dialog.findViewById(R.id.terms_cb);
        Button submitButton = dialog.findViewById(R.id.submit_button);


        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = nameEt.getText().toString();
                String age = ageEt.getText().toString();
                Boolean hasAccepted = termsCb.isChecked();
//                populateInfoTv(name,age,hasAccepted);
                dialog.dismiss();
            }
        });

        dialog.show();
    }
}