package logic;

/**
 * Question.
 * This class represents the questions on the database.
 * This class also have information about the possibles
 * answers and the respective correct answer.
 * 
 * @author PTXXI
 */
public class Question {
    
    private final Answer answer;
    private final String question;
    private final String optionA;
    private final String optionB;

    public Answer getAnswer() {
        return answer;
    }

    public Question(Answer answer, String question, String optionA, String optionB) {
        this.answer = answer;
        this.question = question;
        this.optionA = optionA;
        this.optionB = optionB;
    }

    public String getQuestion() {
        return question;
    }

    public String getOptionA() {
        return optionA;
    }

    public String getOptionB() {
        return optionB;
    }
    
}
