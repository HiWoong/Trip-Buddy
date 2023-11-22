<script setup>
import { ref, onMounted, watch } from "vue";
import { useRouter } from "vue-router";
import { httpStatusCode } from "@/util/http-status";
import { useUserStore } from "@/stores/userStore.js";
const router = useRouter();
const userStore = useUserStore();
const {
  setFavorite,
  getFavorite,
  getLikes,
  getFavHotPlace,
  getmyFavHotPlaces,
  setmyFavHotPlaces,
  setmyStorageHotPlace,
  getmyStorageHotPlace,
} = userStore;
import { minHitCount } from "@/api/hotplaceApi.js";
const props = defineProps({
  favHotPlace: Object,
  userId: String,
});
const emit = defineEmits(["changeFav"]);

const deleteFavorite = async () => {
  await getFavorite(props.userId);
  let myFav = await getLikes();
  console.log(myFav);
  let newMyFav = myFav.filter((data) => data != props.favHotPlace.hotplaceId);
  console.log(newMyFav);
  console.log(props.userId);
  console.log(props.favHotPlace.hotplaceId);
  await setFavorite({ userId: props.userId, favorite: JSON.stringify(newMyFav) }, true);

  await getFavorite(props.userId);
  let newFav = await getLikes();
  console.log(newFav);

  minHitCount(
    props.favHotPlace,
    (response) => {
      if (response.status == httpStatusCode.OK) {
        props.favHotPlace.hitCount--;
      }
    },
    (error) => {
      console.error(error);
    }
  );
  emit("changeFav");
};
</script>

<template>
  <div class="favhotP">
    <div><img class="hotImage" :src="props.favHotPlace.image" /></div>
    <div class="favhotContents">
      <div class="favhotTitle">
        [{{ props.favHotPlace.hotplaceId }}] {{ props.favHotPlace.subject }}
      </div>
      <div class="favhotContent">{{ props.favHotPlace.content }}</div>
      <div>{{ props.favHotPlace.createdDate }}</div>
      <div class="deleteButtonDiv">
        <input @click="deleteFavorite" class="deleteButton" type="button" value="삭제" />
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
.favhotP {
  display: flex;
  flex-direction: row;
  width: 1000px;
  height: 250px;
  border: 3px dashed gray;
  border-radius: 15px;
  padding: 5px;
  margin: 5px 0;
}
.hotImage {
  width: 250px;
  height: 230px;
}
.favhotContents {
  display: flex;
  flex-direction: column;
  margin: 20px 0 0 20px;
}
.favhotTitle {
  margin: 10px 0 5px 0;
  font-size: 30px;
}
.favhotContent {
  font-size: 20px;
  height: 60px;
}
.deleteButtonDiv {
  width: 50px;
  height: 30px;
  margin: 2vh 0 0 65vh;
}
.deleteButton {
  width: 70px;
  height: 35px;
  background-color: rgb(247, 127, 157);
  border-radius: 5px;
  transition: all 0.25s;
  box-shadow: 1px 5px 0px 0px rgb(247, 127, 157);
  font-size: 16px;
  font-weight: 600;
}
.deleteButton:hover {
  box-shadow: 0px 0px 0px 0px rgb(247, 127, 157);
  margin-top: 5px;
  margin-bottom: 5px;
}
</style>
