package test;

import java.awt.Robot;
import java.awt.event.InputEvent;

public class ClickManager {

	private final int SQUARE_SIZE = 75;
	private Robot robot;
	private int inicioX;
	private int inicioY;

	public ClickManager(Robot robot, int inicioX, int inicioY) {
		this.robot = robot;
		this.inicioX = inicioX;
		this.inicioY = inicioY;
	}

	/**
	 * Indicale las filas tal como son (Van del 0-8).
	 * @param rowX1
	 * @param rowY1
	 * @param rowX2
	 * @param rowY2
	 */
	public void move(int rowX1, int rowY1, int rowX2, int rowY2) {
		robot.delay(300);
		//System.out.format("Mouse move: %dx%dy to %dx%dt \n",rowX1, rowY1, rowX2, rowY2);
		// click1
		robot.mouseMove(inicioX + (SQUARE_SIZE * (rowX1)), inicioY + (SQUARE_SIZE * (rowY1)));
		robot.mousePress(InputEvent.BUTTON1_MASK);
		robot.mouseRelease(InputEvent.BUTTON1_MASK);
		robot.delay(300);
		// click2
		robot.mouseMove(inicioX + (SQUARE_SIZE * (rowX2)), inicioY + (SQUARE_SIZE * (rowY2)));
		robot.mousePress(InputEvent.BUTTON1_MASK);
		robot.mouseRelease(InputEvent.BUTTON1_MASK);
	}
	
	public void click(int x, int y){
		robot.delay(300);
		robot.mouseMove(x, y);
		robot.mousePress(InputEvent.BUTTON1_MASK);
		robot.mouseRelease(InputEvent.BUTTON1_MASK);
	}

}
