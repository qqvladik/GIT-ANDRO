package corp.petro.collection;

public class WorkoutEkto {

    private String[] exercices, repits;
    private String name, tabName;
    private int[] podhods;

    private static final String[][] ECERCISES = {{"Жим лежа",
            "Жим лежа на наклонной скамье",
            "Разведение гантелей лежа",
            "Отжимания на брусьях",
            "Жим лежа узким хватом",
            "Французский жим лежа",
            "Трицепс на блоке",
            "Подъемы корпуса"},

            {"Тяга верхнего блока к груди сидя",
            "Тяга штанги в наклоне",
            "Тяга гантели в наклоне одной рукой",
            "Становая тяга",
            "Подъемы штанги на бицепс",
            "Подъемы гантелей на бицепс с супинацией",
            "Подъемы штанги на бицепс хватом сверху"},

            {"Жим штанги с груди стоя",
            "Жим гантелей сидя",
            "Махи гантелями в стороны",
            "Шаги со штангой",
            "Приседания со штангой",
            "Жим ногами",
            "Подъемы на мыски в тренажере",
            "Подъемы ног лежа"}};

    private static final int[][] PODHODS={{3,3,3,3,4,3,3,4},{3,3,3,3,4,3,3},{3,3,3,4,3,3,4,4}};
    private static final String[][] REPITS={{"6-8", "6-8","8-10","8-10","6-8","8-10","10-12","макс."},
                                        {"8","6","6-8","6","8-10","8-10", "8-10","8-10"},
                                        {"8","8-10","8-10","12-15","6-8","8","12-15","макс."}};


    public static final WorkoutEkto []workouts = {new WorkoutEkto("День 1","Грудь, Трицепс, Пресс", ECERCISES[0], PODHODS[0], REPITS[0]),
            new WorkoutEkto("День 2","Спина, Бицепс", ECERCISES[1],PODHODS[1], REPITS[1]),
            new WorkoutEkto("День 3","Плечи, Ноги, Пресс", ECERCISES[2],PODHODS[2], REPITS[2])};

    private WorkoutEkto(String tabName, String name, String[] exercices, int[] podhods, String[] repits) {
        this.tabName = tabName;
        this.name = name;
        this.exercices = exercices;
        this.podhods = podhods;
        this.repits = repits; 
    }

    public String[] getExercices() {
        return exercices;
    }

    public String getName() {
        return name;
    }

    public String getTabName() {
        return tabName;
    }

    public int[] getPodhods(){
        return podhods;
    }

    public String[] getRepits() {
        return repits;
    }
}
