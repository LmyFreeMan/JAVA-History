package com;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


/**
 * @Auther:Cui LiHuan
 * @Date: 2018/12/3 09:34
 * @Description:
 */
public class Draw extends JFrame implements MouseMotionListener, MouseListener {
    private static Color color = Color.BLACK; //画笔颜色
    private static int size = 1; //画笔大小
    //模式
    private static int DRAWLINEA = 1;
    private static int DRAWLINEB = 2;
    private static int DRAWLINEC = 3;
    private static int DRAW_ERASER = 4;
    private static int DRAW_OVAL = 5;
    private static int DRAW_RECTAGULAR = 6;
    private static int DRAW_Line = 7;
    private JPanel p; //画板
    private int startx, starty, endx, endy; //始止位置
    private int ox, oy, oldx, oldy;
    private int model = 1;
    private int oldeModel = 1;
    private boolean flag =  true; //用于判断擦除是否开启

    public Draw() {
        addMenu();
        addToolBar();
        p = new JPanel();
        this.add(p, BorderLayout.CENTER);
        p.addMouseMotionListener(this);
        p.addMouseListener(this);
        this.init();
    }

    public static void main(String[] args) {
        new Draw();
    }


    /**
     * 添加工具栏
     */
    public void addToolBar() {
        JToolBar jToolBar = new JToolBar();
        FlowLayout flowLayout = new FlowLayout();
        flowLayout.setAlignment(FlowLayout.LEADING);
        jToolBar.setLayout(flowLayout);

        String[] s = {"1", "2", "3", "4", "5", "6"};//用于选择线条的宽度
        JComboBox combo1 = new JComboBox(s);
        combo1.setBorder(BorderFactory.createTitledBorder("选择线条的宽度"));
        combo1.setPreferredSize(new Dimension(150, 50));
        combo1.addItemListener(new ItemListener() { //添加监听事件
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    String itemSize = (String) e.getItem();
                    try {
                        size = Integer.parseInt(itemSize); //设置画笔的宽度
                    } catch (Exception ex) {

                    }
                }
            }
        });
        jToolBar.add(combo1);
        ButtonGroup buttonGroup = new ButtonGroup();//设置jradiobutton按钮
        JRadioButton createCircle = new JRadioButton("画圆");
        createCircle.addItemListener(new ItemListener() { //添加监听器
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (createCircle.isSelected()) {
                    System.out.println("画圆");
                    model = Draw.DRAW_OVAL;
                }
            }
        });
        JRadioButton createRectangular = new JRadioButton("画矩形");
        createRectangular.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (createRectangular.isSelected()) {
                    System.out.println("画矩形");
                    model = Draw.DRAW_RECTAGULAR;
                }
            }
        });
        JRadioButton createLine = new JRadioButton("画直线");
        createLine.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (createLine.isSelected()) {
                    System.out.println("画直线");
                    model = Draw.DRAW_Line;

                }
            }
        });
        buttonGroup.add(createCircle);
        buttonGroup.add(createRectangular);
        buttonGroup.add(createLine);

        //工具栏中添加组件
        jToolBar.add(createCircle);
        jToolBar.add(createRectangular);
        jToolBar.add(createLine);

        JButton selectColor = new JButton("选择颜色"); //颜色选择器
        selectColor.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Draw.color = JColorChooser.showDialog(null, "选择颜色", Draw.color);
            }
        });
        jToolBar.add(selectColor); //将颜色选择器填加到工具栏中

        JButton eraser = new JButton("擦除"); //橡皮擦
        eraser.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (flag) {
                    oldeModel = model;
                    model = 4;
                    flag = false;
                } else {
                    model = oldeModel;
                    flag = true;
                }
            }
        });
        jToolBar.add(eraser);

        this.add(jToolBar, BorderLayout.NORTH); //添加到主框架中
    }

    /**
     * 创建菜单栏
     */
    public void addMenu() {
        Font font = new Font("STSong ", Font.PLAIN, 13);//宋体
        JMenuBar mb = new JMenuBar(); // 创建菜单栏MenuBar
        mb.setFont(font);

        JMenu menuShow = new JMenu("Start");
        JMenuItem createNew = new JMenuItem("New");
        createNew.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("new");
                p.repaint();
            }
        });
        JMenu drawLine = new JMenu("DrawLine");
        JMenuItem drawLineA = new JMenuItem("drawLineA");
        drawLineA.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                model = 1;
            }
        });
        JMenuItem drawLineB = new JMenuItem("drawLineB");
        drawLineB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                model = 2;
            }
        });
        JMenuItem drawLineC = new JMenuItem("drawLinec");
        drawLineC.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                model = 3;
            }
        });
        drawLine.add(drawLineA);
        drawLine.add(drawLineB);
        drawLine.add(drawLineC);

        JMenuItem exit = new JMenuItem("Exit");
        exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        menuShow.add(createNew);
        menuShow.add(drawLine);
        menuShow.add(exit);

        mb.add(menuShow);
        this.setJMenuBar(mb);

    }

    /**
     * 初始化Jframe
     */
    public void init() {
        this.setTitle("绘图软件"); //标题
        this.setSize(800, 600); //设置大小
        //居中显示
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        int x = (int) (toolkit.getScreenSize().getWidth() - this.getWidth()) / 2;
        int y = (int) (toolkit.getScreenSize().getHeight() - this.getHeight()) / 2;
        this.setLocation(x, y); //设置位置

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true); //显示出来
    }

    /**
     * 鼠标拖动的行为
     * @param e
     */
    public void mouseDragged(MouseEvent e) {
        if (model == Draw.DRAWLINEB) { //从鼠标左键按下 随鼠标拖动划直线
            int length = (int) Math.sqrt((e.getX() - startx) * (e.getX() - startx) + (e.getY() - starty) * (e.getY() - starty));
            if(length>20){
                Container c = (Container) e.getSource();
                Graphics2D g = (Graphics2D) c.getGraphics();
                g.setStroke(new BasicStroke(size * 2, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL));
                g.drawLine(startx, starty, e.getX(), e.getY());
                startx = e.getX();
                starty = e.getY();
            }
        } else if (model == Draw.DRAW_Line) {//点下鼠标左键后 拖动鼠标线条随之移动，释放左键后画出直线
            Container c = (Container) e.getSource();
            Graphics2D g = (Graphics2D) c.getGraphics();
            g.setColor(this.getBackground());
            g.setStroke(new BasicStroke(size * 2, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL));
            g.drawLine(startx, starty, oldx, oldy);
            g.setColor(Draw.color);
            g.drawLine(startx, starty, e.getX(), e.getY());
            oldx = e.getX();
            oldy = e.getY();
        }
    }

    /**
     * 鼠标移动事件
     * @param e
     */
    public void mouseMoved(MouseEvent e) {

        if (model == Draw.DRAWLINEC) { //鼠标左键按下，随鼠标移动画任意曲线
            Container c = (Container) e.getSource();
            Graphics2D g = (Graphics2D) c.getGraphics();
            g.setColor(Draw.color);
            g.setStroke(new BasicStroke(size * 2, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL));
            g.drawLine(ox, oy, e.getX(), e.getY());

        } else if (model == Draw.DRAW_ERASER) { //用于擦除图像
            Container c = (Container) e.getSource();
            Graphics g = c.getGraphics();
            g.clearRect(ox, oy, 5, 5);
        }
        ox = e.getX();
        oy = e.getY();
    }


    /**
     * 鼠标按下事件
     * @param e
     */
    @Override
    public void mousePressed(MouseEvent e) {
        startx = e.getX();
        starty = e.getY();

    }

    /**
     * 鼠标释放事件
     * @param e
     */
    @Override
    public void mouseReleased(MouseEvent e) {
        endx = e.getX();
        endy = e.getY();
        if (model == Draw.DRAWLINEA) { //从鼠标左键按下到鼠标左键放开划线
            Container c = (Container) e.getSource();
            Graphics2D g = (Graphics2D) c.getGraphics();
            g.setColor(Draw.color);
            g.setStroke(new BasicStroke(size * 2, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL));
            g.drawLine(startx, starty, endx, endy);
        } else if (model == DRAW_OVAL) {//画圆
            Container c = (Container) e.getSource();
            Graphics2D g = (Graphics2D) c.getGraphics();
            g.setColor(Draw.color);
            g.setStroke(new BasicStroke(size * 2, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL));
            int length = (int) Math.sqrt((endx - startx) * (endx - startx) + (endy - starty) * (endy - starty));
            g.drawOval(startx, starty, length, length);
        } else if (model == Draw.DRAW_RECTAGULAR) { //画矩形
            Container c = (Container) e.getSource();
            Graphics2D g = (Graphics2D) c.getGraphics();
            g.setColor(Draw.color);
            g.setStroke(new BasicStroke(size * 2, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL));
            g.drawRect(startx, starty, endx - startx, endy - starty);
        }

    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
