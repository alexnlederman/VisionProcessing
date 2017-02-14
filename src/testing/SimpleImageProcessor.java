package testing;

import java.util.ArrayList;
import java.util.List;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.opencv.videoio.VideoCapture;

public class SimpleImageProcessor implements ImageProcessor {
	
	VideoCapture camera;
	
	
	double minArea;
	double maxArea;
	double minWidth;
	double maxWidth;
	double minHeight;
	double maxHeight;
	List<Rect> bestRects;
	static final int exposureInt = 15;
	
	Mat filteredColorImg;
	
	List<BestRectFinder> brfs;
	
	static final double[] hue = { 60, 90 };
	static final double[] saturation = { 100, 280 };
	static final double[] luminance = { 40, 120 };
	static final int defaultMinArea = 10;
	static final int defaultMaxArea = 199999;
	static final int defaultMinWidth = 5;
	static final int defaultMaxWidth = 1999999;
	static final int defaultMinHeight = 10;
	static final int defaultMaxHeight = 999999;
	
	
	public SimpleImageProcessor(VideoCapture camera, List<BestRectFinder> brf, double minArea, double maxArea, double minWidth, double maxWidth, double minHeight, double maxHeight) {
		this.camera = camera;
		this.brfs = brf;
		this.minArea = minArea;
		this.maxArea = maxArea;
		this.minWidth = minWidth;
		this.maxWidth = maxWidth;
		this.minHeight = minHeight;
		this.maxHeight = maxHeight;
		filteredColorImg = new Mat();
		
	}
	
	public SimpleImageProcessor(VideoCapture camera, List<BestRectFinder> brf) {
		this(camera, brf, SimpleImageProcessor.defaultMinArea, SimpleImageProcessor.defaultMaxArea, SimpleImageProcessor.defaultMinWidth, SimpleImageProcessor.defaultMaxWidth, SimpleImageProcessor.defaultMinHeight, SimpleImageProcessor.defaultMaxHeight);
	}
	
	public Mat processImage() {
		Mat img = new Mat();
		img = getCameraFrame();
		this.filteredColorImg = filterColors(img);
		img = this.filteredColorImg.clone();
		List<MatOfPoint> contours = getContours(img);
		List<Rect> rects = getRectsFromContours(contours);
		List<Rect> filteredRects = filterRects(rects);
		this.bestRects = findBestRect(filteredRects);
		resetBRFs();
		for (Rect bestRect : bestRects) {
			if (bestRect != null) {
				
				Imgproc.rectangle(img, bestRect.tl(), bestRect.br(), new Scalar(255, 255, 255));
			}
		}
		return img;
	}
	
	private void resetBRFs() {
		for (BestRectFinder brf : brfs) {
			brf.resetRect();
		}
	}
	
	public List<Rect> getBestRects() {
		return bestRects;
	}
	
	public Mat getFilteredImg() {
		return filteredColorImg;
	}
	
	private List<Rect> getRectsFromContours(List<MatOfPoint> contours) {
		List<Rect> rects = new ArrayList<Rect>();
		for (MatOfPoint contour : contours) {
			rects.add(Imgproc.boundingRect(contour));
		}
		return rects;
	}
	
	private List<MatOfPoint> getContours(Mat img) {
		Mat hierarchy = new Mat();
		List<MatOfPoint> contours = new ArrayList<MatOfPoint>();
		Imgproc.findContours(img, contours, hierarchy, Imgproc.RETR_LIST, Imgproc.CHAIN_APPROX_SIMPLE);
		
		return contours;
	}
	
	
	private Mat filterColors(Mat image) {
		Imgproc.cvtColor(image, image, Imgproc.COLOR_BGR2HLS);
		Mat out = new Mat();
		Core.inRange(image, new Scalar(hue[0], luminance[0], saturation[0]),
				new Scalar(hue[1], luminance[1], saturation[1]), out);
		return out;
	}
	
	
	public Mat getCameraFrame() {
		Mat image = new Mat();
		camera.read(image);
		return image;
	}
	
	
	private List<Rect> filterRects(List<Rect> rects) {
		List<Rect> out = new ArrayList<Rect>();
		for (Rect rect : rects) {
			if(this.minArea < rect.area() && this.maxArea > rect.area() && this.minWidth < rect.width && this.maxWidth > rect.width && this.minHeight < rect.height && this.maxHeight > rect.height) {
				out.add(rect);
			}
		}
		return out;
	}
	
	
	private List<Rect> findBestRect(List<Rect> rects) {
		for (Rect rect : rects) {
			for (BestRectFinder brf : brfs) {
				brf.checkRect(rect);
			}
		}
		List<Rect> bestRects = new ArrayList<Rect>();
		for (BestRectFinder brf : brfs) {
			bestRects.add(brf.getCurrentBestRect());
		}
		return bestRects;
	}
}
