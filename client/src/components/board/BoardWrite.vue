<script setup>
import { ref } from "vue";
import { useRouter } from "vue-router";
import { RouterView } from "vue-router";
import http from "@/util/http-common.js";

const router = useRouter();

const subject = ref("");
const content = ref("");
const userinfo = JSON.parse(sessionStorage.getItem("userinfo"));

const createArticle = () => {
  http
    .post("articleapi/write", {
      subject: subject.value,
      content: content.value,
      userId: userinfo.userId,
    })
    .then(({ data }) => {
      let msg = "등록 처리 시 문제가 발생했습니다.";
      if (data === 1) {
        msg = "등록이 완료되었습니다.";
      }

      alert(msg);
      moveList();
    });
};

const moveList = () => {
  router.push({ name: "BoardList" });
};
</script>

<template>
  <div class="row justify-content-center mt-5">
    <div class="col-lg-8 col-md-10 col-sm-12">
      <h2 class="my-3 py-3 shadow-sm bg-light text-center">
        <mark class="sky">글쓰기</mark>
      </h2>
    </div>
    <div class="col-lg-8 col-md-10 col-sm-12">
      <form id="form-register" method="POST" action="">
        <input type="hidden" name="action" value="write" />
        <div class="mb-3">
          <label for="subject" class="form-label">제목 : </label>
          <input
            type="text"
            class="form-control"
            id="subject"
            name="subject"
            placeholder="제목..."
            v-model="subject"
          />
        </div>
        <div class="mb-3">
          <label for="content" class="form-label">내용 : </label>
          <textarea
            class="form-control"
            id="content"
            name="content"
            rows="7"
            v-model="content"
          ></textarea>
        </div>
        <div class="col-auto text-center">
          <button
            type="button"
            id="btn-register"
            class="btn btn-outline-primary mb-3"
            @click="createArticle"
          >
            글작성
          </button>
          <button type="reset" class="btn btn-outline-danger mb-3">초기화</button>
        </div>
      </form>
    </div>
  </div>
</template>

<style scoped></style>
