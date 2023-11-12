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
    router.push("/");
    router.go();
  });
};

const MoveMyPage = () => {
  router.push({ name: "MyPage" });
};
</script>

<template>
  <div class="d-flex flex-column">
    <div class="flex-shrink-0">
      <!-- header -->
      <nav class="navbar navbar-expand-lg navbar-light bg-light fixed-top border-bottom">
        <div class="container px-4 px-lg-5">
          <RouterLink class="navbar-brand" to="/">Enjoy Trip</RouterLink>
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
            <ul class="navbar-nav me-auto mb-2 mb-lg-0 ms-lg-4">
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
              <li class="nav-item">
                <RouterLink class="nav-link active" aria-current="page" to="/sites"
                  >사이트맵</RouterLink
                >
              </li>
            </ul>
            <ul class="navbar-nav">
              <div v-if="userinfo == null" class="row">
                <li class="nav-item col">
                  <RouterLink class="nav-link" id="navJoin" to="/user/join">회원가입</RouterLink>
                </li>
                <li class="nav-item col">
                  <RouterLink class="nav-link" id="navLogin" to="/user/login">로그인</RouterLink>
                </li>
              </div>
              <div v-else class="row">
                <li class="nav-item col">
                  <button class="nav-link" @click="MoveMyPage" id="navMypage">마이페이지</button>
                </li>
                <li class="nav-item col">
                  <!-- to="/member?action=logout" -->
                  <button class="nav-link" @click="logout" id="navLogout">로그아웃</button>
                </li>
              </div>
            </ul>
          </div>
        </div>
      </nav>
    </div>
  </div>
</template>

<style scoped></style>
