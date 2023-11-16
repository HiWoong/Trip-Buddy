<script setup>
import { useRouter, RouterView } from "vue-router";
import Header from "./components/common/Header.vue";

// const script = document.createElement("script");
// script.src =
//   "//dapi.kakao.com/v2/maps/sdk.js?appkey=" +
//   import.meta.env.VITE_KAKAO_API_TOKEN +
//   "?autoload=false&libraries=services,clusterer,drawing";
// script.onload = () => window.kakao.maps.load();
// document.head.appendChild(script);
</script>

<template>
  <Header />
  <router-view></router-view>
</template>

<style scoped></style>
