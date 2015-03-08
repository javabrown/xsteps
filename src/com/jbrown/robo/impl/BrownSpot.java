package com.jbrown.robo.impl;

import java.awt.Dimension;
import java.awt.Rectangle;

public class BrownSpot extends Dimension {
    private double _x;
    private double _y;
    private int _seed;
    
    public BrownSpot(Rectangle rectangle) {
        super(new Dimension((int) rectangle.getWidth(),
                (int) rectangle.getHeight()));
        _x =  rectangle.getX();
        _y =  rectangle.getY();
        _seed = -1;
    }

    public BrownSpot(double x, double y, int seed) {
        _x = x;
        _y = y;
        _seed = seed;
    }

    public int getX() {
        return (int)_x;
    }

    public int getY() {
        return (int)_y;
    }

    public int getSeed() {
        return _seed;
    }

    
	@Override
	public String toString() {
		return "Spot [x=" + _x + ", y=" + _y + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + _seed;
		long temp;
		temp = Double.doubleToLongBits(_x);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(_y);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		BrownSpot other = (BrownSpot) obj;
		if (_seed != other._seed)
			return false;
		if (Double.doubleToLongBits(_x) != Double.doubleToLongBits(other._x))
			return false;
		if (Double.doubleToLongBits(_y) != Double.doubleToLongBits(other._y))
			return false;
		return true;
	}
    
    
}