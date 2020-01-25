package pt.ulusofona.lp2.fandeisiaGame;

public class DuplicaAlcance extends Feitico {

    public DuplicaAlcance(int custo, int x, int y) {
        super(custo, x, y);
    }

    @Override
    public boolean lancaFeitico(Creature c) {
        if (c == null) {
            return false;
        }
        int[] distMove = new int[2];
        int[] pos = c.getPosicao();
        int[] pred = c.predictMove(FandeisiaGameManager.criaturas);
        if (pos[0] == pred[0] && pos[1] == pred[1]) {
            return false;
        }
        int[] move = {pred[0] - pos[0], pred[1] - pos[1]};
        distMove[0] = move[0] * 2;
        distMove[1] = move[1] * 2;
        if (distMove[0]+pos[0] > FandeisiaGameManager.columns-1 || distMove[0]+pos[0] < 0 || distMove[1]+pos[1] > FandeisiaGameManager.rows-1 || distMove[1]+pos[1] < 0) {
            return false; //cai fora do mapa
        }
        for (Buraco b : FandeisiaGameManager.buracos) {
            if (b.getPosicao()[0] == distMove[0] + pos[0] && b.getPosicao()[1] == distMove[1] + pos[1]) {
                return false;
            }
        }
        for (Creature k : FandeisiaGameManager.criaturas) {
            if (k.equals(c)) {
                continue;
            }
            if (k.getPosicao()[0] == distMove[0] + pos[0] && k.getPosicao()[1] == distMove[1] + pos[1]) {
                return false;
            }
        }
        return true;
    }
}
