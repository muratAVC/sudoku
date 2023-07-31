package com.murat;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;

public class SettingsFrame {
    public static int dif=40;
    SettingsFrame(){
        JFrame frame=new JFrame("Sudokunun zorluk derecesini ayarla");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setSize(300,100);

        JLabel label=new JLabel("Oyunun zorluk derecesini ayarlayın");
        JLabel difValue=new JLabel("Zorluk= 40");

        JButton button=new JButton("Tamam");
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String str=difValue.getText();
                //dif=Integer.parseInt(str.substring(str.length()-2));
                StringBuilder rakamlar = new StringBuilder();
                for (int i = 0; i < str.length(); i++) {
                    char ch = str.charAt(i);
                    if (Character.isDigit(ch)) {
                        rakamlar.append(ch);
                    }
                }
                dif=Integer.parseInt(rakamlar.toString());
                System.out.println(dif);
                frame.setVisible(false);
                Main.frame=new GameFrame();
            }
        });


        JPanel panel=new JPanel();
        panel.setLayout(new BorderLayout());


        JScrollBar scrollBar=new JScrollBar();
        scrollBar.setSize(100,10);
        scrollBar.setMaximum(91);
        scrollBar.setMinimum(1);
        scrollBar.setValue(40);
        scrollBar.setOrientation(JScrollBar.HORIZONTAL);



        scrollBar.addAdjustmentListener(new AdjustmentListener() {
            @Override
            public void adjustmentValueChanged(AdjustmentEvent e) {
                difValue.setText("Zorluk= "+scrollBar.getValue());
            }
        });

        panel.add(label,BorderLayout.NORTH);
        panel.add(difValue,BorderLayout.CENTER);
        panel.add(button,BorderLayout.EAST);
        panel.add(scrollBar,BorderLayout.SOUTH);
        frame.add(panel);
        frame.setVisible(true);


    }
}
