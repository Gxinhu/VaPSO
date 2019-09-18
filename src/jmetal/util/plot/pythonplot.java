package jmetal.util.plot;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class pythonplot {
	double[][] solutionPf;
	String name;

	public pythonplot(double[][] solutionPf, String name) throws IOException, InterruptedException {
		this.solutionPf = solutionPf;
		this.name = name;
	}

	public void exectue() {
		int objective = solutionPf[0].length;
		try {
			String[] args1 = new String[solutionPf.length * objective + 4];
			args1[0] = "python";
			args1[1] = "/home/hu/Desktop/EvolutionComputation/src/jmetal/util/plot/demo2.py";
			args1[2] = String.valueOf(objective);
			args1[3] = name;
			int count = 4;
			for (int i = 0; i < solutionPf.length; i++) {
				for (int j = 0; j < objective; j++) {
					args1[count] = String.valueOf(solutionPf[i][j]);
					count++;
				}
			}
//			args1[2]="2.200047887871005001";
			Process proc = Runtime.getRuntime().exec(args1);// 执行py文件

			BufferedReader in = new BufferedReader(new InputStreamReader(proc.getInputStream()));
			String line = null;
			while ((line = in.readLine()) != null) {
				System.out.println(line);
			}
			in.close();
			proc.waitFor();
		} catch (
				IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
