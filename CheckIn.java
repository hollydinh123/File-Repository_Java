import java.io.File;
import java.io.PrintWriter;

import org.apache.commons.io.FileUtils;

/**
 * Creates a manifest when a file is updated
 * It also prints the file that was checked in
 * @author Holly Dinh & Jordan Le
 **/

public class CheckIn {
	static String dirRepo;

	public void checkIn() throws Exception {
		String source = Create_Repo.Source();
		dirRepo = Create_Repo.DirRepo();
		File theDir = new File(dirRepo);
		File scrDir = new File(source);
		for (String str : Create_Repo.convertFile(dirRepo)) {
			if (!str.equals("manifest")) {
			for (String str0 : Create_Repo.convertFile(dirRepo + "\\" + str)) {
				if (!str0.equals("PTree")) {
					FileUtils.copyDirectory(scrDir, theDir); // copies/overwrite
																// the folder
					Create_Repo.createAID(str, str0);
				}
			}
			createManifestFile();
			}
		}
	}

	// Creates a Manifest file, specifying that it checks in
	// It also writes the most recent file added to the folder in the manifest
	// file
	public static PrintWriter createManifestFile() throws Exception {
		PrintWriter manFile = new PrintWriter(dirRepo + "\\manifest\\" + Create_Repo.createTimestamp() + ".txt");
		manFile.write(dirRepo + ": ");
		for (String str : Create_Repo.convertFile(dirRepo)) {
			manFile.write(str + " ");
		}
		manFile.write("\r\n" + dirRepo + "\\manifest" + ": ");
		for (String str : Create_Repo.convertFile(dirRepo + "\\manifest")) {
			manFile.write(str + " ");
		}

		for (String str : Create_Repo.convertFile(dirRepo)) {
			if (!str.equals("manifest")) {
			manFile.write("\r\n" + dirRepo + "\\" + str + "\\PTree" + ": ");
			for (String str0 : Create_Repo.convertFile(dirRepo + "\\" + str + "\\PTree")) {
				manFile.write(str0 + " ");
			}
			for (String str1 : Create_Repo.convertFile(dirRepo + "\\" + str + "\\PTree")) {
				String file = str1;
				manFile.write("\r\n" + dirRepo + "\\" + str + "\\PTree\\" + file + ": ");
				File str2 = getLatestFilefromDir(dirRepo + "\\" + str + "\\PTree\\" + file);
				// String name = str2.getName();
				// int index = name.indexOf (".");
				// String namF = name.substring (0, index);
				// "\r\n"+dirRepo+"\\"+str+"\\PTree\\"+file+"\\"+namF
				manFile.write("\r\n" + str2);
			}
			}
		}

		manFile.write("\r\n" + Create_Repo.createTimestamp());
		manFile.write("\r\nCheckin");
		manFile.close();
		return manFile;
	}

	private static File getLatestFilefromDir(String dirPath) {
		File dir = new File(dirPath);
		File[] files = dir.listFiles();
		if (files == null || files.length == 0) {
			return null;
		}

		File lastModifiedFile = files[0];
		for (int i = 1; i < files.length; i++) {
			if (lastModifiedFile.lastModified() < files[i].lastModified()) {
			lastModifiedFile = files[i];
			}
		}
		return lastModifiedFile;
	}
}
