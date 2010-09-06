package org.zigabyte.quantdesk;

import java.io.*;

/**
 * Generic utility methods, not specific to the project.
 * While there are few, will keep all in one class.
 * When there will be many, will split into StrUtil, IoUtil, etc.
 * This class is a good candidate to move to the Core project.
 * @author Andrey Desyatnikov
 */
public class Util {

	/**
	 * Ensures that the parent directory for the given file exists
	 * (checks, whether it exists, and creates, if it doesn't).
	 * @f the given file
	 */
	public static void ensureParentDir(File f) {
		if (f==null) return;
		System.out.println("\t" + f);
		File p = f.getParentFile();
		if (p==null) return;
		if (!p.exists()) { // If there's no parent, then...
			ensureParentDir(p); // maybe there's no grandparent, too
			p.mkdir();
		}
	}

	/**
	 * Ensures that there's the file denoted by the parameter
	 * (checks, whether it exists, and creates a new empty file, if needed).
	 */
	public static void ensureFileExists(File f) throws IOException {
		ensureParentDir(f);
		if (!f.exists()) f.createNewFile();
	}
}
