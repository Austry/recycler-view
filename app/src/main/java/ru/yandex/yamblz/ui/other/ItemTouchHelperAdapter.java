package ru.yandex.yamblz.ui.other;

/**
 * Created by Austry on 26/07/16.
 */
public interface ItemTouchHelperAdapter {
    void onItemMove(int from, int to);
    void onItemDelete(int position);
}
