package pt.ulusofona.lp2.fandeisiaGame;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

public class TestsMain {

    @Test
    public void testMap() {
        FandeisiaGameManager gameManager = new FandeisiaGameManager();
        StatsTiposCriatura c1 = new StatsTiposCriatura("Elfo", 1, 2);
        StatsTiposCriatura c2 = new StatsTiposCriatura("Humano", 2, 2);

        List<StatsTiposCriatura> stats = new ArrayList<>();

        stats.add(c1);
        stats.add(c2);
        List<String> res = new ArrayList<>();

        res = stats.stream()
                .sorted((t1, t2) -> (int) (t2.getTesouros() - t1.getTesouros()))
                .sorted((t1, t2) -> (int) (t1.getCount() - t2.getCount()))
                .map(statsTiposCriatura -> statsTiposCriatura.toString())
                .collect(Collectors.toList());

        assertEquals("Elfo:1:2", res.get(0));
    }

    @Test
    public void testEntidade() {
        Equipa equipa = new Equipa(1, "TestTeam", 0);
        assertEquals(1, equipa.getId());
        assertEquals("TestTeam", equipa.nome);
    }

    @Test
    public void testScore() {
        Equipa equipa = new Equipa(1, "TestTeam", 0);
        assertEquals(0, equipa.getScore());
        equipa.addScore(1);
        assertEquals(1, equipa.getScore());
    }

    @Test
    public void testToString() {
        Equipa equipa = new Equipa(1, "TestTeam", 0);
        assertEquals("TestTeam", equipa.toString());
    }

    @Test
    public void testMultipleParameters() {
        Equipa equipa = new Equipa(1, "TestTeam", 50);
        assertEquals(1, equipa.getId());
        assertEquals(50, equipa.getMoedas());
        assertEquals(0, equipa.getScore());
        equipa.compra(25);
        assertEquals(25, equipa.getMoedas());
        equipa.addScore(5);
        assertEquals(5, equipa.getScore());
        String expected = "id: 1, nome: TestTeam, pontos: 5, moedas: 25";
        assertEquals(expected, equipa.writeToString());
    }

    @Test
    public void testProcessaTurno1() throws InsufficientCoinsException {
        String s1 = "id: 1, type: Anão, teamId: 10, x: 1, y: 1, orientation: Sul";
        String s2 = "id: 2, type: Anão, teamId: 20, x: 4, y: 3, orientation: Norte";
        String[] s = {s1, s2};
        FandeisiaGameManager gameManager = new FandeisiaGameManager();
        gameManager.startGame(s, 5, 7);
        gameManager.processTurn();
        int id = gameManager.getElementId(1, 1);
        assertEquals(0, id);
        id = gameManager.getElementId(4, 3);
        assertEquals(0, id);
        gameManager.processTurn();
        id = gameManager.getElementId(4, 3);
        assertEquals(0, id);
        id = gameManager.getElementId(1, 2);
        assertEquals(1, id);
    }

    @Test
    public void testProcessaTurno2() throws InsufficientCoinsException {
        String s1 = "id: 1, type: Elfo, teamId: 10, x: 1, y: 1, orientation: Este";
        String s2 = "id: 2, type: Elfo, teamId: 20, x: 3, y: 1, orientation: Oeste";
        String s3 = "id: 3, type: gold, x: 2, y: 1";
        String[] s = {s1, s2, s3};
        FandeisiaGameManager gameManager = new FandeisiaGameManager();
        gameManager.startGame(s, 9, 11);
        gameManager.processTurn();
        List<Creature> c = gameManager.getCreatures();
        Creature j = null;
        for (Creature creature : c) {
            if (creature.getId() == 1) {
                j = creature;
            }
        }
        assertEquals(0, j.tesouroAchado());
    }

    @Test
    public void testProcessaTurno3() throws InsufficientCoinsException {
        FandeisiaGameManager gameManager = new FandeisiaGameManager();
        String[] conteudoMundo = new String[3];
        conteudoMundo[0] = "id: 1, type: Elfo, teamId: 10, x: 0, y: 0, orientation: Norte";
        conteudoMundo[1] = "id: 2, type: Elfo, teamId: 20, x: 1, y: 1, orientation: Sul";
        conteudoMundo[2] = "id: -1, type: gold, x: 2, y: 2";
        gameManager.startGame(conteudoMundo, 3, 3);
        // ver se as criaturas e o tesouro estão nos sítios esperados no arranque:
        assertEquals(1, gameManager.getElementId(0, 0));
        assertEquals(2, gameManager.getElementId(1, 1));
        assertEquals(-1, gameManager.getElementId(2, 2));
        // processar 1 turno
        gameManager.processTurn();
        // ver se as criaturas e o tesouro estão nos sítios esperados depois do primeiro turno
        assertEquals(1, gameManager.getElementId(0, 0));  // continua no mesmo sítio
        assertEquals(2, gameManager.getElementId(1, 1));  // ficou vazio (o ID=2 moveu para 1,2)
        assertEquals(0, gameManager.getElementId(1, 2));
        assertEquals(-1, gameManager.getElementId(2, 2));  // não foi apanhado
    }

    @Test
    public void testProcessaTurno4() throws InsufficientCoinsException {
        FandeisiaGameManager gameManager = new FandeisiaGameManager();
        String s1 = "id: 1, type: pika, teamId: 0, x: 1, y: 1, orientation: Este";
        String s2 = "id: 2, type: pika, teamId: 0, x: 1, y: 3, orientation: Oeste";
        String[] s = {s1, s2};
        gameManager.startGame(s, 5, 7);
        gameManager.processTurn();
        assertEquals(0, gameManager.getElementId(1, 1));
    }

    @Test
    public void testGetCoinTotal() {
        FandeisiaGameManager a= new FandeisiaGameManager();

        int expected2= 39;
        Equipa ldr = new Equipa(10, "LDR", 50);
        ldr.compra(3);
        ldr.compra(8);
        a.getCoinTotal(10);

        assertEquals(expected2,ldr.getMoedas());
    }

    @Test
    public void custoCriatura() {

        Equipa equipa = new Equipa(10, "ldr", 50);

        Feitico f= new ReduzAlcance(2,0,1);

        Creature a = new Anao(1, 0, 1, "Sul", equipa);

        assertEquals("Anão",a.getTipo());
        assertEquals(49,equipa.getMoedas()-a.getCusto());

    }

    @Test
    public void testEntidadeToString(){
        Equipa ldr = new Equipa(0, "LDR", 0);
        Creature creature = new Elfo(1, 1, 1, "Norte", ldr);
        String expected = "1 | Elfo | 0 | 0 @ (1, 1) Norte";
        assertEquals(expected, creature.toString());
    }

    @Test
    public void testAddTesouroEgetTesouro(){
        Creature creature = new Elfo(1, 1, 1, "Norte", null);
        creature.addBronze(1);
        assertEquals(1, creature.tesouroAchado());
    }

    @Test
    public void testRotate(){
        Creature creature = new Elfo(1, 1, 1, "Norte", null);
        creature.rotate();
        assertEquals("Nordeste", creature.orientacao);
    }

    @Test
    public void testGetPosicao(){
        Creature creature = new Elfo(1, 1, 1, "Norte", null);
        int[] expected = {1, 1};
        assertEquals(expected[0], creature.getPosicao()[0]);
        assertEquals(expected[1], creature.getPosicao()[1]);
    }

    @Test
    public void testMoveEPredictMove() {
        Creature creature = new Anao(1, 1, 1, "Norte", null);
        List<Creature> lista = new ArrayList<>();
        lista.add(creature);
        int[] expected = {1, 0};
        int[] obtained = creature.predictMove(lista);
        assertEquals(expected[0], obtained[0]);
        assertEquals(expected[1], obtained[1]);
        assertTrue(creature.move(lista));
    }

    @Test
    public void testEquipa() {
        Creature creature = new Anao(1, 1, 1, "Norte", null);
        assertNull(creature.getEquipa());
    }

    @Test
    public void testEntidadeTreasure(){
        Treasure tesouro = new Treasure("1", "gold", "1", "1");
        Treasure tesouro2 = new Treasure("2", "silver", "1", "2");
        Treasure tesouro3 = new Treasure("3", "bronze", "1", "3");
        assertEquals(1, tesouro.getId());
        assertEquals(1, tesouro.x);
        assertEquals(1, tesouro.y);
        assertEquals("gold", tesouro.tipo);
        assertEquals("silver", tesouro2.tipo);
        assertEquals("bronze", tesouro3.tipo);
    }

    @Test
    public void testGetPosition(){
        Treasure tesouro = new Treasure("1", "gold", "1", "1");
        int[] expected = {1, 1};
        assertEquals(expected[0], tesouro.getPosition()[0]);
        assertEquals(expected[1], tesouro.getPosition()[1]);
    }

    @Test
    public void testGetId(){
        Treasure tesouro = new Treasure("1", "gold", "1", "1");
        assertEquals(1, tesouro.getId());
    }

    @Test
    public void testPontos() {
        Treasure tesouro = new Treasure("1", "gold", "1", "1");
        assertEquals(3, tesouro.getPontos());
    }

    @Test
    public void testCompare() {
        Treasure tesouro1 = new Treasure("1", "gold", "1", "1");
        Treasure tesouro2 = new Treasure("2", "gold", "2", "1");
        List<Treasure> lista = new ArrayList<>();
        lista.add(tesouro1);
        lista.add(tesouro2);
        Collections.sort(lista);
        assertEquals(tesouro1, lista.get(0));
        assertEquals(tesouro2, lista.get(1));
    }

    @Test
    public void testFeitico1() {
        Feitico feitico = new Congela(1, 1, 1);
        assertEquals(1, feitico.getCusto());
        assertEquals(1, feitico.getPosicao()[0]);
        assertEquals(1, feitico.getPosicao()[1]);
        assertEquals("Congela", feitico.getTipo());
        feitico = new Congela4Ever(1, 1, 1);
        assertEquals(1, feitico.getCusto());
        assertEquals(1, feitico.getPosicao()[0]);
        assertEquals(1, feitico.getPosicao()[1]);
        assertEquals("Congela4Ever", feitico.getTipo());
        feitico = new Descongela(1, 1, 1);
        assertEquals(1, feitico.getCusto());
        assertEquals(1, feitico.getPosicao()[0]);
        assertEquals(1, feitico.getPosicao()[1]);
        assertEquals("Descongela", feitico.getTipo());
        feitico = new DuplicaAlcance(1, 1, 1);
        assertEquals(1, feitico.getCusto());
        assertEquals(1, feitico.getPosicao()[0]);
        assertEquals(1, feitico.getPosicao()[1]);
        assertEquals("DuplicaAlcance", feitico.getTipo());
        feitico = new ReduzAlcance(1, 1, 1);
        assertEquals(1, feitico.getCusto());
        assertEquals(1, feitico.getPosicao()[0]);
        assertEquals(1, feitico.getPosicao()[1]);
        assertEquals("ReduzAlcance", feitico.getTipo());
    }

    @Test
    public void testFeitico2() {
        Feitico feitico = new EmpurraParaNorte(1, 1, 1);
        assertEquals(1, feitico.getCusto());
        assertEquals(1, feitico.getPosicao()[0]);
        assertEquals(1, feitico.getPosicao()[1]);
        assertEquals("EmpurraParaNorte", feitico.getTipo());
        assertFalse(feitico.lancaFeitico(null));
        feitico = new EmpurraParaSul(1, 1, 1);
        assertEquals(1, feitico.getCusto());
        assertEquals(1, feitico.getPosicao()[0]);
        assertEquals(1, feitico.getPosicao()[1]);
        assertEquals("EmpurraParaSul", feitico.getTipo());
        assertFalse(feitico.lancaFeitico(null));
        feitico = new EmpurraParaEste(1, 1, 1);
        assertEquals(1, feitico.getCusto());
        assertEquals(1, feitico.getPosicao()[0]);
        assertEquals(1, feitico.getPosicao()[1]);
        assertEquals("EmpurraParaEste", feitico.getTipo());
        assertFalse(feitico.lancaFeitico(null));
        feitico = new EmpurraParaOeste(1, 1, 1);
        assertEquals(1, feitico.getCusto());
        assertEquals(1, feitico.getPosicao()[0]);
        assertEquals(1, feitico.getPosicao()[1]);
        assertEquals("EmpurraParaOeste", feitico.getTipo());
        assertFalse(feitico.lancaFeitico(null));
    }

    @Test
    public void testImagens() {
        Creature c = new Anao(1, 1, 1, "Norte", null);
        assertEquals("Anao.png", c.getImagePNG());
        c = new Dragao(1, 1, 1, "Norte", null);
        assertEquals("Dragao.png", c.getImagePNG());
        c = new Elfo(1, 1, 1, "Norte", null);
        assertEquals("Elfo.png", c.getImagePNG());
        c = new Gigante(1, 1, 1, "Norte", null);
        assertEquals("Gigante.png", c.getImagePNG());
        c = new Humano(1, 1, 1, "Norte", null);
        assertEquals("Humano.png", c.getImagePNG());
    }

    @Test
    public void testBuraco() {
        Buraco b1 = new Buraco("1", "hole", "1", "1");
        assertEquals(1, b1.getPosicao()[0]);
        assertEquals(1, b1.getPosicao()[1]);
        assertEquals(1, b1.getId());
        assertEquals("id: 1, type: hole, x: 1, y: 1", b1.writeToString());
        Buraco b2 = new Buraco("2", "hole", "1", "2");
        List<Buraco> buracos= new ArrayList<>();
        buracos.add(b1);
        buracos.add(b2);
        Collections.sort(buracos);
        assertEquals(b1, buracos.get(0));
        assertEquals(b2, buracos.get(1));
    }

    @Test
    public void testDuplicaAlcance() {
        FandeisiaGameManager gameManager = new FandeisiaGameManager();
        gameManager.listaFeiticos = new ArrayList<>();
        FandeisiaGameManager.rows = 5;
        FandeisiaGameManager.columns = 5;
        FandeisiaGameManager.criaturas = new ArrayList<>();
        FandeisiaGameManager.buracos = new ArrayList<>();
        FandeisiaGameManager.criaturasLDR = new ArrayList<>();
        FandeisiaGameManager.criaturasResistance = new ArrayList<>();
        gameManager.tesouros = new ArrayList<>();
        gameManager.currentTurn = FandeisiaGameManager.ldr;
        Creature c = new Anao(1, 0, 3, "Sul", FandeisiaGameManager.ldr);
        FandeisiaGameManager.criaturas.add(c);
        FandeisiaGameManager.criaturasLDR.add(c);
        boolean a = gameManager.enchant(0, 3, "DuplicaAlcance");
        assertFalse(a);
        gameManager.processTurn();
        assertEquals(0, c.getPosicao()[0]);
        assertEquals(4, c.getPosicao()[1]);
    }

}
