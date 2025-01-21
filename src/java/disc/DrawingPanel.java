package disc;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.LinkedList;
import java.util.ListIterator;

class DrawingPanel extends JPanel implements MouseListener, MouseMotionListener {

    private final LinkedList<Disc> discs;
    private Point tempCenter;
    private Point tempEdge;
    private Disc selectedDisc;
    private boolean isDragging;

    public DrawingPanel() {
        discs = new LinkedList<>();
        setBackground(Color.WHITE);
        setPreferredSize(new Dimension(800, 600));

        addMouseListener(this);
        addMouseMotionListener(this);
        setFocusable(true);

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_SHIFT) {
                    Point mousePos = getMousePosition();
                    if (mousePos != null) {
                        ListIterator<Disc> iterator = discs.listIterator(discs.size());
                        while (iterator.hasPrevious()) {
                            Disc disc = iterator.previous();
                            if (disc.contains(mousePos)) {
                                selectedDisc = disc;
                                isDragging = true;
                                break;
                            }
                        }
                    }
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_SHIFT) {
                    isDragging = false;
                    selectedDisc = null;
                }
            }
        });
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1 && !isDragging) {
            tempCenter = e.getPoint();
            tempEdge = e.getPoint();
        } else if (e.getButton() == MouseEvent.BUTTON3) {
            removeTopDiscAt(e.getPoint());
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (tempCenter != null && !isDragging) {
            int radius = (int) tempCenter.distance(e.getPoint());
            if (radius > 0) {
                discs.add(new Disc(tempCenter, radius, discs.size()));
            }
            tempCenter = null;
            tempEdge = null;
            repaint();
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if (isDragging && selectedDisc != null) {
            selectedDisc.setCenter(e.getPoint());
            discs.remove(selectedDisc);
            discs.addLast(selectedDisc); // Place le disque sélectionné en haut
            repaint();
        } else if (tempCenter != null && !isDragging) {
            tempEdge = e.getPoint();
            repaint();
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        // Not used, but required by MouseMotionListener.
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        // Not used, but required by MouseListener.
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        // Not used, but required by MouseListener.
    }

    @Override
    public void mouseExited(MouseEvent e) {
        // Not used, but required by MouseListener.
    }

    private void removeTopDiscAt(Point point) {
        ListIterator<Disc> iterator = discs.listIterator(discs.size());
        while (iterator.hasPrevious()) {
            Disc disc = iterator.previous();
            if (disc.contains(point)) {
                iterator.remove();
                repaint();
                return;
            }
        }
    }

    public void clearDiscs() {
        discs.clear();
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        // Dessine tous les disques existants
        for (Disc disc : discs) {
            g2d.setColor(disc.getColor());
            g2d.fillOval(disc.getX(), disc.getY(), disc.getDiameter(), disc.getDiameter());
        }

        // Dessine le cercle temporaire en dernier (au-dessus), avec transparence
        if (tempCenter != null && tempEdge != null && !isDragging) {
            g2d.setColor(new Color(128, 128, 128, 128)); // Gris semi-transparent (50% d'opacité)
            int radius = (int) tempCenter.distance(tempEdge);
            g2d.fillOval(tempCenter.x - radius, tempCenter.y - radius, radius * 2, radius * 2);
        }
    }


    @Override
    public Dimension getPreferredSize() {
        return new Dimension(800, 600);
    }
}
