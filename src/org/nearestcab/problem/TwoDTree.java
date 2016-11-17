package org.nearestcab.problem;

class TwoDTree
{
	private TwoDNode Root;
	private double d_min;
	private TwoDNode nearest_neighbour;
	private int KD_id;
	private int nList;
	private TwoDNode CheckedNodes[];
	private int checked_nodes;
	private TwoDNode List[];
	private double x_min[], x_max[];
	private boolean max_boundary[], min_boundary[];
	private int n_boundary;

	TwoDTree(int i)
	{
		Root = null;
		KD_id = 1;
		nList = 0;
		List = new TwoDNode[i];
		CheckedNodes = new TwoDNode[i];
		max_boundary = new boolean[2];
		min_boundary = new boolean[2];
		x_min = new double[2];
		x_max = new double[2];
	}

	boolean addElement(double[] point)
	{
		if (Root == null)
		{
			Root = new TwoDNode(point, 0);
			Root.id = KD_id++;
			List[nList++] = Root;
		} 
		else
		{
			TwoDNode pNode;
			if ((pNode = Root.addElement(point)) != null)
			{
				pNode.id = KD_id++;
				List[nList++] = pNode;
			}
		}
		return true;
	}

	TwoDNode getNearestCab(double[] point)
	{
		if (Root == null)
			return null;
		checked_nodes = 0;
		TwoDNode parent = Root.findImmediateParent(point);
		nearest_neighbour = parent;
		d_min = Root.distance2(point, parent.position, 2);
		search_parent(parent, point);
		uncheck();
		return nearest_neighbour;
	}
	
	private TwoDNode search_parent(TwoDNode parent, double[] point)
	{
		for (int k = 0; k < 2; k++)
		{
			x_min[k] = x_max[k] = 0;
			max_boundary[k] = min_boundary[k] = false;
		}
		n_boundary = 0;
		TwoDNode search_root = parent;
		while (parent != null && (n_boundary != 2 * 2))
		{
			check_subtree(parent, point);
			search_root = parent;
			parent = parent.Parent;
		}
		return search_root;
	}

	private void check_subtree(TwoDNode node, double[] point)
	{
		if ((node == null) || node.checked)
			return;
		CheckedNodes[checked_nodes++] = node;
		node.checked = true;
		set_bounding_cube(node, point);
		int dim = node.axis;
		double d = node.position[dim] - point[dim];
		if (d * d > d_min)
		{
			if (node.position[dim] > point[dim])
				check_subtree(node.Left, point);
			else
				check_subtree(node.Right, point);
		} 
		else
		{
			check_subtree(node.Left, point);
			check_subtree(node.Right, point);
		}
	}

	private void set_bounding_cube(TwoDNode node, double[] point)
	{
		if (node == null)
			return;
		int d = 0;
		double dx;
		for (int k = 0; k < 2; k++)
		{
			dx = node.position[k] - point[k];
			if (dx > 0)
			{
				dx *= dx;
				if (!max_boundary[k])
				{
					if (dx > x_max[k])
						x_max[k] = dx;
					if (x_max[k] > d_min)
					{
						max_boundary[k] = true;
						n_boundary++;
					}
				}
			}
			else
			{
				dx *= dx;
				if (!min_boundary[k])
				{
					if (dx > x_min[k])
						x_min[k] = dx;
					if (x_min[k] > d_min)
					{
						min_boundary[k] = true;
						n_boundary++;
					}
				}
			}
			d += dx;
			if (d > d_min)
				return;
		}
		if (d < d_min)
		{
			d_min = d;
			nearest_neighbour = node;
		}
	}

	private void uncheck()
	{
		for (int n = 0; n < checked_nodes; n++)
			CheckedNodes[n].checked = false;
	}
}