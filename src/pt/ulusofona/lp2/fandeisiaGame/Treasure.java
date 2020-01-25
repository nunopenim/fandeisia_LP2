package pt.ulusofona.lp2.fandeisiaGame;

public class Treasure implements Comparable<Treasure> {
    int id;
    int x;
    int y;
    int pontos;
    String tipo;
    String figura;

    public Treasure(String id, String tipo, String x, String y) {
        this.id = Integer.parseInt(id);
        this.tipo = tipo;
        this.x = Integer.parseInt(x);
        this.y = Integer.parseInt(y);
        this.figura=tipo + ".png";
        if("gold".equals(tipo)){
            pontos=3;
        }
        else if("silver".equals(tipo)){
            pontos=2;
        }
        else if("bronze".equals(tipo)){
            pontos=1;
        }
    }

    public int getId() {
        return id;
    }

    public int[] getPosition() {
        int[] ret = {x, y};
        return ret;
    }

    public int getPontos() {
        return pontos;
    }

    public int compareTo(Treasure t) {
        return this.id - t.id;
    }

    public String writeToString() {
        return "id: " + id + ", type: " + tipo + ", x: " + x + ", y: " + y;
    }
}