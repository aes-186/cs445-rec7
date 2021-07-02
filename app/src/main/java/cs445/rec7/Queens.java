package cs445.rec7;

import java.util.Arrays;

public class Queens {
    /**
     * Checks if a partial solution is a complete solution.
     * @param partial The partial solution
     * @return true if the partial solution is complete, false otherwise.
     */
    public static boolean isFullSolution(int[] partial) {
        // TODO: Implement this method
        //given array of integers, check that the array is a full solution
        //if queen is placed in every column
        
        //starting from left 
        /**
        for( int i=0; i < 8; i++){
            if( partial[i] == 0 ) return false; //0 means no queen in col
        }

        return true;
        */ 

        //probably better to start from the right 
        for( int i=7; i >= 0; i--){
            if (partial[i] == 0){
                return false;
            }
        }
        return true; 

        //could also do
        // if( partial[7]==0 ) return false; 

    }

    /**
     * Checks if a partial solution should be rejected because it can never be extended to a
     * complete solution.
     * @param partial The partial solution
     * @return true if the partial solution should be rejected, false otherwise.
     */
    public static boolean reject(int[] partial) {
        // TODO: Implement this method
        //must check that queens are not in same row, same col, same diag
        //should NOT have to worry about two queens being in same column
        //impossible to represent that with how we implement it
        
        //check two queens in same row
        //use nested for loops
        for( int i=0; i < 8; i++){
            //look at all the previous columns, thus j<i
            for( int j=0; j<i; j++){
                //check the rows 
                //if slope or diagonal btwn queens 
                    //if dif btwn rows = dif btwn cols = reject
                    //row(j)-row(i)==j-i
                    //row(j)-row(i)==i-j 

                //no conflict if one of the two columns doesn't have queen in it
                if( partial[i] == 0 || partial[j] == 0) {
                    return false; 
                } else if ( i != j && partial[i]==partial[j]) {
                    // in same row 
                    //must make sure i != j ??????????????
                    return true; //because conflict 
                } else if (partial[j]-partial[i] == j-i ) {
                    // positive diagonal case 
                    return true; 
                } else if ( partial[j] - partial[j] == i-j ) {
                    // negative diagonal case
                    return true; 
                }
            }
        }

        //if no conflict 
        //only time we get here is if we have full solution 
        return false;
    }

    /**
     * Extends a partial solution by adding one additional queen.
     * @param partial The partial solution
     * @return a partial solution with one more queen added, or null if no queen can be added.
     */
    public static int[] extend(int[] partial) {
        // TODO: implement this method
        //puts a queen into the next column that does not contain queen already
        // return integer array - why? - because you can't undo changes made to the same object
        // modify copies of integer array
        // when you backtrack, the changes you made are undone 

        int[] temp = new int[8]; 

        for( int i=0; i<8; i++){
            if( partial[i]==0){
                temp[i]=1; 
                return temp; 
            } else {
                temp[i] = partial[i]; 
            }
        }

        // we cannot extend - cannot put new queen on board 
        // there were no positions with a zero 
        
        return null;
    }

    /**
     * Moves the most recently-placed queen to its next possible position.
     * @param partial The partial solution
     * @return a partial solution with the most recently-placed queen moved to its next possible
     * position, or null if we are out of options for the most recent queen.
     */
    public static int[] next(int[] partial) {
        // TODO: implement this method

        //what was the most recent queen that we placed?
        //try moving that queen down one row 

        int[] temp = new int[8]; 

        //how do we find the most recent queen that we modified?
        int i=0;
        while( i < 8 ) {
            //must check i==7 because partial[8] is index out of bounds 
            if( i==7 || partial[i+1]==0 ){
                //must check that we are not at the bottom of the board
                if( partial[i]==8) {
                    // at last row
                    return null; 
                } else {
                    //not at last row
                    temp[i] = partial[i] + 1; //move queen down one row
                    break; //break out of loop here 
                }

            } else {
                // this is not the last queen 
                // we will not change this queen 
                temp[i] = partial[i]; 
            }
        }


        return temp; //return the temporary copy 
    }

    /**
     * Solves the 8-queens problem and outputs all solutions
     * @param partial The partial solution
     */
    public static void solve(int[] partial) {
        if (reject(partial)) return;
        if (isFullSolution(partial)) System.out.println(Arrays.toString(partial));
        int[] attempt = extend(partial);
        while (attempt != null) {
            solve(attempt);
            attempt = next(attempt);
        }
    }

    /**
     * Solves the 8-queens problem and returns one solution
     * @param partial The partial solution
     * @return A full, correct solution
     */
    public static int[] solveOnce(int[] partial) {
        if (reject(partial)) return null;
        if (isFullSolution(partial)) return partial;
        int[] attempt = extend(partial);
        while (attempt != null) {
            int[] solution = solveOnce(attempt);
            if (solution != null) return solution;
            attempt = next(attempt);
        }
        return null;
    }



    public static void main(String[] args) {
        if (args.length >= 1 && args[0].equals("-a")) {
            // Find all solutions starting from the empty solution
            solve(new int[] {0, 0, 0, 0, 0, 0, 0, 0});
        } else {
            // Find one solution starting from the empty solution
            int[] solution = solveOnce(new int[] {0, 0, 0, 0, 0, 0, 0, 0});
            System.out.println(Arrays.toString(solution));
        }
    }
}

