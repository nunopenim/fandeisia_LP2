package pt.ulusofona.lp2.fandeisiaGame;

public class Buraco implements Comparable<Buraco>{

    int id;
    String tipo;
    int x;
    int y;
    String figuraBuraco;

    public Buraco(String id, String tipo, String x, String y){
        this.id = Integer.parseInt(id);
        this.tipo = tipo;
        this.x = Integer.parseInt(x);
        this.y = Integer.parseInt(y);

        figuraBuraco = tipo + ".png";
    }

    public int[] getPosicao() {
        return new int[] {x,y};
    }

    public int getId() {
        return id;
    }

    public String writeToString() {
        return "id: " + id + ", type: hole, x: " + x + ", y: " + y;
    }

    public int compareTo(Buraco b) {
        return this.id - b.id;
    }
}
