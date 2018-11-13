package br.com.jar.cooked.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import br.com.jar.cooked.R;
import br.com.jar.cooked.model.Step;

public class StepActivity extends AppCompatActivity {

    private static final String USER_AGENT = "Cooked";
    private Step mStep;
    private TextView mStepName;
    private TextView mStepDescription;
    private SimpleExoPlayer mExoPlayer;
    private SimpleExoPlayerView mPlayerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        bindViews();
        bindExtra();
        buildPlayer();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        releasePlayer();
    }

    private void releasePlayer() {
        mExoPlayer.stop();
        mExoPlayer.release();
        mExoPlayer = null;
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    private void bindViews() {
        mPlayerView = findViewById(R.id.pv_video);
        mStepName = findViewById(R.id.tv_step_name);
        mStepDescription = findViewById(R.id.tv_step_description);
    }

    private void bindExtra() {
        if (getIntent() == null || !getIntent().hasExtra(Intent.EXTRA_INTENT)) {
            return;
        }

        mStep = getIntent().getParcelableExtra(Intent.EXTRA_INTENT);
        mStepName.setText(mStep.getShortDescription());
        mStepDescription.setText(mStep.getDescription());
    }

    private void buildPlayer() {
        if (TextUtils.isEmpty(mStep.getUrlPlayer())) {
            return;
        }

        mPlayerView.setVisibility(View.VISIBLE);
        initializePlayer(Uri.parse(mStep.getUrlPlayer()));
    }

    private void initializePlayer(Uri mediaUri) {
        if (mExoPlayer != null) {
            return;
        }

        mExoPlayer = ExoPlayerFactory.newSimpleInstance(this, new DefaultTrackSelector(), new DefaultLoadControl());
        mPlayerView.setPlayer(mExoPlayer);
        String userAgent = Util.getUserAgent(this, USER_AGENT);
        MediaSource mediaSource = new ExtractorMediaSource(mediaUri, new DefaultDataSourceFactory(this, userAgent),
                new DefaultExtractorsFactory(), null, null);
        mExoPlayer.prepare(mediaSource);
        mExoPlayer.setPlayWhenReady(true);
    }

}
