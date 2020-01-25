package pt.ulusofona.lp2.fandeisiaGame;

public class ReduzAlcance extends Feitico{

    public ReduzAlcance(int custo, int x, int y) {
        super(custo, x, y);
    }

    @Override
    public boolean lancaFeitico(Creature c) {
        return c != null;
    }
}
