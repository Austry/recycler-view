package ru.yandex.yamblz.ui.fragments;

import android.graphics.Canvas;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import butterknife.BindView;
import ru.yandex.yamblz.R;

public class ContentFragment extends BaseFragment {

    @BindView(R.id.rv)
    RecyclerView rv;

    private int rvColumnsCount = 1;
    private GridLayoutManager gridLayoutManager;

    @NonNull
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        gridLayoutManager = new GridLayoutManager(getContext(), 1);

        return inflater.inflate(R.layout.fragment_content, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ContentAdapter adapter = new ContentAdapter();
        adapter.setHasStableIds(true);
        LastTwoElementsMovedDecorator lastTwoElementsMovedDecorator = new LastTwoElementsMovedDecorator();
        ItemTouchHelper helper = new ItemTouchHelper(new CustomItemTouchHelperCallback(adapter, lastTwoElementsMovedDecorator));

        rv.setLayoutManager(gridLayoutManager);
        rv.setAdapter(adapter);

        rv.addItemDecoration(new BordersDecorator());
        rv.addItemDecoration(lastTwoElementsMovedDecorator);

        helper.attachToRecyclerView(rv);

    }

    public void addRecyclerColumn() {
        setRvColumnsCount(++rvColumnsCount);
    }

    public void removeRecyclerColumn() {
        if (rvColumnsCount > 1) {
            setRvColumnsCount(--rvColumnsCount);
        }
    }

    private void setRvColumnsCount(int columnsCount){
        gridLayoutManager.setSpanCount(columnsCount);
        gridLayoutManager.requestLayout();
        rv.getAdapter().notifyItemRangeChanged(gridLayoutManager.findFirstVisibleItemPosition(), 0);
    }
}
