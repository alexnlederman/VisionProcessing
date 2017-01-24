package testing;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.highgui.Highgui;
import org.opencv.highgui.VideoCapture;
import org.opencv.imgproc.Imgproc;

public class loadImg {

	static int contourCount;
	static final int filterAreaSize = 100;
	static final int maxWidth = 100;
	static final int expectedHeight = 280;
	static SimpleImageProcessor sameCameraImgPro;
//	static SimpleImageProcessor rightImgPro;
	
	public static void main(String[] args) throws IOException {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		
		List<BestRectFinder> brfs = new ArrayList<BestRectFinder>();
		brfs.add(new LeftMostRectFinder());
		brfs.add(new rightMostRectFinder());
		
		sameCameraImgPro = new SimpleImageProcessor(new VideoCapture(0), brfs);
//		rightImgPro = new SimpleImageProcessor(new VideoCapture(0), new rightMostRectFinder());
		
		Mat rightImg = sameCameraImgPro.processImage();
//		Mat leftImg = rightImgPro.processImage();
		Mat filteredImg = sameCameraImgPro.getFilteredImg();
		
//		JFrame leftImgFrame = displayMat(leftImg);
		JFrame rightImgFrame = displayMat(rightImg);
		JFrame filteredFrame = displayMat(sameCameraImgPro.getFilteredImg());
		JFrame imageFrame = displayMat(sameCameraImgPro.getCameraFrame());
		
		while (System.in.available() == 0) {
//			img = getVideoFrame(video);
//			updateVideoStream(img, imgFrame);
////			img = filterGear(img);
//			img = filterHSLFrame(img, hue, saturation, luminance);
//			
//			updateVideoStream(img, filteredFrame);
//			img = drawContours(filterFindContours(img), img);
//			if (contourCount != 0) {
//				updateVideoStream(img, contoursFrame);
//			}
//			if (targetRect != null) {
//				System.out.println(findDistance(targetRect));
//			}
			
			rightImg = sameCameraImgPro.processImage();
//			leftImg = rightImgPro.processImage();
			filteredImg = sameCameraImgPro.getFilteredImg();
			
			
			updateVideoStream(rightImg, rightImgFrame);
//			updateVideoStream(leftImg, leftImgFrame);
			updateVideoStream(filteredImg, filteredFrame);
			updateVideoStream(sameCameraImgPro.getCameraFrame(), imageFrame);
		}
	}
	
	public static Mat filterGear(Mat img) {
		Imgproc.cvtColor(img, img, Imgproc.COLOR_BGR2HSV);
		Core.inRange(img, new Scalar(15, 70, 200), new Scalar(32, 168, 285), img);
		return img;
	}
	
	public static double findDistance(Rect rect) {
		int height = rect.height;

		double distance = 4.12792 + (11727.809844176 / height);
		return distance;
	}

	public static List<MatOfPoint> filterFindContours(Mat img) {
		Mat hierarchy = new Mat();
		List<MatOfPoint> contours = new ArrayList<MatOfPoint>();

		Imgproc.findContours(img, contours, hierarchy, Imgproc.RETR_LIST, Imgproc.CHAIN_APPROX_SIMPLE);
		contourCount = contours.size();

		return contours;
	}

//	public static Mat drawContours(List<MatOfPoint> contours, Mat img) {
//		Scalar color = new Scalar(255, 255, 0, 0);
//		
//		smallItems = new ArrayList<Rect>();
//		bigItems = new ArrayList<Rect>();
//		
//		double currentDifference = Double.MAX_VALUE;
//		targetRect = null;
//		for (MatOfPoint contour : contours) {
//			Rect rect = Imgproc.boundingRect(contour);
//			if (rect.area() > filterAreaSize && rect.width < maxWidth) {
//				if (Math.abs((rect.height/rect.width) - smallExpectedHeightWidthRatio) < Math.abs((rect.height/rect.width) - bigExpectedHeightWidthRatio)) {
//					smallItems.add(rect);
//				}
//				else {
//					bigItems.add(rect);
//				}
//				targetRect = rect;
//			}
//		}
//		
//		
//		if (targetRect != null) {
//			System.out.println("Rect Height: " + targetRect.height);
//			System.out.println("Rect Width: " + targetRect.width);
//			Core.rectangle(img, targetRect.tl(), targetRect.br(), color);
//		}
//		return img;
//	}

	public static Mat filterHSLFrame(Mat img, double[] hue, double[] saturation, double[] luminance) {
		Imgproc.cvtColor(img, img, Imgproc.COLOR_BGR2HLS);
		Core.inRange(img, new Scalar(hue[0], luminance[0], saturation[0]),
				new Scalar(hue[1], luminance[1], saturation[1]), img);
		return img;
	}

	public static Mat getVideoFrame(VideoCapture video) {
		Mat tempImg = new Mat();
		Boolean ret = video.read(tempImg);
		if (ret) {
			return tempImg;
		} else {
			System.out.println("Video Failed");
			return null;
		}
	}

	public static void updateVideoStream(Mat img, JFrame imgFrame) {
		updateMatJFrame(img, imgFrame);
	}

	public static JFrame displayMat(Mat mat) {

		MatOfByte matOfByte = new MatOfByte();
		Highgui.imencode(".jpg", mat, matOfByte);
		byte[] byteArray = matOfByte.toArray();
		BufferedImage bufImage = null;
		try {
			InputStream in = new ByteArrayInputStream(byteArray);
			bufImage = ImageIO.read(in);
			JFrame frame = new JFrame();
			frame.getContentPane().add(new JLabel(new ImageIcon(bufImage)));
			frame.pack();
			frame.setVisible(true);
			return frame;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static void updateMatJFrame(Mat mat, JFrame frame) {
		MatOfByte matOfByte = new MatOfByte();
		Highgui.imencode(".jpg", mat, matOfByte);
		byte[] byteArray = matOfByte.toArray();
		BufferedImage bufImage = null;
		try {
			InputStream in = new ByteArrayInputStream(byteArray);
			bufImage = ImageIO.read(in);
			frame.getContentPane().removeAll();
			frame.getContentPane().add(new JLabel(new ImageIcon(bufImage)));
			frame.pack();
			frame.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
