package de.chris.apps.bose.commands;

import de.chris.apps.bose.restapi.*;
import de.chris.apps.bose.restapi.websocket.BoseWebSocket;
import de.chris.apps.bose.xml.KeyValues;
import de.chris.apps.bose.xml.NowPlaying;
import de.chris.apps.bose.xml.TrackUpdateCallback;
import de.chris.apps.bose.xml.util.XmlMarshaller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * Communicates with Bose Soundtouch via REST
 *
 * @author glaesec
 */
public class BoseActions {
    private final static Logger LOG = LoggerFactory.getLogger(BoseActions.class);

    private final static String SPOTIFY_SOURCE = "SPOTIFY";
    private static final String REST_API_SOURCES = "sources";
    private static final String REST_API_NOW_PLAYING = "now_playing";

    private BlockingQueue<Object> waitForTrackUpdate = new ArrayBlockingQueue<>(1);

    private TrackUpdateCallback trackUpdateCallback = new TrackUpdateCallback() {
        @Override
        public void newTrack(de.chris.apps.bose.xml.Updates.NowPlayingUpdated.NowPlaying nowPlaying) {
            LOG.info("New track " + nowPlaying.getTrack());
            waitForTrackUpdate.add(new Object());
        }
    };

    public static void postVolume(int volume) {
        if (volume < 0 || volume > 100) {
            throw new IllegalArgumentException("Volume out of range." + "Volume range is 0..100.");
        }

        LOG.info("Setting volume to {}", volume);
        new PostVolume(volume).postCommand();
    }

    public static void choosePreset(int preset) {
        if (preset <= 0 || preset > 6) {
            LOG.error("Only preset from 1 to 6 allowed.");
            return;
        }
        LOG.info("Choosing preset {}", preset);
        String key = "PRESET_" + preset;
        KeyValues keyValues = KeyValues.fromValue(key);
        PostKey.releaseKey(keyValues);
    }

    public static void chooseSpotify() {
        chooseSource(SPOTIFY_SOURCE, Rest.getInstance().getConstants().getSpotifiyAccount());
    }

    public static void chooseSource(String source) {
        new PostSelect(source).postCommand();
    }

    public static void chooseSource(String source, String sourceAccount) {
        new PostSelect(source, sourceAccount).postCommand();
    }

    public static void switchPower() {
        PostKey.postKey(KeyValues.POWER);
    }

    public static void switchPower(boolean onOff) {
        if (onOff == isBoseOn()) {
            LOG.info(String.format("Bose is allready %s.", onOff ? "on" : "off"));
            return;
        }

        switchPower();

        LOG.info(String.format("Bose is now %s.", onOff ? "on" : "off"));
    }

    public static String getSources() {
        return Get.get(REST_API_SOURCES);
    }

    public static boolean isBoseOn() {
        NowPlaying nowP = XmlMarshaller.getInstance().unmarshall(NowPlaying.class, Get.get(REST_API_NOW_PLAYING));
        return !nowP.getSource().contains("STANDBY");
    }

    public static void wakeUpScene(int preset, int volume) {
        postVolume(volume);

        switchPower(true);

        chooseSpotify();

        PostKey.pressKey(KeyValues.SHUFFLE_ON);

        choosePreset(preset);

        postVolume(volume);

        PostKey.pressKey(KeyValues.PLAY);
    }

    public void switchPresetWhenTrackFinished(int preset, int volume) {
        if (!isBoseOn()) {
            LOG.info("Canceled, because Bose is off");
            return;
        }

        waitForNewTrack();

        choosePreset(preset);

        postVolume(volume);
    }

    public void shutDownWhenTrackFinished() {
        if (!isBoseOn()) {
            LOG.info("Canceled, because Bose is already off");
            return;
        }

        waitForNewTrack();

        switchPower(false);
    }

    private void waitForNewTrack() {
        LOG.info("Wait a maximum time of 5 minutes till current song ends.");
        try (BoseWebSocket boseWebSocket = new BoseWebSocket(trackUpdateCallback)) {
            if (waitForTrackUpdate.poll(5, TimeUnit.MINUTES) == null) {
                LOG.info("No listener callback received within 5 Minutes. Forwarding.");
            }
        }
        catch (InterruptedException e) {
            LOG.info("Got interrupted. Switch preset anyway");
            Thread.currentThread().interrupt();
        }
        finally {
            waitForTrackUpdate.clear();
        }
    }

}
