package com.jiaye.cashloan.http.gongxinbao;

import com.google.gson.annotations.SerializedName;

/**
 * GongXinBao
 *
 * @author 贾博瑄
 */

public class GongXinBao {

    /*当前任务阶段状态。PREPARE：预准备阶段；LOGINED：登录成功阶段；FINISHED:任务完成阶段*/
    @SerializedName("stage")
    private String stage;

    /*当前任务具体子状态*/
    // LOGIN_WAITING	        登录进行中	不处理
    // LOGIN_SUCCESS	        登录成功	可隐藏登录页面，出现导入进度页。
    // LOGIN_FAILED	            登录失败	弹窗提示remark对应的失败原因，用户确认后刷新二维码或图片验证码等待用户的下一次操作
    // REFRESH_IMAGE_SUCCESS	刷新登录图片验证码成功	在特定区域展示remark对应的图片验证码
    // REFRESH_IMAGE_FAILED	    刷新登录图片验证码失败	提示用户系统繁忙，刷新重试
    // REFRESH_SMS_SUCCESS	    刷新登录短信验证码成功	提示用户短信发送成功，按smsInterval进行时间倒计时
    // REFRESH_SMS_FAILED	    刷新登录短信验证码失败	提示用户系统繁忙，刷新重试
    // SMS_VERIFY_NEW	        过程中需要短信验证	把短信验证的引导内容显示在弹窗上
    // IMAGE_VERIFY_NEW	        过程中需要图片验证	把remark对应的图片显示在弹窗上
    // WAITING	                任务进行中	可不处理
    // SUCCESS	                任务成功结束	跳转进入一下个流程
    // FAILED
    @SerializedName("phaseStatus")
    private String phaseStatus;

    /*用户授权token*/
    @SerializedName("token")
    private String token;

    /*该具体授权项下对应的登录表单配置列表*/
    @SerializedName("timestamp")
    private long timestamp;

    /*状态的附属信息，如弹窗的展示信息*/
    @SerializedName("extra")
    private Extra extra;

    public String getStage() {
        return stage;
    }

    public void setStage(String stage) {
        this.stage = stage;
    }

    public String getPhaseStatus() {
        return phaseStatus;
    }

    public void setPhaseStatus(String phaseStatus) {
        this.phaseStatus = phaseStatus;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public Extra getExtra() {
        return extra;
    }

    public void setExtra(Extra extra) {
        this.extra = extra;
    }

    public static class Extra {

        /*弹窗引导title，有则需要显示。如：短信验证码*/
        @SerializedName("title")
        private String title;

        /*弹窗的引导文本。如:请输入您收到的浙江移动登录验证码*/
        @SerializedName("tips")
        private String tips;

        /*交互流程是否结束。如：true:交互流程已结束;false:交互流程未结束。*/
        @SerializedName("interactiveOver")
        private boolean interactiveOver;

        /*状态为失败为LOGIN_FAILED、FAILED时给出相应的提示信息。如：用户名密码错误。状态为失败为REFRESH_IMAGE_SUCCESS时，是图片验证码的base64。*/
        @SerializedName("remark")
        private String remark;

        /*二维码初始化信息*/
        @SerializedName("qrCode")
        private QRCode qrCode;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getTips() {
            return tips;
        }

        public void setTips(String tips) {
            this.tips = tips;
        }

        public boolean isInteractiveOver() {
            return interactiveOver;
        }

        public void setInteractiveOver(boolean interactiveOver) {
            this.interactiveOver = interactiveOver;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public QRCode getQrCode() {
            return qrCode;
        }

        public void setQrCode(QRCode qrCode) {
            this.qrCode = qrCode;
        }

        public static class QRCode {

            /*二维码图片地址*/
            @SerializedName("httpQRCode")
            private String httpQRCode;

            /*用于手机rpc唤起本地app的链接,接入的app要实现相应功能需要做额外设置。*/
            @SerializedName("rpcQRCode")
            private String rpcQRCode;

            /*二维码对应的网站标示*/
            @SerializedName("website")
            private String website;

            public String getHttpQRCode() {
                return httpQRCode;
            }

            public void setHttpQRCode(String httpQRCode) {
                this.httpQRCode = httpQRCode;
            }

            public String getRpcQRCode() {
                return rpcQRCode;
            }

            public void setRpcQRCode(String rpcQRCode) {
                this.rpcQRCode = rpcQRCode;
            }

            public String getWebsite() {
                return website;
            }

            public void setWebsite(String website) {
                this.website = website;
            }
        }
    }
}
