package com.csm.straining.dataaccess.entity.test;

public class Test {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_test.id
     *
     * @mbggenerated Sat Mar 19 16:18:33 CST 2016
     */
    private Integer id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_test.name
     *
     * @mbggenerated Sat Mar 19 16:18:33 CST 2016
     */
    private String name;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_test.age
     *
     * @mbggenerated Sat Mar 19 16:18:33 CST 2016
     */
    private Integer age;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_test.id
     *
     * @return the value of tb_test.id
     *
     * @mbggenerated Sat Mar 19 16:18:33 CST 2016
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_test.id
     *
     * @param id the value for tb_test.id
     *
     * @mbggenerated Sat Mar 19 16:18:33 CST 2016
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_test.name
     *
     * @return the value of tb_test.name
     *
     * @mbggenerated Sat Mar 19 16:18:33 CST 2016
     */
    public String getName() {
        return name;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_test.name
     *
     * @param name the value for tb_test.name
     *
     * @mbggenerated Sat Mar 19 16:18:33 CST 2016
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_test.age
     *
     * @return the value of tb_test.age
     *
     * @mbggenerated Sat Mar 19 16:18:33 CST 2016
     */
    public Integer getAge() {
        return age;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_test.age
     *
     * @param age the value for tb_test.age
     *
     * @mbggenerated Sat Mar 19 16:18:33 CST 2016
     */
    public void setAge(Integer age) {
        this.age = age;
    }
}