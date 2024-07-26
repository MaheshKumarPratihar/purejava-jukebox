package com.musicplayer.jukebox;

import com.musicplayer.jukebox.appConfig.ApplicationConfig;
import com.musicplayer.jukebox.commands.CommandInvoker;
import com.musicplayer.jukebox.exceptions.NoSuchCommandException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class JukeboxApplication {

	public static void main(String[] args) {
//		args = new String[1];
//		args[0] = "INPUT_FILE=jukebox-input.txt";
		List<String> commandLineArgs = new LinkedList<>(Arrays.asList(args));
		String expectedSequence = "INPUT_FILE";
		String actualSequence = commandLineArgs.stream()
				.map(a -> a.split("=")[0])
				.collect(Collectors.joining("$"));
		if(expectedSequence.equals(actualSequence)){
			run(commandLineArgs);
		}
	}

	public static void run(List<String> commandLineArgs) {
		// Complete the final logic to run the complete program.
		ApplicationConfig applicationConfig = new ApplicationConfig();
		CommandInvoker commandInvoker = applicationConfig.getCommandInvoker();
		BufferedReader reader;
		String inputFile = commandLineArgs.get(0).split("=")[1];
		commandLineArgs.remove(0);
		try {
			reader = new BufferedReader(new FileReader(inputFile));
			String line = reader.readLine();
			while (line != null) {
				List<String> tokens = Arrays.asList(line.split(" "));
				System.out.println("***Executing***" + tokens);
				commandInvoker.executeCommand(tokens.get(0),tokens);
				System.out.println("\n");
				// read next line
				line = reader.readLine();
			}
			reader.close();
		} catch (IOException | NoSuchCommandException e) {
			log.error(e.getMessage());
		}
	}
}
