package jmetal.operators.clone;

import jmetal.core.SolutionSet;
import jmetal.util.JMException;

import java.util.HashMap;

public class entireclone extends Clone {

	/**
	 * @param No
	 */
	private int clonescale;

	public entireclone(HashMap<String, Object> parameters) {
		// this.clonesize=clonesize;
		super(parameters);
		if (parameters.get("clonescale") != null)
		//clonescale = (int) parameters.get("clonescale");
		{
			clonescale = Integer.valueOf(parameters.get("clonescale").toString());
		}
	} // proportional clone

	/**
	 * /** Executes the operation
	 *
	 * @return An object containing the offSprings
	 */
	@Override
	public Object execute(Object parent) throws JMException {
		SolutionSet parents = (SolutionSet) parent;
		// clonesize=parents.size();
		SolutionSet offSpring = new SolutionSet(clonescale * parents.size());
		for (int i = 0; i < parents.size(); i++) {
			for (int j = 0; j < clonescale; j++) {
				offSpring.add(parents.get(i));
			}
		}
		return offSpring;// */
	} // execute
}
