package com.wang.backend.consumer.utils;

import java.util.Random;

public class Game {
    final private Integer rows;
    final private Integer cols;
    final private Integer inner_walls_count;
    final int[][] games;

    final private static int[] dx = {-1,0,1,0},dy = {0,1,0,-1};
    
    public Game(Integer rows,Integer cols,Integer inner_walls_count){
        this.rows = rows;
        this.cols = cols;
        this.inner_walls_count = inner_walls_count;
        this.games = new int[rows][cols];
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
}
