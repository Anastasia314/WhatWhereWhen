package com.epam.whatwherewhen.entity;

/**
 * Date: 03.02.2019
 *
 * @author Kavzovich Anastasia
 * @version 1.0
 */
public class QuestionStatistic extends Entity {
    private long questionId;
    private long answered;
    private long notAnswered;
    private long liked;
    private long disliked;

    public QuestionStatistic() {
    }

    public QuestionStatistic(long questionId, long answered, long notAnswered, long liked, long disliked) {
        this.questionId = questionId;
        this.answered = answered;
        this.notAnswered = notAnswered;
        this.liked = liked;
        this.disliked = disliked;
    }

    public long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(long questionId) {
        this.questionId = questionId;
    }

    public long getAnswered() {
        return answered;
    }

    public void setAnswered(long answered) {
        this.answered = answered;
    }

    public long getNotAnswered() {
        return notAnswered;
    }

    public void setNotAnswered(long notAnswered) {
        this.notAnswered = notAnswered;
    }

    public long getLiked() {
        return liked;
    }

    public void setLiked(long liked) {
        this.liked = liked;
    }

    public long getDisliked() {
        return disliked;
    }

    public void setDisliked(long disliked) {
        this.disliked = disliked;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof QuestionStatistic)) return false;

        QuestionStatistic that = (QuestionStatistic) o;

        if (questionId != that.questionId) return false;
        if (answered != that.answered) return false;
        if (notAnswered != that.notAnswered) return false;
        if (liked != that.liked) return false;
        return disliked == that.disliked;
    }

    @Override
    public int hashCode() {
        int result = (int) (questionId ^ (questionId >>> 32));
        result = 31 * result + (int) (answered ^ (answered >>> 32));
        result = 31 * result + (int) (notAnswered ^ (notAnswered >>> 32));
        result = 31 * result + (int) (liked ^ (liked >>> 32));
        result = 31 * result + (int) (disliked ^ (disliked >>> 32));
        return result;
    }

    @Override
    public String toString() {
        return "QuestionStatistic{" +
                "questionId=" + questionId +
                ", answered=" + answered +
                ", notAnswered=" + notAnswered +
                ", liked=" + liked +
                ", disliked=" + disliked +
                '}';
    }
}
