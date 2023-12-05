package com.example.colortiles;

import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import com.example.colortitles.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    ArrayList<String> names = new ArrayList<>();
    ListView lv;
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        names.add("Никита Попов");

        lv = findViewById(R.id.list);
        adapter = new ArrayAdapter<>(this, R.layout.item, names);
        lv.setAdapter(adapter);

        // Sort the initial list
        Collections.sort(names);

        Button addButton = findViewById(R.id.addButton);

        addButton.setOnClickListener(view -> {
            // Generate a random name and add it to the list
            String[] namesArray = getResources().getStringArray(R.array.names_array);
            String[] surnamesArray = getResources().getStringArray(R.array.surnames_array);
            Random rand = new Random();
            String randomName = namesArray[rand.nextInt(namesArray.length)];
            String randomSurname = surnamesArray[rand.nextInt(surnamesArray.length)];
            names.add(randomName + " " + randomSurname);

            // Notify the adapter of the change
            adapter.notifyDataSetChanged();
        });
    }
}
