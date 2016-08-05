package ru.yandex.yamblz.ui.decorators;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import ru.yandex.yamblz.ui.fragments.LastMovedDecorator;

public class LastTwoElementsMovedDecorator extends RecyclerView.ItemDecoration implements LastMovedDecorator {

    private static final int DECORATION_CIRCLE_RADIUS = 40;
    private static final int STROKE_WIDTH = 20;
    private static final String TAG = "LastTwoDecorator";
    private int firstMovedIndex = Integer.MIN_VALUE;
    private int secondMovedIndex = Integer.MIN_VALUE;
    private Paint paint;

    public LastTwoElementsMovedDecorator() {
        paint = new Paint();
        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.FILL);
        paint.setStrokeWidth(STROKE_WIDTH);
    }

    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDrawOver(c, parent, state);
        for (int i = 0; i < parent.getChildCount(); i++) {
            View child = parent.getChildAt(i);
            int adapterPosition = parent.getChildAdapterPosition(child);
            if (adapterPosition == firstMovedIndex ||
                    adapterPosition == secondMovedIndex) {
                decorate(c, child);
            }
        }
    }

    @Override
    public void setLastMoved(int first, int second) {
        firstMovedIndex = first;
        secondMovedIndex = second;
    }

    private void decorate(Canvas canvas, View toDecorate) {
        float dX = toDecorate.getTranslationX();
        float dY = toDecorate.getTranslationY();
        float centerX = toDecorate.getLeft() + dX + DECORATION_CIRCLE_RADIUS;
        float centerY = toDecorate.getTop() + dY + DECORATION_CIRCLE_RADIUS;

        canvas.drawCircle(centerX, centerY, DECORATION_CIRCLE_RADIUS, paint);
    }

}
