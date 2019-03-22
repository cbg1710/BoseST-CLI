package de.chris.apps.bose.xml;


import de.chris.apps.bose.xml.Updates.NowPlayingUpdated.NowPlaying;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class TrackUpdateCallback implements DeviceUpdateCallback {
    private String playedTrack = "";

    private final static Logger LOG = LoggerFactory.getLogger(TrackUpdateCallback.class);

    public abstract void newTrack(NowPlaying nowPlaying);

    @Override
    public void onMessage(Updates update) {
        if (update == null || update.getNowPlayingUpdated() == null) {
            return;
        }

        handleNowPlayingTrack(update.getNowPlayingUpdated().getNowPlaying());
    }

    private void handleNowPlayingTrack(NowPlaying nowPlaying) {
        String playingTrack = nowPlaying.getTrack();
        if (playedTrack.isEmpty()) {
            playedTrack = playingTrack;
            LOG.trace("Now Playing " + playedTrack);
        }
        else if (!playedTrack.equals(playingTrack)) {
            LOG.trace("Now Playing " + playingTrack);
            playedTrack = playingTrack;
            newTrack(nowPlaying);
        }
    }
}
