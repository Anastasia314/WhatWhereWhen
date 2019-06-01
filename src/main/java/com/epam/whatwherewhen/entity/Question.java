package com.epam.whatwherewhen.entity;

/**
 * Date: 29.01.2019
 *
 * @author Kavzovich Anastasia
 * @version 1.0
 */
public class Question extends Entity {
    private long questionId;
    private long authorId;
    private String type;
    private String body;
    private String answer;
    private long date;
    private String source;
    private boolean isActive;
    private String photo;

    public Question() {
    }

    public Question( long authorId, String type, String body, String answer, long date, String source) {
        this.authorId = authorId;
        this.type = type;
        this.body = body;
        this.answer = answer;
        this.date = date;
        this.source = source;
    }

    public Question(long authorId, String type, String body, String answer, long date, String source,String photo) {
        this.authorId = authorId;
        this.type = type;
        this.body = body;
        this.answer = answer;
        this.date = date;
        this.source = source;
        this.photo = photo;
    }

    public long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(long questionId) {
        this.questionId = questionId;
    }

    public long getAuthorId() {
        return authorId;
    }

    public void setAuthorId(long authorId) {
        this.authorId = authorId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Question)) return false;

        Question question = (Question) o;

        if (questionId != question.questionId) return false;
        if (authorId != question.authorId) return false;
        if (date != question.date) return false;
        if (isActive != question.isActive) return false;
        if (type != null ? !type.equals(question.type) : question.type != null) return false;
        if (body != null ? !body.equals(question.body) : question.body != null) return false;
        if (answer != null ? !answer.equals(question.answer) : question.answer != null) return false;
        if (source != null ? !source.equals(question.source) : question.source != null) return false;
        return photo != null ? photo.equals(question.photo) : question.photo == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (questionId ^ (questionId >>> 32));
        result = 31 * result + (int) (authorId ^ (authorId >>> 32));
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (body != null ? body.hashCode() : 0);
        result = 31 * result + (answer != null ? answer.hashCode() : 0);
        result = 31 * result + (int) (date ^ (date >>> 32));
        result = 31 * result + (source != null ? source.hashCode() : 0);
        result = 31 * result + (isActive ? 1 : 0);
        result = 31 * result + (photo != null ? photo.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Question{" +
                "questionId=" + questionId +
                ", authorId=" + authorId +
                ", type='" + type + '\'' +
                ", body='" + body + '\'' +
                ", answer='" + answer + '\'' +
                ", date=" + date +
                ", source='" + source + '\'' +
                ", isActive=" + isActive +
                ", photo='" + photo + '\'' +
                '}';
    }
}