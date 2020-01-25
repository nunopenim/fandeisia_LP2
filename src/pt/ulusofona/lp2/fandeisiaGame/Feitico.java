package pt.ulusofona.lp2.fandeisiaGame;

public abstract class Feitico {
    int custo;
    int[]posicao=new int[2];

    public Feitico(int custo, int x, int y){
        this.custo=custo;
        this.posicao[0] = x;
        this.posicao[1] = y;
        //this.tipo = tipo;
    }

    public int getCusto() {
        return custo;
    }

    public int[] getPosicao() {
        return posicao;
    }

    public abstract boolean lancaFeitico(Creature c);

    public String getTipo() {
        if (this instanceof Congela) {
            return "Congela";
        }
        if (this instanceof Congela4Ever) {
            return "Congela4Ever";
        }
        if (this instanceof Descongela) {
            return "Descongela";
        }
        if (this instanceof DuplicaAlcance) {
            return "DuplicaAlcance";
        }
        if (this instanceof EmpurraParaNorte) {
            return "EmpurraParaNorte";
        }
        if (this instanceof EmpurraParaSul) {
            return "EmpurraParaSul";
        }
        if (this instanceof EmpurraParaEste) {
            return "EmpurraParaEste";
        }
        if (this instanceof EmpurraParaOeste) {
            return "EmpurraParaOeste";
        }
        if (this instanceof ReduzAlcance) {
            return "ReduzAlcance";
        }
        return null;
    }

    public String writeToString () {
        return null;
    }
}
