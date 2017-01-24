package testing;


import org.opencv.core.Rect;

public class LeftMostRectFinder extends BestRectFinder {

	int leftMostRectPos;

	@Override
	public void checkRect(Rect rect) {
		if (bestRect == null || leftMostRectPos > rect.x) {
			leftMostRectPos = rect.x;
			bestRect = rect;
		}
	}
}
