package corp.petro.collection;


import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class FirstDayFragment extends Fragment {

    static final String ARGUMENT_PAGE_NUMBER = "arg_page_number";
    private int position;

    public int notification_id;
    public String chanel_id;

    public FirstDayFragment() {
        // Required empty public constructor
    }

    public FirstDayFragment(int position){
        this.position = position;
    }

    /*static FirstDayFragment newInstance(int page) {//Метод newInstance создает новый экземпляр фрагмента и записывает ему в атрибуты число,
        // которое пришло на вход. Это число – номер страницы, которую хочет показать ViewPager. По нему фрагмент будет определять,
        // какое содержимое создавать в фрагменте. Если так не сделать(а сделать передачу позиции через конструктор), то при повороте экрана
        // будет на каждой вкладке отображаться информация принадлежащая первой вкладке, т.к. фрагмент при повороте экрана тоже пересоздается и
        // значение переменной position обнуляется в связи с механизмами java
        FirstDayFragment pageFragment = new FirstDayFragment();
        Bundle arguments = new Bundle();
        arguments.putInt(ARGUMENT_PAGE_NUMBER, page);
        pageFragment.setArguments(arguments);
        return pageFragment;
    }*/

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //position = getArguments().getInt(ARGUMENT_PAGE_NUMBER);
        if(savedInstanceState!=null){
            position=savedInstanceState.getInt("position");
        }

        View layoutInflater = inflater.inflate(R.layout.fragment_first_day, container, false);

        RecyclerView recyclerView = layoutInflater.findViewById(R.id.container_exercices);
        CardsExercisesAdapter adapter = new CardsExercisesAdapter(WorkoutEkto.workouts[position].getExercices(),
                                                        WorkoutEkto.workouts[position].getPodhods(),
                                                        WorkoutEkto.workouts[position].getRepits());
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        final TextView nameText = (TextView) layoutInflater.findViewById(R.id.nameText);

        nameText.setText(WorkoutEkto.workouts[position].getName());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);

        nameText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Создадим новые объекты Intent и PendingIntent, которые описывают намерения и целевые действия. В нашем случае мы хотим запустить нашу активность,
                // когда пользователь среагирует на уведомление. Присоединяем объекты через setContentIntent().
                Intent notificationIntent = new Intent(getActivity(), MainActivity.class);
                 notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP);//Запустить запущенную активность, а не новую

                PendingIntent contentIntent = PendingIntent.getActivity(getActivity(),0, notificationIntent, PendingIntent.FLAG_CANCEL_CURRENT);
                //PendingIntent указывает действие, которое необходимо предпринять в будущем. Он позволяет передать будущее намерение другому приложению(напр. NotificationManager, AlarmManager)
                // и позволить этому приложению выполнять этот намерение
                //requestCode-Это своего рода ключи, чтобы отличать один PendingIntent от других при необходимости
                //intent – этот Intent будет впоследствии использован для вызова activity/broadcast/service (в зависимости от метода создания)
                //flags – флаги, влияющие на поведение и создание PendingIntent

                //Далее формируется внешний вид и поведение уведомления через построитель Notification.Builder. Вы можете задать текст уведомления, значок, заголовок и прочие атрибуты
                NotificationCompat.Builder builder =
                        new NotificationCompat.Builder(getContext(), nameText.getText().toString()/*идентификатор канала*/)//Также следует создать идентификатор канала. Каналы появились в API 28,
                                                        // но старые устройства будут просто игнорировать данный параметр при вызове конструктора NotificationCompat.Builder
                        .setSmallIcon(R.drawable.muscle)
                        .setContentTitle("Day "+(position+1))
                        .setContentText(nameText.getText().toString())
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                        .addAction(0, "следующее", contentIntent)
                        .setContentIntent(contentIntent)//Присоединяем объекты через setContentIntent().
                        .setAutoCancel(true);//автоматически закрываем уведомление после нажатия на него

                NotificationManagerCompat notificationManager = NotificationManagerCompat.from(getContext());
                //Выводится уведомление с помощью метода notify() - своеобразный аналог метода show() у Toast
                notificationManager.notify(0/*идентификатор уведомления*/,builder.build());//Для начала вам надо создать идентификатор уведомления.
                                                             //Если у вас будет один идентификатор, то каждое новое уведомление затрёт предыдущее
            }
        });

        return layoutInflater;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("position", position);
    }
}
