package pt.ulusofona.lp2.fandeisiaGame;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.*;
import java.util.stream.Collectors;

public class FandeisiaGameManager {

    static final int PLAFOND = 50;
    static Equipa ldr = new Equipa(10, "LDR", PLAFOND);
    static Equipa resistance = new Equipa(20, "RESISTENCIA", PLAFOND);
    static int rows; //static para os movimentos criaturas
    static int columns; //static likewise
    static List<Creature> criaturas; //para os feiticos
    List<Treasure> tesouros;
    static List<Buraco> buracos; //para os feiticos
    Equipa currentTurn;
    int noTreasureRound;
    //"cache"
    static List<Creature> criaturasLDR;
    static List<Creature> criaturasResistance;
    static List<Feitico> listaFeiticos = new ArrayList<>();
    int turns;
    boolean loaded = false;
    boolean intelArt = false; //falso por defeito, AI
    boolean isOver = false;

    public FandeisiaGameManager() {
    } //do we need this?

    // metodos

    public String[][] getCreatureTypes() {

        String[][] res = new String[5][4];
        res[0][0] = "Anão";
        res[0][1] = "Anao.png";
        res[0][2] = "Move-se uma casa por turno";
        res[0][3] = "1";

        res[1][0] = "Elfo";
        res[1][1] = "Elfo.png";
        res[1][2] = "Ágil";
        res[1][3] = "5";

        res[2][0] = "Dragão";
        res[2][1] = "Dragao.png";
        res[2][2] = "Voa por cima dos outros";
        res[2][3] = "9";

        res[3][0] = "Gigante";
        res[3][1] = "Gigante.png";
        res[3][2] = "Alto e com pernas compridas";
        res[3][3] = "5";

        res[4][0] = "Humano";
        res[4][1] = "Humano.png";
        res[4][2] = "Move-se duas casas por turno";
        res[4][3] = "3";
        /*
        //criatura opcional, ai o que vem daqui
        res[5][0] = "Brunnus";
        res[5][1] = "Brunnu.png";
        res[5][2] = "Deus, apanha um tesouro por turno";
        res[5][3] = "45";*/

        return res;
    }

    void processaCriaturas() {
        criaturasLDR = new ArrayList<>();
        criaturasResistance = new ArrayList<>();
        for (Creature c : criaturas) {
            if (c.getEquipa().equals(ldr)) {
                criaturasLDR.add(c);
                ldr.compra(c.getCusto());
            }
            if (c.getEquipa().equals(resistance)) {
                criaturasResistance.add(c);
                resistance.compra(c.getCusto());
            }
        }
        Collections.sort(criaturas);
        Collections.sort(criaturasLDR);
        Collections.sort(criaturasResistance);
    }

    public Creature creatureCreator(String id, String x, String y, String orientation, Equipa equipa, String tipo) {
        Creature retC = null;
        if ("Anão".equals(tipo)) {
            retC = new Anao(id, x, y, orientation, equipa);
        } else if ("Dragão".equals(tipo)) {
            retC = new Dragao(id, x, y, orientation, equipa);
        }
        if ("Elfo".equals(tipo)) {
            retC = new Elfo(id, x, y, orientation, equipa);
        }
        if ("Gigante".equals(tipo)) {
            retC = new Gigante(id, x, y, orientation, equipa);
        }
        if ("Humano".equals(tipo)) {
            retC = new Humano(id, x, y, orientation, equipa);
        }
        /*if ("Brunnus".equals(tipo)) {
            retC = new Brunnus(id, x, y, orientation, equipa);
        }*/
        return retC;
    }

    public void startGame(String[] content, int rows, int columns) throws InsufficientCoinsException {
        isOver = false;
        if (!loaded) {
            ldr = new Equipa(10, "LDR", PLAFOND);
            resistance = new Equipa(20, "RESISTENCIA", PLAFOND);
            turns = 0;
            noTreasureRound = 0;
            currentTurn = ldr;
            listaFeiticos = new ArrayList<>();
        }
        loaded = false;
        FandeisiaGameManager.rows = rows;
        FandeisiaGameManager.columns = columns;
        criaturas = new ArrayList<>();
        tesouros = new ArrayList<>();
        buracos = new ArrayList<>();
        for (String separa : content) {
            if (separa == null) {
                break;
            }
            String[] analise = separa.split(", ");
            if (analise.length < 3) {
                break;
            }
            if (analise[1].contains("gold") || analise[1].contains("silver") || analise[1].contains("bronze")) {
                String id = analise[0].replace("id: ", "");
                String tipo = analise[1].replace("type: ", "");
                String x = analise[2].replace("x: ", "");
                String y = analise[3].replace("y: ", "");
                Treasure t = new Treasure(id, tipo, x, y);
                tesouros.add(t);
            } else if (analise[1].contains("hole")) {  // se for os buracos

                String id = analise[0].replace("id: ", "");
                String type = analise[1].replace("type: ", "");
                String x = analise[2].replace("x: ", "");
                String y = analise[3].replace("y: ", "");
                Buraco b = new Buraco(id, type, x, y);
                buracos.add(b);
            } else if (analise.length == 6) {
                String id = analise[0].replace("id: ", "");
                String type = analise[1].replace("type: ", "");
                String teamId = analise[2].replace("teamId: ", "");
                String x = analise[3].replace("x: ", "");
                String y = analise[4].replace("y: ", "");
                String orientation = analise[5].replace("orientation: ", "");
                if (teamId.equals("10")) {
                    Creature c = creatureCreator(id, x, y, orientation, ldr, type);
                    criaturas.add(c);
                } else if (teamId.equals("20")) {
                    Creature c = creatureCreator(id, x, y, orientation, resistance, type);
                    criaturas.add(c);
                }
            }
        }
        processaCriaturas();
        Collections.sort(buracos);
        Collections.sort(tesouros);
        int custoLDR = criaturasLDR.stream()
                .mapToInt(Creature::getCusto)
                .sum();
        int custoRes = criaturasResistance.stream()
                .mapToInt(Creature::getCusto)
                .sum();
        if (custoLDR > PLAFOND && custoRes > PLAFOND) {
            throw new InsufficientCoinsException("Ambas as equipas com PLAFOND esgotado");
        } else if (custoLDR > PLAFOND) {
            throw new InsufficientCoinsException("LDR esgotou PLAFOND");
        } else if (custoRes > PLAFOND) {
            throw new InsufficientCoinsException("Resistencia esgotou PLAFOND");
        }
    }

    public void setInitialTeam(int teamId) {
        if (teamId == 10) {
            currentTurn = ldr;
        } else {
            currentTurn = resistance;
        }
    }

    public void processTurn() {
        if (isOver) {
            return;
        }
        boolean found = false;
        boolean noTreasure = false;
        boolean tesouroDadoLdr = false;
        boolean tesouroDadoRes = false;
        for (Creature c : criaturas) {
            found = false;
            if (c.getCongelado()) {
                continue;
            }
            int[] predicted = c.predictMove(criaturas);
            if (predicted[0] == c.getPosicao()[0] && predicted[1] == c.getPosicao()[1]) {
                c.rotate();
            } else {
                for (Feitico f : listaFeiticos) {
                    if (f instanceof DuplicaAlcance) {
                        if (f.getPosicao()[0] == c.getPosicao()[0] && f.getPosicao()[1] == c.getPosicao()[1]) {
                            c.move(criaturas);
                        }
                    }
                }
                c.move(criaturas);
            }
            for (Treasure t : tesouros) {
                if (t.getPosition()[0] == c.getPosicao()[0] && t.getPosition()[1] == c.getPosicao()[1]) {
                    c.getEquipa().addScore(t.getPontos());
                    found = true;
                    noTreasure = true;
                    noTreasureRound = 0;
                    if ("gold".equals(t.tipo)) {
                        c.addOuro(t.getPontos());
                        tesouros.remove(t);
                        break;
                    }
                    if ("silver".equals(t.tipo)) {
                        c.addPrata(t.getPontos());
                        tesouros.remove(t);
                        break;
                    }
                    if ("bronze".equals(t.tipo)) {
                        c.addBronze(t.getPontos());
                        tesouros.remove(t);
                        break;
                    }
                    c.addTesouro();
                    //tesouros.remove(t);
                    break;
                }
            }
            if (found && c.getEquipa().equals(ldr) && !tesouroDadoLdr) {
                ldr.addMoedas(1);
                tesouroDadoLdr = true;
            } else if (found && c.getEquipa().equals(resistance) && !tesouroDadoRes) {
                resistance.addMoedas(1);
                tesouroDadoRes = true;
            }
        }
        ldr.addMoedas(1);
        resistance.addMoedas(1);
        if (!noTreasure) {
            noTreasureRound++;
        }
        if (currentTurn.equals(ldr)) {
            currentTurn = resistance;
        } else {
            currentTurn = ldr;
        }
        turns++;
        if (gameIsOver()) {
            isOver = true;
        }
        listaFeiticos.removeIf(f -> !(f instanceof Congela4Ever)); //lambdas já? podemos?
    }

    public List<Creature> getCreatures() {
        return criaturas;
    }

    public boolean gameIsOver() {
        Equipa menorPontos = null;
        Equipa maiorPontos = null;
        int treasureScore = 0;
        int goldCount = 0;
        for (Treasure t : tesouros) {
            if ("gold".equals(t.tipo)) {
                goldCount++;
            }
        }

        if(goldCount == 1) {
            return true;
        }

        if (resistance.getScore() < ldr.getScore()) {
            menorPontos = resistance;
            maiorPontos = ldr;
        } else {

            menorPontos = ldr;
            maiorPontos = resistance;

        }
        if (noTreasureRound == 15) {
            return true;
        }
        for (Treasure t : tesouros) {
            treasureScore += t.getPontos();
        }


        return tesouros.size() == 0 || menorPontos.getScore() + treasureScore < maiorPontos.getScore();
    }

    public List<String> getAuthors() {
        List<String> authors = new ArrayList<String>();
        authors.add("Gabriel Souza");
        authors.add("Nuno Penim");
        return authors;
    }

    public List<String> getResults() {

        List<String> resultadoFinal = new ArrayList<>();

        if (ldr.getScore() == resistance.getScore()) {
            String linha1 = "Welcome to FANDEISIA";
            String linha2 = "Resultado: EMPATE";
            String linha3 = "LDR: " + ldr.getScore();
            String linha4 = "RESISTENCIA: " + resistance.getScore();
            String linha5 = "Nr. de Turnos jogados: " + turns;
            String linha6 = "-----";

            resultadoFinal.add(linha1);
            resultadoFinal.add(linha2);
            resultadoFinal.add(linha3);
            resultadoFinal.add(linha4);
            resultadoFinal.add(linha5);
            resultadoFinal.add(linha6);

            for (Creature c : criaturas) {
                String linha = c.getId() + " : " + c.getTipo() + " : " + c.tesouroOuro() + " : " + c.tesouroPrata() + " : " + c.tesouroBronze() + " : " + c.tesouroAchado();
                resultadoFinal.add(linha);
            }

            return resultadoFinal;
        }
        if (ldr.getScore() > resistance.getScore()) {

            String linha0 = "Welcome to FANDEISIA";
            String linha1 = "Resultado: Vitória da equipa LDR";
            String linha2 = ldr.toString() + ": " + ldr.getScore();
            String linha3 = resistance.toString() + ": " + resistance.getScore();
            String linha4 = "Nr. de Turnos jogados: " + turns;
            String linha5 = "-----";

            resultadoFinal.add(linha0);
            resultadoFinal.add(linha1);
            resultadoFinal.add(linha2);
            resultadoFinal.add(linha3);
            resultadoFinal.add(linha4);
            resultadoFinal.add(linha5);

            for (Creature c : criaturas) {
                String linha = c.getId() + " : " + c.getTipo() + " : " + c.tesouroOuro() + " : " + c.tesouroPrata() + " : " + c.tesouroBronze() + " : " + c.tesouroAchado();
                resultadoFinal.add(linha);
            }

            return resultadoFinal;
        }
        if (ldr.getScore() < resistance.getScore()) {


            String linha0 = "Welcome to FANDEISIA";
            String linha1 = "Resultado: Vitória da equipa RESISTENCIA";
            String linha2 = resistance.toString() + ": " + resistance.getScore();
            String linha3 = ldr.toString() + ": " + ldr.getScore();
            String linha4 = "Nr. de Turnos jogados: " + turns;
            String linha5 = "-----";


            resultadoFinal.add(linha0);
            resultadoFinal.add(linha1);
            resultadoFinal.add(linha2);
            resultadoFinal.add(linha3);
            resultadoFinal.add(linha4);
            resultadoFinal.add(linha5);

            for (Creature c : criaturas) {
                String linha = c.getId() + " : " + c.getTipo() + " : " + c.tesouroOuro() + " : " + c.tesouroPrata() + " : " + c.tesouroBronze() + " : " + c.tesouroAchado();
                resultadoFinal.add(linha);
            }


            return resultadoFinal;
        }
        return new ArrayList<>();
    }

    public int getElementId(int x, int y) {
        for (Buraco b : buracos) {
            if (b.getPosicao()[0] == x && b.getPosicao()[1] == y) {
                return b.getId();
            }
        }
        for (Treasure t : tesouros) {
            if (t.getPosition()[0] == x && t.getPosition()[1] == y) {
                return t.getId();
            }
        }
        for (Creature c : criaturas) {
            if (c.getPosicao()[0] == x && c.getPosicao()[1] == y) {
                return c.getId();
            }
        }
        return 0;
    }

    public Creature getCreatureAt(int x, int y) {
        for (Creature c : criaturas) {
            if (c.getPosicao()[0] == x && c.getPosicao()[1] == y) {
                return c;
            }
        }
        return null;
    }

    public int getCurrentTeamId() {
        return currentTurn.getId();
    }

    public int getCurrentScore(int teamID) {
        if (teamID == 10) {
            return ldr.getScore();
        } else if (teamID == 20) {
            return resistance.getScore();
        }
        return 0;
    }

    public String[][] getSpellTypes() {
        String[] f1 = {"EmpurraParaNorte", "Move a criatura 1 unidade para Norte.", "1"};
        String[] f2 = {"EmpurraParaEste", "Move a criatura 1 unidade para Este.", "1"};
        String[] f3 = {"EmpurraParaSul", "Move a criatura 1 unidade para Sul.", "1"};
        String[] f4 = {"EmpurraParaOeste", "Move a criatura 1 unidade para Oeste.", "1"};
        String[] f5 = {"ReduzAlcance", "Reduz o alcance da criatura para: MIN (alcance original, 1)", "2"};
        String[] f6 = {"DuplicaAlcance", "Aumenta o alcance da criatura para o dobro", "3"};
        String[] f7 = {"Congela", "A criatura alvo não se move neste turno.", "3"};
        String[] f8 = {"Congela4Ever", "A criatura não se move mais até ao final do jogo", "10"};
        String[] f9 = {"Descongela", "Inverte a aplicação de um Feitiço Congela4Ever.", "8"};
        return new String[][]{f1, f2, f3, f4, f5, f6, f7, f8, f9};
    }

    public Map<String, Integer> createComputerArmy() {
        int custo = 0;
        int nrCriaturas;
        Map<String, Integer> criaturaspc = new HashMap<String, Integer>();
        do {

            nrCriaturas = (int) (Math.random() * Math.floor(2));
            if (nrCriaturas > 0) {
                custo += nrCriaturas;
                if (custo >= PLAFOND) {
                    break;
                }
                criaturaspc.put("Anão", nrCriaturas);
            }

            nrCriaturas = (int) (Math.random() * Math.floor(2));
            if (nrCriaturas > 0) {
                custo += nrCriaturas * 9;
                if (custo >= PLAFOND) {
                    break;
                }
                criaturaspc.put("Dragão", nrCriaturas);
            }

            nrCriaturas = (int) (Math.random() * Math.floor(2));
            if (nrCriaturas > 0) {
                custo += nrCriaturas * 3;
                if (custo >= PLAFOND) {
                    break;
                }
                criaturaspc.put("Humano", nrCriaturas);
            }

            nrCriaturas = (int) (Math.random() * Math.floor(2));
            if (nrCriaturas > 0) {
                custo += nrCriaturas * 5;
                if (custo >= PLAFOND) {
                    break;
                }
                criaturaspc.put("Gigante", nrCriaturas);
            }

            nrCriaturas = (int) (Math.random() * Math.floor(2));
            if (nrCriaturas > 0) {
                custo += nrCriaturas * 5;
                if (custo >= PLAFOND) {
                    break;
                }
                criaturaspc.put("Elfo", nrCriaturas);
            }

        } while (criaturaspc.size() == 0);
        return criaturaspc;
    }

    public boolean enchant(int x, int y, String spellName) {
        Creature c = getCreatureAt(x, y);
        if (c == null) {
            return false;
        }
        c.setNrFeiticos();
        if ("Congela".equals(spellName)) {
            Feitico fet = new Congela(3, x, y);
            if (fet.lancaFeitico(getCreatureAt(x, y))) {
                if (currentTurn.compra(3)) {
                    listaFeiticos.add(fet);
                    return true;
                }
                return false;
            }
            return false;
        }
        else if ("Congela4Ever".equals(spellName)) {
            Feitico fet = new Congela4Ever(10, x, y);
            if (fet.lancaFeitico(c)) {
                c.setCongelado(true);
                c.setTemFeitico(true);
                if (currentTurn.compra(10)) {
                    listaFeiticos.add(fet);
                    return true;
                }
                return false;
            }
            return false;
        }
        else if ("Descongela".equals(spellName)) {
            Feitico fet = new Descongela(8, x, y);
            if (fet.lancaFeitico(getCreatureAt(x, y))) {

                if (currentTurn.compra(8)) {
                    listaFeiticos.add(fet);
                    return true;
                }
                return false;
            }
            return false;
        }
        else if ("DuplicaAlcance".equals(spellName)) {
            Feitico fet = new DuplicaAlcance(3, x, y);
            if (fet.lancaFeitico(getCreatureAt(x, y))) {

                if (currentTurn.compra(3)) {
                    listaFeiticos.add(fet);
                    return true;
                }
                return false;
            }
            return false;
        }
        else if ("EmpurraParaEste".equals(spellName)) {
            Feitico fet = new EmpurraParaEste(1, x, y);
            for (Buraco b : buracos) {
                if (b.getPosicao()[0] == x + 1 && b.getPosicao()[1] == y) {
                    return false;
                }
            }
            for (Creature k : criaturas) {
                if (k.getPosicao()[0] == x + 1 && k.getPosicao()[1] == y) {
                    return false;
                }
            }
            if (fet.lancaFeitico(c)) {
                if (!currentTurn.compra(1)) {
                    return false;
                }
                listaFeiticos.add(fet);
                for (Treasure t : tesouros) {
                    if (t.getPosition()[0] == c.getPosicao()[0] && t.getPosition()[1] == c.getPosicao()[1]) {
                        c.getEquipa().addScore(t.getPontos());
                        noTreasureRound = 0;
                        if ("gold".equals(t.tipo)) {
                            c.addOuro(t.getPontos());

                            tesouros.remove(t);
                            break;
                        }
                        if ("silver".equals(t.tipo)) {
                            c.addPrata(t.getPontos());
                            tesouros.remove(t);
                            break;
                        }
                        if ("bronze".equals(t.tipo)) {
                            c.addBronze(t.getPontos());
                            tesouros.remove(t);
                            break;
                        }
                        c.addTesouro();
                        tesouros.remove(t);
                        break;
                    }
                }
                return true;
            }
            return false;
        }
        else if ("EmpurraParaNorte".equals(spellName)) {
            Feitico fet = new EmpurraParaNorte(1, x, y);
            for (Buraco b : buracos) {
                if (b.getPosicao()[0] == x && b.getPosicao()[1] == y - 1) {
                    return false;
                }
            }
            for (Creature k : criaturas) {
                if (k.getPosicao()[0] == x && k.getPosicao()[1] == y - 1) {
                    return false;
                }
            }
            if (fet.lancaFeitico(c)) {
                if (!currentTurn.compra(1)) {
                    return false;
                }
                listaFeiticos.add(fet);
                for (Treasure t : tesouros) {
                    if (t.getPosition()[0] == c.getPosicao()[0] && t.getPosition()[1] == c.getPosicao()[1]) {
                        c.getEquipa().addScore(t.getPontos());
                        noTreasureRound = 0;
                        if ("gold".equals(t.tipo)) {
                            c.addOuro(t.getPontos());

                            tesouros.remove(t);
                            break;
                        }
                        if ("silver".equals(t.tipo)) {
                            c.addPrata(t.getPontos());
                            tesouros.remove(t);
                            break;
                        }
                        if ("bronze".equals(t.tipo)) {
                            c.addBronze(t.getPontos());
                            tesouros.remove(t);
                            break;
                        }
                        c.addTesouro();
                        tesouros.remove(t);
                        break;
                    }
                }
                return true;
            }
            return false;
        }
        else if ("EmpurraParaOeste".equals(spellName)) {
            Feitico fet = new EmpurraParaOeste(1, x, y);
            for (Buraco b : buracos) {
                if (b.getPosicao()[0] == x - 1 && b.getPosicao()[1] == y) {
                    return false;
                }
            }
            for (Creature k : criaturas) {
                if (k.getPosicao()[0] == x - 1 && k.getPosicao()[1] == y) {
                    return false;
                }
            }
            if (fet.lancaFeitico(c)) {
                if (!currentTurn.compra(1)) {
                    return false;
                }
                listaFeiticos.add(fet);
                for (Treasure t : tesouros) {
                    if (t.getPosition()[0] == c.getPosicao()[0] && t.getPosition()[1] == c.getPosicao()[1]) {
                        c.getEquipa().addScore(t.getPontos());
                        noTreasureRound = 0;
                        if ("gold".equals(t.tipo)) {
                            c.addOuro(t.getPontos());

                            tesouros.remove(t);
                            break;
                        }
                        if ("silver".equals(t.tipo)) {
                            c.addPrata(t.getPontos());
                            tesouros.remove(t);
                            break;
                        }
                        if ("bronze".equals(t.tipo)) {
                            c.addBronze(t.getPontos());
                            tesouros.remove(t);
                            break;
                        }
                        c.addTesouro();
                        tesouros.remove(t);
                        break;
                    }
                }
                return true;
            }
            return false;
        }
        else if ("EmpurraParaSul".equals(spellName)) {
            Feitico fet = new EmpurraParaSul(1, x, y);
            for (Buraco b : buracos) {
                if (b.getPosicao()[0] == x && b.getPosicao()[1] == y + 1) {
                    return false;
                }
            }
            for (Creature k : criaturas) {
                if (k.getPosicao()[0] == x && k.getPosicao()[1] == y + 1) {
                    return false;
                }
            }
            if (fet.lancaFeitico(c)) {
                if (!currentTurn.compra(1)) {
                    return false;
                }
                listaFeiticos.add(fet);
                for (Treasure t : tesouros) {
                    if (t.getPosition()[0] == c.getPosicao()[0] && t.getPosition()[1] == c.getPosicao()[1]) {
                        c.getEquipa().addScore(t.getPontos());
                        noTreasureRound = 0;
                        if ("gold".equals(t.tipo)) {
                            c.addOuro(t.getPontos());

                            tesouros.remove(t);
                            break;
                        }
                        if ("silver".equals(t.tipo)) {
                            c.addPrata(t.getPontos());
                            tesouros.remove(t);
                            break;
                        }
                        if ("bronze".equals(t.tipo)) {
                            c.addBronze(t.getPontos());
                            tesouros.remove(t);
                            break;
                        }
                        c.addTesouro();
                        tesouros.remove(t);
                        break;
                    }
                }
                return true;
            }
            return false;
        }
        else if ("ReduzAlcance".equals(spellName)) {
            Feitico fet = new ReduzAlcance(2, x, y);
            if (fet.lancaFeitico(getCreatureAt(x, y))) {
                //return false; //diagnosticos

                if (currentTurn.compra(2)) {
                    listaFeiticos.add(fet);
                    return true;
                }
            }
            return false;
        }
        return false;
    }

    static public String getSpell(int x, int y) {

        if (listaFeiticos == null || listaFeiticos.size() == 0) {
            return null;
        }
        for (Feitico f : listaFeiticos) {
            if (f.getPosicao()[0] == x && f.getPosicao()[1] == y) {
                return f.getTipo();
            }
        }

        return null;
    }

    public int getCoinTotal(int teamID) {

        if (teamID == 10) {
            if (ldr == null || criaturasLDR == null) {
                return PLAFOND;
            } else {
                return ldr.getMoedas();
            }
        } else {
            if (resistance == null || criaturasResistance == null) {
                return PLAFOND;
            }
            return resistance.getMoedas();
        }

    }

    public boolean saveGame(File fich) {
        List<String> dados = new ArrayList<>();
        for (Creature c : criaturas) {
            String linha = c.writeToString();
            dados.add(linha);
        }
        for (Treasure t : tesouros) {
            String linha = t.writeToString();
            dados.add(linha);
        }
        for (Buraco b : buracos) {
            String linha = b.writeToString();
            dados.add(linha);
        }
        String finalData = "rows: " + rows + ", columns: " + columns;
        dados.add(ldr.writeToString());
        dados.add(resistance.writeToString());
        dados.add("current: " + currentTurn.getId());
        dados.add("jogadas: " + turns);
        dados.add("jogadas_sem_tesouro: " + noTreasureRound);
        dados.add(finalData);
        for (Feitico f : listaFeiticos) {
            String linha = "";
            dados.add(linha);
        }
        String newLine = System.getProperty("line.separator");
        try {

            FileWriter fw = new FileWriter(fich);
            PrintWriter gravarArq = new PrintWriter(fw);

            for (int i = 0; i < dados.size(); i++) {
                gravarArq.printf(dados.get(i) + newLine);
            }

            fw.close();
        } catch (java.io.IOException e) {
            System.out.println(e.getStackTrace());
            return false;
        }
        return true;
    }

    public boolean loadGame(File fich) {
        List<String> dados = new ArrayList<>();
        try {
            Scanner sc = new Scanner(fich);
            while (sc.hasNextLine()) {
                String linha = sc.nextLine();
                dados.add(linha);
            }
        } catch (java.io.FileNotFoundException e) {
            System.out.println(e.getStackTrace());
            return false;
        }
        String analise = dados.get(dados.size() - 1);
        dados.remove(dados.size() - 1);
        String dims[] = analise.split(", ");
        String x = dims[0].replace("rows: ", "");
        String y = dims[1].replace("columns: ", "");
        Object[] objAr = dados.toArray();
        String[] res = Arrays.copyOf(objAr, objAr.length, String[].class);
        analise = dados.get(dados.size() - 1);
        dados.remove(dados.size() - 1);
        analise = analise.replace("jogadas_sem_tesouro: ", "");
        noTreasureRound = Integer.parseInt(analise);
        analise = dados.get(dados.size() - 1);
        dados.remove(dados.size() - 1);
        analise = analise.replace("jogadas: ", "");
        turns = Integer.parseInt(analise);
        analise = dados.get(dados.size() - 1);
        dados.remove(dados.size() - 1);
        String turn = analise.replace("current: ", "");
        analise = dados.get(dados.size() - 1);
        dados.remove(dados.size() - 1);
        String[] teamAnalisis = analise.split(", ");
        teamAnalisis[0] = teamAnalisis[0].replace("id: ", "");
        teamAnalisis[1] = teamAnalisis[1].replace("nome: ", "");
        teamAnalisis[2] = teamAnalisis[2].replace("pontos: ", "");
        teamAnalisis[3] = teamAnalisis[3].replace("moedas: ", "");
        resistance = new Equipa(teamAnalisis[0], teamAnalisis[1], teamAnalisis[2], teamAnalisis[3]);
        analise = dados.get(dados.size() - 1);
        dados.remove(dados.size() - 1);
        teamAnalisis = analise.split(", ");
        teamAnalisis[0] = teamAnalisis[0].replace("id: ", "");
        teamAnalisis[1] = teamAnalisis[1].replace("nome: ", "");
        teamAnalisis[2] = teamAnalisis[2].replace("pontos: ", "");
        teamAnalisis[3] = teamAnalisis[3].replace("moedas: ", "");
        ldr = new Equipa(teamAnalisis[0], teamAnalisis[1], teamAnalisis[2], teamAnalisis[3]);
        if (Integer.parseInt(turn) == 10) {
            currentTurn = ldr;
        } else {
            currentTurn = resistance;
        }
        loaded = true;
        try {
            startGame(res, Integer.parseInt(x), Integer.parseInt(y));
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public void toggleAI(boolean active) {
        intelArt = active;
    }

    public String whoIsLordEder() {
        //heroi de paris??
        return "Ederzito António Macedo Lopes";
    }

    public Map<String, List<String>> getStatistics() {

        Map<String, List<String>> retMap = new HashMap<>();
        List<String> as3MaisCarregadasSemTesouros = new ArrayList<>();
        List<String> as3MaisCarregadasComTesouros = new ArrayList<>();
        List<String> as5MaisRicas = new ArrayList<>();
        List<String> as3MaisViajadas = new ArrayList<>();
        List<String> osAlvosFavoritos = new ArrayList<>();
        List<String> tiposDeCriaturaESeusTesouros = new ArrayList<>();
        List<String> tiposComTesouros = new ArrayList<>();


        as3MaisCarregadasSemTesouros = criaturas.stream()
                .filter(c -> c.getNrTesouro() == 0)
                .sorted((Creature c1, Creature c2) -> c2.getNrTesouro() - c1.getNrTesouro())
                .map(c -> c.getId() + ":" + c.getNrTesouro())
                .collect(Collectors.toList());

        if (as3MaisCarregadasSemTesouros.size() == 3) {

            as3MaisCarregadasComTesouros = criaturas.stream()
                    .filter(c -> c.getNrTesouro() != 0)
                    .sorted((Creature c1, Creature c2) -> c2.getNrTesouro() - c1.getNrTesouro())
                    .map(c -> c.getId() + ":" + c.getTipo() + ":" + c.getTesouros())
                    .collect(Collectors.toList());

            retMap.put("as3MaisCarregadas", as3MaisCarregadasComTesouros);
        } else {
            as3MaisCarregadasComTesouros = criaturas.stream()
                    .sorted((Creature c1, Creature c2) -> c2.getNrTesouro() - c1.getNrTesouro())
                    .map(c -> c.getId() + ":" + c.getTipo() + ":" + c.getTesouros())
                    .limit(3)
                    .collect(Collectors.toList());

            retMap.put("as3MaisCarregadas", as3MaisCarregadasComTesouros);
        }

        if (criaturas.size() < 5) {
            as5MaisRicas = criaturas.stream()
                    .sorted((Creature c1, Creature c2) -> c2.getTesouros() - c1.getTesouros())
                    .sorted((Creature c1, Creature c2) -> c2.tesouroAchado() - c1.tesouroAchado())
                    .map(c -> c.getId() + ":" + c.tesouroAchado() + ":" + c.getTesouros())
                    .collect(Collectors.toList());
            retMap.put("as5MaisRicas", as5MaisRicas);
        } else {
            as5MaisRicas = criaturas.stream()
                    .sorted((Creature c1, Creature c2) -> c2.getTesouros() - c1.getTesouros())
                    .sorted((Creature c1, Creature c2) -> c2.tesouroAchado() - c1.tesouroAchado())
                    .map(c -> c.getId() + ":" + c.tesouroAchado() + ":" + c.getTesouros())
                    .limit(5)
                    .collect(Collectors.toList());
            retMap.put("as5MaisRicas", as5MaisRicas);
        }


        osAlvosFavoritos = criaturas.stream()
                .sorted((Creature c1, Creature c2) -> c2.getNrFeiticos() - c1.getNrFeiticos())
                .limit(3)
                .map(c -> c.getId() + ":" + c.equipa.getId() + ":" + c.getNrFeiticos())
                .collect(Collectors.toList());

        retMap.put("osAlvosFavoritos", osAlvosFavoritos);

        as3MaisViajadas = criaturas.stream()
                .sorted((Creature c1, Creature c2) -> c2.getKm() - c1.getKm())
                .map(c -> c.getId() + " : " + c.getKm())
                .limit(3)
                .collect(Collectors.toList());

        retMap.put("as3MaisViajadas", as3MaisViajadas);

        List<Creature> anoes = criaturas.stream()
                .filter(creature -> creature instanceof Anao)
                .sorted()
                .collect(Collectors.toList());
        List<Creature> dragoes = criaturas.stream()
                .filter(creature -> creature instanceof Dragao)
                .sorted()
                .collect(Collectors.toList());
        List<Creature> elfos = criaturas.stream()
                .filter(creature -> creature instanceof Elfo)
                .sorted()
                .collect(Collectors.toList());
        List<Creature> gigantes = criaturas.stream()
                .filter(creature -> creature instanceof Gigante)
                .sorted()
                .collect(Collectors.toList());
        List<Creature> humanos = criaturas.stream()
                .filter(creature -> creature instanceof Humano)
                .sorted()
                .collect(Collectors.toList());

        StatsTiposCriatura anao = new StatsTiposCriatura("Anão");
        StatsTiposCriatura dragao = new StatsTiposCriatura("Dragão");
        StatsTiposCriatura elfo = new StatsTiposCriatura("Elfo");
        StatsTiposCriatura gigante = new StatsTiposCriatura("Gigante");
        StatsTiposCriatura humano = new StatsTiposCriatura("Humano");

        if (anoes.size() == 0) {
            anao.updateCount(0);
            anao.updateTesouros(-1);
        } else {
            anao.updateCount(anoes.stream().distinct().count());
            anao.updateTesouros(anoes.stream().mapToInt(Creature::getTesouros).sum());
        }
        if (dragoes.size() == 0) {
            dragao.updateCount(0);
            dragao.updateTesouros(-1);
        } else {
            dragao.updateCount(dragoes.stream().distinct().count());
            dragao.updateTesouros(dragoes.stream().mapToInt(Creature::getTesouros).sum());
        }
        if (elfos.size() == 0) {
            elfo.updateCount(0);
            elfo.updateTesouros(-1);
        } else {
            elfo.updateCount(elfos.stream().distinct().count());
            elfo.updateTesouros(elfos.stream().mapToInt(Creature::getTesouros).sum());
        }
        if (gigantes.size() == 0) {
            gigante.updateCount(0);
            gigante.updateTesouros(-1);
        } else {
            gigante.updateCount(gigantes.stream().distinct().count());
            gigante.updateTesouros(gigantes.stream().mapToInt(Creature::getTesouros).sum());
        }
        if (humanos.size() == 0) {
            humano.updateCount(0);
            humano.updateTesouros(-1);
        } else {
            humano.updateCount(humanos.stream().distinct().count());
            humano.updateTesouros(humanos.stream().mapToInt(Creature::getTesouros).sum());
        }
        List<StatsTiposCriatura> stats = new ArrayList<>();
        stats.add(anao);
        stats.add(dragao);
        stats.add(elfo);
        stats.add(gigante);
        stats.add(humano);


        tiposDeCriaturaESeusTesouros = stats.stream()
                .sorted((t1, t2) -> (int) ((int) t1.getCount() - t2.getCount()))
                .sorted((t1, t2) -> (int) (t2.getTesouros() - t1.getTesouros()))
                .map(StatsTiposCriatura::toString)
                .collect(Collectors.toList());

        retMap.put("tiposDeCriaturaESeusTesouros", tiposDeCriaturaESeusTesouros);

        tiposComTesouros = null;
        int anaoCount = 0;
        int dragaoCount = 0;
        int elfoCount = 0;
        int giganteCount = 0;
        int humanoCount = 0;
        boolean anaoA = false;
        boolean dragaoA = false;
        boolean elfoA = false;
        boolean giganteA = false;
        boolean humanoA = false;
        for (Creature c : criaturas) {
            if (c.getTipo().equals("Anão")) {
                anaoA = true;
                anaoCount += c.getTesouros();
            }
            if (c.getTipo().equals("Dragão")) {
                dragaoA = true;
                dragaoCount += c.getTesouros();
            }
            if (c.getTipo().equals("Elfo")) {
                elfoA = true;
                elfoCount += c.getTesouros();
            }
            if (c.getTipo().equals("Gigante")) {
                giganteA = true;
                giganteCount += c.getTesouros();
            }
            if (c.getTipo().equals("Humano")) {
                humanoA = true;
                humanoCount += c.getTesouros();
            }
        }
        List<String> buffer = new ArrayList<>();
        if (anaoA && anaoCount > 0) {
            buffer.add("Anão");
        }
        if (dragaoA && dragaoCount > 0) {
            buffer.add("Dragão");
        }
        if (giganteA && giganteCount > 0) {
            buffer.add("Gigante");
        }
        if (elfoA && elfoCount > 0) {
            buffer.add("Elfo");
        }
        if (humanoA && humanoCount > 0) {
            buffer.add("Humano");
        }
        buffer.sort(String::compareTo);
        retMap.put("tiposComTesouros", buffer);



        return retMap;

    }

}