package com.jbrown.core.activity;

import com.jbrown.util.StepUtil;
import com.jbrown.util.TSV;

public class TweetActivity implements ActivityI {
	private String[] _tweets;
	private int _index;
	private TSV _tsv;
	
	public TweetActivity(String fileName) {
		_index = 0;
		_tsv = new TSV(fileName);
		_tweets = _tsv.getColumn(0);
	}
	
	public String getNextTweets(){
		if(_index >= _tsv.nRows()){
			_index = 0;
		}
		
		return _tweets[_index++];
	}
	
	@Override
	public void prepareNextClipContent() {
		String tweet = getNextTweets();
		System.out.println(tweet);
		StepUtil.setClipboardContents(tweet);
	}
}
