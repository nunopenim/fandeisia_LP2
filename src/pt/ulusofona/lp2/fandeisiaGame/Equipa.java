package pt.ulusofona.lp2.fandeisiaGame;

public class Equipa {
    int teamId;
    String nome;
    int score;
    int moedas;


    public Equipa(int id, String nome, int moedas) {
        teamId = id;
        this.nome = nome;
        this.score = 0;
        this.moedas = moedas;
    }

    public Equipa(String id, String nome, int moedas) {
        teamId = Integer.parseInt(id);
        this.nome = nome;
        this.score = 0;
        this.moedas = moedas;
    }
    public Equipa(String id, String nome, String pontos, String moedas) {
        teamId = Integer.parseInt(id);
        this.nome = nome;
        this.score = Integer.parseInt(pontos);
        this.moedas = Integer.parseInt(moedas);
    }

    public int getId() {
        return teamId;
    }

    public int getMoedas() {
        return moedas;
    }

    public int getScore() {
        return score;
    }

    public boolean compra(int preco){
        if (moedas - preco < 0) {
            return false;
        }
        moedas -= preco;
        return true;
    }

    public void addMoedas(int number){
        this.moedas += number;
    }

    public void addScore(int pontos) {
        score += pontos;
    }

    public String toString(){
        return nome;
    }

    public String writeToString() {
        return "id: " + teamId + ", nome: " + nome + ", pontos: " + score + ", moedas: " + moedas;
    }

    public static Equipa getEquipaById(int teamId) {
        if (teamId == FandeisiaGameManager.ldr.getId()) {
            return FandeisiaGameManager.ldr;
        }
        else {
            return FandeisiaGameManager.resistance;
        }
    }
}