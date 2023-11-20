<script setup>
import { ref, onMounted } from "vue";
import { useCookies } from "vue3-cookies";
import UserMyPageHotPlace from "./UserMyPageHotPlace.vue";
const { cookies } = useCookies();

import { useUserStore } from "@/stores/userStore.js";
const userStore = useUserStore();
const { getFavorite, getLikes, getFavHotPlace, getmyFavHotPlaces, setmyFavHotPlaces } = userStore;

const userId = cookies.get("userId");
const myFav = ref([]);
const favHotPlaces = ref([]);

const planFlag = ref(true);
const myHotPlaceFlag = ref(false);
const myWritePlaceFlag = ref(false);

const planStyleObject = ref({
  backgroundColor: "gray",
  fontSize: "20px",
});

const myHotStyleObject = ref({
  backgroundColor: "",
  fontSize: "",
});

const myWriteStyleObject = ref({
  backgroundColor: "",
  fontSize: "",
});

const myHotPlaceC = () => {
  if (!myHotPlaceFlag.value) {
    myHotPlaceFlag.value = true;
    console.log(myHotPlaceFlag.value);
    myHotStyleObject.value.backgroundColor = "gray";
    myHotStyleObject.value.fontSize = "20px";
  }
  planFlag.value = false;
  myWritePlaceFlag.value = false;

  planStyleObject.value.backgroundColor = "";
  planStyleObject.value.fontSize = "";
  myWriteStyleObject.value.backgroundColor = "";
  myWriteStyleObject.value.fontSize = "";
};

const myWritePlaceC = () => {
  if (!myWritePlaceFlag.value) {
    myWritePlaceFlag.value = true;
    myWriteStyleObject.value.backgroundColor = "gray";
    myWriteStyleObject.value.fontSize = "20px";
  }
  planFlag.value = false;
  myHotPlaceFlag.value = false;

  planStyleObject.value.backgroundColor = "";
  planStyleObject.value.fontSize = "";
  myHotStyleObject.value.backgroundColor = "";
  myHotStyleObject.value.fontSize = "";
};

const planC = () => {
  if (!planFlag.value) {
    planFlag.value = true;
    planStyleObject.value.backgroundColor = "gray";
    planStyleObject.value.fontSize = "20px";
  }
  myHotPlaceFlag.value = false;
  myWritePlaceFlag.value = false;

  myWriteStyleObject.value.backgroundColor = "";
  myWriteStyleObject.value.fontSize = "";
  myHotStyleObject.value.backgroundColor = "";
  myHotStyleObject.value.fontSize = "";
};

onMounted(async () => {
  await setmyFavHotPlaces();
  await getFavorite(userId);
  myFav.value = await getLikes();
  myFav.value.forEach(async (hotPlaceId) => {
    console.log(hotPlaceId);
    await getFavHotPlace(hotPlaceId);
  });
  favHotPlaces.value = await getmyFavHotPlaces();
});
</script>

<template>
  <div class="wholeLayout">
    <div class="menu">
      <div class="tab-content">
        <span :style="planStyleObject" @click="planC">나의 여행 계획</span>
      </div>
      <div class="tab-content">
        <span :style="myHotStyleObject" @click="myHotPlaceC">좋아요한 핫플레이스</span>
      </div>
      <div class="tab-content">
        <span :style="myWriteStyleObject" @click="myWritePlaceC">내가 등록한 핫플레이스</span>
      </div>
    </div>
    <div class="tab">
      <div v-if="myHotPlaceFlag">
        <UserMyPageHotPlace
          v-for="favHotPlace in favHotPlaces"
          :key="favHotPlace.hotplaceId"
          :favHotPlace="favHotPlace"
        />
      </div>
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
}
.wholeLayout {
  width: 100%;
  height: 100%;
  display: flex;
  flex-direction: column;
}
.menu {
  width: 100%;
  height: 100px;
  display: flex;
  flex-direction: row;
}
.tab {
  margin: 50px 0 0 100px;
  width: 100%;
  height: 800px;
  display: flex;
  flex-wrap: wrap;
  justify-content: flex-start;
  overflow-y: scroll;
}
.tab::-webkit-scrollbar {
  width: 10px; /* 스크롤바의 너비 */
}
.tab::-webkit-scrollbar-thumb {
  height: 10%;
  background: rgb(252, 227, 118); /* 스크롤바의 색상 */
  border-radius: 15px;
}
.tab::-webkit-scrollbar-track {
  background: rgba(233, 214, 161, 0.5); /*스크롤바 뒷 배경 색상*/
}
.tab-content {
  flex: 1;
  height: 100px;
  margin: 0 100px;
  text-align: center;
}
</style>
