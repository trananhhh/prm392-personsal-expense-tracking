package com.example.prm392_personalexpensetracking.ui.report;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.prm392_personalexpensetracking.MainActivity;
import com.example.prm392_personalexpensetracking.adapter.ExpensesDayAdapter;
import com.example.prm392_personalexpensetracking.adapter.ExpensesReportAdapter;
import com.example.prm392_personalexpensetracking.databinding.FragmentHomeBinding;
import com.example.prm392_personalexpensetracking.databinding.FragmentReportBinding;
import com.example.prm392_personalexpensetracking.model.Category;
import com.example.prm392_personalexpensetracking.model.Expense;
import com.example.prm392_personalexpensetracking.ui.home.HomeFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class ReportFragment extends Fragment {
    private FragmentReportBinding binding;
    private RecyclerView mRecyclerView;
    private ExpensesReportAdapter mExpenseAdapter;
    private ImageView prevMonthBtn, nextMonthBtn;
    private TextView expenseStat, balanceStat, incomeStat, monthTitle;
    private Calendar currentMonth;
    private int totalExpenses = 0, currentBalance = 0, totalIncome = 0;
    FirebaseFirestore fStore;
    FirebaseAuth fAuth;
    private ArrayList<Expense> expenseArrayList = new ArrayList<>();

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentReportBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        fStore = FirebaseFirestore.getInstance();
        fAuth = FirebaseAuth.getInstance();

        currentMonth = Calendar.getInstance();

        monthTitle = binding.monthTitle;
        monthTitle.setText(MainActivity.getMonthTitle(currentMonth));

        prevMonthBtn = binding.prevMonthBtn;
        nextMonthBtn = binding.nextMonthBtn;

        prevMonthBtn.setOnClickListener(view -> {
            currentMonth.add(Calendar.MONTH, -1);
            monthTitle.setText(MainActivity.getMonthTitle(currentMonth));
            loadData(binding);
        });

        nextMonthBtn.setOnClickListener(view -> {
            currentMonth.add(Calendar.MONTH, 1);
            monthTitle.setText(MainActivity.getMonthTitle(currentMonth));
            loadData(binding);
        });

        return root;
    }

    private void bindingRecyclerView(FragmentReportBinding binding){
        mRecyclerView = binding.categoriesRecyclerView;
        mExpenseAdapter = new ExpensesReportAdapter(expenseArrayList, getContext());
        mRecyclerView.setAdapter(mExpenseAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }
    private void loadData(FragmentReportBinding binding){
        Calendar tmpCal = Calendar.getInstance();
        fStore.collection("Data").document(fAuth.getUid()).collection("Expenses").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                expenseArrayList.clear();
                for(DocumentSnapshot ds:task.getResult()){
                    tmpCal.setTime(ds.getDate("createAt"));
                    if (tmpCal.get(Calendar.YEAR) == currentMonth.get(Calendar.YEAR) && tmpCal.get(Calendar.MONTH) == currentMonth.get(Calendar.MONTH)){
                        Expense expense = new Expense(
                                ds.getString("expenseId"),
                                Math.toIntExact(ds.getLong("cateId")),
                                ds.getString("description"),
                                Math.toIntExact(ds.getLong("amount")),
                                ds.getDate("createAt")
                        );
                        expenseArrayList.add(expense);
                    }
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

    private void setStats(FragmentReportBinding binding){
        totalExpenses = 0;
        totalIncome = 0;

//        expenseStat = binding.expenseStat;
//        balanceStat = binding.balanceStat;
//        incomeStat = binding.incomeStat;

        expenseArrayList.forEach((item) -> {
            int catType = Category.getCategoryById(item.getCateId()).getType();
            if(catType == 1)
                totalExpenses += item.getAmount();
            if(catType == 2)
                totalIncome += item.getAmount();
        });

        currentBalance = totalIncome - totalExpenses;

//        expenseStat.setText( MainActivity.intToMoneyFormat(totalExpenses));
//        balanceStat.setText( MainActivity.intToMoneyFormat(currentBalance));
//        incomeStat.setText( MainActivity.intToMoneyFormat(totalIncome));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}