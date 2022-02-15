package corp.petro.pz1_tpues;

import java.util.ArrayList;
import java.util.List;

public class TipMontagh {
    private String name, value;

    static List <TipMontagh> montaghs = new ArrayList<>();

    public static List<TipMontagh> getMontaghs(){
        montaghs.add(new TipMontagh("Поверхностный односторонний", "1.2"));
        montaghs.add(new TipMontagh("Поверхностный двусторонний", "1.4"));
        montaghs.add(new TipMontagh("Смешанноразнесенный", "1.8"));
        montaghs.add(new TipMontagh("Смешанный монтаж", "2.8"));

        return montaghs;
    }

    public final static TipMontagh[]montagh = {new TipMontagh("Поверхностный односторонний", "1.2"),
                            new TipMontagh("Поверхностный двусторонний", "1.4"),
                            new TipMontagh("Смешанноразнесенный", "1.8"),
                            new TipMontagh("Смешанный", "2.8")};

    private TipMontagh(String name, String value){
        this.name = name;
        this.value = value;
    }

    public String toString(){
        return name;
    }

    public String getValue(){
        return value;
    }
}
