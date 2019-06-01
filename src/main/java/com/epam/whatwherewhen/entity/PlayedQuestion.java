package com.epam.whatwherewhen.entity;

/**
 * Date: 03.02.2019
 *
 * @author Kavzovich Anastasia
 * @version 1.0
 */
public class PlayedQuestion extends Entity {
    private long questionId;
    private long userId;
    private boolean isOnAppeal;
    private boolean isAnswered;
    private String answer;
    private String correctAnswer;

    public PlayedQuestion() {
    }

    public PlayedQuestion(long questionId, long userId, boolean isAnswered, String answer) {
        this.questionId = questionId;
        this.userId = userId;
        this.isAnswered = isAnswered;
        this.answer = answer;
    }

    public long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(long questionId) {
        this.questionId = questionId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public boolean isAnswered() {
        return isAnswered;
    }

    public void setAnswered(boolean answered) {
        isAnswered = answered;
    }

    public boolean isOnAppeal() {
        return isOnAppeal;
    }

    public void setOnAppeal(boolean onAppeal) {
        isOnAppeal = onAppeal;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PlayedQuestion)) return false;

        PlayedQuestion that = (PlayedQuestion) o;

        if (questionId != that.questionId) return false;
        if (userId != that.userId) return false;
        if (isOnAppeal != that.isOnAppeal) return false;
        if (isAnswered != that.isAnswered) return false;
        return answer != null ? answer.equals(that.answer) : that.answer == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (questionId ^ (questionId >>> 32));
        result = 31 * result + (int) (userId ^ (userId >>> 32));
        result = 31 * result + (isOnAppeal ? 1 : 0);
        result = 31 * result + (isAnswered ? 1 : 0);
        result = 31 * result + (answer != null ? answer.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "PlayedQuestion{" +
                "questionId=" + questionId +
                ", userId=" + userId +
                ", isOnAppeal=" + isOnAppeal +
                ", isAnswered=" + isAnswered +
                ", answer='" + answer + '\'' +
                '}';
    }
}
