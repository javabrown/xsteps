package com.jbrown.util;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class TSV {
	private String[][] _tsvRows;
	String _fileName;

	public TSV(String fileName) {
		_fileName = fileName;
		_tsvRows = read();
	}

	private String[][] read() {
		String[][] resultArray = new String[0][0];

		List<String> lines = new ArrayList<String>();
		try {
			lines = Files.readAllLines(Paths.get(_fileName),
					StandardCharsets.UTF_8);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		resultArray = new String[lines.size()][];

		for (int i = 0; i < lines.size(); i++) {
			resultArray[i] = lines.get(i).split("\t"); // tab-separated
		}

		return resultArray;
	}

	public boolean isValid() {
		return _tsvRows != null && _tsvRows.length > 0;
	}

	public int nColumns() {
		if (!isValid()) {
			return -1;
		}

		return _tsvRows[0].length;
	}

	public int nRows() {
		if (!isValid()) {
			return -1;
		}

		return _tsvRows.length;
	}
	
	public String[] getColumn(int colNumber) {
		if (!this.isValid() || colNumber < 0 || colNumber >= nColumns()) {
			return new String[0];
		}

		List<String> list = new ArrayList<String>();
		for (int row = 0; row < _tsvRows.length; row++) {
			String[] cols = _tsvRows[row];
			list.add(cols[colNumber]);
		}

		return list.toArray(new String[0]);
	}
}