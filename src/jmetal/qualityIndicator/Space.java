//  GenerationalDistance.java
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

package jmetal.qualityIndicator;

/**
 * This class implements the generational distance indicator. It can be used also
 * as a command line by typing:
 * "java jmetal.qualityIndicator.GenerationalDistance <solutionFrontFile>
 * <trueFrontFile> <getNumberOfObjectives>"
 * Reference: Soctt Jason 1995
 */
public class Space {
	public jmetal.qualityIndicator.util.MetricsUtil utils_;
	//utils_ is used to access to the

	/**
	 * Constructor.
	 * Creates a new instance of the Space metric.
	 */
	public Space() {
		utils_ = new jmetal.qualityIndicator.util.MetricsUtil();
	} // GenerationalDistance

	/**
	 * Returns the generational distance value for a given front
	 *
	 * @param front           The front
	 * @param trueParetoFront The true pareto front
	 */
	public double space(double[][] front,
	                    double[][] trueParetoFront,
	                    int numberOfObjectives) {

		double[] distance = new double[front.length];
		double aveDistance;
		double sumDistance = 0;
		for (int i = 0; i < front.length; i++) {
			double min = Double.POSITIVE_INFINITY;
			for (int j = 0; j < front.length; j++) {
				if (i != j) {
					double temp = 0;
					for (int k = 0; k < front[j].length; k++) {
						temp += Math.abs(front[i][k] - front[j][k]);
					}
					if (temp < min) {
						min = temp;
					}
				}
			}
			distance[i] = min;
			sumDistance += distance[i];
		}
		aveDistance = sumDistance / front.length;
		sumDistance = 0;
		for (int i = 0; i < front.length; i++) {
			sumDistance += Math.pow((aveDistance - distance[i]), 2);
		}
		return Math.sqrt(sumDistance / (front.length - 1));
	} // Space

	public static void main(String[] args) {
		double[][] test = {{1, 0, 0}, {2, 1, 0}, {3, 3, 1}};
		Space space = new Space();
		System.out.println(space.space(test, test, 3));
	}


} // GenerationalDistance
