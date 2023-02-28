package com.example.prm392_personalexpensetracking.model;

import com.example.prm392_personalexpensetracking.R;

import java.util.ArrayList;

public class Category {
    public static ArrayList<Category> categoriesList = new ArrayList<>();
    private int cate_id;
    private String cate_name;
    private int parent_cate_id;
    private String icon_name;
    private int type;
    /*
        1. Income
        2. Outcome
        3. Loan / Debt
     */

    public Category() {
        initCategory();
    }

    public Category(String cate_name) {
        this.cate_name = cate_name;
    }

    public Category(int cate_id, String cate_name, int parent_cate_id, int type) {
        this.cate_id = cate_id;
        this.cate_name = cate_name;
        this.parent_cate_id = parent_cate_id;
        this.type = type;;
        this.icon_name = icon_name;
//        this.child_cate = child_cate;
    }

    public int getCate_id() {
        return cate_id;
    }

    public void setCate_id(int cate_id) {
        this.cate_id = cate_id;
    }

    public String getCate_name() {
        return cate_name;
    }

    public void setCate_name(String cate_name) {
        this.cate_name = cate_name;
    }

    public int getParent_cate_id() {
        return parent_cate_id;
    }

    public void setParent_cate_id(int parent_cate_id) {
        this.parent_cate_id = parent_cate_id;
    }

    public String getIcon_name() {
        return icon_name;
    }

    public void setIcon_name(String icon_name) {
        this.icon_name = icon_name;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public static Category getCategoryById(int id){
        return categoriesList.stream().filter((item) -> item.getCate_id() == id).findFirst().get();
    }

    public static void initCategory(){
        categoriesList.clear();
        if(categoriesList.isEmpty()){
            categoriesList.add(new Category(1, "Shopping", -1, 2));
            categoriesList.add(new Category(2, "Food & Beverage", -1, 2));
            categoriesList.add(new Category(3, "Transport", -1, 2));
            categoriesList.add(new Category(4, "Bills & Utilities", -1, 2));
            categoriesList.add(new Category(5, "Other", -1, 2));
            categoriesList.add(new Category(6, "Salary", -1, 1));
            categoriesList.add(new Category(7, "Cafe", -1, 2));
            categoriesList.add(new Category(8, "Donate", -1, 2));
            categoriesList.add(new Category(9, "Education", -1, 2));
            categoriesList.add(new Category(10, "Electronics", -1, 2));
            categoriesList.add(new Category(11, "Fuel", -1, 2));
            categoriesList.add(new Category(12, "Gifts", -1, 1));
            categoriesList.add(new Category(13, "Health", -1, 2));
            categoriesList.add(new Category(14, "Maintenance", -1, 2));
            categoriesList.add(new Category(15, "Party", -1, 2));
            categoriesList.add(new Category(16, "Self Development", -1, 2));
            categoriesList.add(new Category(17, "Sport", -1, 2));
            categoriesList.add(new Category(18, "Savings", -1, 2));
        }
    }

    public int getImage(){
        switch (getCate_id()){
            case 1:
                return R.drawable.groceries;
            case 2:
                return R.drawable.restaurant;
            case 3:
                return R.drawable.transportation;
            case 4:
                return R.drawable.laundry;
            case 5:
                return R.drawable.money;
            case 6:
                return R.drawable.institute;
            case 7:
                return R.drawable.cafe;
            case 8:
                return R.drawable.donate;
            case 9:
                return R.drawable.education;
            case 10:
                return R.drawable.electronics;
            case 11:
                return R.drawable.fuel;
            case 12:
                return R.drawable.gifts;
            case 13:
                return R.drawable.health;
            case 14:
                return R.drawable.maintenance;
            case 15:
                return R.drawable.party;
            case 16:
                return R.drawable.self_development;
            case 17:
                return R.drawable.sport;
            case 18:
                return R.drawable.savings;
        }
//        return R.drawable.groceries;
        return 0;
    }
    public static ArrayList<Category> getCategoryList(){
        return categoriesList;
    }
}
