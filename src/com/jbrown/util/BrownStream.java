package com.jbrown.util;

import java.io.OutputStream;
import java.io.PrintStream;
import java.util.regex.Pattern;

public class BrownStream extends PrintStream {
	public BrownStream(OutputStream out) {
		super(out);
	}

	private boolean matches(String s) {
		Pattern pattern = Pattern
				.compile(".*(JNativeHook|Global|free|GNU|Barker).*");
		return pattern.matcher(s).find();
	}

	@Override
	public void print(String s) {
		if (matches(s)) {
			s = "";
		}

		super.print(s);
	}

	@Override
	public void println(String s) {
		if (matches(s)) {
			s = "";
		}

		super.println(s);
	}
}