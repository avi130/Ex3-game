package gameClient;

import java.util.LinkedList;

import Server.game_service;

public interface IGameClient {
	public void locate(double xmin, double ymin, double ymax, double xmax, LinkedList<Integer> a, game_service game);
}
