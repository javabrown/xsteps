package com.jbrown.util;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;

import org.apache.commons.io.IOUtils;

public class StepUtil {
	public static int cantorP3(int a, int b, int c) {
		int ab = doCantorPair(a, b);
		return doCantorPair(ab, c);
	}

	public static int[] reverseP3(int p3) {
		int[] abc = reverseToCantorPair(p3);

		int[] ab = reverseToCantorPair(abc[0]);

		int c = abc[1];

		int a = ab[0];
		int b = ab[1];

		return new int[] { a, b, c };
	}

	public static int doCantorPair(int x, int y) {
		return ((x + y) * (x + y + 1)) / 2 + y;
	}

	public static int[] reverseToCantorPair(int z) {
		int[] pair = new int[2];
		int t = (int) Math.floor((-1D + Math.sqrt(1D + 8 * z)) / 2D);
		int x = t * (t + 3) / 2 - z;
		int y = z - t * (t + 1) / 2;
		pair[0] = (int) x;
		pair[1] = (int) y;
		return pair;
	}

	// public static void main(String[] args){
	// int a = cantorP3(10, 2, 5);
	// int[] splited = reverseP3(a);
	//
	// System.out.println("CantorPair="+ a);
	//
	// for(int s : splited){
	// System.out.println("reverse="+ s);
	// }
	// }

	public static String getFileWithUtil(String fileName, Class klass) {
		String result = "";

		try {
			result = IOUtils.toString(
					klass.getClass().getResourceAsStream(fileName), "UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}

	public static void setClipboardContents(String content) {
		StringSelection stringSelection = new StringSelection(content);
		Clipboard clpbrd = Toolkit.getDefaultToolkit().getSystemClipboard();
		clpbrd.setContents(stringSelection, null);
	}

	public static String getClipboardContents() {
		String result = "";
		Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
		// odd: the Object param of getContents is not currently used
		Transferable contents = clipboard.getContents(null);
		boolean hasTransferableText = (contents != null)
				&& contents.isDataFlavorSupported(DataFlavor.stringFlavor);
		if (hasTransferableText) {
			try {
				result = (String) contents
						.getTransferData(DataFlavor.stringFlavor);
			} catch (UnsupportedFlavorException | IOException ex) {
				System.out.println(ex);
				ex.printStackTrace();
			}
		}

		return result;
	}
}
