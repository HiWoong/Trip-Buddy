<script setup>
import { ref, onMounted, onBeforeMount } from "vue";
import { useRouter } from "vue-router";
import { RouterView } from "vue-router";
import http from "@/util/http-common.js";

import BoardListRow from "@/components/board/BoardListRow.vue";
import PageNavigation from "@/components/common/PageNavigation.vue";

const router = useRouter();

const articles = ref([]);

const currentPage = ref(1);
const totalPage = ref(0);
const listSize = ref(20);

const param = ref({
  pgno: currentPage.value,
  spp: listSize.value,
});

const onPageChange = (val) => {
  console.log(val + "번 페이지로 이동 준비 끝!!!");
  currentPage.value = val;
  param.value.pgno = val;
  getArticleList();
};

const key = ref("");
const word = ref("");
const pgno = ref(1);

const searchBoards = () => {
  http
    .get(
      "/articleapi/list?pgno=" +
        pgno.value +
        "&key=" +
        key.value +
        "&word=" +
        word.value +
        "&spp=" +
        param.value.spp
    )
    .then(({ data }) => {
      console.log(data);
      articles.value = data;
    });
};

onMounted(() => {
  getArticleList();
});

const getArticleList = () => {
  http
    .get(
      "/articleapi/list?pgno=" +
        param.value.pgno +
        "&key=" +
        key.value +
        "&word=" +
        word.value +
        "&spp=" +
        param.value.spp
    )
    .then(({ data }) => {
      articles.value = data;
    });
};

// http.get("/articleapi/list?pgno=1&key=&word=").then(({ data }) => {
//   articles.value = data;
// });
</script>

<template>
  <div class="container mt-5 pt-5">
    <div class="row justify-content-center">
      <div class="col-lg-8 col-md-10 col-sm-12">
        <h2 class="my-3 py-3 shadow-sm bg-light text-center">
          <mark class="sky">글목록</mark>
        </h2>
      </div>
      <div class="col-lg-8 col-md-10 col-sm-12">
        <div class="row align-self-center mb-2">
          <div class="col-md-2 text-start">
            <button type="button" id="btn-mv-register" class="btn btn-outline-primary btn-sm">
              <RouterLink class="nav-link active" aria-current="page" to="/board/write"
                >글쓰기</RouterLink
              >
            </button>
          </div>
          <div class="col-md-7 offset-3">
            <form class="d-flex" id="form-search" action="">
              <input type="hidden" name="action" value="list" />
              <input type="hidden" name="pgno" value="1" />
              <select
                name="key"
                id="key"
                class="form-select form-select-sm ms-5 me-1 w-50"
                aria-label="검색조건"
                v-model="key"
              >
                <option value="" selected>검색조건</option>
                <option value="article_no">글번호</option>
                <option value="subject">제목</option>
                <option value="user_id">작성자</option>
              </select>
              <div class="input-group input-group-sm">
                <input
                  type="text"
                  name="word"
                  id="word"
                  class="form-control"
                  placeholder="검색어..."
                  v-model="word"
                />
                <button id="btn-search" class="btn btn-dark" type="button" @click="searchBoards">
                  검색
                </button>
              </div>
            </form>
          </div>
        </div>
        <table class="table table-hover">
          <thead>
            <tr class="text-center">
              <th scope="col">글번호</th>
              <th scope="col">제목</th>
              <th scope="col">작성자</th>
              <th scope="col">조회수</th>
              <th scope="col">작성일</th>
            </tr>
          </thead>
          <tbody>
            <BoardListRow v-for="article in articles" :key="article.articleNo" v-bind="article" />
          </tbody>
        </table>
      </div>
    </div>
    <PageNavigation
      :current-page="currentPage"
      :total-page="totalPage"
      @pageChange="onPageChange"
    ></PageNavigation>
  </div>
</template>

<style scoped></style>
