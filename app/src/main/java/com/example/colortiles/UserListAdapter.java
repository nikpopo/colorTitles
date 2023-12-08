package com.example.colortiles;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.colortitles.R;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;

public class UserListAdapter extends BaseAdapter {
    Context ctx; ArrayList<User> users;
    // TODO: реализовать сортировку по каждому из полей
    // класса: sex, name, phoneNumber

    public void sortBySex() {
        users.sort(Comparator.comparing(user -> user.sex));
        notifyDataSetChanged();
    }

    public void sortByName() {
        users.sort((user1, user2) -> user1.name.compareToIgnoreCase(user2.name));
        notifyDataSetChanged();
    }

    public void sortByPhoneNumber() {
        users.sort((user1, user2) -> user1.phoneNumber.compareToIgnoreCase(user2.phoneNumber));
        notifyDataSetChanged();
    }

    public UserListAdapter(Context ctx, ArrayList<User> users) {
        this.ctx = ctx;
        this.users = users;
    }

    public void setSelectedPosition(int selectedPosition) {
        this.selectedPosition = selectedPosition;
    }

    public void addUser(User user) {
        users.add(user);
        notifyDataSetChanged();
    }
    @Override
    public int getCount() {
        return users.size();
    }

    @Override
    public Object getItem(int position) {
        return users.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private int selectedPosition = -1;
    @SuppressLint("ViewHolder")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // получаем данные из коллекции
        Date begin = new Date();
        User u = users.get(position);

        // создаём разметку (контейнер)
        convertView = LayoutInflater.from(ctx).
                inflate(R.layout.useritem, parent, false);
        // получаем ссылки на элементы интерфейса
        ImageView ivUserpic = convertView.findViewById(R.id.userpic);
        ivUserpic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setBackgroundColor(Color.RED);
            }
        });
        TextView tvName = convertView.findViewById(R.id.name);
        TextView tvPhone = convertView.findViewById(R.id.phone);

        // задаём содержание
        tvName.setText(u.name);
        tvPhone.setText(u.phoneNumber);
        switch (u.sex) {
            case MAN: ivUserpic.setImageResource(R.drawable.man1); break;
            case WOMAN: ivUserpic.setImageResource(R.drawable.woman1); break;
            case UNKNOWN: ivUserpic.setImageResource(R.drawable.anon); break;
        }
        Date finish = new Date();
        Log.d("mytag", "getView time: "+(finish.getTime()-begin.getTime()));

        if (position == selectedPosition) {
            convertView.setBackgroundColor(Color.rgb(	51, 181, 229));
        } else {
            convertView.setBackgroundColor(Color.TRANSPARENT);
        }

        convertView.setOnClickListener(v -> {
            selectedPosition = position;
            notifyDataSetChanged();
        });

        return convertView;
    }

}