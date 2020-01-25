package pt.ulusofona.lp2.fandeisiaGame;

import java.util.List;

public class Gigante extends Creature {
    public Gigante(String id, String x, String y, String orientacao, Equipa equipa) {
        super(id, x, y, orientacao, equipa, 5);
    }
    public Gigante(int id, int x, int y, String orientacao, Equipa equipa) {
        super(id, x, y, orientacao, equipa, 5);
    }

    @Override
    public String getImagePNG() {
        return "Gigante.png";
    }
    @Override
    public int[] predictMove(List<Creature> criaturas) {
        for(Creature c : criaturas) {
            if (!c.equals(this)){
                if (this.orientacao.equals("Norte") && c.getPosicao()[0] == posicao[0] && c.getPosicao()[1] == posicao[1]-3){
                    return new int[]{posicao[0], posicao[1]};
                }
                if (this.orientacao.equals("Sul") && c.getPosicao()[0] == posicao[0] && c.getPosicao()[1] == posicao[1]+3){
                    return new int[]{posicao[0], posicao[1]};
                }
                if (this.orientacao.equals("Este") && c.getPosicao()[0] == posicao[0]+3 && c.getPosicao()[1] == posicao[1]){
                    return new int[]{posicao[0], posicao[1]};
                }
                if (this.orientacao.equals("Oeste") && c.getPosicao()[0] == posicao[0]-3 && c.getPosicao()[1] == posicao[1]){
                    return new int[]{posicao[0], posicao[1]};
                }
            }
        }
        if (orientacao.equals("Norte") && posicao[1]>2) {
            return new int[]{posicao[0], posicao[1]-3};
        }
        else if (orientacao.equals("Sul") && posicao[1]<FandeisiaGameManager.rows-3){
            return new int[]{posicao[0], posicao[1]+3};
        }
        else if (orientacao.equals("Oeste") && posicao[0]>2){
            return new int[]{posicao[0]-3, posicao[1]};
        }
        else if (orientacao.equals("Este") && posicao[0]<FandeisiaGameManager.columns-3){
            return new int[]{posicao[0]+3, posicao[1]};
        }
        return new int[]{posicao[0], posicao[1]};
    }

    @Override
    public boolean move(List<Creature> criaturas) {
        for(Creature c : criaturas) {
            if (!c.equals(this)){
                if (this.orientacao.equals("Norte") && c.getPosicao()[0] == posicao[0] && c.getPosicao()[1] == posicao[1]-3){
                    return false;
                }
                if (this.orientacao.equals("Sul") && c.getPosicao()[0] == posicao[0] && c.getPosicao()[1] == posicao[1]+3){
                    return false;
                }
                if (this.orientacao.equals("Este") && c.getPosicao()[0] == posicao[0]+3 && c.getPosicao()[1] == posicao[1]){
                    return false;
                }
                if (this.orientacao.equals("Oeste") && c.getPosicao()[0] == posicao[0]-3 && c.getPosicao()[1] == posicao[1]){
                    return false;
                }
            }
        }
        if (orientacao.equals("Norte") && posicao[1]>2) {
            movimentaNorte();
            return true;
        }
        else if (orientacao.equals("Sul") && posicao[1]<FandeisiaGameManager.rows-3){
            movimentaSul();
            return true;
        }
        else if (orientacao.equals("Oeste") && posicao[0]>2){
            movimentaOeste();
            return true;
        }
        else if (orientacao.equals("Este") && posicao[0]<FandeisiaGameManager.columns-3){
            movimentaLeste();
            return true;
        }
        return false;
    }

    @Override
    void movimentaSul() {
        posicao[1]+=3;
        this.km++;
    }
    @Override
    void movimentaNorte() {
        posicao[1]-=3;
        this.km++;
    }
    @Override
    void movimentaOeste() {
        posicao[0]-=3;
        this.km++;
    }
    @Override
    void movimentaLeste() {
        posicao[0]+=3;
        this.km++;
    }

}
