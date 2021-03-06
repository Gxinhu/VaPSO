/**
 * VaPSORunner.java
 *
 * @author Xin.Hu
 */
package jmetal.metaheuristics.vapso;

import jmetal.core.Algorithm;
import jmetal.core.Operator;
import jmetal.core.Problem;
import jmetal.core.SolutionSet;
import jmetal.metaheuristics.cricleselectproblem;
import jmetal.operators.clone.CloneFactory;
import jmetal.operators.crossover.CrossoverFactory;
import jmetal.operators.mutation.MutationFactory;
import jmetal.problems.ProblemFactory;
import jmetal.qualityIndicator.QualityIndicator;
import jmetal.qualityIndicator.fastHypervolume.wfg.wfgHvPlatEMO;
import jmetal.qualityIndicator.hypeHypervolume.HypeHV;
import jmetal.util.Configuration;
import jmetal.util.JMException;
import jmetal.util.plot.pythonplot;

import java.io.IOException;
import java.util.HashMap;
import java.util.logging.FileHandler;
import java.util.logging.Logger;


public class VaPSORunner {

	public static void main(String[] args) throws JMException,
			SecurityException, IOException, ClassNotFoundException, NullPointerException, InterruptedException {
		// the numbers of objectives
		int m = 5;
		// The number of low represent the problems: 6-12 DTLZ1-7, 13-21 WFG1-9.
		final int low = 6;
		Logger logger = Configuration.getLogger_();
		FileHandler fileHandler = new FileHandler("vapso.log");
		logger.addHandler(fileHandler);
		for (int fun = low; fun <= low; fun++) {
			Problem problem = null;
			Algorithm algorithm;
			QualityIndicator indicators;
			indicators = null;
			if (args.length == 1) {
				Object[] params = {"Real"};
				problem = (new ProblemFactory()).getProblem(args[0], params);
			} else if (args.length == 2) {
				Object[] params = {"Real"};
				problem = (new ProblemFactory()).getProblem(args[0], params);
				indicators = new QualityIndicator(problem, args[1]);
			} else {
				problem = new cricleselectproblem(problem, indicators, fun, m).getProblem();
				indicators = new cricleselectproblem(problem, indicators, fun, m).getindicator();
			}
			// init parameter of algorithm
			algorithm = new VaPSO(problem);
			coffientSetting(algorithm, problem, fun);
			SolutionSet population;
			long initTime = System.currentTimeMillis();
			population = algorithm.execute();
			long endTime = System.currentTimeMillis() - initTime;

			logger.info("Total run time is" + endTime + "ms");
			wfgHvPlatEMO wfgHv = new wfgHvPlatEMO(population.writeObjectivesToMatrix(), problem.getName());
			assert indicators != null;
			HypeHV hype = new HypeHV(population.writeObjectivesToMatrix(), problem.getName());
			double HV;
			if (problem.getNumberOfObjectives() > 8) {
				HV = hype.calculatewfghv();
			} else {
				HV = wfgHv.calculatewfghv();
			}
			logger.info(problem.getName()
					+ "\nHyperVolume: " + HV
					+ "\nEPSILON    : " + indicators.getEpsilon(population)
					+ "\nGD         : " + indicators.getGD(population)
					+ "\nIGD        : " + indicators.getCEC_IGD(population)
					+ "\nSpread     : " + indicators.getGeneralizedSpread(population)
					+ "\nSpace        : " + indicators.getSpace(population)
					+ "\nIGD+        : " + indicators.getInvertedGenerationalDistancePlus(population)
					+ "\nNumberOfPF        : " + population.size()
			);
			pythonplot plot = new pythonplot(population.writeObjectivesToMatrix(), problem.getName());
			plot.exectue();
		}
	}

	private static void coffientSetting(Algorithm algorithm, Problem problem, int fun) throws JMException {
		Operator clone = null;
		// Crossover operator
		Operator crossover;
		// Mutation operator
		Operator mutation;
		algorithm.setInputParameter("normalize", true);
		if (problem.getNumberOfObjectives() == 5) {
			algorithm.setInputParameter("maxIterations", 100000 / 126);
			algorithm.setInputParameter("swarmSize", 126);
			HashMap<String, Integer> parameters = new HashMap<String, Integer>();
			parameters.put("clonesize", 126);
			clone = CloneFactory.getClone("ShiftedDistanceClone", parameters);
		} else if (problem.getNumberOfObjectives() == 8) {
			algorithm.setInputParameter("maxIterations", 100000 / 156);
			algorithm.setInputParameter("swarmSize", 156);
			HashMap<String, Integer> parameters = new HashMap<String, Integer>();
			parameters.put("clonesize", 156);
			clone = CloneFactory.getClone("ShiftedDistanceClone", parameters);
		} else if (problem.getNumberOfObjectives() == 10) {
			algorithm.setInputParameter("maxIterations", 100000 / 275);
			algorithm.setInputParameter("swarmSize", 275);
			HashMap<String, Integer> parameters = new HashMap<String, Integer>();
			parameters.put("clonesize", 275);
			clone = CloneFactory.getClone("ShiftedDistanceClone", parameters);
		} else if (problem.getNumberOfObjectives() == 15) {
			algorithm.setInputParameter("maxIterations", 150000/135);
			algorithm.setInputParameter("swarmSize", 135);
			HashMap<String, Integer> parameters = new HashMap<String, Integer>();
			parameters.put("clonesize", 135);
			clone = CloneFactory.getClone("ShiftedDistanceClone", parameters);
		}
		HashMap<String, Double> parameters = new HashMap<String, Double>();
		parameters.put("probability", 1.0);
		parameters.put("distributionIndex", 30.0);
		crossover = CrossoverFactory.getCrossoverOperator("SBXCrossover", parameters);
		parameters = new HashMap<>();
		parameters.put("probability", 1.0 / problem.getNumberOfVariables());
		parameters.put("distributionIndex", 20.0);
		mutation = MutationFactory.getMutationOperator("PolynomialMutation", parameters);
		// Add the operators to the algorithm
		algorithm.addOperator("clone", clone);
		algorithm.addOperator("crossover", crossover);
		algorithm.addOperator("mutation", mutation);
	}

}