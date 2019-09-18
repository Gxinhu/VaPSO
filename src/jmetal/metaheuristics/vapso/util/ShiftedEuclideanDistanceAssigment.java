
package jmetal.metaheuristics.vapso.util;

import jmetal.core.Problem;
import jmetal.core.SolutionSet;
import jmetal.util.DistanceShifted;
import jmetal.util.normalizationNSGAIII;

import java.util.Arrays;


public class ShiftedEuclideanDistanceAssigment {
	private int numberOfObjectives;

	public ShiftedEuclideanDistanceAssigment(Problem problem) {
		this.numberOfObjectives = problem.getNumberOfObjectives();

	}

	public void fitnessCompute(SolutionSet solutionSet) {
		int size = solutionSet.size();
		//Use a new SolutionSet to elite alter original solutionSet
		SolutionSet front = new SolutionSet(size);
		for (int i = 0; i < size; i++) {
			front.add(solutionSet.get(i));
		}
		normalizationNSGAIII normalizationnsga3 = new normalizationNSGAIII(front,numberOfObjectives);
		normalizationnsga3.execute();
		sdeassigntocrowdingdistancefield(front);
	}

	private void sdeassigntocrowdingdistancefield(SolutionSet solutionSet) {
		int size = solutionSet.size();
		if (size == 0) {
			return;
		}
		if (size == 1) {
			solutionSet.get(0).setCrowdingDistance(1);
			return;
		} // if

		if (size == 2) {
			solutionSet.get(0).setCrowdingDistance(1);
			solutionSet.get(1).setCrowdingDistance(1);
			return;
		} // if

		for (int i = 0; i < size; i++) {
			solutionSet.get(i).setCrowdingDistance(0.0);
		}

		DistanceShifted distanceShifted = new DistanceShifted();
		double[][] SDEDistance = distanceShifted.translateDistanceMatrixShifted(solutionSet);
		int t = 1;
		for (int i = 0; i < SDEDistance.length; i++) {
			Arrays.sort(SDEDistance[i]);
			double tDistance = SDEDistance[i][t];
			solutionSet.get(i).setCrowdingDistance(tDistance);
		} // for

	}


}
