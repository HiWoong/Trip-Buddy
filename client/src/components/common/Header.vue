<script setup>
import http from "@/util/http-common.js";
import { ref } from "vue";
import { RouterLink, useRouter } from "vue-router";
const router = useRouter();

const userinfo = ref(sessionStorage.getItem("userinfo"));
console.log(sessionStorage.getItem("userinfo"));

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
</script>

<template>
  <div>
    <div>
      <!-- header -->
      <nav>
        <div class="grid-container">
          <div id="logo">
            <RouterLink to="" @click="moveHome">Enjoy Trip</RouterLink>
          </div>
          <div class="menu">
            <RouterLink aria-current="page" to="/attraction">여행지 검색</RouterLink>
          </div>
          <div class="menu">
            <RouterLink aria-current="page" to="/attraction/plan">여행 플래너</RouterLink>
          </div>
          <div class="menu">
            <RouterLink aria-current="page" to="#">핫플 자랑</RouterLink>
          </div>
          <div class="menu">
            <RouterLink aria-current="page" to="/board">Q&A게시판</RouterLink>
          </div>
          <div class="menu"></div>
          <div id="user_menu" class="menu" v-if="userinfo == null">
            <div class="user">
              <RouterLink id="navJoin" to="/user/join">회원가입</RouterLink>
            </div>
            <div class="user">
              <RouterLink id="navLogin" to="/user/login">로그인</RouterLink>
            </div>
          </div>
          <div v-else>
            <button @click="moveMyPage" id="navMypage">마이페이지</button>
            <button @click="logout" id="navLogout">로그아웃</button>
          </div>
        </div>
      </nav>
    </div>
  </div>
</template>

<style scoped>
@font-face {
  font-family: "NanumSquare";
  src: url("../../assets/fonts/NanumSquareR.ttf") format("truetype");
}
@font-face {
  font-family: "NanumSquareB";
  src: url("../../assets/fonts/NanumSquareB.ttf") format("truetype");
}
* {
  margin: 0;
  padding: 0;
  font-family: "NanumSquare";
  height: 100px;
}
a {
  text-decoration-line: none;
  color: black;
}
.grid-container {
  display: grid;
  width: 100%;
  grid-template-columns: 200px 100px 100px 100px 100px 1150px 150px;
  position: fixed;
  background-color: white;
  border: 1px solid black;
  border-radius: 3px;
}
#logo {
  display: block;
  width: 100%;
  height: 100px;
  line-height: 100px;
  text-align: center;
  font-family: "NanumSquareB";
  font-size: 30px;
  font-weight: 1000;
}
.menu {
  display: block;
  width: 100%;
  height: 100px;
  line-height: 100px;
  text-align: center;
  font-family: "NanumSquare";
}
#user_menu {
  display: flex;
}
.user {
  flex: 1;
  font-family: "NanumSquare";
}
</style>
