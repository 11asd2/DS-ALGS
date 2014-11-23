import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

/* By Aranjit Daid Student Number: 10052771
 *	--------- Description of Algorithm-------------
 * 1. Sort the fundings in order of decreasing value. (Largest to smallest)
 * 2. Pick the first element out of the sorted list (i.e. the largest element)
 * 3. Schedule to apply for it on the closest available date to its DEADLINE.
 * 		For example, the first choice to apply is always the day of the deadline, but if it is unavailable,
 * 		then try to see if the day before the deadline is available, and keep doing this until you find an available day OR run out of days.
 * 		A date may be unavailable if another element is already booked for it (i.e. another element is scheduled for application on that day).
 * 		So again, if this is the case, then try for the next latest date (i.e. one day earlier).
 * 		Keep trying, but if you reach day 0 and it is also unavailable, then you can't apply for that
 * 		particular funding.
 * 	4. Move onto the next element in the list. Repeat from Step 2.
 *  
 *  	-------INTUITION------------
 *  The intuition behind the greedy algorithm is that postponing the application for the largest funding as much as possible
 *  will allow us to apply for more fundings that have an earlier deadline. You can always schedule a funding with a later deadline ahead of time,
 *  but you can't schedule a funding with an early deadline after its deadline. Therefore, we try to keep the early dates as OPEN as POSSIBLE.
 */

/* this class loads the data from the given input file and instantiates the Fundraiser Class.
 * It instantiates the Fundraiser class by passing it the fundings in the form of an array of type Fundings*/
public class LabTestOne {
	// file i/o and instantiation of Fundraiser.
	public static void main(String args[]) {
		Funding[] fundings = null;
		int days = 0; // in contract
		
		// plain old file i/o
		BufferedReader reader = null;
		try {
			FileReader fileReader = new FileReader(new File("lab1input.txt"));
			reader = new BufferedReader(fileReader);

			String line = reader.readLine();
			String[] info = line.split("\\t");
			days = Integer.parseInt(info[0]);
			int grants = Integer.parseInt(info[1]); // number of grants or fundings.
			fundings = new Funding[grants];
			Funding funding;
			// fill up the fundings array using the data from the file.
			for (int i = 0; (line = reader.readLine()) != null && i < grants ; i++) {
				info = line.split("\\t");
				// create funding with the deadline and value from file.
				funding = new Funding(Integer.parseInt(info[0]), Integer.parseInt(info[1]));
				fundings[i] = funding;
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		// instantiate Fundraiser by passing is the number of days in the contact and the array of fundings,
		Fundraiser fundraiser = new Fundraiser(days, fundings);
		fundraiser.runAlgorithm(); // run the ACTUAL ALGORITHM!
	}
}
