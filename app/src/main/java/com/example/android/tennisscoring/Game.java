package com.example.android.tennisscoring;


final class Game {
    public final int
            server,
            player1SetsWon, player1GamesWon, player1GameScore, player1MissedServes,
            player2SetsWon, player2GamesWon, player2GameScore, player2MissedServes;


    public static Game newGame() {
        return new Game(1,0,0,0,0,0,0,0,0);
    }


    public String player1SetsWon() {
        return String.valueOf(player1SetsWon);
    }

    public String player1GamesWon() {
        return String.valueOf(player1GamesWon);
    }

    public String player1GameScore() {
        return scoreOf(player1GameScore);
    }

    public String player2SetsWon() {
        return String.valueOf(player2SetsWon);
    }

    public String player2GamesWon() {
        return String.valueOf(player2GamesWon);
    }

    public String player2GameScore() {
        return scoreOf(player2GameScore);
    }


    public boolean gameOver() {
        return wonMatch(player1SetsWon) || wonMatch(player2SetsWon);
    }

    public boolean deuce() {
        return player1GameScore == 3 && player2GameScore == 3;
    }

    public boolean leftHasAdvantage() {
        return hasAdvantage(player1GameScore, player2GameScore);
    }

    public boolean rightHasAdvantage() {
        return hasAdvantage(player2GameScore, player1GameScore);
    }


    public Game player1MissServe() {
        if (player1MissedServes == 1)
            return player2Point();
        return new Builder().player1MissedServes(player1MissedServes + 1).create();
    }

    public Game player2MissServe() {
        if (player2MissedServes == 1)
            return player1Point();
        return new Builder().player2MissedServes(player2MissedServes + 1).create();
    }


    public Game player1Point() {
        Builder builder = new Builder()
                .player1MissedServes(0)
                .player2MissedServes(0);
        if (wonGame(player1GameScore, player2GameScore)) {
            builder.server(otherPlayer(server))
                    .player1GameScore(0)
                    .player2GameScore(0)
                    .player1GamesWon(player1GamesWon + 1);
            if (wonSet(builder._player1GamesWon, player2GamesWon)) {
                builder.player1GamesWon(0)
                        .player2GamesWon(0)
                        .player1SetsWon(player1SetsWon + 1);
            }
        }
        else if (hasAdvantage(player2GameScore, player1GameScore))
            builder.player2GameScore(player2GameScore-1);
        else
            builder.player1GameScore(player1GameScore + 1);
        return builder.create();
    }

    public Game player2Point() {
        Builder builder = new Builder()
                .player1MissedServes(0)
                .player2MissedServes(0);
        if (wonGame(player2GameScore, player1GameScore)) {
            builder.server(otherPlayer(server))
                    .player1GameScore(0)
                    .player2GameScore(0)
                    .player2GamesWon(player2GamesWon + 1);
            if (wonSet(builder._player2GamesWon, player1GamesWon)) {
                builder.player1GamesWon(0)
                        .player2GamesWon(0)
                        .player2SetsWon(player2SetsWon + 1);
            }
        }
        else if (hasAdvantage(player1GameScore, player2GameScore))
            builder.player1GameScore(player1GameScore-1);
        else
            builder.player2GameScore(player2GameScore + 1);
        return builder.create();
    }


    private static int otherPlayer(int player) {
        return player == 1 ? 2 : 1;
    }

    private static boolean wonGame(int playerScore, int opponentScore) {
        return (playerScore == 4 && opponentScore == 3)
                || (playerScore == 3 && opponentScore <= 2);
    }

    private static boolean wonSet(int playerGamesWon, int opponentGamesWon) {
        return playerGamesWon == 7
                || (playerGamesWon == 6 && opponentGamesWon < 5);
    }

    private static boolean wonMatch(int setsWon) {
        return setsWon == 2;
    }

    private static boolean hasAdvantage(int playerScore, int opponentScore) {
        return playerScore == 4 && opponentScore == 3;
    }

    private static String scoreOf(int score) {
        if (score == 0) return "LOVE";
        if (score == 1) return "15";
        if (score == 2) return "30";
        if (score == 3) return "40";
        if (score == 4) return "ADV";
        return "ERROR";
    }


    private Game(
            int server,
            int player1SetsWon, int player1GamesWon, int player1GameScore, int player1MissedServes,
            int player2SetsWon, int player2GamesWon, int player2GameScore, int player2MissedServes
    ) {
        this.server = server;
        this.player1SetsWon = player1SetsWon;
        this.player1GamesWon = player1GamesWon;
        this.player1GameScore = player1GameScore;
        this.player1MissedServes = player1MissedServes;
        this.player2SetsWon = player2SetsWon;
        this.player2GamesWon = player2GamesWon;
        this.player2GameScore = player2GameScore;
        this.player2MissedServes = player2MissedServes;
    }


    private class Builder {
        private int
                _server,
                _player1SetsWon, _player1GamesWon, _player1GameScore, _player1MissedServes,
                _player2SetsWon, _player2GamesWon, _player2GameScore, _player2MissedServes;

        Builder() {
            this._server = server;
            this._player1SetsWon = player1SetsWon;
            this._player1GamesWon = player1GamesWon;
            this._player1GameScore = player1GameScore;
            this._player1MissedServes = player1MissedServes;
            this._player2SetsWon = player2SetsWon;
            this._player2GamesWon = player2GamesWon;
            this._player2GameScore = player2GameScore;
            this._player2MissedServes = player2MissedServes;
        }

        Game create() {
            return new Game(
                    _server,
                    _player1SetsWon, _player1GamesWon, _player1GameScore, _player1MissedServes,
                    _player2SetsWon, _player2GamesWon, _player2GameScore, _player2MissedServes
            );
        }

        Builder server(int value) {
            _server = value;
            return this;
        }

        Builder player1SetsWon(int value) {
            _player1SetsWon = value;
            return this;
        }

        Builder player1GamesWon(int value) {
            _player1GamesWon = value;
            return this;
        }

        Builder player1GameScore(int value) {
            _player1GameScore = value;
            return this;
        }

        Builder player1MissedServes(int value) {
            _player1MissedServes = value;
            return this;
        }

        Builder player2SetsWon(int value) {
            _player2SetsWon = value;
            return this;
        }

        Builder player2GamesWon(int value) {
            _player2GamesWon = value;
            return this;
        }

        Builder player2GameScore(int value) {
            _player2GameScore = value;
            return this;
        }

        Builder player2MissedServes(int value) {
            _player2MissedServes = value;
            return this;
        }
    }

}
