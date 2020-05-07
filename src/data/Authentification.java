package be.sioxox.data;

import java.awt.FontFormatException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.mcupdater.Yggdrasil.AuthManager;
import org.mcupdater.Yggdrasil.SessionResponse;

import be.sioxox.main.Constants;
import be.sioxox.main.Main;
import be.sioxox.utils.Util;

class Authentification {
	
	Constants constants;
	Util util;
	Main main;

	SessionResponse authenticate() {
		AuthManager a = new AuthManager();

		SessionResponse rep = a.authenticate(main.getLogin(), main.getPassword(), null);
		System.out.println("Authentification...");
		if (rep.getAccessToken() == null) {
			System.out.println("Erreur critique lors de l'authentification");
		} else {
			System.out.println("L'authenfication c'est déroulée avec succès: " + rep.getSessionId());
			return rep;
		}
		
		return null;
	}

	String uuidToUsername(SessionResponse rep) throws IOException, ParseException {
		String uuid = rep.getSessionId().substring(39, 71);

		URL authLog = new URL("https://api.mojang.com/users/profiles/minecraft/" + uuid);

		URLConnection urlC = authLog.openConnection();
		BufferedReader authLogRead = new BufferedReader(new InputStreamReader(urlC.getInputStream()));

		JSONParser parser = new JSONParser();
		Object obj = parser.parse(authLogRead);
		JSONObject jsonObject = (JSONObject) obj;

		String username = (String) jsonObject.get("name");
		authLogRead.close();

		return username;
	}

	String usernameToUuid(String username) throws IOException, ParseException {
		URL ssMojang = new URL("https://api.mojang.com/users/profiles/minecraft/" + username);

		URLConnection urlC = ssMojang.openConnection();
		BufferedReader ssMojangRead = new BufferedReader(new InputStreamReader(urlC.getInputStream()));

		JSONParser parser = new JSONParser();
		Object obj = parser.parse(ssMojangRead);
		JSONObject jsonObject = (JSONObject) obj;

		String uuid = (String) jsonObject.get("id");
		ssMojangRead.close();

		return uuid;
	}

	void launchMinecraft(SessionResponse rep) throws IOException, InterruptedException, FontFormatException {
		String version = "version";
		String appdata = util.getWorkingDirectory();
		String natives = appdata + "/versions/" + version + "/natives/";
		String javaPath = System.getProperty("java.home", ".") + "/bin/java/";
		String javaArguments = "";
		//Display d = new Display();
		//String pseudo = d.getUsername();

		char separator;
		String os = System.getProperty("os.name").toUpperCase();

		if (os.contains("WIN"))
			separator = ';';
		else
			separator = ':';
		
		natives = appdata + "/versions/" + version + "/" + "natives/";
		String[] commandeOffi = {javaPath, javaArguments, "-Djava.library.path=" + natives, "-cp",
				appdata + "/libraries/minecraftforge-9.11.1.965.jar"
						+ separator + appdata
						+ "/libraries/launchwrapper-1.8.jar" + separator
						+ appdata + "/libraries/asm-all-4.1.jar" + separator
						+ appdata + "/libraries/scala-library-2.10.2.jar"
						+ separator + appdata
						+ "/libraries/scala-compiler-2.10.2.jar" + separator
						+ appdata + "/libraries/lzma-0.0.1.jar" + separator
						+ appdata + "/libraries/jopt-simple-4.5.jar"
						+ separator + appdata
						+ "/libraries/codecjorbis-20101023.jar" + separator
						+ appdata + "/libraries/codecwav-20101023.jar"
						+ separator + appdata
						+ "/libraries/libraryjavasound-20101123.jar"
						+ separator + appdata
						+ "/libraries/librarylwjglopenal-20100824.jar"
						+ separator + appdata
						+ "/libraries/soundsystem-20120107.jar" + separator
						+ appdata + "/libraries/argo-2.25_fixed.jar"
						+ separator + appdata
						+ "/libraries/bcprov-jdk15on-1.47.jar" + separator
						+ appdata + "/libraries/guava-14.0.jar" + separator
						+ appdata + "/libraries/commons-lang3-3.1.jar"
						+ separator + appdata + "/libraries/commons-io-2.4.jar"
						+ separator + appdata + "/libraries/jinput-2.0.5.jar"
						+ separator + appdata + "/libraries/jutils-1.0.0.jar"
						+ separator + appdata + "/libraries/gson-2.2.2.jar"
						+ separator + appdata + "/libraries/lwjgl-2.9.0.jar"
						+ separator + appdata
						+ "/libraries/lwjgl_util-2.9.0.jar" + separator
						+ appdata + "/versions/" + version + "/" + version
						+ ".jar", "net.minecraft.launchwrapper.Launch",
				"--username=" + rep.getSelectedProfile().getName(),
				"--session=" + rep.getSessionId(), "--version", version,
				"--gameDir", appdata, "--assetsDir", appdata + "/assets/",
				"--tweakClass", "cpw.mods.fml.common.launcher.FMLTweaker" };
		
				/*"--username", pseudo, "--version", version, "--gameDir",
				appdata, "--assetsDir", appdata + "/assets/", "--tweakClass",
				"cpw.mods.fml.common.launcher.FMLTweaker" };*/

		Runtime runtime = Runtime.getRuntime();
		System.out.println("Lancement de " + Constants.name + " en version officiel...");
		final Process process = runtime.exec(commandeOffi);

		new Thread() {
			public void run() {
				try {
					BufferedReader readerInput = new BufferedReader(
							new InputStreamReader(process.getInputStream()));
					BufferedReader readerError = new BufferedReader(
							new InputStreamReader(process.getErrorStream()));
					String line = "";
					try {
						while ((line = readerInput.readLine()) != null) {
							System.out.println(line);
						}
						while ((line = readerError.readLine()) != null) {
							System.err.println(line);
						}
					} finally {
						readerInput.close();
						readerError.close();
					}
				} catch (IOException ioe) {
					ioe.printStackTrace();
				}
			}
		}.start();

		try {
			Thread.sleep(2000L);
		} catch (Exception e) {
			e.printStackTrace();
		}

		System.exit(0);
		process.waitFor();
		process.destroy();
	}
}
