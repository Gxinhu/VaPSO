package jmetal.util.savesomething;

import jmetal.core.Problem;

import java.io.*;

public class savetofile {
	Problem problem;
	String path;
	int runtimes;
	double[][] indicator;

	public savetofile(Problem problem, String str, int run, double[][] indi) {
		this.indicator = indi;
		this.problem = problem;
		this.path = str;
		this.runtimes = run;
	}

	public static boolean mkDirectory(String path) {
		File file = null;
		try {
			file = new File(path);
			if (!file.exists()) {
				return file.mkdirs();
			} else {
				return false;
			}
		} catch (Exception e) {
		} finally {
			file = null;
		}
		return false;
	}


	public void save() {
		mkDirectory(path);
		try {
			File csv = new File(path + "/" + problem.getName() + "_" + this.runtimes + ".csv");//CSV文件
			if (csv.exists()) {
				csv.delete();
				csv.createNewFile();
			} else {
				csv.createNewFile();
			}
			BufferedWriter bw = new BufferedWriter(new FileWriter(csv, true));
			//新增一行数据
			bw.flush();
			for (int i = 0; i < indicator.length; i++) {
				for (int j = 0; j < indicator[i].length; j++) {
					bw.write(indicator[i][j] + " ");
				}
				bw.write("\n");
			}
			bw.close();
		} catch (FileNotFoundException e) {
			//捕获File对象生成时的异常
			e.printStackTrace();
		} catch (IOException e) {
			//捕获BufferedWriter对象关闭时的异常
			e.printStackTrace();
		}
	}
}
