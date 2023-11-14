<script setup>
import { computed } from "vue";

const props = defineProps({ currentPage: Number, totalPage: Number});
const emit = defineEmits(["pageChange"]);

const navigationSize = props.totalPage;

const startPage = computed(() => {
  // return parseInt((props.currentPage - 1) / navigationSize) * navigationSize + 1;
  let topPage = parseInt((props.currentPage - 1) / navigationSize) * navigationSize + 1;
  console.log("topPage : " + topPage)
  return topPage;
});

const endPage = computed(() => {
  let bottomPage =
    parseInt((props.currentPage - 1) / navigationSize) * navigationSize + navigationSize;

  // bottomPage = totalPage <= bottomPage ? totalPage : bottomPage;
  bottomPage = bottomPage <= props.totalPage ? bottomPage : props.totalPage;
  console.log("currentPage : ", props.currentPage);
  console.log("bottomPage : ", bottomPage)
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
  <div class="row">
    <ul class="pagination justify-content-center">
      <li class="page-item">
        <a class="page-link" @click="onPageChange(1)">최신</a>
      </li>
      <li class="page-item">
        <a class="page-link" @click="onPageChange(startPage == 1 ? 1 : props.currentPage - 1)">이전</a>
      </li>
      <template v-for="pg in range(startPage, endPage)" :key="pg">
        <li :class="currentPage === pg ? 'page-item active' : 'page-item'">
          <a class="page-link" @click="onPageChange(pg)">{{ pg }}</a>
        </li>
      </template>
      <li class="page-item">
        <a class="page-link" @click="onPageChange(endRange ? totalPage : endPage + 1)">다음</a>
      </li>
      <li class="page-item"><a class="page-link" @click="onPageChange(totalPage)">마지막</a></li>
    </ul>
  </div>
</template>

<style scoped>
a {
  cursor: pointer;
}
</style>
