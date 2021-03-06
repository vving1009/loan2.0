package com.jiaye.cashloan.http.base;

import com.satcatche.library.utils.DateUtil;
import com.satcatche.library.utils.RandomUtil;

/**
 * SatcatcheChildRequest
 *
 * @author 贾博瑄
 */
public abstract class SatcatcheChildRequest {

    /*流水号*/
    private transient String serialnumber = getBusiness() + DateUtil.formatDateTime(System.currentTimeMillis()) + RandomUtil.number4();

    public String getSerialnumber() {
        return serialnumber;
    }

    protected abstract String getBusiness();
}
