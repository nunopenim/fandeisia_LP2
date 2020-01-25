package pt.ulusofona.lp2.fandeisiaGame;

public class EmpurraParaSul extends Feitico {

    int rows;
    int columns;

    public EmpurraParaSul(int custo, int x, int y) {
        super(custo, x, y);
        this.rows = FandeisiaGameManager.rows;
        this.columns = FandeisiaGameManager.columns;
    }

    @Override
    public boolean lancaFeitico(Creature c){
        if (c == null) {
            return false;
        }
        if(c.getPosicao()[1]<rows-1){
            c.feiticoSul();
            return true;
        }
        return false;
    }
}
