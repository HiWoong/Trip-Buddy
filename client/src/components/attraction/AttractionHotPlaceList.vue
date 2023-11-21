<script setup>
import { ref, onMounted } from "vue";
import AttractionHotPlace from "./AttractionHotPlace.vue";
import Last from "@/components/common/Last.vue";
import http from "@/util/http-common.js";
import { useCookies } from "vue3-cookies";
import { useUserStore } from "@/stores/userStore.js";
import { useRoute } from "vue-router";
import { useMenuStore } from "@/stores/menuStore.js"
import { storeToRefs } from "pinia";

const route = useRoute();

const { cookies } = useCookies();
const userStore = useUserStore();
const { getFavorite, getLikes } = userStore;

const menuStore = useMenuStore();
let { menuFlag, flagName } = storeToRefs(menuStore);

const myFav = ref([]);
const nowPage = ref(1);
const hotPlaces = ref([]);
const type = ref("created_date");
const typeString = ref("최신순");
const userId = cookies.get("userId");
onMounted(async () => {
  await paramChange();
  await getFavorite(userId);
  myFav.value = await getLikes();
});

const loadPlaces = async () => {
  // console.log(nowPage.value);
  await http
    .get("/hotplaceapi/list?pgno=" + nowPage.value + "&sort=" + type.value)
    .then(({ data }) => {
      hotPlaces.value = hotPlaces.value.concat(data);
      nowPage.value++;
    });
};

const sortType = () => {
  if (type.value == "created_date") {
    type.value = "hit_count";
    typeString.value = "좋아요순";
  } else if (type.value == "hit_count") {
    type.value = "visited_count";
    typeString.value = "조회순";
  } else {
    type.value = "created_date";
    typeString.value = "최신순";
  }
  nowPage.value = 1;
  hotPlaces.value = [];
  loadPlaces();
};

const paramChange = async() => {
  type.value = menuFlag.value;
  typeString.value = flagName.value;
}

</script>
<template>
  <div class="searchOptions">
    <div class="searchButton" data-type="circle">
      <button id="btn-search" @click="sortType">{{ typeString }}</button>
    </div>
  </div>
  <div class="DivHotPlaces">
    <AttractionHotPlace
      class="DivHotPlace"
      v-for="hotPlace in hotPlaces"
      :key="hotPlace.title"
      :hotPlace="hotPlace"
      :myFav="myFav"
    />
  </div>
  <Last @triggerIntersected="loadPlaces" />
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
.searchOptions {
  display: flex;
  flex-direction: row;
  flex-wrap: wrap;
  align-items: center;
  width: 100%;
  height: 50px;
  justify-content: flex-start;
}
.searchButton {
  margin: 0 10px;
  width: 100px;
}
#key {
  width: 150px;
  height: 35px;
  font-size: 18px;
  border: 2px solid gray;
  border-radius: 5px;
}
#word {
  width: 350px;
  height: 35px;
  font-size: 20px;
  margin: 0 10px;
  border: 2px solid gray;
  border-radius: 5px;
  padding: 5px;
}
#btn-search {
  width: 100px;
  padding: 0.5em 1em;
  font-size: 1em;
  font-weight: 400;
  font-family: "Helvetica", "Arial", sans-serif;
  border-radius: 4px;
  cursor: pointer;
  appearance: none;
  border: none;
  color: #ffffff;
  margin: 10px 0;
  background: linear-gradient(to right, #1488cc, #2b32b2);
  box-shadow: 0 2px 25px rgba(20, 137, 204, 0.5);
}
#btn-search:active {
  background: linear-gradient(to right, rgba(20, 136, 204, 0.9), rgba(43, 50, 178, 0.9));
}

.DivHotPlaces {
  /* background-color: #1b1d25; */
  width: 100%;
  display: flex;
  flex-wrap: wrap;
  justify-content: space-between;
  justify-content: space-around;
}

.DivHotPlace {
  background-color: rgb(222, 222, 222);
  display: inline-block;
  width: 250px;
  height: 300px;
  border-radius: 2vh;
  margin: 3vh 8vh 3vh 8vh;
  box-shadow: 2px 10px 10px 2px rgb(199, 199, 199);
}
</style>
