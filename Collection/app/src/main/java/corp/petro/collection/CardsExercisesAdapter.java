package corp.petro.collection;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

class CardsExercisesAdapter extends RecyclerView.Adapter<CardsExercisesAdapter.ViewHolder> {

    private String[] exercises, repits;//указываем с каким типом данныых работаем
    private int[] podhods;

    CardsExercisesAdapter(String[] exercises, int[] podhods, String[] repits) {//передаем данные
        this.exercises = exercises;
        this.podhods = podhods;
        this.repits = repits;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {//вызывается каждый раз когда нужно создать новый объект ViewHolder
        CardView cv = (CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.card_exercise, parent, false);//создали объект карточки
        return new ViewHolder(cv);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CardView cardView = holder.cardView;
        TextView textDescription = (TextView) cardView.findViewById(R.id.textDescription);
        TextView textPodhod = (TextView) cardView.findViewById(R.id.textPodhod);
        TextView textRepit = (TextView) cardView.findViewById(R.id.textRepit);
        //CheckBox checkBox = (CheckBox) cardView.findViewById(R.id.checkBox);

        textDescription.setText(exercises[position]);
        String s1 = podhods[position] + " подхода, ";
        String s2 = repits[position] + " повторений";
        textPodhod.setText(s1);
        textRepit.setText(s2);


    }

    @Override
    public int getItemCount() {
        return exercises.length;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {//определяем представления, которые будет содержать RecuclerView
        private CardView cardView;

        public ViewHolder(CardView view) {
            super(view);
            cardView = view;
        }
    }
}
