package test01;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.color.ColorSpace;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.DataBuffer;
import java.awt.image.DirectColorModel;
import java.awt.image.Raster;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class Test01Main extends JFrame {

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					new Test01Main();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public Test01Main() throws Exception {
		super("title");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setResizable(false);
		
		JButton b = new JButton("Abc");
		b.setIcon(new ImageIcon(ImageIO.read(new File("C:/Users/Public/Pictures/Sample Pictures/Desert.jpg"))));
		add(b);
		pack();
		setVisible(true);
		
		b.addMouseMotionListener(new MouseAdapter() {
			@Override
			public void mouseMoved(MouseEvent e) {
				Test01Main.this.repaint();
			}
		});
		
		init();
	}
	
	BufferedImage bi1;
	Graphics2D bi1g;
	
	BufferedImage bi2;
	Graphics2D bi2g;

	private void init() {
		bi1 = new BufferedImage(
				new DirectColorModel(24, 0x00ff0000, 0x0000ff00, 0x00000ff),
				Raster.createPackedRaster(DataBuffer.TYPE_INT, getWidth(), getHeight(), new int[] {0x00ff0000, 0x0000ff00, 0x00000ff}, null),
				false,
				null
				);
		bi1g = bi1.createGraphics();
		
		
		bi2 = ImageUtils.createBufferedImage(ImageUtils.colorModel_24BPP,  ImageUtils.createInterleavedRaster(getWidth(), getHeight(), 24,  ImageUtils.bpp_24));
		bi2g = bi2.createGraphics();
	}

	long tint = 0;
	long tbyte = 0;
	int samples = 0;

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		if (bi1 == null) return;
		long t;
		
		t = System.currentTimeMillis();
		super.paint(bi1g);
		tint += System.currentTimeMillis()-t;
		System.out.println("offPaint/avg (int): " + ((float)tint / samples));
		
		t = System.currentTimeMillis();
		super.paint(bi2g);
		tbyte += System.currentTimeMillis()-t;
		System.out.println("offPaint/avg(byte): " + ((float)tbyte / samples));
		
		samples++;
		
		System.out.println("-");
	}

}
