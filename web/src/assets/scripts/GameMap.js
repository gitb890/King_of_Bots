import {GameObjects} from "@/assets/scripts/GameObjects";
import {Wall} from "./Wall"

export class GameMap extends GameObjects{
    constructor(ctx,parent) {
        super();
        this.ctx = ctx;
        this.parent = parent;
        this.L = 0;//定义一个的距离

        //定义一个长度13*13的地图
        this.rows = 13;
        this.cols = 13;

        this.inner_walls_counts = 80;
        this.walls = [];
    }

    check_connectivty(g,sx,sy,tx,ty){
        if (sx == tx && sy == ty) return true;
        g[sx][sy] = true;

        let dx = [-1,0,1,0],dy = [0,1,0,-1];
        for (let i = 0;i < 4;i++){
            let x = sx+dx[i],y = sy+dy[i];
            //判断是否撞墙
            if (!g[x][y] && this.check_connectivty(g,x,y,tx,ty))
                return true;
        }
        return false;

    }
    create_walls(){
        const g = [];
        for (let r = 0;r < this.rows;r++){
            g[r] = [];
            for (let c = 0; c < this.cols;c++){
                g[r][c] = false;
            }
        }

    //    给四周加上障碍物墙壁
        for (let r = 0; r < this.rows;r++){
            g[r][0] = g[r][this.cols - 1] =true;
        //    左右加上墙
        }
        for (let c = 0; c < this.cols;c++){
            g[0][c] = g[this.rows - 1][c] = true;
        //    上下加上墙
        }

        //创建随机障碍物
        for (let i = 0; i <this.inner_walls_counts/2; i++) {
            for (let j = 0; j < 1000; j++) {
                let r = parseInt(Math.random() * this.rows);
                let c = parseInt(Math.random() * this.cols);
                if (g[r][c] || g[c][r]) continue;
                //判断地图不能覆盖左下角和右上角
                if (r == this.rows - 2 && c == 1 || r == 1 && c == this.cols - 2) continue;

                g[r][c] = g[c][r] = true;
                break;
            }
        }

        const copy_g = JSON.parse(JSON.stringify(g));
        if (!this.check_connectivty(copy_g,this.rows-2,1,1,this.cols-2)) return false;

        for (let r = 0;r <this.rows;r++){
            for (let c = 0; c< this.cols;c++){
                if (g[r][c]){
                    this.walls.push(new Wall(r,c,this));
                }
            }
        }

        return true;
    }


    start(){

        for (let i = 0; i < 1000; i++) {
            if (this.create_walls())
                break;
        }
    }
    update_size(){
        this.L =parseInt(Math.min(this.parent.clientWidth / this.cols,this.parent.clientHeight / this.rows));
        this.ctx.canvas.width = this.L * this.cols;
        this.ctx.canvas.height = this.L * this.rows;

    }

    update() {
        this.update_size();
        this.render();

    }

    render(){
        // this.ctx.fillStyle = 'green';
        // this.ctx.fillRect(0,0,this.ctx.canvas.width,this.ctx.canvas.height);

        const color_even = "#AAD751",color_odd = "#A2D149";
        for (let r = 0; r < this.cols; r++) {
            for (let c = 0; c < this.rows; c++) {
                if ((r+c)%2 ==0){
                    this.ctx.fillStyle = color_even;
                }else {
                    this.ctx.fillStyle = color_odd;
                }
                this.ctx.fillRect(r*this.L,c*this.L,this.L,this.L)
            }

        }

    }
}