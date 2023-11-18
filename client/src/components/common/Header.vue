<script setup>
import http from "@/util/http-common.js";
import { ref, onMounted } from "vue";
import { RouterLink, useRouter } from "vue-router";
import { storeToRefs } from "pinia";
import { useUserStore } from "@/stores/userStore.js";
import { useAttractionStore } from "@/stores/attractionStore";
// cookies
import { useCookies } from "vue3-cookies";
const { cookies } = useCookies();

const userStore = useUserStore();

const { isLogin } = storeToRefs(userStore);
const { userLogoutStore } = userStore;

const router = useRouter();

const attractionStore = useAttractionStore();
const { setSidoCode } = attractionStore;

onMounted(async () => {
  const ui = cookies.get("userId");
  if (ui) {
    isLogin.value = true;
  }
});

const logout = async () => {
  await userLogoutStore(cookies.get("userId"));
  router.replace({ name: "HomeView" });
};

const moveHome = () => {
  router.replace({ name: "HomeView" });
};

const moveMyPage = () => {
  router.replace({ name: "UserMyPage" });
};

const moveAttractionSearch = async () => {
  await setSidoCode(0);
  await router.replace({ name: "AttractionSearch" });
  router.go();
};

const moveAttractionPlan = async () => {
  await setSidoCode(0);
  await router.replace({ name: "AttractionPlan" });
  router.go();
};

// console.log("status : ", getLoginStatus());
// isLogin.value = getLoginStatus() ? true : false;
console.log("header var : isLogin : ", isLogin.value);
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
            <RouterLink aria-current="page" to="" @click="moveAttractionSearch"
              >여행지 검색</RouterLink
            >
          </div>
          <div class="menu">
            <RouterLink aria-current="page" to="" @click="moveAttractionPlan"
              >여행 플래너</RouterLink
            >
          </div>
          <div class="menu">
            <RouterLink aria-current="page" to="#">핫플 자랑</RouterLink>
          </div>
          <div class="menu">
            <RouterLink aria-current="page" to="/board">Q&A게시판</RouterLink>
          </div>
          <div class="menu"></div>
          <div v-if="isLogin == false" id="user_menu" class="menu">
            <div class="user">
              <RouterLink id="navJoin" to="/user/join">회원가입</RouterLink>
            </div>
            <div class="user">
              <RouterLink id="navLogin" to="/user/login">로그인</RouterLink>
            </div>
          </div>
          <div v-else id="user_menu" class="menu">
            <div class="user">
              <RouterLink id="navMyPage" to="" @click="moveMyPage"
                >마이페이지</RouterLink
              >
            </div>
            <div class="user">
              <RouterLink id="navLogout" to="" @click="logout"
                >로그아웃</RouterLink
              >
            </div>
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
  border: 1px solid gainsboro;
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
  flex-wrap: wrap;
  display: flex;
}
.user {
  flex: 1;
  font-family: "NanumSquare";
}
</style>
