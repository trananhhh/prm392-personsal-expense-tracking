package com.example.prm392_personalexpensetracking.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.prm392_personalexpensetracking.AddExpenseActivity;
import com.example.prm392_personalexpensetracking.MainActivity;
import com.example.prm392_personalexpensetracking.adapter.ExpensesDayAdapter;
import com.example.prm392_personalexpensetracking.databinding.FragmentHomeBinding;
import com.example.prm392_personalexpensetracking.model.Category;
import com.example.prm392_personalexpensetracking.model.Expense;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class HomeFragment extends Fragment {
    private FragmentHomeBinding binding;
    private RecyclerView mRecyclerView;
    private ExpensesDayAdapter mExpenseAdapter ;
    private int totalExpenses = 0;
    private int currentBalance = 0;
    private int totalIncome = 0;
    private TextView expenseStat;
    private TextView balanceStat;
    private TextView incomeStat;
    private ExtendedFloatingActionButton addExpenseFab;
    private ArrayList<Expense> expenseArrayList;

    FirebaseFirestore fStore;
    FirebaseAuth fAuth;

    private void setStats(FragmentHomeBinding binding){
        totalExpenses = 0;
        totalIncome = 0;

        expenseStat = binding.expenseStat;
        balanceStat = binding.balanceStat;
        incomeStat = binding.incomeStat;

        expenseArrayList.forEach((item) -> {
            int catType = Category.getCategoryById(item.getCateId()).getType();
            if(catType == 1)
                totalIncome += item.getAmount();
            if(catType == 2)
                totalExpenses += item.getAmount();
        });

        currentBalance = totalIncome - totalExpenses;

        expenseStat.setText( MainActivity.intToMoneyFormat(totalExpenses));
        balanceStat.setText( MainActivity.intToMoneyFormat(currentBalance));
        incomeStat.setText( MainActivity.intToMoneyFormat(totalIncome));
    }

    private void bindingRecyclerView(FragmentHomeBinding binding){
        mRecyclerView = binding.expensesRecyclerView;
        mExpenseAdapter = new ExpensesDayAdapter(expenseArrayList, getContext());
        mRecyclerView.setAdapter(mExpenseAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        fStore = FirebaseFirestore.getInstance();
        fAuth = FirebaseAuth.getInstance();
        expenseArrayList = new ArrayList<>();

        if(fAuth.getCurrentUser() != null)
            loadData(binding);

        addExpenseFab = binding.addExpenseFab;
        addExpenseFab.setOnClickListener(view -> {
            Intent intent = new Intent(getActivity(), AddExpenseActivity.class);
            startActivity(intent);
        });

        Log.d("HomeFragment", "onCreateView");

        return root;
    }

    private void loadData(FragmentHomeBinding binding){
        fStore.collection("Data").document(fAuth.getUid()).collection("Expenses").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                for(DocumentSnapshot ds:task.getResult()){
                    Expense expense = new Expense(
                            ds.getString("expenseId"),
                            Math.toIntExact(ds.getLong("cateId")),
                            ds.getString("description"),
                            Math.toIntExact(ds.getLong("amount")),
                            ds.getDate("createAt")
                    );
                    expenseArrayList.add(expense);
                };
                expenseArrayList.sort((Expense ex1, Expense ex2) -> ex2.getCreateAt().compareTo(ex1.getCreateAt()));
                setStats(binding);
                bindingRecyclerView(binding);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}