package jmetal.metaheuristics;

import jmetal.core.Problem;
import jmetal.problems.DTLZ.*;
import jmetal.problems.WFG.*;
import jmetal.problems.ZDT.*;
import jmetal.problems.cec2009Competition.*;
import jmetal.qualityIndicator.QualityIndicator;

public class cricleselectproblem {
	Problem problem;
	QualityIndicator indicators;
	int fun;
	int objectives;

	public cricleselectproblem(Problem problem, QualityIndicator indicator, int fun, int m) {
		this.problem = problem;
		this.indicators = indicator;
		this.fun = fun;
		this.objectives = m;
	}

	public Problem getProblem() throws ClassNotFoundException {

		switch (fun) {
			case 13: {
				problem = new WFG1("Real", 2 * (objectives - 1), 20, objectives);
				break;
			}//problem = new WFG1("Real");
			case 14: {
				problem = new WFG2("Real", 2 * (objectives - 1), 20, objectives);
				break;
			}//problem = new WFG1("Real");
			case 15: {
				problem = new WFG3("Real", 2 * (objectives - 1), 20, objectives);
				break;
			}
			case 16: {
				problem = new WFG4("Real", 2 * (objectives - 1), 20, objectives);
				break;
			}//problem = new WFG1("Real");
			case 17: {
				problem = new WFG5("Real", 2 * (objectives - 1), 20, objectives);
				break;
			}
			case 18: {
				problem = new WFG6("Real", 2 * (objectives - 1), 20, objectives);
				break;
			}//problem = new WFG1("Real");
			case 19: {
				problem = new WFG7("Real", 2 * (objectives - 1), 20, objectives);
				break;
			}//problem = new WFG1("Real");
			case 20: {
				problem = new WFG8("Real", 2 * (objectives - 1), 20, objectives);
				break;
			}
			case 21: {
				problem = new WFG9("Real", 2 * (objectives - 1), 20, objectives);
				break;
			}

			case 1: {
				problem = new ZDT1("Real");
				break;
			}//problem = new WFG1("Real");
			case 2: {
				problem = new ZDT2("Real");
				break;
			}//problem = new WFG1("Real");
			case 3: {
				problem = new ZDT3("Real");
				break;
			}
			case 4: {
				problem = new ZDT4("Real", 10);
				break;
			}//problem = new WFG1("Real");
			case 5: {
				problem = new ZDT6("Real", 10);
				break;
			}//problem = new WFG1("Real");
			case 6: {
				problem = new DTLZ1("Real", objectives + 4, objectives);
				break;
			}
			case 7: {
				problem = new DTLZ2("Real", objectives + 9, objectives);
				break;
			}//problem = new WFG1("Real");
			case 8: {
				problem = new DTLZ3("Real", objectives + 9, objectives);
				break;
			}//problem = new WFG1("Real");
			case 9: {
				problem = new DTLZ4("Real", objectives + 9, objectives);
				break;
			}
			case 10: {
				problem = new DTLZ5("Real", objectives + 9, objectives);
				break;
			}//problem = new WFG1("Real");
			case 11: {
				problem = new DTLZ6("Real", objectives + 9, objectives);
				break;
			}//problem = new WFG1("Real");
			case 12: {
				problem = new DTLZ7("Real", objectives + 19, objectives);
				break;
			}

			default:
				System.exit(0);
		}

		return problem;
	}

	public QualityIndicator getindicator() {
			switch (fun) {
				case 13: {
					indicators = new QualityIndicator(problem, "./PF/WFG/" + objectives + "d/WFG1.pf");
					break;
				}//problem = new WFG1("Real");
				case 14: {
					indicators = new QualityIndicator(problem, "./PF/WFG/" + objectives + "d/WFG2.pf");
					break;
				}//problem = new WFG1("Real");
				case 15: {
					indicators = new QualityIndicator(problem, "./PF/WFG/" + objectives + "d/WFG3.pf");
					break;
				}
				case 16: {
					indicators = new QualityIndicator(problem, "./PF/WFG/" + objectives + "d/WFG4.pf");
					break;
				}//problem = new WFG1("Real");
				case 17: {
					indicators = new QualityIndicator(problem, "./PF/WFG/" + objectives + "d/WFG5.pf");
					break;
				}
				case 18: {
					indicators = new QualityIndicator(problem, "./PF/WFG/" + objectives + "d/WFG6.pf");
					break;
				}//problem = new WFG1("Real");
				case 19: {
					indicators = new QualityIndicator(problem, "./PF/WFG/" + objectives + "d/WFG7.pf");
					break;
				}//problem = new WFG1("Real");
				case 20: {
					indicators = new QualityIndicator(problem, "./PF/WFG/" + objectives + "d/WFG8.pf");
					break;
				}
				case 21: {
					indicators = new QualityIndicator(problem, "./PF/WFG/" + objectives + "d/WFG9.pf");
					break;
				}
				case 1: {
					indicators = new QualityIndicator(problem, "./PF/ZDT/ZDT1.pf");
					break;
				}//problem = new WFG1("Real");
				case 2: {
					indicators = new QualityIndicator(problem, "./PF/ZDT/ZDT2.pf");
					break;
				}//problem = new WFG1("Real");
				case 3: {
					indicators = new QualityIndicator(problem, "./PF/ZDT/ZDT3.pf");
					break;
				}
				case 4: {
					indicators = new QualityIndicator(problem, "./PF/ZDT/ZDT4.pf");
					break;
				}//problem = new WFG1("Real");
				case 5: {
					indicators = new QualityIndicator(problem, "./PF/ZDT/ZDT6.pf");
					break;
				}//problem = new WFG1("Real");
				case 6: {
					indicators = new QualityIndicator(problem, "./PF/DTLZ/" + objectives + "d/DTLZ1.pf");
					break;
				}
				case 7: {
					indicators = new QualityIndicator(problem, "./PF/DTLZ/" + objectives + "d/DTLZ2.pf");
					break;
				}//problem = new WFG1("Real");
				case 8: {
					indicators = new QualityIndicator(problem, "./PF/DTLZ/" + objectives + "d/DTLZ3.pf");
					break;
				}//problem = new WFG1("Real");
				case 9: {
					indicators = new QualityIndicator(problem, "./PF/DTLZ/" + objectives + "d/DTLZ4.pf");
					break;
				}
				case 10: {
					indicators = new QualityIndicator(problem, "./PF/DTLZ/" + objectives + "d/DTLZ5.pf");
					break;
				}//problem = new WFG1("Real");
				case 11: {
					indicators = new QualityIndicator(problem, "./PF/DTLZ/" + objectives + "d/DTLZ6.pf");
					break;
				}//problem = new WFG1("Real");
				case 12: {
					indicators = new QualityIndicator(problem, "./PF/DTLZ/" + objectives + "d/DTLZ7.pf");
					break;
				}
				default:
					System.exit(0);
			}
		return indicators;
	}

}
