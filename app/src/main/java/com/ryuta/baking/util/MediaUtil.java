package com.ryuta.baking.util;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;

public class MediaUtil {

    private static final String TAG = MediaUtil.class.getName();

    // Hides ImageView if error loading image
    public static void loadImage(final ImageView imageView, String url) {
        Picasso.get()
                .load(url)
                .fit()
                .into(imageView, new com.squareup.picasso.Callback() {
                    @Override
                    public void onSuccess() { }

                    @Override
                    public void onError(Exception e) {
                        Log.e(TAG, "error loading image", e);
                        imageView.setVisibility(View.GONE);
                    }
                });
    }

    // Use error drawable if error loading image
    public static void loadImage(final ImageView imageView, String url, int errorDrawable) {
        Picasso picasso = Picasso.get();
        RequestCreator creator = url.isEmpty() ? picasso.load(errorDrawable) : picasso.load(url);
       creator.fit()
               .error(errorDrawable)
               .into(imageView);
    }

    public static ExoPlayer loadVideo(Context context, PlayerView playerView, String url, Boolean autoPlay) {
        ExoPlayer player = ExoPlayerFactory.newSimpleInstance(context, new DefaultTrackSelector());

        MediaSource mediaSource = new ProgressiveMediaSource.Factory(
                new DefaultDataSourceFactory(context, "baking")
        ).createMediaSource(Uri.parse(url));

        player.prepare(mediaSource);
        player.setPlayWhenReady(autoPlay);

        playerView.setPlayer(player);
        return player;
    }
}
