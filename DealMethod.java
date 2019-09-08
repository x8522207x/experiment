package experiment;

import java.util.ArrayList;
import java.util.Map;
import java.util.Random;
import java.util.TreeSet;

public interface DealMethod<T> {
	public ArrayList<ArrayList<StringBuilder>> generateData(Random ran, Map<String, TreeSet<String>> setHasElement,
			TreeSet<String> set, ArrayList<ArrayList<StringBuilder>> A, String args[], T setTimes);

	public Map<Integer, Integer> initTimes(T setTimes);

	public ArrayList<TreeSet<String>> compareSets(ArrayList<TreeSet<String>> C, ArrayList<TreeSet<String>> D,
			int index);

	public Map<Integer, Integer> sunflowerResults(T setTimes, Map<Integer, Integer> sunflowerTimes,
			ArrayList<ArrayList<StringBuilder>> A, ArrayList<TreeSet<String>> C, ArrayList<TreeSet<String>> D,
			Map<Integer, Integer> sunflowerSize);

	public ArrayList<TreeSet<String>> getSets(Map<String, TreeSet<String>> setHasElement, ArrayList<TreeSet<String>> C);

}
