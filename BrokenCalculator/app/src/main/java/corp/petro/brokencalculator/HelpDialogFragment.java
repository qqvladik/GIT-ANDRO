package corp.petro.brokencalculator;

import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.fragment.app.FragmentManager;

public class HelpDialogFragment extends AppCompatDialogFragment {

    private final String titleDialog = "This app works like a simple calculator. It also has a function of smart making mistakes";
    private final String[] items = {"Broke"};
    private boolean[] checkItemsId = {false};
    private boolean[] remembItemsId = new boolean[1];

    private Listener listener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        remembItemsId[0] = checkItemsId[0];//если присваивать массив в массив, то работает как с объектами, присваивается ссылка, а не значение
        TextView textTitle = new TextView(getContext());
        textTitle.setText(titleDialog);
        textTitle.setTextSize(18);
        //textTitle.setTypeface(null, Typeface.BOLD);
        textTitle.setGravity(Gravity.CENTER);
        textTitle.setPadding(16,16,16,0);

        AlertDialog.Builder alBuilder = new AlertDialog.Builder(getActivity(), R.style.MyDialog);
        alBuilder.setCustomTitle(textTitle);
                /*setTitle("Данное приложение работает как обычный калькулятор. Так же" +
                " в нем присутствует функция умного присваивания погрешности");*/
                /*.setMessage("Данное приложение работает как обычный калькулятор. Так же" +
                    "в нем присутствует функция умного присваивания погрешности");*////// setMessage не работает
                                                                // одновременно с setMultiChoice
        alBuilder.setMultiChoiceItems(items, checkItemsId, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                checkItemsId[0]=isChecked;//which=0
            }
        })
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        listener = (Listener) getContext();
                        listener.onBrokeCheckClicked(checkItemsId[0]);
                    }
                })
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        checkItemsId[0] = remembItemsId[0];
                        //dialog.cancel();
                    }
                });
        return alBuilder.create();
    }

    public boolean getBrokeIsChecked(){
        return checkItemsId[0];
    }

    public void setBrokeIsChecked(boolean b){//этот метод нужен чтобы из меню изменить в DialogFragment значение галочки
        checkItemsId[0]=b;
    }

    interface Listener{//здесь мы создаем интерфейс слушателя, определяем в этом же фрагменте объект слушателя, далее передаем
        // аргумент в метод слушателя при нажатии кнопки ОК. Активность имплементируем от этого интерфейса и описываем тело метода
        void onBrokeCheckClicked(boolean brokeIsChecked);
    }
}
