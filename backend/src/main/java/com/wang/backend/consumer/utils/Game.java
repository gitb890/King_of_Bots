package com.wang.backend.consumer.utils;

import com.alibaba.fastjson.JSONObject;
import com.wang.backend.consumer.WebSocketServer;
import com.wang.backend.pojo.Record;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.concurrent.locks.ReentrantLock;

public class Game extends Thread{
    private final Integer rows;
    private final Integer cols;
    private final Integer inner_walls_count;
    private final int[][] games;

    private final static int[] dx = {-1,0,1,0},dy = {0,1,0,-1};

    private final Player playerA,playerB;

    private Integer nextStepA = null,nextStepB = null;

//    加锁防止读写冲突，因为在操作过程中，两个线程出现读写操作
    private ReentrantLock lock = new ReentrantLock();

    private String status = "playing";//palying->finished

    private String loser = "";//all:平局， A:A输，B:B输


    
    public Game(Integer rows,Integer cols,Integer inner_walls_count,Integer idA,Integer idB){
        this.rows = rows;
        this.cols = cols;
        this.inner_walls_count = inner_walls_count;
        this.games = new int[rows][cols];
        playerA = new Player(idA,rows - 2,1,new ArrayList<>());
        playerB = new Player(idB,1,cols - 2,new ArrayList<>());
    }

    public Player getPlayerA(){
        return playerA;
    }
    public Player getPlayerB(){
        return playerB;
    }

    public void setNextStepA(Integer nextStepA){
        lock.lock();
        try {
            this.nextStepA = nextStepA;
        }finally {
            lock.unlock();
            //解锁防止出现死锁
        }
    }
    public void setNextStepB(Integer nextStepB){
        lock.lock();
        try {
            this.nextStepB = nextStepB;
        }finally {
            lock.unlock();
        }
    }

    public int[][] getGames(){
        return games;
    }

    private boolean check_connectivty(int sx,int sy,int tx,int ty){
        if (sx==tx && sy ==ty){
            return true;
        }
        games[sx][sy] = 1;

        for (int i = 0; i < 4; i++) {
            int x = sx+dx[i],y = sy+dy[i];
            if (x>= 0 && x<this.rows&&y>=0&&y<this.cols&&games[x][y]==0){
                if (check_connectivty(x,y,tx,ty)){
                    games[sx][sy] =0;
                    return true;
                }
            }
        }

        games[sx][sy] =0;
        return false;
    }
    
    private boolean dram(){//画地图
        for (int i = 0; i < this.rows; i++) {
            for (int j = 0; j < this.cols; j++) {
                games[i][j] = 0;
            }
        }

        for (int r = 0; r < this.rows; r++) {
            games[r][0] = games[r][cols-1] = 1;
        }

        for (int c = 0; c < this.cols; c++) {
            games[0][c] = games[rows-1][c] = 1;
        }

        Random random = new Random();
        for (int i = 0; i < inner_walls_count/2; i++) {
            for (int j = 0; j < 1000; j++) {
                int r = random.nextInt(this.rows);
                int c = random.nextInt(this.cols);

                if (games[r][c] == 1 || games[this.rows -1-r][this.cols-1-c] ==1){
                    continue;
                }
                if (r == this.rows - 2 && c == 1 || r==1 && r == this.cols-2){
                    continue;
                }

                games[r][c] = games[this.rows-1-r][this.cols-1-c] =1;
                break;
            }
        }
        return check_connectivty(this.rows-2,1,1,this.cols-2);
    }
    
    public void creatMap(){//创建地图
        for (int i = 0; i < 1000; i++) {
            if (dram())
                break;
        }
    }

    private boolean nextStep(){//等待两个玩家的下一步操作
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }


        for (int i = 0; i < 50; i++) {
            try {
                Thread.sleep(100);
                lock.lock();
//循环里加锁和解锁操作，在循环中每一次结束后都会进行解锁操作，防止循环中没有释放锁造成死锁
                try {
                    if (nextStepA !=null && nextStepB !=null){
                        playerA.getSteps().add(nextStepA);
                        playerB.getSteps().add(nextStepB);
                        return true;
                    }
                }finally {
                    lock.unlock();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    private void sendAllMessage(String message){
//        向每个玩家发送信息
        WebSocketServer.users.get(playerA.getId()).sendMessage(message);
        WebSocketServer.users.get(playerB.getId()).sendMessage(message);
    }

    private String getMapString(){
        StringBuilder res = new StringBuilder();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                res.append(games[i][j]);
            }
        }
        return res.toString();
    }
    private void saveToDatabase(){
        Record record = new Record(
                null,
                playerA.getId(),
                playerA.getSx(),
                playerA.getSy(),
                playerB.getId(),
                playerB.getSx(),
                playerB.getSy(),
                playerA.getStepsString(),
                playerB.getStepsString(),
                getMapString(),
                loser,
                new Date()
        );
        WebSocketServer.recordMapper.insert(record);
    }

    private void sendResult(){
//        向两个Client公布结果
        JSONObject resp = new JSONObject();
        resp.put("event","result");
        resp.put("loser",loser);
        saveToDatabase();
        sendAllMessage(resp.toJSONString());

    }

    private void judge(){
//        判断两个玩家下一步操作是否合法

        List<Cell> cellsA = playerA.getCells();
        List<Cell> cellsB = playerB.getCells();

        boolean vaildA = check_vaild(cellsA,cellsB);
        boolean vaildB = check_vaild(cellsB,cellsA);
        if (!vaildA || !vaildB){
            status = "finished";
            if (!vaildA && !vaildB){
                loser = "all";
            } else if (!vaildA) {
                loser = "A";
            }else {
                loser = "B";
            }
        }

    }

    private boolean check_vaild(List<Cell>cellsA,List<Cell>cellsB){
        int n = cellsA.size();
        Cell cell = cellsA.get(n-1);
        if(games[cell.getX()][cell.getY()] ==1){
            return false;
        }

        for (int i = 0; i < n-1; i++) {
            if (cellsA.get(i).getX()==cell.getX() && cellsA.get(i).getY() == cell.getY()){
                return false;
            }
        }
        for (int i = 0; i < n-1; i++) {
            if (cellsB.get(i).getX() == cell.getX() && cellsB.get(i).getY() == cell.getY()){
                return false;
            }
        }
        return true;
    }

    private void sendMove(){
//        向两个client传递移动信息,因为要读client的操作，需要加锁
        lock.lock();
        try {
            JSONObject resp = new JSONObject();
            resp.put("event", "move");
            resp.put("a_direction", nextStepA);
            resp.put("b_direction", nextStepB);
            sendAllMessage(resp.toJSONString());
//            清空操作
            nextStepA= nextStepB = null;
        }finally {
            lock.unlock();
        }
    }

    @Override
    public void run() {
        for (int i = 0; i < 1000; i++) {
            if (nextStep()){
//                是否获取两条蛇的下一步操作
                judge();
                if (status.equals("playing")){
                    sendMove();
                }else {
                    sendResult();
                    break;
                }
            }else {
                status = "finished";
                lock.lock();
                try {
//                    next的读操作
                    if (nextStepA == null && nextStepB ==null){
                        loser = "all";
                    } else if (nextStepA ==null) {
                        loser = "A";
                    }else {
                        loser = "B";
                    }
                }finally {
                    lock.unlock();
                }
                sendResult();
                break;
            }
        }
    }
}
