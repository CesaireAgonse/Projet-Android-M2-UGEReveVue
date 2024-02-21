package fr.uge.ugerevevueandroid.information;


import java.util.Date;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class CodeInformation {

    long id;
    SimpleUserInformation userInformation;
    //Vote.VoteType voteType;
    String title;
    String description;
    String javaContent;
    String unitContent;
    int score;
    Date date;
    Set<CommentInformation> comments;
    Set<ReviewInformation> review;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getJavaContent() {
        return javaContent;
    }

    public void setJavaContent(String javaContent) {
        this.javaContent = javaContent;
    }

    public String getUnitContent() {
        return unitContent;
    }

    public void setUnitContent(String unitContent) {
        this.unitContent = unitContent;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public SimpleUserInformation getUserInformation() {
        return userInformation;
    }

    public void setUserInformation(SimpleUserInformation userInformation) {
        this.userInformation = userInformation;
    }

    public Set<CommentInformation> getComments() {
        return comments;
    }

    public void setComments(Set<CommentInformation> comments) {
        this.comments = comments;
    }

    public Set<ReviewInformation> getReview() {
        return review;
    }

    public void setReview(Set<ReviewInformation> review) {
        this.review = review;
    }

    public CodeInformation(long id, String title, String description, String javaContent,
                           String unitContent, int score, Date date,
                           SimpleUserInformation userInformation,
                           Set<CommentInformation> comments,
                           Set<ReviewInformation> review) {
        this.id = id;
        this.userInformation = userInformation;
        this.title = title;
        this.description = description;
        this.javaContent = javaContent;
        this.unitContent = unitContent;
        this.score = score;
        this.date = date;
        this.comments = comments;
        this.review = review;
    }

    public CodeInformation(){
    }

    /*
    public static CodeInformation from(Code code){
        Objects.requireNonNull(code, "[CodeInformation] code is null");
        return new CodeInformation(
                code.getId(),
                SimpleUserInformation.from(code.getUser()),
                code.getVoteUser(),
                code.getTitle(),
                code.getDescription(),
                code.getJavaContent(),
                code.getUnitContent(),
                code.getScoreVote(),
                code.getDate(),
                code.getComments().stream().map(CommentInformation::from).collect(Collectors.toSet()),
                code.getReviews().stream().map(ReviewInformation::from).collect(Collectors.toSet())
        );
    }
    */
}

