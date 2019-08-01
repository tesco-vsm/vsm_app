package com.example.android.vsm;

import android.content.Context;
import android.database.DataSetObserver;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import static android.R.id.list;

import static java.security.AccessController.getContext;

public class Main2Activity extends AppCompatActivity {

    TextView mealTotalText;
    ArrayList<Food> orders;
    ArrayList<Food> listViewItems = new ArrayList<Food>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        ArrayList<String> list = (ArrayList<String>) getIntent().getSerializableExtra("list");

        ListView storedOrders = (ListView)findViewById(R.id.selected_food_list);

        orders = getListItemData();
        OrderAdapter adapter = new OrderAdapter(this, orders);

        storedOrders.setAdapter(adapter);
    }
    public class Food implements Serializable {

        private  String mName;
        private int mQuantity;

        public void setmQuantity(int mQuantity) {
            this.mQuantity = mQuantity;
        }

        public Food(){}

        public Food(String mName) {
            this.mName = mName;
            this.mQuantity = 1;
        }

        public String getmName() {
            return mName;
        }

        public int getmQuantity(){
            return mQuantity;
        }

        public void addToQuantity(){
            this.mQuantity += 1;
        }

        public void removeFromQuantity(){
            if(this.mQuantity > 1){
                this.mQuantity -= 1;
            }
        }

    }

    private ArrayList<Food> getListItemData(){
        ArrayList<String> list = (ArrayList<String>) getIntent().getSerializableExtra("list");
        Iterator it = list.iterator();
        while (it.hasNext())
        {
            String s= (String) it.next();
            listViewItems.add(new Food(s));
        }

        return listViewItems;
    }

    public class OrderAdapter extends ArrayAdapter<Food>{
        private List<Food> list;
        private Context context;

        TextView currentFoodName,
                quantityText,
                addMeal,
                subtractMeal,
                removeMeal;

        public OrderAdapter(Context context, List<Food> myOrders) {
            super(context, 0, myOrders);
            this.list = myOrders;
            this.context = context;
        }

        public View getView(final int position, View convertView, ViewGroup parent){
            View listItemView = convertView;
            if(listItemView == null){
                listItemView = LayoutInflater.from(getContext()).inflate(
                        R.layout.list_item,parent,false
                );
            }

            final Food currentFood = getItem(position);

            currentFoodName = (TextView)listItemView.findViewById(R.id.selected_food_name);
            subtractMeal = (TextView)listItemView.findViewById(R.id.minus_meal);
            quantityText = (TextView)listItemView.findViewById(R.id.quantity);
            addMeal = (TextView)listItemView.findViewById(R.id.plus_meal);
            removeMeal = (TextView)listItemView.findViewById(R.id.delete_item);

            //Set the text of the meal, amount and quantity
            currentFoodName.setText(currentFood.getmName());
            quantityText.setText("x "+ currentFood.getmQuantity());

            //OnClick listeners for all the buttons on the ListView Item
            addMeal.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    currentFood.addToQuantity();
                    quantityText.setText("x "+ currentFood.getmQuantity());
                    notifyDataSetChanged();
                }
            });

            subtractMeal.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    currentFood.removeFromQuantity();
                    quantityText.setText("x "+currentFood.getmQuantity());
                    notifyDataSetChanged();
                }
            });

            removeMeal.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    list.remove(position);
                    notifyDataSetChanged();
                }
            });

            return listItemView;
        }

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("list", listViewItems);
    }

}
