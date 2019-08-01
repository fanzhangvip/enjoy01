package com.example.socketfileupload;

import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.Label;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.swing.JLabel;

public class MainFrame extends Frame {

    private JLabel label;

    public MainFrame(String title) throws UnsupportedEncodingException {
        super(title);
        label = new JLabel();
        add(label, BorderLayout.PAGE_START);
        label.setText("服务器已经启动了");
        this.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {

            }

            @Override
            public void windowClosing(WindowEvent e) {

            }

            @Override
            public void windowClosed(WindowEvent e) {

            }

            @Override
            public void windowIconified(WindowEvent e) {

            }

            @Override
            public void windowDeiconified(WindowEvent e) {

            }

            @Override
            public void windowActivated(WindowEvent e) {

            }

            @Override
            public void windowDeactivated(WindowEvent e) {

            }
        });

    }

     public static void main(String ... args) throws UnknownHostException, UnsupportedEncodingException {
             MainFrame frame = new MainFrame("文件上传服务器: " + InetAddress.getLocalHost().getHostAddress());

             frame.setSize(600,400);
             frame.setVisible(true);
     }
}
