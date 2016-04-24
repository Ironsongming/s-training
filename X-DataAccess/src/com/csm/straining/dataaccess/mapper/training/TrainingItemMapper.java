package com.csm.straining.dataaccess.mapper.training;

import com.csm.straining.dataaccess.entity.training.TrainingItem;
import com.csm.straining.dataaccess.entity.training.TrainingItemExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TrainingItemMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_training_item
     *
     * @mbggenerated Sun Apr 24 01:38:45 CST 2016
     */
    int countByExample(TrainingItemExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_training_item
     *
     * @mbggenerated Sun Apr 24 01:38:45 CST 2016
     */
    int deleteByExample(TrainingItemExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_training_item
     *
     * @mbggenerated Sun Apr 24 01:38:45 CST 2016
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_training_item
     *
     * @mbggenerated Sun Apr 24 01:38:45 CST 2016
     */
    int insert(TrainingItem record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_training_item
     *
     * @mbggenerated Sun Apr 24 01:38:45 CST 2016
     */
    int insertSelective(TrainingItem record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_training_item
     *
     * @mbggenerated Sun Apr 24 01:38:45 CST 2016
     */
    List<TrainingItem> selectByExample(TrainingItemExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_training_item
     *
     * @mbggenerated Sun Apr 24 01:38:45 CST 2016
     */
    TrainingItem selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_training_item
     *
     * @mbggenerated Sun Apr 24 01:38:45 CST 2016
     */
    int updateByExampleSelective(@Param("record") TrainingItem record, @Param("example") TrainingItemExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_training_item
     *
     * @mbggenerated Sun Apr 24 01:38:45 CST 2016
     */
    int updateByExample(@Param("record") TrainingItem record, @Param("example") TrainingItemExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_training_item
     *
     * @mbggenerated Sun Apr 24 01:38:45 CST 2016
     */
    int updateByPrimaryKeySelective(TrainingItem record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_training_item
     *
     * @mbggenerated Sun Apr 24 01:38:45 CST 2016
     */
    int updateByPrimaryKey(TrainingItem record);
}