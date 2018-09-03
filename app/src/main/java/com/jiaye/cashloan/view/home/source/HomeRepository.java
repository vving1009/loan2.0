package com.jiaye.cashloan.view.home.source;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.text.TextUtils;

import com.jiaye.cashloan.LoanApplication;
import com.jiaye.cashloan.R;
import com.jiaye.cashloan.http.base.EmptyResponse;
import com.jiaye.cashloan.http.data.home.CheckLoanRequest;
import com.jiaye.cashloan.http.data.home.LoanRequest;
import com.jiaye.cashloan.http.data.loan.Loan;
import com.jiaye.cashloan.http.data.loan.RiskAppList;
import com.jiaye.cashloan.http.data.loan.RiskAppListRequest;
import com.jiaye.cashloan.http.data.loan.UploadRiskAppListRequest;
import com.jiaye.cashloan.http.utils.SatcatcheResponseTransformer;
import com.jiaye.cashloan.persistence.User;
import com.jiaye.cashloan.view.LocalException;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Flowable;

/**
 * HomeRepository
 *
 * @author 贾博瑄
 */

public class HomeRepository implements HomeDataSource {

    @Override
    public Flowable<User> queryUser() {
        return Flowable.just(LoanApplication.getInstance().getDbHelper().queryUser())
                .filter(user -> {
                    if (!TextUtils.isEmpty(user.getToken())) {
                        return true;
                    } else {
                        throw new LocalException(R.string.error_auth_not_log_in);
                    }
                });
    }

    @Override
    public Flowable<EmptyResponse> checkLoan() {
        return Flowable.just(new CheckLoanRequest())
                .compose(new SatcatcheResponseTransformer<CheckLoanRequest, EmptyResponse>("checkLoan"));
    }

    @Override
    public Flowable<Loan> requestLoan() {
        return queryUser()
                .map(user -> {
                    LoanRequest request = new LoanRequest();
                    request.setPhone(user.getPhone());
                    return request;
                })
                .compose(new SatcatcheResponseTransformer<LoanRequest, Loan>("loan"))
                .map(loan -> {
                    LoanApplication.getInstance().getDbHelper().updateUser(null, null, loan.getLoanId());
                    return loan;
                });
    }

    @Override
    public Flowable<EmptyResponse> uploadRiskAppList() {
        return Flowable.just(new RiskAppListRequest())
                .compose(new SatcatcheResponseTransformer<RiskAppListRequest, RiskAppList>("riskAppList"))
                .map(riskAppList -> {
                    UploadRiskAppListRequest request = new UploadRiskAppListRequest();
                    String loanId = LoanApplication.getInstance().getDbHelper().queryUser().getLoanId();
                    request.setLoanId(loanId);
                    ArrayList<UploadRiskAppListRequest.RiskApp> list = new ArrayList<>();
                    PackageManager pm = LoanApplication.getInstance().getApplicationContext().getPackageManager();
                    List<PackageInfo> packages = pm.getInstalledPackages(0);
                    for (PackageInfo packageInfo : packages) {
                        for (RiskAppList.RiskApp riskApp : riskAppList.getList()) {
                            if (riskApp.getAppPackage().equals(packageInfo.packageName)) {
                                UploadRiskAppListRequest.RiskApp app = new UploadRiskAppListRequest.RiskApp();
                                app.setAppName(riskApp.getAppName());
                                list.add(app);
                            }
                        }
                    }
                    request.setList(list);
                    return request;
                })
                .compose(new SatcatcheResponseTransformer<UploadRiskAppListRequest, EmptyResponse>("uploadRiskAppList"));
    }
}
