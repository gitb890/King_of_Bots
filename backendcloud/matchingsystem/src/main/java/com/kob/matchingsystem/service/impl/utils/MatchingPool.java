package com.kob.matchingsystem.service.impl.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

@Component
public class MatchingPool extends Thread{
    private static List<Player> players = new ArrayList<>();
    private ReentrantLock lock = new ReentrantLock();

    private static RestTemplate restTemplate;
    private final static String startGameUrl="http://127.0.0.1:3000/pk/start/game/";

    @Autowired
    public void setRestTemplate(RestTemplate restTemplate){
        MatchingPool.restTemplate = restTemplate;
    }
    public void addPlayer(Integer userId,Integer rating){
        System.out.println("add player:"+userId+" "+rating);
        lock.lock();
        try {
            players.add(new Player(userId,rating,0));
        }finally {
            lock.unlock();
        }
    }

    public void removePlayer(Integer userId){
        lock.lock();
        try {
            List<Player> newPlayers = new ArrayList<>();
            for (Player player:
                    players) {
                if (!player.getUserId().equals(userId)){
                    newPlayers.add(player);
                }

            }
            players = newPlayers;
        }finally {
            lock.unlock();
        }
    }

    private void increaseWaitTime(){ //将所有匹配玩家的等待时间加1
        for (Player player:
             players) {
            player.setWaitingTime(player.getWaitingTime()+1);
        }
    }

    private void matchPlayers() {//匹配所有玩家
        System.out.println("match player:"+players);
        boolean[] used = new boolean[players.size()];
        for (int i = 0; i < players.size(); i++) {
            if (used[i]) continue;
            for (int j = i+1; j < players.size(); j++) {
                if (used[j]) continue;
                Player a = players.get(i);Player b = players.get(j);
                if (checkMatched(a,b)){
                    used[i] = used[j] = true;
                    sendResult(a,b);
                    break;
                }
            }
        }

        List<Player> newPlayers = new ArrayList<>();
        for (int i = 0; i < players.size(); i++) {
            if (!used[i]){
                newPlayers.add(players.get(i));
            }
        }
        players = newPlayers;
    }

    private boolean checkMatched(Player a,Player b){//判断玩家是否匹配
        int ratingDelta = Math.abs(a.getRating()-b.getRating());
        int watingTIme = Math.min(a.getWaitingTime(),b.getWaitingTime());
        return ratingDelta<=watingTIme*10;
    }

    private void sendResult(Player a,Player b){//返回匹配结果
        System.out.println("send result:"+a+" "+b);
        MultiValueMap<String,String> data = new LinkedMultiValueMap<>();
        data.add("a_id",a.getUserId().toString());
        data.add("b_id",b.getUserId().toString());
        restTemplate.postForObject(startGameUrl,data,String.class);
    }

    @Override
    public void run() {
        while (true){
            try{
                Thread.sleep(1000);
                lock.lock();
                try{
                    increaseWaitTime();
                    matchPlayers();
                }finally {
                    lock.unlock();
                }
            }catch (InterruptedException e){
                e.printStackTrace();
                break;
            }
        }
    }


}
