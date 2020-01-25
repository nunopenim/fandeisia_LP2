package pt.ulusofona.lp2.fandeisiaGame;

public class Anao extends Creature {
    public Anao(int id, int x, int y, String orientacao, Equipa equipa) {
        super(id, x, y, orientacao, equipa, 1);
    }
    public Anao(String id, String x, String y, String orientacao, Equipa equipa) {
        super(id, x, y, orientacao, equipa, 1);
    }

    @Override
    public String getImagePNG() {
        return "Anao.png";
    }
    //se move no mundo, desloca-se 1 casa/posições de cada vez.

    @Override
    public String toString() {
        String feitico = FandeisiaGameManager.getSpell(this.getPosicao()[0], this.getPosicao()[1]);
        if (feitico == null) {
            feitico = "Nenhum";
        }
        return id + " | " + this.getTipo() + " | " + equipa.getId() + " | " + feitico + " | " + tesouros + " @ (" + posicao[0] + ", " + posicao[1] + ") " + orientacao;
    }

}
