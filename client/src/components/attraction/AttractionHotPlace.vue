<script setup>
import { ref } from "vue";
import { useRouter } from "vue-router";
const isClicked = ref(false);
const router = useRouter();
defineProps({
  hotPlace: Object,
});
// 사용자 테이블에 자신이 좋아요 한 게시물의 id들이 저장되어 있고,
// 그거를 pinia로 가지고 와서 관리하면서 만약 이 게시물이 로딩될 때 목록
// 안에 있다면 isLoved는 true, 아니면 false
const isLoved = ref(false);
const click = () => {
  isClicked.value = !isClicked.value;
  console.log(isClicked.value);
};

// 현재 핫플레이스를 사용자 찜 목록에 저장
const takePlace = () => {};
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
        :src="hotPlace.image"
        @click="click"
        style="width: 250px; height: 300px; border-radius: 2vh"
      />
    </div>
    <div class="placeDesc">
      <div class="placeTitle">{{ hotPlace.subject }}</div>
      <div v-show="isClicked" class="placeContent">{{ hotPlace.content }}</div>
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
