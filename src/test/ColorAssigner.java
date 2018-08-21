package test;

import java.util.ArrayList;

public class ColorAssigner {
	private final int SKULL = 0;
	private final int GREEN = 1;
	private final int RED = 2;
	private final int BLUE = 3;
	private final int YELLOW = 4;
	private final int BROWN = 5;
	private final int PURPLE = 6;

	public String getColorAverageName(ArrayList<Integer> hexColorsList) {
		int r = 0;
		int g = 0;
		int b = 0;
		int arraySize = hexColorsList.size();

		for (int i = 0; i < arraySize; i++) {
			int hexColor = hexColorsList.get(i);
			r += (hexColor & 0xFF0000) >> 16;
			g += (hexColor & 0xFF00) >> 8;
			b += (hexColor & 0xFF);
		}

		ColorUtils colorUtils = new ColorUtils();
		return colorUtils.getColorNameFromRgb(r / arraySize, g / arraySize, b / arraySize);
	}

	public int assaignColor(String colorName) {
		int color = -1;

		switch (colorName) {
		case "DarkGray":
		case "IndianRed": // DOOM SKULL
		case "RosyBrown": // DOOM SKULL
		case "LightSlateGray":
			color = SKULL;
			break;

		case "OliveDrab":
			color = GREEN;
			break;

		case "Crimson":
		case "FireBrick":
			color = RED;
			break;

		case "SteelBlue":
			color = BLUE;
			break;

		case "SandyBrown":
			color = YELLOW;
			break;

		case "DimGray":
			color = BROWN;
			break;

		case "DarkOrchid":
			color = PURPLE;
			break;

		default:
			color = SKULL;
		}

		// if (color == -1) {
		// System.out.println(colorName);
		// }

		return color;
	}
}
