package ru.yandex.yamblz.ui.activities;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.view.Menu;
import android.view.MenuItem;

import javax.inject.Inject;
import javax.inject.Named;

import ru.yandex.yamblz.App;
import ru.yandex.yamblz.R;
import ru.yandex.yamblz.developer_settings.DeveloperSettingsModule;
import ru.yandex.yamblz.ui.fragments.ContentFragment;
import ru.yandex.yamblz.ui.other.ViewModifier;

public class MainActivity extends BaseActivity {

    @Inject @Named(DeveloperSettingsModule.MAIN_ACTIVITY_VIEW_MODIFIER)
    ViewModifier viewModifier;

    private FragmentManager fm;

    @SuppressLint("InflateParams") // It's okay in our case.
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        App.get(this).applicationComponent().inject(this);

        fm = getSupportFragmentManager();
        setContentView(viewModifier.modify(getLayoutInflater().inflate(R.layout.activity_main, null)));



        if (savedInstanceState == null) {
            fm.beginTransaction()
                    .replace(R.id.main_frame_layout, new ContentFragment())
                    .commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        ContentFragment fragment = (ContentFragment) fm.findFragmentById(R.id.main_frame_layout);
        int itemId = item.getItemId();
        switch (itemId) {
            case R.id.menuItemAddColumn:
                fragment.addRecyclerColumn();
                break;
            case R.id.menuItemRemoveColumn:
                fragment.removeRecyclerColumn();
                break;
            case R.id.menuItemToggleBorders:
                fragment.toggleBorders();
                break;
            default:
                throw new IllegalArgumentException("Not implemented "+ itemId + " menu button!");
        }
        return super.onOptionsItemSelected(item);
    }
}
