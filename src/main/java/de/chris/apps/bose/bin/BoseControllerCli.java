package de.chris.apps.bose.bin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * CliApplication for controlling a Bose SoundTouch30 system via REST.
 * 
 * @author glaesec
 */
@SpringBootApplication
@ComponentScan(basePackages = {"de.chris.apps"})
public class BoseControllerCli {

	public static void main(String[] args) {
        SpringApplication.run(BoseControllerCli.class, args);
	}
}
