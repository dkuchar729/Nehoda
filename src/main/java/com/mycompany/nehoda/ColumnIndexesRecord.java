package com.mycompany.nehoda;

import com.mycompany.nehoda.ColumnsIndexing.AvailableColumns;

import java.util.HashMap;
import java.util.Map;

public class ColumnIndexesRecord {



    public static Map<AvailableColumns,Integer> columnIndexes=new HashMap<>();

    public static void reset()
    {
        columnIndexes=new HashMap<>();
    }
    public static void addIndex(AvailableColumns av,int index)
    {
        columnIndexes.put(av,index);
    }

    public static int getIndex(AvailableColumns av)
    {
      return columnIndexes.getOrDefault(av,-1);
    }

    public static AvailableColumns getKey(int index)
    {
        for(AvailableColumns av:columnIndexes.keySet())
        {
           if(columnIndexes.get(av)==index)
           {
               return av;
           }
        }
        return null;
    }


}
