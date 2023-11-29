package com.example.colortiles;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import java.util.Random;

public class ColorTileView extends View {
    private Paint paint;
    private int backgroundColor;

    public ColorTileView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        paint = new Paint();
        paint.setAntiAlias(true);

        Random random = new Random();
        backgroundColor = (random.nextBoolean()) ? Color.BLUE : Color.CYAN;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        paint.setColor(backgroundColor);
        canvas.drawRect(0, 0, getWidth(), getHeight(), paint);
    }

    public void toggleColor() {
        backgroundColor = (backgroundColor == Color.CYAN) ? Color.BLUE : Color.CYAN;;
        invalidate();
    }

    public int getBackgroundColor() {
        return backgroundColor;
    }
}
