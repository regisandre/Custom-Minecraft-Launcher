package com.galkins.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class Util {

	public static enum OS {
		WINDOWS, MACOS, SOLARIS, LINUX, OTHER;
	}

	public static OS getPlatform() {
		final String osName = System.getProperty("os.name").toLowerCase();
		if (osName.contains("win"))
			return OS.WINDOWS;
		if (osName.contains("mac"))
			return OS.MACOS;
		if (osName.contains("linux") || osName.contains("unix"))
			return OS.LINUX;
		return OS.OTHER;
	}

	public static File getWorkingDirectory() {
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
			workingDirectory = new File(userHome, "Library/" + Constants.name
					+ "/");
			break;
		default:
			workingDirectory = new File(userHome, Constants.name + "/");
		}
		if (!workingDirectory.exists())
			workingDirectory.mkdirs();
		return workingDirectory;
	}

	public static void download(String fileURL, String directory)
			throws IOException {
		String downloadedFileName = fileURL
				.substring(fileURL.lastIndexOf("/") + 1);

		URL url = new URL(fileURL);
		InputStream is = url.openStream();

		FileOutputStream fos = new FileOutputStream(directory + "/"
				+ downloadedFileName);

		byte[] buffer = new byte[4096];
		int bytesRead = 0;

		System.out.println("Download " + downloadedFileName);
		while ((bytesRead = is.read(buffer)) != -1) {
			System.out.print(".");
			fos.write(buffer, 0, bytesRead);
		}
		System.out.println("\nDownloading completion " + downloadedFileName);

		fos.close();
		is.close();
	}
}
