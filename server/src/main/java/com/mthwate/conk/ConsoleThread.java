package com.mthwate.conk;

import com.mthwate.conk.command.Command;
import com.mthwate.conk.command.Exec;
import com.mthwate.datlib.IOUtils;
import com.mthwate.datlib.TimeUtils;
import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author mthwate
 */
public class ConsoleThread extends Thread {

	private static final Logger log = LoggerFactory.getLogger(ConsoleThread.class);

	private boolean run = true;

	private ServerApp app;

	private Map<String, Command> commands = new HashMap<>();

	public ConsoleThread(ServerApp serverApp) {
		this.app = serverApp;

		Reflections reflections = new Reflections();

		Set<Class<? extends Command>> types = reflections.getSubTypesOf(Command.class);

		for (Class<? extends Command> type : types) {
			if (type.isAnnotationPresent(Exec.class)) {
				try {
					Command command = type.newInstance();
					commands.put(command.getName(), command);
				} catch (ReflectiveOperationException e) {
					log.error("Could not instantiate command {}", type);
				}
			}
		}
	}

	@Override
	public void run() {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

		try {
			while (run) {
				if (reader.ready()) {
					String line = reader.readLine();
					String[] split = line.split(" ");
					Command command = commands.get(split[0]);
					if (command != null) {
						command.run(app);
					}
				} else {
					TimeUtils.sleep(100);
				}
			}
		} catch (IOException e) {
			log.error("Error while accepting console input");
		} finally {
			IOUtils.close(reader);
		}

	}

	public void shutdown() {
		if (this.isAlive()) {
			log.info("Stopping console thread");
			run = false;
			while (this.isAlive()) {
				TimeUtils.sleep(100);
			}
			log.info("Console thread stopped");
		}
	}

}
