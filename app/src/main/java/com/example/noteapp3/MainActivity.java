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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
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
                Toast.makeText(MainActivity.this, name, Toast.LENGTH_SHORT).show();
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(MainActivity.this, "Removed: " + items.get(i), Toast.LENGTH_SHORT).show(); //display item at index i (adica cel pe care am apasat)
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
                    Toast.makeText(MainActivity.this, "Added: " + text, Toast.LENGTH_SHORT).show(); //display added item
                } else { //if text is empty
                    Toast toast = Toast.makeText(MainActivity.this, "Enter something", Toast.LENGTH_SHORT);
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
                        Toast.makeText(MainActivity.this, "Found: " + text, Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(MainActivity.this, "Not found", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast toast = Toast.makeText(MainActivity.this, "Enter something", Toast.LENGTH_SHORT);
                }
                search.setText("");
            }
        });

        loadContent();

    }

    @Override
    protected void onDestroy() {
        File path = getApplicationContext().getFilesDir();
        try {
            FileOutputStream writer = new FileOutputStream(new File(path, "data.txt")); //create file
            writer.write(items.toString().getBytes());  //write items to file
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        super.onDestroy();
    }

    public void loadContent() {
        File path = getApplicationContext().getFilesDir();
        File readFrom = new File(path, "data.txt");
        byte[] content = new byte[(int) readFrom.length()]; //create byte array with size of file length
        try {
            FileInputStream fis = new FileInputStream(readFrom); //reads from here
            fis.read(content); //and writes in content

            String s = new String(content); //convert byte array to string
            s = s.replace("[", ""); //remove [ from string
            s = s.replace("]", ""); //remove ] from string
            String split[] = s.split(", "); //remove ", " from string
            items = new ArrayList<>(Arrays.asList(split)); //convert array to arraylist
            adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, items); //create adapter
            listView.setAdapter(adapter); //set adapter to listview

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

}