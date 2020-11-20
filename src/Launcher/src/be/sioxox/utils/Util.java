package be.sioxox.utils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.List;
import java.util.Locale;

import be.sioxox.main.Constants;

public class Util {

	enum OS {
		WINDOWS, MACOS, SOLARIS, LINUX, OTHER;
	}

	OS getPlatform() {
		final String osName = System.getProperty("os.name").toLowerCase();
		if (osName.contains("win"))
			return OS.WINDOWS;
		if (osName.contains("mac"))
			return OS.MACOS;
		if (osName.contains("linux") || osName.contains("unix"))
			return OS.LINUX;
		return OS.OTHER;
	}

	public String getWorkingDirectory() {
		final String userHome = System.getProperty("user.home", ".");
		File workingDirectory;
		switch (getPlatform()) {
		case SOLARIS:
		case LINUX:
			workingDirectory = new File(userHome, Constants.name + "/");
			break;
		case WINDOWS:
			final String applicationData = System.getenv("APPDATA");
			final String folder = applicationData != null ? applicationData
					: userHome;
			workingDirectory = new File(folder, "." + Constants.name + "/");
			break;
		case MACOS:
			workingDirectory = new File(userHome,
					"Library/Application Support/" + Constants.name + "/");
			break;
		default:
			workingDirectory = new File(userHome, Constants.name + "/");
		}
		if (!workingDirectory.exists())
			workingDirectory.mkdirs();
		return workingDirectory.toString();
	}

	String readLine(String file, int lineNumber)
			throws IOException {
		List<String> list = Files.readAllLines(new File(file).toPath(),
				Charset.defaultCharset());
		String line = list.get(lineNumber - 1);
		return line;
	}

	String getLanguage() {
		Locale locale = Locale.getDefault();
		return locale.toString();
	}
}
