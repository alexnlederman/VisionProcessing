package testing;


import org.opencv.core.Rect;

public class rightMostRectFinder extends BestRectFinder {
	
	int rightMostRectPos;
	
	@Override
	public void checkRect(Rect rect) {
		if (bestRect == null || rightMostRectPos < rect.x) {
			rightMostRectPos = rect.x;
			bestRect = rect;
		}
	}
}
