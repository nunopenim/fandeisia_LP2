package pt.ulusofona.lp2.fandeisiaGame;

public class EmpurraParaEste extends Feitico {

    int rows;
    int columns;

    public EmpurraParaEste(int custo, int x, int y) {
        super(custo, x, y);
        this.rows = FandeisiaGameManager.rows;
        this.columns = FandeisiaGameManager.columns;
    }

    @Override
    public boolean lancaFeitico(Creature c) {
        if (c == null) {
            return false;
        }
        if(c.getPosicao()[0]<columns-1){
            c.feiticoLeste();
            return true;
        }
        return false;
    }
}
