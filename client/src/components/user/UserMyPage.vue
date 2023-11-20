<script setup>
import { ref, onMounted } from "vue";
import { useCookies } from "vue3-cookies";
import UserMyPageHotPlace from "@/components/user/UserMyPageHotPlace.vue";
import { useRouter } from "vue-router";
const { cookies } = useCookies();
const router = useRouter();
import { useUserStore } from "@/stores/userStore.js";
const userStore = useUserStore();
const { getFavorite, getLikes, getFavHotPlace, getmyFavHotPlaces, setmyFavHotPlaces } = userStore;
import UserPlan from "@/components/user/UserPlan.vue";

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
    // console.log(myHotPlaceFlag.value);
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
    // console.log(hotPlaceId);
    await getFavHotPlace(hotPlaceId);
  });
  favHotPlaces.value = await getmyFavHotPlaces();
});

const moveMyPageInfo = () => {
  router.push({ name: "UserMyPageInfo" });
};
</script>

<template>
  <div class="wholeLayout">
    <div class="menu">
      <div class="tab-content">
        <div class="clickMenu" :style="planStyleObject" @click="planC">나의 여행 계획</div>
      </div>
      <div class="tab-content">
        <div class="clickMenu" :style="myHotStyleObject" @click="myHotPlaceC">
          좋아요한 핫플레이스
        </div>
      </div>
      <div class="tab-content">
        <div class="clickMenu" :style="myWriteStyleObject" @click="myWritePlaceC">
          내가 등록한 핫플레이스
        </div>
      </div>
      <button style="border: none; width: 70px; height: 70px; background-color: transparent">
        <img
          id="submitImg"
          src="@/assets/img/settings.png"
          @click="moveMyPageInfo"
          style="width: 70px; height: 70px"
        />
      </button>
    </div>
    <div class="tab">
      <div v-if="myHotPlaceFlag">
        <UserMyPageHotPlace
          v-for="favHotPlace in favHotPlaces"
          :key="favHotPlace.hotplaceId"
          :favHotPlace="favHotPlace"
        />
      </div>
      <div v-if="planFlag">
        <UserPlan />
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
  width: 1900px;
  height: 100px;
  display: flex;
  flex-direction: row;
  align-items: center;
  text-align: center;
}
.tab {
  margin: 50px 0 0 100px;
  width: 90%;
  height: 800px;
  display: flex;
  flex-wrap: wrap;
  justify-content: center;
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
  display: flex;
  width: 100%;
  height: 100px;
  cursor: pointer;
}
.clickMenu {
  width: 100%;
  height: 100%;
  text-align: center;
  display: flex;
  justify-content: center;
  align-items: center;
}
</style>
