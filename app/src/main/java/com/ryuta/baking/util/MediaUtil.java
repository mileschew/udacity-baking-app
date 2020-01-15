package com.ryuta.baking.util;

import android.content.Context;
import android.net.Uri;
import android.widget.ImageView;

import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.ryuta.baking.R;
import com.squareup.picasso.Picasso;

public class MediaUtil {

    public static void loadImage(ImageView imageView, String url) {
        Picasso.get()
                .load(url)
                .fit()
                .error(R.drawable.exo_controls_shuffle_off) // TODO replace with error image
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
