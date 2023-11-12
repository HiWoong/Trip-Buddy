import { createRouter, createWebHistory } from "vue-router";
import HomeView from "@/views/HomeView.vue";
import UserView from "@/views/UserView.vue";
import BoardView from "@/views/BoardView.vue";
import Attraction from "@/components/attraction/Attraction.vue";

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
      name: "Attraction",
      component: Attraction,
    },
    {
      path: "/user",
      name: "UserView",
      component: UserView,
      children: [
        {
          path: "login",
          name: "Login",
          component: () => import("@/components/user/Login.vue"),
        },
        {
          path: "myPage",
          name: "MyPage",
          component: () => import("@/components/user/MyPage.vue"),
        },
        {
          path: "join",
          name: "Join",
          component: () => import("@/components/user/Join.vue"),
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
