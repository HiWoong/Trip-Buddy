<script setup>
import { useRouter } from "vue-router";
import { onMounted } from "vue";
const router = useRouter();
onMounted(async () => {
  if (sessionStorage.getItem("isLogin") != null) {
    sessionStorage.removeItem("isLogin");
    router.go();
  }
});
</script>

<template>
  <div class="row justify-content-center mt-5 pt-5">
    <div class="col-lg-8 col-md-10 col-sm-12 mt-5 pt-5">
      <h2 class="my-3 py-3 shadow-sm bg-light text-center">
        <mark class="orange">메인 페이지</mark>
      </h2>
    </div>
  </div>
</template>

<style scoped></style>
