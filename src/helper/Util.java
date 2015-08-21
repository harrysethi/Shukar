package helper;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 
 */

/**
 * @author harinder
 *
 */
public class Util {
	public static int randInt(int min, int max) {
		Random rand = new Random();
		int randomNum = rand.nextInt((max - min) + 1) + min;
		return randomNum;
	}
	
	public static List<Integer> getIDsFromStr(String str){
		List<Integer> list2Ret = new ArrayList<Integer>();
		
		int index = 1;
		char[] arr = str.toCharArray();

		while (index < arr.length - 1) {
			int ID = arr[index++] - '0' - 1;
			list2Ret.add(ID);
			index++;
		}
		
		return list2Ret;
	}

}
