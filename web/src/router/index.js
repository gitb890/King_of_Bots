import {createRouter,createWebHistory} from 'vue-router';
// import Vue from 'vue'
// import VueRouter from 'vue-router'
import PkIndexView from '../views/pk/PkIndexView';
import RankListIndexView from '../views/ranklist/RankListIndexView';
import RecordIndexView from '../views/record/RecordIndexView';
import UserBotIndexView from '../views/user/bot/UserBotIndexView';
import UserIndexView from '../views/user/UserIndexView';
import NotFound from '../views/error/NotFound';


// Vue.use(VueRouter)

const routes = [
    {
        path: "/",
        name: "home",
        redirect: "/pk/"
    },
    {
        path:"/pk/",
        name:"pk_index",
        component: PkIndexView
    },
    {
      path: "/ranklist/",
      name: "rank_list_index",
      component: RankListIndexView
    },
    {
        path:"/record/",
        name:"record_index",
        component: RecordIndexView
    },
    {
        path:"/user/bot",
        name:"user_bot_index",
        component: UserBotIndexView
    },
    {
        path: "/user/",
        name: "user_index",
        component: UserIndexView
    },
    {
        path: "/404/",
        name: "404",
        component: NotFound
    },
    {
        path: "/:catchAll(.*)",
        redirect: "/404/"
    }
]

const router =createRouter({
    history: createWebHistory(),
    routes
})


export default router
