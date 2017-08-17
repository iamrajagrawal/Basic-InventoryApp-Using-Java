package com.thinking.machines.inventory.pl.ui;
import java.awt.event.*;
import javax.swing.*;
import java.awt.*;
import javax.swing.event.*;
import com.thinking.machines.inventory.pl.model.interfaces.*;
import com.thinking.machines.inventory.pl.model.exceptions.*;
import com.thinking.machines.inventory.pl.model.*;
public class ItemPanel extends JPanel implements DocumentListener,ListSelectionListener,ActionListener
{
private enum
MODE{VIEW_MODE,ADD_MODE,EDIT_MODE,DELETE_MODE,EXPORT_TO_PDF_MODE};
private MODE mode;
private ItemCRUDPanel itemCRUDPanel;
private JLabel moduleTitleLabel;
private JLabel searchErrorLabel;
private JLabel searchCaptionLabel;
private JTextField searchTextField;
private JButton clearSearchTextFieldButton;
private JTable itemTable;
private JScrollPane itemTableScrollPane;
private ItemModel itemModel;

public ItemPanel()
{
initComponents();
setAppearance();
addListeners();
this.setViewMode();
}

private void initComponents()
{
moduleTitleLabel=new JLabel("Items");
searchErrorLabel=new JLabel(" ");
searchCaptionLabel=new JLabel("Search");
searchTextField=new JTextField();
clearSearchTextFieldButton=new JButton("x");
itemModel=new ItemModel();
itemTable=new JTable(itemModel);
itemTableScrollPane=new JScrollPane(itemTable,ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
itemCRUDPanel=new ItemCRUDPanel();
}

private void setAppearance()
{
this.setLayout(null);
int lm,tm;
lm=5;
tm=3;
Font moduleTitleFont=new Font("Verdana",Font.BOLD,24);
moduleTitleLabel.setFont(moduleTitleFont);
Font dataFont=new Font("Verdana",Font.PLAIN,16);
Font searchErrorFont=new Font("Verdana",Font.BOLD,10);
searchErrorLabel.setFont(searchErrorFont);
searchErrorLabel.setForeground(Color.red);
searchCaptionLabel.setFont(dataFont);
searchTextField.setFont(dataFont);
itemTable.setFont(dataFont);
itemTable.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
moduleTitleLabel.setBounds(lm+0,tm+0,150,40);
searchErrorLabel.setBounds(lm+0+50+10+320,tm+0+20,60,25);
searchCaptionLabel.setBounds(lm+0,tm+0+40+5,90,30);
searchTextField.setBounds(lm+0+50+10,tm+0+40+5,380,30);
clearSearchTextFieldButton.setBounds(lm+0+50+10+380+5,tm+0+40+5,30,30);
itemTableScrollPane.setBounds(lm+0,tm+0+40+5+30+5,480,250);
this.itemTable.getColumnModel().getColumn(0).setPreferredWidth(80);
this.itemTable.getColumnModel().getColumn(1).setPreferredWidth(80);
this.itemTable.getColumnModel().getColumn(2).setPreferredWidth(320);
this.itemTable.setRowHeight(30);
itemCRUDPanel.setBorder(BorderFactory.createLineBorder(Color.gray));
itemCRUDPanel.setBounds(lm+0,tm+0+40+5+30+5+250+5,480,250);
this.add(moduleTitleLabel);
this.add(searchErrorLabel);
this.add(searchCaptionLabel);
this.add(searchTextField);
this.add(clearSearchTextFieldButton);
this.add(itemTableScrollPane);
this.add(itemCRUDPanel);
}

private void addListeners()
{
this.searchTextField.getDocument().addDocumentListener(this);
this.itemTable.getSelectionModel().addListSelectionListener(this);
this.clearSearchTextFieldButton.addActionListener(this);
}

private void searchItem()
{
searchErrorLabel.setText("");
String itemToSearch=searchTextField.getText().trim();
if(itemToSearch.length()==0) return;
try
{
int index=itemModel.searchItem(itemToSearch,true,false);
ListSelectionModel listSelectionModel=this.itemTable.getSelectionModel();
listSelectionModel.setSelectionInterval(index,index);
itemTable.scrollRectToVisible(itemTable.getCellRect(index,0,true));
}catch(ModelException modelException)
{
searchErrorLabel.setText("Not found");
}
}

public void changedUpdate(DocumentEvent event)
{
searchItem();
}

public void insertUpdate(DocumentEvent event)
{
searchItem();
}

public void removeUpdate(DocumentEvent event)
{
searchItem();
}

private void setViewMode()
{
this.mode=MODE.VIEW_MODE;
this.searchTextField.setEnabled(true);
this.clearSearchTextFieldButton.setEnabled(true);
this.itemTable.setEnabled(true);
}

public void setAddMode()
{
this.mode=MODE.ADD_MODE;
this.searchTextField.setEnabled(false);
this.clearSearchTextFieldButton.setEnabled(false);
this.itemTable.setEnabled(false);
}

public void setEditMode()
{
this.mode=MODE.EDIT_MODE;
this.searchTextField.setEnabled(false);
this.clearSearchTextFieldButton.setEnabled(false);
this.itemTable.setEnabled(false);
}

public void valueChanged(ListSelectionEvent ev)
{
int selectedRowIndex=itemTable.getSelectedRow();
if(selectedRowIndex<0 || selectedRowIndex>=itemModel.getRowCount())
{ 
itemCRUDPanel.setItem(null);
} 
else
{ 
try
{
ItemInterface item=itemModel.getItemAt(selectedRowIndex);
itemCRUDPanel.setItem(item);
}catch(ModelException modelException)
{
itemCRUDPanel.setItem(null);
}
}
}

public void actionPerformed(ActionEvent ev)
{ 
if(ev.getSource()==clearSearchTextFieldButton)
{
searchTextField.setText("");
}
} 

// inner class start
class ItemCRUDPanel extends JPanel implements ActionListener
{
private ItemInterface item;
private JLabel codeCaption;
private JLabel codeLabel;
private JTextField codeTextField;
private JButton clearCodeButton;
private JLabel nameCaption;
private JLabel nameLabel;
private JTextField nameTextField;
private JButton clearNameButton;
private JLabel categoryCaption;
private JLabel categoryLabel;
private JTextField categoryTextField;
private JButton clearCategoryButton;
private JLabel priceCaption;
private JLabel priceLabel;
private JTextField priceTextField;
private JButton clearPriceButton;
private JPanel buttonsPanel;
private JButton addButton;
private JButton editButton;
private JButton cancelButton;
private JButton deleteButton;
private JButton exportToPDFButton;

private ImageIcon addIcon;
private ImageIcon editIcon;
private ImageIcon deleteIcon;
private ImageIcon cancelIcon;
private ImageIcon saveIcon;
private ImageIcon exportToPDFIcon;

ItemCRUDPanel()
{
initComponents();
addListeners();
setAppearance();
this.setViewMode();
}

public void initComponents()
{
codeCaption=new JLabel("Code");
codeLabel=new JLabel("");
codeTextField=new JTextField();
clearCodeButton=new JButton("x");
nameCaption=new JLabel("Name");
nameLabel=new JLabel("");
nameTextField=new JTextField();
clearNameButton=new JButton("x");
categoryCaption=new JLabel("Category");
categoryLabel=new JLabel();
categoryTextField=new JTextField();
clearCategoryButton=new JButton("x");
priceCaption=new JLabel("Price");
priceLabel=new JLabel();
priceTextField=new JTextField();
clearPriceButton=new JButton("x");
buttonsPanel=new JPanel();


addIcon=new ImageIcon(this.getClass().getResource("/icons/add_icon.png"));
editIcon=new ImageIcon(this.getClass().getResource("/icons/edit_icon.png"));
deleteIcon=new ImageIcon(this.getClass().getResource("/icons/delete_icon.png"));
cancelIcon=new ImageIcon(this.getClass().getResource("/icons/cancel_icon.png"));
saveIcon=new ImageIcon(this.getClass().getResource("/icons/save_icon.png"));
exportToPDFIcon=new ImageIcon(this.getClass().getResource("/icons/pdf_icon.png"));


addButton=new JButton(addIcon);
editButton=new JButton(editIcon);
deleteButton=new JButton(deleteIcon);
cancelButton=new JButton(cancelIcon);
exportToPDFButton=new JButton(exportToPDFIcon);
}

public void setAppearance()
{
Font dataFont=new Font("Verdana",Font.PLAIN,16);
int lm=0;
int tm=5;
codeCaption.setBounds(lm+5,tm+5,100,30);
codeLabel.setBounds(lm+5+100+2,tm+5,150,30);
codeTextField.setBounds(lm+5+100+2,tm+5,150,30);
clearCodeButton.setBounds(lm+5+100+2+150+1,tm+5,30,30);
nameCaption.setBounds(lm+5,tm+5+30+5,70,30);
nameLabel.setBounds(lm+5+100+2,tm+5+30+5,300,30);
nameTextField.setBounds(lm+5+100+2,tm+5+30+5,300,30);
clearNameButton.setBounds(lm+5+100+2+300+1,tm+5+30+5,30,30);
categoryCaption.setBounds(lm+5,tm+5+30+5+30+5,100,30);
categoryLabel.setBounds(lm+5+100+2,tm+5+30+5+30+5,300,30);
categoryTextField.setBounds(lm+5+100+2,tm+5+30+5+30+5,300,30);
clearCategoryButton.setBounds(lm+5+100+2+300+1,tm+5+30+5+30+5,30,30);
priceCaption.setBounds(lm+5,tm+5+30+5+30+5+30+5,70,30);
priceLabel.setBounds(lm+5+100+2,tm+5+30+5+30+5+30+5,150,30);
priceTextField.setBounds(lm+5+100+2,tm+5+30+5+30+5+30+5,150,30);
clearPriceButton.setBounds(lm+5+100+2+150+1,tm+5+30+5+30+5+30+5,30,30);
buttonsPanel.setBounds(85,tm+5+30+5+30+5+30+5+30+25,310,70);
buttonsPanel.setBorder(BorderFactory.createLineBorder(Color.gray));
buttonsPanel.setLayout(null);
addButton.setBounds(10,10,50,50);
editButton.setBounds(70,10,50,50);
cancelButton.setBounds(130,10,50,50);
deleteButton.setBounds(190,10,50,50);
exportToPDFButton.setBounds(250,10,50,50);
buttonsPanel.add(addButton);
buttonsPanel.add(editButton);
buttonsPanel.add(cancelButton);
buttonsPanel.add(deleteButton);
buttonsPanel.add(exportToPDFButton);

codeCaption.setFont(dataFont);
codeLabel.setFont(dataFont);
codeTextField.setFont(dataFont);
nameCaption.setFont(dataFont);
nameLabel.setFont(dataFont);
nameTextField.setFont(dataFont);
categoryCaption.setFont(dataFont);
categoryLabel.setFont(dataFont);
categoryTextField.setFont(dataFont);
priceCaption.setFont(dataFont);
priceLabel.setFont(dataFont);
priceTextField.setFont(dataFont);
setLayout(null);
add(codeCaption);
add(codeTextField);
add(clearCodeButton);
add(codeLabel);
add(nameCaption);
add(nameLabel);
add(nameTextField);
add(clearNameButton);
add(categoryCaption);
add(categoryLabel);
add(categoryTextField);
add(clearCategoryButton);
add(priceCaption);
add(priceLabel);
add(priceTextField);
add(clearPriceButton);
add(buttonsPanel);
}

public void addListeners()
{
addButton.addActionListener(this);
editButton.addActionListener(this);
deleteButton.addActionListener(this);
cancelButton.addActionListener(this);
exportToPDFButton.addActionListener(this);
clearCodeButton.addActionListener(this);
clearNameButton.addActionListener(this);
clearCategoryButton.addActionListener(this);
clearPriceButton.addActionListener(this);
}

public void actionPerformed(ActionEvent ev)
{
if(ev.getSource()==clearCodeButton)
{
codeTextField.setText("");
return;
}
if(ev.getSource()==clearNameButton)
{
nameTextField.setText("");
return;
}
if(ev.getSource()==clearCategoryButton)
{
categoryTextField.setText("");
return;
}
if(ev.getSource()==clearPriceButton)
{
priceTextField.setText("");
return;
}
if(ev.getSource()==addButton)
{
if(mode==MODE.VIEW_MODE)
{
ItemPanel.this.setAddMode();
this.setAddMode();
} 
else
{

String vName=nameTextField.getText().trim();
String vCategory=categoryTextField.getText().trim();


if(codeTextField.getText().trim().length()==0)
{
JOptionPane.showMessageDialog(ItemPanel.this,"Code required");
codeTextField.requestFocus();
return;
}
if(vName.length()==0)
{
JOptionPane.showMessageDialog(ItemPanel.this,"Name required");
nameTextField.requestFocus();
return;
} 
if(vCategory.length()==0)
{
JOptionPane.showMessageDialog(ItemPanel.this,"Category required");
categoryTextField.requestFocus();
return;
} 
if(priceTextField.getText().trim().length()==0)
{
JOptionPane.showMessageDialog(ItemPanel.this,"Price required");
priceTextField.requestFocus();
return;
} 
int vCode=0;
int vPrice=0;
try
{
vCode=Integer.parseInt(codeTextField.getText().trim());
}catch(NumberFormatException nfe)
{
JOptionPane.showMessageDialog(this,"Code should be a number");
codeTextField.requestFocus();
return;
}
try
{
vPrice=Integer.parseInt(priceTextField.getText().trim());
}catch(NumberFormatException numberFormatException)
{
JOptionPane.showMessageDialog(this,"Price should be a number");
priceTextField.requestFocus();
return;
}
ItemInterface itemInterface=new Item();
itemInterface.setCode(vCode);
itemInterface.setName(vName);
itemInterface.setCategory(vCategory);
itemInterface.setPrice(vPrice);
try
{
itemModel.add(itemInterface);
itemModel.fireTableDataChanged();
ItemPanel.this.setViewMode();
this.setViewMode();
int index=itemModel.getIndexOf(itemInterface);

ListSelectionModel listSelectionModel=itemTable.getSelectionModel();
listSelectionModel.setSelectionInterval(index,index);

itemTable.scrollRectToVisible(itemTable.getCellRect(index,0,true));



}catch(ModelException modelException)
{
JOptionPane.showMessageDialog(this,modelException.getMessage());
}
}
}

if(ev.getSource()==editButton)
{
if(mode==MODE.VIEW_MODE)
{
int index=itemTable.getSelectedRow();
if(index<0 || index>itemModel.getRowCount())
{
JOptionPane.showMessageDialog(ItemPanel.this,"Select an item");
return;
}
ItemPanel.this.setEditMode();
this.setEditMode();
codeTextField.setText(String.valueOf(this.item.getCode()));
nameTextField.setText(this.item.getName());
categoryTextField.setText(this.item.getCategory());
priceTextField.setText(String.valueOf(this.item.getPrice()));
} 
else
{

String vName=nameTextField.getText().trim();
String vCategory=categoryTextField.getText().trim();


if(codeTextField.getText().trim().length()==0)
{
JOptionPane.showMessageDialog(ItemPanel.this,"Code required");
codeTextField.requestFocus();
return;
}
if(vName.length()==0)
{
JOptionPane.showMessageDialog(ItemPanel.this,"Name required");
nameTextField.requestFocus();
return;
} 
if(vCategory.length()==0)
{
JOptionPane.showMessageDialog(ItemPanel.this,"Category required");
categoryTextField.requestFocus();
return;
} 
if(priceTextField.getText().trim().length()==0)
{
JOptionPane.showMessageDialog(ItemPanel.this,"Price required");
priceTextField.requestFocus();
return;
} 
int vCode=0;
int vPrice=0;
try
{
vCode=Integer.parseInt(codeTextField.getText().trim());
}catch(NumberFormatException numberFormatException)
{
JOptionPane.showMessageDialog(this,"Code should be a number");
codeTextField.requestFocus();
return;
}
try
{
vPrice=Integer.parseInt(priceTextField.getText().trim());
}catch(NumberFormatException numberFormatException)
{
JOptionPane.showMessageDialog(this,"Price should be a number");
priceTextField.requestFocus();
return;
}
ItemInterface itemInterface=new Item();
itemInterface.setCode(vCode);
itemInterface.setName(vName);
itemInterface.setCategory(vCategory);
itemInterface.setPrice(vPrice);
try
{
itemModel.update(itemInterface);
itemModel.fireTableDataChanged();
ItemPanel.this.setViewMode();
this.setViewMode();
int index=itemModel.getIndexOf(itemInterface);

ListSelectionModel listSelectionModel=itemTable.getSelectionModel();
listSelectionModel.setSelectionInterval(index,index);

itemTable.scrollRectToVisible(itemTable.getCellRect(index,0,true));
try
{
ItemInterface item=itemModel.getItemAt(index);
itemCRUDPanel.setItem(item);
}catch(ModelException modelException)
{
itemCRUDPanel.setItem(null);
}
}catch(ModelException modelException)
{
JOptionPane.showMessageDialog(this,modelException.getMessage());
}
}
}


if(ev.getSource()==deleteButton)
{
if(item==null)
{
JOptionPane.showMessageDialog(ItemPanel.this,"Select an item");
return;
}
else
{
int x=JOptionPane.showConfirmDialog(ItemPanel.this,"Are you sure want you want to delete this item?","Delete item",JOptionPane.YES_NO_OPTION);
if(x==JOptionPane.YES_OPTION)
{
try
{
itemModel.delete(this.item.getCode());
itemModel.fireTableDataChanged();
}catch(ModelException modelException)
{
JOptionPane.showMessageDialog(ItemPanel.this,modelException.getMessage());
}
}
else
{
ItemPanel.this.setViewMode();
this.setViewMode();
}
}
}

if(ev.getSource()==cancelButton)
{
ItemPanel.this.setViewMode();
this.setViewMode();
}
}

private void setViewMode()
{
codeLabel.setVisible(true);
codeTextField.setVisible(false);
clearCodeButton.setVisible(false);
nameTextField.setVisible(false);
clearNameButton.setVisible(false);
nameLabel.setVisible(true);
categoryTextField.setVisible(false);
clearCategoryButton.setVisible(false);
categoryLabel.setVisible(true);
priceTextField.setVisible(false);
clearPriceButton.setVisible(false);
priceLabel.setVisible(true);
addButton.setIcon(addIcon);
editButton.setIcon(editIcon);
deleteButton.setIcon(deleteIcon);
addButton.setEnabled(true);
cancelButton.setEnabled(false);
if(itemModel.getRowCount()>0)
{
editButton.setEnabled(true);
deleteButton.setEnabled(true);
exportToPDFButton.setEnabled(true);
}
else
{
editButton.setEnabled(false);
deleteButton.setEnabled(false);
exportToPDFButton.setEnabled(false);
}
}

private void setAddMode()
{
clearForm();
codeLabel.setVisible(false);
codeTextField.setVisible(true);
clearCodeButton.setVisible(true);
nameLabel.setVisible(false);
nameTextField.setVisible(true);
clearNameButton.setVisible(true);
categoryLabel.setVisible(false);
categoryTextField.setVisible(true);
clearCategoryButton.setVisible(true);
priceLabel.setVisible(false);
priceTextField.setVisible(true);
clearPriceButton.setVisible(true);
addButton.setIcon(saveIcon);
cancelButton.setIcon(cancelIcon);
cancelButton.setEnabled(true);
editButton.setEnabled(false);
deleteButton.setEnabled(false);
exportToPDFButton.setEnabled(false);
}

private void setEditMode()
{
codeLabel.setVisible(false);
codeTextField.setVisible(true);
clearCodeButton.setVisible(true);
nameLabel.setVisible(false);
nameTextField.setVisible(true);
clearNameButton.setVisible(true);
categoryLabel.setVisible(false);
categoryTextField.setVisible(true);
clearCategoryButton.setVisible(true);
priceLabel.setVisible(false);
priceTextField.setVisible(true);
clearPriceButton.setVisible(true);
editButton.setIcon(saveIcon);
cancelButton.setIcon(cancelIcon);
addButton.setEnabled(false);
cancelButton.setEnabled(true);
deleteButton.setEnabled(false);
exportToPDFButton.setEnabled(false);
}

public void setItem(ItemInterface item)
{ 
this.item=item;
if(this.item==null)
{
codeLabel.setText("");
nameLabel.setText("");
categoryLabel.setText("");
priceLabel.setText("");
} 
else
{ 
codeLabel.setText(String.valueOf(this.item.getCode()));
nameLabel.setText(this.item.getName());
categoryLabel.setText(this.item.getCategory());
priceLabel.setText(String.valueOf(this.item.getPrice()));
}
}

private void clearForm()
{
codeTextField.setText("");
nameTextField.setText("");
categoryTextField.setText("");
priceTextField.setText("");
}
}
}