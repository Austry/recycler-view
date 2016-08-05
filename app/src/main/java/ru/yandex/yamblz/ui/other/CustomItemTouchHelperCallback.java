package ru.yandex.yamblz.ui.other;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.View;

import ru.yandex.yamblz.ui.fragments.LastMovedDecorator;
import ru.yandex.yamblz.ui.other.ItemTouchHelperAdapter;

public class CustomItemTouchHelperCallback extends ItemTouchHelper.Callback {

    private static final String TAG = "CustomItemCallback";
    private ItemTouchHelperAdapter adapter;
    private LastMovedDecorator lastMovedDecorator;

    private Paint paint;

    public CustomItemTouchHelperCallback(ItemTouchHelperAdapter adapter, @Nullable LastMovedDecorator lastMovedDecorator) {
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
        if (lastMovedDecorator != null) {
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
            int width = (int) (c.getWidth() * getSwipeThreshold(viewHolder));
            if(dX < width){
                paint.setAlpha((int) ((dX * 255) / width));
            }else{
                paint.setAlpha(255);
            }
            View bounds = viewHolder.itemView;
            c.drawRect(bounds.getLeft(), bounds.getTop(), bounds.getRight(), bounds.getBottom(), paint);
        }
        if(actionState == ItemTouchHelper.ACTION_STATE_IDLE){
            Log.d(TAG, "onChildDraw: blaaa");
        }
    }
}
