// package whatever; // don't place package name!

import java.util.Arrays;


public class ArraySet {
    private boolean set[];
    private int size = 0; // index of the last occupied position in the array
  
  ArraySet () {
	  set = new boolean[50];
      Arrays.fill(set, false);
  }
  
  // return true if add is changing the arraySet
  boolean add(int i) {
	  if(i >= set.length) {
		  // array doubling
		  boolean temp[] = new boolean[set.length * 2];
		  Arrays.fill(temp, false);
		  System.arraycopy(set, 0, temp, 0, set.length);
		  set = temp;
	  }  
	  boolean added = set[i];
	  set[i] = true;
	  if(i >= size) 
		  size = i + 1;
	  return !added;
  }
  
  boolean delete(int i){
	  if(i < size) { // you can't delete over size index, because there are no true values
		  boolean deleted = set[i];
		  set[i] = false;
		  if(i == size - 1) {
		  // update size, scrolling until the last occupied position in the array
			  int j;
			  for(j = size - 1; j >= 0 && !set[j]; j--);  
			  size = j + 1; // Size is set after the last significant value
		  }
		  return deleted;
	  }
	  return false;
  }
  
  boolean get(int i) {
	  return set[i];
  }
  
  int[] toArray() {
	  /* The index of every true value found in the set is inserted in
	   * the dynamic array a. 
	   */
	  int a[] = new int[0];
	  for(int i = 0; i < size; i++) {
		  if(get(i)) {
			  // Enlarge a of one position for every true value found.
			  int temp[] = new int[a.length + 1];
			  System.arraycopy(a, 0, temp, 0, a.length);
			  a = temp;
			  a[a.length - 1] = i;
		  }	
	  }
	  return a;
  }
}