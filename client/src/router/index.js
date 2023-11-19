import { createRouter, createWebHistory } from "vue-router";
import HomeView from "@/views/HomeView.vue";
import UserView from "@/views/UserView.vue";
import BoardView from "@/views/BoardView.vue";
import AttractionView from "@/views/AttractionView.vue";

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: "/",
      name: "HomeView",
      component: HomeView,
    },
    {
      path: "/attraction",
      name: "AttractionView",
      component: AttractionView,
      children: [
        {
          path: "",
          name: "searchView",
          component: () => import("@/components/attraction/AttractionSearch.vue"),
        },
        {
          path: "plan",
          name: "AttractionPlan",
          component: () => import("@/components/attraction/AttractionPlan.vue"),
        },
      ],
    },
    {
      path: "/user",
      name: "UserView",
      component: UserView,
      children: [
        {
          path: "login",
          name: "UserLogin",
          component: () => import("@/components/user/UserLogin.vue"),
        },
        {
          path: "myPage",
          name: "UserMyPage",
          component: () => import("@/components/user/UserMyPage.vue"),
        },
        {
          path: "join",
          name: "UserJoin",
          component: () => import("@/components/user/UserJoin.vue"),
        },
        {
          path: "plan",
          name: "UserPlan",
          component: () => import("@/components/user/UserPlan.vue"),
        },
      ],
    },
    {
      path: "/board",
      mane: "BoardView",
      component: BoardView,
      children: [
        {
          path: "",
          name: "BoardList",
          component: () => import("@/components/board/BoardList.vue"),
        },
        {
          path: "write",
          name: "BoardWrite",
          component: () => import("@/components/board/BoardWrite.vue"),
        },
        {
          path: "detail/:articleNo",
          name: "BoardDetail",
          component: () => import("@/components/board/BoardDetail.vue"),
        },
        {
          path: "modify/:articleNo",
          name: "BoardModify",
          component: () => import("@/components/board/BoardModify.vue"),
        },
      ],
    },
  ],
});

export default router;
