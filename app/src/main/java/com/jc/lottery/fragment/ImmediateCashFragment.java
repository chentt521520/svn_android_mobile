package com.jc.lottery.fragment;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.jc.lottery.R;
import com.jc.lottery.base.BaseFragment;
import com.jc.lottery.bean.GameListBean;
import com.jc.lottery.bean.req.pos_GameQueryInfo;
import com.jc.lottery.fragment.betting.G90x5BettingRecordFragment;
import com.jc.lottery.fragment.betting.K3BettingRecordFragment;
import com.jc.lottery.http.MyUrl;
import com.jc.lottery.inter.vStringCallback;
import com.jc.lottery.util.ProgressUtil;
import com.jc.lottery.util.SPUtils;
import com.jc.lottery.util.SPkey;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

@SuppressLint("ValidFragment")
public class ImmediateCashFragment extends BaseFragment implements ViewPager.OnPageChangeListener {

    @BindView(R.id.tv_immediate_one)
    TextView tvImmediateOne;
    @BindView(R.id.tv_immediate_two)
    TextView tvImmediateTwo;
    private ViewPager viewPager;
    private FragmentManager fm;
    private String[] mTitle = {};
    // fragment的集合
    private ArrayList<Fragment> list;
    private MyCashFragmentViewPageAdapter adapter;
    private List<String> nameList = new ArrayList<String>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_immediate_cash;
    }

    @Override
    protected void initView(View view) {
        fm = getChildFragmentManager();
        viewPager = (ViewPager) view.findViewById(R.id.view_pager);
    }

    private void showView(){
        // 将fragment放进集合，并初始化适配器
        list = new ArrayList<Fragment>();
        list.add(new MyImmediateRecordFragment(mTitle[0]));
        list.add(new MyImmediateRecordFragment(mTitle[1]));
        adapter = new MyCashFragmentViewPageAdapter(fm,
                list);
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onPageSelected(int position) {
//                Log.d("","viewpager onPageSelected"+position);
                if (position == 0){
                    tvImmediateOne.setTextColor(Color.rgb(0,165,83));
                    tvImmediateTwo.setTextColor(Color.rgb(0,0,0));
                    tvImmediateOne.setBackgroundResource(R.drawable.cash_top_lly_bg);
                    tvImmediateTwo.setBackground(null);
                }else {
                    tvImmediateOne.setBackground(null);
                    tvImmediateTwo.setBackgroundResource(R.drawable.cash_top_lly_bg);
                    tvImmediateOne.setTextColor(Color.rgb(0,0,0));
                    tvImmediateTwo.setTextColor(Color.rgb(0,165,83));
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public void initData() {
        getGameHttpInfo();
    }


    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
//        if (!hidden) {
//            GetGameList();
//        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    public class MyCashFragmentViewPageAdapter extends FragmentPagerAdapter {

        private List<Fragment> list;

        public MyCashFragmentViewPageAdapter(FragmentManager fm, ArrayList<Fragment> list) {
            super(fm);
            this.list = list;
        }

        @Override
        public Fragment getItem(int arg0) {
            return list.get(arg0);
        }

        @Override
        public int getCount() {
            return list.size();
        }

    }

    private void getGameHttpInfo() {
        ProgressUtil.showProgressDialog(getActivity(), getString(R.string.waitting));
        String account_name = SPUtils.look(getActivity(), SPkey.username);
        String account_password = SPUtils.look(getActivity(), SPkey.password);
        pos_GameQueryInfo pos_gameQueryInfo = new pos_GameQueryInfo(account_name, account_password, "3");
        String s1 = new Gson().toJson(pos_gameQueryInfo);
        OkGo.<String>post(MyUrl.pos_GetGameQueryInfo)
                .upJson(s1)
                .execute(new vStringCallback(getContext()) {

                    @Override
                    public void vOnSuccess(Response<String> response) {
                        ProgressUtil.dismissProgressDialog();
                        try {
                            JSONObject jsonObject = new JSONObject(response.body());
                            String code = jsonObject.getString("code");
                            if (code.equals("00000")) {
                                JSONArray gameList = jsonObject.getJSONArray("gameList");
                                for (int i = 0; i < gameList.length(); i++) {
                                    JSONObject json = gameList.getJSONObject(i);
                                    GameListBean gameListBean = new GameListBean();
                                    gameListBean.setGameName(json.getString("gameName"));
                                    gameListBean.setGameAlias(json.getString("gameAlias"));
                                    gameListBean.setTicketPrice(json.getString("ticketPrice"));
                                    gameListBean.setEnabled(json.getString("enabled"));
                                    if (gameListBean.getEnabled().equals("00")) {
                                        nameList.add(gameListBean.getGameName());
//                                        gameListBeans.add(gameListBean);
                                    }
                                }
                                mTitle = nameList.toArray(new String[nameList.size()]);
                                showView();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        ProgressUtil.dismissProgressDialog();
                    }
                });
    }
}
