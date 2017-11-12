package com.jiaye.cashloan.http.gongxinbao;

import com.google.gson.annotations.SerializedName;

/**
 * GongXinBaoOperatorsConfig
 *
 * @author 贾博瑄
 */

@SuppressWarnings("ALL")
public class GongXinBaoOperatorsConfig {

    /*运营商类型*/
    @SerializedName("operatorType")
    public String operatorType;

    /*登录表单配置列表*/
    @SerializedName("loginForms")
    public LoginForms[] loginForms;

    public String getOperatorType() {
        return operatorType;
    }

    public void setOperatorType(String operatorType) {
        this.operatorType = operatorType;
    }

    public LoginForms[] getLoginForms() {
        return loginForms;
    }

    public void setLoginForms(LoginForms[] loginForms) {
        this.loginForms = loginForms;
    }

    public static class LoginForms {

        /*授权所需要的字段列表*/
        @SerializedName("fields")
        public Fields[] fields;

        /*密码重置配置*/
        @SerializedName("pwdResetConfig")
        public PwdResetConfig pwdResetConfig;

        public Fields[] getFields() {
            return fields;
        }

        public void setFields(Fields[] fields) {
            this.fields = fields;
        }

        public PwdResetConfig getPwdResetConfig() {
            return pwdResetConfig;
        }

        public void setPwdResetConfig(PwdResetConfig pwdResetConfig) {
            this.pwdResetConfig = pwdResetConfig;
        }

        public static class Fields {

            /*字段名*/
            @SerializedName("field")
            public String field;

            /*字段额外配置*/
            @SerializedName("fieldExtraConfig")
            public FieldExtraConfig fieldExtraConfig;

            public String getField() {
                return field;
            }

            public void setField(String field) {
                this.field = field;
            }

            public FieldExtraConfig getFieldExtraConfig() {
                return fieldExtraConfig;
            }

            public void setFieldExtraConfig(FieldExtraConfig fieldExtraConfig) {
                this.fieldExtraConfig = fieldExtraConfig;
            }

            public static class FieldExtraConfig {

                /*验证码类型 PIC SMS*/
                @SerializedName("fieldExtraType")
                public String fieldExtraType;

                public String getFieldExtraType() {
                    return fieldExtraType;
                }

                public void setFieldExtraType(String fieldExtraType) {
                    this.fieldExtraType = fieldExtraType;
                }
            }
        }

        public static class PwdResetConfig {

            /*密码重置提示*/
            @SerializedName("resetTips")
            public String resetTips;

            public String getResetTips() {
                return resetTips;
            }

            public void setResetTips(String resetTips) {
                this.resetTips = resetTips;
            }
        }
    }
}
