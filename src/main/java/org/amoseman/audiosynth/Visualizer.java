package org.amoseman.audiosynth;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.nio.ByteBuffer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Visualizer extends JPanel implements AudioProcessorListener {
    private final BufferedImage buffer;
    private final JFrame frame;
    private final ExecutorService service;
    final int height = 400;
    final int width = 400;
    final int scale = 2;
    final int n;
    final int[] x;
    final int[] y;
    int i;
    int chunks = 16;

    public Visualizer(JFrame frame, int n) {
        this.buffer = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        this.frame = frame;
        this.service = Executors.newCachedThreadPool();

        this.n = n;
        this.x = new int[n * chunks];
        for (int k = 0; k < x.length; k++) {
            x[k] = (int) ((double) k / x.length * width);
        }
        this.y = new int[x.length];
        this.i = 0;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(buffer, 0, 0, width * scale, height * scale, this);
    }

    @Override
    public void onFull(byte[] leftSampleBuffer, byte[] rightSampleBuffer) {
        Graphics graphics = buffer.getGraphics();
        //service.submit(() -> {

            ByteBuffer bb = ByteBuffer.wrap(leftSampleBuffer);

            for (int k = 0; k < n; k++) {
                double leftSample = bb.getShort();
                double leftYNormalized = (leftSample / Short.MAX_VALUE);
                double leftYScaled = leftYNormalized * 5;
                leftYScaled = leftYScaled * height + height / 2;
                int leftY = (int) (leftYScaled);
                y[k + n * i] = leftY;
            }

            i++;
            if (i % chunks == 0) {
                i = 0;

                graphics.setColor(Color.BLACK);
                graphics.fillRect(0, 0, width, height);

                graphics.setColor(Color.WHITE);

                graphics.drawPolyline(x, y, x.length);
                frame.repaint();
            }
        //});
    }
}
