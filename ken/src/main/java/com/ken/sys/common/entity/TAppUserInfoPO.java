/******************************************************************************
 *
 * 作者（author）：ken
 * 微信（weChat）：mlchao1992
 * 个人博客（website）：
 *
 ******************************************************************************
 * 注意：尊重原创
 *****************************************************************************/

package com.ken.sys.common.entity;

import com.ken.sys.common.anno.DataType;
import com.ken.sys.common.anno.ExcelColumnAnnotation;
import com.ken.sys.common.anno.ExcelDateFormateAnnotation;
import com.ken.sys.common.util.RegExUtil;

import java.io.Serializable;
import java.util.Date;

public class TAppUserInfoPO  implements Serializable {
    private Long id;

    @ExcelColumnAnnotation(index = 1,length = 30,nullable = true,columnName="用户姓名",fieldName="name")
    private String name;

    @ExcelColumnAnnotation(index = 0,length = 11,nullable = false,pattern=RegExUtil.checkPhone,columnName="业务号码",fieldName="tel")
    private String tel;

    private String userId;

    @ExcelColumnAnnotation(index = 2,length = 11,nullable = true,columnName="性别",fieldName="sexName")
    private String sexName;

    /**会员等级id**/

    private Long memberLevelId;
    /**会员等级名称**/

    @ExcelColumnAnnotation(index = 3,length = 32,nullable = true,columnName="级别",fieldName="memberLevel")
    private String memberLevel;


    @ExcelColumnAnnotation(index = 4,length = 32,nullable = true,columnName="套餐",fieldName="setMeal")
    private String setMeal;

    //导出使用的自定义注解
    @ExcelDateFormateAnnotation(dateFormate="YYYYMMdd",fieldName="birthday")
    //导入使用的自定义注解
    @ExcelColumnAnnotation(index = 5, dataType =DataType.DATE,nullable = true,pattern="YYYYMMdd",columnName="生日",fieldName="birthday")
    private Date birthday;

    @ExcelColumnAnnotation(index = 6,dataType = DataType.LONG,nullable = true,columnName="在网时长",fieldName="networkTimes")
    private Long networkTimes;

    @ExcelColumnAnnotation(index = 7,length = 32,nullable = true,columnName="在网时长",fieldName="location")
    private String location;

    @ExcelColumnAnnotation(index = 8,length = 32,nullable = true,columnName="偏好",fieldName="preference")
    private String preference;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getSexName() {
        return sexName;
    }

    public void setSexName(String sexName) {
        this.sexName = sexName;
    }

    public Long getMemberLevelId() {
        return memberLevelId;
    }

    public void setMemberLevelId(Long memberLevelId) {
        this.memberLevelId = memberLevelId;
    }

    public String getMemberLevel() {
        return memberLevel;
    }

    public void setMemberLevel(String memberLevel) {
        this.memberLevel = memberLevel;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getSetMeal() {
        return setMeal;
    }

    public void setSetMeal(String setMeal) {
        this.setMeal = setMeal;
    }


    public Long getNetworkTimes() {
        return networkTimes;
    }

    public void setNetworkTimes(Long networkTimes) {
        this.networkTimes = networkTimes;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getPreference() {
        return preference;
    }

    public void setPreference(String preference) {
        this.preference = preference;
    }
}
