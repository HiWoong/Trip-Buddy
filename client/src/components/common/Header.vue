<script setup>
import http from "@/util/http-common.js";
import { ref, onMounted } from "vue";
import { RouterLink, useRouter } from "vue-router";
import { storeToRefs } from "pinia";
import { useUserStore } from "@/stores/userStore.js";

const userStore = useUserStore();

const { isLogin, userInfo } = storeToRefs(userStore);
const { getLoginStatus } = userStore;

const router = useRouter();

const logout = () => {
  http.get("/userapi/logout").then(() => {
    
    sessionStorage.removeItem("userinfo");
    sessionStorage.setItem("isLogin", "true");
    router.replace({ name: "HomeView" });
    router.go();
  });
};

const moveHome = () => {
  router.replace({ name: "HomeView" });
};

const moveMyPage = () => {
  router.replace({ name: "UserMyPage" });
};

console.log("status : ", getLoginStatus());
isLogin.value = getLoginStatus() ? true : false;
console.log("header var : isLogin : ", isLogin.value);
</script>

<template>
  <div class="d-flex flex-column">
    <div class="flex-shrink-0">
      <!-- header -->
      <nav class="navbar navbar-expand-lg navbar-light bg-light fixed-top border-bottom">
        <div class="container px-4 px-lg-5">
          <RouterLink class="navbar-brand" to="" @click="moveHome">Enjoy Trip</RouterLink>
          <button
            class="navbar-toggler"
            type="button"
            data-bs-toggle="collapse"
            data-bs-target="#navbarSupportedContent"
            aria-controls="navbarSupportedContent"
            aria-expanded="false"
            aria-label="Toggle navigation"
          >
            <span class="navbar-toggler-icon"></span>
          </button>
          <div class="collapse navbar-collapse" id="navbarSupportedContent">
            <ul class="navbar-nav mb-2 mb-lg-0 ms-lg-4">
              <li class="nav-item">
                <RouterLink class="nav-link active" aria-current="page" to="/attraction"
                  >지역별여행지</RouterLink
                >
              </li>
              <li class="nav-item">
                <RouterLink class="nav-link active" aria-current="page" to="#"
                  >나의여행계획</RouterLink
                >
              </li>
              <li class="nav-item">
                <RouterLink class="nav-link active" aria-current="page" to="#"
                  >핫플자랑하기</RouterLink
                >
              </li>
              <li class="nav-item">
                <RouterLink class="nav-link active" aria-current="page" to="/board"
                  >여행게시판</RouterLink
                >
              </li>
              <!-- <li class="nav-item">
                <RouterLink class="nav-link active" aria-current="page" to="/sites"
                  >사이트맵</RouterLink
                >
              </li> -->
            </ul>
            <div v-if="isLogin" class="collapse navbar-collapse">
              <ul class="navbar-nav mb-2 mb-lg-0 ms-auto">
                <li class="nav-item">
                  <button class="nav-link" @click="moveMyPage" id="navMypage">마이페이지</button>
                </li>
                <li class="nav-item">
                  <!-- to="/member?action=logout" -->
                  <button class="nav-link" @click="logout" id="navLogout">로그아웃</button>
                </li>
              </ul>
            </div>
            <div v-else class="collapse navbar-collapse">
              <ul class="navbar-nav mb-2 mb-lg-0 ms-auto">
                <li class="nav-item">
                  <RouterLink class="nav-link" id="navJoin" to="/user/join">회원가입</RouterLink>
                </li>
                <li class="nav-item">
                  <RouterLink class="nav-link" id="navLogin" to="/user/login">로그인</RouterLink>
                </li>
              </ul>
            </div>
          </div>
        </div>
      </nav>
    </div>
  </div>
</template>

<style scoped></style>
