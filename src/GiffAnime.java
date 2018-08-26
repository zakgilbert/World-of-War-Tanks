
public class GiffAnime {

	private long time;
	private int x, y, xLeft, xRight, yLeft, yRight, counter, fps, pixelX, pixelY, frames, rows;
	EZImage sheet;

	/*
	 * <======================================================================>
	 * Constructor for GiffAnime class. Enter sprite sheet file name, position x
	 * & Y, height and width of each frame in pixels, how long you want each frame to last in
	 * milliseconds, how many frames in each row, and how many rows.
	 * 
	 * Starting xLeft,xRight etc.. coordinates off on the first frame of sprite
	 * sheet.
	 * <=======================================================================>
	 */

	public GiffAnime(String fileName, int posX, int posY, int widthOfFrame, int heightOfFrame, int milliSecondsPerFrame,
			int numberOfFrames) {
		time = System.currentTimeMillis();
		x = posX;
		y = posY;
		xLeft = 0;
		xRight = widthOfFrame;
		yLeft = 0;
		yRight = heightOfFrame;
		counter = 0;
		pixelX = widthOfFrame;
		frames = numberOfFrames;
		fps = milliSecondsPerFrame;
		sheet = EZ.addImage(fileName, posX, posY);
	}

	private void setImagePosition() {
		sheet.setFocus(xLeft, yLeft, xRight, yRight);
	}

	void animate() {// Animates sprite sheet.
		if (counter >= frames) {
			xLeft = 0;
			xRight = pixelX;
			counter = 0;
			x = getXCenter();
			y = getYCenter();
			/*
			 * <===============================================================>
			 * If counter is >=frames Image position is set back to first frame
			 * of sprite sheet.
			 * <===============================================================>
			 * >
			 */
		}
		if (System.currentTimeMillis() - time >= fps) {
			setImagePosition();
			xLeft += pixelX;
			xRight += pixelX;
			time = System.currentTimeMillis();
			counter++;
			x = getXCenter();
			y = getYCenter();
			/*
			 * <===============================================================>
			 * If current time minus time >= fps(how many seconds you want each
			 * frame to last.) Set Image position, then add the size of each
			 * frame to top left x and bottom right x
			 * <===============================================================>
			 */
		}

	}

	void moveForward(float spd) {
		sheet.moveForward(spd);

	}
	
	void turnLeft(double degrees) {
		sheet.turnLeft(degrees);
	}
	
	void turnRight(double degrees) {
		sheet.turnRight(degrees);
	}

	void translateTo(double x, double y) {
		sheet.translateTo(x, y);

	}

	public boolean isPointInElement(int posx, int posy) {
		return sheet.isPointInElement(posx, posy);
	}

	int getXCenter() {
		return sheet.getXCenter();

	}

	int getYCenter() {
		return sheet.getYCenter();

	}

	void rotateTo(double rotationalValue) {
		sheet.rotateTo(rotationalValue);
	}

	void hide() {
		sheet.hide();
	}

	void show() {
		sheet.show();
	}

	double getRotation() {
		return sheet.getRotation();
	}

	int getHeight() {
		return sheet.getHeight();
	}

	int getWidth() {
		return sheet.getWidth();
	}

}
