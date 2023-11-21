<script setup>
import { ref, onMounted } from "vue";
import { useCookies } from "vue3-cookies";
import UserMyPageHotPlace from "@/components/user/UserMyPageHotPlace.vue";
import UserMyPageMyHotPlace from "@/components/user/UserMyPageMyHotPlace.vue";
import { useRouter } from "vue-router";
import { useUserStore } from "@/stores/userStore.js";
import { httpStatusCode } from "@/util/http-status";
import { info } from "@/api/userApi";
import { totalPlans } from "@/api/planApi";
import UserPlanRow from "@/components/user/UserPlanRow.vue";

const { cookies } = useCookies();
const router = useRouter();
const userStore = useUserStore();
const {
  getFavorite,
  getLikes,
  getFavHotPlace,
  getmyFavHotPlaces,
  setmyFavHotPlaces,
  setmyStorageHotPlace,
  getmyStorageHotPlace,
} = userStore;

const userId = cookies.get("userId");
const myFav = ref([]);
const favHotPlaces = ref([]);
const mineHotPlaces = ref([]);

const planFlag = ref(true);
const myHotPlaceFlag = ref(false);
const myWritePlaceFlag = ref(false);

const newUser = ref([]);
const plans = ref([]);

const styleObject = ref({
  backgroundColor: "#332708",
  fontSize: "30px",
  color: "whiteSmoke",
  border: "5px solid #e0a200",
  "border-radius": "15px",
});

const planStyleObject = ref({});

const myHotStyleObject = ref({});

const myWriteStyleObject = ref({});

const myHotPlaceC = () => {
  myHotStyleObject.value = styleObject.value;
  planStyleObject.value = {};
  myWriteStyleObject.value = {};
  myHotPlaceFlag.value = true;
  planFlag.value = false;
  myWritePlaceFlag.value = false;
};

const myWritePlaceC = () => {
  myWriteStyleObject.value = styleObject.value;
  planStyleObject.value = {};
  myHotStyleObject.value = {};
  myWritePlaceFlag.value = true;
  planFlag.value = false;
  myHotPlaceFlag.value = false;
};

const planC = () => {
  planStyleObject.value = styleObject.value;
  myWriteStyleObject.value = {};
  myHotStyleObject.value = {};
  planFlag.value = true;
  myWritePlaceFlag.value = false;
  myHotPlaceFlag.value = false;
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

  // mystorage hotplace
  await setmyStorageHotPlace(cookies.get("userId"));
  mineHotPlaces.value = await getmyStorageHotPlace();

  getUserInfo(cookies.get("userId"));
  getTotalPlans(cookies.get("userId"));

  planStyleObject.value = styleObject.value;
});

const moveMyPageInfo = () => {
  router.push({ name: "UserMyPageInfo" });
};

const getUserInfo = async (userId) => {
  info(
    userId,
    (response) => {
      console.log(response.status);
      if (response.status === httpStatusCode.OK) {
        console.log("3. getUserInfo data >> ", response.data.userInfo);
        newUser.value = response.data.userInfo;
      } else {
        console.log("유저 정보 없음!!!!");
      }
    },
    async (error) => {
      console.error(
        "getUserInfo() error code [토큰 만료되어 사용 불가능.] ::: ",
        error.response.status
      );
    }
  );
};

const getTotalPlans = async (userId) => {
  await totalPlans(
    userId,
    (response) => {
      console.log(response.status);
      if (response.status === httpStatusCode.OK) {
        console.log("plans >> ", response.data);
        console.log(plans.value);
        plans.value = response.data;

        // console.log("new User", newUser.value);
        // console.log("new User ProfileImg : ", newUser.value.profileImage);
      } else {
        console.log("유저 정보 없음!!!!");
      }
    },
    async (error) => {
      console.error(
        "getUserInfo() error code [토큰 만료되어 사용 불가능.] ::: ",
        error.response.status
      );
    }
  );
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
        <UserPlanRow v-for="plan in plans" :key="plan.planId" v-bind="plan" />
      </div>
      <div v-if="myWritePlaceFlag">
        <UserMyPageMyHotPlace
          v-for="mineHotPlace in mineHotPlaces"
          :key="mineHotPlace.hotplaceId"
          :myHotPlace="mineHotPlace"
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
  height: 100%;
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
