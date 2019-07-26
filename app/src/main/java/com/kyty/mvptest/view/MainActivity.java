package com.kyty.mvptest.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;
import com.kyty.mvptest.R;
import com.kyty.mvptest.mvp.BaseFragment;
import com.kyty.mvptest.view.fragment.HomeFragment;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.enums.PopupAnimation;
import com.lxj.xpopup.impl.LoadingPopupView;


public class MainActivity extends AppCompatActivity{

    private DrawerLayout drawerLayout;
    private NavigationView menuView;
    private MyDrawerListener drawerListener;
    private HomeFragment homeFragment;
    private LoadingPopupView loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initListener();
        if (homeFragment==null){
            homeFragment = new HomeFragment();
        }
        addFragment(homeFragment,"home");
    }

    private void initListener() {
        drawerListener = new MyDrawerListener();
        drawerLayout.addDrawerListener(drawerListener);
        menuView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                menuItem.setCheckable(true);//设置选项可选
                menuItem.setChecked(true);//设置选型被选中
                switch (menuItem.getItemId()){
                    case R.id.left_home:

                        break;
                    case R.id.left_search:

                        break;
                    case R.id.left_model:

                        break;
                }
                drawerLayout.closeDrawer(Gravity.LEFT);
                return true;
            }
        });
    }

    private void initView() {
        drawerLayout = findViewById(R.id.drawerLayout);
        menuView = findViewById(R.id.menuView);
        menuView.setCheckedItem(R.id.left_home);
        loading = new XPopup.Builder(this)
                .dismissOnBackPressed(false)
                .dismissOnTouchOutside(false)
                .popupAnimation(PopupAnimation.ScaleAlphaFromCenter)
                .asLoading("正在加载中");

    }

    private void addFragment(BaseFragment fragment, String name){
        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.fragment,fragment,name).commit();
    }


    public void ShowLoadding() {
        loading.show();
    }

    public void HideLoadding() {
        loading.dismiss();
    }


    class MyDrawerListener implements DrawerLayout.DrawerListener{

        @Override
        public void onDrawerSlide(@NonNull View drawerView, float slideOffset) {
            //drawerView 为菜单view
            //可以重新侧滑方法,该方法实现侧滑动画,整个布局移动效果
            //获取mDrawerLayout中的第一个子布局，也就是布局中的RelativeLayout
            //获取抽屉的view
            View mContent = ((ViewGroup)drawerView.getParent()).getChildAt(0);
            float scale = 1 - slideOffset;
            float endScale = 0.8f + scale * 0.2f;
            float startScale = 1 - 0.3f * scale;

            //设置左边菜单滑动后的占据屏幕大小
            drawerView.setScaleX(startScale);
            drawerView.setScaleY(startScale);
            //设置菜单透明度
            drawerView.setAlpha(0.6f + 0.4f * (1 - scale));

            //设置内容界面水平和垂直方向偏转量
            //在滑动时内容界面的宽度为 屏幕宽度减去菜单界面所占宽度
            mContent.setTranslationX(drawerView.getMeasuredWidth() * (1 - scale));
            //设置内容界面操作无效（比如有button就会点击无效）
            mContent.invalidate();
            //设置右边菜单滑动后的占据屏幕大小
            mContent.setScaleX(endScale);
            mContent.setScaleY(endScale);
        }

        @Override
        public void onDrawerOpened(@NonNull View drawerView) {

        }

        @Override
        public void onDrawerClosed(@NonNull View drawerView) {

        }

        @Override
        public void onDrawerStateChanged(int newState) {

        }
    }

}
