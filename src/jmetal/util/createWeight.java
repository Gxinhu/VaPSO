package jmetal.util;

import jmetal.core.Problem;
import org.apache.commons.math3.linear.Array2DRowRealMatrix;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.linear.RealVector;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class createWeight {
	Problem problem_;
	int populationSize;
	double[][] lamdaVectors;

	public createWeight(Problem problem, int populationSize, double[][] lamdaVectors) {
		this.lamdaVectors = lamdaVectors;
		this.populationSize = populationSize;
		this.problem_ = problem;
	}

	public double[][] initUniformWeightnothing() {
		String dataFileName;
		String datadirectory = "./weight";
		dataFileName = "W" + problem_.getNumberOfObjectives() + "D_"
				+ populationSize + ".dat";

		try {
			// Open the file
			FileInputStream fis = new FileInputStream(datadirectory + "/"
					+ dataFileName);
			InputStreamReader isr = new InputStreamReader(fis);
			BufferedReader br = new BufferedReader(isr);

			int i = 0;
			int j = 0;
			String aux = br.readLine();
			while (aux != null) {
				StringTokenizer st = new StringTokenizer(aux);
				j = 0;
				while (st.hasMoreTokens()) {
					double value = new Double(st.nextToken());
					lamdaVectors[i][j] = value;
					j++;
				}
				aux = br.readLine();
				i++;
			}
			br.close();
		} catch (Exception e) {
			System.out
					.println("initUniformWeight: failed when reading for file: "
							+ datadirectory + "/" + dataFileName);
			e.printStackTrace();
		}
		return lamdaVectors;
	}
}
