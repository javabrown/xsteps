package com.jbrown.core.activity;

import com.jbrown.util.StepUtil;
import com.jbrown.util.TSV;

public class TweetActivity implements ActivityI {
	private String[] _tweets;
	private String[] _trends;
	private int _index;
	private TSV _tsv;
	private String _fileName;
	
	public TweetActivity(String fileName) {
		_fileName = fileName;
		this.reload();
	}
	
	public void reload(){
		_index = 0;
		_tsv = new TSV(_fileName);
		_tweets = _tsv.getColumn(0);
		_trends = _tsv.getColumn(1);
	}
	
	public String getNextTweets(){
		if(_index >= _tsv.nRows()){
			_index = 0;
		}
		
		String tweets =  _tweets[_index] + " " + _trends[_index];
		_index++;
		
		return tweets;
	}
	
	@Override
	public void prepareNextClipContent() {
		String tweet = getNextTweets();
		System.out.println(tweet);
		StepUtil.setClipboardContents(tweet);
	}
}
