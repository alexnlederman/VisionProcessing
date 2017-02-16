package testing;

public class Triangle {

	// Peg
	// |\
	// |b\
	// | \
	// | \
	// | \Hyp
	// Adj| \
	// | \
	// | \
	// |a______c\
	// Opp Camera

	double hypLength;
	double oppLength;
	double adjLength;

	double angleB;
	double angleC;
	double angleA;

	public Triangle(double hypLength, double angleC) {
		this.hypLength = hypLength;
		this.angleC = Math.toRadians(angleC);

		recalculateAngles();
	}

	private void recalculateAngles() {
		this.angleA = 1.5707;
		this.angleB = 1.5707 - angleC;

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

	public void offsetTriangle(double offset, double robotAngle, boolean onRight) {
		if (robotAngle > 90) {
			robotAngle = robotAngle - ((robotAngle - 90) * 2);
		}
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

		System.out.println("Angle B: " + Math.toDegrees(this.angleB));
		double adjustedAngle;
		if (!onRight) {
			adjustedAngle = this.angleB + newAngle;
		} else {
			adjustedAngle = this.angleB - newAngle;
		}
		System.out.println("Adjusted Angle: " + Math.toRadians(adjustedAngle));
		this.angleC = 90 - adjustedAngle;
		this.hypLength = hypLength;
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
		return angleC;
	}
}
