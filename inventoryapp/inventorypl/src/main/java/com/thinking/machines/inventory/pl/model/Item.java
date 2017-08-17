package com.thinking.machines.inventory.pl.model;
import com.thinking.machines.inventory.pl.model.interfaces.*;
public class Item implements ItemInterface
{
private int code;
private String name;
private String category;
private int price;
public void setCode(int code)
{ 
this.code=code;
}
public int getCode()
{
return this.code;
}
public void setName(String name)
{ 
this.name=name;
}
public String getName()
{
return this.name;
}
public void setCategory(String category)
{ 
this.category=category;
}
public String getCategory()
{
return this.category;
}
public void setPrice(int price)
{ 
this.price=price;
}
public int getPrice()
{
return this.price;
}
public boolean equals(Object object)
{ 
if(!(object instanceof ItemInterface)) return false;
ItemInterface itemInterface;
itemInterface=(ItemInterface)object;
return this.code==itemInterface.getCode();
}
public int compareTo(ItemInterface itemInterface)
{
return this.name.compareToIgnoreCase(itemInterface.getName());
}
}