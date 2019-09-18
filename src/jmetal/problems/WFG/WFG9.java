/**
 * WFG9.java
 *
 * @author Juan J. Durillo
 * @version 1.0
 */
package jmetal.problems.WFG;

import jmetal.core.Solution;
import jmetal.core.Variable;
import jmetal.util.JMException;


/**
 * Creates a default WFG9 problem with
 * 2 position-related parameters,
 * 4 distance-related parameters,
 * and 2 objectives
 */
public class WFG9 extends WFG {

	/**
	 * Creates a default WFG9 with
	 * 2 position-related parameters,
	 * 4 distance-related parameters,
	 * and 2 objectives
	 *
	 * @param solutionType The solution type must "Real" or "BinaryReal".
	 */
	public WFG9(String solutionType) throws ClassNotFoundException {
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
	} // WFG9

	/**
	 * Creates a WFG9 problem instance
	 *
	 * @param k            Number of position variables
	 * @param l            Number of distance variables
	 * @param M            Number of objective functions
	 * @param solutionType The solution type must "Real" or "BinaryReal".
	 */
	public WFG9(String solutionType, Integer k, Integer l, Integer M) throws ClassNotFoundException {
		super(solutionType, k, l, M);
		problemName_ = "WFG9";

		s = new int[m];
		for (int i = 0; i < m; i++) {
			s[i] = 2 * (i + 1);
		}

		a = new int[m - 1];
		for (int i = 0; i < m - 1; i++) {
			a[i] = 1;
		}
	} // WFG9


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
		y = t2(y, k);
		y = t3(y, k, m);

		float[] result = new float[m];
		float[] x = calculateX(y);
		for (int m = 1; m <= this.m; m++) {
			result[m - 1] = d * x[this.m - 1] + s[m - 1] * (new Shapes()).concave(x, m);
		}
		return result;
	} //evaluate

	/**
	 * WFG9 t1 transformation
	 */
	public float[] t1(float[] z, int k) {
		float[] result = new float[z.length];
		float[] w = new float[z.length];

		for (int i = 0; i < w.length; i++) {
			w[i] = (float) 1.0;
		}

		for (int i = 0; i < z.length - 1; i++) {
			int head = i + 1;
			int tail = z.length - 1;
			float[] subZ = subVector(z, head, tail);
			float[] subW = subVector(w, head, tail);
			float aux = (new Transformations()).rSum(subZ, subW);
			result[i] = (new Transformations()).bParam(z[i], aux, (float) 0.98 / (float) 49.98, (float) 0.02, (float) 50);
		}

		result[z.length - 1] = z[z.length - 1];
		return result;
	} // t1

	/**
	 * WFG9 t2 transformation
	 */
	public float[] t2(float[] z, int k) {
		float[] result = new float[z.length];

		for (int i = 0; i < k; i++) {
			result[i] = (new Transformations()).sDecept(z[i], (float) 0.35, (float) 0.001, (float) 0.05);
		}

		for (int i = k; i < z.length; i++) {
			result[i] = (new Transformations()).sMulti(z[i], 30, 95, (float) 0.35);
		}

		return result;
	} // t2

	/**
	 * WFG9 t3 transformation
	 */
	public float[] t3(float[] z, int k, int M) {
		float[] result = new float[M];

		for (int i = 1; i <= M - 1; i++) {
			int head = (i - 1) * k / (M - 1) + 1;
			int tail = i * k / (M - 1);
			float[] subZ = subVector(z, head - 1, tail - 1);
			result[i - 1] = (new Transformations()).rNonsep(subZ, k / (M - 1));
		}

		int head = k + 1;
		int tail = z.length;
		int l = z.length - k;
		float[] subZ = subVector(z, head - 1, tail - 1);
		result[M - 1] = (new Transformations()).rNonsep(subZ, l);

		return result;
	} // t3

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
	}  // evaluate
} // WFG9


