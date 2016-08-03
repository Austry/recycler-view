package ru.yandex.yamblz.ui.fragments;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.view.View;


public class BordersDecorator extends RecyclerView.ItemDecoration {

    private final static int PAINT_STROKE_WIDTH = 30;
    private final static int PAINT_STROKE_WIDTH_HALF = PAINT_STROKE_WIDTH / 2;
    private Paint paint = new Paint();


    public BordersDecorator() {
        paint.setColor(Color.GRAY);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(PAINT_STROKE_WIDTH);
    }

    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDrawOver(c, parent, state);
        int viewsToDecorateCount = parent.getChildCount();
        for (int i = 0; i < viewsToDecorateCount; i++) {
            View currentView = parent.getChildAt(i);
            if (needToDecorate(parent.getChildAdapterPosition(currentView))) {
                decorate(c, currentView);
            }
        }
    }

    private boolean needToDecorate(int childAdapterPosition) {
        return childAdapterPosition % 2 == 0;
    }

    private void decorate(Canvas canvas, View view) {
        canvas.drawRect(view.getLeft() + PAINT_STROKE_WIDTH_HALF, view.getTop() + PAINT_STROKE_WIDTH_HALF,
                view.getRight() - PAINT_STROKE_WIDTH_HALF, view.getBottom() - PAINT_STROKE_WIDTH_HALF, paint);
    }
}
