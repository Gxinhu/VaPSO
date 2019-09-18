//  WFG5.java
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

package jmetal.problems.WFG;

import jmetal.core.Solution;
import jmetal.core.Variable;
import jmetal.util.JMException;

/**
 * This class implements the WFG5 problem
 * Reference: Simon Huband, Luigi Barone, Lyndon While, Phil Hingston
 * A Scalable Multi-objective Test Problem Toolkit.
 * Evolutionary Multi-Criterion Optimization:
 * Third International Conference, EMO 2005.
 * Proceedings, volume 3410 of Lecture Notes in Computer Science
 */
public class WFG5 extends WFG {
	/**
	 * Creates a default WFG5 instance with
	 * 2 position-related parameters
	 * 4 distance-related parameters
	 * and 2 objectives
	 *
	 * @param solutionType The solution type must "Real" or "BinaryReal".
	 */
	public WFG5(String solutionType) throws ClassNotFoundException {
//	  this(solutionType, k, l, M) ; k: position parameters,l distance parameters
//	  this(solutionType, 2, 20, 2) ;
		this(solutionType, 4, 20, 3);
//    this(solutionType, 6, 8, 4) ;
//    this(solutionType, 8, 10, 5) ;
//    this(solutionType, 10, 12, 6) ;
//    this(solutionType, 14, 14, 8) ;
//    this(solutionType, 18, 16, 10) ;
//	  this(solutionType, 28, 20, 15) ;
//	  this(solutionType, 38, 20, 20) ;
//	  this(solutionType, 24, 30, 25) ;
//	  this(solutionType, 49, 30, 50) ;
	} // WFG5

	/**
	 * Creates a WFG5 problem instance
	 *
	 * @param k            Number of position parameters
	 * @param l            Number of distance parameters
	 * @param M            Number of objective functions
	 * @param solutionType The solution type must "Real" or "BinaryReal".
	 */
	public WFG5(String solutionType, Integer k, Integer l, Integer M) throws ClassNotFoundException {
		super(solutionType, k, l, M);
		problemName_ = "WFG5";

		s = new int[m];
		for (int i = 0; i < m; i++) {
			s[i] = 2 * (i + 1);
		}

		a = new int[m - 1];
		for (int i = 0; i < m - 1; i++) {
			a[i] = 1;
		}
	} // WFG5

	/**
	 * Evaluates a solution
	 *
	 * @param z The solution to evaluate
	 * @return double [] with the evaluation results
	 */
	@Override
	public float[] evaluate(float[] z) {
		float[] y;

		y = normalise(z);
		y = t1(y, k);
		y = t2(y, k, m);

		float[] result = new float[m];
		float[] x = calculateX(y);
		for (int m = 1; m <= this.m; m++) {
			result[m - 1] = d * x[this.m - 1] + s[m - 1] * (new Shapes()).concave(x, m);
		}

		return result;
	} // evaluate

	/**
	 * WFG5 t1 transformation
	 */
	public float[] t1(float[] z, int k) {
		float[] result = new float[z.length];

		for (int i = 0; i < z.length; i++) {
			result[i] = (new Transformations()).sDecept(z[i], (float) 0.35, (float) 0.001, (float) 0.05);
		}

		return result;
	} // t1


	/**
	 * WFG5 t2 transformation
	 */
	public float[] t2(float[] z, int k, int M) {
		float[] result = new float[M];
		float[] w = new float[z.length];

		for (int i = 0; i < z.length; i++) {
			w[i] = (float) 1.0;
		}

		for (int i = 1; i <= M - 1; i++) {
			int head = (i - 1) * k / (M - 1) + 1;
			int tail = i * k / (M - 1);
			float[] subZ = subVector(z, head - 1, tail - 1);
			float[] subW = subVector(w, head - 1, tail - 1);

			result[i - 1] = (new Transformations()).rSum(subZ, subW);
		}

		int head = k + 1;
		int tail = z.length;
		float[] subZ = subVector(z, head - 1, tail - 1);
		float[] subW = subVector(w, head - 1, tail - 1);
		result[M - 1] = (new Transformations()).rSum(subZ, subW);

		return result;
	} // t2

	/**
	 * Evaluates a solution
	 *
	 * @param solution The solution to evaluate
	 * @throws JMException
	 */
	@Override
	public final void evaluate(Solution solution) throws JMException {
		float[] variables = new float[getNumberOfVariables()];
		Variable[] dv = solution.getDecisionVariables();

		for (int i = 0; i < getNumberOfVariables(); i++) {
			variables[i] = (float) dv[i].getValue();
		}

		float[] sol = evaluate(variables);

		for (int i = 0; i < sol.length; i++) {
			solution.setObjective(i, sol[i]);
		}
	} // evaluate

} // WFG5
