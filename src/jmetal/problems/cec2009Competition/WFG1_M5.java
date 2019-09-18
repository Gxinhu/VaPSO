//  WFG1.java
//
//  Author:
//       Antonio J. Nebro <antonio@lcc.uma.es>
//       Juan J. Durillo <durillo@lcc.uma.es>
//
//  Copyright (c) 2011 Antonio J. Nebro, Juan J. Durillo
//
//  This program is free software: you can redistribute it and/or modify
//  it under the terms of the GNU Lesser General Public License as published by
//  the Free Software Foundation, either version 3 of the License, or
//  (at your option) any later version.
//
//  This program is distributed in the hope that it will be useful,
//  but WITHOUT ANY WARRANTY; without even the implied warranty of
//  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
//  GNU Lesser General Public License for more details.
// 
//  You should have received a copy of the GNU Lesser General Public License
//  along with this program.  If not, see <http://www.gnu.org/licenses/>.

package jmetal.problems.cec2009Competition;

import jmetal.core.Problem;
import jmetal.core.Solution;
import jmetal.core.Variable;
import jmetal.encodings.solutionType.BinaryRealSolutionType;
import jmetal.encodings.solutionType.RealSolutionType;
import jmetal.util.JMException;

/**
 * This class implements the WFG1 problem
 * Reference: Simon Huband, Luigi Barone, Lyndon While, Phil Hingston
 * A Scalable Multi-objective Test Problem Toolkit.
 * Evolutionary Multi-Criterion Optimization:
 * Third International Conference, EMO 2005.
 * Proceedings, volume 3410 of Lecture Notes in Computer Science
 */
public class WFG1_M5 extends Problem {
	private static final double EPSILON = 1.0e-10;

	/**
	 * Constructor
	 * Creates a default WFG1 instance with
	 * 2 position-related parameters
	 * 4 distance-related parameters
	 * and 2 objectives
	 *
	 * @param solutionType The solution type must "Real" or "BinaryReal".
	 */
	public WFG1_M5(String solutionType) throws ClassNotFoundException {
		this(solutionType, 30);
	} // WFG1_M5

	/**
	 * Creates a WFG1 problem instance
	 *
	 * @param solutionType The solution type must "Real" or "BinaryReal".
	 */
	public WFG1_M5(String solutionType, Integer numberOfVariables) {
		numberOfVariables_ = numberOfVariables;
		numberOfObjectives_ = 5;
		numberOfConstraints_ = 0;
		problemName_ = "WFG1_M5";

		upperLimit_ = new double[numberOfVariables_];
		lowerLimit_ = new double[numberOfVariables_];

		for (int var = 0; var < numberOfVariables_; var++) {
			lowerLimit_[var] = 0;
			upperLimit_[var] = 2 * (var + 1);
		} //for


		if (solutionType.compareTo("BinaryReal") == 0) {
			solutionType_ = new BinaryRealSolutionType(this);
		} else if (solutionType.compareTo("Real") == 0) {
			solutionType_ = new RealSolutionType(this);
		} else {
			System.out.println("Error: solution type " + solutionType + " invalid");
			System.exit(-1);
		}
	} // WFG1_M5

	/**
	 * Evaluates a solution.
	 *
	 * @param solution The solution to evaluate.
	 * @throws JMException
	 */
	@Override
	public void evaluate(Solution solution) throws JMException {
		Variable[] decisionVariables = solution.getDecisionVariables();

		double[] z = new double[numberOfVariables_];
		for (int i = 0; i < numberOfVariables_; i++) {
			z[i] = decisionVariables[i].getValue();
		}
		int nx = numberOfVariables_;
		int M = numberOfObjectives_;
		double[] f = new double[M];

		int i = 0;
		int j = 0;
		double[] y = new double[30];
		double[] t1 = new double[30];
		double[] t2 = new double[30];
		double[] t3 = new double[30];
		double[] t4 = new double[5];
		int k = M == 2 ? 4 : 2 * (M - 1);
		for (i = 0; i < nx; i++) {
			y[i] = z[i] / (2.0 * (i + 1));
		}
		for (i = 0; i < k; i++) {
			t1[i] = y[i];
		}
		for (i = k; i < nx; i++) {
			t1[i] = s_linear(y[i], 0.35);
		}
		for (i = 0; i < k; i++) {
			t2[i] = t1[i];
		}
		for (i = k; i < nx; i++) {
			t2[i] = b_flat(t1[i], 0.8, 0.75, 0.85);
		}
		for (i = 0; i < nx; i++) {
			t3[i] = b_poly(t2[i], 0.02);
		}
		double[] w = new double[30];
		double[] y_sub = new double[30];
		double[] w_sub = new double[30];
		double[] y_sub2 = new double[30];
		double[] w_sub2 = new double[30];
		for (i = 1; i <= nx; i++) {
			w[i - 1] = 2.0 * i;
		}
		for (i = 1; i <= M - 1; i++) {
			int head = (i - 1) * k / (M - 1);
			int tail = i * k / (M - 1);
			for (j = head; j < tail; j++) {
				y_sub[j - head] = t3[j];
				w_sub[j - head] = w[j];
			}
			t4[i - 1] = r_sum(y_sub, w_sub, tail - head);
		}
		for (j = k; j < nx; j++) {
			y_sub2[j - k] = t3[j];
			w_sub2[j - k] = w[j];
		}
		t4[i - 1] = r_sum(y_sub2, w_sub2, nx - k);
		int m;
		int[] A = new int[5];
		double[] x = new double[5];
		double[] h = new double[5];
		double[] S = new double[5];
		A[0] = 1;
		for (i = 1; i < M - 1; i++) {
			A[i] = 1;
		}
		for (i = 0; i < M - 1; i++) {
			double tmp1;
			tmp1 = t4[M - 1];
			if (A[i] > tmp1) {
				tmp1 = A[i];
			}
			x[i] = tmp1 * (t4[i] - 0.5) + 0.5;
		}
		x[M - 1] = t4[M - 1];
		for (m = 1; m <= M - 1; m++) {
			h[m - 1] = convex(x, m, M);
		}
		h[m - 1] = mixed(x, 5, 1.0);
		for (m = 1; m <= M; m++) {
			S[m - 1] = m * 2.0;
		}
		for (i = 0; i < M; i++) {
			f[i] = 1.0 * x[M - 1] + S[i] * h[i];
		}

		for (int l = 0; l < M; l++) {
			solution.setObjective(l, f[l]);
		}

	} // evaluate

	private static double correct_to_01(double aa, double epsilon) {
		double min = 0.0, max = 1.0;
		double min_epsilon = min - epsilon;
		double max_epsilon = max + epsilon;
		if (aa <= min && aa >= min_epsilon) {
			return min;
		} else if (aa >= max && aa <= max_epsilon) {
			return max;
		} else {
			return aa;
		}
	}

	private static double convex(double[] x, int m, int M) {
		int i;
		double result = 1.0;
		for (i = 1; i <= M - m; i++) {
			result *= 1.0 - Math.cos(x[i - 1] * Math.PI / 2.0);
		}
		if (m != 1) {
			result *= 1.0 - Math.sin(x[M - m] * Math.PI / 2.0);
		}
		return correct_to_01(result, EPSILON);
	}

	private static double mixed(double[] x, int A, double alpha) {
		double tmp = 2.0 * A * Math.PI;
		return correct_to_01(Math.pow(1.0 - x[0] - Math.cos(tmp * x[0] + Math.PI / 2.0) / tmp, alpha), EPSILON);
	}

	private static double min_double(double aa, double bb) {
		return aa < bb ? aa : bb;
	}

	private static double b_poly(double y, double alpha) {
		return correct_to_01(Math.pow(y, alpha), EPSILON);
	}

	private static double b_flat(double y, double A, double B, double C) {
		double tmp1 = min_double(0.0, Math.floor(y - B)) * A * (B - y) / B;
		double tmp2 = min_double(0.0, Math.floor(C - y)) * (1.0 - A) * (y - C) / (1.0 - C);
		return correct_to_01(A + tmp1 - tmp2, EPSILON);
	}

	private static double s_linear(double y, double A) {
		return correct_to_01(Math.abs(y - A) / Math.abs(Math.floor(A - y) + A), EPSILON);
	}

	private static double r_sum(double[] y, double[] w, int ny) {
		int i;
		double numerator = 0.0;
		double denominator = 0.0;
		for (i = 0; i < ny; i++) {
			numerator += w[i] * y[i];
			denominator += w[i];
		}
		return correct_to_01(numerator / denominator, EPSILON);
	}
} // WFG1
