///////////////////////////////////////////////////////////////////////////////
/*NAME:       Brian Bowden
 *COURSE:     Comp2631
 *INSTRUCTOR: Laura Marik
 *DUE DATE:   Nov 14, 2016
 *ASSIGNMENT: 3
 *=============================================================================
 * PURPOSE: Returns the number of instances and positions of a nucleotide 
 *          within a *.fasta file, where the nucleotide is inquired by the user. 
 *           
 * 
 * DETAILS: The fasta file contains a long string of letters (A, C, T and G)'s 
 * 			in various combinations. Each combination of length K is a nucleotide.
 * 			The user will be prompted for the length K before the file is 
 * 			completely read in, which assists in the organization later on. The
 * 			file is read in, populates a queue of length K. The characters in the
 * 			queue are assigned integer values one by one, and then this is used in 
 * 			the keying of the hashMap it will be stored in. The queue dequeues the
 * 			first element and assigns it an integer value, and puts it in an array,
 * 			the next K - 1 elements are dequeued one at a time, assigned a value
 * 			put into the same array, but are enqueued again. The next element in 
 * 			the file is then enqueued. This whole process is repeated until there
 * 			is nothing left in the fasta. 
 * 			This process populates a hashMap, so that searching for a specific
 * 			element is much quicker.
 * 			Once the map is full, the user is asked to enter the nucleotide they
 * 			are looking for. If the sequence is contained, it will return the 
 * 			number of instances of the nucleotide and the positions in the file 
 * 			where the nucleotide can be found. 
 * 			User is then prompted to declare another search or quit.
 * 			 
 * NOTE***: myHashMap is not made, therefore it is not implemented
 * 
 * 
 * BUGS: No known bugs, however, the program has not been tested for all potential
 * 		 lengths of K. Error handling is not done in the case if the user enters 
 * 		 a 0 for KMer length. If the user enters a 'q' at any time in their 
 * 		 nucleotide search, the program will terminate (I don't consider this a bug,
 * 		 I mean, I'm giving fair warning, I tell them to enter q to quit, it's
 * 		 not my fault if they do it in the middle of their search) 
 * 
 * ASSUMPTIONS: Assume fasta file is well made
 * 			    Assume the user will not try to break the code
 *              Assume fasta file is not empty
 * 
 * 
 *****************************************************************************/
/*TESTCASES:
 * yeast1Test.fasta    - small file
 * ----------------------------------------------------------------------------
 * Please enter a filename: 
yeast1Test.fasta
xxx   xxx       xxx   xxx       xxx   xxx       xxx   xxx       x
   x x   x     x   x x   x     x   x x   x     x   x x   x     x
    x     x   x     x     x   x     x     x   x     x     x   x
   x x     x x     x x     x x     x x     x x     x x     x x
  x   x     x     x   x     x     x   x     x     x   x     x
 x     x   x x   x     x   x x   x     x   x x   x     x   x x
x       xxx   xxx       xxx   xxx       xxx   xxx       xxx   xxx
Please enter the length of the nucleotide string: 
4
625
>I Yeast
count is: 10
time to parse file = 2

Press q to exit, else, please enter nucleotide you are searching for, of length 4 : 
TC
Length of string incorrect, please try again
Press q to exit, else, please enter nucleotide you are searching for, of length 4 : 
TCTC
you entered the sequence: TCTC
key is contained at position(s): [1] 
number of instances found: 1
search successful in: 0ms 
Press q to exit, else, please enter nucleotide you are searching for, of length 4 : 
CTCT
you entered the sequence: CTCT
key is contained at position(s): [0, 2] 
number of instances found: 2
search successful in: 0ms 
Press q to exit, else, please enter nucleotide you are searching for, of length 4 : 
q
Program will terminate...
-------------------------------------------------------------------------------
 * yeast1Sample.fasta  - medium file
 * ----------------------------------------------------------------------------
 * Please enter a filename: 
yeast1Sample.fasta
xxx   xxx       xxx   xxx       xxx   xxx       xxx   xxx       x
   x x   x     x   x x   x     x   x x   x     x   x x   x     x
    x     x   x     x     x   x     x     x   x     x     x   x
   x x     x x     x x     x x     x x     x x     x x     x x
  x   x     x     x   x     x     x   x     x     x   x     x
 x     x   x x   x     x   x x   x     x   x x   x     x   x x
x       xxx   xxx       xxx   xxx       xxx   xxx       xxx   xxx
Please enter the length of the nucleotide string: 
2
25
>I Yeast
count is: 60
time to parse file = 2

Press q to exit, else, please enter nucleotide you are searching for, of length 2 : 
AC
you entered the sequence: AC
key is contained at position(s): [2, 4, 7, 9, 13, 15, 17, 21, 23, 25, 28, 30, 33, 35, 37, 40, 42, 45, 47, 51, 53, 55, 57, 58] 
number of instances found: 24
search successful in: 0ms 
Press q to exit, else, please enter nucleotide you are searching for, of length 2 : 
AA
you entered the sequence: AA
key is not contained 
search unsuccessful in: 0ms 

Press q to exit, else, please enter nucleotide you are searching for, of length 2 : 
q
Program will terminate...
-------------------------------------------------------------------------------
 * yeast1.fasta        - large file
 * ----------------------------------------------------------------------------
 * Please enter a filename: 
yeast1.fasta
xxx   xxx       xxx   xxx       xxx   xxx       xxx   xxx       x
   x x   x     x   x x   x     x   x x   x     x   x x   x     x
    x     x   x     x     x   x     x     x   x     x     x   x
   x x     x x     x x     x x     x x     x x     x x     x x
  x   x     x     x   x     x     x   x     x     x   x     x
 x     x   x x   x     x   x x   x     x   x x   x     x   x x
x       xxx   xxx       xxx   xxx       xxx   xxx       xxx   xxx
Please enter the length of the nucleotide string: 
3
125
>I Yeast
count is: 230203
time to parse file = 702

Press q to exit, else, please enter nucleotide you are searching for, of length 3 : 
CTC
you entered the sequence: CTC
//There are 2556 instances here... There are no new lines, I think I'll pass on copying it here..
number of instances found: 2556
search successful in: 0ms 
Press q to exit, else, please enter nucleotide you are searching for, of length 3 : 

******************************************************************************/	
 //////////////////////////////////////////////////////////////////////////////
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class main {
	public static BufferedReader fIn = null;
	static Scanner sc = new Scanner(System.in);
	static public char[] query;
	static public int[] code;
	
	public static void main(String[] args) {

		String fileName = null;
		Banner fun = new Banner();
		int sequence = 0;
		int key = 0;
		int size = 0;
		String s;
		boolean noQuit = true;
		long start;
		long end;
		
		System.out.println("Please enter a filename: ");
		try{
			fileName = sc.nextLine();
		}
		catch(Exception e){
			System.out.println("Usage: Main <filename>" );
			System.exit(1);
		}
		
		fIn = opening(fileName);
		fun.printBanner();				//just for fun

		KMer chain = new KMer(fIn);

		size = chain.getSize();
		query = new char[size];
		code = new int[size];
		
		while(noQuit){
			s = inquire(query, size);				//asking for nucleotide
			start = System.currentTimeMillis();
			query = s.toCharArray();
			if (s.contains("q")){
				System.out.println("Program will terminate...");
				noQuit = false;
			}
			else{
				System.out.println("you entered the sequence: " + s);
	
			
				for (int i = 0; i < size; i++){
					code[i] = decision(query[i]);
				}
		
				sequence = Sequencer(code, size);
				key = sequence % chain.hashsize;
				if (chain.DNA.containsKey(key)){
					end = System.currentTimeMillis() - start;
					System.out.print("key is contained at position(s): " + chain.DNA.get(key) + " \n"
							+ "number of instances found: " + chain.DNA.get(key).size() + '\n'
							+ "search successful in: " + end + "ms \n");
				}
				else{
					end = System.currentTimeMillis() - start;
					System.out.println("key is not contained \n" + "search unsuccessful in: " + end + "ms \n");
					}
				}
			}
			
		sc.close();
	}

	public static BufferedReader opening(String fileName) {
		
		try{
			fIn = new BufferedReader(new FileReader(fileName));
		}
		catch(Exception e){
			System.out.println("Failed to open " +fileName);
			System.exit(1);
		}
		return fIn;
	}

	public static String inquire(char[] c, int f){
		
		String ask = "Press q to exit, else, please enter nucleotide you are searching for, of length " + f + " : ";
		int i;
		boolean error = true;
		String inp = null;

		while (error){
			System.out.println(ask);
			inp = sc.nextLine();
			c = inp.toCharArray();
			error = false;
			if (!inp.contains("q")){
				if (inp.length() != f){
					System.out.println("Length of string incorrect, please try again");
					error = true;
						}
				else{
					for (i = 0; i < f; i++){
						if ((c[i] != 'A')&&(c[i] != 'C')&&(c[i] != 'G')&&(c[i] != 'T')){
							System.out.println("You entered an invalid character: " + c[i] + " please re-enter string");
							error = true;
						}
					}
				}
			}
		}
		return inp;
	}
	
	public static int Sequencer(int[] n, int s){
		
		int j = 0;
		int r = 0;
		for (int i = 0; i < s; i++){
			r = pow(10, (s-i) - 1);
			j += n[i] * r;
		}
		return j;
	}
	
	public static int pow(int a, int b){
		int c = 1;
		for (int i = 1; i <= b; i++){
			c *= a;
		}
		return c;
	}
	
	public static int decision(char c){
		int decider = 0;
		if (c == 'A')
			decider = 1;
		else if (c == 'G')
			decider = 2;
		else if (c == 'C')
			decider = 3;
		else if (c == 'T')
			decider = 4;
		return decider;
	}
}
