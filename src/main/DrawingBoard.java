package main;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JPanel;
import objects.*;

public class DrawingBoard extends JPanel {

	private MouseAdapter mouseAdapter; 
	private List<GObject> gObjects;
	private GObject target;
	
	private int gridSize = 10;
	
	public DrawingBoard() {
		gObjects = new ArrayList<GObject>();
		
		mouseAdapter = new MAdapter();
		addMouseListener(mouseAdapter);
		addMouseMotionListener(mouseAdapter);
		setPreferredSize(new Dimension(800, 600));
	}
	
	public void addGObject(GObject gObject) {
		// TODO: Implement this method.
		gObjects.add(gObject);
		repaint();
	}
	
	public void groupAll() {
		// TODO: Implement this method.
		CompositeGObject grouped = new CompositeGObject();
		for(GObject g : gObjects) {
			grouped.add(g);
		}
		grouped.recalculateRegion();
		gObjects.clear();
		gObjects.add(grouped);
		target = grouped;
		grouped.selected();
		repaint();
	}

	public void deleteSelected() {
		// TODO: Implement this method.
		gObjects.remove(target);
		target = null;
		repaint();
	}
	
	public void clear() {
		// TODO: Implement this method.
		gObjects.clear();
		target = null;
		repaint();
	}
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		paintBackground(g);
		paintGrids(g);
		paintObjects(g);
	}

	private void paintBackground(Graphics g) {
		g.setColor(Color.white);
		g.fillRect(0, 0, getWidth(), getHeight());
	}

	private void paintGrids(Graphics g) {
		g.setColor(Color.lightGray);
		int gridCountX = getWidth() / gridSize;
		int gridCountY = getHeight() / gridSize;
		for (int i = 0; i < gridCountX; i++) {
			g.drawLine(gridSize * i, 0, gridSize * i, getHeight());
		}
		for (int i = 0; i < gridCountY; i++) {
			g.drawLine(0, gridSize * i, getWidth(), gridSize * i);
		}
	}

	private void paintObjects(Graphics g) {
		for (GObject go : gObjects) {
			go.paint(g);
		}
	}

	class MAdapter extends MouseAdapter {

		// TODO: You need some variables here
		private int lastX,lastY;
		private int gx, gy;
		private boolean selectBox;
		
		private void deselectAll() {
			// TODO: Implement this method.
			for(GObject g : gObjects) {
				if(g != target) {
					g.deselected();
				}
			}
		}
		
		@Override
		public void mousePressed(MouseEvent e) {
			// TODO: Implement this method.
			int x = e.getX();
			int y = e.getY();
			target = null;
			for(GObject g : gObjects) {
				if(g.pointerHit(x, y)) {
					g.selected();
					target = g;
				}
			}
			deselectAll();
			if(target == null) {
				gx = x;
				gy = y;
			}
			lastX = x;
			lastY = y;
			repaint();
		}

		@Override
		public void mouseDragged(MouseEvent e) {
			// TODO: Implement this method.
			if(target != null) {
				target.move(e.getX()-lastX, e.getY()-lastY);
				lastX = e.getX();
				lastY = e.getY();
			}else {
				Graphics gp = getGraphics();
				gp.setColor(Color.blue);
				Graphics2D gp2d = (Graphics2D) gp;
				gp2d.setStroke(new BasicStroke(2));
				gp2d.drawRect(gx, gy, e.getX()-gx, e.getY()-gy);
				selectBox = true;
			}
			repaint();
		}
		
		@Override
		public void mouseReleased(MouseEvent e) {
			if(selectBox == true) {
				CompositeGObject grouped = new CompositeGObject();
				List<GObject> gList = new ArrayList<GObject>();
				for(GObject g : gObjects) {
					if(g.intersect(gx, gy, e.getX()-gx, e.getY()-gy)) {
						grouped.add(g);
						gList.add(g);
					}
				}
				if(!gList.isEmpty()) {
					for(GObject g : gList) {
						gObjects.remove(g);
					}
					grouped.recalculateRegion();
					gObjects.add(grouped);
					target = grouped;
					grouped.selected();
				}
				repaint();
			}
			selectBox = false;
		}
	}
	
}