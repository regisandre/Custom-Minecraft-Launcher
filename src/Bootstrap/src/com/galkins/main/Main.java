package com.galkins.main;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;

import com.galkins.utils.Constants;
import com.galkins.utils.Util;

public class Main {
	public static void main(String[] args) throws IOException {
		Constants.directory.mkdirs();
		URLConnection distLauncher = new URL(Constants.defaultSite
				+ "launcher.jar").openConnection();
		long dLLM = distLauncher.getLastModified();
		File localLauncher = new File(Constants.directory + "launcher.jar");
		long lLLM = localLauncher.lastModified();
		if (dLLM != lLLM) {
			Util.download(Constants.defaultSite + "launcher.jar",
					Constants.directory.toString());
			localLauncher.setLastModified(dLLM);
		}

		String command = System.getProperty("java.home", ".") + "/bin/java.exe"
				+ " -jar " + Constants.directory + "launcher.jar";
		Runtime.getRuntime().exec(command);
		System.exit(0);
	}

}