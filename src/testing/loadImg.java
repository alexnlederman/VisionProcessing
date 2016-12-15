package testing;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.highgui.Highgui;
import org.opencv.highgui.VideoCapture;
import org.opencv.imgproc.Imgproc;

public class loadImg {

	static int contourCount;
	static final int filterAreaSize = 1000;
	static final int maxWidth = 100;
	static final int expectedHeight = 280;
	static final int blur = 10;
	static final double expectedHeightWidthRatio = 4.23;
	static Rect targetRect; 
	
	public static void main(String[] args) throws IOException {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		
		targetRect = new Rect();
		Mat img = new Mat(); // Highgui.imread("/Users/BertTurtle/workspace/OpenCVTest1/src/test_image.jpg");

		VideoCapture video = new VideoCapture(0);
		img = getVideoFrame(video);
		JFrame imgFrame = displayMat(img);
		JFrame filteredFrame = displayMat(img);
		JFrame contoursFrame = displayMat(img);

		// double[] hue = { 45, 45 };
		// double[] saturation = { 64, 64 };
		// double[] luminance = { 251, 251 };
		double[] hue = { 35, 45 };
		double[] saturation = { 60, 64 };
		double[] luminance = { 251, 251 };

		while (System.in.available() == 0) {
			img = getVideoFrame(video);
			Imgproc.blur( img, img, new Size(blur, blur));
			updateVideoStream(img, imgFrame);
			img = filterHSLFrame(img, hue, saturation, luminance);
			updateVideoStream(img, filteredFrame);
			img = drawContours(filterFindContours(img), img);
			if (contourCount != 0) {
				updateVideoStream(img, contoursFrame);
			}
		}

		// Mat output = img;
		// displayMat(img);
		//// Imgproc.resize(img, img, new Size(640, 480));
		// Imgproc.cvtColor(img, output, Imgproc.COLOR_BGR2HLS);
		// displayMat(output);
		//
		// double[] hslThresholdHue = {47, 180};t
		// double[] hslThresholdSaturation = {228, 255};
		// double[] hslThresholdLuminance = {32, 255};
		// Core.inRange(output, new Scalar(hslThresholdHue[0],
		// hslThresholdLuminance[0], hslThresholdSaturation[0]),
		// new Scalar(hslThresholdHue[1], hslThresholdLuminance[1],
		// hslThresholdSaturation[1]), output);
		//
		// displayMat(output);
		// List<MatOfPoint> contours = new ArrayList<MatOfPoint>();
		// Imgproc.findContours(output, contours, new Mat(), Imgproc.RETR_LIST,
		// Imgproc.CHAIN_APPROX_SIMPLE);
		//
		// List<MatOfPoint> newContours = contours;
		// for (int i = 0; i < contours.size(); i++) {
		// MatOfPoint contour = contours.get(i);
		// System.out.println(contour.width());
		// System.out.println(contour.width() * contour.height());
		// if (contour.width() * contour.height() < 1000) {
		// newContours.remove(contours.indexOf(contour));
		// }
		// }
		// System.out.println(newContours.size());
	}

	public static List<MatOfPoint> filterFindContours(Mat img) {

		Mat hierarchy = new Mat();
		List<MatOfPoint> contours = new ArrayList<MatOfPoint>();

		// Imgproc.cvtColor(img, img, Imgproc.COLOR_HLS2BGR );
		// Imgproc.cvtColor(img, img, Imgproc.COLOR_BGR2GRAY);
		// Amount of Blur - TODO
		// Imgproc.blur( img, img, new Size(3,3));
		// Imgproc.Canny(img, img, 0, 200);
		Imgproc.findContours(img, contours, hierarchy, Imgproc.RETR_LIST, Imgproc.CHAIN_APPROX_SIMPLE);
		contourCount = contours.size();

		// System.out.println("Contour Count: " + contours.size());
		return contours;
	}

	public static Mat drawContours(List<MatOfPoint> contours, Mat img) {
		// for (MatOfPoint point : contours) {
		// System.out.println("Looped");
		Scalar color = new Scalar(255, 255, 0, 0);
		// if(!contours.isEmpty()){
		// for(int i = 0; i<contours.size(); i++){
		// Imgproc.drawContours(img, contours, i, color);
		// }
		// }
		// }

		// contourCount = 0;
		double currentDifference = 1000;
		targetRect = null;
		for (MatOfPoint contour : contours) {
			Rect rect = Imgproc.boundingRect(contour);
			if (rect.area() > filterAreaSize && rect.width < maxWidth && Math.abs((rect.height/rect.width) - expectedHeightWidthRatio) < currentDifference) {
				targetRect = rect;
				currentDifference = Math.abs((rect.height/rect.width) - expectedHeightWidthRatio);
			}
			// System.out.println("Contour X-Pos: " + contour.);
		}
		if (targetRect != null) {
			System.out.println("Rect Height: " + targetRect.height);
			System.out.println("Rect Width: " + targetRect.width);
			Core.rectangle(img, targetRect.tl(), targetRect.br(), color);
		}
		return img;
	}

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
