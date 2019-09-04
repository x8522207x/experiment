package experiment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

public class Experiment implements DealMethod {
	final private String sets;
	final private String elements;
	final private String length;

	public Experiment(String[] args) {
		this.sets = args[0];
		this.elements = args[1];
		this.length = args[2];
	}

	public ArrayList<ArrayList<StringBuilder>> generateData(Random ran, Map<String, TreeSet<String>> setHasElement,
			TreeSet<String> set, ArrayList<ArrayList<StringBuilder>> A, String args[], int setTimes) {
		for (int a = 0; a < Integer.valueOf(this.sets) * setTimes; a++) {// 有幾個SET
			ArrayList<StringBuilder> S = new ArrayList<StringBuilder>();
			for (int b = 0; b < Integer.valueOf(this.elements); b++) {// 每個SET裡面幾個ELEMENT
				TreeSet<String> treeSet = new TreeSet<String>();
				StringBuilder element = new StringBuilder();
				for (int c = 0; c < ran.nextInt(Integer.valueOf(this.length)) + 1; c++) {// 每個ELEMENT的長度
					element.append(ran.nextInt(10));// ELEMENT的內容
				}

				if (setHasElement.containsKey(element.toString())) {
					treeSet = setHasElement.get(element.toString());
				}
				treeSet.add(String.valueOf(a));
				setHasElement.put(element.toString(), treeSet);
				set.add(element.toString());
				S.add(b, element);
			}
			A.add(a, S);
		}
		return A;
	}

	public Map<Integer, Integer> initTimes(int setTimes) {
		Map<Integer, Integer> sunflowerTimes = new TreeMap<Integer, Integer>();
		for (int i = 2; i <= Integer.valueOf(this.sets) * setTimes; i++) {
			sunflowerTimes.put(i, 0);
		}
		return sunflowerTimes;
	}

	public ArrayList<TreeSet<String>> compareSets(ArrayList<TreeSet<String>> C, ArrayList<TreeSet<String>> D,
			int index) {
		int sameSetposition = -1;
		TreeSet<String> E = new TreeSet<String>();
		if (D.isEmpty()) {
			D.add(C.get(index));
		} else {
			for (int b = 0; b < D.size(); b++) {
				for (String d : D.get(b)) {
					for (String c : C.get(index)) {
						if (c.equals(d)) {
							E.add(d);
							sameSetposition = b;
							break;
						}
					}
				}
				if (sameSetposition >= 0) {
					break;
				}
			}
			if (E.size() > 1) {
				D.set(sameSetposition, E);
			} else {
				D.add(C.get(index));
			}
		}
		index++;
		if (index < C.size()) {
			return compareSets(C, D, index);
		} else {
			return D;
		}

	}

	public Map<Integer, Integer> sunflowerResults(int setTimes, Map<Integer, Integer> sunflowerTimes,
			ArrayList<ArrayList<StringBuilder>> A, ArrayList<TreeSet<String>> C, ArrayList<TreeSet<String>> D,
			Map<Integer, Integer> sunflowerSize) {
		for (int i = 1; i <= Integer.valueOf(this.sets) * setTimes; i++) {
			sunflowerSize.put(i, 0);
		}

		for (TreeSet<String> i : D) {
			sunflowerSize.put(i.size(), sunflowerSize.get(i.size()) + 1);
		}

		if (C.size() == 0) {
			sunflowerSize.put(A.size(), 1);
		}
		for (int i = 2; i <= (Integer.valueOf(this.sets) * setTimes); i++) {
			if (sunflowerSize.get(i) > 0) {
				sunflowerTimes.put(i, sunflowerTimes.get(i) + 1);
			}
		}
		return sunflowerTimes;
	}

	public ArrayList<TreeSet<String>> getSets(Map<String, TreeSet<String>> setHasElement,
			ArrayList<TreeSet<String>> C) {
		C.addAll(setHasElement.values());
		return C;
	}

	@SuppressWarnings("rawtypes")
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		String[] test = { "200", "10", "5" };
		Experiment experiment = new Experiment(test);
		// // for (int setTimes = 1; setTimes < 6; setTimes++) {
		Map<Integer, Integer> sunflowerTimes = experiment.initTimes(/* setTimes */1);
		for (int times = 0; times < 1000; times++) {
			Random ran = new Random();
			TreeSet<String> set = new TreeSet<String>();
			ArrayList<ArrayList<StringBuilder>> A = new ArrayList<ArrayList<StringBuilder>>();
			Map<String, TreeSet<String>> setHasElement = new HashMap<String, TreeSet<String>>();
			ArrayList<TreeSet<String>> C = new ArrayList<TreeSet<String>>();
			ArrayList<TreeSet<String>> D = new ArrayList<TreeSet<String>>();
			Map<Integer, Integer> sunflowerSize = new TreeMap<Integer, Integer>();
			A = experiment.generateData(ran, setHasElement, set, A, args, /* setTimes */1);
			C = experiment.getSets(setHasElement, C);
			int index = 0;
			D = experiment.compareSets(C, D, index);
			sunflowerTimes = experiment.sunflowerResults(/* setTimes */1, sunflowerTimes, A, C, D, sunflowerSize);
		}
		// }
	}
}
