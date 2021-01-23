package com.xzsd.pc.images.entity;

/**
 * @Description 用户实体类（顶部导航栏）
 * @Author jintian
 * @Date 2020-08-22
 */
public class TopInfo {
    /**
     * 用户编号
     */
    private String userCode;
    /**
     * 用户姓名
     */
    private String userName;
    /**
     * 头像路径
     */
    private String imagePath;
    /**
     * 角色
     */
    private String role;
    /**
     * 作废标记 0为存在，1为作废
     */
    private int isDeleted;
    /**
     * 创造时间
     */
    private String createTime;
    /**
     * 创建者
     */
    private String createName;
    /**
     * 更新时间
     */
    private String updateTime;
    /**
     * 更新者
     */
    private String updateName;
    /**
     * 版本号
     */
    private String version;

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setIsDeleted(int isDeleted) {
        this.isDeleted = isDeleted;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public void setCreateName(String createName) {
        this.createName = createName;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public void setUpdateName(String updateName) {
        this.updateName = updateName;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getUserCode() {
        return userCode;
    }

    public String getUserName() {
        return userName;
    }

    public String getImagePath() {
        return imagePath;
    }

    public String getRole() {
        return role;
    }

    public int getIsDeleted() {
        return isDeleted;
    }

    public String getCreateTime() {
        return createTime;
    }

    public String getCreateName() {
        return createName;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public String getUpdateName() {
        return updateName;
    }

    public String getVersion() {
        return version;
    }
}
