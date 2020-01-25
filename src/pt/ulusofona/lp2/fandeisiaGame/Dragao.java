package pt.ulusofona.lp2.fandeisiaGame;

import java.util.List;

public class Dragao extends Creature{
    public Dragao(int id, int x, int y, String orientacao, Equipa equipa) {
        super(id, x, y, orientacao, equipa, 9);
    }
    public Dragao(String id, String x, String y, String orientacao, Equipa equipa) {
        super(id, x, y, orientacao, equipa, 9);
    }

    @Override
    public boolean rotate() {
        if (orientacao.equals("Norte")) {
            orientacao = "Nordeste";
            return true;
        }
        else if (orientacao.equals("Nordeste")) {
            orientacao = "Este";
            return true;
        }
        else if (orientacao.equals("Este")) {
            orientacao = "Sudeste";
            return true;
        }
        else if (orientacao.equals("Sudeste")) {
            orientacao = "Sul";
            return true;
        }
        else if (orientacao.equals("Sul")) {
            orientacao = "Sudoeste";
            return true;
        }
        else if (orientacao.equals("Sudoeste")) {
            orientacao = "Oeste";
            return true;
        }
        else if (orientacao.equals("Oeste")) {
            orientacao = "Noroeste";
            return true;
        }
        else if (orientacao.equals("Noroeste")) {
            orientacao = "Norte";
            return true;
        }
        return false;
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
                if (this.orientacao.equals("Nordeste") && c.getPosicao()[0] == posicao[0]+3 && c.getPosicao()[1] == posicao[1]-3) {
                    return false;
                }
                if (this.orientacao.equals("Sudeste") && c.getPosicao()[0] == posicao[0]+3 && c.getPosicao()[1] == posicao[1]+3) {
                    return false;
                }
                if (this.orientacao.equals("Noroeste") && c.getPosicao()[0] == posicao[0]-3 && c.getPosicao()[1] == posicao[1]-3) {
                    return false;
                }
                if (this.orientacao.equals("Sudoeste") && c.getPosicao()[0] == posicao[0]-3 && c.getPosicao()[1] == posicao[1]+3) {
                    return false;
                }
            }
        }
        if (orientacao.equals("Norte") && posicao[1]>2) {
            this.movimentaNorte();
            return true;
        }
        else if (orientacao.equals("Sul") && posicao[1]<FandeisiaGameManager.rows-3){
            this.movimentaSul();
            return true;
        }
        else if (orientacao.equals("Oeste") && posicao[0]>2){
            this.movimentaOeste();
            return true;
        }
        else if (orientacao.equals("Este") && posicao[0]<FandeisiaGameManager.columns-3){
            this.movimentaLeste();
            return true;
        }
        else if (orientacao.equals("Nordeste") && posicao[0]<FandeisiaGameManager.columns-3 && posicao[1]>2){
            movimentaNordeste();
            return true;
        }
        else if (orientacao.equals("Noroeste") && posicao[0]>2 && posicao[1]>2){
            movimentaNoroeste();
            return true;
        }
        else if (orientacao.equals("Sudeste") && posicao[0]<FandeisiaGameManager.columns-3 && posicao[1]<FandeisiaGameManager.rows-3){
            movimentaSudeste();
            return true;
        }
        else if (orientacao.equals("Sudoeste") && posicao[0]>2 && posicao[1]<FandeisiaGameManager.rows-3){
            movimentaSudoeste();
            return true;
        }

        return false;
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
                if (this.orientacao.equals("Nordeste") && c.getPosicao()[0] == posicao[0]+3 && c.getPosicao()[1] == posicao[1]-3) {
                    return new int[]{posicao[0], posicao[1]};
                }
                if (this.orientacao.equals("Sudeste") && c.getPosicao()[0] == posicao[0]+3 && c.getPosicao()[1] == posicao[1]+3) {
                    return new int[]{posicao[0], posicao[1]};
                }
                if (this.orientacao.equals("Noroeste") && c.getPosicao()[0] == posicao[0]-3 && c.getPosicao()[1] == posicao[1]-3) {
                    return new int[]{posicao[0], posicao[1]};
                }
                if (this.orientacao.equals("Sudoeste") && c.getPosicao()[0] == posicao[0]-3 && c.getPosicao()[1] == posicao[1]+3) {
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
        else if (orientacao.equals("Nordeste") && posicao[0]<FandeisiaGameManager.columns-3 && posicao[1]>2){
            return new int[]{posicao[0]+3, posicao[1]-3};
        }
        else if (orientacao.equals("Noroeste") && posicao[0]>2 && posicao[1]>2){
            return new int[]{posicao[0]-3, posicao[1]-3};
        }
        else if (orientacao.equals("Sudeste") && posicao[0]<FandeisiaGameManager.columns-3 && posicao[1]<FandeisiaGameManager.rows-3){
            return new int[]{posicao[0]+3, posicao[1]+3};
        }
        else if (orientacao.equals("Sudoeste") && posicao[0]>2 && posicao[1]<FandeisiaGameManager.rows-3){
            return new int[]{posicao[0]-3, posicao[1]+3};
        }
        return new int[]{posicao[0], posicao[1]};
    }

    void movimentaSul() {
        posicao[1]+=3;
    }
    void movimentaNorte() {
        posicao[1]-=3;
    }
    void movimentaOeste() {
        posicao[0]-=3;
    }
    void movimentaLeste() {
        posicao[0]+=3;
    }
    void movimentaNordeste() {
        posicao[1]-=3;
        posicao[0]+=3;
        this.km++;
    }
    void movimentaNoroeste() {
        posicao[0]-=3;
        posicao[1]-=3;
        this.km++;
    }
    void movimentaSudeste() {
        posicao[0]+=3;
        posicao[1]+=3;
        this.km++;
    }
    void movimentaSudoeste() {
        posicao[0]-=3;
        posicao[1]+=3;
        this.km++;
    }

    @Override
    public String getImagePNG() {
        return "Dragao.png";
    }

}
