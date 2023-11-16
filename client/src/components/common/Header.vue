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
          <div>
            <RouterLink to="" @click="moveHome">Enjoy Trip</RouterLink>
          </div>
          <div>
            <div>
              <RouterLink aria-current="page" to="/attraction">지역별여행지</RouterLink>
            </div>
            <div>
              <RouterLink aria-current="page" to="/attraction/plan">나의여행계획</RouterLink>
            </div>
            <div>
              <RouterLink aria-current="page" to="#">핫플자랑하기</RouterLink>
            </div>
            <div>
              <RouterLink aria-current="page" to="/board">여행게시판</RouterLink>
            </div>
            <div v-if="userinfo == null">
              <div>
                <RouterLink id="navJoin" to="/user/join">회원가입</RouterLink>
              </div>
              <div>
                <RouterLink id="navLogin" to="/user/login">로그인</RouterLink>
              </div>
            </div>
            <div v-else>
              <button @click="moveMyPage" id="navMypage">마이페이지</button>
              <button @click="logout" id="navLogout">로그아웃</button>
            </div>
          </div>
        </div>
      </nav>
    </div>
  </div>
</template>

<style scoped>
* {
  margin: 0;
  padding: 0;
}

.grid-container {
  display: grid;
  grid-template-columns: auto auto auto auto auto auto auto;
}
</style>
