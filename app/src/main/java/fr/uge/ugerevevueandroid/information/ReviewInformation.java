package fr.uge.ugerevevueandroid.information;

import java.util.Date;
import java.util.List;

public class ReviewInformation {
    private long id;
    private UserInformation userInformation;
    //private Vote.VoteType voteType;
    private String title;
    private String content;
    private int score;
    private Date date;
    private List<CommentInformation> comments;
    private List<ReviewInformation> reviews;

    public ReviewInformation(long id, UserInformation userInformation, String title, String content, int score, Date date, List<CommentInformation> comments, List<ReviewInformation> reviews) {
        this.id = id;
        this.userInformation = userInformation;
        //this.voteType = voteType;
        this.title = title;
        this.content = content;
        this.score = score;
        this.date = date;
        this.comments = comments;
        this.reviews = reviews;
    }

    /*
    public static ReviewInformation from(Review review){
        Objects.requireNonNull(review, "[ReviewInformation] review is null");
        return new ReviewInformation(
                review.getId(),
                SimpleUserInformation.from(review.getUser()),
                review.getVoteUser(),
                review.getTitle(),
                review.getContent(),
                review.getScoreVote(),
                review.getDate(),
                review.getComments().stream().map(CommentInformation::from).sorted(Comparator.comparing(CommentInformation::getDate).reversed()).toList(),
                review.getReviews().stream().map(ReviewInformation::from)
                        .sorted(Comparator.comparing(ReviewInformation::getDate).reversed())
                        .sorted(Comparator.comparing(ReviewInformation::getScore).reversed()).toList()
        );
    }
    */

    public long getId() {return id;}

    public void setId(long id) {this.id = id;}

    public UserInformation getUserInformation() {return userInformation;}

    public void setUserInformation(UserInformation userInformation) {this.userInformation = userInformation;}

    public String getTitle() {return title;}

    public void setTitle(String title) {this.title = title;}

    public String getContent() {return content;}

    public void setContent(String content) {this.content = content;}

    public int getScore() {return score;}

    public void setScore(int score) {this.score = score;}

    public Date getDate() {return date;}

    public void setDate(Date date) {this.date = date;}

    public List<CommentInformation> getComments() {return comments;}

    public void setComments(List<CommentInformation> comments) {this.comments = comments;}

    public List<ReviewInformation> getReviews() {return reviews;}

    public void setReviews(List<ReviewInformation> reviews) {this.reviews = reviews;}
}
