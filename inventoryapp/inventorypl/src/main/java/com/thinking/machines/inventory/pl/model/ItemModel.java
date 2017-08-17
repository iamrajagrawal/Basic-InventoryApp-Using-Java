package com.thinking.machines.inventory.pl.model;
import com.thinking.machines.inventory.pl.model.interfaces.*;
import com.thinking.machines.inventory.pl.model.exceptions.*;
import com.thinking.machines.inventory.dl.exceptions.*;
import com.thinking.machines.inventory.dl.interfaces.*;
import com.thinking.machines.inventory.dl.dao.*;
import com.thinking.machines.inventory.dl.dto.*;
import java.util.*;
import javax.swing.table.*;
public class ItemModel extends AbstractTableModel implements ItemModelInterface
{
private ArrayList<ItemInterface> items;
private String title[];
public ItemModel()
{ 
loadDataStructure();
}
private void loadDataStructure()
{ 
title=new String[3];
title[0]="S.No.";
title[1]="Code";
title[2]="Item";
ItemDAOInterface itemDAOInterface=new ItemDAO();
List<ItemDTOInterface> items;
try
{ 
items=itemDAOInterface.getAll();
this.items=new ArrayList<ItemInterface>();
ItemDTOInterface itemDTOInterface;
ItemInterface itemInterface;
int x;
x=0;
while(x<items.size())
{ 
itemDTOInterface=items.get(x);
itemInterface=new Item();
itemInterface.setCode(itemDTOInterface.getCode());
itemInterface.setName(itemDTOInterface.getName());
itemInterface.setCategory(itemDTOInterface.getCategory());
itemInterface.setPrice(itemDTOInterface.getPrice());
this.items.add(itemInterface);
x++;
}
}catch(DAOException daoException)
{ 
this.items=new ArrayList<ItemInterface>();
}
}
public int getColumnCount()
{
return this.title.length;
}
public int getRowCount()
{
return this.items.size();
}
public boolean isCellEditable(int rowIndex,int columnIndex)
{
return false;
}
public Class getColumnClass(int columnIndex)
{
Class c=null;
try
{ 
if(columnIndex<2)
{ 
c=Class.forName("java.lang.Integer");
} 
else
{ 
c=Class.forName("java.lang.String");
}
}catch(ClassNotFoundException classNotFoundException)
{
}
return c;
}

public Object getValueAt(int rowIndex,int columnIndex)
{ 
if(columnIndex==0) return new Integer(rowIndex+1);
if(columnIndex==1)
{
return new Integer(this.items.get(rowIndex).getCode());
} 
if(columnIndex==2)
{
return this.items.get(rowIndex).getName();
}
return null;
}

public void add(ItemInterface itemInterface) throws ModelException
{ 
try
{
ItemDAOInterface itemDAOInterface;
itemDAOInterface=new ItemDAO();
ItemDTOInterface itemDTOInterface;
itemDTOInterface=new ItemDTO();
itemDTOInterface.setCode(itemInterface.getCode());
itemDTOInterface.setName(itemInterface.getName());
itemDTOInterface.setCategory(itemInterface.getCategory());
itemDTOInterface.setPrice(itemInterface.getPrice());
itemDAOInterface.add(itemDTOInterface);

int i=0;
while(i<this.items.size())
{
if(items.get(i).compareTo(itemInterface)>0)
{
break;
}
i++;
}
items.add(i,itemInterface);
}catch(DAOException daoException)
{
throw new ModelException(daoException.getMessage());
}
}

public void update(ItemInterface itemInterface) throws ModelException
{ 
try
{
ItemDAOInterface itemDAOInterface;
itemDAOInterface=new ItemDAO();
ItemDTOInterface itemDTOInterface;
itemDTOInterface=new ItemDTO();
itemDTOInterface.setCode(itemInterface.getCode());
itemDTOInterface.setName(itemInterface.getName());
itemDTOInterface.setCategory(itemInterface.getCategory());
itemDTOInterface.setPrice(itemInterface.getPrice());
itemDAOInterface.update(itemDTOInterface);
int i;
i=0;
while(i<this.items.size())
{
if(this.items.get(i).equals(itemInterface))
{
this.items.remove(i);
break;
}
i++;
}
i=0;
while(i<this.items.size())
{
if(this.items.get(i).compareTo(itemInterface)>0)
{
break;
}
i++;
}
this.items.add(i,itemInterface);
}catch(DAOException daoException)
{
throw new ModelException(daoException.getMessage());
}
}

public void delete(int code) throws ModelException
{ 
try
{
ItemDAOInterface itemDAOInterface;
itemDAOInterface=new ItemDAO();
itemDAOInterface.delete(code);
int i;
for(i=0;i<this.items.size();i++)
{
if(this.items.get(i).getCode()==code)
{
this.items.remove(i);
break;
}
}
}catch(DAOException daoException)
{
throw new ModelException(daoException.getMessage());
}
}

public ArrayList<ItemInterface> getItems() throws ModelException
{
if(this.items.size()==0) throw new ModelException("No items");
return this.items;
}

public int getCount() throws ModelException
{ 
return this.items.size();
}

public int searchItem(String name,boolean performLeftPartialSearch,boolean performCaseSensitiveSearch) throws ModelException
{ 
String fromList;
int i=0;
while(i<this.items.size())
{
fromList=this.items.get(i).getName();
if(performLeftPartialSearch)
{
if(performCaseSensitiveSearch)
{
if(fromList.startsWith(name)) return i;
}
else
{
if(fromList.toUpperCase().startsWith(name.toUpperCase())) return i;
}
}
else
{
if(performCaseSensitiveSearch)
{
if(fromList.equals(name)) return i;
}
else
{
if(fromList.equalsIgnoreCase(name)) return i;
}
}
i++;
}
throw new ModelException("Not found : "+name);
}

public int getIndexOf(ItemInterface itemInterface) throws ModelException
{ 
for(int i=0;i<this.items.size();i++)
{
if(this.items.get(i).equals(itemInterface)) return i;
}
return -1;
}

public ItemInterface getItemAt(int index) throws ModelException
{ 
if(index<0 || index>=this.items.size()) throw new ModelException("Invalid index :"+index);
return this.items.get(index);
}
}