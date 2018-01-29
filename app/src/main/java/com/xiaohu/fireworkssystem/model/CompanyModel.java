package com.xiaohu.fireworkssystem.model;

import com.xiaohu.fireworkssystem.model.view.CompanyImageModel;

import java.io.Serializable;

/**
 * 临售注册企业
 */
public class CompanyModel implements Serializable {
    private String CompanyId;//企业编码
    private String CompanyName;//企业名称
    private String CompanyType;//企业类型
    private String CompanyState;//企业状态
    private String CompanyAddress;//企业地址
    private String CompanyPhone;//企业电话
    private String Area;//辖区编码
    private String Boss;//法人
    private String BossCertNumber;//证件号码
    private String BossCredentialId;//法人上岗证
    private String base64BossCredentialImage;//法人上岗证图片
    private String Leader;//负责人
    private String LeaderCertNumber;//负责人证件号
    private String LeaderCredentialId;//负责人上岗证号
    private String base64LeaderCredentialImage;//负责人上岗证图片app
    private String LeaderLinkWay;//负责人联系方式
    private double PointEast;//东经
    private double PointNorth;//北纬
    private String LicenseNumber;//许可证号
    private String Base64LicenseImage;//许可证图片
    private String LicenseNumberValidity;//有效期（至）
    private String BusinessLicenseNumber;//营业执照号
    private String BusinessLicenseNumberValidity;//营业执照有效期
    private String Base64BusinessLicenseImage;//营业执照图片
    private String Comment;//备注
    private String EncryptionKey;//加密key
    private String CreateTime;//创建时间
    private String CreateUserId;//创建用户
    private String LastTime;//最后一次修改时间
    private CompanyImageModel[] CompanyImages;
    private String DeviceCode;//设备描述符
    private String AuthorizedCode;//授权码
    private boolean isCVR;//true普通手机false一体机
    private String TerminalCode;//设备编码
    private String ClientUserPassWord;//客户端添加用户使用

    public String getCompanyId() {
        return CompanyId;
    }

    public void setCompanyId(String companyId) {
        CompanyId = companyId;
    }

    public String getCompanyName() {
        return CompanyName;
    }

    public void setCompanyName(String companyName) {
        CompanyName = companyName;
    }

    public String getCompanyType() {
        return CompanyType;
    }

    public void setCompanyType(String companyType) {
        CompanyType = companyType;
    }

    public String getCompanyState() {
        return CompanyState;
    }

    public void setCompanyState(String companyState) {
        CompanyState = companyState;
    }

    public String getCompanyAddress() {
        return CompanyAddress;
    }

    public void setCompanyAddress(String companyAddress) {
        CompanyAddress = companyAddress;
    }

    public String getCompanyPhone() {
        return CompanyPhone;
    }

    public void setCompanyPhone(String companyPhone) {
        CompanyPhone = companyPhone;
    }

    public String getArea() {
        return Area;
    }

    public void setArea(String area) {
        Area = area;
    }

    public String getBoss() {
        return Boss;
    }

    public void setBoss(String boss) {
        Boss = boss;
    }

    public String getBossCertNumber() {
        return BossCertNumber;
    }

    public void setBossCertNumber(String bossCertNumber) {
        BossCertNumber = bossCertNumber;
    }

    public String getBossCredentialId() {
        return BossCredentialId;
    }

    public void setBossCredentialId(String bossCredentialId) {
        BossCredentialId = bossCredentialId;
    }

    public String getBase64BossCredentialImage() {
        return base64BossCredentialImage;
    }

    public void setBase64BossCredentialImage(String base64BossCredentialImage) {
        this.base64BossCredentialImage = base64BossCredentialImage;
    }

    public String getLeader() {
        return Leader;
    }

    public void setLeader(String leader) {
        Leader = leader;
    }

    public String getLeaderCertNumber() {
        return LeaderCertNumber;
    }

    public void setLeaderCertNumber(String leaderCertNumber) {
        LeaderCertNumber = leaderCertNumber;
    }

    public String getLeaderCredentialId() {
        return LeaderCredentialId;
    }

    public void setLeaderCredentialId(String leaderCredentialId) {
        LeaderCredentialId = leaderCredentialId;
    }

    public String getBase64LeaderCredentialImage() {
        return base64LeaderCredentialImage;
    }

    public void setBase64LeaderCredentialImage(String base64LeaderCredentialImage) {
        this.base64LeaderCredentialImage = base64LeaderCredentialImage;
    }

    public String getBase64LicenseImage() {
        return Base64LicenseImage;
    }

    public void setBase64LicenseImage(String base64LicenseImage) {
        Base64LicenseImage = base64LicenseImage;
    }

    public String getBase64BusinessLicenseImage() {
        return Base64BusinessLicenseImage;
    }

    public void setBase64BusinessLicenseImage(String base64BusinessLicenseImage) {
        Base64BusinessLicenseImage = base64BusinessLicenseImage;
    }

    public String getLeaderLinkWay() {
        return LeaderLinkWay;
    }

    public void setLeaderLinkWay(String leaderLinkWay) {
        LeaderLinkWay = leaderLinkWay;
    }

    public double getPointEast() {
        return PointEast;
    }

    public void setPointEast(double pointEast) {
        PointEast = pointEast;
    }

    public double getPointNorth() {
        return PointNorth;
    }

    public void setPointNorth(double pointNorth) {
        PointNorth = pointNorth;
    }

    public String getLicenseNumber() {
        return LicenseNumber;
    }

    public void setLicenseNumber(String licenseNumber) {
        LicenseNumber = licenseNumber;
    }

    public String getLicenseNumberValidity() {
        return LicenseNumberValidity;
    }

    public void setLicenseNumberValidity(String licenseNumberValidity) {
        LicenseNumberValidity = licenseNumberValidity;
    }

    public String getBusinessLicenseNumber() {
        return BusinessLicenseNumber;
    }

    public void setBusinessLicenseNumber(String businessLicenseNumber) {
        BusinessLicenseNumber = businessLicenseNumber;
    }

    public String getBusinessLicenseNumberValidity() {
        return BusinessLicenseNumberValidity;
    }

    public void setBusinessLicenseNumberValidity(String businessLicenseNumberValidity) {
        BusinessLicenseNumberValidity = businessLicenseNumberValidity;
    }

    public String getComment() {
        return Comment;
    }

    public void setComment(String comment) {
        Comment = comment;
    }

    public String getEncryptionKey() {
        return EncryptionKey;
    }

    public void setEncryptionKey(String encryptionKey) {
        EncryptionKey = encryptionKey;
    }

    public String getCreateTime() {
        return CreateTime;
    }

    public void setCreateTime(String createTime) {
        CreateTime = createTime;
    }

    public String getCreateUserId() {
        return CreateUserId;
    }

    public void setCreateUserId(String createUserId) {
        CreateUserId = createUserId;
    }

    public String getLastTime() {
        return LastTime;
    }

    public void setLastTime(String lastTime) {
        LastTime = lastTime;
    }

    public String getDeviceCode() {
        return DeviceCode;
    }

    public void setDeviceCode(String deviceCode) {
        DeviceCode = deviceCode;
    }

    public String getAuthorizedCode() {
        return AuthorizedCode;
    }

    public void setAuthorizedCode(String authorizedCode) {
        AuthorizedCode = authorizedCode;
    }

    public boolean isCVR() {
        return isCVR;
    }

    public void setCVR(boolean CVR) {
        isCVR = CVR;
    }

    public String getTerminalCode() {
        return TerminalCode;
    }

    public CompanyImageModel[] getCompanyImages() {
        return CompanyImages;
    }

    public void setCompanyImages(CompanyImageModel[] companyImages) {
        CompanyImages = companyImages;
    }

    public String getClientUserPassWord() {
        return ClientUserPassWord;
    }

    public void setClientUserPassWord(String clientUserPassWord) {
        ClientUserPassWord = clientUserPassWord;
    }

    public void setTerminalCode(String terminalCode) {
        TerminalCode = terminalCode;
    }
}
