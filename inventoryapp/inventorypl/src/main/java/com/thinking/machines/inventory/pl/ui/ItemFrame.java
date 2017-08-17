package com.thinking.machines.inventory.pl.ui;
import javax.swing.*;
import java.awt.*;
public class ItemFrame extends JFrame
{
private ItemPanel itemPanel;
private Container container;
private ImageIcon appIcon;
public ItemFrame()
{ 
initComponents();
setAppearance();
addListeners();
setDefaultCloseOperation(EXIT_ON_CLOSE);
}

private void initComponents()
{
this.setTitle("Inventory application");
this.container=getContentPane();
this.itemPanel=new ItemPanel();
appIcon=new ImageIcon(this.getClass().getResource("/icons/app_icon.png"));
setIconImage(appIcon.getImage());
}

private void setAppearance()
{
this.itemPanel.setBorder(BorderFactory.createLineBorder(Color.gray));
this.container.setLayout(null);
int lm,tm;
lm=5;
tm=5;
this.itemPanel.setBounds(lm+0,tm+0,490,600);
this.container.add(this.itemPanel);
this.setSize(515,650);
Dimension dimension=Toolkit.getDefaultToolkit().getScreenSize();
this.setLocation(dimension.width/2-this.getWidth()/2,dimension.height/2-this.getHeight()/2);
this.setVisible(true);
}

private void addListeners()
{
}
}