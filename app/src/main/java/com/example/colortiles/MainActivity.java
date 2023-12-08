package com.example.colortiles;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import com.example.colortitles.R;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    UserListAdapter adapter;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.list);
        Button sortByNameButton = findViewById(R.id.sortByNameButton);
        Button sortByPhoneButton = findViewById(R.id.sortByPhoneButton);
        Button sortBySexButton = findViewById(R.id.sortBySexButton);

        ArrayList<User> users = loadUsersFromJson();

        // TODO: реализовать загрузку данных из JSON-файла
        // который загрузить в папку assets

//        for (int i = 0; i < 10; i++) {
//            users.add(new User("Petya", "123", Sex.MAN));
//            users.add(new User("Vasya", "234", Sex.MAN));
//            users.add(new User("Valya", "456", Sex.WOMAN));
//            users.add(new User("UFO", "@@@", Sex.UNKNOWN));
//        }

        adapter = new UserListAdapter(this, users);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener((parent, view, position, id) -> {
            adapter.setSelectedPosition(position);
            adapter.notifyDataSetChanged();
        });

        sortByNameButton.setOnClickListener(v -> adapter.sortByName());
        sortByPhoneButton.setOnClickListener(v -> adapter.sortByPhoneNumber());
        sortBySexButton.setOnClickListener(v -> adapter.sortBySex());

        Button addUserButton = findViewById(R.id.addUserButton);
        addUserButton.setOnClickListener(v -> showAddUserDialog());
    }

    private ArrayList<User> loadUsersFromJson() {
        ArrayList<User> users = new ArrayList<>();

        try {
            InputStream inputStream = getAssets().open("users.json");
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();

            String json = new String(buffer, "UTF-8");
            JSONArray jsonArray = new JSONArray(json);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String name = jsonObject.getString("name");
                String phoneNumber = jsonObject.getString("phoneNumber");
                String sexStr = jsonObject.getString("sex");
                Sex sex = Sex.valueOf(sexStr); // Преобразование строки в enum

                users.add(new User(name, phoneNumber, sex));
            }
        } catch (JSONException | IOException e) {
            e.printStackTrace();
        }

        return users;
    }

    private void showAddUserDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Добавление нового пользователя");

        View dialogView = getLayoutInflater().inflate(R.layout.dialog_add_user, null);
        builder.setView(dialogView);

        builder.setPositiveButton("Добавить", (dialog, which) -> {
            EditText nameEditText = dialogView.findViewById(R.id.editTextName);
            EditText phoneEditText = dialogView.findViewById(R.id.editTextPhone);

            RadioGroup radioGroupSex = dialogView.findViewById(R.id.radioGroupSex);

            String name = nameEditText.getText().toString();
            String phoneNumber = phoneEditText.getText().toString();

            Sex sex;
            int selectedId = radioGroupSex.getCheckedRadioButtonId();
            if (selectedId == R.id.radioButtonMale) {
                sex = Sex.MAN;
                //avatarImageView.setImageResource(R.drawable.man1);
            } else if (selectedId == R.id.radioButtonFemale) {
                sex = Sex.WOMAN;
                //avatarImageView.setImageResource(R.drawable.woman1);
            } else {
                sex = Sex.UNKNOWN;
                //avatarImageView.setImageResource(R.drawable.anon);
            }

            User newUser = new User(name, phoneNumber, sex);
            adapter.addUser(newUser);
            adapter.notifyDataSetChanged();
        });

        builder.setNegativeButton("Отменить", null);

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
