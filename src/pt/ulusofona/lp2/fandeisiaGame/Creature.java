package pt.ulusofona.lp2.fandeisiaGame;

import java.util.List;

public class Creature implements Comparable<Creature>{
    int id;
    int[] posicao = new int[2];
    String orientacao;
    int tesouros;
    int tesourosOuro;
    int tesourosPrata;
    int tesourosBronze;
    Equipa equipa;
    String figuras;
    int custo;
    boolean congelado;
    boolean temFeitico;
    int pontos;
    int nrFeiticos = 0;
    int km =0;

    public Creature(int id, int x, int y, String orientacao, Equipa equipa, int custo) {
        this.id = id;
        posicao[0] = x;
        posicao[1] = y;
        this.orientacao = orientacao;
        this.equipa = equipa;
        this.custo = custo;
        this.congelado = false;
        tesouros = 0;
        tesourosOuro=0;
        tesourosPrata=0;
        tesourosBronze=0;

        figuras = this.getTipo() + ".png";
    }

    public Creature(String id, String x, String y, String orientacao, Equipa equipa, int custo) {
        this.id = Integer.parseInt(id);
        posicao[0] = Integer.parseInt(x);
        posicao[1] = Integer.parseInt(y);
        this.orientacao = orientacao;
        this.equipa = equipa;
        this.custo = custo;
        this.congelado = false;
        tesouros = 0;
        tesouros = 0;
        tesourosOuro=0;
        tesourosPrata=0;
        tesourosBronze=0;

        figuras = this.getTipo();
    }

    public int getCusto() {
        return custo;
    }

    public String getTipo() {
        if(this instanceof Anao){
            return "Anão";
        }
        /*else if(this instanceof Brunnus){
            return "Brunnu";
        }*/
        else if(this instanceof Dragao){
            return "Dragão";
        }
        else if(this instanceof Elfo){
            return "Elfo";
        }
        else if(this instanceof Gigante){
            return "Gigante";
        }
        else if(this instanceof Humano){
            return "Humano";
        }
        return "Criatura";
    }

    public int getId() {
        return id;
    }

    public int[] getPosicao() {
        return posicao;
    }

    public boolean getCongelado() {
        if (congelado){
            return true;
        }
        return false;

    }

    public void setCongelado(boolean congelado) {
        this.congelado = congelado;
    }

    public void setTemFeitico(boolean temFeitico) {

        this.temFeitico = temFeitico;
    }

    public int setNrFeiticos() {
        return nrFeiticos++;
    }

    public int getNrFeiticos() {
        return nrFeiticos;
    }

    public boolean rotate() {

            if (orientacao.equals("Norte")) {
                orientacao = "Este";
                return true;
            } else if (orientacao.equals("Este")) {
                orientacao = "Sul";
                return true;
            } else if (orientacao.equals("Sul")) {
                orientacao = "Oeste";
                return true;
            } else if (orientacao.equals("Oeste")) {
                orientacao = "Norte";
                return true;
            }
            return false;
    }

    public int getKm() {
    return km;
    }



    public boolean move(List<Creature> criaturas) {
            for (Creature c : criaturas) {
                    if (!c.equals(this)) {
                        if (this.orientacao.equals("Norte") && c.getPosicao()[0] == posicao[0] && c.getPosicao()[1] == posicao[1] - 1) {
                            return false;
                        }
                        if (this.orientacao.equals("Sul") && c.getPosicao()[0] == posicao[0] && c.getPosicao()[1] == posicao[1] + 1) {
                            return false;
                        }
                        if (this.orientacao.equals("Este") && c.getPosicao()[0] == posicao[0] + 1 && c.getPosicao()[1] == posicao[1]) {
                            return false;
                        }
                        if (this.orientacao.equals("Oeste") && c.getPosicao()[0] == posicao[0] - 1 && c.getPosicao()[1] == posicao[1]) {
                            return false;
                        }
                    }

            }
            if (FandeisiaGameManager.buracos != null) {
                for (Buraco c : FandeisiaGameManager.buracos) {
                    if (this.orientacao.equals("Norte") && c.getPosicao()[0] == posicao[0] && c.getPosicao()[1] == posicao[1] - 1) {
                        return false;
                    }
                    if (this.orientacao.equals("Sul") && c.getPosicao()[0] == posicao[0] && c.getPosicao()[1] == posicao[1] + 1) {
                        return false;
                    }
                    if (this.orientacao.equals("Este") && c.getPosicao()[0] == posicao[0] + 1 && c.getPosicao()[1] == posicao[1]) {
                        return false;
                    }
                    if (this.orientacao.equals("Oeste") && c.getPosicao()[0] == posicao[0] - 1 && c.getPosicao()[1] == posicao[1]) {
                        return false;
                    }
                }
            }
            if (orientacao.equals("Norte") && posicao[1] > 0) {
                movimentaNorte();
                return true;
            } else if (orientacao.equals("Sul") && posicao[1] < FandeisiaGameManager.rows - 1) {
                movimentaSul();

                return true;
            } else if (orientacao.equals("Oeste") && posicao[0] > 0) {
                movimentaOeste();

                return true;
            } else if (orientacao.equals("Este") && posicao[0] < FandeisiaGameManager.columns - 1) {
                movimentaLeste();

                return true;
            }

        return false;

    }

    public int[] predictMove(List<Creature> criaturas) {

            for (Creature c : criaturas) {
                if (!c.equals(this)) {
                    if (this.orientacao.equals("Norte") && c.getPosicao()[0] == posicao[0] && c.getPosicao()[1] == posicao[1] - 1) {
                        return new int[]{posicao[0], posicao[1]};
                    }
                    if (this.orientacao.equals("Sul") && c.getPosicao()[0] == posicao[0] && c.getPosicao()[1] == posicao[1] + 1) {
                        return new int[]{posicao[0], posicao[1]};
                    }
                    if (this.orientacao.equals("Este") && c.getPosicao()[0] == posicao[0] + 1 && c.getPosicao()[1] == posicao[1]) {
                        return new int[]{posicao[0], posicao[1]};
                    }
                    if (this.orientacao.equals("Oeste") && c.getPosicao()[0] == posicao[0] - 1 && c.getPosicao()[1] == posicao[1]) {
                        return new int[]{posicao[0], posicao[1]};
                    }
                }
            }
            if (FandeisiaGameManager.buracos != null) {
                for (Buraco c : FandeisiaGameManager.buracos) {
                    if (this.orientacao.equals("Norte") && c.getPosicao()[0] == posicao[0] && c.getPosicao()[1] == posicao[1] - 1) {
                        return new int[]{posicao[0], posicao[1]};
                    }
                    if (this.orientacao.equals("Sul") && c.getPosicao()[0] == posicao[0] && c.getPosicao()[1] == posicao[1] + 1) {
                        return new int[]{posicao[0], posicao[1]};
                    }
                    if (this.orientacao.equals("Este") && c.getPosicao()[0] == posicao[0] + 1 && c.getPosicao()[1] == posicao[1]) {
                        return new int[]{posicao[0], posicao[1]};
                    }
                    if (this.orientacao.equals("Oeste") && c.getPosicao()[0] == posicao[0] - 1 && c.getPosicao()[1] == posicao[1]) {
                        return new int[]{posicao[0], posicao[1]};
                    }
                }
            }
            if (orientacao.equals("Norte") && posicao[1] > 0) {
                return new int[]{posicao[0], posicao[1] - 1};
            } else if (orientacao.equals("Sul") && posicao[1] < FandeisiaGameManager.rows - 1) {
                return new int[]{posicao[0], posicao[1] + 1};
            } else if (orientacao.equals("Oeste") && posicao[0] > 0) {
                return new int[]{posicao[0], posicao[1] - 1};
            } else if (orientacao.equals("Este") && posicao[0] < FandeisiaGameManager.columns - 1) {
                return new int[]{posicao[0], posicao[1] + 1};
            }
        return new int[]{posicao[0], posicao[1]};
    }

    public void addTesouro() {
        tesouros++;
        pontos++;
    }

    public void addOuro(int pontos) {
        tesourosOuro+=pontos;
        this.addTesouro();
    }
    public void addPrata(int pontos) {
        tesourosPrata+=pontos;
        this.addTesouro();
    }
    public void addBronze(int pontos) {
        tesourosBronze+=pontos;
        this.addTesouro();
    }

    public int getPontos() {
        return this.pontos;
    }

    public int tesouroOuro() { return tesourosOuro/3; }
    public int tesouroPrata() { return tesourosPrata/2;}
    public int tesouroBronze() { return tesourosBronze; }

    public int getNrTesouro() {
        return tesouroOuro() + tesouroPrata() + tesouroBronze();
    }

    public int tesouroAchado() {
        return this.tesourosOuro + this.tesourosPrata + this.tesourosBronze;
    }

    public int getTesouros() {
        return tesouros;
    }

    public int getOuro() {
        return this.tesourosOuro;
    }

    public int getPrata() {
        return this.tesourosPrata;
    }

    public int getBronze() {
        return this.tesourosBronze;
    }

    public String getImagePNG() {
        return figuras;
    }

    public Equipa getEquipa() {
        return equipa;
    }

    public String toString() {
        return id + " | " + this.getTipo() + " | " + equipa.getId() + " | " + tesouros + " @ (" + posicao[0] + ", " + posicao[1] + ") " + orientacao;
    }

    public int compareTo(Creature c){
        return this.id - c.id;
    }

    public String writeToString() {
        String ret = "id: " + id + ", " + "type: " + this.getTipo() + ", ";
        ret += "teamId: " + this.getEquipa().getId() + ", " + "x: " + posicao[0] + ", ";
        ret += "y: " + posicao[1] + ", " + "orientation: " + orientacao;
        return ret;
    }

    //Gabriel, acesso privado de propósito! Não quero usar estas funções fora da classe, perigoso.

    //Mudança de planos, os feiticos precisam disto
    void movimentaSul() {
        posicao[1]++;
        this.km++;
    }
    void movimentaNorte() {
        posicao[1]--;
        this.km++;
    }
    void movimentaOeste() {
        posicao[0]--;
        this.km++;
    }
    void movimentaLeste() {
        posicao[0]++;
        this.km++;
    }

    void feiticoSul() {
        posicao[1]++;
    }
    void feiticoNorte() {
        posicao[1]--;
    }
    void feiticoOeste() {
        posicao[0]--;
    }
    void feiticoLeste() {
        posicao[0]++;
    }


}