package util;

import java.util.Random;
import java.util.StringTokenizer;

/**
 * 
 */

/**
 * @author harinder
 *
 */
public class util {
	public static int randInt(int min, int max) {
		Random rand = new Random();
		int randomNum = rand.nextInt((max - min) + 1) + min;
		return randomNum;
	}

}
