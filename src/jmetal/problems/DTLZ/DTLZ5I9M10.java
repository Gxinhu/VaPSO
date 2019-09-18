//  DTLZ5IM.java
//


package jmetal.problems.DTLZ;

import jmetal.core.Problem;
import jmetal.core.Solution;
import jmetal.core.Variable;
import jmetal.encodings.solutionType.BinaryRealSolutionType;
import jmetal.encodings.solutionType.RealSolutionType;
import jmetal.util.JMException;

/**
 * Class representing problem DTLZ5
 */
public class DTLZ5I9M10 extends Problem {
	Integer numberOfI_;

	/**
	 * Creates a default DTLZ5IM problem instance (12 variables and 3 objectives)
	 *
	 * @param solutionType The solution type must "Real" or "BinaryReal".
	 */
	public DTLZ5I9M10(String solutionType) throws ClassNotFoundException {
// this (solutionType, numberOfVariables, numberOfObjectives, numberOfI);//
		this(solutionType, 19, 10, 9);//10 objectives
//
	} // DTLZ5IM

	/**
	 * Creates a new DTLZ5 problem instance
	 *
	 * @param numberOfVariables  Number of variables
	 * @param numberOfObjectives Number of objective functions
	 * @param solutionType       The solution type must "Real" or "BinaryReal".
	 */
	public DTLZ5I9M10(String solutionType,
	                  Integer numberOfVariables,
	                  Integer numberOfObjectives,
	                  Integer numberOfI) {
		numberOfVariables_ = numberOfVariables;
		numberOfObjectives_ = numberOfObjectives;
		numberOfConstraints_ = 0;
		problemName_ = "DTLZ5I4M10";
		numberOfI_ = numberOfI;

		lowerLimit_ = new double[numberOfVariables_];
		upperLimit_ = new double[numberOfVariables_];
		for (int var = 0; var < numberOfVariables_; var++) {
			lowerLimit_[var] = 0.0;
			upperLimit_[var] = 1.0;
		}

		if (solutionType.compareTo("BinaryReal") == 0) {
			solutionType_ = new BinaryRealSolutionType(this);
		} else if (solutionType.compareTo("Real") == 0) {
			solutionType_ = new RealSolutionType(this);
		} else {
			System.out.println("Error: solution type " + solutionType + " invalid");
			System.exit(-1);
		}
	} // DTLZ5IM

	/**
	 * Evaluates a solution
	 *
	 * @param solution The solution to evaluate
	 * @throws JMException
	 */
	@Override
	public void evaluate(Solution solution) throws JMException {
		Variable[] gen = solution.getDecisionVariables();

		double[] xreal = new double[numberOfVariables_];
		double[] obj = new double[numberOfObjectives_];

		double sum = 0.0;
		double gx;
		int i, j;
		double[] x = new double[numberOfObjectives_ - 1];

		for (int k = 0; k < numberOfVariables_; k++) {
			xreal[k] = gen[k].getValue();
		}

		for (i = numberOfObjectives_ - 1; i < numberOfVariables_; i++) {
			sum += Math.pow((xreal[i] - 0.5), 2.0);
		}

		for (i = 0; i < numberOfI_ - 1; i++) {
			x[i] = xreal[i] * Math.PI / 2.0;
		}
		for (i = numberOfI_ - 1; i < numberOfObjectives_ - 1; i++) {
			x[i] = Math.PI / (4 * (1 + sum)) * (1 + 2 * sum * xreal[i]);
		}
		gx = 1.0 + 100 * sum;

		sum = gx;
		for (j = 0; j < numberOfObjectives_ - 1; j++) {
			sum = sum * Math.cos(x[j]);
		}
		obj[0] = sum;

		for (i = 1; i < numberOfObjectives_; i++) {
			sum = gx;
			for (j = 0; j < numberOfObjectives_ - 1 - i; j++) {
				sum = sum * Math.cos(x[j]);
			}
			sum = sum * Math.sin(x[numberOfObjectives_ - 1 - i]);
			obj[i] = sum;
		}

		for (int k = 0; k < numberOfObjectives_; k++) {
			solution.setObjective(k, obj[k]);
		}
	} // evaluate
}
