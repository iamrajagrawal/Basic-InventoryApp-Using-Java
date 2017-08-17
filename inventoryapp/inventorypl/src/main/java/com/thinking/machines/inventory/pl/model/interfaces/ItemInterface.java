package com.thinking.machines.inventory.pl.model.interfaces;
import java.io.*;
public interface ItemInterface extends Serializable,Comparable<ItemInterface>
{
public void setCode(int code);
public int getCode();
public void setName(String name);
public String getName();
public void setCategory(String category);
public String getCategory();
public void setPrice(int price);
public int getPrice();
}