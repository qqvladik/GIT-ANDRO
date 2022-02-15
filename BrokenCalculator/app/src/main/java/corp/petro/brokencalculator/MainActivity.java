package corp.petro.brokencalculator;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class MainActivity extends AppCompatActivity implements HelpDialogFragment.Listener {

    HelpDialogFragment helpDialogFragment;
    Menu menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);//отключаем темную тему

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //CalculatorFragment calculatorFragment = (CalculatorFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_calculator);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        this.menu=menu;
        helpDialogFragment = new HelpDialogFragment();//нужно чтобы диалогфрагмент создался один раз, чтобы сохранялись его данные
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.item_about_us:
                Intent intent = new Intent(this, InfoActivity.class);
                startActivity(intent);
                break;
            case R.id.item_info:
                FragmentManager fragmentManager = getSupportFragmentManager();
                helpDialogFragment.show(fragmentManager, null);
                break;
            case R.id.item_broke:
                item.setChecked(!item.isChecked());//обрабатываем нажатие на галочку в менюItem, чтобы она менялась сразу после нажатия
                helpDialogFragment.setBrokeIsChecked(item.isChecked());
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBrokeCheckClicked(boolean brokeIsChecked) {
        menu.findItem(R.id.item_broke).setChecked(brokeIsChecked);
    }

}