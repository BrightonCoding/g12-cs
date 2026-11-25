import java.io.*;
import java.util.*;

public class Bacteria{
    public static int[][] target;
    
    public static void main(String[]args){

        Scanner scanner = new Scanner(System.in);
        int r = scanner.nextInt();
        int c = scanner.nextInt();
        char[][] grid = new char[r][c];
        
        for (int i = 0; i < r; i++){
            String line = scanner.next();
                for (int j = 0; j < c; j++){
                    grid[i][j] = line.charAt(j);
            }
        }

        for (int i = 0; i < r; i++){
            for (int j = 0; j < c; j++){
                if (grid[i][j] == '*'){
                    System.out.println("Colony starts at (" + i + "," + j + ") with size " + find(i,j,grid));
                }
            }
        }
    }

    public static int find(int i, int j, char[][] grid){
        if (i < 0 || i > grid.length -1|| j > grid[i].length -1|| j < 0 || grid[i][j] != '*'){
            return 0;
        } else {
            grid[i][j] = '-';
            return find(i-1,j,grid) + find(i,j-1,grid) + find(i,j+1,grid) + find(i+1,j,grid) + 1;

        }

        }
    }