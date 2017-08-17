package com.thinking.machines.inventory.pl.model.interfaces;
import com.thinking.machines.inventory.pl.model.exceptions.*;
import java.util.*;
public interface ItemModelInterface
{
public void add(ItemInterface itemInterface) throws ModelException;
public void update(ItemInterface itemInterface) throws ModelException;
public void delete(int code) throws ModelException;
public ArrayList<ItemInterface> getItems() throws ModelException;
public int getCount() throws ModelException;
public int searchItem(String name,boolean performLeftPartialSearch,boolean performCaseSensitiveSearch) throws ModelException;
public int getIndexOf(ItemInterface itemInterface) throws ModelException;
public ItemInterface getItemAt(int index) throws ModelException;
}