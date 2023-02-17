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
        }
        return R.drawable.groceries;
    }
    public static ArrayList<Category> getCategoryList(){
        return categoriesList;
    }
}
