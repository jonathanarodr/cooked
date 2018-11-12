package br.com.jar.cooked.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import br.com.jar.cooked.R;

public class MainActivity extends AppCompatActivity implements android.support.v4.app.FragmentManager.OnBackStackChangedListener {

    private boolean mLayoutTwoPane;

    public boolean isLayoutTwoPane() {
        return mLayoutTwoPane;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setLayoutType();
        buildLayout(savedInstanceState);
    }

    @Override
    public boolean onSupportNavigateUp() {
        getSupportFragmentManager().popBackStack();
        return true;
    }

    @Override
    public void onBackStackChanged() {
        setDisplayUpToolBar();
    }

    public void setDisplayUpToolBar() {
        if (isLayoutTwoPane()) {
            return;
        }

        boolean back = getSupportFragmentManager().getBackStackEntryCount() > 0;
        getSupportActionBar().setDisplayHomeAsUpEnabled(back);
    }

    private void setLayoutType() {
        mLayoutTwoPane = findViewById(R.id.layout_two_pane) != null;
    }

    private void buildLayout(Bundle bundle) {
        if (bundle != null || isLayoutTwoPane()) {
            return;
        }

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frame_recipes, new MainFragment())
                .commit();
    }

    public void addBackStackListener() {
        getSupportFragmentManager().addOnBackStackChangedListener(this);
    }

}
