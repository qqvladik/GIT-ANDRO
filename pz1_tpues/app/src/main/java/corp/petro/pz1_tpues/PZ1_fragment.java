package corp.petro.pz1_tpues;


import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.text.NumberFormat;


/**
 * A simple {@link Fragment} subclass.
 */
public class PZ1_fragment extends Fragment implements View.OnClickListener {

    static EditText[] editTexts = new EditText[11];
    TextView[] textViews = new TextView[7];
    TextView koef;
    TableLayout table11;
    LinearLayout linear11;
    boolean sostButton=false;//указывает на бывшее состояние текствью: выведены ли рассчитанные коэффициенты на экран или нет

    public PZ1_fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View layoutInflater = inflater.inflate(R.layout.fragment_pz1, container, false);

        table11 = (TableLayout) layoutInflater.findViewById(R.id.table11);
        linear11 = (LinearLayout) layoutInflater.findViewById(R.id.linear11);

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            twoColumns();
        }else{
            oneColumn();
        }

        editTexts[0] = (EditText) layoutInflater.findViewById(R.id.editText);
        editTexts[1] = (EditText) layoutInflater.findViewById(R.id.editText2);
        editTexts[2] = (EditText) layoutInflater.findViewById(R.id.editText3);
        editTexts[3] = (EditText) layoutInflater.findViewById(R.id.editText4);
        //registerForContextMenu(editTexts[3]);
        editTexts[3].setOnCreateContextMenuListener(this);//можно и так
        editTexts[4] = (EditText) layoutInflater.findViewById(R.id.editText5);
        editTexts[5] = (EditText) layoutInflater.findViewById(R.id.editText6);
        editTexts[6] = (EditText) layoutInflater.findViewById(R.id.editText7);
        editTexts[7] = (EditText) layoutInflater.findViewById(R.id.editText8);
        editTexts[8] = (EditText) layoutInflater.findViewById(R.id.editText9);
        editTexts[9] = (EditText) layoutInflater.findViewById(R.id.editText10);
        editTexts[10] = (EditText) layoutInflater.findViewById(R.id.editText11);

        textViews[0] = (TextView) layoutInflater.findViewById(R.id.kap);
        textViews[1] = (TextView) layoutInflater.findViewById(R.id.kau);
        textViews[2] = (TextView) layoutInflater.findViewById(R.id.kt);
        textViews[3] = (TextView) layoutInflater.findViewById(R.id.kakn);
        textViews[4] = (TextView) layoutInflater.findViewById(R.id.kpow);
        textViews[5] = (TextView) layoutInflater.findViewById(R.id.ktp);
        textViews[6] = (TextView) layoutInflater.findViewById(R.id.kspd);

        koef = (TextView) layoutInflater.findViewById(R.id.textView27);

        koef.setOnClickListener(this);

        if (savedInstanceState!=null&&savedInstanceState.getBoolean("sostButton")){
            sostButton=true;
        }
        return layoutInflater;
    }

    @Override
    public void onStart() {
        super.onStart();
        if(sostButton){
            onClick(koef);
        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        for (int i = 0; i < 4; i++) {
            menu.add(1, i, i, TipMontagh.getMontaghs().get(i).toString());//обращение к члену списка и его преобразование в стринг
            //menu.add(1, i, i, TipMontagh.montagh[i].toString());
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        for (int i = 0; i < 4; i++) {
            if (item.getItemId() == i) {
                editTexts[3].setText(TipMontagh.getMontaghs().get(i).getValue());
            }
        }
        return super.onContextItemSelected(item);
    }

    @Override
    public void onClick(View v) {

        View view = getView();
        //////////////////
        double k = 0, k1 = 0, k2 = 0;
        double[] K = new double[7];
        double[] N = new double[11];
        final double[] F = {1, 1, 0.8, 0.5, 0.3, 0.2, 0.1};
        String[] str = new String[11];

        NumberFormat numberFormat = new DecimalFormat("##.##");//создаем такой формат чтобы потом преобразовывать double в строку и выводить три числа
        // после запятой если они есть, а если число целое, то выводит просто число без запятой


        //обнулили числа
        k = 0;
        k1 = 0;
        k2 = 0;

        //определили вью элементы
        try {
            //сосчитываем введенные данные
            for (int j = 0; j < 11; j++) {
                str[j] = editTexts[j].getText().toString();
                N[j] = Double.parseDouble(str[j]);
            }

            //обрабатываем искючение если делим на ноль, а точнее сразу находим числа равные нулю, которые находятся в делителе
            if (N[0] == 0 || N[3] == 0 || N[4] == 0 || N[8] == 0 || N[9] == 0) {

                unShow("На ноль делить нельзя!");
                return;//прекращаем выполнение функции
                /*Toast.makeText(getContext(), "На ноль делить нельзя!", Toast.LENGTH_SHORT).show();
                for (int i = 0; i < 7; i++) {
                    textViews[i].setVisibility(View.INVISIBLE);
                }
                //koef.setVisibility(View.INVISIBLE);
                koef.setText("K=");
                return;*/

            }/*else if(N[7]>N[0]){

                unShow("Нт не может быть больше Нэк!");
                return;
            }*/

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
            koef.setText("K=" + numberFormat.format(k));
            sostButton=true;
            //koef.setVisibility(View.VISIBLE);
            //обработка исключения если какое-то поле пустое
        } catch (Exception e) {

            unShow("Заполните все поля!");
            /*Toast.makeText(getContext(), "Заполните все поля!", Toast.LENGTH_SHORT).show();
            for (int i = 0; i < 7; i++) {
                textViews[i].setVisibility(View.INVISIBLE);
            }
            //koef.setVisibility(View.INVISIBLE);
            koef.setText("K=");*/
        }

    }

    private void unShow(String message) {
        Toast.makeText(/*this*/getContext(), message, Toast.LENGTH_SHORT).show();
        for (int i = 0; i < 7; i++) {
            textViews[i].setVisibility(View.INVISIBLE);
        }
        //koef.setVisibility(View.INVISIBLE);
        koef.setText("K=");
        sostButton=false;
    }

    public void oneColumn(){
        linear11.setOrientation(LinearLayout.VERTICAL);
        linear11.setDividerDrawable(null);//убрали разделитель
        table11.setColumnStretchable(0, true);//устанавливаем растяжение для первой колонки
    }

    public void twoColumns(){
        linear11.setOrientation(LinearLayout.HORIZONTAL);
        linear11.setDividerDrawable(getResources().getDrawable(R.drawable.separator));//программно установили разделитель
        linear11.setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);//разделитель стоит между вью
        table11.setColumnStretchable(0, false);//убираем растяжение для первой колонки
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean("sostButton",sostButton);
    }
}
