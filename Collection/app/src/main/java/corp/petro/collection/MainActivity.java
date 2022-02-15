 package corp.petro.collection;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {

    public static final String APP_PREFERENCES = "mysettings";//название файла настроек
    public static final String APP_PREFERENCES_POSITION = "counter";//задали название сохраняемой переменной

    private SharedPreferences mSettings;//Создаём переменную, представляющую экземпляр класса SharedPreferences, который отвечает за работу с настройками

    ViewPager pager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pager = (ViewPager) findViewById(R.id.pager);//нашло вьюпэйджер
        pager.setAdapter(new WorkoutPagerAdapter(getSupportFragmentManager()));//установили адаптер
        /*pager.setCurrentItem(1);//открываем определенную вкладку*/
        mSettings = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);//нащли тулбар
        setSupportActionBar(toolbar);//установили тулбар как экшнбар

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);//нашли таблаяут
        tabLayout.setupWithViewPager(pager);//связали таблаяут с вьюпэйджером
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mSettings.contains(APP_PREFERENCES_POSITION)) {
            pager.setCurrentItem(mSettings.getInt(APP_PREFERENCES_POSITION, 0));//во втором параметре нужно поставить 0
        }
    }

    public class WorkoutPagerAdapter extends FragmentPagerAdapter {//нужно реализовать адаптер для ViewPager
        public WorkoutPagerAdapter(@NonNull FragmentManager fm) {//должен быть такой конструктор
            super(fm);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {//возвращает нужные фрагменты для определенной вкладки
            return /*FirstDayFragment.newInstance(position)*/ new FirstDayFragment(position);
            //return null;
        }

        @Override
        public int getCount() {//количество вкладок
            return 3;
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return WorkoutEkto.workouts[position].getTabName();
            //return null;//здесь нужно возвращать null
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences.Editor editor = mSettings.edit();//Чтобы внести изменения в настройки, нужно использовать класс SharedPreferences.Editor.
                                                    // Получить объект Editor можно через вызов метода edit() объекта SharedPreferences.
        editor.putInt(APP_PREFERENCES_POSITION, pager.getCurrentItem());//сохраняем позицию вкладки
        editor.apply();//После того, как вы внесли все необходимые изменения, вызовите метод apply(), чтобы изменения вступили в силу.


    }

    /*public void onClick(View view){
        Toast.makeText(this, "fdknbsp", Toast.LENGTH_SHORT).show();
    }*/
}