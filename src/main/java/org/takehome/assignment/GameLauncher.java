package org.takehome.assignment;

import lombok.extern.slf4j.Slf4j;
import org.takehome.assignment.games.ScratchGame;
import org.takehome.assignment.models.ApplicationContext;
import org.takehome.assignment.models.GameResult;
import org.takehome.assignment.models.GameConfig;
import org.takehome.assignment.utils.GameUtils;

import java.io.IOException;

@Slf4j
public class GameLauncher {

    public static void main(String[] args) throws IOException {

        // Validate the arguments
        if (!hasValidArguments(args)) {
            log.error("Please ensure the program arguments are in the following format: --config config.json --betting-amount 100");
            throw new IllegalArgumentException("Invalid Program Arguments" );
        }

        // Read the program arguments
        String filename = args[1];
        Integer bettingAmount = Integer.valueOf(args[3]);

        // Get the game configuration from the *.json file
        GameConfig config = GameUtils.readJsonFromResources(filename, GameConfig.class);

        // Populate the application context
        ApplicationContext context =  ApplicationContext.getInstance();
        context.setConfig(config);

        // Play the game
        GameResult result = new ScratchGame().play(bettingAmount);

        String resultJson = context.getMapper().writerWithDefaultPrettyPrinter().writeValueAsString(result);

        // Display the result
        log.info (resultJson);
    }

    private static boolean hasValidArguments(String[] args) {

        if (GameUtils.isNullOrEmpty(args)) {
            return false;
        }

        if (args.length != 4) {
            return false;
        }

        if (!"--config".equalsIgnoreCase(args[0])) {
            return false;
        }

        if (GameUtils.isNullOrEmpty(args[1]) || !args[1].contains(".json")) {
            return false;
        }

        if (!"--betting-amount".equalsIgnoreCase(args[2])) {
            return false;
        }

        if (GameUtils.isNullOrEmpty(args[3])) {
            return false;
        }

        if (Integer.parseInt(args[3]) <= 0) {
            return false;
        }

        return true;
    }

}