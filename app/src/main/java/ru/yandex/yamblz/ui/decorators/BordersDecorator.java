package ru.yandex.yamblz.ui.decorators;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.view.View;


public class BordersDecorator extends RecyclerView.ItemDecoration {

    private final static int PAINT_STROKE_WIDTH = 30;
    private final static int PAINT_STROKE_WIDTH_HALF = PAINT_STROKE_WIDTH / 2;
    private final static String LOG_TAG = "BordersDecorator";

    private Paint paint = new Paint();
    private boolean isDecorationOn = false;


    public BordersDecorator() {
        paint.setColor(Color.GRAY);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(PAINT_STROKE_WIDTH);
    }

    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDrawOver(c, parent, state);
        if (!isDecorationOn) {
            return;
        }
        int viewsToDecorateCount = parent.getChildCount();
        for (int i = 0; i < viewsToDecorateCount; i++) {
            View currentView = parent.getChildAt(i);
            if (needToDecorate(parent.getChildAdapterPosition(currentView))) {
                decorate(c, currentView);
            }
        }
    }

    public void toggleDecoration() {
        isDecorationOn = !isDecorationOn;
    }

    private boolean needToDecorate(int childAdapterPosition) {
        return childAdapterPosition % 2 == 0;
    }

    private void decorate(Canvas canvas, View view) {
        float dX = view.getTranslationX();
        float dY = view.getTranslationY();
        canvas.drawRect(view.getLeft() + dX + PAINT_STROKE_WIDTH_HALF, view.getTop() + dY + PAINT_STROKE_WIDTH_HALF,
                view.getRight() + dX - PAINT_STROKE_WIDTH_HALF, view.getBottom() + dY - PAINT_STROKE_WIDTH_HALF, paint);
    }


}
