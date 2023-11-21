<script setup>
import { ref, onMounted, onBeforeMount } from "vue";
import { useRouter } from "vue-router";
import { RouterView } from "vue-router";
import http from "@/util/http-common.js";

import BoardListRow from "@/components/board/BoardListRow.vue";
import PageNavigation from "@/components/common/PageNavigation.vue";

import { useCookies } from "vue3-cookies";
const { cookies } = useCookies();
const uid = cookies.get("userId");

const router = useRouter();

const articles = ref([]);

const currentPage = ref(1);
const totalPage = ref(5);
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
  getTotalPage();
};

const key = ref("");
const word = ref("");
const pgno = ref(1);
const sort = ref("");

const searchBoards = () => {
  currentPage.value = 1;
  getArticleList();
  getTotalPage();
  onPageChange(currentPage.value);
};

onMounted(() => {
  getArticleList();
  getTotalPage();
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
        param.value.spp +
        "&sort=" +
        sort.value
    )
    .then(({ data }) => {
      articles.value = data;
    });
};

const getTotalPage = () => {
  http
    .get("/articleapi/articleCount?pgno=" + "&key=" + key.value + "&word=" + word.value)
    .then(({ data }) => {
      totalPage.value = Math.ceil(data / param.value.spp);
    });
};

const moveWrite = () => {
  router.push({ name: "BoardWrite" });
};
</script>
<template>
  <div>
    <div>
      <div class="title">Q&A 게시판</div>
      <div class="content">
        <div class="searchOptions">
          <div v-if="uid != null" id="submitLogin">
            <input type="submit" class="writeButton" @click="moveWrite" value="글쓰기" />
          </div>
          <div class="sortType">
            <select name="sort" id="sort" aria-label="정렬조건" v-model="sort">
              <option value="" selected>정렬조건</option>
              <option value="register_time" selected>최신순</option>
              <option value="hit">조회순</option>
              <option value="article_no">글번호</option>
            </select>
          </div>
          <div class="searchType">
            <select name="key" id="key" aria-label="검색조건" v-model="key">
              <option value="" selected>검색조건</option>
              <option value="article_no">글번호</option>
              <option value="subject">제목</option>
              <option value="user_id">작성자</option>
            </select>
          </div>
          <div class="searchWord">
            <input
              type="text"
              name="word"
              id="word"
              placeholder="검색어를 입력해주세요."
              v-model="word"
            />
          </div>
          <div class="searchButton">
            <button id="btn-search" @click="searchBoards">검색</button>
          </div>
        </div>
        <div class="searchResult">
          <table>
            <thead>
              <tr class="section">
                <th class="articleNo">글번호</th>
                <th class="articleTitle">제목</th>
                <th class="articleAuthor">작성자</th>
                <th class="articleHit">조회수</th>
                <th class="articleDate">작성일</th>
              </tr>
            </thead>
            <tbody class="nextSection">
              <BoardListRow
                class="eachSection"
                v-for="article in articles"
                :key="article.articleNo"
                v-bind="article"
              />
            </tbody>
          </table>
        </div>
      </div>
    </div>
    <PageNavigation
      :current-page="currentPage"
      :total-page="totalPage"
      @pageChange="onPageChange"
    ></PageNavigation>
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
.title {
  margin: 10px 0 0 0;
  width: 100%;
  height: 50px;
  /* background-color: aliceblue; */
  display: flex;
  flex-direction: column;
  justify-content: center;
  font-size: 40px;
  flex-wrap: wrap;
}
.content {
  display: flex;
  flex-direction: column;
  flex-wrap: wrap;
  width: 90%;
}
.searchOptions {
  display: flex;
  flex-direction: row;
  justify-content: flex-end;
  flex-wrap: wrap;
  align-items: center;
  width: 100%;
  height: 50px;
}
#submitLogin {
  width: 750px;
}
.writeButton {
  margin-right: 707px;
  background-color: rgb(175, 171, 243);
  border-radius: 5px;
  width: 100px;
  height: 35px;
  transition: all 0.25s;
  box-shadow: 1px 5px 0px 0px rgb(175, 171, 243);
  font-size: 16px;
  font-weight: 600;
}
.writeButton:hover {
  box-shadow: 0px 0px 0px 0px rgb(175, 171, 243);
  margin-top: 10px;
  margin-bottom: 0px;
}
.sortType {
  width: 120px;
  padding-left: 10px;
}
.searchType {
  width: 90px;
}
.searchWord {
  width: 370px;
}
.searchButton {
  margin: 0 10px;
  width: 100px;
}
#sort {
  width: 90px;
  height: 35px;
  font-size: 18px;
  border: 2px solid gray;
  border-radius: 5px;
}
#key {
  width: 90px;
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
  height: 35px;
  color: #292929;
  font-weight: 500;
  border-radius: 5px;
  background: transparent;
  transition: all 0.2s ease;
  position: relative;
  display: inline-block;
  box-shadow: inset 2px 2px 2px 0px rgba(255, 255, 255, 0.5), 7px 7px 20px 0px rgba(0, 0, 0, 0.1),
    4px 4px 5px 0px rgba(0, 0, 0, 0.1);
  outline: none;
  border: none;
  transition: all 0.3s ease;
  overflow: hidden;
  text-align: center;
  font-size: 19px;
}
#btn-search:after {
  position: absolute;
  content: " ";
  z-index: -1;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-color: #f5bb4f;
  transition: all 0.3s ease;
}
#btn-search:hover {
  background: transparent;
  box-shadow: 4px 4px 6px 0 rgba(255, 255, 255, 0.5), -4px -4px 6px 0 rgba(116, 125, 136, 0.2),
    inset -4px -4px 6px 0 rgba(255, 255, 255, 0.5), inset 4px 4px 6px 0 rgba(116, 125, 136, 0.3);
  font-size: 23px;
}
#btn-search:hover:after {
  -webkit-transform: scale(2) rotate(180deg);
  transform: scale(2) rotate(180deg);
  box-shadow: 4px 4px 6px 0 rgba(255, 255, 255, 0.5), -4px -4px 6px 0 rgba(116, 125, 136, 0.2),
    inset -4px -4px 6px 0 rgba(255, 255, 255, 0.5), inset 4px 4px 6px 0 rgba(116, 125, 136, 0.3);
}
.searchResult {
  margin: 0 15%;
  padding: 0 0 5px 0;
  border: 2px solid gray;
  border-radius: 15px;
  width: 85%;
}
.section {
  display: flex;
  width: 1445px;
  height: 45px;
  justify-content: flex-start;
  align-items: center;
  flex-wrap: wrap;
  font-size: 18px;
  text-align: center;
}
.articleNo {
  width: 100px;
  /* border: 1px solid #292929;
  border-radius: 5px; */
  margin: 0 50px 0 10px;
}
.articleTitle {
  width: 500px;
  /* border: 1px solid #292929;
  border-radius: 5px; */
  margin: 0 50px 0 50px;
}
.articleAuthor {
  width: 200px;
  /* border: 1px solid #292929;
  border-radius: 5px; */
  margin: 0 10px 0 10px;
}
.articleHit {
  width: 100px;
  /* border: 1px solid #292929;
  border-radius: 5px; */
  margin: 0 10px 0 10px;
}
.articleDate {
  width: 345px;
  /* border: 1px solid #292929;
  border-radius: 5px; */
}

.nextSection {
  display: flex;
  flex-direction: column;
}
.eachSection {
  width: 1445px;
  display: flex;
  flex-direction: row;
  justify-content: flex-start;
  margin: 2px 0;
  font-size: 18px;
  border-bottom: 1px solid rgb(221, 220, 220);
}
</style>
