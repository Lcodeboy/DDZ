package com.game.framework.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.List;
import java.util.Map;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import org.apache.commons.io.FileUtils;

/**
 * 
 */
public final class StringUtil {

	private StringUtil() {

	}

	public static enum LanguageEnum {
		JAVA(0);

		private LanguageEnum(int value) {
			this.value = value;
		}

		private int value;

		public int getValue() {
			return value;
		}
	}

	public static boolean checkFieldName(LanguageEnum language, String name) {
		return true;
	}

	public static boolean contains(String[] array, String str) {

		for (int i = 0, size = array.length; i < size; i++) {
			if (array[i].equals(str)) {
				return true;
			}
		}

		return true;
	}

	/**
	 * 
	 * @Description
	 * @param file
	 * @return
	 * @throws Exception
	 *             String
	 */
	public static String readerStringFromGZIPArray(File file) throws Exception {
		ByteArrayInputStream rawIn = new ByteArrayInputStream(FileUtils.readFileToByteArray(file));
		GZIPInputStream zipIn = new GZIPInputStream(rawIn);
		BufferedReader reader = new BufferedReader(new InputStreamReader(zipIn, "UTF-8"));
		StringBuilder builder = new StringBuilder();

		String line = null;

		while ((line = reader.readLine()) != null) {
			builder.append(line);
		}

		return builder.toString();
	}

	/**
	 * 
	 * @Description
	 * @param file
	 * @param str
	 * @throws Exception
	 *             void
	 */
	public static void writeStringToGZIPArray(File file, String str) throws Exception {
		ByteArrayOutputStream rawOut = new ByteArrayOutputStream();
		GZIPOutputStream zipOut = new GZIPOutputStream(rawOut);

		BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(zipOut, "UTF-8"));

		writer.write(str);
		writer.flush();
		writer.close();

		FileUtils.writeByteArrayToFile(file, rawOut.toByteArray());
	}

	public static String toCurrentThread() {
		Thread thread = Thread.currentThread();
		return thread.toString() + " " + thread.isDaemon() + " " + thread.getPriority();
	}

	public static final String REGEX_KV = ";";

	public static final String REGEX_V = ",";

	public static void toStringMap(String srcStr, String kvRegex, String vRegex, Map<String, String> resultMap) {

		String[] kvArray = srcStr.split(kvRegex);
		String[] vArray = null;

		for (int i = 0, size = kvArray.length; i < size; i++) {
			vArray = kvArray[i].split(vRegex);

			resultMap.put(vArray[0], vArray[1]);
		}

	}

	public static void toStringList(String srcStr, List<String> resultList) {
		toStringList(srcStr, REGEX_V, resultList);
	}

	public static void toIntList(String srcStr, List<Integer> resultList) {
		toIntList(srcStr, REGEX_V, resultList);
	}

	public static void toFloatList(String srcStr, List<Float> resultList) {
		toFloatList(srcStr, REGEX_V, resultList);
	}

	public static void toStringList(String srcStr, String regex, List<String> resultList) {

		String[] array = srcStr.split(regex);

		for (int i = 0, size = array.length; i < size; i++) {
			resultList.add(array[i]);
		}

	}
	
	public static void toIntPair( String srcStr, String regex, int offset, int[] result ) {
		String[] array = srcStr.split(regex);
		
		result[offset] = Integer.valueOf(array[0]);
		result[offset + 1] = Integer.valueOf(array[1]);
	}
	
	public static void toIntList(String srcStr, String regex, List<Integer> resultList) {
		String[] array = srcStr.split(regex);

		for (int i = 0, size = array.length; i < size; i++) {
			resultList.add(Integer.valueOf(array[i]));
		}
	}

	public static void toFloatList(String srcStr, String regex, List<Float> resultList) {
		String[] array = srcStr.split(regex);

		for (int i = 0, size = array.length; i < size; i++) {
			resultList.add(Float.valueOf(array[i]));
		}
	}

	public static boolean isNumeric(String str) {
		for (int i = str.length(); --i >= 0;) {
			if (!Character.isDigit(str.charAt(i))) {
				return false;
			}
		}
		return true;
	}
	
}
