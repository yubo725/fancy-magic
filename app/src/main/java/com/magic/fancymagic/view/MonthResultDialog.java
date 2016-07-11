package com.magic.fancymagic.view;

import android.app.Dialog;
import android.content.Context;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.magic.fancymagic.BaseApplication;
import com.magic.fancymagic.R;
import com.magic.fancymagic.adapter.MonthResultGridAdapter;
import com.magic.fancymagic.bean.MonthBean;

import net.tsz.afinal.FinalActivity;
import net.tsz.afinal.annotation.view.ViewInject;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by yubo on 2016/7/11.
 */
public class MonthResultDialog extends Dialog {

    private View rootView;

    @ViewInject(id = R.id.hint_text)
    TextView hintTv;

    @ViewInject(id = R.id.month_result_grid_view)
    GridView monthResultGridView;

    private MonthBean selectedMonthBean;
    private List<MonthBean> allMonthList;
    private MonthResultGridAdapter monthResultGridAdapter;

    private Random random;
    private List<MonthBean> randomList;

    public MonthResultDialog(Context context, int themeResId) {
        super(context, themeResId);
        init();
    }

    private void init() {
        rootView = View.inflate(getContext(), R.layout.month_result_dialog, null);
        FinalActivity.initInjectedView(this, rootView);
        hintTv.setText(Html.fromHtml("把下面的12个月份按照<font color='red'>从上到下，从左到右</font>的顺序读给魔术听吧，颜色不一样的那个是你选择的，记住读到你选择的那个月份时不要紧张哦，摇一摇可以打乱顺序~"));
        hintTv.setTypeface(BaseApplication.getInstance().getTypeface());

        random = new Random();
        randomList = new ArrayList<>();
    }

    public void setSelectedMonth(MonthBean bean) {
        this.selectedMonthBean = bean;
    }

    public void setAllMonths(List<MonthBean> list) {
        this.allMonthList = list;
    }

    public void showDialog() {
        setContentView(rootView);
        show();
    }

    //重新排序月份
    public void reorderMonth() {
        randomList.clear();
        MonthBean firstMonthBean = allMonthList.get(random.nextInt(12));
        Log.e("yubo", "firstMonth: " + firstMonthBean.getMonthName());
        while(firstMonthBean.getIndex() == selectedMonthBean.getIndex() || firstMonthBean.getIndex() == 1) {
            firstMonthBean = allMonthList.get(random.nextInt(12));
        }
        int selectedMonthPos = firstMonthBean.getIndex() - 1;
        randomList.add(firstMonthBean);
        while(randomList.size() != 12) {
            if(randomList.size() == selectedMonthPos) {
                selectedMonthBean.setSelected(true);
                randomList.add(selectedMonthBean);
            }else {
                MonthBean bean = allMonthList.get(random.nextInt(12));
                if (!randomList.contains(bean) && bean.getIndex() != selectedMonthBean.getIndex()) {
                    bean.setSelected(false);
                    randomList.add(bean);
                }
            }
        }



        if(monthResultGridAdapter == null) {
            monthResultGridAdapter = new MonthResultGridAdapter(getContext(), randomList, R.layout.month_result_grid_item);
            monthResultGridView.setAdapter(monthResultGridAdapter);
        }else {
            monthResultGridAdapter.setData(randomList);
            monthResultGridAdapter.notifyDataSetChanged();
        }
    }
}
