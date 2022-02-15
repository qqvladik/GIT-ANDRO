package corp.petro.pz1_tpues;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.text.NumberFormat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_one);

        /*Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);*/


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        /*for (int i=0; i<4; i++) {
            menu.add(1, i, i, TipMontagh.getMontaghs().get(i).toString());//обращение к члену списка и его преобразование в стринг
            //menu.add(1, i, i, TipMontagh.montagh[i].toString());
        }*/

        getMenuInflater().inflate(R.menu.menu_main, menu);

        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        /*for (int i=0; i<4; i++) {
            if (item.getItemId()==i){
                //PZ1_fragment fr = new PZ1_fragment();
                PZ1_fragment.editTexts[3].setText(TipMontagh.getMontaghs().get(i).getValue());
            }
        }*/

        switch (item.getItemId()) {
            case R.id.itemTheory:
                /*Toast.makeText(this, "теории пока нету", Toast.LENGTH_SHORT).show();*/
                Intent intent = new Intent(this, TheoryActivity.class);
                startActivity(intent);
                break;
            case R.id.itemInfo:
                Intent intent2 = new Intent(this, InfoActivity.class);
                startActivity(intent2);
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onShow(View view) {
        double k = 0, k1 = 0, k2 = 0;
        double[] K = new double[7];
        double[] N = new double[11];
        final double[] F = {1, 1, 0.8, 0.5, 0.3, 0.2, 0.1};
        String[] str = new String[11];
        TextView[] textViews = new TextView[7];
        EditText[] editTexts = new EditText[11];
        NumberFormat numberFormat = new DecimalFormat("##.###");//создаем такой формат чтобы потом преобразовывать double в строку и выводить три числа
        // после запятой если они есть, а если число целое, то выводит просто число без запятой
        TextView koef;


        //обнулили числа
        k = 0;
        k1 = 0;
        k2 = 0;
        //определили вью элементы
        editTexts[0] = (EditText) findViewById(R.id.editText);
        editTexts[1] = (EditText) findViewById(R.id.editText2);
        editTexts[2] = (EditText) findViewById(R.id.editText3);
        editTexts[3] = (EditText) findViewById(R.id.editText4);
        editTexts[4] = (EditText) findViewById(R.id.editText5);
        editTexts[5] = (EditText) findViewById(R.id.editText6);
        editTexts[6] = (EditText) findViewById(R.id.editText7);
        editTexts[7] = (EditText) findViewById(R.id.editText8);
        editTexts[8] = (EditText) findViewById(R.id.editText9);
        editTexts[9] = (EditText) findViewById(R.id.editText10);
        editTexts[10] = (EditText) findViewById(R.id.editText11);

        textViews[0] = (TextView) findViewById(R.id.kap);
        textViews[1] = (TextView) findViewById(R.id.kau);
        textViews[2] = (TextView) findViewById(R.id.kt);
        textViews[3] = (TextView) findViewById(R.id.kakn);
        textViews[4] = (TextView) findViewById(R.id.kpow);
        textViews[5] = (TextView) findViewById(R.id.ktp);
        textViews[6] = (TextView) findViewById(R.id.kspd);

        koef = (TextView) findViewById(R.id.textView27);

        try {
            //сосчитываем введенные данные
            for (int j = 0; j < 11; j++) {
                str[j] = editTexts[j].getText().toString();
                N[j] = Double.parseDouble(str[j]);
            }
            //обрабатываем искючение если делим на ноль, а точнее сразу находим числа равные нулю, которые находятся в делителе
            if (N[0] == 0 || N[3] == 0 || N[4] == 0 || N[8] == 0 || N[9] == 0) {
                Toast.makeText(this, "На ноль делить нельзя!", Toast.LENGTH_SHORT).show();
                for (int i = 0; i < 7; i++) {
                    textViews[i].setVisibility(View.INVISIBLE);
                }
                koef.setVisibility(View.INVISIBLE);
                return;
            }
            //производим расчет коэффициентов
            K[0] = N[1] / N[0];
            K[1] = N[2] / N[0];
            K[2] = 1 / N[3];
            K[3] = (N[5] + N[6]) / N[4];
            K[4] = 1 - N[7] / N[0];
            K[5] = N[10] / N[8];
            K[6] = 1 / N[9];
            //выводим коэффициенты на экран
            for (int i = 0; i < 7; i++) {
                textViews[i].setText(numberFormat.format(K[i]));
                textViews[i].setVisibility(View.VISIBLE);
                k1 = k1 + K[i] * F[i];
                k2 = k2 + F[i];
            }
            //расчет главного коэффициенты и его вывод на экран
            k = k1 / k2;
            koef.setText(numberFormat.format(k));
            koef.setVisibility(View.VISIBLE);
            //обработка исключения если какое-то поле пустое
        } catch (Exception e) {

            Toast.makeText(this, "Заполните все поля!", Toast.LENGTH_SHORT).show();
            for (int i = 0; i < 7; i++) {
                textViews[i].setVisibility(View.INVISIBLE);
            }
            koef.setVisibility(View.INVISIBLE);
        }

    }
}
