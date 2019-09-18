//  CarSideImpact.java
//
//  Author:
//       Yi Xiang <antonio@lcc.uma.es>


package jmetal.problems;

import jmetal.core.Problem;
import jmetal.core.Solution;
import jmetal.encodings.solutionType.BinaryRealSolutionType;
import jmetal.encodings.solutionType.RealSolutionType;
import jmetal.util.JMException;

import java.util.Random;

/**
 * Class representing problem Water
 */
public class CarSideImpact extends Problem {
	public Random rdm = new Random();

	/**
	 * Constructor.
	 * Creates a default instance of the CrashWorthinessDesign problem.
	 *
	 * @param solutionType The solution type must "Real" or "BinaryReal".
	 */
	public CarSideImpact(String solutionType) {
		numberOfVariables_ = 7;
		numberOfObjectives_ = 3;
		numberOfConstraints_ = 10;
		problemName_ = "CarSideImpact";

		upperLimit_ = new double[numberOfVariables_];
		lowerLimit_ = new double[numberOfVariables_];

		lowerLimit_[0] = 0.5;
		upperLimit_[0] = 1.5;

		lowerLimit_[1] = 0.45;
		upperLimit_[1] = 1.35;

		lowerLimit_[2] = 0.5;
		upperLimit_[2] = 1.5;

		lowerLimit_[3] = 0.5;
		upperLimit_[3] = 1.5;

		lowerLimit_[4] = 0.875;
		upperLimit_[4] = 2.625;

		lowerLimit_[5] = 0.4;
		upperLimit_[5] = 1.2;

		lowerLimit_[6] = 0.4;
		upperLimit_[6] = 1.2;

		if (solutionType.compareTo("BinaryReal") == 0) {
			solutionType_ = new BinaryRealSolutionType(this);
		} else if (solutionType.compareTo("Real") == 0) {
			solutionType_ = new RealSolutionType(this);
		} else {
			System.out.println("Error: solution type " + solutionType + " invalid");
			System.exit(-1);
		}
	} // CarSideImpact

	/**
	 * Evaluates a solution
	 *
	 * @param solution The solution to evaluate
	 * @throws JMException
	 */
	@Override
	public void evaluate(Solution solution) throws JMException {
		double[] x = new double[7]; // 7 decision variables
		double[] f = new double[3]; // 3 functions

		x[0] = solution.getDecisionVariables()[0].getValue();
		x[1] = solution.getDecisionVariables()[1].getValue();
		x[2] = solution.getDecisionVariables()[2].getValue();
		x[3] = solution.getDecisionVariables()[3].getValue();
		x[4] = solution.getDecisionVariables()[4].getValue();
		x[5] = solution.getDecisionVariables()[5].getValue();
		x[6] = solution.getDecisionVariables()[6].getValue();


		// First function
		f[0] = 1.98 + 4.9 * x[0] + 6.67 * x[1] + 6.98 * x[2]
				+ 4.01 * x[3] + 1.78 * x[4] + 0.00001 * x[5] + 2.73 * x[6];
		// Second function
		f[1] = 4.72 - 0.5 * x[3] - 0.19 * x[1] * x[2];

		// Third function
		f[2] = 0.5 * (10.58 - 0.674 * x[0] * x[1] - 0.67275 * x[1] + 16.45 - 0.489 * x[2] * x[6] - 0.843 * x[4] * x[5]);

		for (int i = 0; i < this.numberOfObjectives_; i++) {
			solution.setObjective(i, f[i]);
		}

	} // evaluate

	/**
	 * Evaluates the constraint overhead of a solution
	 *
	 * @param solution The solution
	 * @throws JMException
	 */
	@Override
	public void evaluateConstraints(Solution solution) throws JMException {
		double[] constraint = new double[10]; // 10 constraints
		double[] x = new double[7]; // 7 decision variables

		x[0] = solution.getDecisionVariables()[0].getValue();
		x[1] = solution.getDecisionVariables()[1].getValue();
		x[2] = solution.getDecisionVariables()[2].getValue();
		x[3] = solution.getDecisionVariables()[3].getValue();
		x[4] = solution.getDecisionVariables()[4].getValue();
		x[5] = solution.getDecisionVariables()[5].getValue();
		x[6] = solution.getDecisionVariables()[6].getValue();


		constraint[0] = -(1.16 - 0.3717 * x[1] * x[3] - 0.0092928 * x[2]);
		constraint[1] = -((0.261 - 0.0159 * x[0] * x[1] - 0.06486 * x[0] - 0.019 * x[1] * x[6] + 0.0144 * x[2] * x[4]
				+ 0.0154464 * x[5]) / 0.32 - 1);
		constraint[2] = -((0.214 + 0.00817 * x[4] - 0.045195 * x[0] - 0.0135168 * x[0]
				+ 0.03099 * x[1] * x[5] - 0.018 * x[1] * x[6] + 0.007176 * x[2]
				+ 0.023232 * x[2] - 0.00364 * x[4] * x[5] - 0.018 * x[1] * x[1]) / 0.32 - 1);

		constraint[3] = -((0.74 - 0.61 * x[1] - 0.031296 * x[2] - 0.031872 * x[6] + 0.227 * x[1] * x[1]) / 0.32 - 1);

		constraint[4] = -((28.98 + 3.818 * x[2] - 4.2 * x[0] * x[1] + 1.27296 * x[5] - 2.68065 * x[6]) / 32 - 1);

		constraint[5] = -((33.86 + 2.95 * x[2] - 5.057 * x[0] * x[1] - 3.795 * x[1] - 3.4431 * x[6] + 1.45728) / 32 - 1);

		constraint[6] = -((46.36 - 9.9 * x[1] - 4.4505 * x[0]) / 32 - 1);

		constraint[7] = -((4.72 - 0.5 * x[3] - 0.19 * x[1] * x[2]) / 4 - 1);

		constraint[8] = -((10.58 - 0.674 * x[0] * x[1] - 0.67275 * x[1]) / 9.9 - 1);

		constraint[9] = -((16.45 - 0.489 * x[2] * x[6] - 0.843 * x[4] * x[5]) / 15.7 - 1);

		double total = 0.0;
		int number = 0;
		for (int i = 0; i < numberOfConstraints_; i++) {
			if (constraint[i] < 0.0) {
				total += (-constraint[i]);
				number++;
			} // int
		} // for

		solution.setOverallConstraintViolation(total);
		solution.setNumberOfViolatedConstraint(number);
	}
} // 
