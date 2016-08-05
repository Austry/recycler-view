package ru.yandex.yamblz.ui.adapters;

import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import ru.yandex.yamblz.R;
import ru.yandex.yamblz.ui.other.ItemTouchHelperAdapter;

public class ContentAdapter extends RecyclerView.Adapter<ContentAdapter.ContentHolder> implements ItemTouchHelperAdapter {

    private static final String TAG = "ContentAdapter";
    private final Random rnd = new Random();
    private final static List<Integer> COLORS = new ArrayList<>();

    @Override
    public ContentHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ContentHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.content_item, parent, false));
    }

    @Override
    public void onBindViewHolder(ContentHolder holder, int position) {
        int color = createColorForPosition(position);
        holder.bind(color);
        holder.itemView.setOnClickListener(v -> {
            holder.animatedRebind(createColorForPosition(position), newColorForPosition(position));
        });
    }

    private Integer newColorForPosition(int position) {
        COLORS.set(position, getRandomColor());
        return COLORS.get(position);
    }

    @Override
    public int getItemCount() {
        return Integer.MAX_VALUE;
    }

    private Integer createColorForPosition(int position) {
        int colorsSize = COLORS.size();
        if (position == colorsSize) {
            COLORS.add(getRandomColor());
        } else if (position > colorsSize) {
            for (int i = 0, diff = position - colorsSize; i <= diff; i++) {
                COLORS.add(getRandomColor());
            }
        }
        return COLORS.get(position);
    }

    private Integer getRandomColor() {
        return Color.rgb(rnd.nextInt(255), rnd.nextInt(255), rnd.nextInt(255));
    }

    @Override
    public void onItemMove(int from, int to) {
        if (from > to) {
            for (int i = from; i < to; i++) {
                Collections.swap(COLORS, i, i + 1);
            }
        } else {
            for (int i = from; i > to; i--) {
                Collections.swap(COLORS, i, i - 1);
            }
        }
        notifyItemMoved(from, to);
    }

    @Override
    public void onItemDelete(int position) {
        COLORS.remove(position % COLORS.size());
        notifyItemRemoved(position);
    }

    static class ContentHolder extends RecyclerView.ViewHolder {
        ValueAnimator colorAnimation;

        ContentHolder(View itemView) {
            super(itemView);
        }


        void animatedRebind(Integer oldColor, Integer newColor) {
            int colorFrom = oldColor;
            int colorTo = newColor;
            if (colorAnimation != null && colorAnimation.isRunning()) {
                int animatedColor = (int) colorAnimation.getAnimatedValue();
                colorAnimation.cancel();
                colorAnimation = ValueAnimator.ofObject(new ArgbEvaluator(), animatedColor, colorTo);

            } else {
                colorAnimation = ValueAnimator.ofObject(new ArgbEvaluator(), colorFrom, colorTo);
            }

            colorAnimation.setDuration(1000);

            colorAnimation.addUpdateListener(animator -> {
                itemView.setBackgroundColor((int) animator.getAnimatedValue());

            });
            setColorText(itemView, colorTo);
            colorAnimation.start();

        }

        void bind(Integer color) {
            itemView.setBackgroundColor(color);
            setColorText(itemView, color);
        }

        private void setColorText(View toSet, Integer color) {
            ((TextView) toSet).setText("#".concat(Integer.toHexString(color).substring(2)));
        }
    }
}
