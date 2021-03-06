package jmetal.qualityIndicator;

import jmetal.util.VectorUtils;
import jmetal.util.distance.impl.DominanceDistanceBetweenVectors;
import jmetal.util.errorchecking.Check;

import java.io.FileNotFoundException;

/**
 * This class implements the inverted generational distance metric plust (IGD+)
 * Reference: Ishibuchi et al 2015, "A Study on Performance Evaluation Ability of a Modified
 * Inverted Generational Distance Indicator", GECCO 2015
 *
 * @author Antonio J. Nebro <antonio@lcc.uma.es>
 */
public class InvertedGenerationalDistancePlus {
    public jmetal.qualityIndicator.util.MetricsUtil utils_;  //utils_ is used to access to the
    private double[][] referenceFront;

    /**
     * Default constructor
     */
    public InvertedGenerationalDistancePlus() {
        utils_ = new jmetal.qualityIndicator.util.MetricsUtil();
    }


    /**
     * Constructor
     *
     * @param referenceFront
     * @throws FileNotFoundException
     */
    public InvertedGenerationalDistancePlus(double[][] referenceFront) {
        this.referenceFront = referenceFront;
    }

    /**
     * Evaluate() method
     *
     * @param front
     * @return
     */
    public double compute(double[][] front) {
        Check.isNotNull(front);

        return invertedGenerationalDistancePlus(front, referenceFront);
    }

    /**
     * Returns the inverted generational distance plus value for a given front
     *
     * @param front          The front
     * @param referenceFront The reference pareto front
     */
    public double invertedGenerationalDistancePlus(double[][] front, double[][] referenceFront) {

        double sum = 0.0;
        for (double[] doubles : referenceFront) {
            sum += VectorUtils.distanceToClosestVector(doubles, front, new DominanceDistanceBetweenVectors());
        }

        // STEP 4. Divide the sum by the maximum number of points of the reference Pareto front
        return sum / referenceFront.length;
    }

    public String getName() {
        return "IGD+";
    }

    public String getDescription() {
        return "Inverted Generational Distance+";
    }

    public boolean isTheLowerTheIndicatorValueTheBetter() {
        return true;
    }
}

