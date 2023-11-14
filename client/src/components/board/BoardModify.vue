<script setup>
import { ref } from "vue";
import { useRouter, useRoute } from "vue-router";
import { RouterView } from "vue-router";
import http from "@/util/http-common.js";

const router = useRouter();
const route = useRoute();

const article = ref({
  articleNo: Number,
  userId: String,
  subject: String,
  content: String,
  hit: Number,
  registerTime: String,
});

http.get("/articleapi/view/" + route.params.articleNo).then(({ data }) => {
  article.value = data;
});

const modifyArticle = () => {
  http
    .post("/articleapi/modify", {
      articleNo: article.value.articleNo,
      subject: article.value.subject,
      content: article.value.content,
    })
    .then(({ data }) => {
      let msg = "수정 처리 시 문제가 발생했습니다.";
      if (data === 1) {
        msg = "수정이 완료되었습니다.";
      }

      alert(msg);
      moveDetail();
    });
};

const moveDetail = () => {
  router.push({ name: "BoardList" });
};
</script>

<template>
  <div class="row justify-content-center mt-5">
    <div class="col-lg-8 col-md-10 col-sm-12">
      <h2 class="my-3 py-3 shadow-sm bg-light text-center">
        <mark class="sky">글수정</mark>
      </h2>
    </div>
    <div class="col-lg-8 col-md-10 col-sm-12">
      <form id="form-modify" method="POST" action="">
        <input type="hidden" name="action" value="modify" />
        <input type="hidden" name="articleno" value="${article.articleNo}" />
        <div class="mb-3">
          <label for="subject" class="form-label">제목 : </label>
          <input
            type="text"
            class="form-control"
            id="subject"
            name="subject"
            v-model="article.subject"
          />
        </div>
        <div class="mb-3">
          <label for="content" class="form-label">내용 : </label>
          <textarea
            class="form-control"
            id="content"
            name="content"
            rows="7"
            v-model="article.content"
          />
        </div>
        <div class="col-auto text-center">
          <button
            type="button"
            id="btn-modify"
            class="btn btn-outline-primary mb-3"
            @click="modifyArticle"
          >
            글수정
          </button>
          <button type="button" id="btn-list" class="btn btn-outline-danger mb-3">
            <RouterLink :to="{ name: 'BoardList' }"> 목록으로이동...</RouterLink>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<style scoped></style>
