package com.csm.straining.dataaccess.entity.user;

public class UserRank {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_user_rank.id
     *
     * @mbggenerated Fri Apr 15 10:47:54 CST 2016
     */
    private Integer id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_user_rank.userID
     *
     * @mbggenerated Fri Apr 15 10:47:54 CST 2016
     */
    private Long userID;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_user_rank.rank
     *
     * @mbggenerated Fri Apr 15 10:47:54 CST 2016
     */
    private Integer rank;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_user_rank.score
     *
     * @mbggenerated Fri Apr 15 10:47:54 CST 2016
     */
    private Integer score;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_user_rank.id
     *
     * @return the value of tb_user_rank.id
     *
     * @mbggenerated Fri Apr 15 10:47:54 CST 2016
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_user_rank.id
     *
     * @param id the value for tb_user_rank.id
     *
     * @mbggenerated Fri Apr 15 10:47:54 CST 2016
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_user_rank.userID
     *
     * @return the value of tb_user_rank.userID
     *
     * @mbggenerated Fri Apr 15 10:47:54 CST 2016
     */
    public Long getUserID() {
        return userID;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_user_rank.userID
     *
     * @param userID the value for tb_user_rank.userID
     *
     * @mbggenerated Fri Apr 15 10:47:54 CST 2016
     */
    public void setUserID(Long userID) {
        this.userID = userID;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_user_rank.rank
     *
     * @return the value of tb_user_rank.rank
     *
     * @mbggenerated Fri Apr 15 10:47:54 CST 2016
     */
    public Integer getRank() {
        return rank;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_user_rank.rank
     *
     * @param rank the value for tb_user_rank.rank
     *
     * @mbggenerated Fri Apr 15 10:47:54 CST 2016
     */
    public void setRank(Integer rank) {
        this.rank = rank;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_user_rank.score
     *
     * @return the value of tb_user_rank.score
     *
     * @mbggenerated Fri Apr 15 10:47:54 CST 2016
     */
    public Integer getScore() {
        return score;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_user_rank.score
     *
     * @param score the value for tb_user_rank.score
     *
     * @mbggenerated Fri Apr 15 10:47:54 CST 2016
     */
    public void setScore(Integer score) {
        this.score = score;
    }
}