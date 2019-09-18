//MatrixMult Order 4.java
//
//Author:
//   Y Xiang<gzhuxiang_yi@163.com>
//

package jmetal.problems;

import jmetal.core.Problem;
import jmetal.core.Solution;
import jmetal.core.Variable;
import jmetal.encodings.solutionType.BinaryRealSolutionType;
import jmetal.encodings.solutionType.RealSolutionType;
import jmetal.util.JMException;

/**
 * Class representing problem MatrixMultO4
 */
public class MatrixMultO4 extends Problem {

	/**
	 * Constructor.
	 * Creates a default instance of the Water problem.
	 *
	 * @param solutionType The solution type must "Real" or "BinaryReal".
	 */
	public MatrixMultO4(String solutionType) {
		numberOfVariables_ = 9;
		numberOfObjectives_ = 3;
		numberOfConstraints_ = 2;
		problemName_ = "MatrixMultO4";

		upperLimit_ = new double[numberOfVariables_];
		lowerLimit_ = new double[numberOfVariables_];

		for (int var = 0; var < numberOfVariables_; var++) {
			lowerLimit_[var] = 0.0;
			upperLimit_[var] = 1.0;
		} // for

		if (solutionType.compareTo("BinaryReal") == 0) {
			solutionType_ = new BinaryRealSolutionType(this);
		} else if (solutionType.compareTo("Real") == 0) {
			solutionType_ = new RealSolutionType(this);
		} else {
			System.out.println("Error: solution type " + solutionType + " invalid");
			System.exit(-1);
		}
	} // MatrixMultO4

	/**
	 * Evaluates a solution
	 *
	 * @param solution The solution to evaluate
	 * @throws JMException
	 */
	@Override
	public void evaluate(Solution solution) throws JMException {
		Variable[] gen = solution.getDecisionVariables();
		double[] obj = new double[3]; // 3 functions
		double a, b, c, d, e, f, g, h, i, j;
		double tau = gen[0].getValue();
		int q = 5;

		a = gen[1].getValue();
		b = gen[2].getValue();
		e = gen[3].getValue();
		f = gen[4].getValue();
		g = gen[5].getValue();
		h = gen[6].getValue();
		i = gen[7].getValue();
		j = gen[8].getValue();

		c = f * e * j / (h * h);
		d = e * g * j / (h * i);

		double A0 = 2 * a + 2 * b + 2 * c + 2 * d + e;
		double A1 = 2 * b + 2 * f + 2 * g + 2 * h;
		double A2 = 2 * c + 2 * g + 2 * i + j;
		double A3 = 2 * d + 2 * h + 2 * j;
		double A4 = 2 * e + 2 * h + i;
		double A5 = 2 * d + 2 * g;
		double A6 = 2 * c + f;
		double A7 = 2 * b;
		double A8 = a;

		double SUM = A0 + A1 + A2 + A3 + A4 + A5 + A6 + A7 + A8;
		double V008 = 1;
		double V017 = Math.pow(4 * q, tau);
		double V026 = Math.pow(4 + 6 * q * q, tau);
		double V035 = Math.pow(12 * q + 4 * q * q * q, tau);
		double V044 = Math.pow(6 + 12 * q * q + Math.pow(q, 4), tau);
		double V116 = Math.pow(2, 2 / 3) * Math.pow(8 * Math.pow(q, 3 * tau) * (Math.pow(q, 3 * tau) + 2) + Math.pow(2 * q, 6 * tau), 1 / 3);
		double V125 = Math.pow(2, 2 / 3) * Math.pow(2 * Math.pow(q * q + 2, 3 * tau) + 4 * Math.pow(q, 3 * tau) * (Math.pow(q, 3 * tau) + 2), 1 / 3)
				* Math.pow((4 * Math.pow(q, 3 * tau) * (Math.pow(q, 3 * tau) + 2)) / (Math.pow(q * q + 2, 3 * tau)) + Math.pow(2 * q, 3 * tau), 1 / 3);
		double V134 = Math.pow(2, 2 / 3) * Math.pow(Math.pow(2 * q, 3 * tau) + 4 * Math.pow(q, 3 * tau) * (Math.pow(q, 3 * tau) + 2), 1 / 3)
				* Math.pow(2 + 2 * Math.pow(2 * q, 3 * tau) + Math.pow(q * q + 2, 3 * tau), 1 / 3);
		double V224 = Math.pow(2 * Math.pow(q * q + 2, 3 * tau) + 4 * Math.pow(q, 3 * tau) * (Math.pow(q, 3 * tau) + 2), 2 / 3)
				* Math.pow(2 + 2 * Math.pow(2 * q, 3 * tau) + Math.pow(q * q + 2, 3 * tau), 1 / 3)
				/ Math.pow(q * q + 2, tau);
		double V233 = Math.pow(2 * Math.pow(q * q + 2, 3 * tau) + 4 * Math.pow(q, 3 * tau) * (Math.pow(q, 3 * tau) + 2), 1 / 3)
				* Math.pow(Math.pow(2 * q, 3 * tau) + 4 * Math.pow(q, 3 * tau) * (Math.pow(q, 3 * tau) + 2), 2 / 3)
				/ (Math.pow(q, tau) * Math.pow(Math.pow(q, 3 * tau) + 2, 1 / 3));

		double LEFT = 4 * Math.log(q + 2) + A0 * Math.log(A0) + A1 * Math.log(A1) + A2 * Math.log(A2) + A3 * Math.log(A3) + A4 * Math.log(A4) + A5 * Math.log(A5)
				+ A6 * Math.log(A6) + A7 * Math.log(A7) + A8 * Math.log(A8);
		double RIGHT = 6 * b * Math.log(V017) + 6 * c * Math.log(V026) + 6 * d * Math.log(V035) + 3 * e * Math.log(V044) + 3 * f * Math.log(V116) + 6 * g * Math.log(V125)
				+ 6 * h * Math.log(V134) + 3 * i * Math.log(V224) + 3 * j * Math.log(V233);

//	// First function
		obj[0] = tau;
		// Second function
		obj[1] = Math.abs(SUM - 1);//* Math.abs(SUM-1) +  Math.abs(LEFT-RIGHT) * Math.abs(LEFT-RIGHT);
		// Third function
		obj[2] = Math.abs(LEFT - RIGHT); // equality constraints

		// First function
//	obj[0] = Math.abs(SUM-1);
//	// Second function
//	obj[1] = Math.abs(LEFT-RIGHT);


		solution.setObjective(0, obj[0]);
		solution.setObjective(1, obj[1]);
		solution.setObjective(2, obj[2]);


	} // evaluate

	/**
	 * Evaluates the constraint overhead of a solution
	 *
	 * @param solution The solution
	 * @throws JMException
	 */
	@Override
	public void evaluateConstraints(Solution solution) throws JMException {
//	   double [] constraint = new double[2]; // 2 constraints
//	  
//	   constraint[0] = solution.getObjective(1);
//	   constraint[1] = solution.getObjective(2);	   
//			   
//	   solution.setNumberOfConstraints(numberOfConstraints_);       
//	    
//	    for (int j = 0;j< numberOfConstraints_; j++){ // For each constraint
//	    	solution.setConstraint(j, constraint[j]);
//	    }
//	    
//	    double total = 0.0;
//	    int number = 0;
//	    for (int i = 0; i < numberOfConstraints_; i++) {
//	      if (constraint[i] > Math.pow(10, -12)){
//	        total+= (constraint[i]);
//	        number++;
//	      } // int
//	    } // for
//	        
//	    solution.setOverallConstraintViolation(total);    
//	    solution.setNumberOfViolatedConstraint(number);    
	} // evaluateConstraints

} // MatrixMultO4


