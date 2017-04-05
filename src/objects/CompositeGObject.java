package objects;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

public class CompositeGObject extends GObject {

	private List<GObject> gObjects;

	public CompositeGObject() {
		super(0, 0, 0, 0);
		gObjects = new ArrayList<GObject>();
	}

	public void add(GObject gObject) {
		// TODO: Implement this method.
		gObjects.add(gObject);
	}

	public void remove(GObject gObject) {
		// TODO: Implement this method.
		gObjects.remove(gObject);
	}

	@Override
	public void move(int dX, int dY) {
		// TODO: Implement this method.
		x += dX;
		y += dY;
		for(GObject g : gObjects) {
			g.move(dX, dY);
		}
	}
	
	public void recalculateRegion() {
		// TODO: Implement this method.
		if(!gObjects.isEmpty()) {
			GObject g1 = gObjects.get(0);
			GObject g2 = gObjects.get(0);
			GObject g3 = gObjects.get(0);
			GObject g4 = gObjects.get(0);
			for(GObject g : gObjects) {
				if(g.x <= g1.x) {
					g1 = g;
				}
				if(g.y <= g2.y) {
					g2 = g;
				}
				if(g.x + g.width >= g3.x + g3.width) {
					g3 = g;
				}
				if(g.y + g.height >= g4.y + g4.height) {
					g4 = g;
				}
			}
			x = g1.x;
			y = g2.y;
			width = g3.x + g3.width - g1.x;
			height = g4.y + g4.height - g2.y;
		}
	}

	@Override
	public void paintObject(Graphics g) {
		// TODO: Implement this method.
		for(GObject go : gObjects) {
			go.paintObject(g);
		}
	}

	@Override
	public void paintLabel(Graphics g) {
		// TODO: Implement this method.
		g.drawString("Grouped", x, y + height + 15);
	}
	
}
