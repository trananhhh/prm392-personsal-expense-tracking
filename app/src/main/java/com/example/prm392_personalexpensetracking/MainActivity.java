package com.example.prm392_personalexpensetracking;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.prm392_personalexpensetracking.model.Category;
import com.example.prm392_personalexpensetracking.model.Expense;
import com.example.prm392_personalexpensetracking.model.ExpenseType;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.example.prm392_personalexpensetracking.databinding.ActivityMainBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    static FirebaseAuth fAuth;
    public static String intToMoneyFormat(int amount){
        return (String.format("%,d", amount) + " đ");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());

        fAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = fAuth.getCurrentUser();

        if(currentUser == null) {
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
        }
        setContentView(binding.getRoot());

        ExpenseType.initExpenseType();
        Category.initCategory();

        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_report, R.id.navigation_settings)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
    }

    public static String getMonthTitle(Calendar cur){
        return new SimpleDateFormat("MMM, yyyy").format(cur.getTime());
    }


//    public static void fetchDataFirebase(){
//        totalBalance = 0;
//        fStore.collection("Data").document(fAuth.getUid()).collection("Expenses").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                allExpenseList.clear();
//                for(DocumentSnapshot ds:task.getResult()){
//                    totalBalance += Math.toIntExact(ds.getLong("amount")) * (Category.getCategoryById(Math.toIntExact(ds.getLong("cateId"))).getType() == 1 ? -1 : 1);
//                    Expense expense = new Expense(
//                            ds.getString("expenseId"),
//                            Math.toIntExact(ds.getLong("cateId")),
//                            ds.getString("description"),
//                            Math.toIntExact(ds.getLong("amount")),
//                            ds.getDate("createAt")
//                    );
//                    allExpenseList.add(expense);
//                };
//                allExpenseList.sort((Expense ex1, Expense ex2) -> ex2.getCreateAt().compareTo(ex1.getCreateAt()));
//            }
//        });
//    }
}