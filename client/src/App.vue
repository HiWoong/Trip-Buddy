<script setup>
import { useRouter, RouterView } from "vue-router";
import Header from "./components/common/Header.vue";
</script>

<template>
  <Header />
  <!-- <HomeView /> -->
  <!-- <RouterLink></RouterLink> -->
  <router-view></router-view>
</template>

<style scoped></style>
