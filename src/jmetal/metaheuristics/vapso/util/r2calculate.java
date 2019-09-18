package jmetal.metaheuristics.vapso.util;

import jmetal.core.SolutionSet;
import org.apache.commons.math3.linear.Array2DRowRealMatrix;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.linear.RealVector;

public class r2calculate {
	public r2calculate() {
	}

	public double[] r2Calculate(SolutionSet population, double[][] lambdaVector) {
		RealMatrix populationMatrix = new Array2DRowRealMatrix(population.writeObjectivesToMatrix());
		double[] indelpoint = new double[populationMatrix.getColumnDimension()];
		for (int i = 0; i < populationMatrix.getColumnDimension(); i++) {
			indelpoint[i] = populationMatrix.getColumnVector(i).getMinValue();
			populationMatrix.setColumnVector(i, populationMatrix.getColumnVector(i).mapSubtract(indelpoint[i]));
		}
		RealMatrix lambdaMatrix = new Array2DRowRealMatrix(lambdaVector);
		int populationSize = populationMatrix.getRowDimension();
		int lambdaSize = lambdaVector.length;
		RealVector lambdaVectors;
		RealVector populationVector;
		double[][] r2 = new double[lambdaSize][populationSize];
		for (int i = 0; i < lambdaSize; i++) {
			lambdaVectors = lambdaMatrix.getRowVector(i);
			for (int j = 0; j < populationSize; j++) {
				populationVector = populationMatrix.getRowVector(j);
				populationVector = populationVector.ebeDivide(lambdaVectors);
				r2[i][j] = populationVector.getMaxValue();
			}

		}
		RealMatrix r2Matrix = new Array2DRowRealMatrix(r2);
		double[] r2s = new double[populationSize];
		int index;
		for (int i = 0; i < lambdaSize; i++) {
			index = r2Matrix.getRowVector(i).getMinIndex();
			r2s[index] += r2Matrix.getEntry(i, index);
		}
		for (int i = 0; i < populationSize; i++) {
			population.get(i).setR2indicator(r2s[i]);
		}
		return r2s;
	}
}
