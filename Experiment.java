package experiment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;
import java.util.TreeSet;

public class Experiment {
	private String sets;
	private String elements;
	private String length;

	public Experiment(String[] args) {
		this.sets = args[0];
		this.elements = args[1];
		this.length = args[2];
	}

	public ArrayList<ArrayList<StringBuilder>> generateData(Random ran, Map<String, String> setHasElement,
			TreeSet<String> set, ArrayList<ArrayList<StringBuilder>> A, String args[], int setTimes) {
		for (int a = 0; a < Integer.valueOf(this.sets) * setTimes; a++) {// 有幾個SET
			ArrayList<StringBuilder> S = new ArrayList<StringBuilder>();
			for (int b = 0; b < Integer.valueOf(this.elements); b++) {// 每個SET裡面幾個ELEMENT
				StringBuilder element = new StringBuilder();
				for (int c = 0; c < ran.nextInt(Integer.valueOf(this.length)) + 1; c++) {// 每個ELEMENT的長度
					element.append(ran.nextInt(10));// ELEMENT的內容
				}
				if (setHasElement.containsKey(element.toString())) {
					String suppose = setHasElement.get(element.toString()) + "-" + String.valueOf(a);
					Boolean repeat = false;
					for (String d : setHasElement.get(element.toString()).split("-")) {
						if (d.equals(String.valueOf(a))) {
							repeat = true;
							break;
						}
					}
					if (repeat == false) {
						setHasElement.put(element.toString(), suppose);
					}
				} else {
					setHasElement.put(element.toString(), String.valueOf(a));
				}
				set.add(element.toString());
				S.add(b, element);
			}
			A.add(a, S);
		}
		return A;
	}

	public Map<String, Integer> initTimes(int setTimes) {
		Map<String, Integer> sunflowerTimes = new TreeMap<String, Integer>();
		for (int i = 2; i <= Integer.valueOf(this.sets) * setTimes; i++) {
			sunflowerTimes.put(Integer.toString(i), 0);
		}
		return sunflowerTimes;
	}

	public void compareSets(ArrayList<String> C, ArrayList<String> D) {
		D.add(C.get(0));
		for (int a = 1; a < C.size(); a++) {
			StringBuilder E = new StringBuilder();
			int sameSetposition = -1;
			for (int b = 0; b < D.size(); b++) {
				for (String c : D.get(b).split("-")) {
					for (String d : C.get(a).split("-")) {
						if (c.equals(d)) {
							E.append(d + "-");
							sameSetposition = b;
							break;
						}
					}
				}
				if (sameSetposition >= 0) {
					break;
				}
			}
			if (E.length() > 0 && E.substring(0, E.length() - 1).split("-").length > 1) {
				D.set(sameSetposition, E.substring(0, E.length() - 1));
			} else {
				D.add(C.get(a));
			}
		}
	}

	public Map<String, Integer> sunflowerResults(int setTimes, Map<String, Integer> sunflowerTimes,
			ArrayList<ArrayList<StringBuilder>> A, ArrayList<String> C, ArrayList<String> D,
			Map<String, Integer> sunflowerSize) {
		for (int i = 2; i <= Integer.valueOf(this.sets) * setTimes; i++) {
			sunflowerSize.put(Integer.toString(i), 0);
		}
		for (String i : D) {
			int length = i.split("-").length;
			sunflowerSize.put(Integer.toString(length), sunflowerSize.get(Integer.toString(length)) + 1);
		}
		if (C.size() == 0) {
			sunflowerSize.put(Integer.toString(A.size()), 1);
		}
		for (int i = 2; i <= (Integer.valueOf(this.sets) * setTimes); i++) {
			if (sunflowerSize.get(Integer.toString(i)) > 0) {
				sunflowerTimes.put(Integer.toString(i), sunflowerTimes.get(Integer.toString(i)) + 1);
			}
		}
		return sunflowerTimes;
	}

	public ArrayList<String> getSets(Map<String, String> setHasElement, ArrayList<String> C) {
		for (Iterator it = setHasElement.values().iterator(); it.hasNext();) {
			String sets = it.next().toString();
			if (sets.split("-").length > 1) {
				C.add(sets);
			}
		}
		return C;
	}

	@SuppressWarnings("rawtypes")
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		Experiment experiment = new Experiment(args);
		// for (int setTimes = 1; setTimes < 6; setTimes++) {
		Map<String, Integer> sunflowerTimes = experiment.initTimes(/* setTimes */1);
		for (int times = 0; times < 1000; times++) {
			Random ran = new Random();
			TreeSet<String> set = new TreeSet<String>();
			ArrayList<ArrayList<StringBuilder>> A = new ArrayList<ArrayList<StringBuilder>>();
			Map<String, String> setHasElement = new HashMap<String, String>();
			ArrayList<String> C = new ArrayList<String>();
			ArrayList<String> D = new ArrayList<String>();
			Map<String, Integer> sunflowerSize = new TreeMap<String, Integer>();
			A = experiment.generateData(ran, setHasElement, set, A, args, /* setTimes */1);
			C = experiment.getSets(setHasElement, C);
			experiment.compareSets(C, D);
			sunflowerTimes = experiment.sunflowerResults(/* setTimes */1, sunflowerTimes, A, C, D, sunflowerSize);
		}
		System.out.println(sunflowerTimes);
		// }
	}
}
