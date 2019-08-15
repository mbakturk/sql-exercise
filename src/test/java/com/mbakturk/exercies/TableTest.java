package com.mbakturk.exercies;

import com.mbakturk.exercise.Table;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class TableTest {


    @Test
    public void tableShouldAddColumnNamesCorrectly() throws Exception {
        Table users = new Table("users");
        Table purchases = new Table("purchases");

        List<String[]> data;

        data = users.orderByDesc("id");
        Assert.assertArrayEquals(data.get(0), new String[]{"id", "name"});

        data = purchases.innerJoin(users, "user_id", "id")
                .orderByDesc("users.name");

        Assert.assertArrayEquals(data.get(0), new String[]{
                "purchases.id", "purchases.product", "purchases.user_id", "users.id", "users.name"});
    }

    @Test
    public void tableShouldOrderedCorrectly() throws Exception {
        Table users = new Table("users");

        List<String[]> data;

        data = users.orderByDesc("id");
        Assert.assertEquals(data.get(1)[0], "4");
        Assert.assertEquals(data.get(data.size() - 1)[0], "1");

        data = users.orderByDesc("name");

        Assert.assertEquals(data.get(1)[0], "3");
        Assert.assertEquals(data.get(data.size() - 1)[0], "2");
    }

    @Test
    public void tableShouldJoinedCorrectly() throws Exception {
        Table users = new Table("users");
        Table purchases = new Table("purchases");

        List<String[]> data;

        data = purchases.innerJoin(users, "user_id", "id")
                .orderByDesc("users.name");

        Assert.assertEquals(data.get(1)[1], "Swimmies");
        Assert.assertEquals(data.get(1)[4], "Jon Snow");
    }

    @Test(expected = Exception.class)
    public void tableShouldThrowExceptionIfColumnNameWrong() throws Exception {
        Table users = new Table("users");
        Table purchases = new Table("purchases");

        List<String[]> data;

        data = purchases.innerJoin(users, "user_id", "id")
                .orderByDesc("name");
    }

    @Test(expected = Exception.class)
    public void tableShouldThrowExceptionIfWrongInnerJoin() throws Exception {
        Table users = new Table("users");
        Table purchases = new Table("purchases");

        List<String[]> data;

        // user_id_x is wrong column name
        data = purchases.innerJoin(users, "user_id_x", "id")
                .orderByDesc("name");
    }

}
