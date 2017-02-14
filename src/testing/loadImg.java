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
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.videoio.VideoCapture;

import edu.wpi.first.wpilibj.networktables.NetworkTable;

//import edu.wpi.first.wpilibj.networktables.NetworkTable;

public class loadImg {

	static int contourCount;
	static final int filterAreaSize = 100;
	static final int maxWidth = 100;
	static final int expectedHeight = 280;
	static final int CAMERA_WIDTH = 640;
	static final double SET_LENGTH = 10.5;
	static final double CAMERA_OFFSET = 15;
	static SimpleImageProcessor sameCameraImgPro;
	static List<Rect> bestRects;
	// static SimpleImageProcessor rightImgPro;

	public static void main(String[] args) throws IOException {
		System.out.println(System.getProperty("java.library.path"));
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

		Runtime.getRuntime().exec("v4l2-ctl -c exposure_auto=1 -c exposure_absolute=2");

		List<BestRectFinder> brfs = new ArrayList<BestRectFinder>();
		brfs.add(new LeftMostRectFinder());
		brfs.add(new rightMostRectFinder());
		NetworkTable.setClientMode();
		NetworkTable table = NetworkTable.getTable("Vision");

		table.putNumber("Test Value", 8);
		System.out.println(table.getNumber("Test Value", 0));
		System.out.println("Connected: " + table.isConnected());

		sameCameraImgPro = new SimpleImageProcessor(new VideoCapture(0), brfs);
		// rightImgPro = new SimpleImageProcessor(new VideoCapture(0), new
		// rightMostRectFinder());

		Mat rightImg = sameCameraImgPro.processImage();
		// Mat leftImg = rightImgPro.processImage();
		// Mat filteredImg = sameCameraImgPro.getaFilteredImg();

		// JFrame leftImgFrame = displayMat(leftImg);
		JFrame rightImgFrame = displayMat(rightImg);
		// JFrame filteredFrame = displayMat(sameCameraImgPro.getFilteredImg());
		// JFrame imageFrame = displayMat(sameCameraImgPro.getCameraFrame());

		while (System.in.available() == 0) {
			// img = getVideoFrame(video);
			// updateVideoStream(img, imgFrame);
			//// img = filterGear(img);
			// img = filterHSLFrame(img, hue, saturation, luminance);
			//
			// updateVideoStream(img, filteredFrame);
			// img = drawContours(filterFindContours(img), img);
			// if (contourCount != 0) {
			// updateVideoStream(img, contoursFrame);
			// }
			// if (targetRect != null) {
			// System.out.println(findDistance(targetRect));
			// }

			rightImg = sameCameraImgPro.processImage();
			// leftImg = rightImgPro.processImage();
			// filteredImg = sameCameraImgPro.getFilteredImg();

			bestRects = sameCameraImgPro.getBestRects();

			updateVideoStream(rightImg, rightImgFrame);
			// updateVideoStream(leftImg, leftImgFrame);
			// updateVideoStream(filteredImg, filteredFrame);
			// updateVideoStream(sameCameraImgPro.getCameraFrame(), imageFrame);
			if (bestRects.get(0) != null && bestRects.get(0) != null) {
//				getWidthRatio(bestRects.get(0), bestRects.get(1));
//				newTheta(getAngle(bestRects.get(0), bestRects.get(1)),
//						distanceFromTarget(bestRects.get(0), bestRects.get(1)));
//				System.out.println("Offset: " + getAngle(bestRects.get(0), bestRects.get(1)));
//				System.out.println(
//						"Offset Distance: " + getOffsetDistance(distanceFromTarget(bestRects.get(0), bestRects.get(1)),
//								getAngleForReal(bestRects.get(0), bestRects.get(1))));
				// System.out.println("Length Between: " +
				// getLengthBetweenRects(bestRects.get(0), bestRects.get(1)));
				System.out.println("Angle: " + (90 + getAngleForReal(bestRects.get(0), bestRects.get(1))));
//				System.out.println("Angle For Real: " + (getAngleForReal(bestRects.get(0), bestRects.get(1)) + getAngle(bestRects.get(0), bestRects.get(1))));
				// System.out.println("Angle Offset To Goal: " +
				// getAngleOffset(bestRects.get(0), bestRects.get(1)));
				// System.out.println("Angle Towards Goal: " +
				// getAngle(bestRects.get(0), bestRects.get(1)));
				// System.out.println("Distance: " +
				// distanceFromTarget(bestRects.get(0), bestRects.get(1)));
				// System.out.println("New Distance: " +
				// newHyp(getAngle(bestRects.get(0), bestRects.get(1)),
				// distanceFromTarget(bestRects.get(0), bestRects.get(1))));
				// System.out.println("New Angle: " +
				// newTheta(getAngle(bestRects.get(0), bestRects.get(1)),
				// distanceFromTarget(bestRects.get(0), bestRects.get(1))));
			}
		}
	}

	public static double distanceFromTarget(Rect left, Rect right) {
		// distance costant divided by length between centers of contours
		// double leftCenter = left.x + (left.width / 2);index
		// double rightCenter = right.x + (right.width / 2);
		// double lengthBetweenRects = Math.abs(leftCenter - rightCenter);
		double rectHeight;
		if (left.height > right.height) {
			rectHeight = left.height;
		} else {
			rectHeight = right.height;
		}
		double distanceFromTarget = 3375 / rectHeight;
		double OFFSET_TO_FRONT = 0;
		return distanceFromTarget - OFFSET_TO_FRONT;
	}

	public static double getLengthBetweenRects(Rect left, Rect right) {
		double leftCenter = left.x + (left.width / 2);
		double rightCenter = right.x + (right.width / 2);
		double lengthBetweenRects = Math.abs(leftCenter - rightCenter);
		return lengthBetweenRects;
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

	// public static Mat drawContours(List<MatOfPoint> contours, Mat img) {
	// Scalar color = new Scalar(255, 255, 0, 0);index
	//
	// smallItems = new ArrayList<Rect>();
	// bigItems = new ArrayList<Rect>();
	//
	// double currentDifference = Double.MAX_VALUE;
	// targetRect = null;
	// for (MatOfPoint contour : contours) {
	// Rect rect = Imgproc.boundingRect(contour);
	// if (rect.area() > filterAreaSize && rect.width < maxWidth) {
	// if (Math.abs((rect.height/rect.width) - smallExpectedHeightWidthRatio)
	// 1280<
	// Math.abs((rect.height/rect.width) - bigExpectedHeightWidthRatio)) {
	// smallItems.add(rect);
	// }
	// else {
	// bigItems.add(rect);
	// }
	// targetRect = rect;
	// }
	// }index
	//
	//
	// if (targetRect != null) {
	// System.out.println("Rect Height: " + targetRect.height);
	// System.out.println("Rect Width: " + targetRect.width);
	// Core.rectangle(img, targetRect.tl(), targetRect.br(), color);
	// }
	// return img;
	// }

	public static Mat filterHSLFrame(Mat img, double[] hue, double[] saturation, double[] luminance) {
		Imgproc.cvtColor(img, img, Imgproc.COLOR_BGR2HLS);
		Core.inRange(img, new Scalar(hue[0], luminance[0], saturation[0]), new Scalar(hue[1], luminance[1], saturation[1]), img);
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
		Imgcodecs.imencode(".jpg", mat, matOfByte);
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
		Imgcodecs.imencode(".jpg", mat, matOfByte);
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

	public static double getAngleOffset(Rect left, Rect right) {
		double angleToGoal = 0;
		if (left != null && right != null) {
			// distance costant divided by length between centers of contours
			double leftCenter = left.x + (left.width / 2);
			double rightCenter = right.x + (right.width / 2);
			// 8.5in is for the distance from cent to center from goal,
			// indexthen
			// divide by lengthBetweenCenters in pixels to get proportion
			double constant = 8.5 / Math.abs(leftCenter - rightCenter);
			// Looking for the 2 blocks to actually start trig
			// this calculates the distance from the center of goal to
			// center of webcam
			double distanceFromCenterPixels = ((leftCenter + rightCenter) / 2) - (CAMERA_WIDTH / 2);
			// Converts pixels to inches using the constant from above.
			double distanceFromCenterInch = distanceFromCenterPixels * constant;
			// math brought to you buy Chris and Jones
			angleToGoal = Math.atan(distanceFromCenterInch / distanceFromTarget(left, right));
			angleToGoal = Math.toDegrees(angleToGoal);
			// prints angle
			// System.out.println("Angle: " + angleToGoal);
		}
		return angleToGoal;
	}

	public static double getAngle(Rect left, Rect right) {
		double height;
		if (left.height > right.height) {
			height = left.height;
		} else {
			height = right.height;
		}
		double conversionFactor = 5 / height;
		double width = conversionFactor * getLengthBetweenRects(left, right);
		double anglePercent = width / 8.5;

		// if (leftCenter < rightCenter) {
		// rightCenter = CAMERA_WIDTH - rightCenter;
		// }
		// else {
		// leftCenter = CAMERA_WIDTH - leftCenter;
		// }

		if (anglePercent > 89.99) {
			return 90;
		}
		return Math.toDegrees(Math.asin(anglePercent));
	}

	public static double newHyp(double currentTheta, double currentHyp) {
		if (currentTheta > 90) {
			currentTheta = currentTheta - ((currentTheta - 90) * 2);
			System.out.println(currentTheta);
		}
		return Math.sqrt(Math.pow(((Math.tan(currentTheta) * Math.cos(currentTheta) * currentHyp) - SET_LENGTH), 2)
				- Math.pow(Math.cos(currentTheta) * currentHyp, 2));
	}

	public static double newTheta(double currentTheta, double currentHyp) {
		currentTheta = Math.toRadians(currentTheta);
		currentHyp = Math.abs(currentHyp);
		double currentOpp = currentHyp * Math.sin(currentTheta);
		double currentAdj = Math.sqrt(Math.pow(currentHyp, 2) - Math.pow(currentOpp, 2));
		double newOpp = currentOpp - SET_LENGTH;
		double newHyp = Math.sqrt(Math.pow(currentAdj, 2) + Math.pow(newOpp, 2));
		double newTheta = Math.toDegrees(Math.asin(newOpp / newHyp));
		System.out.println("New Theta: " + newTheta);
		System.out.println("New Hyp: " + newHyp);
		return newTheta;
	}

	public static double getWidthRatio(Rect left, Rect right) {
		// double ratio = (double) left.width / right.width;
		// System.out.println("Ratio: " + 90 * ratio);
		// return 90 * ratio;
		double leftCenter = left.x + (left.width / 2);
		double rightCenter = right.x + (right.width / 2);
		if (leftCenter > rightCenter) {
			leftCenter = CAMERA_WIDTH - leftCenter;
		} else {
			rightCenter = CAMERA_WIDTH - rightCenter;
		}
//		System.out.println("Half Width: " + ((double) CAMERA_WIDTH / 2));
		
		System.out.println("Angle: " + 90 * leftCenter / rightCenter);
		return 90 * leftCenter / rightCenter;
	}

	public static double getOffsetDistance(double distance, double angle) {
		double hypLength = Math.sqrt(
				Math.pow(distance, 2) + Math.pow(CAMERA_OFFSET, 2) - (2 * distance * CAMERA_OFFSET * Math.cos(angle)));
		System.out.println("New Offset Hyp Length: " + hypLength);
		return hypLength;
	}

	public static double getOffsetAngle(double hypLength, double robotAngle, double angleDirection) {
		double newAngle = Math.toDegrees(Math.asin((CAMERA_OFFSET * Math.sin(robotAngle)) / hypLength));
		double adjustedAngle = angleDirection - newAngle;
		System.out.println("New Offset Angle: " + adjustedAngle);
		return adjustedAngle;
	}
	
	public static double getAngleForReal(Rect left, Rect right) {
		double leftCenter = left.x + (left.width / 2);
		double rightCenter = right.x + (right.width / 2);
		
		double centerCenter = ((leftCenter + rightCenter) / 2) - CAMERA_WIDTH / 2;
		double centerOffset = centerCenter / CAMERA_WIDTH;
		double angle = Math.toDegrees(Math.asin(2 * centerOffset * Math.tan(Math.toRadians(30))));
		return angle;
	}
	
	public static double angleToTarger(Rect left, Rect right) {
		double leftCenter = left.x + (left.width / 2);
		double rightCenter = right.x + (right.width / 2);
		double center = (leftCenter + rightCenter) / 2;
		double centerOfImage = (CAMERA_WIDTH / 2) - 0.5;
		Math.atan(())
	}
}
