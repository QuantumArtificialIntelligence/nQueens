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
            System.err.println("Please add N as an argument of this Queens Board Finder");
            System.exit(1);
        }

        boolean printBoards = false;

        if(args.length == 2) {
            if(args[1].equals("print")) {
                System.out.println("Boards will be printed out and validated to check if there are no duplicate ones");
                printBoards = true;
            }
        }

        int count = 0;

        long start = (new Date()).getTime();

        System.out.println("Computing " + n + " Queens!");

        int rows = n;
        int cols = n;

        Stack<Pair<Integer, Integer>> st = new Stack<>();

        Map<Integer, Boolean> usedCols = new HashMap<>();
        boolean[][] pos = new boolean[rows][cols];

        Map<String, Boolean> boards = new HashMap<>();

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
                if (r == n - 1) { // We are in the last row, and since it is available, we found a solution
                    count++;

                    if(printBoards) {
                        System.out.println("Board #" + count);  // Comment out to save resources
                        printSol(pos, boards);
                    }

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

        long ended = (new Date()).getTime() - start;

        System.out.println("Finished in " + ended + " ms. Boards found: " + count);
    }

    private static boolean isAvailable(int r, int c, Map<Integer, Boolean> usedCols, boolean[][] pos) {
        return !usedCols.containsKey(c) && diagonalsClear(r,c, pos) && noStraightLine(r,c,pos);
    }

    private static boolean noStraightLine(int r, int c, boolean[][] pos) {
        // Check Upper Left

        int ir = r - 1;
        int ic = c - 1;

        int distance1c;
        int distance1r;

        int distance2c;
        int distance2r;

        boolean clear = true;

        int maxCols = pos.length;

        while(ir >= 0 && ic >= 0) {
            if(pos[ir][ic]) {

                // We compute the distance between r,c and ir,ic

                distance1r = r - ir;
                distance1c = c - ic;

                // Now we verify that there is no other Queen placed above, having the same distance

                distance2r = ir - distance1r;
                distance2c = ic - distance1c;

                if(distance2c >= 0 && distance2r >=0 && pos[distance2r][distance2c]) { // If this is true, it means that we found another Queen that will create a straight line if we place a Queen in r,c
                    ir = -1;
                    clear = false;
                    continue;
                }
            }
            ic--;
            if(ic == -1) {
                ic = c - 1;
                ir--;
            }
        }

        // ====== Going now through Col first

        ir = r - 1;
        ic = c - 1;

        while(ir >= 0 && ic >= 0 && clear) {
            if(pos[ir][ic]) {
                // We compute the distance between r,c and ir,ic

                distance1r = r - ir;
                distance1c = c - ic;

                // Now we verify that there is no other Queen placed above, having the same distance

                distance2r = ir - distance1r;
                distance2c = ic - distance1c;

                if(distance2c >= 0 && distance2r >=0 && pos[distance2r][distance2c]) { // If this is true, it means that we found another Queen that will create a straight line if we place a Queen in r,c
                    ir = -1;
                    clear = false;
                    continue;
                }
            }
            ir--;
            if(ir == -1) {
                ir = r - 1;
                ic--;
            }
        }

        // We check upper right

        ir = r - 1;
        ic = c + 1;

        while((ir >= 0 && ic < maxCols) && clear) {
            if(pos[ir][ic]) {

                // We compute the distance between r,c and ir,ic

                distance1r = r - ir;
                distance1c = ic - c;

                // Now we verify that there is no other Queen placed above, having the same distance

                distance2r = ir - distance1r;
                distance2c = distance1c + ic;

                if(distance2c < maxCols && distance2r >=0 && pos[distance2r][distance2c]) { // If this is true, it means that we found another Queen that will create a straight line if we place a Queen in r,c
                    ir = -1;
                    clear = false;
                    continue;
                }

            }
            ic++;
            if(ic == maxCols) {
                ic = c + 1;
                ir--;
            }
        }

        // == Through Col first

        ir = r - 1;
        ic = c + 1;

        while((ir >= 0 && ic < maxCols) && clear) {
            if(pos[ir][ic]) {
                // We compute the distance between r,c and ir,ic

                distance1r = r - ir;
                distance1c = ic - c;

                // Now we verify that there is no other Queen placed above, having the same distance

                distance2r = ir - distance1r;
                distance2c = distance1c + ic;

                if(distance2c < maxCols && distance2r >=0 && pos[distance2r][distance2c]) { // If this is true, it means that we found another Queen that will create a straight line if we place a Queen in r,c
                    ir = -1;
                    clear = false;
                    continue;
                }
            }
            ir--;
            if(ir == -1) {
                ir = r - 1;
                ic++;
            }
        }

        return clear;
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

    private static void printSol(boolean[][] pos, Map<String, Boolean> boards) {
        int rs = 0;
        int cs = 0;

        StringBuilder sb = new StringBuilder();

        while(rs < pos.length) {
            sb.append(BoolToChar(pos[rs][cs]));
            System.out.print(BoolToChar(pos[rs][cs]) + " ");
            cs++;
            if(cs == pos[0].length) {
                rs++;
                cs=0;
                System.out.println();
            }
        }

        String board = sb.toString();
        if(boards.containsKey(board)) {
            System.err.println("RED ALERT: DUPLICATED BOARD FOUND !!! - " + board);
            System.exit(1);
        } else {
            boards.put(board, true);
        }
    }

    private static char BoolToChar(boolean b) {
        if(b) {
            return 'Q';
        } else {
            return '_';
        }
    }
}