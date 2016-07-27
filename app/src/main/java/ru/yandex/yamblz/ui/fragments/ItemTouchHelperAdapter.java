package ru.yandex.yamblz.ui.fragments;

/**
 * Created by Austry on 26/07/16.
 */
public interface ItemTouchHelperAdapter {
    void onItemMove(int from, int to);
    void onItemDelete(int position);
}
