package com.jiaye.cashloan.config;

import com.jiaye.cashloan.LoanApplication;

/**
 * FileConfig
 *
 * @author 贾博瑄
 */
public interface FileConfig {

    /**
     * 存放所有数据字典的文件夹路径
     */
    @SuppressWarnings("ConstantConditions")
    String DOWNLOAD_PATH = LoanApplication.getInstance().getExternalFilesDir("Download").getPath();

    /**
     * 地区文件路径
     */
    String AREA_PATH = DOWNLOAD_PATH + "/" + "area.json";

    /**
     * 教育状况文件路径
     */
    String EDUCATION_PATH = DOWNLOAD_PATH + "/" + "education.json";

    /**
     * 婚姻状况文件路径
     */
    String MARRIAGE_PATH = DOWNLOAD_PATH + "/" + "marriage.json";

    /**
     * 关系文件路径
     */
    String RELATION_PATH = DOWNLOAD_PATH + "/" + "relation.json";
}
