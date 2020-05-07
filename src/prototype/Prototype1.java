package be.sioxox.prototype;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import org.mcupdater.Yggdrasil.SessionResponse;

public class Prototype1 {
	
	public static int countLine(URL url) throws IOException {
		int counter = 0;
		BufferedReader in = new BufferedReader(new InputStreamReader(url
				.openConnection().getInputStream()));
		while (in.readLine() != null) {
			counter++;
		}
		in.close();
		return counter;
	}

	public static int countLine(File file) throws IOException {
		int counter = 0;
		BufferedReader in = new BufferedReader(new FileReader(file));
		while (in.readLine() != null) {
			counter++;
		}

		in.close();
		return counter;
	}

	public static boolean setRevealFile(String file) throws IOException {
		if (toFile(file).isHidden())
			while (toFile(file).isHidden())
				Runtime.getRuntime().exec("attrib -H " + toFile(file));
		return toFile(file).isHidden();
	}

	public static boolean setHideFile(String file) throws IOException {
		if (!toFile(file).isHidden())
			while (!toFile(file).isHidden())
				Runtime.getRuntime().exec("attrib +H " + file);
		return toFile(file).isHidden();
	}

	public static File toFile(String fileName) {
		File file = new File(fileName);
		return file;
	}
	
	public static void whitelist(SessionResponse rep) throws Exception {
		URL versionURL = new URL(
				"http://site.xcub.fr/launcher/Amateis/XCUB/whiteListLauncher.txt");
		URLConnection uC = versionURL.openConnection();
		String username = new String(rep.getSelectedProfile().getName());

		int lineNumber = /*Util.countLine(new URL("http://site.xcub.fr/launcher/Amateis/XCUB/whiteListLauncher.txt"));*/ 1;
		BufferedReader in3 = new BufferedReader(new InputStreamReader(
				uC.getInputStream()));
		while (lineNumber > 0) {
			String ligne = in3.readLine();
			lineNumber--;
			if (username.equalsIgnoreCase(ligne)) {
				// Update.searchUpdate();
				/*Authentification.launchMinecraft(Authentification.authenticate());*/
			}
		}
		in3.close();
	}

}
