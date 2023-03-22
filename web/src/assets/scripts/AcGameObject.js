const Ac_GAME_OBJECTS = [];

export class AcGameObject {
    constructor () {
        Ac_GAME_OBJECTS.push(this);
        this.timedelta = 0;
        this.has_called_start = false;
    }

    // 执行一次
    start () {

    }

    //每一帧 执行一次， 除了第一帧之外
    update() {

    }

    //删除之前执行
    on_destroy () {

    }

    //删除对象
    destroy () {
        this.on_destroy();

        for (let i in Ac_GAME_OBJECTS) {
            const obj = Ac_GAME_OBJECTS[i];
            if (obj == this) {
                Ac_GAME_OBJECTS.splice(i);
                break;
            }
        }
    }
}


let last_timestamp; // 上一次执行的时刻

//每一帧执行的动作
const step = timedelta => {
    for (let obj of Ac_GAME_OBJECTS) {
        if (!obj.has_called_start) {
            obj.has_called_start = true;
            obj.start();
        } else {
            obj.timedelta = timedelta - last_timestamp;
            obj.update();
        }
    }

    last_timestamp = timedelta;
    requestAnimationFrame(step);
}
// 加载网页就渲染
requestAnimationFrame(step);
