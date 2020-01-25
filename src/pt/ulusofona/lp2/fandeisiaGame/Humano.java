package pt.ulusofona.lp2.fandeisiaGame;

import java.util.List;

public class Humano extends Creature {
    public Humano(int id, int x, int y, String orientacao, Equipa equipa) {
        super(id, x, y, orientacao, equipa, 3);
    }
    public Humano(String id, String x, String y, String orientacao, Equipa equipa) {
        super(id, x, y, orientacao, equipa, 3);
    }

    @Override
    public String getImagePNG() {
        return "Humano.png";
    }

    @Override
    public boolean move(List<Creature> criaturas) {
        for(Creature c : criaturas) {
            if (!c.equals(this)){
                if (this.orientacao.equals("Norte") && c.getPosicao()[0] == posicao[0] && c.getPosicao()[1] == posicao[1]-2){
                    return false;
                }
                if (this.orientacao.equals("Sul") && c.getPosicao()[0] == posicao[0] && c.getPosicao()[1] == posicao[1]+2){
                    return false;
                }
                if (this.orientacao.equals("Este") && c.getPosicao()[0] == posicao[0]+2 && c.getPosicao()[1] == posicao[1]){
                    return false;
                }
                if (this.orientacao.equals("Oeste") && c.getPosicao()[0] == posicao[0]-2 && c.getPosicao()[1] == posicao[1]){
                    return false;
                }
                if (this.orientacao.equals("Norte") && c.getPosicao()[0] == posicao[0] && c.getPosicao()[1] == posicao[1]-1){
                    return false;
                }
                if (this.orientacao.equals("Sul") && c.getPosicao()[0] == posicao[0] && c.getPosicao()[1] == posicao[1]+1){
                    return false;
                }
                if (this.orientacao.equals("Este") && c.getPosicao()[0] == posicao[0]+1 && c.getPosicao()[1] == posicao[1]){
                    return false;
                }
                if (this.orientacao.equals("Oeste") && c.getPosicao()[0] == posicao[0]-1 && c.getPosicao()[1] == posicao[1]){
                    return false;
                }
            }
        }
        if (FandeisiaGameManager.buracos != null) {
            for(Buraco c : FandeisiaGameManager.buracos) {
                if (this.orientacao.equals("Norte") && c.getPosicao()[0] == posicao[0] && c.getPosicao()[1] == posicao[1]-2){
                    return false;
                }
                if (this.orientacao.equals("Sul") && c.getPosicao()[0] == posicao[0] && c.getPosicao()[1] == posicao[1]+2){
                    return false;
                }
                if (this.orientacao.equals("Este") && c.getPosicao()[0] == posicao[0]+2 && c.getPosicao()[1] == posicao[1]){
                    return false;
                }
                if (this.orientacao.equals("Oeste") && c.getPosicao()[0] == posicao[0]-2 && c.getPosicao()[1] == posicao[1]){
                    return false;
                }
                if (this.orientacao.equals("Norte") && c.getPosicao()[0] == posicao[0] && c.getPosicao()[1] == posicao[1]-1){
                    return false;
                }
                if (this.orientacao.equals("Sul") && c.getPosicao()[0] == posicao[0] && c.getPosicao()[1] == posicao[1]+1){
                    return false;
                }
                if (this.orientacao.equals("Este") && c.getPosicao()[0] == posicao[0]+1 && c.getPosicao()[1] == posicao[1]){
                    return false;
                }
                if (this.orientacao.equals("Oeste") && c.getPosicao()[0] == posicao[0]-1 && c.getPosicao()[1] == posicao[1]){
                    return false;
                }
            }
        }
        if (orientacao.equals("Norte") && posicao[1]>1) {
            movimentaNorte();
            return true;
        }
        else if (orientacao.equals("Sul") && posicao[1]<FandeisiaGameManager.rows-2){
            movimentaSul();
            return true;
        }
        else if (orientacao.equals("Oeste") && posicao[0]>1){
            movimentaOeste();
            return true;
        }
        else if (orientacao.equals("Este") && posicao[0]<FandeisiaGameManager.columns-2){
            movimentaLeste();
            return true;
        }
        return false;
    }
    @Override
    public int[] predictMove(List<Creature> criaturas) {
        for(Creature c : criaturas) {
            if (!c.equals(this)){
                if (this.orientacao.equals("Norte") && c.getPosicao()[0] == posicao[0] && c.getPosicao()[1] == posicao[1]-2){
                    return new int[]{posicao[0], posicao[1]};
                }
                if (this.orientacao.equals("Sul") && c.getPosicao()[0] == posicao[0] && c.getPosicao()[1] == posicao[1]+2){
                    return new int[]{posicao[0], posicao[1]};
                }
                if (this.orientacao.equals("Este") && c.getPosicao()[0] == posicao[0]+2 && c.getPosicao()[1] == posicao[1]){
                    return new int[]{posicao[0], posicao[1]};
                }
                if (this.orientacao.equals("Oeste") && c.getPosicao()[0] == posicao[0]-2 && c.getPosicao()[1] == posicao[1]){
                    return new int[]{posicao[0], posicao[1]};
                }
                if (this.orientacao.equals("Norte") && c.getPosicao()[0] == posicao[0] && c.getPosicao()[1] == posicao[1]-1){
                    return new int[]{posicao[0], posicao[1]};
                }
                if (this.orientacao.equals("Sul") && c.getPosicao()[0] == posicao[0] && c.getPosicao()[1] == posicao[1]+1){
                    return new int[]{posicao[0], posicao[1]};
                }
                if (this.orientacao.equals("Este") && c.getPosicao()[0] == posicao[0]+1 && c.getPosicao()[1] == posicao[1]){
                    return new int[]{posicao[0], posicao[1]};
                }
                if (this.orientacao.equals("Oeste") && c.getPosicao()[0] == posicao[0]-1 && c.getPosicao()[1] == posicao[1]){
                    return new int[]{posicao[0], posicao[1]};
                }
            }
        }
        if (FandeisiaGameManager.buracos != null) {
            for(Buraco c : FandeisiaGameManager.buracos) {
                if (this.orientacao.equals("Norte") && c.getPosicao()[0] == posicao[0] && c.getPosicao()[1] == posicao[1]-2){
                    return new int[]{posicao[0], posicao[1]};
                }
                if (this.orientacao.equals("Sul") && c.getPosicao()[0] == posicao[0] && c.getPosicao()[1] == posicao[1]+2){
                    return new int[]{posicao[0], posicao[1]};
                }
                if (this.orientacao.equals("Este") && c.getPosicao()[0] == posicao[0]+2 && c.getPosicao()[1] == posicao[1]){
                    return new int[]{posicao[0], posicao[1]};
                }
                if (this.orientacao.equals("Oeste") && c.getPosicao()[0] == posicao[0]-2 && c.getPosicao()[1] == posicao[1]){
                    return new int[]{posicao[0], posicao[1]};
                }
                if (this.orientacao.equals("Norte") && c.getPosicao()[0] == posicao[0] && c.getPosicao()[1] == posicao[1]-1){
                    return new int[]{posicao[0], posicao[1]};
                }
                if (this.orientacao.equals("Sul") && c.getPosicao()[0] == posicao[0] && c.getPosicao()[1] == posicao[1]+1){
                    return new int[]{posicao[0], posicao[1]};
                }
                if (this.orientacao.equals("Este") && c.getPosicao()[0] == posicao[0]+1 && c.getPosicao()[1] == posicao[1]){
                    return new int[]{posicao[0], posicao[1]};
                }
                if (this.orientacao.equals("Oeste") && c.getPosicao()[0] == posicao[0]-1 && c.getPosicao()[1] == posicao[1]){
                    return new int[]{posicao[0], posicao[1]};
                }
            }
        }
        if (orientacao.equals("Norte") && posicao[1]>1) {
            return new int[]{posicao[0], posicao[1]-2};
        }
        else if (orientacao.equals("Sul") && posicao[1]<FandeisiaGameManager.rows-2){
            return new int[]{posicao[0], posicao[1]+2};
        }
        else if (orientacao.equals("Oeste") && posicao[0]>1){
            return new int[]{posicao[0], posicao[1]-2};
        }
        else if (orientacao.equals("Este") && posicao[0]<FandeisiaGameManager.columns-2){
            return new int[]{posicao[0], posicao[1]+2};
        }
        return new int[]{posicao[0], posicao[1]};
    }

    @Override
    void movimentaSul() {
        posicao[1]+=2;
        this.km++;
    }
    @Override
    void movimentaNorte() {
        posicao[1]-=2;
        this.km++;
    }
    @Override
    void movimentaOeste() {
        posicao[0]-=2;
        this.km++;
    }
    @Override
    void movimentaLeste() {
        posicao[0]+=2;
        this.km++;
    }

    // desloca-se 2 casas/posições de cada vez
    //pode mover-se na horizontal e na vertical.
}
