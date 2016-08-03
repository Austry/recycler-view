package ru.yandex.yamblz.ui.fragments;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;

public class CustomItemTouchHelperCallback extends ItemTouchHelper.Callback {

    private static final String TAG = "CustomItemCallback";
    private ItemTouchHelperAdapter adapter;
    private LastMovedDecorator lastMovedDecorator;

    private Paint paint;

    public CustomItemTouchHelperCallback(ItemTouchHelperAdapter adapter,@Nullable LastMovedDecorator lastMovedDecorator) {
        this.adapter = adapter;
        this.paint = new Paint();
        paint.setColor(Color.RED);
        this.lastMovedDecorator = lastMovedDecorator;
    }

    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN | ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
        int swipeFlags = ItemTouchHelper.RIGHT;

        return makeMovementFlags(dragFlags, swipeFlags);
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        int firstPosition = viewHolder.getAdapterPosition();
        int secondPosition = target.getAdapterPosition();
        adapter.onItemMove(firstPosition, secondPosition);
        if(lastMovedDecorator != null){
            lastMovedDecorator.setLastMoved(firstPosition, secondPosition);
        }

        return true;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        adapter.onItemDelete(viewHolder.getAdapterPosition());
    }

    @Override
    public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder,
                            float dX, float dY, int actionState, boolean isCurrentlyActive) {
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
            int width = c.getWidth();
            paint.setAlpha((int) ((dX * width) / 255));
            c.drawRect(0, 0, c.getWidth(), c.getHeight(), paint);
        }
    }
}
