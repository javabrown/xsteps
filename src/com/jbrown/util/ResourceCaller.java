package com.jbrown.util;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import com.jbrown.core.activity.TweetActivity;

public class ResourceCaller {
	private static ExecutorService _executor = Executors.newFixedThreadPool(10);
	
	public static TweetActivity getTweetActivity(String fileName,
			boolean isGenerateCombination, int maxGenerateCombinationCount) {
		Future<TweetActivity> future = _executor.submit(new TweetResourceCaller(fileName,
				isGenerateCombination, maxGenerateCombinationCount));
		try {
			return future.get();
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}
		
		return null;
	}
}

class TweetResourceCaller implements Callable<TweetActivity>{
	private String _fileName;
	private boolean _isGenerateCombination;
	private int _maxGenerateCombinationCount;
	
	public TweetResourceCaller(String fileName, 
			boolean isGenerateCombination, int maxGenerateCombinationCount){
		_fileName = fileName;
		_isGenerateCombination = isGenerateCombination;
		_maxGenerateCombinationCount = maxGenerateCombinationCount;
	}
	
	@Override
	public TweetActivity call() throws Exception {
		return new TweetActivity(_fileName, 
				_isGenerateCombination, _maxGenerateCombinationCount);
	}
}
