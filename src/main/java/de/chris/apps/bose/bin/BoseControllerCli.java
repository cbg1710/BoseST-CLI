package de.chris.apps.bose.bin;

import org.jline.utils.AttributedString;
import org.jline.utils.AttributedStyle;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.shell.jline.PromptProvider;

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

	@Bean
	public PromptProvider myPromptProvider() {
		return () -> new AttributedString("bose-shell:>",
				AttributedStyle.DEFAULT.foreground(AttributedStyle.YELLOW));
	}
}
