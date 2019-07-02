package racingGems;

import java.util.*;

public class Gem {
	private double x_val, y_val;
	private int val, ogval, gemNum;
	private Gem src;
	private List<Gem> paths;

	public Gem() {
		x_val = 0;
		y_val = 0;
		val = 0;
		ogval = 0;
		gemNum = 0;
		src = null;
		paths = new ArrayList<Gem>();
	}

	public Gem(double x, double y, int v, int num) {
		x_val = x;
		y_val = y;
		val = v;
		ogval = v;
		gemNum = num;
		src = this;
		paths = new ArrayList<Gem>();
	}
	public double getX() {
		return x_val;
	}
	public double getY() {
		return y_val;
	}
	public int getNum() {
		return gemNum;
	}
	public int getVal() {
		return val;
	}
	public void updateVal(int v) {
		val += v;
	}
	public void addPath(Gem g) {
		paths.add(g);
	}	
	public Gem getPath(int x) {
		return paths.get(x);
	}
	public int getnumPaths() {
		return paths.size();
	}
	public void setSource(Gem g) {
		src = g;
	}
	public Gem getSource() {
		return src;
	}
	public boolean equals(Gem g) {
		return (this.getX() == g.getX() && this.getY() == g.getY());
	}
	public String toString() {
		return x_val+","+y_val+","+ogval;
	}
}
