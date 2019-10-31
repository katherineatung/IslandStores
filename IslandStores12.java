import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class IslandStores12 
{
	private static final double TOLERANCE = Math.pow(10, -15); 
	private static final int solutionSize = 12; //exact minimum number of stores
	public static void main(String[] args) 
	{
		check12(); 
		/**ArrayList<ArrayList<String> > successSets = new ArrayList<>(); 
		ArrayList<ArrayList<String> > coverageSets = new ArrayList<>();
		setup11(successSets, coverageSets);
		modifyParallelLists(successSets, coverageSets);
		finalCheck11(successSets, coverageSets); **/
	}
	
	/**
	 * Method takes as input a number in base-10 and outputs that number in 
	 * base-2.
	 * @param decimalNumber		the original base-10 number
	 * @return					the number converted to base-2
	 */
	public static String decimalToBinary(double decimalNumber)
	{
		if (decimalNumber == 0)
		{
			return "0";
		}
		
		// Calculating the start point for the binary number
		int highestPowerOf2 = (int) Math.pow(2, 
				(int) (Math.log(decimalNumber)/(Math.log(2))));
		
		return binaryNumberCalculator(highestPowerOf2, decimalNumber);
	}
	
	/**
	 * With a set highest power of 2, this method converts a decimal number to a
	 * binary number.
	 * @param highestPowerOf2		highest power of 2 less than or equal to 
	 * 								original number
	 * @param decimalNumber			the original number in base-10		
	 * @return						the number in base-2
	 */
	public static String binaryNumberCalculator(int highestPowerOf2, 
												double decimalNumber)
	{
		double currentPowerOf2 = highestPowerOf2;
		String binaryNumber = "";
		
		//Takes the biggest power of 2 <= the original number, then subtracts 
		//that power of 2 from the number. The process repeats with this new  
		//number, continuing while tolerance is met or exponents are positive.
		while(currentPowerOf2 >= 1 || decimalNumber > TOLERANCE)
		{
			binaryNumber+=binaryNumberAddSymbol(decimalNumber, currentPowerOf2);
			if (decimalNumber >= currentPowerOf2)
			{
				decimalNumber = decimalNumber - currentPowerOf2;
			} 
			
			currentPowerOf2 = currentPowerOf2/2;
		}
		return binaryNumber;
	}
	
	/**
	 * Method creates a string with digits of the binary number. 
	 * @param decimalNumber		the original number in base-10
	 * @param currentPowerOf2   highest power of 2 less than or equal to 
	 * 							current number
	 * @return					string containing digits of binary number
	 */
	public static String binaryNumberAddSymbol (double decimalNumber, 
			double currentPowerOf2)
	{
		String binaryNumber = "";
		if (decimalNumber >= currentPowerOf2)
		{
			binaryNumber+="1";
		} 
		else 
		{
			binaryNumber+="0";
		}
		return binaryNumber;
	}
	
	/**
	 * Makes the inputted string as long as the binary string.
	 * 
	 * @param binaryString
	 * @return
	 */
	public static String zeroInserter (String binaryString, int bitString)
	{
		String workingString = "";
		while (workingString.length() < bitString - binaryString.length())
		{
			workingString+="0";
		}
		workingString += binaryString;
		return workingString;
	}
	
	/**
	 * Checks if 2 bit strings of equal length differ from one another by 1 bit
	 * (i.e., if they are neighbors).
	 * 
	 * @param s1		first string
	 * @param s2		second string
	 * @return			true if they're neighbors, false otherwise 
	 */
	public static boolean hammingDist1Check (String s1, String s2)
	{
		int counter = 0;
		for (int i = 0; i < s1.length(); i++)
		{
			if (s1.charAt(i) != s2.charAt(i))
			{
				counter++;
			}		
		}
		if (counter == 1)
		{
			return true;
		}
		return false;
	
	}
	
	/** 
	 * Creates HashMap of neighbors for each island.
	 * 
	 * @param islandSet		the set of islands
	 * @return				hashMap of neighbors for each island
	 */
	public static HashMap<String, ArrayList<String>> neighborSet
	(ArrayList<String> islandSet)
	{
		HashMap<String, ArrayList<String> > dist1 = new HashMap<>();
		for (int i = 0; i < islandSet.size(); i++)
		{
			//neighbors for a given island
			ArrayList<String> tempNeighbors = new ArrayList<>(); 
			for (int j = 0; j < islandSet.size(); j++)
			{
				if (hammingDist1Check(islandSet.get(i), islandSet.get(j)))
				{
					tempNeighbors.add(islandSet.get(j));
				}
			}
			dist1.put(islandSet.get(i), tempNeighbors);
		}
		return dist1;
	}
	
	/**
	 * Create and return a HashSet containing all islands covered by the stores
	 * in storeSet.
	 * 
	 * @param storeSet		the set of stores
	 * @param islandSet		the set of islands
	 * @return				the HashSet of islands covered by all stores
	 */
	public static HashSet<String> checkCoverage (ArrayList<String> storeSet, 
			ArrayList<String> islandSet)
	{
		HashSet<String> covered = new HashSet<>(); //store return result
		
		//Build HashMap of all neighbors
		HashMap<String, ArrayList<String>> neighbors = neighborSet(islandSet);
		
		//Go through every store, adding all of its neighbors to covered
		for (int i = 0; i < storeSet.size(); i++)
		{
			covered.addAll(neighbors.get(storeSet.get(i)));
		}
		
		//add the stores in the storeSet because they cover themselves
		covered.addAll(storeSet); 
		return covered;
	}
	
	/**
	 * Fills islandSet with all bitStrings of a given length.
	 * 
	 * @param islandSet			set of islands
	 * @param bitStringLength	length of bitString
	 */
	public static void islandSetInitialize (ArrayList<String> islandSet, 
			int bitStringLength)
	{
		for (int i = 0; i < Math.pow(2, bitStringLength); i++)
		{
			islandSet.add(zeroInserter(decimalToBinary(i), bitStringLength));
		}
	}
	
    /**
     * With the goal of selecting a certain number of stores, take an existing 
     * set of already selected stores, and try all possible subsets of 
     * the unpicked islands that could complete a set of stores with the desired 
     * size.
     * 
     * @param storeNumber		the number of stores left to select
     * @param islandSet			the set of islands			
     * @param storeSet			the set of stores already selected
     * @param bitString			the length of the bitString
     */
	public static void possibilityChecker(int storeNumber, 
			ArrayList<String> islandSet, 
			ArrayList<String> storeSet, 
			int bitString)
	{
		//the available islands from which subsets of stores will be picked
        ArrayList<String> optionSet = new ArrayList<String>(islandSet); 
		optionSet.removeAll(storeSet);
		ArrayList<ArrayList<String> > allCases = 
				Subsets.subset(optionSet, storeNumber); 

		//combine subsets with existing store set and tests coverage
		for (int i = 0; i < allCases.size(); i++)
		{
			allCases.get(i).addAll(storeSet);
			
			if ((checkCoverage(allCases.get(i), islandSet)).size() == 
					(int)(Math.pow(2, bitString)))
			{
				System.out.print(allCases.get(i));
				return;
			}
		}
		System.out.println("no answer");
	}
	
	/**
	 * In a 6D world, given 6 stores, generate all possible ways to choose 6 
	 * other stores. Then, check if these 12 stores cover all 2^6 = 64 islands.
	 */
	public static void check12()
	{
		ArrayList<String> storeSet = new ArrayList<String>(); 
		storeSet.add("000000");
		storeSet.add("001110");
		storeSet.add("001111");
		storeSet.add("011001");
		storeSet.add("011110");
		storeSet.add("010011");
		ArrayList<String> islandSet = new ArrayList<String>();
		islandSetInitialize(islandSet, 6);
		possibilityChecker(solutionSize - storeSet.size(), islandSet,
				storeSet, 6);
	}

	/**
	 * Find island configurations in 5D that cover 26 islands. Then add
	 * these island configurations to a list. For each configuration, determine
	 * the uncovered islands and add them to a list. These lists will be kept
	 * in parallel.
	 */
	public static void setup11(ArrayList<ArrayList<String> > successSets, 
			ArrayList<ArrayList<String> > coverageSets)
	{
		//the set of all islands in 5D
		ArrayList<String> islandSet = new ArrayList<String>(); 
		
		//the set of stores you have already selected
		ArrayList<String> storeSet = new ArrayList<String>();  
		storeSet.add("00000");
		islandSetInitialize(islandSet, 5);
		
		//the set of options you have for the remaining stores
		ArrayList<String> optionSet = new ArrayList<String>(islandSet); 
		optionSet.removeAll(storeSet);
		ArrayList<ArrayList<String> > allCases = Subsets.subset(optionSet, 4);

		//First, check if a given island configuration covers 26 islands in 5D.
		//If yes, add the configuration to successSets. Then add the 6 uncovered
		//islands to coverageSets.
		for (int i = 0; i < allCases.size(); i++)
		{
			allCases.get(i).addAll(storeSet);
			HashSet<String> checkCoverages = 
					checkCoverage(allCases.get(i), islandSet);
			if (checkCoverages.size() == (int)(26))
			{
				successSets.add(allCases.get(i));
				ArrayList<String> islandSetCopy = 
						new ArrayList<String>(islandSet);
				islandSetCopy.removeAll(checkCoverages);
				coverageSets.add(islandSetCopy);
			}
		}
	}
	/**
	 * Modify the parallel lists so that the bit strings are 6 characters long,
	 * thereby turning the 5D setup into the 6D problem we want to solve.
	 * 
	 * @param successSets		contains all sets of 5 stores that cover 26 
	 * 							islands in 5D
	 * @param coverageSets		contains the corresponding sets of 6 stores that
	 * 							were not covered in 5D
	 */
	public static void modifyParallelLists(
			ArrayList<ArrayList<String> > successSets,
			ArrayList<ArrayList<String> > coverageSets)
	{
		for (int i = 0; i < successSets.size(); i++)
		{
			for (int j = 0; j < successSets.get(0).size(); j++)
			{
				successSets.get(i).set(j, "0" + successSets.get(i).get(j));
			}
		}
		
		for (int i = 0; i < coverageSets.size(); i++)
		{
			for (int j = 0; j < coverageSets.get(0).size(); j++)
			{
				coverageSets.get(i).set(j, "1" + coverageSets.get(i).get(j));
			}
		}
	}
	
	/**
	 * Checks coverage for the parallel lists.
	 * 
	 * @param successSets		stores starting with 0		 
	 * @param coverageSets		stores starting with 1
	 */
	public static void finalCheck11(ArrayList<ArrayList<String> > successSets,
			ArrayList<ArrayList<String> > coverageSets)
	{
		ArrayList<String> islandSet = new ArrayList<>(); //all islands in 6D
		islandSetInitialize(islandSet, 6);
		for (int i = 0; i < successSets.size(); i++)
		{
			ArrayList<String> solutionToTry = new ArrayList<>();
			solutionToTry.addAll(coverageSets.get(i));
			solutionToTry.addAll(successSets.get(i));
			if (checkCoverage(solutionToTry, islandSet).size() == 64)
			{
				System.out.println("Solution found");
			}
		}
		System.out.println("Not found");
	}
}
