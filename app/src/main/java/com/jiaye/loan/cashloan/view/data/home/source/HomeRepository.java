package com.jiaye.loan.cashloan.view.data.home.source;

import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.AbsoluteSizeSpan;

import com.jiaye.loan.cashloan.R;
import com.jiaye.loan.cashloan.view.data.home.Card;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

/**
 * HomeRepository
 *
 * @author 贾博瑄
 */

public class HomeRepository implements HomeDataSource {

    @Override
    public void open() {

    }

    @Override
    public void close() {

    }

    @Override
    public Observable<List<Card>> getCardList() {
        List<Card> list = new ArrayList<>();
        Card card1 = new Card();
        card1.setLabelResId(R.drawable.home_ic_label_blue);
        card1.setColor(R.color.color_blue);
        String amount1 = "最高 ¥ 2000.00";
        SpannableString sp1 = new SpannableString(amount1);
        sp1.setSpan(new AbsoluteSizeSpan(12, true), 0, 4, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        sp1.setSpan(new AbsoluteSizeSpan(30, true), 5, amount1.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        card1.setAmount(sp1);
        card1.setDeadline("1月");
        card1.setPaymentMethod("到期还本付息");
        card1.setType(1);
        card1.setOpen(true);
        Card card2 = new Card();
        card2.setLabelResId(R.drawable.home_ic_label_gray);
        card2.setColor(R.color.color_gray_dark);
        String amount2 = "最高 ¥ 5000.00";
        SpannableString sp2 = new SpannableString(amount2);
        sp2.setSpan(new AbsoluteSizeSpan(12, true), 0, 4, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        sp2.setSpan(new AbsoluteSizeSpan(30, true), 5, amount2.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        card2.setAmount(sp2);
        card2.setDeadline("1月");
        card2.setPaymentMethod("到期还本付息");
        card2.setType(2);
        card2.setOpen(false);
        list.add(card1);
        list.add(card2);
        return Observable.just(list);
    }
}
