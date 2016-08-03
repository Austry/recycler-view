package ru.yandex.yamblz.ui.fragments;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class LastTwoElementsMovedDecorator extends RecyclerView.ItemDecoration implements LastMovedDecorator {


    public static final int DECORATION_SQUARE_SIDE = 40;
    private int firstMovedIndex = -1;
    private int secondMovedIndex = -1;
    private Paint paint;

    public LastTwoElementsMovedDecorator() {
        paint = new Paint();
        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.FILL);
        paint.setStrokeWidth(20);
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

    private void decorate(Canvas canvas, View toDecorate) {
        int left = toDecorate.getLeft();
        int top = toDecorate.getTop();
        canvas.drawRect(left, top, left + DECORATION_SQUARE_SIDE, top + DECORATION_SQUARE_SIDE, paint);
    }

    @Override
    public void setLastMoved(int first, int second) {
        firstMovedIndex = first;
        secondMovedIndex = second;
    }
}
