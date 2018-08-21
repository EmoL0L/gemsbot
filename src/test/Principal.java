package test;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.InputEvent;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Principal {

	public static void main(String[] args) throws AWTException, IOException {

		Robot r = new Robot();
		// https://stackoverflow.com/questions/44326765/color-mapping-for-specific-range
		// https://github.com/lee-jason/BejeweledBot

		// 93x93 tamaño cuadrado
		// empieza en el 272x119 (wtf)
		// empieza en 269x146
		// resolucion 1024x768 Cuadrados de 75 Inicio 660x 261y

		// cursor 556x247y

		int inicioX = 661;
		int inicioY = 262;

		Robot robot = new Robot();
		robot.delay(2000);

		Game game = new Game(robot, inicioX, inicioY);
		ClickManager cm = new ClickManager(robot, inicioX, inicioY);

		game.printGrid(game.updateGame());

		System.out.println(game.getColorsGame());
		
		System.out.println(game.insidePVP());

		// TEST
		long startTime = System.currentTimeMillis();
		long currentTime = startTime;
		while (currentTime < startTime + 12 * 60 * 60 * 1000) {
			robot.delay(200);

			if (game.isYourTurn()) {
				game.findMatch(game.updateGame());
			} else if (game.gameIsFinished()) {
				game.continueGame();
			} else if (game.insidePVP()){
				cm.click(1270, 700);
			}else {
				cm.click(1100, 660);
				cm.click(1100, 860);
			}

			currentTime = System.currentTimeMillis();
		}

	}

	public static Point findOrigin() throws IOException, AWTException {
		Rectangle cali = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
		BufferedImage image = (new Robot()).createScreenCapture(cali);
		ColorUtils cu = new ColorUtils();
		for (int y = 0; y < image.getHeight(); y++) {
			for (int x = 0; x < image.getWidth(); x++) {
				if (x == 523 && y == 266) {
					System.out.println(image.getRGB(x, y));
				}
				System.out.println(cu.getColorNameFromHex(image.getRGB(x, y)));
				// if (image.getRGB(x, y) == -Colors.originPixel.colorNum
				// && image.getRGB(x, y + 1) ==
				// -Colors.originBottomPixel.colorNum) {
				// return new Point(x + Bejeweled.squareDimension / 2, y +
				// Bejeweled.squareDimension / 2);
				// }
			}
		}
		return null;
	}

	public static void printrgb(int intRGB) {
		Color color = new Color(intRGB);
		String frase = "Red: " + color.getRed() + " Green: " + color.getGreen() + " Blue: " + color.getBlue();
		System.out.println(frase);
	}

	private static int[][] convertTo2DUsingGetRGB(BufferedImage image) {
		int width = image.getWidth();
		int height = image.getHeight();
		int[][] result = new int[height][width];

		for (int row = 0; row < height; row++) {
			for (int col = 0; col < width; col++) {
				result[row][col] = image.getRGB(col, row);
			}
		}

		return result;
	}

	private static int[][] convertTo2DWithoutUsingGetRGB(BufferedImage image) {

		final byte[] pixels = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
		final int width = image.getWidth();
		final int height = image.getHeight();
		final boolean hasAlphaChannel = image.getAlphaRaster() != null;

		int[][] result = new int[height][width];
		if (hasAlphaChannel) {
			final int pixelLength = 4;
			for (int pixel = 0, row = 0, col = 0; pixel < pixels.length; pixel += pixelLength) {
				int argb = 0;
				argb += (((int) pixels[pixel] & 0xff) << 24); // alpha
				argb += ((int) pixels[pixel + 1] & 0xff); // blue
				argb += (((int) pixels[pixel + 2] & 0xff) << 8); // green
				argb += (((int) pixels[pixel + 3] & 0xff) << 16); // red
				result[row][col] = argb;
				col++;
				if (col == width) {
					col = 0;
					row++;
				}
			}
		} else {
			final int pixelLength = 3;
			for (int pixel = 0, row = 0, col = 0; pixel < pixels.length; pixel += pixelLength) {
				int argb = 0;
				argb += -16777216; // 255 alpha
				argb += ((int) pixels[pixel] & 0xff); // blue
				argb += (((int) pixels[pixel + 1] & 0xff) << 8); // green
				argb += (((int) pixels[pixel + 2] & 0xff) << 16); // red
				result[row][col] = argb;
				col++;
				if (col == width) {
					col = 0;
					row++;
				}
			}
		}

		return result;
	}

}
