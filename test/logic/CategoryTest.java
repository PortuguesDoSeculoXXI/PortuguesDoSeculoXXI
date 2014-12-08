package logic;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Category Unit Test.
 * This class test the Category.
 * 
 * @author PTXXI
 */
public class CategoryTest {
    
    private Category instance;
    
    public CategoryTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        instance = new Category(1, "Category");
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of getId method, of class Category.
     */
    @Test
    public void testGetId() {
        System.out.println("getId");
        Category instance = new Category(1, "Category");
        int expResult = 1;
        int result = instance.getId();
        assertEquals(instance.getId(), expResult, result);
    }

    /**
     * Test of getName method, of class Category.
     */
    @Test
    public void testGetName() {
        System.out.println("getName");
        Category instance = new Category(1, "Category");
        String expResult = "Category";
        String result = instance.getName();
        assertEquals(expResult, result);
    }
    
    /**
     * Test of toString method, of class Category.
     */
    @Test
    public void testToString() {
        System.out.println("toString");
        String expResult = "Id: 1 Name: Category";
        String result = "Id: " + instance.getId() + " Name: " + instance.getName();
        assertEquals(expResult, result);
    }
    
}
