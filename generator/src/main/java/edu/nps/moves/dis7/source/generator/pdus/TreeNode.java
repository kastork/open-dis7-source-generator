/**
 * Copyright (c) 2008-2020, MOVES Institute, Naval Postgraduate School (NPS). All rights reserved.
 * This work is provided under a BSD open-source license, see project license.html and license.txt
 */
package edu.nps.moves.dis7.source.generator.pdus;

import java.util.*;

/**
 *
 * @author DMcG
 */
public class TreeNode 
{
    GeneratedClass aClass = null;
    List<TreeNode> children = new ArrayList<>();

    public TreeNode(GeneratedClass aClass)
    {
        this.aClass = aClass;
    }
    
    public TreeNode findClass(String name)
    {
        if(aClass != null && aClass.getName().equalsIgnoreCase(name))
            return this;
        
        for(int idx = 0; idx < children.size(); idx++)
        {
            TreeNode aNode = children.get(idx);
            if(aNode.findClass(name) != null)
                return aNode;
        }
        
        return null; 
    }
    
    public void addClass(GeneratedClass aClass)
    {
        children.add(new TreeNode(aClass));
    }
    
    public void getList(List<TreeNode> aList)
    {
        aList.addAll(children);
        
        for (TreeNode aNode : children) {
            aNode.getList(aList);
        }
        
    }

}
