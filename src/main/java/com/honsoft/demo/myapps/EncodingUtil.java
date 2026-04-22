package com.honsoft.demo.myapps;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;

import com.ibm.icu.text.CharsetDetector;
import com.ibm.icu.text.CharsetMatch;

public class EncodingUtil {

	public static String detectEncoding(File file) throws Exception {

		byte[] data = Files.readAllBytes(file.toPath());

		if (data.length == 0)
			return "UNKNOWN";

		// 1. BOM
		String bom = detectBOM(data);
		if (bom != null)
			return bom;

		// 2. UTF-8 validation
		if (isValidUTF8(data))
			return "UTF-8";

		// 3. ICU4J detection
		CharsetDetector detector = new CharsetDetector();
		detector.setText(data);

		CharsetMatch match = detector.detect();

		if (match != null) {
			String encoding = match.getName();
			int confidence = match.getConfidence();

			// 4. Korean correction
			if (confidence < 80 && ("ISO-8859-1".equalsIgnoreCase(encoding) || "WINDOWS-1252".equalsIgnoreCase(encoding)
					|| "KOI8-R".equalsIgnoreCase(encoding))) {

				return "EUC-KR";
			}

			return encoding.toUpperCase();
		}

		return "UTF-8"; // final fallback
	}

	// --- helpers ---

	private static String detectBOM(byte[] data) {
		if (data.length >= 3 && (data[0] & 0xFF) == 0xEF && (data[1] & 0xFF) == 0xBB && (data[2] & 0xFF) == 0xBF) {
			return "UTF-8";
		}
		if (data.length >= 2) {
			if ((data[0] & 0xFF) == 0xFE && (data[1] & 0xFF) == 0xFF)
				return "UTF-16BE";
			if ((data[0] & 0xFF) == 0xFF && (data[1] & 0xFF) == 0xFE)
				return "UTF-16LE";
		}
		return null;
	}

	private static boolean isValidUTF8(byte[] input) {
		try {
			String s = new String(input, "UTF-8");
			return Arrays.equals(input, s.getBytes("UTF-8"));
		} catch (Exception e) {
			return false;
		}
	}

	public static void main(String[] args) throws IOException, Exception {
		EncodingUtil eu = new EncodingUtil();
		eu.walk("D:\\DBS\\project\\DSI");
	}

	private void walk(String filePath) throws IOException, Exception {
		// TODO Auto-generated method stub
		File root = new File(filePath);
		File[] list = root.listFiles();
		if (list == null) {
			if (root != null) {
				System.out.println(root.getCanonicalPath()+", "+detectEncoding(root));
			}
			return;
		}

		for (File f : list) {

			if (f.isDirectory()) {
				walk(f.getAbsolutePath());
			} else {
				System.out.println(f.getCanonicalPath()+", "+detectEncoding(f));
			}
		}
	}
}
