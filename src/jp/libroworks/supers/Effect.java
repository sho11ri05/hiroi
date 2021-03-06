package jp.libroworks.supers;

import jp.libroworks.GraphicsInfo;

public class Effect {

	public static double linear(
			GraphicsInfo ginfo, long duration, double start, double end)
	{
		long t = ginfo.currenttime % duration;
		double td = (double)t / (double)duration;
		return (end - start)*td + start;
	}
}
