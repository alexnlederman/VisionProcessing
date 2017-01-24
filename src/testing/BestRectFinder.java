package testing;


import org.opencv.core.Rect;

public abstract class BestRectFinder {
	
	Rect bestRect;
	
	public BestRectFinder() {
		bestRect = null;
	}
	
	public abstract void checkRect(Rect rect);
	
	public Rect getCurrentBestRect() {
		return bestRect;
	}
	
	public void resetRect() {
		bestRect = null;
	}
}
