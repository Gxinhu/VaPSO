//  Distance.java
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

package jmetal.util;

import jmetal.core.SolutionSet;
import jmetal.util.comparators.CosineComparator;

/**
 * This class implements some utilities for calculating degree
 */
public class Degree {

	/**
	 * Constructor.
	 */
	public Degree() {
		//do nothing.
	} // Distance

	/**
	 * Assigns crowding distances to all solutions in a <code>SolutionSet</code>.
	 *
	 * @param solutionSet The <code>SolutionSet</code>.
	 * @param nObjs       Number of objectives.
	 */
	public void crowdingDegreeAssignment(SolutionSet solutionSet, int nObjs) {
		int size = solutionSet.size();

		if (size == 0) {
			return;
		}

		if (size == 1) {
			solutionSet.get(0).setDegree(Double.POSITIVE_INFINITY);
			return;
		} // if

		if (size == 2) {
			solutionSet.get(0).setDegree(Double.POSITIVE_INFINITY);
			solutionSet.get(1).setDegree(Double.POSITIVE_INFINITY);
			return;
		} // if

		//Use a new SolutionSet to alter original solutionSet
		SolutionSet front = new SolutionSet(size);
		for (int i = 0; i < size; i++) {
			front.add(solutionSet.get(i));
		}

		for (int i = 0; i < size; i++) {
			front.get(i).setDegree(0.0);
		}

		double DegreeMax;
		double DegreeMin;
		double degree;

		for (int i = 0; i < nObjs; i++) {
			// Sort the population by Obj n
			front.sort(new CosineComparator(i));
			DegreeMin = front.get(0).getCosine(i);
			DegreeMax = front.get(front.size() - 1).getCosine(i);

			//Set de crowding distance
			front.get(0).setDegree(Double.POSITIVE_INFINITY);
			front.get(size - 1).setDegree(Double.POSITIVE_INFINITY);
			for (int j = 1; j < size - 1; j++) {
				degree = front.get(j + 1).getCosine(i) - front.get(j - 1).getCosine(i);
				degree = degree / (DegreeMax - DegreeMin);
				degree += front.get(j).getDegree();
				front.get(j).setDegree(degree);
			} // for
		}

	} // crowdingDistanceAssignment
} // Distance

