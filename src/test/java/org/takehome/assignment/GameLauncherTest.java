package org.takehome.assignment;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class GameLauncherTest {


    @Test
    void shouldLaunchGame() throws Exception {
        String[] args = new String[] {
                "--config",
                "config.json",
                "--betting-amount",
                "100"
        };

        GameLauncher.main(args);
    }

    @Test
    void shouldNotLaunchGameIfArgsIsEmpty() {

        String[] args = new String[] {};

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            GameLauncher.main(args);
        });

        assertThat(exception).isNotNull();
        assertThat(exception.getMessage()).isEqualTo("Invalid Program Arguments");
    }

    @Test
    void shouldNotLaunchGameIfArgsLengthIsLessThanFour() {

        String[] args = new String[] {
                "--config",
                "config.json",
                "--betting-amount"
        };

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            GameLauncher.main(args);
        });

        assertThat(exception).isNotNull();
        assertThat(exception.getMessage()).isEqualTo("Invalid Program Arguments");
    }

    @Test
    void shouldNotLaunchGameIfFirstArgIsNotConfig() {

        String[] args = new String[] {
                "--invalid-config",
                "config.json",
                "--betting-amount",
                "100"
        };

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            GameLauncher.main(args);
        });

        assertThat(exception).isNotNull();
        assertThat(exception.getMessage()).isEqualTo("Invalid Program Arguments");
    }

    @Test
    void shouldNotLaunchGameIfSecondArgIsNotJsonFile() {

        String[] args = new String[] {
                "--config",
                "config.txt",
                "--betting-amount",
                "100"
        };

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            GameLauncher.main(args);
        });

        assertThat(exception).isNotNull();
        assertThat(exception.getMessage()).isEqualTo("Invalid Program Arguments");
    }

    @Test
    void shouldNotLaunchGameIfThirdArgIsNotBettingAmount() {

        String[] args = new String[] {
                "--config",
                "config.txt",
                "--invalid-betting-amount",
                "100"
        };

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            GameLauncher.main(args);
        });

        assertThat(exception).isNotNull();
        assertThat(exception.getMessage()).isEqualTo("Invalid Program Arguments");
    }

    @Test
    void shouldNotLaunchGameIfFourthArgIsZero() {

        String[] args = new String[] {
                "--config",
                "config.txt",
                "--invalid-betting-amount",
                "0"
        };

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            GameLauncher.main(args);
        });

        assertThat(exception).isNotNull();
        assertThat(exception.getMessage()).isEqualTo("Invalid Program Arguments");
    }
}
