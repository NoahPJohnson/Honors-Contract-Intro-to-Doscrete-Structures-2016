/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BplusTree;

/**
 *
 * @author Noah
 */

import java.util.ArrayList;
import java.util.Collections;

public class Node
{
 private int n = 3;
 private int firstKey;
 private ArrayList<Integer> keyList;
 private ArrayList<Integer> sortList;
 private ArrayList<Node> children;
 private ArrayList<Node> oldChildren;
 private Node leftSibling;
 private Node rightSibling;
 private Node parent;
 private Node splitOne;
 private Node splitTwo;
 
 private Node target;
 private Node target2;
 private Node newRoot;
 
 private int childCount;
 private boolean root = false;
 private boolean covered = false;

 public Node(boolean r)
 {
  keyList = new ArrayList();
  sortList = new ArrayList();
  children = new ArrayList();
  oldChildren = new ArrayList();  
  if (keyList.size() > 0)
     {
      firstKey = keyList.get(0);
     }
  childCount = 0;
  parent = null;
  leftSibling = null;
  rightSibling = null;
  root = r; 
 }        
 
 public int getN()
 {
    return n;
 } 
    
 public void setParent(Node p)
 {
    parent = p;
 }
 
 public Node getParent()
 {
    return parent; 
 }        
 
 public void setIfRoot(boolean r)
 {
     root = r;
 }        
 
 public boolean checkIfRoot()
 {
    return root;
 }     

 public void setIfCovered(boolean c)
 {
     covered = c;
 }        
 
 public boolean checkIfCovered()
 {
    return covered;
 } 
 
 public void setNewRoot(Node t)
 {
     newRoot = t;
 }
  
 public Node getNewRoot()
 {
     return newRoot;
 }        
 
 public ArrayList getKeyList()
 {
    return keyList;   
 }
 
 public int getKey(int i)
 {
    return keyList.get(i);
 }        
 
 public ArrayList<Node> getChildren()
 {
    return children;   
 }        
 
 public int getChildCount()
 {
     return childCount;
 }  
 
 public Node getChild(int i)
 {
     return children.get(i);
 }        
         
 public void setKey(int e)
 {
    if (!keyList.contains(e))
       { 
        keyList.add(e);
       } 
    Collections.sort(keyList);
    if (keyList.size() >= n)
       {
        split();   
       } 
 }   
 
 public void remKey(int e)
 {
    keyList.remove(keyList.indexOf(e));
    target = this;
    target2 = target;
    target = target.parent;
    while (target != null)
            {
             //System.out.print("removeTarget: ");
             //System.out.println(target.toString());
             if (target.keyList.contains(e))
                {
                 System.out.print("Ancestor to remove from: ");
                 System.out.println(target.toString());
                 target.keyList.remove(target.keyList.indexOf(e));
                 target.keyList.add(0, target2.getFirstKey());
                 Collections.sort(keyList);
                } 
             target = target.parent;
            }
    if (keyList.size() < n/2)
       { 
        if (leftSibling != null && leftSibling.getKeyList().size() > n/2)
           {
            leftRedistribute();  
           } 
        else if (rightSibling != null && rightSibling.getKeyList().size() > n/2)
           {
            rightRedistribute();    
           }
        else if (leftSibling != null && leftSibling.getKeyList().size() <= n/2)
           {
            merge(leftSibling);   
           } 
        else if (rightSibling != null && rightSibling.getKeyList().size() <= n/2)
           {
            merge(rightSibling);
           }
       } 
 }        
 
 public void addChild()
 {
    childCount ++;
    children.add(new Node(false));
 }          
 
 public void addSpecificChild(Node c)
 {
     childCount ++;
     children.add(c);
 }        
 
 public int getFirstKey()
 {
     setFirstKey();
     return firstKey;   
 }
 
 public void setFirstKey()
 {
  if (keyList.size() != 0)
     {
      firstKey = keyList.get(0);
     } 
 }    
 
 public void setLeftSibling(Node s)
 {
     leftSibling  = s;
 } 
 
 public Node getLeftSibling()
 {
     return leftSibling;    
 }   
 
 public void setRightSibling(Node s)
 {
     rightSibling = s;
 }        
 
 public Node getRightSibling()
 {
     return rightSibling;    
 }
 
 public boolean itemSearch(int key)
 {
    if (keyList.contains(key))
       {
        return true;
       }
    else
       {
        return false; 
       } 
 }       
         
 public void split()
 {
     int mid = n/2;
     int midKey = keyList.get((n/2));
     int index = 0; 
     if (checkIfRoot() == true)
        { 
         if (children.size() > 0)
            { 
             System.out.println("Root Secondary Split");
             for (int i = 0; i < children.size(); i ++)
                 {
                  oldChildren.add(children.get(i));   
                 } 
             while (children.size() != 0)
                 { 
                  children.remove(0);
                 }
             System.out.print("OldChildren: ");
             System.out.println(oldChildren.toString());
             //System.out.print("ChildrenEmpty: ");
             //System.out.println(children.toString());

             splitOne = new Node(false);
             for (int i = 0; i < mid; i ++)
                 {
                  splitOne.setKey(keyList.get(i));
                  if (oldChildren.size() > 0)
                     { 
                      for (int q = 0; q < oldChildren.size()/2; q ++)
                          {
                           splitOne.children.add(oldChildren.get(q));
                          }
                     }     
                 }
             System.out.print("Children1: ");
             System.out.println(splitOne.children.toString());

             splitTwo = new Node(false);
             for (int i = mid+1; i < n; i ++)
                 {
                  splitTwo.setKey(keyList.get(i));
                  if (oldChildren.size() > 0)
                     { 
                      for (int q = oldChildren.size()/2; q < oldChildren.size(); q ++)
                          {
                           splitTwo.children.add(oldChildren.get(q));
                          }
                     }      
                 }
             //children.get(children.size()-2).addSpecificChild(children.get(childCount-1));
             System.out.print("Children2: ");
             System.out.println(splitTwo.children.toString());
             
             if (splitOne.getFirstKey() > splitTwo.getFirstKey())
                { 
                 addSpecificChild(splitTwo);
                 addSpecificChild(splitOne);
                } 
             else
                {
                 addSpecificChild(splitOne);
                 addSpecificChild(splitTwo);   
                } 
             while (oldChildren.size() != 0)
                 { 
                  oldChildren.remove(0);
                 }
             while (keyList.size() != 1)
                {
                 if (keyList.get(index) != midKey)
                    {
                     //System.out.println("removed");
                     keyList.remove(index);
                    }
                 index ++;
                }
            }
        else 
            {
             System.out.println("Root Split");
             splitOne = new Node(false);
             for (int i = 0; i < mid; i ++)
                 {
                  splitOne.setKey(keyList.get(i));
                 }
             
             splitTwo = new Node(false);
             for (int i = mid; i < n; i ++)
                 {
                  splitTwo.setKey(keyList.get(i));
                 }
             //splitOne.setRightSibling(splitTwo);
             //splitTwo.setleftSibling(splitOne);
             if (splitOne.getFirstKey() > splitTwo.getFirstKey())
                { 
                 addSpecificChild(splitTwo);
                 addSpecificChild(splitOne);
                } 
             else
                {
                 addSpecificChild(splitOne);
                 addSpecificChild(splitTwo);   
                } 
             while (keyList.size() != 1)
                {
                 if (keyList.get(index) != midKey)
                    {
                     //System.out.println("removed");
                     keyList.remove(index);
                    }
                 index ++;
                }
            }
        }
    else
        {
         if (children.size() > 0)
            {
             System.out.println("Parent Secondary Split");
             for (int i = 0; i < children.size(); i ++)
                 {
                  oldChildren.add(children.get(i));   
                 } 
             
             while (children.size() != 0)
                 { 
                  children.remove(0);
                 }
             System.out.print("OldChildren: ");
             System.out.println(oldChildren.toString());
             //System.out.print("ChildrenEmpty: ");
             //System.out.println(children.toString());

             splitOne = new Node(false);
             for (int i = mid+1; i < n; i ++)
                 {
                  splitOne.setKey(keyList.get(i));
                  if (oldChildren.size() > 0)
                     { 
                      for (int q = oldChildren.size()/2; q < oldChildren.size(); q ++)
                          {
                           splitOne.children.add(oldChildren.get(q));
                          }
                     }      
                 }
             //children.get(children.size()-2).addSpecificChild(children.get(childCount-1));
             System.out.print("Children2: ");
             System.out.println(splitOne.children.toString());
             
             while(keyList.size() > mid)
                 {
                  //System.out.println("splitcheck");
                  //System.out.println(keyList.get(keyList.size()-1));
                  if (keyList.get(keyList.size()-1) >= midKey)
                     {     
                      keyList.remove(keyList.size()-1);
                     } 
                 }
             if (oldChildren.size() > 0)
                { 
                 for (int q = 0; q < oldChildren.size()/2; q ++)
                     {
                      this.children.add(oldChildren.get(q));
                     }   
                 }
             System.out.print("Children1: ");
             System.out.println(this.children.toString());
             
             splitOne.setParent(this.parent);
             
             for (int i = 0; i < parent.getChildren().size(); i ++)
                 {
                  if (i == parent.getChildren().size())
                     {
                      parent.getChildren().add(i, splitOne); 
                      break;
                     } 
                  else if (splitOne.getFirstKey() < parent.getChildren().get(i).getFirstKey())   
                     {
                      parent.getChildren().add(i, splitOne);
                      break;
                     }
                  System.out.print("inOrder: ");
                  System.out.println(parent.getChildren().get(i).toString());
                 }
             while (oldChildren.size() != 0)
                 { 
                  oldChildren.remove(0);
                 }
             while (keyList.size() != 1)
                {
                 if (keyList.get(index) != midKey)
                    {
                     //System.out.println("removed");
                     keyList.remove(index);
                    }
                 index ++;
                }   
             parent.setKey(midKey);
            }     
         else
            { 
             System.out.println("Not Parent Split");
             System.out.print("This node: ");
             System.out.println(this.toString());
             //System.out.println(parent.toString());
             splitOne = new Node(false);
             //rightSibling = splitOne;
             for (int i = 0; i < parent.children.size(); i ++)
                 {
                  oldChildren.add(parent.children.get(i));   
                 }
             oldChildren.add(splitOne);
             System.out.print("oldChildren: ");
             System.out.println(oldChildren.toString());
             while (parent.children.size() != 0)
                   { 
                    parent.children.remove(0);
                   }
             //System.out.print("ParentKids: ");
             //System.out.println(parent.children.toString());
             //rightSibling.setSibling(this);
             for (int i = mid; i < n; i ++)
                 {
                  //System.out.println(parent.children.toString());
                  splitOne.setKey(keyList.get(i));
                 }
             System.out.print("splitOne: ");
             System.out.println(splitOne.toString());
             while(keyList.size() > mid)
                 {
                  //System.out.println("splitcheck");
                  //System.out.println(keyList.get(keyList.size()-1));
                  if (keyList.get(keyList.size()-1) >= midKey)
                     {     
                      keyList.remove(keyList.size()-1);
                     } 
                 }
             System.out.print("keyList: ");
             System.out.println(keyList.toString());
             //System.out.println("splitTestThing1");
             //System.out.println(oldChildren.size());
             int ind = oldChildren.size();
             for (int i = 0; i < ind; i ++)
                 {
                  sortList.add(oldChildren.get(i).getFirstKey());                 
                  //System.out.println(oldChildren.get(i).setFirstKey());
                  //System.out.println(i);
                 }
             System.out.print("sortList: ");
             System.out.println(sortList.toString());
             Collections.sort(sortList);
             //System.out.println("splitTestThing2");
             //System.out.println(oldChildren.size());
             //System.out.println(sortList.size()); 
             for (int i = 0; i < ind; i ++)
                 {
                  //System.out.print("i");
                  //System.out.println(i);
                  for (int q = 0; q < ind; q ++)
                      {
                       //System.out.print("q");
                       //System.out.println(q);
                       if (oldChildren.get(q).getFirstKey() == sortList.get(i))
                          {
                           System.out.print("addThis: ");
                           System.out.println(oldChildren.get(q).toString());
                           parent.children.add(oldChildren.get(q));
                          } 
                      }
                 }
             System.out.print("NotParentSplit, Children: ");
             System.out.println(parent.children.toString());
             while (oldChildren.size() != 0)
                   { 
                    oldChildren.remove(0);
                   }
             while (sortList.size() != 0)
                   { 
                    sortList.remove(0);
                   }
             parent.setKey(midKey);
            }
        } 
 }
 
 public void rightRedistribute()
 {
     keyList.add(rightSibling.getFirstKey());
     rightSibling.keyList.remove(0);
     while (parent.keyList.size() != 0)
         {
          parent.keyList.remove(0);
         }
     for (int i = 1; i < parent.getChildren().size(); i ++)
         {
          parent.keyList.add(parent.getChild(i).getFirstKey());
         }
 }
 
 public void leftRedistribute()
 {
     keyList.add(leftSibling.keyList.get(leftSibling.keyList.size()-1));
     leftSibling.keyList.remove(leftSibling.keyList.size()-1);
     while (parent.keyList.size() != 0)
         {
          parent.keyList.remove(0);
         }
     for (int i = 1; i < parent.getChildren().size(); i ++)
         {
          parent.keyList.add(parent.getChild(i).getFirstKey());
         }
 }
 
 public void merge(Node t)
 {
     int sepKey; 
     int thisIndex;
     int thatIndex;
     thisIndex = parent.getChildren().indexOf(this);
     thatIndex = parent.getChildren().indexOf(t);
     if (thisIndex > thatIndex)
        {
         sepKey = this.getFirstKey();
        }
     else
        {
         sepKey = t.getFirstKey();
        } 
      
     //Remove seperating key from parent (call parentRemKey)
     if (parent.getKeyList().contains(sepKey))
        { 
         parent.getKeyList().remove(parent.getKeyList().indexOf(sepKey));
        }
     
     //Move all keys to the sibling leaf, t for merging
     t.getKeyList().addAll(keyList);
     Collections.sort(t.getKeyList());
     while (keyList.size() != 0)
         {
          keyList.remove(0);
         }
     
     //Delete child pointer at parent node pointing to this
     parent.getChildren().remove(thisIndex);
     target = parent;
     rebalance(target);
 }         
 
 public void rebalance(Node t)
 {
     target = t;
     int thisIndex; 

     while (target != null)
         { 
          System.out.print("Rebalance Target: ");
          System.out.println(target.toString());
          if (target.checkIfRoot() == true)
             {
              if (target.getChildren().size() < 2)
                 {
                  System.out.print("targetRoot: ");
                  System.out.println(target.toString());

                  target2 = target.getChild(0);
                  target2.getKeyList().addAll(target.getKeyList());
                  Collections.sort(target2.getKeyList());
                  
                  target.setIfRoot(false);
                  target2.setIfRoot(true);
                  
                  while (target.getKeyList().size() != 0)
                        {
                         target.getKeyList().remove(0);
                        }
                  while (target.getChildren().size() != 0)
                        {
                         target.getChildren().remove(0);
                        }
                  setNewRoot(target2);
                 }   
             }
          else
             { 
              thisIndex = target.getParent().getChildren().indexOf(target);
              if (target.getChildren().size() <= n/2)
                 {
                  System.out.print("thisIndex: ");
                  System.out.println(thisIndex);
                  if (thisIndex > 0 && thisIndex != n)
                     { 
                      target2 = target.getParent().getChildren().get(thisIndex-1);
                      target2.getChildren().addAll(target.getChildren());
                     }
                  else if (thisIndex == 0)
                     {
                      target2 = target.getParent().getChildren().get(thisIndex+1);  
                      target2.getChildren().addAll(0, target.getChildren());
                     }
                  target2.getKeyList().addAll(target.getKeyList());
                  Collections.sort(target2.getKeyList());

                  while (target.getKeyList().size() != 0)
                        {
                         target.getKeyList().remove(0);
                        }              
                  while (target.getChildren().size() != 0)
                        {
                         target.getChildren().remove(0);
                        }
                  System.out.print("thisIndex2: ");
                  System.out.println(thisIndex);
                  target.getParent().getChildren().remove(thisIndex);
                 } 
             } 
          target = target.getParent();
          target2 = null;
        } 
 }        
 
 public String toString()
 {
     String result = "";
     for (int i = 0; i < keyList.size(); i ++)
         {
          result += keyList.get(i);
          result += ",";
         }
     return result;
 }       
}


