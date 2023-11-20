<script setup>
import { ref, onMounted } from "vue";
import { useRouter } from "vue-router";
const isClicked = ref(false);
const router = useRouter();
import { useCookies } from "vue3-cookies";
const { cookies } = useCookies();

import { useUserStore } from "@/stores/userStore.js";
const userStore = useUserStore();
const { getFavorite, setFavorite, getLikes } = userStore;

const props = defineProps({
  hotPlace: Object,
  myFav: Object,
});
const userId = cookies.get("userId");
// 현재 핫플레이스를 사용자 찜 목록에 저장
// 사용자 테이블에 자신이 좋아요 한 게시물의 id들이 저장되어 있고,
// 그거를 pinia로 가지고 와서 관리하면서 만약 이 게시물이 로딩될 때 목록
// 안에 있다면 isLoved는 true, 아니면 false
const isLoved = ref(false);
onMounted(() => {
  if (props.myFav.includes(props.hotPlace.hotplaceId)) {
    isLoved.value = true;
  }
});

const takePlace = async () => {
  if (!isLoved.value) {
    const abc = props.myFav;
    abc.push(props.hotPlace.hotplaceId);
    await setFavorite({
      userId: userId,
      favorite: JSON.stringify(abc),
    });
    isLoved.value = true;
  } else {
    // 좋아요 삭제 로직 만들기
    alert("좋아하는 마음이 어떻게 변해요?");
  }
};

const click = () => {
  isClicked.value = !isClicked.value;
  console.log(isClicked.value);
};
</script>
<template>
  <div class="wholeContent">
    <div>
      <div id="icon">
        <button id="favorite" @click="takePlace">
          <span v-if="isLoved" role="img" style="color: red">❤</span>
          <span v-else role="img">🤍</span>
        </button>
      </div>
      <img
        id="mainImage"
        :src="props.hotPlace.image"
        @click="click"
        style="width: 250px; height: 300px; border-radius: 2vh"
      />
    </div>
    <div class="placeDesc">
      <div class="placeTitle">{{ props.hotPlace.subject }}</div>
      <div v-show="isClicked" class="placeContent">{{ props.hotPlace.content }}</div>
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
.wholeContent {
  position: relative;
  cursor: pointer;
}
.placeDesc {
  position: absolute;
  top: 55%;
  left: 10%;
  width: 100%;
}
.placeTitle {
  font-size: 30px;
  font-weight: bold;
  color: honeydew;
}
.placeContent {
  font-size: 15px;
  color: beige;
}
#mainImage {
  position: relative;
}
#icon {
  display: flex;
  justify-content: end;
}
#favorite {
  position: absolute;
  z-index: 52;
  border: none;
  background-color: transparent;
  display: flex;
  justify-content: end;
}
span {
  font-size: 20px;
  margin: 10px 10px 0 0;
}
</style>
