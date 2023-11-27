package com.example.colortiles;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import com.example.colortitles.R;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    int darkColor;
    int brightColor;
    View[][] tiles = new View[4][4];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        darkColor = Color.DKGRAY;
        brightColor = Color.LTGRAY;

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                String tileID = "t" + i + j;
                @SuppressLint("DiscouragedApi") int resID = getResources().getIdentifier(tileID, "id", getPackageName());
                tiles[i][j] = findViewById(resID);
                setRandomColor(tiles[i][j]);
            }
        }
    }

    private void setRandomColor(View tile) {
        Random random = new Random();
        int color = random.nextBoolean() ? darkColor : brightColor;
        tile.setBackgroundColor(color);
    }

    public void onClick(View v) {
        String tag = v.getTag().toString();
        int x = Character.getNumericValue(tag.charAt(0));
        int y = Character.getNumericValue(tag.charAt(1));

        changeColor(v);

        for (int i = 0; i < 4; i++) {
            changeColor(tiles[x][i]);
            changeColor(tiles[i][y]);
        }

        int res = checkWin();

        if (res != 0) {
            String winningColor = String.format("#%06X", (0xFFFFFF & res));
            if (winningColor.equals("#444444")) {
                Toast.makeText(this, "Победа черных", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Победа белых", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void changeColor(View v) {
        int currentColor = ((ColorDrawable) v.getBackground()).getColor();
        int newColor = (currentColor == darkColor) ? brightColor : darkColor;
        v.setBackgroundColor(newColor);
    }

    private int checkWin() {
        int firstColor = ((ColorDrawable) tiles[0][0].getBackground()).getColor();

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                int currentColor = ((ColorDrawable) tiles[i][j].getBackground()).getColor();
                if (currentColor != firstColor) {
                    return 0;
                }
            }
        }
        return firstColor;
    }
}
