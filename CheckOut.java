import java.io.File;
import java.io.PrintWriter;
import java.util.Scanner;

import org.apache.commons.io.FileUtils;

/**
 * Creates copy of the repo into designated location
 * @author Holly Dinh and Jordan Le
 **/
public class CheckOut {
	Scanner scan = new Scanner(System.in);
	static String dirRepo;
	static String dir;
	static String outDir;

	public void checkOut() throws Exception {
		System.out.println("Enter the directory to check out to: ");
		outDir = scan.next();
		System.out.println("Enter the Ptree directory you want to check out: ");
		dir = scan.next();
		dirRepo = Create_Repo.DirRepo();

		File theDir = new File(dir);
		File newDir = new File(outDir);

		// Create the folder
		FileUtils.copyDirectory(theDir, newDir);
		System.out.println("Repository is copied.");
		createManifestFile();
	}

	// Creates a Manifest file, specifying that it checks out
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
				for (String str2 : Create_Repo.convertFile(dirRepo + "\\" + str + "\\PTree\\" + file)) {
					manFile.write("\r\n" + dirRepo + "\\" + str + "\\PTree\\" + file + "\\" + str2);
				}
			}
			}
		}

		manFile.write("\r\n" + Create_Repo.createTimestamp());
		manFile.write("\r\nCheckout: " + outDir);
		manFile.close();
		return manFile;
	}

}