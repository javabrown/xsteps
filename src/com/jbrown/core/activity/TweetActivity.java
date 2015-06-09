package com.jbrown.core.activity;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import com.jbrown.robo.KeysI;
import com.jbrown.util.StepUtil;
import com.jbrown.util.StringUtils;
import com.jbrown.util.TSV;

public class TweetActivity implements ActivityI {
	private RowTweets _rowTweets;
	private String[] _rawTweetMessages;
	private String _fileName;
	private int _index;
	
	private boolean _isGenerateCombination;
	private int _maxGenerateCombinationCount;
	
	
	public TweetActivity(String fileName,  
			boolean isGenerateCombination, int maxGenerateCombinationCount) {
		_fileName = fileName;
		_isGenerateCombination = isGenerateCombination;
		_maxGenerateCombinationCount = maxGenerateCombinationCount;
		
		reload();
		
		System.out.printf("Total Tweets: %s. Last Tweets: %s", _rawTweetMessages.length, 
				_rawTweetMessages[_rawTweetMessages.length -1]);
		
	}
	
	public static void main(String[] args){
		TweetActivity a = new TweetActivity("c:/test/tweets.tsv", true, 2);
		
		System.out.printf("Total Tweets: %s", a._rawTweetMessages.length, 
				a._rawTweetMessages[a._rawTweetMessages.length -1]);
		
		int i = 0;
		for(String s : a._rawTweetMessages){a.prepareNextClipContent();
			System.out.printf("%s - %s\n", i++, "");
			
		}
		a.getNextTweets();
	}
	
	public void reload(){
		_rowTweets = new RowTweets(_fileName);
		_rawTweetMessages  = _rowTweets.getAllTweets(_isGenerateCombination, 
				_maxGenerateCombinationCount);
	}

 
	
	public String getNextTweets() {
		 
		
		if(_index >= _rawTweetMessages.length){
			_index = 0;
		}
		
		String tweets =  _rawTweetMessages[_index];
		_index++;
		
		return tweets;
	}
	
//	public static void main(String[] arg) {
//	    String text = "To be or not to be, that is the question. Hello!!";
//		char[][] chars = new char[][]{
//				{',', ';'},
//				{',', '.'},
//				{',', '~'},
//				
//				{'-', ':'},
//				{'-', '~'},
//				
//				{'.', ','},
//				{'.', ';'},
//				{'.', '~'},
//				
//				{'!', '.'},
//				{'!', ';'},
//				{'!', ','},
//		};
//	    
//	    String newText = text.replace(' ', '/');     // Modify the string text
//	    
//	    String[] result = 
//	    		StringUtils.buildStrMatrixWithReplacementCharSet(text, chars);
//	    int i=0;
//	    for(String s: result){
//	    	System.out.printf("%s-%s\n", i++, s);
//	    }
//	   
//	}
	
	@Override
	public void prepareNextClipContent() {
		String tweet = this.getNextTweets();
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
			_tsv = new TSV(_fileName);
			_tweets = _tsv.getColumn(0);
			_trends = _tsv.getColumn(1);
			_index = 0;
		}

		public String getTweet(){
			return _tweets[_index];
		}
		
		public String getTrend(){
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
		
		public boolean isLast(){
			if(_index < 0 || _index >= (_tsv.nRows()-1)){
				 return true;
			}
			
			return false;
		}
		
		public String[] getAllTweets(boolean generateCombination, int maxMixCombinationCount) {
			System.out.printf("Total %s Tweets Loaded", _tsv.nRows());
			List<String> results = new ArrayList<String>();
			
			while(!isLast()){
				String tweet = getTweet();
				String trend = getTrend();
				
				incr();
	
				tweet = StringUtils.avoidNullString(tweet);
				trend = StringUtils.avoidNullString(trend);
				
				if(StringUtils.isEmpty(tweet)){
					continue;
				}
				
				if (generateCombination) {
					String[] tweetCombimations = StringUtils
							.buildStrMatrixWithReplacementCharSet(tweet,
									KeysI.CHAR_REPLACEMENT_MATRIX);
					int count = 0;
					
					for (String t : tweetCombimations) {
						String msg = String.format("%s %s", t, trend);
	
						if (msg.length() <= 140) {// max tweet length
							results.add(msg);
							
							if(count++ < maxMixCombinationCount){
								results.add(String.format("%s %s", tweet, trend));
								break;
							}
						}
					}
				} else {
					results.add(String.format("%s %s", tweet, trend));
				}
			}
			
			return results.toArray(new String[0]);
		}
		
//		public List<String> relpaceOneByOne(String str, char from, char to) {
//			List<String> list = new ArrayList<String>();
//
//			list.add(str);
//			if(str != null && str.trim().length() > 0){
//				while(str.indexOf((int)from) >= 0) {
//					str = replaceCharAt(str, str.indexOf((int)from), to);
//					System.out.println(str);
//					list.add(new String(str));
//				}
//			}
//			
//			return list;
//		}
//		
//		public String replaceCharAt(String s, int pos, char c) {
//		    return s.substring(0, pos) + c + s.substring(pos + 1);
//		}
		
//		public String[] makeCombination(String message) {
//		
//			
//			String[] set0 = new String[] { message };
//
//			List<String> set1 = relpaceOneByOne(message, ',', ';');
//			set1.addAll(relpaceOneByOne(message, ',', '.'));
//			set1.addAll(relpaceOneByOne(message, ',', '~'));
//			String[] set_1 = set1.toArray(new String[0]);
//
//			List<String> set2 = relpaceOneByOne(message, '-', ':');
//			set2.addAll(relpaceOneByOne(message, '-', '~'));
//			String[] set_2 = set2.toArray(new String[0]);
//
//			List<String> set3 = relpaceOneByOne(message, '.', ',');
//			set3.addAll(relpaceOneByOne(message, '.', ';'));
//			set3.addAll(relpaceOneByOne(message, '.', '~'));
//			String[] set_3 = set3.toArray(new String[0]);
//
//			List<String> set4 = relpaceOneByOne(message, '!', '.');
//			set4.addAll(relpaceOneByOne(message, '!', ';'));
//			set4.addAll(relpaceOneByOne(message, '!', ','));
//			String[] set_4 = set4.toArray(new String[0]);
//
//			String[][] matrix = { { message },
//
//			set_1,
//
//			set_2,
//
//			set_3,
//
//			set_4 };
//
//			Set<String> set = new HashSet<String>();
//
//			for (String[] line : matrix) {
//				for (String s : line) {
//					set.add(s);
//				}
//			}
//
//			int i = 0;
//			for (String s : set) {
//				System.out.printf("%s - %s\n", i, s);
//				i++;
//			}
//
//			return set.toArray(new String[0]);
//		}
		
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

	
}
