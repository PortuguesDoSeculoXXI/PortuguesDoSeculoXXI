package logic;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Question Unit Test.
 * This class test the Question.
 * 
 * @author PTXXI
 */
public class QuestionTest {
    
    private Question instance;
    
    public QuestionTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        Answer answer = Answer.OPTION_B;
        instance = new Question(answer, "Como se escreve o quarto mês do ano civil?", "Abril", "abril", 0);
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of getAnswer method, of class Question.
     */
    @Test
    public void testGetAnswer() {
        System.out.println("getAnswer");
        Answer expResult = Answer.OPTION_B; //2
        Answer result = instance.getAnswer();
        assertEquals(expResult, result);
    }

    /**
     * Test of getQuestion method, of class Question.
     */
    @Test
    public void testGetQuestion() {
        System.out.println("getQuestion");
        String expResult = "Como se escreve o quarto mês do ano civil?";
        String result = instance.getQuestion();
        assertEquals(expResult, result);
    }

    /**
     * Test of getOptionA method, of class Question.
     */
    @Test
    public void testGetOptionA() {
        System.out.println("getOptionA");
        String expResult = "Abril";
        String result = instance.getOptionA();
        assertEquals(expResult, result);
    }

    /**
     * Test of getOptionB method, of class Question.
     */
    @Test
    public void testGetOptionB() {
        System.out.println("getOptionB");
        String expResult = "abril";
        String result = instance.getOptionB();
        assertEquals(expResult, result);
    }
    
}
