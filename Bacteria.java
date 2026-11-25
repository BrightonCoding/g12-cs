import java.io.*;
import java.util.*;

public class Bacteria{
    
    public static void main(String[]args){

        Scanner scanner = new Scanner(System.in);
        int rows = scanner.nextInt();
        int cols = scanner.nextInt();
        char[][] grid = new char[rows][cols];
        
        for (int i = 0; i < rows; i++){
            String line = scanner.next();
                for (int j = 0; j < cols; j++){
                    grid[i][j] = line.charAt(j);
            }
        }

        for (int i = 0; i < rows; i++){
            for (int j = 0; j < cols; j++){
                if (grid[i][j] == '*'){
                    System.out.println("(" + i + ", " + j + ") " + find(i,j,grid));
                }
            }
        }
    }

    public static int find(int r, int c, char[][] grid){
        if (r < 0 || r > grid.length -1|| c > grid[r].length -1|| c < 0 || grid[r][c] != '*'){
            return 0;
        } else {
            grid[r][c] = '_';
            return find(r-1,c,grid) + find(r,c-1,grid) + find(r,c+1,grid) + find(r+1,c,grid) + 1;

        }

        }
    }