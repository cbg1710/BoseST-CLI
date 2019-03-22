# Bose Soundtouch Control

This project enables you to control your Bose Soundtouch system via an simple Java-Based CLI application. 
The CLI offers some essential commands, like: 
* Switching the Soundtouch on and off
* Controlling the volume
* Choose presets and further more.. 


## Getting Started

### Build

This package can be build with Apache Maven. Since you have already installed maven on your system, 
simple execute following command in the same directory where the pom.xml is located. 

```bash
mvn clean install
```

### Starting the CLI 

To start the CLI navigate to the directory where the artifact was just created. Before you can start the 
CLI a bose.properties have to be created in the same directory from where you want to execute the CLI. 
The bose.properties file must contain following: 

```properties
bose.rest=http://${the_ip_address_from_your_bose}:8090/
bose.websocket=ws://${the_ip_address_from_your_bose}:8080/
bose.protocol=gabbo
spotify.account=${your_spotify_account_name}
```

After successfully created the bose.properties you are able to execute 
the CLI with following command within the terminal.

```bash 
java -jar SoundTouchControl-X.X.jar 
```

Now the CLI, which is based on the Spring CLI, starts. 
Simple type **help** into the console and all available commands will be shown.

## Use Cases

Since it is not very comfortable to control a SoundTouch via an CLI I'm telling you now
how I use this CLI by myself. 

### Alarm clock

In principle I'm running this CLI on my raspberry. There I have created a cron job for 
scheduling specific commands each day. So now I'm able to use my Soundtouch as an alarm clock. 
Each morning my cron job executes following commands: 

* **java -jar SoundTouchControl-X.X.jar wakeUpMusic** --> Turn the system on, sets the volume to 10 and playing preset 1. 
* **java -jar SoundTouchControl-X.X.jar partyMusic** --> Plays preset 2 after current track is finished, sets the volume 
to 20 and starts playing preset 2
* **java -jar SoundTouchControl-X.X.jar shutDownWhenTrackFinished** --> Turn the device off after current track is finished.

### Docker

First install Docker on your system. For creating the image execute following command in the same 
directory where the Dockerfile is located:

``` bash
docker build -f Dockerfile \
	-t soundtouch:latest . \
	--build-arg JAR_FILE=/location/of/SoundTouchControl-X.X.jar \
	--build-arg BOSE=/location/of/bose.properties
```

You can start the CLI with following command:

```bash
docker run --rm \
        --ti -p 5432:5432 \
        --name soundtouch \
        -v /logs:/logs \
        soundtouch:latest
```

### Cron job example 

```
00 6 * * 1-5 docker run --rm -p 5432:5432 -v /logs:/logs --name soundtouch soundtouch:latest wakeUpMusic >/dev/null 2>&1
30 6 * * 1-5 docker run --rm -p 5432:5432 -v /logs:/logs --name soundtouch soundtouch:latest partyMusic >/dev/null 2>&1
00 7 * * 1-5 docker run --rm -p 5432:5432 -v /logs:/logs --name soundtouch soundtouch:latest shutdownwhentrackfinished >/dev/null 2>&1

00 11 * * 6-7 docker run --rm -p 5432:5432 -v /logs:/logs --name soundtouch soundtouch:latest wakeUpMusic >/dev/null 2>&1
30 11 * * 6-7 docker run --rm -p 5432:5432 -v /logs:/logs --name soundtouch soundtouch:latest partyMusic >/dev/null 2>&1
00 12 * * 6-7 docker run --rm -p 5432:5432 -v /logs:/logs --name soundtouch soundtouch:latest shutdownwhentrackfinished >/dev/null 2>&1
```

## License

Copyright &copy; 2019 [cbg1710](https://github.com/cbg1710).
Licensed under the [MIT License](LICENSE).