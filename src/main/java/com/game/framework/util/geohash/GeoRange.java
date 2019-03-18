package com.game.framework.util.geohash;

public class GeoRange {
	private long min;
	private long max;

	public GeoRange(long min, long max) {
		this.min = min;
		this.max = max;
	}

	public long getMin() {
		return min;
	}

	public long getMax() {
		return max;
	}
}
