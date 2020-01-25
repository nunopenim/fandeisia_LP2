package pt.ulusofona.lp2.fandeisiaGame;

public class Congela extends Feitico{
    public Congela(int custo, int x, int y) {
        super(custo, x, y);
    }

    @Override
    public boolean lancaFeitico(Creature c) {
        if (c == null) {
            return false;
        }
        return true;
    }
}
