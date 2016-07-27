package ru.yandex.yamblz.ui.fragments;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.view.View;


public class BordersDecorator extends RecyclerView.ItemDecoration {

    private Paint paint = new Paint();

    public BordersDecorator() {
        paint.setColor(Color.GRAY);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(30);
    }

    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDrawOver(c, parent, state);
        int viewsToDecorate = parent.getChildCount();
        for (int i = 0; i < viewsToDecorate; i++) {
            View currentView = parent.getChildAt(i);
            if(needToDecorate(parent.getChildAdapterPosition(currentView))){
                decorate(c, currentView);
            }
        }
    }

    private boolean needToDecorate(int childAdapterPosition) {
        return childAdapterPosition % 2 == 0;
    }

    private void decorate(Canvas canvas, View view) {
        canvas.drawRect(view.getLeft(), view.getTop(), view.getRight(), view.getBottom(), paint);
    }
}
