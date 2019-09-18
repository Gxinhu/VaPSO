package jmetal.util.deepcopy;

import java.util.Arrays;

public class deepCopy {
	public static double[][] deepCopysDouble2d(double[][] original) {
		if (original == null) {
			return null;
		}
		final double[][] result = new double[original.length][];
		for (int i = 0; i < original.length; i++) {
			result[i] = Arrays.copyOf(original[i], original[i].length);
		}
		return result;
	}
}
