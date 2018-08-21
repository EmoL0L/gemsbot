package test;

import java.awt.AWTException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Game {

	private final int ROWS = 8;
	private final int SQUARE_SIZE = 75;
	private ColorUtils colorUtils = new ColorUtils();

	private Robot robot;
	private int inicioX;
	private int inicioY;
	private int[][] matrix = new int[ROWS][ROWS];
	private String[][] test = new String[ROWS][ROWS];

	public Game(Robot robot, int inicioX, int inicioY) {
		this.robot = robot;
		this.inicioX = inicioX + 10;
		this.inicioY = inicioY + 10;
	}

	public int[][] updateGame() {
		ColorAssigner colorAssigner = new ColorAssigner();
		Rectangle cali = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
		BufferedImage image = robot.createScreenCapture(cali);
		int equilibrar = 0;

		for (int x = 0; x < ROWS; x++) {
			for (int y = 0; y < ROWS; y++) {
				if (y % 2 == 1)
					equilibrar = 1;
				else
					equilibrar = 0;

				ArrayList<Integer> hexColorList = new ArrayList<Integer>();
				hexColorList.add(image.getRGB(inicioX + ((SQUARE_SIZE * x) + equilibrar), inicioY + (SQUARE_SIZE * y)));
				for (int i = 0; i < 40; i++) {
					for (int j = 0; j < 40; j++) {
						hexColorList.add(image.getRGB(inicioX + ((SQUARE_SIZE * x) + equilibrar + i),
								inicioY + (SQUARE_SIZE * y) + j));
					}
				}

				// System.out.format("CORDS %dx %dy\n", inicioX + ((SQUARE_SIZE
				// * x) + equilibrar),
				// inicioY + (SQUARE_SIZE * y));
				matrix[x][y] = colorAssigner.assaignColor(colorAssigner.getColorAverageName(hexColorList));
				test[x][y] = colorAssigner.getColorAverageName(hexColorList);
				// System.out.format("%dx %dy %s\n", x, y, test[x][y]);
				// robot.delay(300);
				// robot.mouseMove(inicioX + ((SQUARE_SIZE * x) + equilibrar),
				// inicioY + (SQUARE_SIZE * y));
			}
		}

		return matrix;
	}

	public void printGrid(int[][] grid) {

		for (int y = 0; y < ROWS; y++) {
			for (int x = 0; x < ROWS; x++) {
				System.out.format("%d|", grid[x][y]);
			}
			System.out.println("");
		}
	}

	public ArrayList<String> getColorsGame() {
		ArrayList<String> listaColores = new ArrayList<String>();
		String matrix[][] = test;

		for (int x = 0; x < ROWS; x++) {
			for (int y = 0; y < ROWS; y++) {
				if (!listaColores.contains(matrix[x][y])) {
					listaColores.add(matrix[x][y]);
				}
			}
		}

		return listaColores;
	}

	public boolean isYourTurn() {
		Rectangle cali = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
		BufferedImage image = robot.createScreenCapture(cali);

		// el color es -1
		return image.getRGB(556, 247) == -1 ? true : false;
	}
	
	public void continueGame(){
		ClickManager clickManager = new ClickManager(robot, inicioX, inicioY);
		clickManager.click(1088, 840);
		clickManager.click(1088, 840);
	}
	
	public boolean gameIsFinished(){
		Rectangle cali = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
		BufferedImage image = robot.createScreenCapture(cali);

		return image.getRGB(1088, 840) == -12414173 ? true : false;
	}
	
	public boolean insidePVP(){
		Rectangle cali = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
		BufferedImage image = robot.createScreenCapture(cali);
		
		return image.getRGB(1270, 775) == -15906437;
	}

	public void findMatch(int[][] grid) {
		int color = 0;
		ClickManager clickManager = new ClickManager(robot, inicioX, inicioY);

		for (int x = 0; x < ROWS; x++) {
			for (int y = 0; y < ROWS; y++) {
				// System.out.println("x" + x + "y" + y);
				
				if (x + 3 < 8) {
					if (grid[x + 0][y + 0] == grid[x + 2][y + 0] && grid[x + 2][y + 0] == grid[x + 3][y + 0]) {
						clickManager.move(x, y, x + 1, y);
						return;
					}
				}
				if (x - 3 >= 0) {
					if (grid[x + 0][y + 0] == grid[x - 2][y + 0] && grid[x - 2][y + 0] == grid[x - 3][y + 0]) {
						clickManager.move(x, y, x - 1, y);
						return;
					}
				}
				if (y + 3 < 8) {
					if (grid[x + 0][y + 0] == grid[x + 0][y + 2] && grid[x + 0][y + 0] == grid[x + 0][y + 3]) {
						clickManager.move(x, y, x, y + 1);
						return;
					}
				}
				if (y - 3 >= 0) {
					if (grid[x + 0][y + 0] == grid[x + 0][y - 2] && grid[x + 0][y + 0] == grid[x + 0][y - 3]) {

						clickManager.move(x, y, x, y - 1);
						return;
					}
				}
				if (y + 1 < 8) {
					if (x + 2 < 8 && grid[x + 0][y + 0] == grid[x + 1][y + 1]
							&& grid[x + 0][y + 0] == grid[x + 2][y + 1]) {

						clickManager.move(x, y, x, y + 1);
						return;
					}
					if (x - 2 >= 0 && grid[x + 0][y + 0] == grid[x - 1][y + 1]
							&& grid[x + 0][y + 0] == grid[x - 2][y + 1]) {

						clickManager.move(x, y, x, y + 1);
						return;

					}
					if ((x + 1 < 8 && x - 1 >= 0) && grid[x + 0][y + 0] == grid[x + 1][y + 1]
							&& grid[x + 0][y + 0] == grid[x - 1][y + 1]) {

						clickManager.move(x, y, x, y + 1);
						return;
					}
				}
				if (y - 1 >= 0) {
					if (x + 2 < 8 && grid[x + 0][y + 0] == grid[x + 1][y - 1]
							&& grid[x + 0][y + 0] == grid[x + 2][y - 1]) {

						clickManager.move(x, y, x, y - 1);
						return;
					}
					if (x - 2 >= 0 && grid[x + 0][y + 0] == grid[x - 1][y - 1]
							&& grid[x + 0][y + 0] == grid[x - 2][y - 1]) {

						clickManager.move(x, y, x, y - 1);
						return;
					}
					if ((x + 1 < 8 && x - 1 >= 0) && grid[x + 0][y + 0] == grid[x + 1][y - 1]
							&& grid[x + 0][y + 0] == grid[x - 1][y - 1]) {

						clickManager.move(x, y, x, y - 1);
						return;
					}
				}
				if (x + 1 < 8) {
					if (y + 2 < 8 && grid[x + 0][y + 0] == grid[x + 1][y + 1]
							&& grid[x + 0][y + 0] == grid[x + 1][y + 2]) {
						clickManager.move(x, y, x + 1, y);
						return;
					}
					if (y - 2 >= 0 && grid[x + 0][y + 0] == grid[x + 1][y - 1]
							&& grid[x + 0][y + 0] == grid[x + 1][y - 2]) {
						clickManager.move(x, y, x + 1, y);
						return;
					}
					if ((y + 1 < 8 && y - 1 >= 0) && grid[x + 0][y + 0] == grid[x + 1][y - 1]
							&& grid[x + 0][y + 0] == grid[x + 1][y + 1]) {
						clickManager.move(x, y, x + 1, y);
						return;
					}
				}
				if (x - 1 >= 0) {
					if (y + 2 < 8 && grid[x + 0][y + 0] == grid[x - 1][y + 1]
							&& grid[x + 0][y + 0] == grid[x - 1][y + 2]) {
						clickManager.move(x, y, x - 1, y);
						return;
					}
					if (y - 2 >= 0 && grid[x + 0][y + 0] == grid[x - 1][y - 1]
							&& grid[x + 0][y + 0] == grid[x - 1][y - 2]) {
						clickManager.move(x, y, x - 1, y);
						return;
					}
					if ((y + 1 < 8 && y - 1 >= 0) && grid[x + 0][y + 0] == grid[x - 1][y - 1]
							&& grid[x + 0][y + 0] == grid[x - 1][y + 1]) {
						clickManager.move(x, y, x - 1, y);
						return;
					}
				}
			}
		}
	}

}
