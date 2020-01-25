package pt.ulusofona.lp2.fandeisiaGame;

import java.util.List;

public class Elfo extends Creature {

    public Elfo(String id, String x, String y, String orientacao, Equipa equipa) {
        super(id, x, y, orientacao, equipa, 5);
    }
    public Elfo(int id, int x, int y, String orientacao, Equipa equipa) {
        super(id, x, y, orientacao, equipa, 5);
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
                if (this.orientacao.equals("Nordeste") && c.getPosicao()[0] == posicao[0]+2 && c.getPosicao()[1] == posicao[1]-2) {
                    return false;
                }
                if (this.orientacao.equals("Sudeste") && c.getPosicao()[0] == posicao[0]+2 && c.getPosicao()[1] == posicao[1]+2) {
                    return false;
                }
                if (this.orientacao.equals("Noroeste") && c.getPosicao()[0] == posicao[0]-2 && c.getPosicao()[1] == posicao[1]-2) {
                    return false;
                }
                if (this.orientacao.equals("Sudoeste") && c.getPosicao()[0] == posicao[0]-2 && c.getPosicao()[1] == posicao[1]+2) {
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
                if (this.orientacao.equals("Nordeste") && c.getPosicao()[0] == posicao[0]+2 && c.getPosicao()[1] == posicao[1]-2) {
                    return false;
                }
                if (this.orientacao.equals("Sudeste") && c.getPosicao()[0] == posicao[0]+2 && c.getPosicao()[1] == posicao[1]+2) {
                    return false;
                }
                if (this.orientacao.equals("Noroeste") && c.getPosicao()[0] == posicao[0]-2 && c.getPosicao()[1] == posicao[1]-2) {
                    return false;
                }
                if (this.orientacao.equals("Sudoeste") && c.getPosicao()[0] == posicao[0]-2 && c.getPosicao()[1] == posicao[1]+2) {
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
        else if (orientacao.equals("Nordeste") && posicao[0]<FandeisiaGameManager.columns-2 && posicao[1]>1){
            movimentaNordeste();
            return true;
        }
        else if (orientacao.equals("Noroeste") && posicao[0]>1 && posicao[1]>1){
            movimentaNoroeste();
            return true;
        }
        else if (orientacao.equals("Sudeste") && posicao[0]<FandeisiaGameManager.columns-2 && posicao[1]<FandeisiaGameManager.rows-2){
            movimentaSudeste();
            return true;
        }
        else if (orientacao.equals("Sudoeste") && posicao[0]>1 && posicao[1]<FandeisiaGameManager.rows-2){
            movimentaSudoeste();
            return true;
        }

        return false;
    }

    @Override
    public int[] predictMove(List<Creature> criaturas) {
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
                if (this.orientacao.equals("Nordeste") && c.getPosicao()[0] == posicao[0]+2 && c.getPosicao()[1] == posicao[1]-2) {
                    return new int[]{posicao[0], posicao[1]};
                }
                if (this.orientacao.equals("Sudeste") && c.getPosicao()[0] == posicao[0]+2 && c.getPosicao()[1] == posicao[1]+2) {
                    return new int[]{posicao[0], posicao[1]};
                }
                if (this.orientacao.equals("Noroeste") && c.getPosicao()[0] == posicao[0]-2 && c.getPosicao()[1] == posicao[1]-2) {
                    return new int[]{posicao[0], posicao[1]};
                }
                if (this.orientacao.equals("Sudoeste") && c.getPosicao()[0] == posicao[0]-2 && c.getPosicao()[1] == posicao[1]+2) {
                    return new int[]{posicao[0], posicao[1]};
                }
            }
        }
        for(Creature c : criaturas) {
            if (!c.equals(this)) {
                if (this.orientacao.equals("Norte") && c.getPosicao()[0] == posicao[0] && c.getPosicao()[1] == posicao[1] - 2) {
                    return new int[]{posicao[0], posicao[1]};
                }
                if (this.orientacao.equals("Sul") && c.getPosicao()[0] == posicao[0] && c.getPosicao()[1] == posicao[1] + 2) {
                    return new int[]{posicao[0], posicao[1]};
                }
                if (this.orientacao.equals("Este") && c.getPosicao()[0] == posicao[0] + 2 && c.getPosicao()[1] == posicao[1]) {
                    return new int[]{posicao[0], posicao[1]};
                }
                if (this.orientacao.equals("Oeste") && c.getPosicao()[0] == posicao[0] - 2 && c.getPosicao()[1] == posicao[1]) {
                    return new int[]{posicao[0], posicao[1]};
                }
                if (this.orientacao.equals("Nordeste") && c.getPosicao()[0] == posicao[0] + 2 && c.getPosicao()[1] == posicao[1] - 2) {
                    return new int[]{posicao[0], posicao[1]};
                }
                if (this.orientacao.equals("Sudeste") && c.getPosicao()[0] == posicao[0] + 2 && c.getPosicao()[1] == posicao[1] + 2) {
                    return new int[]{posicao[0], posicao[1]};
                }
                if (this.orientacao.equals("Noroeste") && c.getPosicao()[0] == posicao[0] - 2 && c.getPosicao()[1] == posicao[1] - 2) {
                    return new int[]{posicao[0], posicao[1]};
                }
                if (this.orientacao.equals("Sudoeste") && c.getPosicao()[0] == posicao[0] - 2 && c.getPosicao()[1] == posicao[1] + 2) {
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
            return new int[]{posicao[0]-2, posicao[1]};
        }
        else if (orientacao.equals("Este") && posicao[0]<FandeisiaGameManager.columns-2){
            return new int[]{posicao[0]+2, posicao[1]};
        }
        else if (orientacao.equals("Nordeste") && posicao[0]<FandeisiaGameManager.columns-2 && posicao[1]>1){
            return new int[]{posicao[0]+2, posicao[1]-2};
        }
        else if (orientacao.equals("Noroeste") && posicao[0]>1 && posicao[1]>1){
            return new int[]{posicao[0]-2, posicao[1]-2};
        }
        else if (orientacao.equals("Sudeste") && posicao[0]<FandeisiaGameManager.columns-2 && posicao[1]<FandeisiaGameManager.rows-2){
            return new int[]{posicao[0]+2, posicao[1]+2};
        }
        else if (orientacao.equals("Sudoeste") && posicao[0]>1 && posicao[1]<FandeisiaGameManager.rows-2){
            return new int[]{posicao[0]-2, posicao[1]+2};
        }
        return new int[]{posicao[0], posicao[1]};
    }

    void movimentaNordeste() {
        posicao[1]-=2;
        posicao[0]+=2;
        this.km++;
    }
    void movimentaNoroeste() {
        posicao[0]-=2;
        posicao[1]-=2;
        this.km++;
    }
    void movimentaSudeste() {
        posicao[0]+=2;
        posicao[1]+=2;
        this.km++;
    }
    void movimentaSudoeste() {
        posicao[0]-=2;
        posicao[1]+=2;
        this.km++;
    }
    @Override
    void movimentaSul() {
        posicao[1]+=2;
    }
    @Override
    void movimentaNorte() {
        posicao[1]-=2;
    }
    @Override
    void movimentaOeste() {
        posicao[0]-=2;
    }
    @Override
    void movimentaLeste() {
        posicao[0]+=2;
    }

    @Override
    public String getImagePNG() {
        return "Elfo.png";
    }

}
