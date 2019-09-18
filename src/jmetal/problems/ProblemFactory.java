//  ProblemFactory.java
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

package jmetal.problems;

import jmetal.core.Problem;
import jmetal.util.Configuration;
import jmetal.util.JMException;

import java.lang.reflect.Constructor;

/**
 * This class represents a factory for problems
 */
public class ProblemFactory {
	/**
	 * Creates an object representing a problem
	 *
	 * @param name   Name of the problem
	 * @param params Parameters characterizing the problem
	 * @return The object representing the problem
	 * @throws JMException
	 */
	public Problem getProblem(String name, Object[] params) throws JMException {
		// Params are the arguments
		// The number of argument must correspond with the problem constructor params

		String base = "jmetal.problems.";


		if (name.equalsIgnoreCase("TSP")) {
			base += "singleobjective.";
		} else if (name.equalsIgnoreCase("mQAP")) {
			base += "mqap.";
		} else if (name.equalsIgnoreCase("QAP")) {
			base += "qap.";
		} else if (name.substring(0, 2).equalsIgnoreCase("JY")) {
			base += "jiangYang.";
		} else if (name.substring(0, name.length() - 1).equalsIgnoreCase("WFG")) {
			base += "WFG.";
		} else if (name.substring(0, name.length() - 1).equalsIgnoreCase("MOP")) {
			base += "M2M.";
		}
//    else if (name.contains("WFG") && name.substring(0,5).equalsIgnoreCase("Minus") )
//        base += "WFG.";
//    else if (name.contains("WFG") && name.substring(4,9).equalsIgnoreCase("Mixed") )
//        base += "WFG.";
//    else if (name.substring(name.length()-4,name.length()-1).equalsIgnoreCase("WFG"))
//        base += "WFG.";
		else if (name.substring(0, 1).equalsIgnoreCase("F") || name.substring(0, 2).equalsIgnoreCase("CF")) {
			base += "ZHX.";
		} else if (name.substring(0, name.length() - 2).equalsIgnoreCase("ZHX")
				|| name.substring(0, name.length() - 2).equalsIgnoreCase("ZHXII")) {
			base += "ZHX.";
		} else if (name.substring(0, name.length() - 1).equalsIgnoreCase("WZF")) {
			base += "wangZhang.";
		} else if (name.substring(0, name.length() - 1).equalsIgnoreCase("DTLZ")) {
			base += "DTLZ.";
		} else if (name.substring(0, name.length() - 1).equalsIgnoreCase("LDTLZ")) {
			base += "LDTLZ.";
		} else if (name.contains("DTLZ") && name.substring(0, 2).equalsIgnoreCase("Co")) {
			base += "DTLZ.";
		} else if (name.contains("DTLZ") && name.substring(0, 1).equalsIgnoreCase("C")) {
			base += "CDTLZ.";
		} else if (name.contains("DTLZ")) {
			base += "DTLZ.";
		} else if (name.substring(0, name.length() - 1).equalsIgnoreCase("UF")) {
			base += "cec2009Competition.";
		} else if (name.equalsIgnoreCase("R2_DTLZ2_M5") || name.equalsIgnoreCase("R2_DTLZ3_M5") || name.equalsIgnoreCase("WFG1_M5")) {
			base += "cec2009Competition.";
		}
//    else if (name.substring(0,name.length()-1).equalsIgnoreCase("CF"))
//        base += "cec2009Competition.";
//    else if (name.substring(0,name.length()-2).equalsIgnoreCase("UF"))
//      base += "cec2009Competition.";
//    else if (name.substring(0,name.length()-2).equalsIgnoreCase("CF"))
//        base += "cec2009Competition.";    
		else if (name.substring(0, name.length() - 1).equalsIgnoreCase("ZDT")) {
			base += "ZDT.";
		} else if (name.substring(0, name.length() - 3).equalsIgnoreCase("ZZJ07")) {
			base += "ZZJ07.";
		} else if (name.substring(0, name.length() - 3).equalsIgnoreCase("LZ09")) {
			base += "LZ09.";
		} else if (name.length() - 7 >= 0 && name.substring(0, name.length() - 7).equalsIgnoreCase("DTLZ")) {
			base += "DTLZ."; // for DTLZ2Convex problem
		} else if (name.substring(0, name.length() - 3).equalsIgnoreCase("LZ06")) {
			base += "LZ06.";
		} else if (name.contains("MaF")) {
			base += "MaF.";
		}

		try {
			Class problemClass = Class.forName(base + name);

			Constructor[] constructors = problemClass.getConstructors();
			int i = 0;
			//find the constructor
			while ((i < constructors.length) &&
					(constructors[i].getParameterTypes().length != params.length)) {
				i++;
			}

			// constructors[i] is the selected one constructor
			Problem problem = (Problem) constructors[i].newInstance(params);
			return problem;
		}// try
		catch (Exception e) {
			Configuration.getLogger_().severe("ProblemFactory.getProblem: " +
					"Problem '" + name + "' does not exist. " +
					"Please, check the problem names in jmetal/problems");
			e.printStackTrace();
			throw new JMException("Exception in " + name + ".getProblem()");
		} // catch
	}
}
