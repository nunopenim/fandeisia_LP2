package pt.ulusofona.lp2.fandeisiaGame;

public class StatsTiposCriatura {
    String tipo;
    long count;
    long tesouros;

    public StatsTiposCriatura(String tipo, long count, long tesouros) {
        this.tipo = tipo;
        this.count = count;
        this.tesouros = tesouros;
    }

    public StatsTiposCriatura() {}

    public StatsTiposCriatura(String tipo) {
        this.tipo = tipo;
    }

    public void updateCount(long count) {
        this.count = count;
    }

    public void updateTesouros(long tesouros) {
        this.tesouros = tesouros;
    }

    public long getCount() {
        return count;
    }

    public long getTesouros() {
        return tesouros;
    }

    public String toString() {
        return tipo+":"+count+":"+tesouros;
    }

    public boolean compareTo(long a2) {
        if(this.tesouros == a2){
            return true;
        }
         return false;
    }
}
