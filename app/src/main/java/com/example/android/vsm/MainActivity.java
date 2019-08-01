package com.example.android.vsm;

import android.app.Activity;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import static android.R.id.list;
import static com.example.android.vsm.R.layout.activity_main;

public class MainActivity extends AppCompatActivity {
    String items[]=new String[]{"Apple","Banana","Orange","Grapes","Pear","Rice","Beans","Carrot","Potato","Wheat","Milk","Bread","Cereal","Honey","Tea","Coffee"};
    ArrayList<String> mylist = new ArrayList<String>();
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(activity_main);

        ListView listView=(ListView) findViewById(R.id.listView);
        adapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,items);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String selectedItem = (String) parent.getItemAtPosition(position);
                if(!mylist.contains(selectedItem))
                {
                    mylist.add(selectedItem);
                    Toast.makeText(MainActivity.this,selectedItem,Toast.LENGTH_SHORT).show();
                }

            }
        });

        FloatingActionButton fb=(FloatingActionButton) findViewById(R.id.floatingActionButton);
        fb.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this, Main2Activity.class);
                intent.putExtra("list", mylist);
                startActivity(intent);
            }
        });
    }
}
