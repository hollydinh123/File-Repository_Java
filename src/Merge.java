import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.io.LineIterator;

/**
 * This class sets up the files to merge. It finds 
 * the conflicting files and place them in the TGT File.
 * This also performs a 3-way merge where each of the
 * 3 conflicting files are placed in the TGT and renamed
 * MT, MR, or MS.
 * @author Holly Dinh & Jordan Le
 **/

public class Merge {
	Scanner scan = new Scanner(System.in);
	String source;

	String TGTLine;
	String dir = Create_Repo.DirRepo();

	public void merge() throws IOException {
		System.out.println("Enter the manifest file that is going to be the source: ");
		source = scan.next();
		// set file to MT
		File mt = new File(dir + "\\manifest\\MT");

		// set file to MR
		File mr = new File(dir + "\\manifest\\MR");

		// set file to MS
		File ms = new File(dir + "\\manifest\\MS");

		// Grabs the locations of the checkin file AKA TGT file
		String manifestR = manifestReader(source);
		System.out.println(manifestR);
		File srcDir = new File(manifestR);
		// srcDir.renameTo(mt); // renames the file

		// Sets the location of the merge files
		File mergeDir = Create_Repo.DirLeaf();

		// Copies the TGT file to the location of the merge file
		FileUtils.copyDirectory(srcDir, mergeDir);

		System.out.println("Enter how many directory is merging into the source (1 or 2)");
		int numDir = scan.nextInt();
		if (numDir == 1) {
			System.out.println("Enter the directory of the PTree you want to merge with the source: ");
			String m1 = scan.next();
			File file1 = new File(m1);
			boolean isTwoEqual = FileUtils.contentEquals(srcDir, file1);
			if (isTwoEqual == true) {
			file1.renameTo(mr); // renames the file
			FileUtils.copyDirectory(file1, mergeDir); // copies the file to
														// PTree folder
			}
		} else {
			System.out.println("Enter the directory of the 1st PTree you want to merge with the source: ");
			String m1 = scan.next();
			File file1 = new File(m1);

			System.out.println("Enter the directory of the 2nd PTree you want to merge with the source: ");
			String m2 = scan.next();
			File file2 = new File(m2);

			boolean isTwoEqual0 = FileUtils.contentEquals(srcDir, file1);
			if (isTwoEqual0 == true) {
			file1.renameTo(mr); // renames the file
			FileUtils.copyDirectory(file1, mergeDir); // copies the file to
														// PTree folder
			}

			boolean isTwoEqual1 = FileUtils.contentEquals(srcDir, file2);
			if (isTwoEqual1 == true) {
			file1.renameTo(ms); // renames the file
			FileUtils.copyDirectory(file2, mergeDir); // copies the file to
														// PTree folder
			}
		}

	}

	public String manifestReader(String manifest) throws IOException {
		// File man = new File(manifest);
		TGTLine = Files.readAllLines(Paths.get(manifest + ".txt")).get(4);// .get(#)
																			// change
																			// line
																			// number
		System.out.println(TGTLine);
		return TGTLine;
	}

}
