package testing;

public class Triangle {

	// Peg
	// |\
	// |b\
	// |  \
	// |   \
	// |	\Hyp
	// Adj	 \
	// | 	  \
	// |	   \
	// |a______c\
	// Opp Camera

	private double hypLength;
	private double oppLength;
	private double adjLength;

	private double angleB;
	private double angleC;
	private double angleA;

	public Triangle(double hypLength, double angleC) {
		this.hypLength = hypLength;
		this.angleC = Math.toRadians(angleC);

		recalculateAngles();
	}

	private void recalculateAngles() {
		this.angleA = Math.toRadians(90);
		this.angleB = Math.toRadians(90) - Math.abs(angleC);

		this.oppLength = Math.sin(angleB) * hypLength;
		this.adjLength = Math.cos(angleB) * hypLength;
	}

	public void shortenTriangle(double length) {

		double newOpp = this.oppLength - length;
		double newHyp = Math.sqrt(Math.pow(this.adjLength, 2) + Math.pow(newOpp, 2));
		this.angleC = Math.asin(newOpp / newHyp);
		this.hypLength = newHyp;
		recalculateAngles();
	}

	public void offsetTriangle(double offset, double robotAngle, boolean onRight, double getAngleThing) {
		if (robotAngle > 90) {
			System.out.println("Adjusted: " + robotAngle);
			robotAngle = robotAngle - ((robotAngle - 90) * 2);
		}
		
		
		
		
		// New Line

		robotAngle = Math.toRadians(robotAngle);
		double hypLength = Math.sqrt(Math.pow(this.hypLength, 2) + Math.pow(offset, 2)
				- (2 * this.hypLength * offset * Math.cos(robotAngle)));
		// System.out.println("Robot Angle: " + robotAngle);
		// System.out.println("Offset: " + offset);
		// System.out.println("Hyp Length: " + this.hypLength);
		// System.out.println("Test Number: " + (Math.pow(robotAngle, 2) +
		// Math.pow(offset, 2) - (2 * this.hypLength * offset *
		// Math.cos(robotAngle))));
		double newAngle = Math.asin(offset * Math.sin(robotAngle) / hypLength);

		double adjustedAngle = Math.toRadians(90) - Math.abs(newAngle);
		System.out.println("Adjusted Angle: " + Math.toDegrees(newAngle));
//		if (!onRight) {
//			adjustedAngle = this.angleB + newAngle;
//		} else {
//			adjustedAngle = this.angleB - newAngle;
//		}
		this.angleC = Math.toRadians(90) - adjustedAngle;
		this.hypLength = hypLength;
		System.out.println("Angle C Offset: " + Math.toDegrees(this.angleC));
		recalculateAngles();
	}

	public void setHypLength(double hypLength) {
		System.out.println("Set Hyp Length: " + hypLength);
		this.hypLength = hypLength;
		recalculateAngles();
	}

	public void setAngleC(double angleC) {
		this.angleC = Math.toRadians(angleC);
		recalculateAngles();
	}

	public double getAdjLength() {
		return adjLength;
	}

	public double getOppLength() {
		return oppLength;
	}

	public double getHypLength() {
		return hypLength;
	}

	public double getAngleB() {
		return angleB;
	}

	public double getAngleC() {
		return Math.toDegrees(angleC);
	}
}
