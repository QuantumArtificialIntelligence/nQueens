package qai.queens;

import javafx.util.Pair;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

class Main {
    public static void main(String[] args) {

        int n = 0;
        if (args.length > 0) {
            try {
                n = Integer.parseInt(args[0]);
            } catch (NumberFormatException e) {
                System.err.println("Argument" + args[0] + " must be an integer.");
                System.exit(1);
            }
        } else {
            System.err.println("Please add N as an argument of this super app");
            System.exit(1);
        }

        int count = 0;

        long start = (new Date()).getTime();

        System.out.println("Hello " + n + " Queens!");

        int rows = n;
        int cols = n;

        Stack<Pair<Integer, Integer>> st = new Stack<>();

        Map<Integer, Boolean> usedCols = new HashMap<>();
        boolean[][] pos = new boolean[rows][cols];

        st.push(new Pair<>(0, 0));

        while (!st.empty()) {

            Pair<Integer, Integer> current = st.pop();

            int r = current.getKey();
            int c = current.getValue();

            if (current.getValue() == n) {
                continue;
            }

            if (isAvailable(r, c, usedCols, pos)) {
                pos[r][c] = true;
                if (r == n - 1) { //we are in the last row, and since it is available, we found a solution
                    System.out.println("Found " + count++);  // Comment out to save resources
                    printSol(pos);
                    pos[r][c] = false;
                } else {
                    usedCols.put(c, true);
                    st.push(new Pair<>(r, c));
                    st.push(new Pair<>(r + 1, 0));
                }
            } else {
                if (pos[r][c]) {
                    pos[r][c] = false;
                    usedCols.remove(c);
                }
                st.push(new Pair<>(r, c + 1));
            }
        }


        System.out.println("Finished in " + ((new Date()).getTime() - start) + " ms");
    }

    private static void printSol(boolean[][] pos) {
        int rs = 0;
        int cs = 0;

        while(rs < pos.length) {
            System.out.print(BoolToChar(pos[rs][cs]) + " ");
            cs++;
            if(cs == pos[0].length) {
                rs++;
                cs=0;
                System.out.println();
            }
        }
    }

    private static char BoolToChar(boolean b) {
        if(b) {
            return 'Q';
        } else {
            return '_';
        }
    }

    private static boolean isAvailable(int r, int c, Map<Integer, Boolean> usedCols, boolean[][] pos) {
        return !usedCols.containsKey(c) && diagonalsClear(r,c, pos) && noStraightLine(r,c,pos);
    }

    private static boolean noStraightLine(int r, int c, boolean[][] pos) {


        return true;
    }

    private static boolean diagonalsClear(int r, int c, boolean[][] pos) {
        // Check upper left
        int ir = r - 1;
        int ic = c - 1;

        boolean clear = true;

        while((ir >= 0 && ic >= 0) && clear) {
            clear = !pos[ir][ic];
            ir--;
            ic--;
        }

        // Check upper right

        ir = r - 1;
        ic = c + 1;

        while((ir >= 0 && ic < pos[ir].length) && clear) {
            clear = !pos[ir][ic];
            ir--;
            ic++;
        }

        return clear;
    }
}