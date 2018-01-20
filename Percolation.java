/*
 *  Jingyuan Yi
 *  jyi2@abany.edu
 */

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;
import java.lang.IllegalArgumentException;




public class Percolation {
    private int [][]grid; //2d array indicates open or not
    private int n;
    private WeightedQuickUnionUF uf;
    private int numOfOpen;

    public Percolation(int n){ // create n-by-n grid, with all sites blocked
        if(n<1) throw new IllegalArgumentException();
        this.n = n;
        this.grid = new int[n+1][n+1];
        this.uf = new WeightedQuickUnionUF(n*n+1);
        this.numOfOpen=0;

        top_union(uf);
    }

    private WeightedQuickUnionUF top_union(WeightedQuickUnionUF uf){
        int i;
        for(i=1;i<this.n;i++){
            uf.union(0,to_1d(1,i));
        }
        return uf;
    }

    private int to_1d(int x, int y){
        return (x-1)*this.n+y;
    }

    public    void open(int row, int col){ // open site (row, col) if it is not open already
        if(this.grid[row][col]==0) { //count iff it's not open initially
            this.grid[row][col] = 1;
            this.numOfOpen++;

            if (row > 1 && this.isOpen(row - 1, col)) uf.union(to_1d(row, col), to_1d(row - 1, col));
            if (row < n && this.isOpen(row + 1, col)) uf.union(to_1d(row, col), to_1d(row + 1, col));
            if (col > 1 && this.isOpen(row, col - 1)) uf.union(to_1d(row, col), to_1d(row, col - 1));
            if (col < n && this.isOpen(row, col + 1)) uf.union(to_1d(row, col), to_1d(row, col + 1));
        }
    }

    public boolean isOpen(int row, int col){ // is site (row, col) open?
        return this.grid[row][col]==1;
    }
    public boolean isFull(int row, int col){
        return uf.connected(0,to_1d(row,col));
    }  // is site (row, col) full?

    public int numberOfFullSites(){ // n^2 * lgn cost! 开销 ,ヽ(*。>Д<)o゜可怕
    int i,j;
    int count=0;
    for(i=1;i<this.n+1;i++){
        for (j=1;j<this.n+1;j++){
            if(isFull(i,j)){
                count++;
            }
        }
    }
    return count;

    }       // number of open sites

    public int numberOfOpenSites(){
        return this.numOfOpen;
    }
    public boolean percolates(){
        int i;
        for(i=1;i<this.n+1;i++)
        {
            if(isFull(this.n,i)){
                return true;
            }
        }
        return false;
    }              // does the system percolate?


    public static void main(String[] args){

    }   // test client (optional)


}
