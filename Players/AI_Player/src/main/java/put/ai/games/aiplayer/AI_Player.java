/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package put.ai.games.aiplayer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.*;

import put.ai.games.game.Board;
import put.ai.games.game.Move;
import put.ai.games.game.Player;


public class AI_Player extends Player {

    private Random random = new Random(0xdeadbeef);


    @Override
    public String getName() {
        return "Patryk Lukaszewski 148058";
    }

    @Override
    public Move nextMove(Board b) {
        Color player = getColor();
        Color opponent = getOpponent(player);
        List<Move> moves = b.getMovesFor(player);
        long time = getTime();
        long start = System.currentTimeMillis();
        Move move_best = moves.get(random.nextInt(moves.size()));
        int depth;
        for(depth=1; depth<100; depth++) {
            int alpha = -999999;
            Move move_best_this_depth = moves.get(random.nextInt(moves.size()));
            for (Move move : moves) {
                Board b_copy = b.clone();
                b_copy.doMove(move);
                int val = -AlphaBeta(b_copy, opponent, depth, -999999, -alpha, start + time);
                if (val > alpha) {
                    alpha = val;
                    move_best_this_depth = move;
                }
            }
            try {
                Files.write(Paths.get("file.txt"), (""+depth+" "+move_best_this_depth+"\n").getBytes(), StandardOpenOption.APPEND);
            }catch (IOException e) {}
            if((start + time) - System.currentTimeMillis() < 25) break;
            else move_best = move_best_this_depth;
            if(alpha == 10000) break;
        }
        try {
            Files.write(Paths.get("file.txt"), (""+depth+"\n").getBytes(), StandardOpenOption.APPEND);
        }catch (IOException e) {}
        return move_best;
    }

    private int AlphaBeta(Board b, Color player, int depth, int alpha, int beta, long endTime) {
        if(endTime - System.currentTimeMillis() < 25) return 999999;
        Color opponent = getOpponent(player);
        List<Move> moves = b.getMovesFor(player);
        if(moves.size() == 0) return -10000;
//        if(depth == 0) return moves.size() - b.getMovesFor(opponent).size();
        if(depth == 0) return getNumberOfMoves(b, player) - getNumberOfMoves(b, opponent);

        for(Move move : moves) {
            Board b_copy = b.clone();
            b_copy.doMove(move);
            int val = -AlphaBeta(b_copy, opponent, depth - 1, -beta, -alpha, endTime);
            if (val > alpha) alpha = val;
            if(endTime - System.currentTimeMillis() < 25) return 999999;
            if (alpha >= beta) return beta;
        }
        return alpha;
    }

    int getNumberOfMoves(Board b, Color c) {
        int numberOfMoves = 0;
        Board b_clone = b.clone();
        while(true) {
            List<Move> moves = b_clone.getMovesFor(c);
            if(moves.size() == 0) return numberOfMoves;
            numberOfMoves += moves.size();
            for(Move move : moves) b_clone.doMove(move);
        }
    }
}

