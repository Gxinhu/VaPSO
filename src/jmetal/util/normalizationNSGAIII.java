package jmetal.util;

import jmetal.core.Solution;
import jmetal.core.SolutionSet;
import org.apache.commons.math3.linear.Array2DRowRealMatrix;
import org.apache.commons.math3.linear.MatrixUtils;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.linear.SingularValueDecomposition;

public class normalizationNSGAIII {
	SolutionSet mgPopulation;


	int obj;

	public double[] zideal;

	double[] zmax;

	double[][] extremePoints;

	double[] intercepts;


	public normalizationNSGAIII(SolutionSet population, int obj) {
		this.obj = obj;
		this.mgPopulation = population;

	}

	public static void main(String[] args) {
		SolutionSet front = new SolutionSet(3);
		Solution newSoution = new Solution(3);
		newSoution.setObjective(0, 0.0);
		newSoution.setObjective(1, 0.02759);
		newSoution.setObjective(2, 9.97);
		front.add(newSoution);
		Solution newSoution1 = new Solution(3);
		newSoution1.setObjective(0, 0.0);
		newSoution1.setObjective(1, 0.0);
		newSoution1.setObjective(2, 10.0);
		front.add(newSoution1);
		Solution newSoution2 = new Solution(3);
		newSoution2.setObjective(0, 0.0);
		newSoution2.setObjective(1, 0.2659);
		newSoution2.setObjective(2, 9.96);
		front.add(newSoution2);
		normalizationNSGAIII normalizations = new normalizationNSGAIII(front, 3);
		normalizations.execute();
		front.writeTransObjectivesToMatrix();
	}


	public void execute() {
		computeIdealPoint();

		computeMaxPoint();
		computeExtremePoints();
		computeIntercepts();
		normalizePopulation();
	}

	void computeIdealPoint() {
		zideal = new double[obj];

		for (int j = 0; j < obj; j++) {
			zideal[j] = Double.MAX_VALUE;

			for (int i = 0; i < mgPopulation.size(); i++) {
				if (mgPopulation.get(i).getObjective(j) < zideal[j]) {
					zideal[j] = mgPopulation.get(i).getObjective(j);
				}
			}
		}

	}

	void computeMaxPoint() {
		zmax = new double[obj];

		for (int j = 0; j < obj; j++) {
			zmax[j] = Double.MIN_VALUE;

			for (int i = 0; i < mgPopulation.size(); i++) {
				if (mgPopulation.get(i).getObjective(j) > zmax[j]) {
					zmax[j] = mgPopulation.get(i).getObjective(j);
				}
			}
		}
	}

	void computeExtremePoints() {
		extremePoints = new double[obj][obj];

		for (int j = 0; j < obj; j++) {
			int index = -1;
			double min = Double.MAX_VALUE;

			for (int i = 0; i < mgPopulation.size(); i++) {
				double asfValue = asfFunction(mgPopulation.get(i), j);
				if (asfValue < min) {
					min = asfValue;
					index = i;
				}
			}

			for (int k = 0; k < obj; k++) {
				extremePoints[j][k] = mgPopulation.get(index).getObjective(k);
			}
		}
	}

	void computeIntercepts() {

		intercepts = new double[obj];

		double[][] temp = new double[obj][obj];

		for (int i = 0; i < obj; i++) {
			for (int j = 0; j < obj; j++) {
				double val = extremePoints[i][j] - zideal[j];
				temp[i][j] = val;
			}
		}

		RealMatrix EX=new Array2DRowRealMatrix(temp);
		SingularValueDecomposition singularValueDecomposition=new SingularValueDecomposition(EX);
		int rank=singularValueDecomposition.getRank();
		if (rank == EX.getRowDimension()) {
			double[] u = new double[obj];
			for (int j = 0; j < obj; j++) {
				u[j] = 1;
			}
			RealMatrix EX_inverse;
			EX_inverse =  MatrixUtils.inverse(EX);
			RealMatrix UM=new Array2DRowRealMatrix(u);
			RealMatrix AL=EX_inverse.multiply(UM);
			int j;
			for (j = 0; j < obj; j++) {

				double aj = 1.0 / AL.getEntry(j, 0) + zideal[j];

				if ((aj > zideal[j]) && (!Double.isInfinite(aj)) && (!Double.isNaN(aj))) {
					intercepts[j] = aj;
				} else {
					break;
				}
			}
			if (j != obj) {
				if (obj >= 0) {
					System.arraycopy(zmax, 0, intercepts, 0, obj);
				}
			}

		} else {
			if (obj >= 0) {
				System.arraycopy(zmax, 0, intercepts, 0, obj);
			}
		}

	}

	private void normalizePopulation() {
		for (int i = 0; i < mgPopulation.size(); i++) {
			Solution sol = mgPopulation.get(i);

			for (int j = 0; j < obj; j++) {

				double val = (sol.getObjective(j) - zideal[j])
						/ (intercepts[j] - zideal[j]);

				sol.setTranslatedObjectives(val, j);
			}
		}
	}


	private double asfFunction(Solution sol, int j) {
		double max = Double.MIN_VALUE;
		double epsilon = 1.0E-6;

		for (int i = 0; i < obj; i++) {

			double val = Math.abs(sol.getObjective(i) - zideal[i]);

			if (j != i) {
				val = val / epsilon;
			}

			if (val > max) {
				max = val;
			}
		}

		return max;
	}


}
