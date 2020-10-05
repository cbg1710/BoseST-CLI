# Bose Soundtouch Control

This project enables you to control your Bose Soundtouch system via an simple Java-Based RESTful server.

## Getting Started

### Build

This package can be build with Apache Maven. Since you have already installed maven on your system,
simple execute following command in the same directory where the pom.xml is located.

```bash
mvn clean install
```

### Starting the Server

To start the server navigate to the directory where the artifact was just created. Before you can start a bose.properties have to be created in the same directory from where you want to execute the program.
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

Now open http://localhost:8080/swagger-ui/#/sound-touch-manager in a browser to see the REST API of Soundtouch controller.

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
        --name soundtouch \
        -v /logs:/logs \
        soundtouch:latest
```

## License

Copyright &copy; 2019 [cbg1710](https://github.com/cbg1710).
Licensed under the [MIT License](LICENSE).
