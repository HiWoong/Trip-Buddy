<script setup>
import { computed } from "vue";

const props = defineProps({ currentPage: Number, totalPage: Number });
const emit = defineEmits(["pageChange"]);

const navigationSize = props.totalPage;

const startPage = computed(() => {
  // return parseInt((props.currentPage - 1) / navigationSize) * navigationSize + 1;
  let topPage = parseInt((props.currentPage - 1) / navigationSize) * navigationSize + 1;
  console.log("topPage : " + topPage);
  return topPage;
});

const endPage = computed(() => {
  let bottomPage =
    parseInt((props.currentPage - 1) / navigationSize) * navigationSize + navigationSize;

  // bottomPage = totalPage <= bottomPage ? totalPage : bottomPage;
  bottomPage = bottomPage <= props.totalPage ? bottomPage : props.totalPage;
  console.log("currentPage : ", props.currentPage);
  console.log("bottomPage : ", bottomPage);
  // return bottomPage;
  return bottomPage;
  // return props.totalPage < lastPage ? lastPage : props.totalPage;
});

const endRange = computed(() => {
  return parseInt((props.totalPage - 1) / navigationSize) * navigationSize < props.currentPage;
});

function range(start, end) {
  const list = [];
  for (let i = start; i <= end; i++) list.push(i);
  return list;
}

function onPageChange(pg) {
  console.log("onPageChange" + pg + "로 이동!!!");
  emit("pageChange", pg);
}
</script>

<template>
  <div class="wholeContent">
    <div class="mostFore" @click="onPageChange(1)">
      <img src="@/assets/img/mostFore.png" style="width: 25px; height: 25px" />
    </div>
    <div class="fore" @click="onPageChange(props.currentPage == 1 ? 1 : props.currentPage - 1)">
      <img src="@/assets/img/fore.png" style="width: 25px; height: 25px" />
    </div>
    <template v-for="pg in range(startPage, endPage)" :key="pg">
      <div :class="currentPage === pg ? 'activePage' : 'page'" @click="onPageChange(pg)">
        {{ pg }}
      </div>
    </template>
    <div
      class="rear"
      @click="
        onPageChange(props.currentPage == props.totalPage ? totalPage : props.currentPage + 1)
      "
    >
      <img src="@/assets/img/rear.png" style="width: 25px; height: 25px" />
    </div>
    <div class="mostRear" @click="onPageChange(totalPage)">
      <img src="@/assets/img/mostRear.png" style="width: 25px; height: 25px" />
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
  padding: 0;
  margin: 0;
  font-family: "NanumSquare";
}
.wholeContent {
  margin: 10px 0;
  padding: 0 200px 0 0;
  display: flex;
  justify-content: flex-end;
  height: 50px;
  align-items: center;
  flex-wrap: wrap;
  cursor: pointer;
  text-align: center;
}
.mostRear {
  width: 50px;
}
.rear {
  width: 50px;
}
.fore {
  width: 50px;
}
.mostFore {
  width: 50px;
}
.page {
  padding: 4px 0 0 0;
  border: 1px solid gray;
  border-radius: 50%;
  font-size: 17px;
  height: 34px;
  width: 35px;
  margin: 0 5px 0 5px;
}
.activePage {
  padding: 4px 0 0 0;
  border: none;
  color: whitesmoke;
  font-size: 17px;
  font-weight: 500;
  border-radius: 50%;
  height: 34px;
  width: 35px;
  background-color: rgb(238, 179, 71);
  margin: 0 5px 0 5px;
}
</style>
