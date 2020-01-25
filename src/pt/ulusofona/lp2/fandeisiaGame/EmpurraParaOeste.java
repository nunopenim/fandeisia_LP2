package pt.ulusofona.lp2.fandeisiaGame;

public class EmpurraParaOeste extends Feitico {

    int rows;
    int columns;

    public EmpurraParaOeste(int custo, int x, int y) {
        super(custo, x, y);
        this.rows = FandeisiaGameManager.rows;
        this.columns = FandeisiaGameManager.columns;
    }

    @Override
    public boolean lancaFeitico(Creature c){
        if (c == null) {
            return false;
        }
        if(c.getPosicao()[0]>0){
            c.feiticoOeste();
            return true;
        }
        return false;
    }
}
