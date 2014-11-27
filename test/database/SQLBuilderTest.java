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
    }

    /**
     * Test of selectField method, of class SQLBuilder.
     */
    @Test
    public void testSelectField_String_String() {
        System.out.println("selectField");
        String fieldName = "";
        String aliasName = "";
        SQLBuilder instance = new SQLBuilder();
        String expResult = "";
        SQLBuilder result = instance.selectField(fieldName, aliasName);
        assertTrue("Result: "+result.getSelectQuery(), expResult.equalsIgnoreCase(result.getSelectQuery()));
    }

    /**
     * Test of selectField method, of class SQLBuilder.
     */
    @Test
    public void testSelectField_String() {
        System.out.println("selectField");
        String fieldName = "";
        SQLBuilder instance = new SQLBuilder();
        String expResult = "";
        SQLBuilder result = instance.selectField(fieldName);
        assertTrue("Result: "+result.getSelectQuery(), expResult.equalsIgnoreCase(result.getSelectQuery()));
    }

    /**
     * Test of whereField method, of class SQLBuilder.
     */
    @Test
    public void testWhereField_3args() {
        System.out.println("whereField");
        String fieldName = "";
        String val = "";
        String op = "";
        SQLBuilder instance = new SQLBuilder();
        String expResult = "";
        SQLBuilder result = instance.whereField(fieldName, val, op);
        assertTrue("Result: "+result.getSelectQuery(), expResult.equalsIgnoreCase(result.getSelectQuery()));
    }

    /**
     * Test of whereField method, of class SQLBuilder.
     */
    @Test
    public void testWhereField_4args() {
        System.out.println("whereField");
        String fieldName = "";
        String val = "";
        String op = "";
        String concat = "";
        SQLBuilder instance = new SQLBuilder();
        String expResult = "";
        SQLBuilder result = instance.whereField(fieldName, val, op, concat);
        assertTrue("Result: "+result.getSelectQuery(), expResult.equalsIgnoreCase(result.getSelectQuery()));
    }

    /**
     * Test of whereField method, of class SQLBuilder.
     */
    @Test
    public void testWhereField_String_String() {
        System.out.println("whereField");
        String fieldName = "";
        String val = "";
        SQLBuilder instance = new SQLBuilder();
        String expResult = "";
        SQLBuilder result = instance.whereField(fieldName, val);
        assertTrue("Result: "+result.getSelectQuery(), expResult.equalsIgnoreCase(result.getSelectQuery()));
    }

    /**
     * Test of whereSubquery method, of class SQLBuilder.
     */
    @Test
    public void testWhereSubquery() {
        System.out.println("whereSubquery");
        String fieldName = "";
        String subQuery = "";
        SQLBuilder instance = new SQLBuilder();
        String expResult = "";
        SQLBuilder result = instance.whereSubquery(fieldName, subQuery);
        assertTrue("Result: "+result.getSelectQuery(), expResult.equalsIgnoreCase(result.getSelectQuery()));
    }

    /**
     * Test of joinField method, of class SQLBuilder.
     */
    @Test
    public void testJoinField_3args() {
        System.out.println("joinField");
        String leftFielield = "";
        String rightField = "";
        String op = "";
        SQLBuilder instance = new SQLBuilder();
        String expResult = "";
        SQLBuilder result = instance.joinField(leftFielield, rightField, op);
        assertTrue("Result: "+result.getSelectQuery(), expResult.equalsIgnoreCase(result.getSelectQuery()));
    }

    /**
     * Test of joinField method, of class SQLBuilder.
     */
    @Test
    public void testJoinField_String_String() {
        System.out.println("joinField");
        String leftField = "";
        String rightField = "";
        SQLBuilder instance = new SQLBuilder();
        String expResult = "";
        SQLBuilder result = instance.joinField(leftField, rightField);
        assertTrue("Result: "+result.getSelectQuery(), expResult.equalsIgnoreCase(result.getSelectQuery()));
    }

    /**
     * Test of getInsertQuery method, of class SQLBuilder.
     */
    @Test
    public void testGetInsertQuery() {
        System.out.println("getInsertQuery");
        SQLBuilder instance = new SQLBuilder();
        String expResult = "";
        String result = instance.getInsertQuery();
        assertTrue("Result: "+result, expResult.equalsIgnoreCase(result));
    }

    /**
     * Test of getSelectQuery method, of class SQLBuilder.
     */
    @Test
    public void testGetSelectQuery() {
        System.out.println("getSelectQuery");
        SQLBuilder instance = new SQLBuilder();
        instance.addTable("test").selectField("name").whereField("name", "lorem ipsum");
        String expResult = "select name from test where name = `lorem upsum`";
        String result = instance.getSelectQuery();
        assertTrue("Result: "+result, expResult.equalsIgnoreCase(result));
    }

    /**
     * Test of getDeleteQuery method, of class SQLBuilder.
     */
    @Test
    public void testGetDeleteQuery() {
        System.out.println("getDeleteQuery");
        SQLBuilder instance = new SQLBuilder();
        String expResult = "";
        String result = instance.getDeleteQuery();
        assertTrue("Result: "+result, expResult.equalsIgnoreCase(result));
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
    }

    /**
     * Test of getWhereFieldLeftSeperatorChar method, of class SQLBuilder.
     */
    @Test
    public void testGetWhereFieldLeftSeperatorChar() {
        System.out.println("getWhereFieldLeftSeperatorChar");
        boolean isSubQuery = false;
        SQLBuilder instance = new SQLBuilder();
        String expResult = "";
        String result = instance.getWhereFieldLeftSeperatorChar(isSubQuery);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getWhereFieldRightSeperatorChar method, of class SQLBuilder.
     */
    @Test
    public void testGetWhereFieldRightSeperatorChar() {
        System.out.println("getWhereFieldRightSeperatorChar");
        boolean isSubQuery = false;
        SQLBuilder instance = new SQLBuilder();
        String expResult = "";
        String result = instance.getWhereFieldRightSeperatorChar(isSubQuery);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getInsertValueLeftChar method, of class SQLBuilder.
     */
    @Test
    public void testGetInsertValueLeftChar() {
        System.out.println("getInsertValueLeftChar");
        boolean isWildChar = false;
        SQLBuilder instance = new SQLBuilder();
        String expResult = "";
        String result = instance.getInsertValueLeftChar(isWildChar);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getInsertValueRightChar method, of class SQLBuilder.
     */
    @Test
    public void testGetInsertValueRightChar() {
        System.out.println("getInsertValueRightChar");
        boolean isWildChar = false;
        SQLBuilder instance = new SQLBuilder();
        String expResult = "";
        String result = instance.getInsertValueRightChar(isWildChar);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
