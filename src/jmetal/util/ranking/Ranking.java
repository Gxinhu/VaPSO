package jmetal.util.ranking;

import jmetal.core.SolutionSet;

public interface Ranking {
	SolutionSet getSubfront(int layer);

	int getNumberOfSubfronts();
}
