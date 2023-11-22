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

const tooltip = ref(
  `◆ 여행 플래너 저장, Q&A 게시판 등록, 댓글 등록, 핫 플레이스 등록 및 좋아요 기능은 로그인 후 사용 가능합니다.\n\n` +
    `◆ 여행 플래너는 여행 계획 좌측 상단에 있는 달력 모양 아이콘을 클릭하면 최대 5일차까지 생성할 수 있으며 우측 상단에 있는 다운 아이콘을 클릭하면 계획을 저장할 수 있습니다.\n\n` +
    `◆ 여행 플래너에서 검색 후 길찾기 버튼을 통해 현재 위치로부터 해당 목적지까지의 택시 비용, 예상 시간 및 총 거리를 알 수 있습니다.\n\n` +
    `◆ 길찾기는 소형 자동차 기준이며 가장 빠르게 도착하는 경로를 제공합니다.`
);
tooltip.value.replace(/\n/g, "<br/>");

onMounted(async () => {
  const ui = cookies.get("userId");
  if (ui) {
    isLogin.value = true;
  }
});

const logout = async () => {
  await userLogoutStore({ userId: cookies.get("userId") });
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
// console.log("header var : isLogin : ", isLogin.value);
</script>

<template>
  <div>
    <div>
      <!-- header -->
      <nav>
        <div class="grid-container">
          <div id="logo">
            <RouterLink to="" @click="moveHome">Trip Buddy</RouterLink>
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
            <RouterLink aria-current="page" to="" @click="moveHotPlace">핫플 보기</RouterLink>
          </div>
          <div class="menu">
            <RouterLink aria-current="page" to="/board">Q&A게시판</RouterLink>
          </div>

          <div class="menu"></div>
          <div v-if="isLogin == false" id="user_menu">
            <div class="beforeLogin"></div>
            <div class="beforeLogin">
              <RouterLink id="navDesc" to="" :tooltip="tooltip">도움말</RouterLink>
            </div>
            <div class="beforeLogin">
              <RouterLink id="navJoin" to="/user/join">회원가입</RouterLink>
            </div>
            <div class="beforeLogin">
              <RouterLink id="navLogin" to="/user/login">로그인</RouterLink>
            </div>
          </div>
          <div v-else id="user_menu">
            <div class="loginUser">
              <RouterLink id="navDesc" to="" :tooltip="tooltip">도움말</RouterLink>
            </div>
            <div class="loginUser">
              <RouterLink id="navHotPlace" to="" @click="moveHotPlaceWrite">핫플 자랑</RouterLink>
            </div>
            <div class="loginUser">
              <RouterLink id="navMyPage" to="" @click="moveMyPage">마이페이지</RouterLink>
            </div>
            <div class="loginUser">
              <RouterLink id="navLogout" to="" @click="logout">로그아웃</RouterLink>
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
  height: 70px;
}
a {
  text-decoration-line: none;
  color: black;
}
.grid-container {
  align-content: center;
  display: grid;
  width: 100%;
  grid-template-columns: 200px 100px 100px 100px 100px 850px 450px;
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

.beforeLogin {
  flex: 1;
  text-align: center;
  padding-top: 40px;
}
.loginUser {
  flex: 1;
  text-align: center;
  padding-top: 40px;
}
#navDesc {
  white-space: pre-line;
}
[tooltip]:hover {
  position: relative;
}
[tooltip]:hover:after {
  content: attr(tooltip);
  position: absolute;
  width: 500px;
  height: 255px;
  text-align: start;
  right: 50px;
  background-color: rgba(0, 0, 0, 0.8);
  color: #ffffff;
  font-size: 15px;
  z-index: 999;
  border-radius: 5px;
  padding: 5px;
}
</style>
