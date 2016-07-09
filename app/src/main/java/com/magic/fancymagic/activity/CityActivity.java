package com.magic.fancymagic.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.magic.fancymagic.BaseApplication;
import com.magic.fancymagic.R;
import com.magic.fancymagic.adapter.CityGridAdapter;
import com.magic.fancymagic.bean.CityBean;
import com.magic.fancymagic.listener.ShakeListener;
import com.magic.fancymagic.utils.PhoneUtils;
import com.magic.fancymagic.view.CityResultDialog;
import com.magic.fancymagic.view.LoadingDialog;
import com.magic.fancymagic.view.TitleView;

import net.tsz.afinal.annotation.view.ViewInject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.ref.WeakReference;
import java.security.spec.ECField;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 城市读心术
 */
public class CityActivity extends BaseActivity {

    private static final int MSG_DATA_LOADED = 1;
    private static final char[] STRAIGHT = {'A', 'E', 'F', 'H', 'I', 'K', 'L', 'M', 'N', 'T', 'V', 'W', 'X', 'Y', 'Z'};

    enum CityType {
        STRAIGHT, CURVED
    }

    @ViewInject(id = R.id.title_view)
    TitleView titleView;

    @ViewInject(id = R.id.city_hint_tv)
    TextView cityHintTv;

    @ViewInject(id = R.id.city_grid_view)
    GridView gridView;

    private CityGridAdapter cityGridAdapter;
    private List<CityBean> gridDataList;
    private List<CityBean> cityList;
    private List<CityBean> straightCityList;
    private List<CityBean> curvedCityList;
    private List<Integer> randomList;

    private MyHandler handler;

    private LoadingDialog loadingDialog;
    private Random random;

    private ShakeListener shakeListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city);
        initView();
    }

    private void initView() {
        titleView.setTitle("城市读心术");
        cityHintTv.setTypeface(BaseApplication.getInstance().getTypeface());
        cityList = new ArrayList<>();
        gridDataList = new ArrayList<>();
        randomList = new ArrayList<>();
        straightCityList = new ArrayList<>();
        curvedCityList = new ArrayList<>();

        loadingDialog = new LoadingDialog(this, R.style.loading_dialog);
        loadingDialog.setLoadingMsg("正在加载数据");
        loadingDialog.showLoadingDialog();

        handler = new MyHandler(this);
        new Thread(new Runnable() {
            @Override
            public void run() {
                loadCityData();
            }
        }).start();

        shakeListener = new ShakeListener(this);
        shakeListener.registerListener();
        shakeListener.setOnShakeListener(new ShakeListener.OnShakeListener() {
            @Override
            public void onShake() {
                Log.e("yubo", "onShake...");
                PhoneUtils.vibrateOnce(CityActivity.this, 200);
                showRandomCities();
            }
        });
    }

    //加载城市数据，在子线程中执行
    private void loadCityData() {
        InputStream is = getResources().openRawResource(R.raw.city_data);
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        String line;
        StringBuilder sb = new StringBuilder();
        try {
            while((line = br.readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                br.close();
                is.close();
            } catch (IOException e) {
            }
        }
        String json = sb.toString();
        cityList = JSON.parseArray(json, CityBean.class);
        classifiedCities();
        handler.obtainMessage().sendToTarget();
    }

    //显示随机城市
    private void showRandomCities() {
        if(cityList == null || cityList.size() == 0) {
            showToast("找不到城市数据");
            return ;
        }
        if(random == null) {
            random = new Random();
        }
        int cityCount = cityList.size();
        gridDataList.clear();
        randomList.clear();
        int randomInt;
        for(int i = 0; i < 20; i++) {
            randomInt = random.nextInt(cityCount);
            while(randomList.contains(randomInt)) {
                randomInt = random.nextInt(cityCount);
            }
            gridDataList.add(cityList.get(randomInt));
            randomList.add(randomInt);
        }
        if(cityGridAdapter == null) {
            cityGridAdapter = new CityGridAdapter(this, gridDataList, R.layout.city_grid_item);
            gridView.setAdapter(cityGridAdapter);
            cityGridAdapter.setOnCitySelectedListener(onCitySelectedListener);
        }else {
            cityGridAdapter.setData(gridDataList);
            cityGridAdapter.notifyDataSetChanged();
        }
    }

    //某个城市被选中后触发这里的操作
    private CityGridAdapter.OnCitySelectedListener onCitySelectedListener = new CityGridAdapter.OnCitySelectedListener() {
        @Override
        public void onCitySelected(CityBean bean) {
            List<CityBean> list = getConverseCities(resolveCityType(bean));
            StringBuilder sb = new StringBuilder();
            for(CityBean item : list) {
                sb.append(item.getCityName() + ", ");
            }
            String converseCites = sb.toString().substring(0, sb.toString().length() - 2);
            Log.e("yubo", "selected city: " + bean.getCityName() + "\n converse cities: " + converseCites);
            new CityResultDialog(CityActivity.this, R.style.loading_dialog).showDialog();
        }
    };

    //获取8个和type类型相反的城市
    private List<CityBean> getConverseCities(CityType type) {
        List<CityBean> list = new ArrayList<>();
        randomList.clear();
        List<CityBean> selectList = new ArrayList<>();
        switch(type) {
            case STRAIGHT:
                selectList = curvedCityList;
                break;
            case CURVED:
                selectList = straightCityList;
                break;
        }
        int randomInt;
        int count = selectList.size();
        for(int i = 0; i < 8; i++) {
            randomInt = random.nextInt(count);
            while(randomList.contains(randomInt)) {
                randomInt = random.nextInt(count);
            }
            randomList.add(randomInt);
            list.add(selectList.get(randomInt));
        }
        return list;
    }

    class MyHandler extends Handler {
        private Activity act;
        private WeakReference<Activity> weakRef;

        public MyHandler(Activity act) {
            weakRef = new WeakReference<>(act);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            loadingDialog.dismiss();
            if(weakRef.get() != null) {
                showRandomCities();
            }
        }
    }

    //判断城市属于哪种类型
    private CityType resolveCityType(CityBean bean) {
        char firstLetter = bean.getFirstLetter();
        for(int i = 0; i < STRAIGHT.length; i++) {
            if(firstLetter == STRAIGHT[i]) {
                return CityType.STRAIGHT;
            }
        }
        return CityType.CURVED;
    }

    //城市分类
    private void classifiedCities() {
        if(cityList != null && cityList.size() > 0) {
            for(CityBean bean : cityList) {
                if(resolveCityType(bean) == CityType.STRAIGHT) {
                    straightCityList.add(bean);
                }else {
                    curvedCityList.add(bean);
                }
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        shakeListener.unregisterListener();
    }
}
