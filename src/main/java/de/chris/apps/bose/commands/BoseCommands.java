package de.chris.apps.bose.commands;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

/**
 * In this class all available CLI commands are implemented.
 *
 * @author glaesec
 */
@ShellComponent
public class BoseCommands {
    private final static Logger LOG = LoggerFactory.getLogger(BoseCommands.class);

    public BoseCommands() {
    }

    @ShellMethod(value = "Wake up music. Starts playing preset 1 in low volume",
            key = "wakeUpMusic")
    public void wakeUpMusic() {
        BoseActions.wakeUpScene(1, 10);
    }

    @ShellMethod(value = "Preset 2 with volume 20", key = "partyMusic")
    public void partyMusic() {
        new BoseActions().switchPresetWhenTrackFinished(2, 25);
    }

    @ShellMethod(value = "Set the volume", key = "setVolume")
    public void setVolume(@ShellOption Integer volume) {
        BoseActions.postVolume(volume);

        LOG.info(String.format("Volume is set to %d.", volume));
    }

    @ShellMethod(value = "Play a preset", key = {"playPreset"})
    public void playPreset(@ShellOption Integer preset) {
        BoseActions.choosePreset(preset);

        LOG.info(String.format("Preset %d now playing.", preset));
    }

    @ShellMethod(key = "getPower", value = "Prints the current on/off state")
    public void getPowerState() {
        LOG.info(String.format("Bose is %s.", BoseActions.isBoseOn() ? "on" : "off"));
    }

    @ShellMethod(value = "Switches the current power state",
            key = {"switchpower", "switchPower"})
    public void switchPower() {
        BoseActions.switchPower();
        LOG.info(String.format("Bose is now %s.", BoseActions.isBoseOn() ? "on" : "off"));
    }

    @ShellMethod(key = {"shutdownwhentrackfinished", "shutDownWhenTrackFinished"},
        value = "Schedule shutdown after current track")
    public void shutDownWhenTrackFinished() {
        new BoseActions().shutDownWhenTrackFinished();
    }

    @ShellMethod(key = {"listSources", "listsources"},
        value = "Lists all available sources. E.g. Bluetooth")
    public void listSources() {
        LOG.info(BoseActions.getSources());
    }

    @ShellMethod(key = {"startSpotify", "startspotify"},
        value = "Starts playing spotify")
    public void startSpotify() {
        BoseActions.chooseSpotify();
    }

    @ShellMethod(key = {"chooseBluetooth", "choosebluetooth"},
        value = "Bluetooth will be current source")
    public void chooseBluetooth() {
        BoseActions.chooseSource("BLUETOOTH");

        LOG.info("BLUETOOTH is now current source.");
    }

    @ShellMethod(key = {"isBoseOn", "isboseon"},
     value = "Prints if bose is on")
    public void isBoseOn() {
        LOG.info("Bose is " + (BoseActions.isBoseOn() ? "on" : "not on"));
    }

}
