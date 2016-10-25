
/**
 * This class creates a repository
 * It consists of
 * 	-Create Folder Method
 * 	-Create Manifest Method
 * 	-Create Directory List Method
 *  -Create AID
 * @author Holly Dinh & Jordan Le
 */
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

import org.apache.commons.io.FileUtils;

public class Create_Repo {
	Scanner scan = new Scanner(System.in);
	private static String dirRepo;
	private static String source;
	private static File dirLeaf;

	// Creates Repo
	public void createRepo() throws Exception {
		System.out.println("Enter the directory to create a repository: "); // User
																			// Enter
																			// the
																			// where
																			// the
																			// repo
																			// is
																			// created
		dirRepo = scan.next();
		File theDir = new File(dirRepo);
		System.out.println("Enter the directory to copy: ");
		source = scan.next();
		File scrDir = new File(source);
		// Create the folder
		if (theDir.mkdir()) {
			System.out.println("Repository is created");
		} else {
			System.out.println("Failed to create repository");
		}
		createProjectfolder(scrDir, theDir);
		createManifestFolder();
	}

	// Creates the project folder (this version is for testing)
	public void createProjectfolder(File srcDir, File destDir) throws Exception {
		FileUtils.copyDirectory(srcDir, destDir);
		for (String str : convertFile(dirRepo)) {
			createPtree(str);
		}
	}

	// Creates the Manifest folder
	public void createManifestFolder() throws Exception {
		File dirManifest = new File(dirRepo + "\\manifest"); // Creates the
																// manifest when
																// directory is
																// made
		dirManifest.mkdir();
		// for(String str: convertFile(dirRepo)){
		// String folder = str;
		// if(!str.equals("manifest")){
		createManifestFile();
		// }
		// }
	}

	// Creates the PTree Folder
	public void createPtree(String folder) throws Exception {
		File dirPTree = new File(dirRepo + "\\" + folder + "\\PTree");
		dirPTree.mkdir();
		createLeaf(folder);
	}

	// Creates the leaf folder
	public void createLeaf(String str) throws Exception {
		for (String str0 : convertFile(dirRepo + "\\" + str)) {
			if (!str0.equals("PTree")) {
			File f = new File(dirRepo + "\\" + str + "\\" + str0);
			if (f.isDirectory()) {
				for (String str1 : convertFile(dirRepo + "\\" + str + "\\" + str0)) {
					dirLeaf = new File(dirRepo + "\\" + str + "\\PTree\\" + str1); // for
																					// testing
					File srcDir = new File(dirRepo + "\\" + str + "\\" + str0);
					File destDir = new File(dirRepo + "\\" + str);
					FileUtils.copyDirectory(srcDir, destDir);
					dirLeaf.mkdir();
					createAID(str, str1);
				}
			} else {
				dirLeaf = new File(dirRepo + "\\" + str + "\\PTree\\" + str0); // for
																				// testing
				dirLeaf.mkdir();
				createAID(str, str0);
			}

			}
		}
	}

	// Creates the Manifest file
	public static PrintWriter createManifestFile() throws Exception {
		PrintWriter manFile = new PrintWriter(dirRepo + "\\manifest\\" + createTimestamp() + ".txt");
		manFile.write(dirRepo + ": ");
		for (String str : convertFile(dirRepo)) {
			manFile.write(str + " ");
		}
		manFile.write("\r\n" + dirRepo + "\\manifest" + ": ");
		for (String str : convertFile(dirRepo + "\\manifest")) {
			manFile.write(str + " ");
		}

		for (String str : convertFile(dirRepo)) {
			if (!str.equals("manifest")) {
			manFile.write("\r\n" + dirRepo + "\\" + str + "\\PTree" + ": ");
			for (String str0 : convertFile(dirRepo + "\\" + str + "\\PTree")) {
				manFile.write(str0 + " ");
			}
			for (String str1 : convertFile(dirRepo + "\\" + str + "\\PTree")) {
				String file = str1;
				manFile.write("\r\n" + dirRepo + "\\" + str + "\\PTree\\" + file + ": ");
				for (String str2 : convertFile(dirRepo + "\\" + str + "\\PTree\\" + file)) {
					manFile.write("\r\n		" + dirRepo + "\\" + str + "\\PTree\\" + file + "\\" + str2);
				}
			}
			}
		}

		manFile.write("\r\n" + createTimestamp());
		manFile.close();
		return manFile;
	}

	// Creates the check sum method "using .length()"
	public static int checkSum(String folder, String file) throws Exception {
		String filepath = dirRepo + "\\" + folder + "\\" + file; // for testing
		int byteSize = (int) new File(filepath).length();

		System.out.println("Checksum for the File: " + byteSize + " bytes");

		return (int) byteSize;
	}

	// Creates the Artifact ID
	public static PrintWriter createAID(String folder, String file) throws Exception {
		PrintWriter AID = new PrintWriter(
			dirRepo + "\\" + folder + "\\PTree" + "\\" + file + "\\" + checkSum(folder, file) + ".txt"); // for
																											// testing
		FileReader fileReader = new FileReader(dirRepo + "\\" + folder + "\\" + file);
		String line = null;
		// Copies content of the main file and paste it on the AID file
		@SuppressWarnings("resource")
		BufferedReader bufferedReader = new BufferedReader(fileReader);
		while ((line = bufferedReader.readLine()) != null) {
			AID.write(line);
		}
		AID.close();
		return AID;
	}

	// Converts File arrays into arraylist of strings
	public static ArrayList<String> convertFile(String dir) {
		File folder = new File(dir);
		File[] listOfFiles = folder.listFiles();
		ArrayList<String> fileNames = new ArrayList<String>();
		for (File file : listOfFiles) {
			fileNames.add(file.getName());
		}
		return fileNames;

	}

	// Creates a Timestamp
	public static String createTimestamp() {
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy_MM_dd_h_mm_ss_a");
		String formattedDate = sdf.format(date);
		return formattedDate;
	}

	// Variable dirRepo can be retrieved
	public static String DirRepo() {
		return dirRepo;
	}

	// Variable source can be retrieved
	public static String Source() {
		return source;
	}

	// Variable dirLeaf can be retrieved
	public static File DirLeaf() {
		return dirLeaf;

	}

}