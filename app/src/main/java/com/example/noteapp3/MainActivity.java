package com.example.noteapp3;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ListView listView;
    ArrayList<String> items;
    ArrayAdapter<String> adapter;

    EditText input;
    ImageView enter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.listView);

        input = findViewById(R.id.input);
        enter = findViewById(R.id.add);

        items = new ArrayList<>();
        items.add("Apples");
        items.add("Banana");
        items.add("Orange");
        items.add("Strawberry");
        items.add("Kiwi");
        items.add("Mango");

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) { //i = index
                String name = items.get(i);
                Toast.makeText(MainActivity.this, name, Toast.LENGTH_SHORT).show(); //Toast.LENGTH_SHORT = 2 sec
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(MainActivity.this, "Removed: " + items.get(i), Toast.LENGTH_LONG).show(); //display item at index i
                items.remove(i); //remove item at index i
                adapter.notifyDataSetChanged(); //refresh list
                return true;
            }
        });

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, items);
        listView.setAdapter(adapter);

        enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String text = input.getText().toString();//get text from input
                if (!text.isEmpty()) { //if text is not empty
                    items.add(text); //add text to items
                    adapter.notifyDataSetChanged(); //refresh list
                    input.setText("");// clear input
                    Toast.makeText(MainActivity.this, "Added: " + text, Toast.LENGTH_SHORT).show();
                } else { //if text is empty
                    Toast.makeText(MainActivity.this, "Enter some text", Toast.LENGTH_SHORT).show();
                }
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(MainActivity.this, "Removed: " + items.get(i), Toast.LENGTH_SHORT).show(); //display item at index i
                items.remove(i); //remove item at index i
                adapter.notifyDataSetChanged(); //refresh list
                return true;
            }
        });

    }
}