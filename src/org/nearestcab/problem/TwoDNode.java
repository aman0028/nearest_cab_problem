package org.nearestcab.problem;

import java.util.Arrays;

class TwoDNode
{
	int axis;
	double[] position;
	int id;
	boolean checked;
	boolean orientation;
	TwoDNode Parent;
	TwoDNode Left;
	TwoDNode Right;
	
	TwoDNode(double[] position, int axis)
	{
		this.axis = axis;
		this.position = position;
		Left = Right = Parent = null;
		checked = false;
		id = 0;
	}

	TwoDNode findImmediateParent(double[] point)
	{
		TwoDNode parent = null;
		TwoDNode next = this;
		int split;
		while (next != null)
		{
			split = next.axis;
			parent = next;
			if (point[split] > next.position[split])
				next = next.Right;
			else
				next = next.Left;
		}
		return parent;
	}

	TwoDNode addElement(double[] point)
	{
		TwoDNode parent = findImmediateParent(point);
		TwoDNode newNode = new TwoDNode(point, parent.axis + 1 < 2 ? parent.axis + 1: 0);
		newNode.Parent = parent;
		if (point[parent.axis] > parent.position[parent.axis])
		{
			parent.Right = newNode;
			newNode.orientation = true;
		} 
		else
		{
			parent.Left = newNode;
			newNode.orientation = false; 
		}
		return newNode;
	}

	double distance2(double[] point1, double[] point2, int dim)
	{
		double S = 0;
		for (int k = 0; k < dim; k++)
			S += (point1[k] - point2[k]) * (point1[k] - point2[k]);
		return S;
	}

	@Override
	public String toString() {
		return "KDNode [axis=" + axis + ", x=" + Arrays.toString(position) + ", id="
				+ id + ", checked=" + checked + ", orientation=" + orientation
				+ "]";
	}

	
	
}