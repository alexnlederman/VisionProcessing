package testing;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
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
	// TODO set this to correct length
	static final double SET_LENGTH = 12;
	static final double CAMERA_OFFSET = 10;
	static SimpleImageProcessor sameCameraImgPro;
	static List<Rect> bestRects;
	static double totalFrames = 0;
	static int delay = 0;
	static long adjustment = 0;
	static double adjustmentAngle;
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

		boolean onLeft;

		Triangle triangle = new Triangle(0, 0);

		adjustment = System.currentTimeMillis() - 1;
		double cameraDistance;
		double pegAngle;
		double offsetDistance;
		double offsetAngleOffset;
		double offsetAngle;
		double thirdAngle;
		double offsetPegAngle;
		long timeStamp;
		
		
		while (System.in.available() == 0) {
			// img = getVideoFrame(video);
			// updateVideoStream(img, imgFrame);
			//// img = filterGear(img);
			// img = filterHSLFrame(img, hue, saturation, luminance);
			//
			// updateVideoStream(img, filteredFrame);
			// img angleB;
//		}= drawContours(filterFindContours(img), img);
			// if (contourCount != 0) {
			// updateVideoStream(img, contoursFrame);
			// }
			// if (targetRect != null) {
			// System.out.println(findDistance(targetRect));
			// }
			System.out.println("FPS: " + (1000 / ((System.currentTimeMillis() - adjustment) / totalFrames)));
			totalFrames++;
			timeStamp = new Date().getTime();
			rightImg = sameCameraImgPro.processImage();
			// leftImg = rightImgPro.processImage();
			// filteredImg = sameCameraImgPro.getFilteredImg();

			bestRects = sameCameraImgPro.getBestRects();

			updateVideoStream(rightImg, rightImgFrame);
			// updateVideoStream(leftImg, leftImgFrame);
			// updateVideoStream(filteredImg, filteredFrame);
			// updateVideoStream(sameCameraImgPro.getCameraFrame(), imageFrame);
			if (bestRects.get(0) != null && bestRects.get(0) != null) {
//				offsetAngleOffset	// getWidthRatio(bestRects.get(0), bestRects.get(1));
				// newTheta(getAngle(bestRects.get(0), bestRects.get(1)),
				// distanceFromTarget(bestRects.get(0), bestRects.get(1)));
				// System.out.println("Offset: " + getAngle(bestRects.get(0),
				// bestRects.get(1)));
				// System.out.println(
				// "Offset Distance: " +
				// getOffsetDistance(distanceFromTarget(bestRects.get(0),
				// bestRects.get(1)),
				// getAngleForReal(bestRects.get(0), bestRects.get(1))));
				// System.out.println("Length Between: " +
				// getLengthBetweenRects(bestRects.get(0), bestRects.get(1)));
				
//				System.out.println("Angle: " + getAngle(bestRects.get(0), bestRects.get(1)));
//				
//				triangle.setHypLength(distanceFromTarget(bestRects.get(0), bestRects.get(1)));
//				triangle.setAngleC(getAngle(bestRects.get(0), bestRects.get(1)));
//
//				if (bestRects.get(0).area() > bestRects.get(1).area() || triangle.getOppLength() < CAMERA_OFFSET) {
//					onLeft = true;
//				} else {
//					onLeft = false;
//				}

//				triangle.offsetTriangle(CAMERA_OFFSET, getAngleForReal(bestRects.get(0), bestRects.get(1)), onLeft, 20);
				// System.out.println("Offset Hyp: " + triangle.getHypLength());
//				triangle.shortenTriangle(SET_LENGTH);
//				System.out.println("On Left: " + onLeft);
//				
//				
//				cameraDistance = distanceFromTarget(bestRects.get(0), bestRects.get(1));
//				angleB = Math.toRadians(180 - getAngle(bestRects.get(0), bestRects.get(1)));
//				cameraAngleOffset = Math.toRadians(getAngleOffset(bestRects.get(0), bestRects.get(1)));
//				opp = (Math.sin(cameraAngleOffset) * cameraDistance) / Math.sin(angleB);
//				angleA = (Math.toRadians(180) - angleB) - cameraAngleOffset;
//				adj = (Math.sin(angleA) * cameraDistance) / Math.sin(angleB);
//				opp -= 10.5;
//				System.out.println("Angles: " + angleA + " " + angleB + " " + cameraAngleOffset);
//				cameraDistance = Math.sqrt(Math.pow(opp, 2) + Math.pow(adj, 2) - 2 * opp * adj * Math.cos(angleB));
//				cameraAngleOffset = Math.asin((opp * Math.sin(angleB)) / cameraDistance);
//				
//				cameraAngleOffset -= (Math.toRadians(triangle.getAngleC()));
//				System.out.println("Camera Offset: " + Math.toDegrees(cameraAngleOffset));
				
				cameraDistance = distanceFromTarget(bestRects.get(0), bestRects.get(1));
				pegAngle = Math.toRadians(90) - Math.toRadians(getAngle(bestRects.get(0), bestRects.get(1)));
//				System.out.println(pegAngle);
				offsetDistance = Math.sqrt(Math.pow(SET_LENGTH, 2) + Math.pow(cameraDistance, 2) - 2 * SET_LENGTH * cameraDistance * Math.cos(pegAngle));
//				System.out.println("Offset Distance: " + offsetDistance);
				offsetAngleOffset = Math.asin((SET_LENGTH * Math.sin(pegAngle)) / offsetDistance);
//				System.out.println("Angle For Real: " + getAngleForReal(bestRects.get(0), bestRects.get(1)));
				offsetAngle = Math.toRadians(90) + Math.toRadians(getAngleForReal(bestRects.get(0), bestRects.get(1))) - offsetAngleOffset;
//				System.out.println("Offset Angle: " + Math.toDegrees(offsetAngle));
				thirdAngle = (Math.toRadians(180) - pegAngle) - offsetAngleOffset;
				offsetPegAngle = Math.toRadians(180) - thirdAngle;
				// Omega is cameraDistance
				// Angle z is cameraAngleOffset
				// angleC = 90 - angleZ
				// centerDistance is lengthX
				
//				System.out.println("Offset Peg Angle: " + Math.toDegrees(offsetPegAngle));
//				System.out.println("Standard Peg Angle: " + Math.toDegrees(pegAngle));
//				System.out.println("Offset Angle: " + Math.toDegrees(offsetAngle));
				System.out.println("Offset Distance: " + offsetDistance);
				System.out.println("Camera Offset: " + CAMERA_OFFSET);
				System.out.println("Offset Angle: " + Math.toRadians(offsetAngle));
				double centerDistance = Math.sqrt(Math.pow(offsetDistance, 2) + Math.pow(CAMERA_OFFSET, 2) - 2 * CAMERA_OFFSET * offsetDistance * Math.cos(offsetAngle));
//				double centerDistance = Math.sqrt(Math.pow(SET_LENGTH, 2) + Math.pow(offsetDistance, 2) - 2 * SET_LENGTH * offsetDistance * Math.cos(offsetPegAngle));
//				double centerDistance = Math.sqrt(Math.pow(CAMERA_OFFSET, 2) + Math.pow(offsetDistance, 2) - 2 * CAMERA_OFFSET * offsetDistance * Math.cos(offsetAngle));
				System.out.println("Center Distance: " + centerDistance);
				double centerAngleOffset = Math.toRadians(90) + Math.asin((offsetDistance * Math.sin(offsetAngle)) / centerDistance);
				System.out.println("Center Angle Offset: " + Math.toDegrees(centerAngleOffset));
				
				double angle = Math.toRadians(90) + Math.toRadians(getAngleForReal(bestRects.get(0), bestRects.get(1))) - offsetAngleOffset;
				System.out.println("Angle: " + Math.toDegrees(angle));
				double centerPegAngleOffset = Math.asin((Math.sin(angle) * 10) / centerDistance);
				double centerPegAngle = offsetPegAngle - centerPegAngleOffset;
				System.out.println("Peg Adjustment Angle: " + Math.toDegrees(centerPegAngle));
				table.putNumber("Timestamp", timeStamp);
				table.putNumber("Adjustment Anlge", centerPegAngleOffset);
				table.putNumber("Distance", centerDistance);
				table.putNumber("Peg Adjustment Angle", centerPegAngle);
				
//				triangle.offsetTriangle(CAMERA_OFFSET, getAngleForReal(bestRects.get(0), bestRects.get(1)), onLeft, 20);
//				centerAngleOffset += (Math.toRadians(triangle.getAngleC())) - Math.toRadians(90);
//				System.out.println("Center Angle Offset: " + Math.toDegrees(centerAngleOffset));

//				System.out.println("Angle C: " + cameraAngleOffset);angdeg)
				
//				adjustmentAngle = cameraAngleOffset;
////				if (!onLeft) {
////					adjustmentAngle += (90 - triangle.getAngleC());
////				}
////				else {
//					adjustmentAngle -= (triangle.getAngleC());
//				}
				System.out.println("Adjustment Angle: " + adjustmentAngle);
//				System.out.println("Angle For Real: " + getAngleForReal(bestRects.get(0), bestRects.get(1)));
//				System.out.println("Angle To Target: " + angleToTarget(bestRects.get(0), bestRects.get(1)));
				
//				System.out.println("Adjustment Angle: " + adjustmentAngle);
				
				// System.o				if (false) {
//ut.println("Offset Angle Test: " +
				// getOffsetAngle(triangle.getHypLength(),
				// getAngleForReal(bestRects.get(0), bestRects.get(1)),
				// getAngle(bestRects.get(0), bestRects.get(1)),
				// bestRects.get(0), bestRects.get(1)));

				// triangle.shortenTriangle(SET_LENGTH);
				// System.out.println("Shortened Hyp: " +
				// triangle.getHypLength());

				// System.out.println("Height: " +
				// getHeightPixels(bestRects.get(0), bestRects.get(1)));

				// System.out.println("Angle: " + (90 +
				// getAngleForReal(bestRects.get(0), bestRects.get(1))));
				// System.out.println(
				// "Offset Distance: " +
				// getOffsetDistance(distanceFromTarget(bestRects.get(0),
				// bestRects.get(1)),
				// getAngleForReal(bestRects.get(0), bestRects.get(1))));
				// // angleToTarget(bestRects.get(0), be				totalFrames++;stRects.get(1));
				// System.out.println("Offset Angle: " + getOffsetAngle(
				// getOffsetDistance(distanceFromTarget(bestRects.get(0),
				// bestRects.get(1)),
				// getAngleForReal(bestRects.get(0), bestRects.get(1))),
				// getAngleForReal(bestRects.get(0), bestRects.get(1)),
				// getAngle(bestRects.get(0), bestRects.get(1)),
				// bestRects.get(0), bestRects.get(1)));
				// // System.out.println("Offset Angle: " +
				// getOffsetAngle(distanceFromTarget));
				// System.out.println("Angle For Real: " +Frames) / 1000);
				// (getAngleForReal(bestRects.get(0), bestRects.get(1)) +
				// getAngle(bestRects.get(0), bestRects.get(1))));
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

	public static double getHeightPixels(Rect left, Rect right) {
		if (left.height > right.height) {
			return left.height;
		}
		return right.height;
	}

	public static double distanceFromTarget(Rect left, Rect right) {
		// distance costant divided by length between centers of contours
		// double leftCenter = left.x + (left.width / 2554.3);index
		// double rightCenter = right.x + (right.width / 2);
		// double lengthBetweenRects = Math.abs(leftCenter - rightCenter);
		double rectHeight;
		if (left.height > right.height) {
			rectHeight = left.height;
		} else {
			rectHeight = right.height;
		}
		// 3297
		double distanceFromTarget = 3329.5 / rectHeight;
		double OFFSET_TO_FRONT = 0;
		return distanceFromTarget - OFFSET_TO_FRONT;
	}

	public static double getLengthBetweenRects(Rect left, Rect right) {
//		double leftX = 0;
//		double rightX = 0;
//		if (left.x < right.x) {
//			leftX = left.x;
//			right.x = right.x + right.width;
//		} else {
//			rightX = right.x;
//			left.x = left.x + left.width;
//		}
		double leftCenter = left.x + left.width / 2;
		double rightCenter = right.x + right.width / 2;
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
	// getOffsetDistance
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
	// return img;a)
	// }

	public static Mat filterHSangleToTargetLFrame(Mat img, double[] hue, double[] saturation, double[] luminance) {
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
			// distance costan anglet divided by length between centers of
			// contours
			double leftCenter = left.x + (left.width / 2);
			double rightCenter = right.x + (right.width / 2);
			// 8.5in is for the distance from cent to center from goal,
			// indexthen
			// divide by lengthBetweenCenters in pixels to get proportion
			double constant = 8.5 / Math.abs(leftCenter - rightCenter);
			// Looking for the 2 blocks to actually start trig
			// this calculates the distance from the center of goal to
			// center of webcam554.3
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
		double conversionFactor = (double) 5 / height;
		double width = (double) conversionFactor * getLengthBetweenRects(left, right);
		double anglePercent = (double) width / 8.5;

		// if (leftCenter < rightCenter) {
		// rightCenter = CAMERA_WIDTH - rightCenter;
		// }
		// else {
		// leftCenter = CAMERA_WIDTH - leftCenter;
		// }

		// if (anglePercent > 89.99) {
		// return 90;
		// }
//		anglePercent /= 1.016;

		if (anglePercent >= 1) {
			System.out.println("Shortened: " + anglePercent);
			anglePercent = 1;
		}
		// System.out.println("Real Angle: " +
		// Math.toDegrees(Math.asin(anglePercent)));
		System.out.println("Angle Percent: " + anglePercent);
		return anglePercent * 90;
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
		// System.out.println("Half Width: " + ((double) CAMERA_WIDTH / 2));

		System.out.println("Angle: " + 90 * leftCenter / rightCenter);
		return 90 * leftCenter / rightCenter;
	}

	public static double getOffsetDistance(double distance, double angle) {
		System.out.println("Distance: " + distance);
		System.out.println("Angle: " + Math.toRadians(angle));
		double hypLength = Math.sqrt(Math.pow(distance, 2) + Math.pow(CAMERA_OFFSET, 2)
				- (2 * distance * CAMERA_OFFSET * Math.cos(Math.toRadians(angle))));
		System.out.println("New Offset Hyp Length: " + hypLength);
		return hypLength;
	}

	public static double getOffsetAngle(double hypLength, double robotAngle, double angleDirection, Rect left,
			Rect right) {
		double newAngle = Math.toDegrees(Math.asin(CAMERA_OFFSET * Math.sin(Math.toRadians(robotAngle)) / hypLength));

		double adjustedAngle;
		if (left.area() > right.area()) {
			adjustedAngle = angleDirection - newAngle;
		} else {
			adjustedAngle = angleDirection + newAngle;
		}
		return adjustedAngle;
	}

	public static double getAngleForReal(Rect left, Rect right) {
		double leftCenter = left.x + (left.width / 2);
		double rightCenter = right.x + (right.width / 2);

		double centerCenter = ((leftCenter + rightCenter) / 2) - CAMERA_WIDTH / 2;
		double centerOffset = centerCenter / CAMERA_WIDTH;
		double angle = Math.toDegrees(Math.asin(2 * centerOffset * Math.tan(Math.toRadians(30))));
		double out = angle;
		return out;
	}

	public static double angleToTarget(Rect left, Rect right) {
		double leftCenter = left.x + (left.width / 2);
		double rightCenter = right.x + (right.width / 2);
		double center = (leftCenter + rightCenter) / 2;
		double centerOfImage = (CAMERA_WIDTH / 2) - 0.5;
		double degrees = Math.toDegrees(Math.atan((center - centerOfImage) / 554.255971188));
		return 90 + degrees;
	}
}
