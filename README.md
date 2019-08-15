# SQL Exercise

All cases couldn't be covered by the Unit test. Especially Table.getFileBufferedReader method could be mocked by PowerMock. Therefore empty tables and other different scenarios could be tested.

The program uses too much memory so it can be reduced by some refactoring.

Each CSV files have own Table object. To join them, innerJoin method could be used. After joined two tables, the name of the tables are appended to column names as prefix.

When two tables are joined each others, innerJoin method returns new Table object.

PS: More comments could be added to code.