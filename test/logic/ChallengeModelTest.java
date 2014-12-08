package logic;

import database.DataController;
import java.util.ArrayList;
import java.util.List;
import logic.database.Controller;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import states.WaitAnswer;
import states.WaitConfiguration;
import states.WaitScore;

/**
 * ChallengeModel Unit Test.
 * This class test the ChallengeModel.
 * 
 * @author PTXXI
 */
public class ChallengeModelTest {
    
    private ChallengeModel instanceCM;
    private Challenge instanceC;
    private Player player;
    private Controller controller;
    private DataController dc;
    
    public ChallengeModelTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        player = new Player(1, "Carol");
        controller = new Controller(null);
        instanceC = new Challenge(controller, player);
        instanceCM = new ChallengeModel(instanceC);
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of newGame method, of class ChallengeModel.
     */
    @Test
    public void testNewGame() {
        System.out.println("newGame");
        instanceCM.newGame();
        if (!(instanceCM.getChallenge().getCurrentState() instanceof WaitConfiguration))
            fail("Result: "+instanceCM.getChallenge().getCurrentState());
    }

    /**
     * Test of startGame method, of class ChallengeModel.
     */
    @Test
    public void testStartGame() {
        System.out.println("startGame");
        List<Challenge.Categories> categoryList = new ArrayList<>();
        categoryList.add(Challenge.Categories.RANDOM);
        instanceCM.startGame(categoryList);
        if (!(instanceCM.getChallenge().getCurrentState() instanceof WaitAnswer))
            fail("Result: "+instanceCM.getChallenge().getCurrentState());
    }

    /**
     * Test of nextAnswer method, of class ChallengeModel.
     */
    @Test
    public void testNextAnswer() {
        System.out.println("nextAnswer");
        Question question1, question2;
        
        // State new game
        instanceCM.newGame();
        // State start game (done configurations)
        List<Challenge.Categories> categoryList = new ArrayList<>();
        categoryList.add(Challenge.Categories.UPPERCASE_AND_LOWERCASE);
        instanceCM.startGame(categoryList);
        // Test Questions
        List<Question> questionList = new ArrayList<>();
        question1 = new Question(Answer.NO_ANSWER, "Teste 1", "", "", 0);
        question2 = new Question(Answer.NO_ANSWER, "Teste 2", "", "", 0);
        questionList.add(question1);
        questionList.add(question2);
        instanceCM.challenge.setQuestionsList(questionList);
        
        instanceCM.nextAnswer(Answer.OPTION_A);
        
        if (!instanceCM.challenge.getCurrentQuestion().getQuestion().equals(question2.getQuestion()))
            fail("Result: "+instanceCM.challenge.getCurrentQuestion().getQuestion());
    }

    /**
     * Test of quitGame method, of class ChallengeModel.
     */
    @Test
    public void testQuitGame() {
        System.out.println("quitGame");
        instanceCM.startGame(null);
        instanceCM.quitGame();
        if(!(instanceCM.getChallenge().getCurrentState() instanceof WaitConfiguration))
            fail("Error.");
    }

    /**
     * Test of end method, of class ChallengeModel.
     */
    @Test
    public void testEnd() {
        System.out.println("end");
        instanceCM.startGame(null);
        instanceCM.end();
        System.out.println(instanceCM.challenge.getCurrentState().toString());
        if(!(instanceCM.getChallenge().getCurrentState() instanceof WaitScore)) 
            fail("Error.");
    }

    /**
     * Test of setCurrentProfile method, of class ChallengeModel.
     */
    @Test
    public void testSetCurrentProfile() {
        System.out.println("setCurrentProfile");
        Player player = new Player(2, "John");
        instanceCM.setCurrentProfile(player);
        String expResult = "Id: 2 Name: John";
        String result = "Id: " + instanceCM.challenge.getCurrentProfile().getId() + " Name: " + instanceCM.challenge.getCurrentProfile().getName();
        assertEquals(expResult, result);
    }

    /**
     * Test of getChallenge method, of class ChallengeModel.
     */
//    @Test
//    public void testGetChallenge() {
//        System.out.println("getChallenge");
//       // ChallengeModel instance = null;
//        //Challenge expResult = null;
//        String result = instanceCM.getChallenge().toString();
//        System.out.println(result);
//        //assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        //fail("The test case is a prototype.");
//    }

    /**
     * Test of getCurrentPlayer method, of class ChallengeModel.
     */
    @Test
    public void testGetCurrentPlayer() {
        System.out.println("getCurrentPlayer");
        Player player = new Player(2, "John");
        instanceCM.setCurrentProfile(player);
        String expResult = "Id: 2 Name: John";
        String result = "Id: " + instanceCM.challenge.getCurrentProfile().getId() + " Name: " + instanceCM.challenge.getCurrentProfile().getName();
        assertEquals(expResult, result);
    }

    /**
     * Test of backPressed method, of class ChallengeModel.
     */
    @Test
    public void testBackPressed() {
        System.out.println("backPressed");
        instanceCM.backPressed();
        if(instanceCM.getChallenge() != null)
            fail("Error.");
    }
    
}
