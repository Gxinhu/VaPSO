package jmetal.operators.clone;

import jmetal.core.SolutionSet;
import jmetal.util.JMException;

import java.util.HashMap;

public class ShiftedDistanceClone extends Clone {

	/**
	 * @param No
	 */
	private int clonesize;

	public ShiftedDistanceClone(HashMap<String, Object> parameters) {
		super(parameters);
		if (parameters.get("clonesize") != null) {
			clonesize = Integer.valueOf(parameters.get("clonesize").toString());
		}
	}
	// proportional clone

	/**
	 * /** Executes the operation
	 *
	 * @param parent parent population
	 * @return An object containing the offSprings
	 */
	@Override
	public Object execute(Object parent) throws JMException {
		SolutionSet parents = (SolutionSet) parent;
		SolutionSet offSpring = new SolutionSet(clonesize);
		double sum_distance = 0.0;
		int k = 0;
		for (k = 0; k < parents.size(); k++) {
			sum_distance += parents.get(k).getCrowdingDistance();

		} // for
		//begin to clone
		//clone number of each parent
		double[] clones = new double[parents.size()];
		for (k = 0; k < parents.size(); k++) {
			clones[k] = Math.ceil(clonesize * parents.get(k).getCrowdingDistance() / sum_distance);
			//all individual are to one point
			if (sum_distance == 0) {
				clones[k] = Math.ceil((double) clonesize / parents.size());
				System.out.print("zeros");
				System.out.print(clones[k] + " ");
			}
		}
		int remain = clonesize;
		int i = 0;
		for (k = 0; k < parents.size(); k++) {
			for (int l = 0; l < clones[k]; l++) {
				if (remain > 0) {
					offSpring.add(parents.get(k));
					remain--;
				}
				i++;
			}
			if (remain == 0) {
				break;
			}
			if (i > 400) {
				System.out.print("zeros400");
			}
		}

		return offSpring;
	} // execute
}
