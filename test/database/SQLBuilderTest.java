package database;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * SQL Query Builder Unit Test.
 * This class test the SQLBuilder.
 * 
 * @author PTXXI
 */
public class SQLBuilderTest {
    
    public SQLBuilderTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of addTable method, of class SQLBuilder.
     */
    @Test
    public void testAddTable() {
        System.out.println("addTable");
        String tableName = "test";
        SQLBuilder instance = new SQLBuilder();
        String expResult = "select * from test";
        SQLBuilder result = instance.addTable(tableName);
        assertTrue("Result: "+result.getSelectQuery(), expResult.equalsIgnoreCase(result.getSelectQuery()));
        // Tested
    }

    /**
     * Test of selectField method, of class SQLBuilder.
     */
    @Test
    public void testSelectField_String_String() {
        System.out.println("selectField");
        String fieldName = "id_test";
        String aliasName = "ident.";
        SQLBuilder instance = new SQLBuilder();
        instance.addTable("Test");
        String expResult = "select id_test as 'ident.' from test";
        SQLBuilder result = instance.selectField(fieldName, aliasName);
        assertTrue("Result: "+result.getSelectQuery(), expResult.equalsIgnoreCase(result.getSelectQuery()));
        // Tested
    }

    /**
     * Test of selectField method, of class SQLBuilder.
     */
    @Test
    public void testSelectField_String() {
        System.out.println("selectField");
        SQLBuilder instance = new SQLBuilder();
        instance.addTable("Test");
        String expResult = "select id, name from test";
        SQLBuilder result = instance.selectField("id").selectField("name");
        assertTrue("Result: "+result.getSelectQuery(), expResult.equalsIgnoreCase(result.getSelectQuery()));
        // Tested
    }

    /**
     * Test of whereField method, of class SQLBuilder.
     */
    @Test
    public void testWhereField_3args() {
        System.out.println("whereField");
        String fieldName = "id";
        String val = "3";
        String op = ">=";
        SQLBuilder instance = new SQLBuilder();
        instance.addTable("Test");
        String expResult = "select * from test where id >= \"3\"";
        SQLBuilder result = instance.whereField(fieldName, val, op);
        assertTrue("Result: "+result.getSelectQuery(), expResult.equalsIgnoreCase(result.getSelectQuery()));
        // Tested
    }

    /**
     * Test of whereField method, of class SQLBuilder.
     */
    @Test
    public void testWhereField_4args() {
        System.out.println("whereField");
        SQLBuilder instance = new SQLBuilder();
        instance.addTable("Test");
        String expResult = "select * from test where id >= \"4\" and name = \"xpto\"";
        SQLBuilder result = instance.whereField("id", "4", ">=", "and").whereField("name", "xpto");
        assertTrue("Result: "+result.getSelectQuery(), expResult.equalsIgnoreCase(result.getSelectQuery()));
        // Tested
    }

    /**
     * Test of whereField method, of class SQLBuilder.
     */
    @Test
    public void testWhereField_String_String() {
        System.out.println("whereField");
        String fieldName = "id";
        String val = "4";
        SQLBuilder instance = new SQLBuilder();
        instance.addTable("Test");
        String expResult = "select * from test where id = \"4\"";
        SQLBuilder result = instance.whereField(fieldName, val);
        assertTrue("Result: "+result.getSelectQuery(), expResult.equalsIgnoreCase(result.getSelectQuery()));
        // Tested
    }

    /**
     * Test of whereSubquery method, of class SQLBuilder.
     */
    @Test
    public void testWhereSubquery() {
        System.out.println("whereSubquery");
        String fieldName = "id";
        String subQuery = new SQLBuilder().addTable("test").selectField("id").whereField("name", "xpto", ">=").getSelectQuery();
        SQLBuilder instance = new SQLBuilder();
        instance.addTable("Test");
        String expResult = "select * from test where id = (select id from test where name >= \"xpto\")";
        SQLBuilder result = instance.whereSubquery(fieldName, subQuery);
        assertTrue("Result: "+result.getSelectQuery(), expResult.equalsIgnoreCase(result.getSelectQuery()));
        // Tested
    }

    /**
     * Test of getInsertQuery method, of class SQLBuilder.
     */
    @Test
    public void testGetInsertQuery() {
        System.out.println("getInsertQuery");
        SQLBuilder instance = new SQLBuilder();
        instance.addTable("Test").addField("name", "Xpto", SQLBuilder.DataType.asText);
        String expResult = "insert into test (name) values ('xpto')";
        String result = instance.getInsertQuery();
        assertTrue("Result: "+result, expResult.equalsIgnoreCase(result));
        // Tested
    }

    /**
     * Test of getSelectQuery method, of class SQLBuilder.
     */
    @Test
    public void testGetSelectQuery() {
        System.out.println("getSelectQuery");
        SQLBuilder instance = new SQLBuilder();
        instance.addTable("Test").selectField("name","Name of Test").whereField("name", "lorem ipsum");
        String expResult = "select name as 'Name of Test' from test where name = \"lorem ipsum\"";
        String result = instance.getSelectQuery();
        assertTrue("Result: "+result, result.equalsIgnoreCase(expResult));
        // Tested
    }

    /**
     * Test of getDeleteQuery method, of class SQLBuilder.
     */
    @Test
    public void testGetDeleteQuery() {
        System.out.println("getDeleteQuery");
        SQLBuilder instance = new SQLBuilder();
        instance.addTable("Test").whereField("name", "Xpto", ">=");
        String expResult = "delete from Test where name >= 'Xpto'";
        String result = instance.getDeleteQuery();
        assertTrue("Result: "+result, result.equalsIgnoreCase(expResult));
        // Tested
    }

    /**
     * Test of clear method, of class SQLBuilder.
     */
    @Test
    public void testClear() {
        System.out.println("clear");
        SQLBuilder instance = new SQLBuilder();
        instance.addTable("test").selectField("name").whereField("name", "lorem ipsum");
        instance.clear();
        assertTrue(instance.getSelectQuery() + " was the result and need to be empty.", 
                instance.getSelectQuery().equals(""));
        // Tested
    }    
}
