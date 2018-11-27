package com.pavan;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Pavan4 {
	public static void main(String[] args) throws IOException {
		System.out.println("pavan");
		File file = new File("c:\\tests\\test1\\TestData.txt");

		BufferedReader br = new BufferedReader(new FileReader(file));
		String st;
		List<String> fileData = new ArrayList<String>();
		while ((st = br.readLine()) != null) {
			fileData.add(st);
		}
		Map<String, List<String>> lineMapData = new LinkedHashMap<>();
		for (int i = 0; i < fileData.size(); i++) {
			String lineData = fileData.get(i);
			List<String> valuesList = new ArrayList<String>();
			for (String s : lineData.split("=")[1].split("\\.")) {
				valuesList.add(s);
			}
			lineMapData.put(lineData.split("=")[0],
			                valuesList);
		}
		System.out.println(generateData(lineMapData));
		br.close();
	}

	private static String generateData(Map<String, List<String>> lineMapData) {
		StringBuffer data = new StringBuffer();
		for (Map.Entry<String, List<String>> entry : lineMapData.entrySet()) {
			// getter
			data.append("public void " + generateGetter(entry.getKey(),
			                                            entry.getValue()
			                                                 .get(0))
			        + "{\n");
			data.append("  if(");

			for (int i = 0; i < entry.getValue()
			                         .size(); i++) {
				if (i == 0) {
					data.append(toLowerUpper(entry.getValue()
					                              .get(i)));
				} else if (i != entry.getValue()
				                     .size()
				        - 1) {
					data.append(toLowerUpper(entry.getValue()
					                              .get(0))
					        + "." + generateGetter(entry.getValue()
					                                    .get(i),
					                               null));
				}
				if (i != entry.getValue()
				              .size()
				        - 1) {
					data.append("!=null");
					if (i != entry.getValue()
					              .size()
					        - 2) {
						data.append(" && ");
					}
				}
			}

			data.append("){\n     ");
			for (int i = 0; i < entry.getValue()
			                         .size(); i++) {
				if (i == 0) {
					data.append(toLowerUpper(entry.getValue()
					                              .get(i)));
				} else {
					data.append(generateGetter(entry.getValue()
					                                .get(i),
					                           null));
				}
				if (i != entry.getValue()
				              .size()
				        - 1) {
					data.append(".");
				}
			}
			data.append(";\n  }");
			data.append("\n}\n\n");

			// setter
			data.append("public void " + generateSetter(entry.getKey(),
			                                            entry.getValue()
			                                                 .get(0))
			        + "{\n  ");
			data.append(entry.getKey() + "=");
			for (int i = 0; i < entry.getValue()
			                         .size(); i++) {
				if (i == 0) {
					data.append(toLowerUpper(entry.getValue()
					                              .get(i)));
				} else {
					data.append(generateGetter(entry.getValue()
					                                .get(i),
					                           null));
				}
				if (i != entry.getValue()
				              .size()
				        - 1) {
					data.append(".");
				}
			}
			data.append(";\n}");
			data.append("\n\n");
		}
		return data.toString();
	}

	public static String generateGetter(String s, String s1) {
		String temp = "get" + toUpperLower(s);
		if (s1 != null) {
			temp += "(" + s1 + " " + toLowerUpper(s1) + ")";
		} else {
			temp += "()";
		}
		return temp;
	}

	public static String generateSetter(String s, String s1) {
		String temp = "set" + toUpperLower(s);
		if (s1 != null) {
			temp += "(" + s1 + " " + toLowerUpper(s1) + ")";
		} else {
			temp += "()";
		}
		return temp;
	}

	public static String toUpperLower(String s) {
		return Character.toUpperCase(s.charAt(0)) + s.substring(1);
	}

	public static String toLowerUpper(String s) {
		return Character.toLowerCase(s.charAt(0)) + s.substring(1);
	}

}
