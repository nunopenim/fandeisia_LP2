package pt.ulusofona.lp2.fandeisiaGame;

public class EmpurraParaNorte extends Feitico {

    int rows;
    int columns;

    public EmpurraParaNorte(int custo, int x, int y) {
        super(custo, x, y);
        this.rows = FandeisiaGameManager.rows;
        this.columns = FandeisiaGameManager.columns;
    }

    @Override
    public boolean lancaFeitico(Creature c){
        if (c == null) {
            return false;
        }
        if(c.getPosicao()[1]>0){
            c.feiticoNorte();
            return true;
        }
        return false;
    }
}
