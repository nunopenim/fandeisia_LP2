package pt.ulusofona.lp2.fandeisiaGame;

public class Congela4Ever extends Feitico{
    public Congela4Ever(int custo, int x, int y) {
        super(custo, x, y);
    }

    @Override
    public boolean lancaFeitico(Creature c){
        if (c == null) {
            return false;
        }
        return !c.getCongelado();
    }
}
