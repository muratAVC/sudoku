package com.murat;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;

public class GameFrame implements ActionListener {


    int[][] mainboard=new int[9][9];
    int[][] suduku=
            {{5, 3, 4, 6, 7, 8, 9, 1, 2},
                    {6, 7, 2, 1, 9, 5, 3, 4, 8},
                    {1, 9, 8, 3, 4, 2, 5, 6, 7},
                    {8, 5, 9, 7, 6, 1, 4, 2, 3},
                    {4, 2, 6, 8, 5, 3, 7, 9, 1},
                    {7, 1, 3, 9, 2, 4, 8, 5, 6},
                    {9, 6, 1, 5, 3, 7, 2, 8, 4},
                    {2, 8, 7, 4, 1, 9, 6, 3, 5},
                    {3, 4, 5, 2, 8, 6, 1, 7, 9}};
    int[][] easySudoku;/*={
            {0,6,2,4,0,5,0,0,8},
            {0,0,5,0,0,0,4,2,0},
            {3,0,4,9,0,0,5,6,0},
            {0,0,0,6,0,2,9,8,4},
            {0,2,7,0,4,0,0,0,0},
            {0,0,0,0,0,1,0,0,0},
            {4,9,6,1,5,7,8,0,0},
            {2,0,8,0,0,0,7,0,5},
            {7,5,0,0,0,4,0,9,6}};*/

    Random random=new Random();

    public static final int PEN_WITH=500;
    public static final int PEN_HEIGHT=400;

    JFrame gameFrame=new JFrame();
    JPanel tabloPanel;
    JButton[][] buttons=new JButton[9][9];

    JPopupMenu popupMenu=new JPopupMenu();
    JMenuItem[] menuItems=new JMenuItem[10];

    Point point=new Point();

    GameFrame(){
        gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gameFrame.setResizable(false);
        //gameFrame.setLayout(new GridLayout(1,1));
        gameFrame.setSize(GameFrame.PEN_WITH,GameFrame.PEN_HEIGHT);
        gameFrame.setTitle("Sudoku Game(By Mavcı)");

        int diffuculty=SettingsFrame.dif;
        SudokuCreater sudokuCreater=new SudokuCreater(diffuculty);
        sudokuCreater.fillValues();
        easySudoku=sudokuCreater.resultSudoku();

        tabloPanel=new JPanel(){
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                drawLine(g);
            }

            // Düz çizgi çizmek için bir yardımcı metot oluşturuyoruz
            private void drawLine(Graphics g) {
                g.setColor(Color.BLUE); // Çizgi rengini kırmızı yapıyoruz
                g.drawLine(PEN_WITH/9*3,0,PEN_WITH/9*3,PEN_HEIGHT); // Başlangıç ve bitiş koordinatlarını belirliyoruz

            }
        };
        tabloPanel.setLayout(new GridLayout(9,9));
        tabloPanel.setBackground(new Color(215, 93, 93));
        gameFrame.add(tabloPanel,BorderLayout.CENTER);

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                buttons[i][j]=new JButton();
                //buttons[i][j].setSize(20,20);
                //buttons[i][j].setLocation(20*i,20*j);
                buttons[i][j].setFont(new Font("MV Boli",Font.BOLD,20));
                buttons[i][j].setFocusable(false);
                buttons[i][j].addActionListener(this);
                tabloPanel.add(buttons[i][j]);
            }
        }

        buttons[0][0].add(popupMenu);

        for (int i = 0; i < 9; i++) {
            menuItems[i]=new JMenuItem();
            menuItems[i].setText(i+1+"ekle");
            popupMenu.add(menuItems[i]);
        }
        menuItems[9] =new JMenuItem();
        menuItems[9].setText("Sil");
        popupMenu.add(menuItems[9]);

        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                drawLine(g);
            }

            // Düz çizgi çizmek için bir yardımcı metot oluşturuyoruz
            private void drawLine(Graphics g) {
                g.setColor(Color.BLUE); // Çizgi rengini kırmızı yapıyoruz
                g.drawLine(buttons[0][2].getLocation().x-1, 1, buttons[8][2].getLocation().x-1, buttons[8][2].getLocation().y+buttons[0][0].getHeight()); // Başlangıç ve bitiş koordinatlarını belirliyoruz
            }
        };



        menuItems[0].addActionListener(actionEvent -> {
            replaceButtonText(1);
        });
        menuItems[1].addActionListener(actionEvent -> {
            replaceButtonText(2);
        });
        menuItems[2].addActionListener(actionEvent -> {
            replaceButtonText(3);
        });
        menuItems[3].addActionListener(actionEvent -> {
            replaceButtonText(4);
        });
        menuItems[4].addActionListener(actionEvent -> {
            replaceButtonText(5);
        });
        menuItems[5].addActionListener(actionEvent -> {
            replaceButtonText(6);
        });
        menuItems[6].addActionListener(actionEvent -> {
            replaceButtonText(7);
        });
        menuItems[7].addActionListener(actionEvent -> {
            replaceButtonText(8);
        });
        menuItems[8].addActionListener(actionEvent -> {
            replaceButtonText(9);
        });
        menuItems[9].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                for (int i = 0; i < 9; i++) {
                    for (int j = 0; j < 9; j++) {
                        if (point.x == buttons[i][j].getLocation().x && point.y == buttons[i][j].getLocation().y) {
                            buttons[i][j].setText("-");
                            easySudoku[i][j]=0;
                        }
                    }
                }
            }
        });

        JMenuBar menuBar=new JMenuBar();
        JMenu oyun=new JMenu("Oyun");
        JMenuItem newGame=new JMenuItem("Yani Oyun");
        newGame.addActionListener(actionEvent -> {
            if (Main.frame!=null){
                int result = JOptionPane.showOptionDialog(null, "Oynadığınız oyunu bitirmek ve yeni bir oyun başlatmak istediğinizden eminmisinz", "Onay isteği", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, new Object[] { "Evet", "Hayır" }, "OK");
                if (result==0){
                    gameFrame.setVisible(false);
                    Main.settingsFrame=new SettingsFrame();
                }
            }

        });
        JMenuItem about=new JMenuItem("Hakkında");
        about.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                int result = JOptionPane.showOptionDialog(null, "Java öğreniyorum", "Sudoku Game", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, new Object[] { "Evet" }, "OK");

            }
        });
        JMenuItem exit=new JMenuItem("Kapat");
        exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                System.exit(1);
            }
        });
        oyun.add(newGame);
        oyun.add(about);
        oyun.add(exit);

        menuBar.add(oyun);


        gameFrame.add(menuBar,BorderLayout.NORTH);



        fill();
        gameFrame.setVisible(true);
    }

    public void replaceButtonText(int a){
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (point.x==buttons[i][j].getLocation().x && point.y==buttons[i][j].getLocation().y){
                    //System.out.println("checkR(i,j,a) = " + checkR(i, j, a));
                    if (!checkR(i,j,a)){
                        if (!checkC(i,j,a)){
                            if (!checkG(i,j,a)){
                                buttons[i][j].setText(a+"");
                                easySudoku[i][j]=a;
                            }
                        }
                    }
                }
            }
        }
    }
    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (actionEvent.getSource()==buttons[i][j]){
                    point=buttons[i][j].getLocation();
                    popupMenu.show(gameFrame,point.x,point.y);
                }
            }
        }



    }

    public void fill(){
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (easySudoku[i][j]==0){
                    buttons[i][j].setText("-");
                }else{
                    //buttons[i][j].setForeground(Color.RED);
                    //buttons[i][j].setFont(new Font(Font.SANS_SERIF,Font.BOLD,20));
                    buttons[i][j].setBackground(new Color(243, 191, 23));
                    buttons[i][j].setText(easySudoku[i][j]+"");
                    buttons[i][j].setEnabled(false);
                }
            }
        }
    }



    public void fillBorder(){
        ArrayList<Integer> row = new ArrayList<>(9);
        int count=0;
        for (int i = 0; i < 9; i++) {//row count
            for (int j = 1; j <= 9; j++) {
                row.add(j);
            }

            int j=0;
            while (row.size()!=0){
                int a;
                if (row.size()!=1){
                    a = row.get(random.nextInt(1,row.size()));//dizideki rastgele bir sayıyı alıyor
                }else {
                    a= row.get(0);
                }
                while (true){
                    boolean con=checkC(i,j,a),cvb=checkG(i,j,a);
                    if (!con){
                        if (!cvb){
                            break;
                        }
                    }
                    a = row.get(random.nextInt(row.size()));//dizideki rastgele bir sayıyı alıyor
                    //System.out.println("received number"+a);
                }
                row.remove((Integer) a);
                suduku[i][j]=a;
                buttons[count][1].setText(a+"");
                count++;
                j++;
            }
        }
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                System.out.print(suduku[i][j]+"-");
            }
            System.out.println();
        }
    }

    public boolean checkR(int row,int col, int a){

        for (int i = 0; i < 9; i++) {
            if (easySudoku[row][i] == a ) {
                return true;
            }
        }
        return false;
    }
    public boolean checkC(int row,int col, int a){

        for (int i = 0; i < 9; i++) {
            if (easySudoku[i][col] == a) {
                return true;
            }
        }
        return false;
    }
    public boolean checkG(int row,int col, int a){
        int startRow = (row / 3) * 3;
        int startCol = (col / 3) * 3;
        for (int i = startRow; i < startRow + 3; i++) {
            for (int j = startCol; j < startCol + 3; j++) {
                if (easySudoku[i][j] == a) {
                    return true;
                }
            }
        }
        return false;
    }

}
