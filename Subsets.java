import java.util.ArrayList;

public class Subsets 
{
	/**public static void main(String[] args)
	{
		
		ArrayList<String> startingSet = new ArrayList<>();
		startingSet.add("1");
		startingSet.add("2");
		startingSet.add("3");
		startingSet.add("4");
		
		int subsetSize = 3;
		System.out.print(auxSubset(startingSet, subsetSize));
	} **/
	
	public static ArrayList<ArrayList<String> > subset
		(ArrayList<String> startingSet, int subsetSize)
	{
		ArrayList<ArrayList<String> > masterSet = new ArrayList<>();
		ArrayList<String> workingSet = new ArrayList<>();
		int index = 0;
		auxSubset(startingSet, masterSet, workingSet, subsetSize, index);
		return masterSet;
	}
	
	/**
	 * Precondition: subsetSize >= 0.
	 * 
	 * @param startingSet
	 * @param masterSet
	 * @param workingSet
	 * @param subsetSize
	 * @param index
	 */
	public static void auxSubset(ArrayList<String> startingSet, 
			ArrayList<ArrayList<String> > masterSet,
			ArrayList<String> workingSet,
			int subsetSize, int index)
	{
		if (workingSet.size() == subsetSize)
		{
			masterSet.add(new ArrayList<String>(workingSet));
			
		}
		else
		{
			for (int i = index; i < startingSet.size(); i++)
			{
				workingSet.add(startingSet.get(i));
				auxSubset(startingSet, masterSet, workingSet, subsetSize, i + 1);
				workingSet.remove(startingSet.get(i));
			}
		}
	}
	

}
