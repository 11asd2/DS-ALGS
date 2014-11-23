
/* This is the class containing the actual algorithm *.
 * Contains a mergeSort() method and runAlgorithm() method.
 * After instantiation of this class, the user only need to call runAlgorithm() to obtain the results.
 */
public class Fundraiser {
	private Funding[] fundings; // all the fundings
	int days; // days before contact ends.
	// the bookings variable holds fundings picked as a result of our GREEDY CHOICE.
	// the index represent the days the funding application is booked for. (i.e. A funding at index 0 of the array means that it is booked for Day 1). Initially the array elements are all null, meaning that no funding has been booked any of the days.
	private Funding[] bookings; 
	private int maxMoney; // in thousands ; valid only after calling runAlgorithm().
	public Fundraiser(int days, Funding[] fundings) {
		this.days = days;
		this.fundings = fundings;
		bookings = new Funding[days]; // at max, you can make as many bookings as the number of days you have.
	}

	// run the greedy algorithm and prints the results.
	public void runAlgorithm() {
		mergeSort(0, fundings.length - 1); // sort the array of fundings.
		maxMoney = 0; // this will hold the maximum funds it is possible to raise.
		for(Funding funding : fundings) {
			int date = funding.deadline - 1; // note , index 0 is day 1, hence the minus 1.
			if (date > days) // if the deadline is greater than our date, we can only postpone it's scheduling until the last day of our contract
				date = days;
			// loop down starting from the deadline date until you find an available date.
			for(; date >= 0; date--) {
				if(bookings[date] == null) {   // if space available on this date, 
					bookings[date] = funding; // book the funding for this date. 
					maxMoney += funding.value; // add the funding value to the maxMoney.
					break; // break out since we found a date to apply for this funding. No need to check for another date.
				}
			}
		}
		// print results
		System.out.println("Optimal Solution: " + "$" + maxMoney + ",000");
		System.out.println("Day     d  v");
		for (int i=1; i <= days; i++) {
			if(bookings[i-1] == null)
				System.out.println("Day	" + i + ": Rest");
			// two conditional statements just for formatting purposes. The latter has two tabs after the :.
			else if(i >= 10)
				System.out.println("Day " + i + ": " + bookings[i-1].deadline + " " + bookings[i-1].value);
			else 
				System.out.println("Day " + i + ":  " + bookings[i-1].deadline + " " + bookings[i-1].value);
		}
	}

	// merge sort! O(nlogn). Simple method.
	private void mergeSort(int left, int right) {
		if(right - left < 1)
			return;
		int mid = (right + left) / 2;
		mergeSort(left, mid); // sort left half of array
		mergeSort(mid + 1, right); // sort right half of array
		merge(left, mid, mid +1, right); // merge together the above two sorted subarrays.
	}

	//  merge method used by mergeSort. 
	private void merge(int start1, int end1, int start2, int end2) {
		Funding[] tempArray= new Funding[end2 - start1 + 1]; // temporary array for holding the merged array.
		int i = start1, j = start2, k = 0;
		while(i <= end1 && j <= end2) { 
			if(fundings[i].value >= fundings[j].value) {
				tempArray[k] = fundings[i];
				k++;
				i++;
			}
			else {
				tempArray[k] = fundings[j];
				k++;
				j++;
			} 
		}
		// if one subarray has completely been merged, then dump all the remaining elements from the other subarray into tempArray.
		if(i > end1)
			for(; j <= end2; k++, j++) 
				tempArray[k] = fundings[j];
		else 
			for(; i <= end1 ; k++, i++) 
				tempArray[k] = fundings[i]; 
		
		// copy sorted sub-array back into the corresponding section of the main array.
		for(i = start1, k = 0; i <= end2; i++, k++)
			fundings[i] = tempArray[k];		
	}
}