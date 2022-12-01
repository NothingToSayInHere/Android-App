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
import java.util.ListIterator;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    ListView listView;
    ArrayList<String> items;
    ArrayAdapter<String> adapter;

    EditText input;
    ImageView enter;
    EditText search;
    ImageView searchButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        input = findViewById(R.id.input);
        enter = findViewById(R.id.add);
        search = findViewById(R.id.searchField);
        searchButton = findViewById(R.id.search_icon);

        listView = findViewById(R.id.listView);
        items = new ArrayList<>();
        items.add("Apples");
        items.add("Banana");
        items.add("Orange");
        items.add("Strawberry");
        items.add("Kiwi");
        items.add("Mango");

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String name = items.get(i);
                makeToast(name);
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                makeToast("Removed: " + items.get(i)); //display item at index i (adica cel pe care am apasat)
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
                    makeToast("Added " + text);// display toast
                } else { //if text is empty
                    makeToast("Please enter something"); //display toast
                }
            }
        });

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String text = search.getText().toString().trim();
                ListIterator<String> iterator = items.listIterator();
                while (iterator.hasNext()) {
                    String item = iterator.next(); //
                    items.set(iterator.previousIndex(), item.toLowerCase(Locale.ROOT)); //set item to lowercase
                }
                if (!text.isEmpty()) {
                    if (items.contains(text)) {
                        makeToast("Found " + text);
                    } else {
                        makeToast("Not found " + text);
                    }
                } else {
                    makeToast("Please enter something");
                }
                search.setText("");
            }
        });

    }

    Toast t;

    public void makeToast(String s) {
        if (t != null) {
            t.cancel();
        }
        t = Toast.makeText(this, s, Toast.LENGTH_SHORT);
        t.show();
    }

}