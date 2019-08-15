package com.mbakturk.exercise;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.stream.Collectors;

public class Table {


    private String tableName;
    private List<String[]> data;
    private Map<String, Integer> columns;


    public Table(String tableName) {
        this.tableName = tableName;
        this.parseCVS(tableName);
    }

    private Table(Map<String, Integer> columns, List<String[]> data) {
        this.columns = columns;
        this.data = data;
    }

    public List<String[]> orderByDesc(String columnName) throws Exception {
        Integer columnId = this.columns.get(columnName);

        if (columnId == null) {
            throw new Exception("The column name is not exist");
        }

        List<String[]> copyOfData = this.data.stream().collect(Collectors.toList());
        Collections.sort(copyOfData, new OrderByDescSort(columnId));
        copyOfData.add(0, this.getOrderedColumnNames(this.columns));

        return copyOfData;
    }

    public Table innerJoin(Table table, String leftTableColumnName, String rightTableColumnName) throws Exception {
        List<String[]> newData = new ArrayList<>();
        Integer leftTableColumnId = this.columns.get(leftTableColumnName);
        Integer rightTableColumnId = this.columns.get(rightTableColumnName);

        if (leftTableColumnId == null || rightTableColumnId == null) {
            throw new Exception("One of the column name  is not exist");
        }

        for (String[] leftTableRow : this.data) {
            List<String[]> rightTableRow = table.data.stream()
                    .filter(row -> row[rightTableColumnId].equals(leftTableRow[leftTableColumnId]))
                    .collect(Collectors.toList());
            if (!rightTableRow.isEmpty()) {
                for (String[] joinedRow : rightTableRow) {
                    newData.add(this.concatData(leftTableRow, joinedRow));
                }
            }
        }

        return new Table(this.getJoinedTablesColumnNames(this, table), newData);
    }

    private String[] getOrderedColumnNames(Map<String, Integer> columns, String prefix) {
        String[] columnNames = new String[columns.keySet().size()];

        for (Map.Entry<String, Integer> entry : columns.entrySet()) {
            columnNames[entry.getValue()] = prefix + entry.getKey();
        }

        return columnNames;
    }

    private String[] getOrderedColumnNames(Map<String, Integer> columns) {
        return this.getOrderedColumnNames(columns, "");
    }

    private void parseCVS(String fileName) {

        this.data = new ArrayList<>();
        this.columns = new HashMap<>();

        String line = "";
        String cvsSplitBy = ",";
        int count = 0;


        try (BufferedReader br = this.getFileBufferedReader(fileName)) {
            while ((line = br.readLine()) != null) {
                String[] parsedLine = line.split(cvsSplitBy);
                // The first line will contain column names
                if (count == 0) {
                    for (int i = 0, l = parsedLine.length; i < l; i++) {
                        this.columns.put(parsedLine[i], i);
                    }
                } else {
                    this.data.add(parsedLine);
                }
                count++;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private Map<String, Integer> getJoinedTablesColumnNames(Table leftTable, Table rightTable) {
        String[] columnNames = this.concatData(this.getOrderedColumnNames(leftTable.columns, leftTable.tableName + "." ),
                this.getOrderedColumnNames(rightTable.columns, rightTable.tableName + "." ));

        Map<String, Integer> columns = new HashMap<>();

        for (int i = 0, l = columnNames.length; i < l; i++) {
            columns.put(columnNames[i], i);
        }

        return columns;
    }

    private String[] concatData(String[] a, String[] b) {
        int aLen = a.length;
        int bLen = b.length;

        String[] c = new String[aLen + bLen];
        System.arraycopy(a, 0, c, 0, aLen);
        System.arraycopy(b, 0, c, aLen, bLen);

        return c;
    }

    private BufferedReader getFileBufferedReader(String fileName) {
        return new BufferedReader(new InputStreamReader(getClass()
                .getClassLoader().getResourceAsStream(fileName + ".csv")));
    }

}
