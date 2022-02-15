package corp.petro.brokencalculator;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;


public class CalculatorFragment extends Fragment implements View.OnClickListener {

    public final static String EXTRA_OPERATOR = "operator";
    public final static String EXTRA_NUM_FIRST = "num first";
    public final static String EXTRA_TEXT_INPUTT = "text inputt";
    public final static String EXTRA_TEXT_FIELD = "text field";


    Button[] buttonsNumbersGlobal;
    Button buttonMod, buttonPower, buttonClearAll, buttonIncrease, buttonSeparate, buttonSum,
            buttonPoint, buttonDifference, buttonIs;
    ImageButton buttonClear;                              // кнопки различных операций

    EditText editNumsField; // поле, куда вводятся числа
    Editable editableText;//получаем класс редактируемый текст из поля для его дальнейшей обработки
    TextView textInputtedData;//текствью, отображающий введенные данные
    String textInputt;//текст из текствью
    String textField;//берет значение из editableText
    String remembOper; //будет использоваться для запоминания и определения последней выбранной операции,
    // чтобы потом использовать это при расчете значения после нажатия кнопки =
    double remembNumFirst; // запоминает первое введенное число

    DecimalFormat df15 = new DecimalFormat("#.###############");
    DecimalFormat df6 = new DecimalFormat("#.######");//класс для форматирования любого числа в Java, решетки заданы так,
    // чтобы указывать не более 6 знаков после запятой, а если будут нули в конце, то они будут опускаться

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View layoutInflater = inflater.inflate(R.layout.fragment_calculator, container, false);
        Button[] buttonsNumbers = {(Button) layoutInflater.findViewById(R.id.button0),
                (Button) layoutInflater.findViewById(R.id.button1),
                (Button) layoutInflater.findViewById(R.id.button2),
                (Button) layoutInflater.findViewById(R.id.button3),
                (Button) layoutInflater.findViewById(R.id.button4),
                (Button) layoutInflater.findViewById(R.id.button5),
                (Button) layoutInflater.findViewById(R.id.button6),
                (Button) layoutInflater.findViewById(R.id.button7),
                (Button) layoutInflater.findViewById(R.id.button8),
                (Button) layoutInflater.findViewById(R.id.button9)};
        buttonsNumbersGlobal = buttonsNumbers;
        for (Button b : buttonsNumbersGlobal) {
            b.setOnClickListener(this);
        }

        buttonMod = (Button) layoutInflater.findViewById(R.id.mod);
        buttonPower = (Button) layoutInflater.findViewById(R.id.power);
        buttonClearAll = (Button) layoutInflater.findViewById(R.id.clearAll);
        buttonIncrease = (Button) layoutInflater.findViewById(R.id.increase);
        buttonSeparate = (Button) layoutInflater.findViewById(R.id.separate);
        buttonSum = (Button) layoutInflater.findViewById(R.id.sum);
        buttonPoint = (Button) layoutInflater.findViewById(R.id.point);
        buttonDifference = (Button) layoutInflater.findViewById(R.id.difference);
        buttonIs = (Button) layoutInflater.findViewById(R.id.is);
        setListenerAndContent(buttonMod, buttonPower, buttonClearAll, buttonIncrease, buttonSeparate, buttonSum, buttonPoint, buttonDifference, buttonIs);

        buttonClear = (ImageButton) layoutInflater.findViewById(R.id.clear);
        buttonClear.setOnClickListener(this);

        editNumsField = layoutInflater.findViewById(R.id.editNumbersField);
        editNumsField.setSelection(editNumsField.getText().length());//устанавливаем курсор в конец текствью

        textInputtedData = (TextView) layoutInflater.findViewById(R.id.textInputtedData);

        if (savedInstanceState!=null){
            remembNumFirst=savedInstanceState.getInt(EXTRA_NUM_FIRST);
            remembOper=savedInstanceState.getString(EXTRA_OPERATOR);
            textInputtedData.setText(savedInstanceState.getString(EXTRA_TEXT_INPUTT));
            editNumsField.setText(savedInstanceState.getString(EXTRA_TEXT_FIELD));
        }
        return layoutInflater;
    }

    @Override
    public void onResume() {
        super.onResume();
        editNumsField.setShowSoftInputOnFocus(false);//устанавливает будет ли метод ввода ввидимым когда edittext в фокусе(убираем
        // всплывающую клаву)). Однако этот способ не убирает всплывающую клаву если edittext был в фокусе при сворачивании и
        // разворачивании активности. Чтобы после разворачивания убрать всплывающую клаву (и оставить возможность управлять курсором
        // (и фокусом) в editext) нужно добавить метод ниже:
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {//чтобы сохраняло данныев родительской активности при нажатии на кнопку вверх
        // нужно в родительской активности в манифесте установить android:launchMode="singleTask" ибо android:launchMode="singleTop"
        super.onSaveInstanceState(outState);
        outState.putDouble(EXTRA_NUM_FIRST, remembNumFirst);;
        outState.putString(EXTRA_OPERATOR, remembOper);
        outState.putString(EXTRA_TEXT_FIELD, textField);
        outState.putString(EXTRA_TEXT_INPUTT, textInputt);
    }

    @Override
    public void onClick(View v) {
        editableText = editNumsField.getText();
        textField = String.valueOf(editableText);
        textInputt = textInputtedData.getText().toString();
        if (textField.indexOf(',') != -1) {//проверяем содержит ли текст запятую, и если что ее меняем на точку
            String textTemp = textField.replace(',', '.');
            textField = textTemp;
        }

        int indexStart = editNumsField.getSelectionStart() >= 0 ? editNumsField.getSelectionStart() : 0;//(тернарная операция "?")если > или = 0, то index присваивает значение до : , если не равно то присваивает значение после :
        // получаем где стоит начало выбранного текста (или курсор)
        int indexEnd = editNumsField.getSelectionEnd() >= 0 ? editNumsField.getSelectionEnd() : 0;
        //получаем где стоит конец выбранного текста (или Курсор)
        //тернарная операция необходима (либо Math.max()) ибо если в EditText нет выбора или курсора, getSelectionStart()
        // и getSelectionEnd() будут возвращать -1.

        if (v.getId() == R.id.clear) {////обрабатываем нажатие imageButton отдельно,т.к у нас код построен на getText, а в imButt его нету
            if (indexStart != indexEnd)
                editableText.delete(Math.min(indexStart, indexEnd), Math.max(indexStart, indexEnd));
            else if (indexStart != 0)
                editableText.replace(indexStart - 1, indexStart, "");
            return;
        }

        //определение button стоит после метода с imagebutton, иначе выкинет ошибку при определении button при нажатии на imageButton
        Button button = (Button) v;//определили баттон, чтобы иметь доступ к таким методам как getText и другие
        String textButton = (String) button.getText(); // строка с текстом кнопкки
        String textContent = (String) button.getContentDescription();// строка с текстом описания контента (добавлен,
        // т.к. imageButton не может вернуть getText)

        ///////////////////////////////обработка нажатий на кнопки операций

        switch (v.getId()) {
            case R.id.clearAll:////////кнопка стирающая всё
                editableText.clear();
                textInputtedData.setText("");
                break;
            ///////////////////////////кнопки математических операций
            case R.id.sum:
            case R.id.difference:
            case R.id.increase:
            case R.id.separate:
            case R.id.mod:
            case R.id.power:
                saveInputtedData(button, textField);
                break;
            case R.id.is://////////////обрабатываем нажатие кнопки =
                if (!textField.equals("") && remembOper != null) {//второе условие нужно если введено число, но не введен знак операции
                    String is = is();
                    editNumsField.setText(is);//пишем в поле полученное значение
                    editNumsField.setSelection(is.length());//устанавливаем курсор в конце поля
                    remembOper = null;
                }
                break;
            case R.id.point://///////////////вводим десятичную точку
                String textFieldPart;
                boolean isDecimal = false;//для проверки введено ли число с плавающей точкой или нет
                if (textField.indexOf('.') != -1) isDecimal = true;

                //isDecimal = (Double.parseDouble(textValue)) % 1 != 0;//проверяем введено ли число с плавающей точкой или нет

                /*if (isDecimal) {//число десятичное
                    if (indexStart != indexEnd) {//какой-то текст выделен, а не один курсор
                        textFieldPart = textField.substring(indexStart, indexEnd);
                        boolean isDecimalPart = false;//для проверки содержания выделенным текстом точки
                        if (textFieldPart.indexOf('.')!=-1) isDecimalPart = true;//пришлось посимвольно перебрать введенную строку чтобы
                        // найти точку, ибо может случиться вариант, что выбранный текст может содержать только одну точку
                        if (isDecimalPart) {
                            if (indexStart != 0)
                                editableText.replace(Math.min(indexStart, indexEnd), Math.max(indexStart, indexEnd), textButton);
                            else
                                editableText.replace(Math.min(indexStart, indexEnd), Math.max(indexStart, indexEnd), "0." + textButton);
                        }
                    }
                } else if (indexStart != 0) {//число не десятичное и индекс не в начале
                    editableText.replace(Math.min(indexStart, indexEnd), Math.max(indexStart, indexEnd), textButton);
                } else { //индекс в начале
                    editableText.replace(Math.min(indexStart, indexEnd), Math.max(indexStart, indexEnd), "0." + textButton);
                }*///условие без break;

                /////отработать условие через break проще ибо в двух вариантах if else (самого верхнего блока) в комментах выше одинаковые выражения
                if (isDecimal) {//число десятичное
                    if (indexStart != indexEnd) {//какой-то текст выделен, а не один курсор
                        textFieldPart = textField.substring(indexStart, indexEnd);
                        boolean isDecimalPart = false;//для проверки содержания выделенным текстом точки
                        if (textFieldPart.indexOf('.') != -1)
                            isDecimalPart = true;//пришлось посимвольно перебрать введенную строку чтобы
                        // найти точку, ибо может случиться вариант, что выбранный текст может содержать только одну точку
                        if (!isDecimalPart) {//недесятичная часть выделена
                            break;
                        }
                    }
                }
                if (indexStart != 0) {//число не десятичное и индекс не в начале
                    editableText.replace(Math.min(indexStart, indexEnd), Math.max(indexStart, indexEnd), textButton);
                } else { //индекс в начале
                    editableText.replace(Math.min(indexStart, indexEnd), Math.max(indexStart, indexEnd), "0." + textButton);
                }

                break;

            case R.id.button0:
                String textBefore = "1";
                if (indexStart > 0)
                    textBefore = textField.substring(0, indexStart);
                if ((!textField.equals("") && indexStart == 0 && indexEnd!=textField.length()) || (Double.parseDouble(textBefore) == 0 && textBefore.indexOf('.') == -1)) {
                    break;
                }
                editableText.replace(Math.min(indexStart, indexEnd), Math.max(indexStart, indexEnd), textButton);
                break;
            default:
                //////////////////////////////обработка нажатий на цифры
                for (int i = 1; i < 10; i++) {//определяем какой текст у кнопки
                    if (!textButton.equals(String.valueOf(i))) {
                        if (textField.equals("0")){//не дает вывести результат типа 0989
                            editNumsField.setText(textButton);
                            editNumsField.setSelection(1);//перемещаем курсор в конец, т.к. мы знаем изначально что мы заменяем ноль на
                                    // вводимый символ, то значит длина текста равна 1
                        }
                        continue;
                    }
                    ///////////////вставляем текст в нужную позицию в edit text
                    editableText.replace(Math.min(indexStart, indexEnd), Math.max(indexStart, indexEnd), textButton/*так же еще можно добавить два параметра для задания начала и конца части вставляемого текста*/);
                    return;//нужно вызвать  брэйк чтобы лишний раз не гоняло по циклу после определения текста нажатой кнопки, однако
                    // лучше вызвать return, чтобы не гонять все остальные операции из метода onClick
                }

        }

    }

    public void setListenerAndContent(Button... buttons) {//устанавливает слушатель на кнопки
        for (Button b : buttons) {
            b.setOnClickListener(this);
            b.setContentDescription(b.getText());
        }
    }

    public void saveInputtedData(Button button, String textField) {//устанавливает в текствью уже введенные числа и
        // оператор, либо меняет математический оператор, либо вызывает знак равно и запоминает последний оператор.
        // Вызывается при нажатии на кнопку математического оператора
        String text = "";
        String textTemp;
        if (!textField.equals("")) {

            if (!textInputt.equals("")&&textInputt.indexOf('=')==-1) {
                String is = is();
                remembNumFirst = Double.parseDouble(is);//сохраняем в память значение предыдущего выражения
                textTemp = is + " " + button.getText() + " ";
            }else{
                remembNumFirst = Double.parseDouble(textField);//сохраняем в память последнее введенное число
                textTemp = df15.format(remembNumFirst) + " " + button.getText() + " ";
            }

            if (textTemp.indexOf(',') != -1) {//проверяем содержит ли введенный текст запятую, и если что ее меняем на точку
                text = textTemp.replace(',', '.');
            } else {
                text = textTemp;
            }

            editableText.clear();
        } else if (!textInputt.equals("")) {//если в textInp выведено число с оператором и в поле не введено число, тогда просто заменяется оператор
            textTemp = textInputt;
            text = textTemp.replace(remembOper, button.getText());//заменяет текст такой же как в параметре 1 на текст из
            // параметра 2. нужно не просто написать text.replace(...), а нужно присвоить это значение другой переменной,
            // чтобы все заработало и чтобы джава не игнорировало эту команду
        } /*else {//нужен ибо выдает ошибку что text может быть не реализован
            text = "";
        }*/
        textInputtedData.setText(text);
        remembOper = button.getText().toString();//сохраняем в память последнюю выбранную математическую операцию
    }

    public String is(){//оператор "равно"
        double numSecond = Double.parseDouble(textField);
        double numIs = 101;
        String is;
        switch (remembOper) {
            case "+":
                numIs = remembNumFirst + numSecond;
                break;
            case "-":
                numIs = remembNumFirst - numSecond;
                break;
            case "*":
                numIs = remembNumFirst * numSecond;
                break;
            case "/":
                numIs = remembNumFirst / numSecond;
                break;
            case "%":
                numIs = remembNumFirst % numSecond;
                break;
            case "^":
                numIs = Math.pow(remembNumFirst, numSecond);
                break;
            //default://добавил дефолт ибо при присвоении для numIs значения is джава ругается что numIs может быть не определен
            //  numIs = 101;
        }
        StringBuffer textBuffer = new StringBuffer();
        textBuffer.append(textInputt).append(df15.format(numSecond)).append(" = ");//добавляем второе
        // число с форматом к тексту и добавляем =
        String textTempForBuff = textBuffer.toString();
        if (textBuffer.toString().indexOf(',') != -1) { //проверяем содержит ли текст запятую, и если что ее меняем на точку
            textTempForBuff = textBuffer.toString().replace(',', '.');
        }
        textInputtedData.setText(textTempForBuff);////обновляем текствью с данными (добавляем второе число и знак равно)

        is = String.valueOf(df15.format(numIs)); // преобразовали число в строку чтобы потом извлечь длину строки
        if (is.indexOf(',') != -1) { //проверяем содержит ли текст запятую, и если что ее меняем на точку
            String textTemp = is.replace(',', '.');
            is = textTemp;
        }
        return is;
    }
}