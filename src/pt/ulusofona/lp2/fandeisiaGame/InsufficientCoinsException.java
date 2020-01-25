package pt.ulusofona.lp2.fandeisiaGame;

import java.util.List;

public class InsufficientCoinsException extends Exception {
    String val;

    public InsufficientCoinsException(String val) {
        this.val = val;
    }

    public boolean teamRequiresMoreCoins(int teamId) {
        int val = getRequiredCoinsForTeam(teamId);
        return val > FandeisiaGameManager.PLAFOND;
    }

    public int getRequiredCoinsForTeam(int teamID) {
        List<Creature> lista;
        Equipa team = Equipa.getEquipaById(teamID);
        if (team.equals(FandeisiaGameManager.ldr)) {
            lista = FandeisiaGameManager.criaturasLDR;
        }
        else {
            lista = FandeisiaGameManager.criaturasResistance;
        }
        return lista.stream()
                    .mapToInt(Creature::getCusto)
                    .sum();
    }

}
