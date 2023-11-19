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

const moveHotPlace = () => {
  router.replace({ name: "AttractionHotPlaceList" });
};

const moveHotPlaceWrite = () => {
  router.replace({ name: "AttractionHotPlaceWrite" });
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
            <RouterLink aria-current="page" to="" @click="moveHotPlace"
              >핫플 보기</RouterLink
            >
          </div>
          <div class="menu">
            <RouterLink aria-current="page" to="/board">Q&A게시판</RouterLink>
          </div>
          <div class="menu"></div>
          <div v-if="isLogin == false" id="user_menu">
            <div class="userJoin">
              <RouterLink id="navJoin" to="/user/join">회원가입</RouterLink>
            </div>
            <div class="userLogin">
              <RouterLink id="navLogin" to="/user/login">로그인</RouterLink>
            </div>
          </div>
          <div v-else id="user_menu">
            <div class="loginUser">
              <RouterLink id="navHotPlace" to="" @click="moveHotPlaceWrite"
                >핫플 자랑</RouterLink
              >
            </div>
            <div class="loginUser">
              <RouterLink id="navMyPage" to="" @click="moveMyPage"
                >마이페이지</RouterLink
              >
            </div>
            <div class="loginUser">
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
  grid-template-columns: 200px 100px 100px 100px 100px 1000px 300px;
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
}
#user_menu {
  flex-wrap: wrap;
  display: flex;
  align-items: center;
}
.userJoin {
  flex: 1;
  text-align: center;
  padding-left: 80px;
  padding-top: 40px;
}
.userLogin {
  flex: 1;
  text-align: center;
  padding-top: 40px;
}
.loginUser {
  flex: 1;
  text-align: center;
  padding-top: 40px;
}
</style>
