package com.jiaye.cashloan.view.data.home;

import android.content.ContentValues;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.text.TextUtils;

import com.jiaye.cashloan.LoanApplication;
import com.jiaye.cashloan.R;
import com.jiaye.cashloan.http.data.home.BannerList;
import com.jiaye.cashloan.http.data.home.BannerListRequest;
import com.jiaye.cashloan.http.data.home.ProductList;
import com.jiaye.cashloan.http.data.home.ProductListRequest;
import com.jiaye.cashloan.http.data.loan.CheckLoan;
import com.jiaye.cashloan.http.data.loan.CheckLoanRequest;
import com.jiaye.cashloan.http.data.loan.Loan;
import com.jiaye.cashloan.http.data.loan.LoanRequest;
import com.jiaye.cashloan.http.data.loan.RiskAppList;
import com.jiaye.cashloan.http.data.loan.RiskAppListRequest;
import com.jiaye.cashloan.http.data.loan.UploadRiskAppList;
import com.jiaye.cashloan.http.data.loan.UploadRiskAppListRequest;
import com.jiaye.cashloan.http.data.my.User;
import com.jiaye.cashloan.http.utils.SatcatcheResponseTransformer;
import com.jiaye.cashloan.persistence.DbContract;
import com.jiaye.cashloan.view.LocalException;

import org.reactivestreams.Publisher;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.functions.Function;

/**
 * HomeRepository
 *
 * @author 贾博瑄
 */

public class HomeRepository implements HomeDataSource {

    @Override
    public Flowable<BannerList.Banner[]> requestBannerList() {
        return Flowable.just(new BannerListRequest())
                .compose(new SatcatcheResponseTransformer<BannerListRequest, BannerList>
                        ("bannerList"))
                .map(new Function<BannerList, BannerList.Banner[]>() {
                    @Override
                    public BannerList.Banner[] apply(BannerList bannerList) throws Exception {
                        return bannerList.getBannerArray();
                    }
                });
    }

    @Override
    public Flowable<ProductList.Product[]> requestProductList() {
        return Flowable.just(new ProductListRequest())
                .compose(new SatcatcheResponseTransformer<ProductListRequest, ProductList>
                        ("productList"))
                .map(new Function<ProductList, ProductList.Product[]>() {
                    @Override
                    public ProductList.Product[] apply(ProductList productList) throws Exception {
                        return productList.getProductArray();
                    }
                });
    }

    @Override
    public Flowable<UploadRiskAppList> requestLoan(final String productId) {
        return queryUser()
                .map(new Function<User, CheckLoanRequest>() {
                    @Override
                    public CheckLoanRequest apply(User user) throws Exception {
                        CheckLoanRequest checkLoanRequest = new CheckLoanRequest();
                        checkLoanRequest.setProductId(productId);
                        return checkLoanRequest;
                    }
                })
                .compose(new SatcatcheResponseTransformer<CheckLoanRequest, CheckLoan>("checkLoan"))
                .map(new Function<CheckLoan, LoanRequest>() {
                    @Override
                    public LoanRequest apply(CheckLoan checkLoan) throws Exception {
                        LoanRequest request = new LoanRequest();
                        Cursor cursorUser = LoanApplication.getInstance().getSQLiteDatabase().rawQuery("SELECT phone FROM user", null);
                        if (cursorUser != null) {
                            if (cursorUser.moveToNext()) {
                                String phone = cursorUser.getString(cursorUser.getColumnIndex(DbContract.User.COLUMN_NAME_PHONE));
                                request.setPhone(phone);
                            }
                            cursorUser.close();
                        }
                        request.setProductId(productId);
                        return request;
                    }
                })
                .compose(new SatcatcheResponseTransformer<LoanRequest, Loan>("loan"))
                .map(new Function<Loan, Loan>() {
                    @Override
                    public Loan apply(Loan loan) throws Exception {
                        ContentValues values = new ContentValues();
                        values.put("loan_id", loan.getLoanId());
                        LoanApplication.getInstance().getSQLiteDatabase().update("user", values, null, null);
                        return loan;
                    }
                })
                .flatMap(new Function<Loan, Publisher<RiskAppList>>() {
                    @Override
                    public Publisher<RiskAppList> apply(Loan loan) throws Exception {
                        return Flowable.just(new RiskAppListRequest())
                                .compose(new SatcatcheResponseTransformer<RiskAppListRequest, RiskAppList>("riskAppList"));
                    }
                })
                .map(new Function<RiskAppList, UploadRiskAppListRequest>() {
                    @Override
                    public UploadRiskAppListRequest apply(RiskAppList riskAppList) throws Exception {
                        UploadRiskAppListRequest request = new UploadRiskAppListRequest();
                        Cursor cursor = LoanApplication.getInstance().getSQLiteDatabase().rawQuery("SELECT loan_id FROM user", null);
                        if (cursor != null) {
                            if (cursor.moveToNext()) {
                                String loanId = cursor.getString(cursor.getColumnIndex(DbContract.User.COLUMN_NAME_LOAN_ID));
                                request.setLoanId(loanId);
                            }
                            cursor.close();
                        }

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
                    }
                })
                .compose(new SatcatcheResponseTransformer<UploadRiskAppListRequest, UploadRiskAppList>("uploadRiskAppList"));
    }

    private Flowable<User> queryUser() {
        return Flowable.just("SELECT * FROM user").map(new Function<String, User>() {
            @Override
            public User apply(String sql) throws Exception {
                String token = "";
                String name = "";
                String phone = "";
                Cursor cursor = LoanApplication.getInstance().getSQLiteDatabase().rawQuery(sql, null);
                if (cursor != null) {
                    if (cursor.moveToNext()) {
                        token = cursor.getString(cursor.getColumnIndex(DbContract.User.COLUMN_NAME_TOKEN));
                        name = cursor.getString(cursor.getColumnIndex(DbContract.User.COLUMN_NAME_OCR_NAME));
                        phone = cursor.getString(cursor.getColumnIndex(DbContract.User.COLUMN_NAME_PHONE));
                    }
                    cursor.close();
                }
                if (TextUtils.isEmpty(token)) {
                    throw new LocalException(R.string.error_auth_not_log_in);
                } else {
                    User user = new User();
                    user.setToken(token);
                    user.setName(name);
                    user.setPhone(phone);
                    return user;
                }
            }
        });
    }
}
