const GAME_OBJECTS = [];

export class GameObjects{
    constructor() {
        GAME_OBJECTS.push(this);
        this.timedetail = 0;
        //给一个时间间隔，来判断物体的移动
        this.has_calld_start = false;
    }

    start(){//只在开始执行一次

    }

    update(){//每一帧执行一次，除了第一帧外

    }

    ondistroy(){//销毁之前执行


    }

    distroy(){//销毁

        this.ondistroy();
        for (let i in GAME_OBJECTS){
            const obj = GAME_OBJECTS[i];
            if (obj === this){
                GAME_OBJECTS.splice(i);
                break;
            }
        }
    }

}


let last_timestamp; //上一次执行的时间
const step= timestamp =>{
    for (let obj of GAME_OBJECTS){
        //使用of遍历值，使用in遍历下标
        if (!obj.has_calld_start){
            obj.has_calld_start = true;
            obj.start();
        }else {
            obj.timedetail = timestamp - last_timestamp;
            obj.update();
        }
    }

    last_timestamp = timestamp;
    requestAnimationFrame(step)
}

requestAnimationFrame(step)