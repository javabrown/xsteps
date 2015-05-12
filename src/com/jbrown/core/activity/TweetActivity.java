package com.jbrown.core.activity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.jbrown.util.StepUtil;
import com.jbrown.util.TSV;

public class TweetActivity implements ActivityI {
	private RowTweets _rowTweets;

	public TweetActivity(String fileName) {
		_rowTweets = new RowTweets(fileName);
	}
	
	 
//	
//	public String getNextTweets(){
//		if(_index >= _tsv.nRows()){
//			_index = 0;
//		}
//		
//		String tweets =  _tweets[_index] + " " + _trends[_index];
//		_index++;
//		
//		return tweets;
//	}
	
	public static void main(String[] arg) {
	    String text = "To be or not to be, that is the question.";
	    String newText = text.replace(' ', '/');     // Modify the string text
	    
	    RowTweets.makeCombination1("This is test. I am Test!!");
	    //RowTweets.relpaceOneByOne("This is test. I am Test!!", '!', '-');
	    System.out.println(newText);
	  }
	
	@Override
	public void prepareNextClipContent() {
		String tweet = "";//getNextTweets();
		System.out.println(tweet);
		StepUtil.setClipboardContents(tweet);
	}
	
	static class RowTweets {
		private String[] _tweets;
		private String[] _trends;
		private int _index;
		private TSV _tsv;
		
		private String _fileName;
		
		public RowTweets(String fileName) {
			_fileName = fileName;
			this.refresh();
		}

		public void refresh() {
			TSV tsv = new TSV(_fileName);
			_tweets = tsv.getColumn(0);
			_trends = tsv.getColumn(1);
			_index = 0;
		}

		public String getTweet(){
			if(_index < 0 || _index >= _tsv.nRows()){
				_index = 0;
			}
			
			return _tweets[_index];
		}
		
		public String getTrend(){
			if(_index >= _tsv.nRows()){
				_index = 0;
			}
			
			return _trends[_index];
		}
		
		public void incr(){
			_index++;
			
			if(_index < 0 || _index >= _tsv.nRows()){
				_index = 0;
			}
		}
		
		public void decr(){
			_index--;
			
			if(_index < 0 || _index >= _tsv.nRows()){
				_index = 0;
			}
		}
		
		
		public String getxTweet() {
			List<String> combination = new ArrayList<String>();

			String tweet = getTweet();
			String trend = getTrend();

			String msg = String.format("%s %s", tweet, trend);
			String[] sombimations =  makeCombination(msg);
			
			if (msg.length() <= 140) {// max tweet length
				combination.add(msg);
			}

			incr();
			return null;
		}
		
		public List<String> relpaceOneByOne(String str, char from, char to) {
			List<String> list = new ArrayList<String>();

			list.add(str);
			if(str != null && str.trim().length() > 0){
				while(str.indexOf((int)from) >= 0) {
					str = replaceCharAt(str, str.indexOf((int)from), to);
					System.out.println(str);
					list.add(new String(str));
				}
			}
			
			return list;
		}
		
		public String replaceCharAt(String s, int pos, char c) {
		    return s.substring(0, pos) + c + s.substring(pos + 1);
		  }
		
		public String[] makeCombination(String message) {
			String[] set0 = new String[] { message };

			List<String> set1 = relpaceOneByOne(message, ',', ';');
			set1.addAll(relpaceOneByOne(message, ',', '.'));
			set1.addAll(relpaceOneByOne(message, ',', '~'));
			String[] set_1 = set1.toArray(new String[0]);

			List<String> set2 = relpaceOneByOne(message, '-', ':');
			set2.addAll(relpaceOneByOne(message, '-', '~'));
			String[] set_2 = set2.toArray(new String[0]);

			List<String> set3 = relpaceOneByOne(message, '.', ',');
			set3.addAll(relpaceOneByOne(message, '.', ';'));
			set3.addAll(relpaceOneByOne(message, '.', '~'));
			String[] set_3 = set3.toArray(new String[0]);

			List<String> set4 = relpaceOneByOne(message, '!', '.');
			set4.addAll(relpaceOneByOne(message, '!', ';'));
			set4.addAll(relpaceOneByOne(message, '!', ','));
			String[] set_4 = set4.toArray(new String[0]);

			String[][] matrix = { { message },

			set_1,

			set_2,

			set_3,

			set_4 };

			Set<String> set = new HashSet<String>();

			for (String[] line : matrix) {
				for (String s : line) {
					set.add(s);
				}
			}

			int i = 0;
			for (String s : set) {
				System.out.printf("%s - %s\n", i, s);
				i++;
			}

			return set.toArray(new String[0]);
		}
		
//		public String[] getTweets() {
//			return _tweets;
//		}
//
//		public String[] getTrends() {
//			return _trends;
//		}
//
//		public int getIndex() {
//			return _index;
//		}
	}

	@Override
	public void reload() {
		// TODO Auto-generated method stub
		
	}
}
