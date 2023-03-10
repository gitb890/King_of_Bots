import {GameObjects} from "@/assets/scripts/GameObjects";

export class GameMap extends GameObjects{
    constructor(ctx,parent) {
        super();
        this.ctx = ctx;
        this.parent = parent;
        this.L = 0;//定义一个的距离

        //定义一个长度13*13的地图
        this.rows = 13;
        this.cols = 13;
    }
    start(){

    }
    update_size(){
        this.L = Math.min(this.parent.clientWidth / this.cols,this.parent.clientHeight / this.rows);
        this.ctx.canvas.width = this.L * this.cols;
        this.ctx.canvas.height = this.L * this.rows;

    }

    update() {
        this.update_size();
        this.render();

    }

    render(){
        this.ctx.fillStyle = 'green';
        this.ctx.fillRect(0,0,this.ctx.canvas.width,this.ctx.canvas.height);

        const color_even = "#AAD751",color_odd = "#A2D149";
        for (let i = 0; i < this.cols; i++) {
            for (let j = 0; j < this.rows; j++) {
                if ((i+j)%2 ==0){
                    this.ctx.fillStyle = color_even;
                }else {
                    this.ctx.fillStyle = color_odd;
                }
                this.ctx.fillRect(i*this.L,j*this.L,this.L,this.L)
            }

        }

    }
}