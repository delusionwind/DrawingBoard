package objects;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

public abstract class GObject {

	protected boolean selected;
	
	protected int x;
	protected int y;
	protected int width;
	protected int height;

	public GObject(int x, int y, int width, int height) {
		this.selected = false;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}

	public boolean pointerHit(int pointerX, int pointerY) {
		// TODO: Implement this method.
		if(pointerX >= x && pointerY >= y && pointerX <= x+width && pointerY <= y+height) {
			return true;
		}
		return false;
	}
	
	public boolean intersect(int ix, int iy, int iw, int ih) {
		
		return (inRange(ix,x,x+width)||inRange(x,ix,ix+iw))
				&&(inRange(iy,y,y+height)||inRange(y,iy,iy+ih));
	}
	
	private boolean inRange(int value, int min, int max) {
		return value >= min && value <= max;
	}
	
	public void selected() {
		// TODO: Implement this method.
		selected = true;
	}
	
	public void deselected() {
		// TODO: Implement this method.
		selected = false;
	}
	
	public void move(int dX, int dY) {
		// TODO: Implement this method.
		x += dX;
		y += dY;
	}

	public final void paint(Graphics g) {
		/* Example of template method pattern */
		paintObject(g);
		paintRegion(g);
		paintLabel(g);
	}

	public void paintRegion(Graphics g) {
		/* Set color */
		Color color = selected ? Color.red : Color.black;
		g.setColor(color);
		/* Set size */
		int size = selected ? 3 : 1;
		/* Draw a region */
		Graphics2D g2d = (Graphics2D) g;
		g2d.setStroke(new BasicStroke(size));
		g2d.drawRect(x, y, width, height);
	}

	public abstract void paintObject(Graphics g);

	public abstract void paintLabel(Graphics g);
	
}
