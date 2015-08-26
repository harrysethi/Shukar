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

	public static List<Integer> getIDsFromStr(String str) {
		List<Integer> list2Ret = new ArrayList<Integer>();

		str = str.substring(1, str.length() - 1);

		String[] arr = str.split(",");

		for (String s : arr) {
			if(s.equals("") || s==null) break;
			int ID = Integer.parseInt(s) - 1;
			list2Ret.add(ID);
		}

		return list2Ret;
	}

	public static String getNodeIDsAsDelimetedStr(List<Integer> nodeIDs) {
		StringBuilder sb = new StringBuilder("");

		sb.append("[");

		int len = nodeIDs.size();
		int index = 0;
		for (Integer ID : nodeIDs) {
			sb.append(ID + 1);
			index++;
			if (index != len)
				sb.append(",");
		}
		sb.append("]");

		return sb.toString();
	}

}
