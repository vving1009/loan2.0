package com.jiaye.cashloan.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.jiaye.cashloan.R;
import com.jiaye.cashloan.view.about.AboutFragment;
import com.jiaye.cashloan.view.account.AccountFragment;
import com.jiaye.cashloan.view.bankcard.BankCardFragment;
import com.jiaye.cashloan.view.bindbank.BindBankFragment;
import com.jiaye.cashloan.view.cash.CashFragment;
import com.jiaye.cashloan.view.certification.CertificationFragment;
import com.jiaye.cashloan.view.company.CompanyFragment;
import com.jiaye.cashloan.view.file.FileFragment;
import com.jiaye.cashloan.view.forgetpassword.ForgetPasswordFragment;
import com.jiaye.cashloan.view.id.IDFragment;
import com.jiaye.cashloan.view.info.InfoFragment;
import com.jiaye.cashloan.view.login.LoginFragment;
import com.jiaye.cashloan.view.phone.PhoneFragment;
import com.jiaye.cashloan.view.plan.PlanFragment;
import com.jiaye.cashloan.view.register.RegisterFragment;
import com.jiaye.cashloan.view.search.SearchFragment;
import com.jiaye.cashloan.view.sign.SignFragment;
import com.jiaye.cashloan.view.support.SupportFragment;
import com.jiaye.cashloan.view.taobao.TaoBaoFragment;
import com.jiaye.cashloan.view.vehicle.VehicleFragment;

/**
 * FunctionActivity
 *
 * @author 贾博瑄
 */
public class FunctionActivity extends AppCompatActivity {

    private BaseFragment mFragment;

    public static void function(Activity activity, String function) {
        Intent intent = new Intent(activity, FunctionActivity.class);
        intent.putExtra("function", function);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity);
        String function = getIntent().getStringExtra("function");
        if (savedInstanceState == null) {
            switch (function) {
                case "Login":
                    // 登录
                    mFragment = LoginFragment.newInstance();
                    break;
                case "Register":
                    // 注册
                    mFragment = RegisterFragment.newInstance();
                    break;
                case "ForgetPassword":
                    // 忘记密码
                    mFragment = ForgetPasswordFragment.newInstance();
                    break;
                case "Certification":
                    // 认证中心
                    mFragment = CertificationFragment.newInstance();
                    break;
                case "ID":
                    // 身份认证
                    mFragment = IDFragment.newInstance();
                    break;
                case "Info":
                    // 个人资料
                    mFragment = InfoFragment.newInstance();
                    break;
                case "Phone":
                    // 手机运营商
                    mFragment = PhoneFragment.newInstance();
                    break;
                case "Vehicle":
                    // 车辆证件
                    mFragment = VehicleFragment.newInstance();
                    break;
                case "Taobao":
                    // 淘宝
                    mFragment = TaoBaoFragment.newInstance();
                    break;
                case "File":
                    // 进件资料
                    mFragment = FileFragment.newInstance();
                    break;
                case "Sign":
                    // 电子签章
                    mFragment = SignFragment.newInstance();
                    break;
                case "Plan":
                    // 还款计划
                    mFragment = PlanFragment.newInstance();
                    break;
                case "About":
                    // 关于我们
                    mFragment = AboutFragment.newInstance();
                    break;
                case "Account":
                    //账户管理
                    mFragment = AccountFragment.newInstance();
                    break;
                case "BankCard":
                    // 银行卡管理
                    mFragment = BankCardFragment.newInstance();
                    break;
                case "BindBank":
                    // 绑定银行卡
                    mFragment = BindBankFragment.newInstance();
                    break;
                case "Support":
                    // 支持的银行
                    mFragment = SupportFragment.newInstance();
                    break;
                case "Cash":
                    // 提现
                    mFragment = CashFragment.newInstance(getIntent().getParcelableExtra("balance"));
                    break;
                case "Company":
                    // 公司业务员搜索
                    mFragment = CompanyFragment.newInstance(getIntent().getStringExtra("city"));
                    break;
                case "Search":
                    // 公司业务员搜索
                    mFragment = SearchFragment.newInstance();
                    break;
            }
        } else {
            mFragment = (BaseFragment) getSupportFragmentManager().getFragment(savedInstanceState, "fragment");
        }
        getSupportFragmentManager().beginTransaction().replace(R.id.layout_content, mFragment).commit();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mFragment != null) {
            getSupportFragmentManager().putFragment(outState, "fragment", mFragment);
        }
    }

    @Override
    public void onBackPressed() {
        if (mFragment != null && mFragment.onBackPressed()) {
            return;
        }
        super.onBackPressed();
    }
}
