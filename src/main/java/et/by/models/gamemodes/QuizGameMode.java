package et.by.models.gamemodes;

public class QuizGameMode {
    private String question;
    private String[] options;
    private int rightAnswerNumber;
    private int chosenAnswerNumber;

    QuizGameMode(String question, String[] options, int rightAnswerNumber, int chosenAnswerNumber){
        this.setQuestion(question);
        this.setOptions(options);
        this.setRightAnswerNumber(rightAnswerNumber);
        this.setChosenAnswerNumber(chosenAnswerNumber);
    }

    public String getQuestion() {
        return question;
    }

    public String[] getOptions() {
        return options;
    }

    public int getRightAnswerNumber() {
        return rightAnswerNumber;
    }

    public int getChosenAnswerNumber() {
        return chosenAnswerNumber;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public void setOptions(String[] options) {
        this.options = options;
    }

    public void setRightAnswerNumber(int rightAnswerNumber) {
        this.rightAnswerNumber = rightAnswerNumber;
    }

    public void setChosenAnswerNumber(int chosenAnswerNumber) {
        this.chosenAnswerNumber = chosenAnswerNumber;
    }
}
