package br.edu.gs.tests;

public class MainTestes {
	public static void main(String[] args) {
		String a = "bas-sa-sa@gma5il.co-m";
		String b = "a123";
		String c = "@#$%%";
		
		System.out.println(a.matches("(?:\\w+(?:\\.|-)*){1,4}@(?:\\w+(?:\\.|-)*){1,4}"));
		System.out.println(b.matches(".*\\d+.*"));
		System.out.println(c.matches(".*\\d+.*"));
	}
}
