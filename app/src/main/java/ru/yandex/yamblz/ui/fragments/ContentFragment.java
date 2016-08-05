package ru.yandex.yamblz.ui.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.BindView;
import ru.yandex.yamblz.R;
import ru.yandex.yamblz.ui.adapters.ContentAdapter;
import ru.yandex.yamblz.ui.decorators.BordersDecorator;
import ru.yandex.yamblz.ui.decorators.LastTwoElementsMovedDecorator;
import ru.yandex.yamblz.ui.other.CustomItemTouchHelperCallback;

public class ContentFragment extends BaseFragment {

    private static final String LOG_TAG = "ContentFragment";
    private static final String COLUMNS_COUNT_ARGUMENT = "columnsCount";

    private int rvColumnsCount = 1;
    private GridLayoutManager gridLayoutManager;
    private BordersDecorator bordersDecorator;

    @BindView(R.id.rv)
    RecyclerView rv;


    @NonNull
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(savedInstanceState != null){
            rvColumnsCount = savedInstanceState.getInt(COLUMNS_COUNT_ARGUMENT);
        }
        bordersDecorator = new BordersDecorator();
        gridLayoutManager = new GridLayoutManager(getContext(), rvColumnsCount);
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
        helper.attachToRecyclerView(rv);

        rv.addItemDecoration(bordersDecorator);
        rv.addItemDecoration(lastTwoElementsMovedDecorator);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putInt(COLUMNS_COUNT_ARGUMENT, rvColumnsCount);
        super.onSaveInstanceState(outState);
    }

    public  void toggleBorders(){
        bordersDecorator.toggleDecoration();
        rv.invalidateItemDecorations();
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
