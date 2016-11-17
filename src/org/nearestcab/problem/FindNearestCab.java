package org.nearestcab.problem;

import java.util.Scanner;

public class FindNearestCab
{
	private static double[][] driversLocation = {{2,4},{9,2},{4,9},{3,9},{5,6},{1,2},{5,1}};
	
	public static void main(String args[])
    {
		int numpoints = 9;
		TwoDTree tdTree = new TwoDTree(numpoints);
		for(int i=0;i<driversLocation.length;i++)
		{
			double x[] = {driversLocation[i][0], driversLocation[i][1]};
			tdTree.addElement(x);
		}
		Scanner scan = new Scanner(System.in);
		System.out.println("Provide X co-ordinate");
		double xAxis = Double.parseDouble(scan.next());
		System.out.println("Provide Y co-ordinate");
		double yAxis = Double.parseDouble(scan.next());
		double s[] = {xAxis, yAxis};
		TwoDNode tdNode = tdTree.getNearestCab(s);
		System.out.println("The nearest cab available at position=> (" + tdNode.position[0] + " , " + tdNode.position[1] + ")");
	}
}
