/*
 *  Jingyuan Yi
 *  jyi2@abany.edu
 */

import edu.princeton.cs.algs4.StdRandom;
import java.lang.Math;


public class PercolationStats {

    private double sample[];
    private int n;
    private int trials;


    public PercolationStats(int n, int trials){
        if(n<1) throw new IllegalArgumentException();

        this.n = n;
        this.trials = trials;
        this.sample = new double[this.trials];

        int i;
        for(i=0;i<trials;i++){
            Percolation pcl = new Percolation(n);
            while(true) {
                if (run(n, pcl)) {
                    break;
                }
            }
            this.sample[i]=(double)pcl.numberOfOpenSites()/((this.n)*(this.n));
            //System.out.println("Trails no"+i+"out of"+trials+" open sites number "+pcl.numberOfOpenSites()+" gird no "+this.n);
        }

    }    // perform trials independent experiments on an n-by-n grid

    private boolean run(int n,Percolation pcl){
        int i = StdRandom.uniform(1,n+1);
        int j = StdRandom.uniform(1,n+1);
        pcl.open(i,j);
        return pcl.percolates();
    }
    public double mean(){
        double sum=0;
        for (double i:this.sample) {
            sum +=i;
        }
        return sum/this.trials;
    }                          // sample mean of percolation threshold
    public double stddev(){
        double mean = mean();
        double sd=0;
        for (double i:this.sample
             ) {
            sd=sd+Math.pow(i-mean,2);
        }
        return sd/(this.trials-1);
    }                        // sample standard deviation of percolation threshold
    public double confidenceLo(){
        double mean = mean();
        double std = stddev();
        return mean-(1.96)*std/(Math.sqrt(this.trials));
    }                  // low  endpoint of 95% confidence interval
    public double confidenceHi(){
        double mean = mean();
        double std = stddev();
        return mean+(1.96)*std/(Math.sqrt(this.trials));
    }                  // high endpoint of 95% confidence interval

    public static void main(String[] args){
        int n = Integer.parseInt(args[0]);
        int trails = Integer.parseInt(args[1]);

        PercolationStats pclStats = new PercolationStats(n,trails);
        System.out.println("mean     =      " + pclStats.mean());
        System.out.println("stddev   =      "+ pclStats.stddev());
        System.out.println("95% confidence interval = ["+pclStats.confidenceLo()+",  "+pclStats.confidenceHi()+"]");
    }        // test client (described below)
}
